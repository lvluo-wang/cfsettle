package com.upg.ucars.factory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

import org.apache.commons.beanutils.PropertyUtils;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.ICommonDAO;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.util.PropertyTransVo;
import com.upg.ucars.util.StringUtil;

/**
 * 数据集合属性转换类
 * 
 * @author alw
 * @author shentuwy
 */
public class DynamicPropertyTransfer {

	/** 增加map属性的字段名 */
	public static final String EXT_PROPERTY_MAP_KEY = "extProps";
	
	public static final <T> T transform(T entity,List<PropertyTransVo> propertyConfigs ){
		List<T> list = new ArrayList<T>(1);
		list.add(entity);
		list = transform(list,propertyConfigs);
		return list.get(0);
	}
	
	private static final List<Object>[] getRoigValues(Collection<?> collect,List<PropertyTransVo> propertyConfigs ){
		List<Object>[] result = new List[propertyConfigs.size()];
		if(collect!=null){
			Iterator<?> it = collect.iterator();
			while(it.hasNext()){
				Object bean = it.next();
				for(int i=0;i<propertyConfigs.size();i++){
					//Object value = BeanUtils.getProperty(bean, propertyConfigs.get(i).getOrigProperty());
					Object value = null;
					if (bean instanceof Map) {
						value = ((Map)bean).get(propertyConfigs.get(i).getOrigProperty());
					}else{
						value=getBeanPropertyValue(bean,propertyConfigs.get(i).getOrigProperty());
					}
					if(result[i]==null) result[i] = new ArrayList<Object>();
					if(!result[i].contains(value))
						result[i].add(value);
				}
			}
		}
		return result;
	}
	
	
	private static final Object getBeanPropertyValue(Object bean,String propertyName){
		Object result = null;
		if (bean != null && propertyName != null && propertyName.trim().length() > 0) {
			result=BeanMap.create(bean).get(propertyName);
			if (result == null ) {
				Map extProps = (Map) BeanMap.create(bean).get(EXT_PROPERTY_MAP_KEY);
				if (extProps != null) {
					result = extProps.get(propertyName);
				}
			}
		}
		return result;
	}
	
	private static final Map<Object,Object>[] getValueLibs(List<PropertyTransVo> propertyConfigs,List<Object>[] origValues){
		
		@SuppressWarnings("unchecked")
		Map<Object,Object>[] result = new HashMap[propertyConfigs.size()];
		ICommonDAO commonDao = DAOFactory.getCommonDao();
		
		for(int i=0;i<propertyConfigs.size();i++)
		{
			result[i]= new HashMap<Object,Object>();
			if(origValues[i] == null || origValues[i].size() == 0){
				continue;
			}
			@SuppressWarnings("rawtypes")
			List destBeanList = commonDao.findByOneProperty(propertyConfigs.get(i).getCls(), propertyConfigs.get(i).getDestKeyProperty(), origValues[i]);
			// 将查询到对象构造成 key:value，便于后续使用
			for(int j=0;j<destBeanList.size();j++)
			{
				Object destBean = destBeanList.get(j);
				//String key = BeanUtils.getProperty(destBean, propertyConfigs.get(i).getDestKeyProperty());   // 以具体需要转换的值作为key
				Object key = BeanMap.create(destBean).get(propertyConfigs.get(i).getDestKeyProperty());
				result[i].put(key, destBean);
			}
		}
		return result;
	}
	
	public static final <T> List<T> transformExtProps(Collection<T> collect,String propertyName,Object propertyValue){
		List<T> result = new ArrayList<T>();
		BeanGenerator  bg = null;
		if (collect != null && collect.size() > 0) {
			Iterator<T> it = collect.iterator();
			while(it.hasNext()){
				T bean = it.next();
				Map extProps = null;
				if (bean instanceof Map) {
					extProps = (Map) bean;
					result.add((T)extProps);
				}else{
					//--构造BeanGenerator 并动态增加属性
					extProps = (Map) BeanMap.create(bean).get(EXT_PROPERTY_MAP_KEY);
					if (extProps != null){
						result.add(bean);
					} else {
						if(bg==null){
							bg = getBeanGenerator(bean.getClass());
							bg.addProperty(EXT_PROPERTY_MAP_KEY, Map.class);
						}
						extProps = new HashMap();
						T destObj = (T) bg.create();
						copyBeanProperties(destObj, bean);
						BeanMap beanMap = BeanMap.create(destObj);
						beanMap.put(EXT_PROPERTY_MAP_KEY, extProps);
						//-------------
						result.add(destObj);
					}
				}
				extProps.put(propertyName, propertyValue);
			}
		}
		return result;
	}
	
