package com.upg.ucars.tools.imp.excel;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
/**
 * 
 * 功能说明：TODO(实体工具) 用户excel导入导出
 * @author shentuwy  
 * @date 2012-1-29 下午1:24:36 
 *
 */
public class EntryUtil {
	/**
	 * 调用实体默认的构造方法，获取实体对象
	 * @param clazz 实体的类对象
	 * @return
	 */
	public static Object getEntity(String clazz){
		try{
			Class c=Class.forName(clazz);
			Constructor cons=c.getDeclaredConstructor();
			return cons.newInstance(null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 为实体对象设置值
	 * @param entity 实体对象
	 * @param fieldName 实体字段名（包含set方法的变量）
	 * @param fieldValue 字段值
	 * @throws Exception
	 */
	public static void setValue(Object entity,String fieldName,Object fieldValue)throws Exception{
		Field field=entity.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(entity, fieldValue);
	}
	/**
	 * 获取实体的字段值
	 * @param entity 实体对象
	 * @param fieldName 实体的字段名称（包含get方法的变量）
	 * @return 字段值
	 * @throws Exception
	 */
	public static Object getValue(Object entity,String fieldName)throws Exception{
		Field field=entity.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(entity);
	}
}
