package com.upg.ucars.util;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * Bean处理工具类
 */
public class BeanWrapperUtils {
	public BeanWrapperUtils() {
	}
	/**
	 * 取目标bean的所有属性名称
	 * @param beanClaz		对象类
	 * @return				bean属性名称(String)集合
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static List getBeanPropertyName(Object obj){
		List propNames = new ArrayList();
		BeanWrapper wrapper = new BeanWrapperImpl(obj);
		PropertyDescriptor[] propDesc = wrapper.getPropertyDescriptors();
		for(int i = 0;i < propDesc.length;i++) {
			if("class".equals(propDesc[i].getName())) continue;
			propNames.add(propDesc[i].getName());
		}
		return propNames;
	}
	/**
	 * 设置对象属性值
	 * @param obj
	 * @param propName
	 * @param value
	 */
	public static void setBeanProperty(Object obj, String propName, Object value) {
		BeanWrapper wrapper = new BeanWrapperImpl(obj);
		wrapper.setPropertyValue(propName,value);
	}
	/**
	 * 取对象属性值
	 * @param obj
	 * @param propName
	 * @return
	 */
	public static Object getBeanPropertyValue(Object obj, String propName) {
		BeanWrapper wrapper = new BeanWrapperImpl(obj);
		return wrapper.getPropertyValue(propName);
	}
	/**
	 * 取属性字段的类型
	 * @param obj		类对象
	 * @param propName	属性名称
	 * @return
	 */
	public static Class getPropertyType(Object obj,String propName) {
		BeanWrapper wrapper = new BeanWrapperImpl(obj);
		return wrapper.getPropertyType(propName);
	}
	/**
	 * 将bean对象转换为XML格式的字串,支持嵌套bean
	 * @param obj	bean对象
	 * @return String xml字串
	 */
	public static String beanToXml(Object obj) {
		StringBuffer buf = new StringBuffer();
		List props = getBeanPropertyName(obj);
		Iterator it = props.iterator();
		String propName = "";
		while(it.hasNext()) {
			propName = (String)it.next();
			buf.append("<");
			buf.append(propName);
			buf.append(">");
			Object value = getBeanPropertyValue(obj, propName);
			Class clz = getPropertyType(obj,propName);
			if(clz.isPrimitive() || isWrapClass(clz) || clz == String.class) {
				buf.append(value);
			} else {
				buf.append(beanToXml(value));
			}
			buf.append("</");
			buf.append(propName);
			buf.append(">");
		}
		
		return buf.toString();
	}
	/**
	 * 是否为基本类型及基本类型的扩展(如int,Integer..)
	 * @param clz	类
	 * @return boolean
	 */
	public static boolean isWrapClass(Class clz) {
		try {
			return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
		} catch (Exception e) {
			return false;
		}
	}	
	

}