	/**
	 * 动态加入map属性来扩充属性
	 * 
	 * @param collect
	 * @param propertyConfigs
	 * @return
	 */
	public static final <T> List<T> transformExtProps(Collection<T> collect,List<PropertyTransVo> propertyConfigs ) 	
	{
		
		if(propertyConfigs == null || propertyConfigs.size() == 0){
			throw ExceptionManager.getException(ServiceException.class,
					ErrorCodeConst.SYS_ERROR,new String[]{"List<PropertyTransVo>的数据为空，请检查传入数据!" });
		}
		
		// 循环集合对象，收集需要转换对象的属性值
		if(collect!=null)
		{
			List<Object>[] roigValues = getRoigValues(collect, propertyConfigs);
						
			Map<Object,Object>[] valueLibs = getValueLibs(propertyConfigs, roigValues);
			
			List<String> propertyNameList = getTranslatedPropertyNames(propertyConfigs);
			
			BeanGenerator  bg = null;
			List<T> returnLs = new ArrayList<T>();
			try{
				Iterator<T> it = collect.iterator();
				while(it.hasNext())
				{
					T bean = it.next();
					
					Map extProps = null;
					
					if (bean instanceof Map) {
						extProps = (Map) bean;
						returnLs.add((T)extProps);
					}else{
						//--构造BeanGenerator 并动态增加属性
						extProps = (Map) BeanMap.create(bean).get(EXT_PROPERTY_MAP_KEY);
						if (extProps != null){
							returnLs.add(bean);
						} else {
							if(bg==null){
								bg = getBeanGenerator(bean.getClass());
								bg.addProperty(EXT_PROPERTY_MAP_KEY, Map.class);
							}
							extProps = new HashMap();
							T destObj = (T) bg.create();
							copyBeanProperties(destObj, bean);
							BeanMap beanMap = BeanMap.create(destObj);
							beanMap.put(EXT_PROPERTY_MAP_KEY, extProps);
							//-------------
							returnLs.add(destObj);
						}
					}
					
					List<Object> propertyValues= new ArrayList<Object>();
					for(int i=0;i<propertyConfigs.size();i++)
					{
						PropertyTransVo  propertyConf = propertyConfigs.get(i);					
						Map<Object,Object> mapLib = valueLibs[i];

						Object key = null;
						if (bean instanceof Map) {
							key = ((Map)bean).get(propertyConfigs.get(i).getOrigProperty());
						}else{
							key = getBeanPropertyValue(bean,propertyConfigs.get(i).getOrigProperty());
						}
						String[] props = propertyConf.getDestValueProperty().split("#");
						Object dictObj = mapLib.get(key);
						if(dictObj!=null){
							BeanMap beanMap = BeanMap.create(dictObj);
							for(int j = 0; j< props.length; j++){
								Object value = beanMap.get(props[j]);
								propertyValues.add(value);
							}
						}else{
							for(int j = 0; j< props.length; j++){
								propertyValues.add(null);
							}
						}
					}
					
					if (propertyNameList != null && propertyValues != null && propertyNameList.size() == propertyValues.size()) {
						for (int i = 0 ; i < propertyNameList.size(); i++) {
							extProps.put(propertyNameList.get(i), propertyValues.get(i));
						}
					}
					
				}
			}catch(Exception e)
			{
				e.printStackTrace();
				throw ExceptionManager.getException(ServiceException.class,	ErrorCodeConst.SYS_ERROR,	new String[] { e.getMessage() },e);
			}
			collect = null;  // 清空源数据集
			return returnLs;
			
		}else
			return new ArrayList<T>();
		
	}
	
