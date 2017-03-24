package com.upg.ucars.factory;

import java.lang.reflect.Field;
import java.util.Map;

import net.sf.cglib.beans.BeanGenerator;
import net.sf.cglib.beans.BeanMap;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;

public class Transformer {
	
	private static final Logger logger = LoggerFactory.getLogger(Transformer.class);
	
	/**
	 * map对象转换为类对象<br>
	 * 如map中的key为对象中不存属性，则动态增加到对象中
	 */
	@SuppressWarnings("unchecked")
	public static Object mapToObject(Class clz,Map<String,Object> nameValueMap){
		BeanGenerator  bg = new BeanGenerator();
		bg.setSuperclass(clz);
		//构造新对象
		for(String propName : nameValueMap.keySet()){
			//如果已有属性则不再动态添加
			boolean haveField = false;
			for(Field field : clz.getDeclaredFields()){
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
		Object result = bg.create();
		
		//设置新属性的值
		BeanMap  bm =  BeanMap.create (result);
		for(String propName : nameValueMap.keySet()){
			Object value = nameValueMap.get(propName);
			boolean haveField = false;
			if( value != null){
				for(Field field : clz.getDeclaredFields()){
					if(field.getName().equals(propName)){
						if(field.getType() == value.getClass()){
							bm.put(propName, value);
						}else{
//							if(field.getType() == java.lang.Long.class){
//								bm.put(propName,new Long(value.toString()));
//							}else 
							if(field.getType() == java.util.Date.class
									&& value.getClass() == java.sql.Date.class){
								java.util.Date date = new java.util.Date(((java.sql.Date)value).getTime());
								bm.put(propName,date);
							}else{
								try {
									Object obj = field.getType().getConstructor(String.class).newInstance(value.toString());
									bm.put(propName,obj);
								} catch (Exception ex) {
									String msg = propName + "构造属性值出错，数据类型不匹配：【filedType:" + field.getType() +
											"，valueType:" + value.getClass() + "】";
									logger.error(msg,ex);
									throw ExceptionManager.getException(ServiceException.class,ErrorCodeConst.SYS_ERROR,msg);
//									throw ExceptionManager.getException(ServiceException.class,ErrorCodeConst.SYS_ERROR_2,new String[]{field.getType().toString(),value.getClass().toString()});
								}
							}
						}
						haveField = true;
						break;
					}
				}
			}
			if(!haveField){
				bm.put(propName, value);
			}
		}
		return result;
	}
	
	
}