	private static final List<String> getTranslatedPropertyNames(List<PropertyTransVo> propertyConfigs){
		List<String> result = new ArrayList<String>();
		for(int i=0; i < propertyConfigs.size(); i++){
			PropertyTransVo  propertyConf = propertyConfigs.get(i);
			if(propertyConf.getTranslatedProperty() != null && !"".equals(propertyConf.getTranslatedProperty())){
				String[] arr = propertyConf.getTranslatedProperty().split("#");
				for(int j = 0; j<arr.length ; j++){
					result.add(arr[j]);
				}
			}else{
				String[] arr = propertyConf.getDestValueProperty().split("#");
				for(int j = 0; j<arr.length ; j++){
					result.add(propertyConf.getOrigProperty() + "_" + StringUtil.firstLower(arr[j]));
				}
			}
		}
		return result;
	}
	
	
	/**
	 * 对集合对象进行属性的抓取转换
	 * <br/>
	 * 适用于转换时用到的关联对象表的数据在百万之内，并且destKeyProperty对应属性创建了相应的索引。
	 * <br/>
	 * 
	 * @param collect  需要转换的源数据集合
	 * @param propertyConfigs  转换至用到的属性配置
	 *  <br>origProperty对应需要转换的源数据集合对象的属性，destKeyProperty 对应转换时关联对象的属性，destValueProperty 转换后关联对象的目标属性<br>
	 * @return
	 */
	public static final <T> List<T> transform(Collection<T> collect,List<PropertyTransVo> propertyConfigs ) 	
	{
		
		if(propertyConfigs == null || propertyConfigs.size() == 0){
			throw ExceptionManager.getException(ServiceException.class,
					ErrorCodeConst.SYS_ERROR,new String[]{"List<PropertyTransVo>的数据为空，请检查传入数据!" });
		}
		
		// 循环集合对象，收集需要转换对象的属性值
		if(collect!=null)
		{
			List<Object>[] roigValues = getRoigValues(collect, propertyConfigs);
						
			Map<Object,Object>[] valueLibs = getValueLibs(propertyConfigs, roigValues);
			
			// 根据查询到的对象执行转换
			return transform(collect,propertyConfigs,valueLibs);
		}else
			return new ArrayList<T>();
		
	}
	
	/**
	 * 根据查询到的对象执行转换
	 * <br> 转换过程中将动态增加一个属性用于记录转换后的值<br>
	 * <br> 新增加的属性名称规则为:默认为：对象名_属性名，如需要根据从Branch对象中获取brchName,则新生成的属性名称为 branch_brchName<br>
	 * <br> 可以自己通过PropertyTransVo.setTranslatedProperty指定转换后增加的属性名<br>
	 * @param collect
	 * @param cls
	 * @param propertyConfigs
	 * @param valueLibs
	 * @return
	 */
	
	private static final <T> List<T> transform(Collection<T> collect,List<PropertyTransVo> propertyConfigs,Map<Object,Object>[] valueLibs ) 
	{
		List<String> propertyNameList = getTranslatedPropertyNames(propertyConfigs);
		
//		//构造需要追加属性的名称
//		for(int i=0;i<propertyConfigs.size();i++)
//		{
//			PropertyTransVo  propertyConf = propertyConfigs.get(i);
//			String translatedProperty = propertyConf.getTranslatedProperty();
//			if(translatedProperty==null||"".equals(translatedProperty))
//			{
//				//Map mapLib = valueLibs[i];
//				//Object obj = mapLib.get( mapLib.keySet().iterator().next());
//				//String prefixName = obj.getClass().getSimpleName(); 
//				propertyNames[i]=StringUtil.firstLower(propertyConf.getOrigProperty()+"_"+propertyConfigs.get(i).getDestValueProperty());
//			}else
//				propertyNames[i]=translatedProperty;
//		}
		
		BeanGenerator  bg = null;
		
		
		//翻译后返回的集合
		List<T> returnLs = new ArrayList<T>();
		try{
			Iterator<T> it = collect.iterator();
			while(it.hasNext())
			{
				T bean = it.next();
				
				//--构造BeanGenerator 并动态增加属性
				if(bg==null){
					bg = getBeanGenerator(bean, propertyNameList);
				}
				//-------------
				
				List<String> propertyValues= new ArrayList<String>();
				for(int i=0;i<propertyConfigs.size();i++)
				{
					PropertyTransVo  propertyConf = propertyConfigs.get(i);					
					Map<Object,Object> mapLib = valueLibs[i];

					Object key = BeanMap.create(bean).get(propertyConfigs.get(i).getOrigProperty());
					String[] props = propertyConf.getDestValueProperty().split("#");
					Object dictObj = mapLib.get(key);
					if(dictObj!=null){
						BeanMap beanMap = BeanMap.create(dictObj);
						for(int j = 0; j< props.length; j++){
							Object val = beanMap.get(props[j]);
							String value = (val == null)? "" : val.toString();
							propertyValues.add(value);
						}
					}else{
						for(int j = 0; j< props.length; j++){
							propertyValues.add("");
						}
					}
				}
				//对添加完属性的类动态设置属性值
				T returnObj =dynamicSetProperty(bg,bean,propertyNameList,propertyValues);
				
				returnLs.add(returnObj);
				
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			throw ExceptionManager.getException(ServiceException.class,	ErrorCodeConst.SYS_ERROR,	new String[] { e.getMessage() },e);
		}
		collect = null;  // 清空源数据集
		return returnLs;
		
	}
	
	/**
	 * 针对某个对象动态添加属性，并设置相应值
	 * <br>主要用于针对单个对象动态增加属性并设置属性值
	 * @param origObj    源对象
	 * @param nameValueMap 需要添加属性的名称与值映射,名称为为字符型
	 * @return
	 */
	public static Object dynamicAddProperty(Object origObj,Map<String,Object> nameValueMap){
		
		//构造新对象
		BeanGenerator  bg = new BeanGenerator();
		bg.setSuperclass(origObj.getClass());
		for(String propName : nameValueMap.keySet()){
			//如果已有属性则不再动态添加
			boolean haveField = false;
			for(Field field : origObj.getClass().getDeclaredFields()){
				if(field.getName().equals(propName)){
					haveField = true;
					break;
				}
			}
			if(!haveField){
				Object value = nameValueMap.get(propName);
				bg.addProperty(propName,(value != null) ? value.getClass() : String.class);	//增加新属性
			}
		}
		Object destObj = bg.create();
		
		//复制原对象属性
		try {
			PropertyUtils.copyProperties(destObj, origObj);
		} catch (Exception e) {
			throw ExceptionManager.getException(ServiceException.class,	ErrorCodeConst.SYS_ERROR,new String[] { e.getMessage() });	
		}
		
		//设置新属性的值
		BeanMap  bm =  BeanMap.create (destObj);
		for(String propName : nameValueMap.keySet()){
			bm.put(propName, nameValueMap.get(propName));
		}
		return destObj;
	}

	
	
	/**
	 * 针对某个对象动态添加属性，并设置相应值
	 * <br>主要用于针对单个对象动态增加属性并设置属性值
	 * @param origObj    源对象
	 * @param propertyNames  需要添加的属性名称
	 * @param propertyValues 需要添加属性的值
	 * @return
	 */
	public static <T> T dynamicAddProperty(T origObj,List<String>propertyName,List<String> propertyValue)
	{
		BeanGenerator  bg = new BeanGenerator();
		bg.setSuperclass(origObj.getClass());
		//bg.addProperty(propertyName, String.class);
		
		return dynamicAddProperty(bg,origObj,propertyName,propertyValue);
	}
	
	/**
	 * 给对象动态增加一个属性，并设置相应的值
	 * @param origObj       源对象
	 * @param propertyName  需要添加的属性名称
	 * @param propertyValue 需要添加属性的值
	 * @return
	 */
	public static Object dynamicAddProperty(Object origObj,String propertyName,String propertyValue){
		List<String> propertyList = new ArrayList<String>(1);
		propertyList.add(propertyName);
		List<String> valueList = new ArrayList<String>(1);
		valueList.add(propertyValue);
		return dynamicAddProperty(origObj,propertyList,valueList);
	}
	
	/**
	 * 针对添加属性后的代理类，动态设置属性值
	 * <br>主要用于对于集合对象进行设置时候使用，可以共用一个BeanGenerator<br>
	 * @param bg      追加属性后的代理类生成器
	 * @param origObj  数据源对象
	 * @param propertyNames  需要设置的属性名称
	 * @param propertyValues  设置的属性值
	 * @return
	 */
	public static final <T> T dynamicSetProperty(BeanGenerator  bg,T origObj,List<String> propertyNames,List<?> propertyValues)
	{
		
		if(propertyNames==null||propertyValues==null||propertyNames.size()!=propertyValues.size())
			throw ExceptionManager.getException(ServiceException.class,	ErrorCodeConst.SYS_ERROR,	new String[] { "propertyNames与propertyValues的数据数量不一致，请检查传入数据!" });

		@SuppressWarnings("unchecked")
		T destObj = (T) bg.create();
		setDynamicBeanProperties(destObj, origObj, propertyNames, propertyValues);
		return destObj;
		
	}
	
	/**
	 * 动态添加属性，并设置相应的值
	 * <br>主要用于对于集合对象进行设置时候使用，可以共用一个BeanGenerator<br>
	 * @param bg
	 * @param origObj
	 * @param propertyNames
	 * @param propertyValues
	 * @return
	 */
	private static final <T> T dynamicAddProperty(BeanGenerator bg,T origObj,List<String> propertyNames,List<String> propertyValues){
		if(bg==null){
			bg = getBeanGenerator(origObj.getClass());
		}
		addStringProperty(bg, propertyNames);
		return dynamicSetProperty(bg,origObj,propertyNames,propertyValues);
	}
	
	private static final BeanGenerator getBeanGenerator(Class<?> clazz){
		BeanGenerator generator = new BeanGenerator();
		generator.setSuperclass(clazz);
		return generator;
	}
	
	private static final BeanGenerator addStringProperty(BeanGenerator generator,List<String> propertyNames){
		if (generator != null && propertyNames != null) {
			for (String pn : propertyNames) {
				if (pn != null && pn.length() > 0) {
					generator.addProperty(pn, String.class);
				}
			}
		}
		return generator;
	}
	private static final <T> BeanGenerator getBeanGenerator(T object,List<String> propertyNames){
		BeanGenerator generator = getBeanGenerator(object.getClass());
		if (propertyNames != null) {
			List<String> fieldNameList = getFieldNames(object.getClass());
			List<String> addPropertyNames = new ArrayList<String>();
			for (String pn : propertyNames) {
				if (!fieldNameList.contains(pn)) {
					addPropertyNames.add(pn);
				}
			}
			addStringProperty(generator, addPropertyNames);
		}
		return generator;
	}
	
	private static final <T> void copyBeanProperties(T destBean,T origBean){
		if (destBean != null && origBean != null) {
			try{
				PropertyUtils.copyProperties(destBean, origBean);
			} catch (Exception ex) {
				//
			}
		}
	}
	
	private static final <T> void setDynamicBeanProperties(T dynamicBean,T origBean,List<String> propertyNames,List<?> propertyValues){
		if (dynamicBean != null) {
			copyBeanProperties(dynamicBean, origBean);
			if (propertyNames != null && propertyValues != null && propertyNames.size() == propertyValues.size()) {
				BeanMap beanMap = BeanMap.create(dynamicBean);
				for (int i = 0; i < propertyNames.size(); i++) {
					beanMap.put(propertyNames.get(i), propertyValues.get(i));
				}
			}
		}
	}
	
	private static final Map<String,List<String>> CLASS_FIELD_MAP = new HashMap<String,List<String>>();
	
	private static final List<String> getFieldNames(Class<?> clazz){
		List<String> fieldList = null;
		if (clazz != null) {
			String key = clazz.getClass().getName();
			fieldList = CLASS_FIELD_MAP.get(key);
			if (fieldList == null) {
				Field[] fields = clazz.getClass().getDeclaredFields();
				fieldList = new ArrayList<String>(fields.length);
				for (Field f : fields) {
					fieldList.add(f.getName());
				}
				CLASS_FIELD_MAP.put(key, fieldList);
			}
		}
		return fieldList;
	}
}
