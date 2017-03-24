package com.upg.ucars.framework.bpm.assist.model;

import java.util.Date;

/** 
 * 任务实例对象
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */
public class VariableInstanceDTO {
	/**类型--字符串*/
	public final static String Type_String = "1";
	/**类型--整数*/
	public final static String Type_Long = "2";
	/**类型--整数*/
	public final static String Type_Integer = "3";
	/**类型--布尔值*/
	public final static String Type_Boolean = "4";
	/**类型--浮点数*/
	public final static String Type_Double = "5";
	/**类型--日期*/
	public final static String Type_Date = "6";
	
	/* 
	 V : org.jbpm.context.exe.VariableInstance 
     B : org.jbpm.context.exe.variableinstance.ByteArrayInstance 
     D : org.jbpm.context.exe.variableinstance.DateInstance 
     O : org.jbpm.context.exe.variableinstance.DoubleInstance 
     H : org.jbpm.context.exe.variableinstance.HibernateLongInstance 
     I : org.jbpm.context.exe.variableinstance.HibernateStringInstance 
     L : org.jbpm.context.exe.variableinstance.LongInstance 
     S : org.jbpm.context.exe.variableinstance.StringInstance 
     N : org.jbpm.context.exe.variableinstance.NullInstance 
     J : org.jbpm.context.exe.variableinstance.JcrNodeInstance 
	
	B org.jbpm.context.exe.converter.BooleanToStringConverter
	Y org.jbpm.context.exe.converter.BytesToByteArrayConverter
	E org.jbpm.context.exe.converter.ByteToLongConverter
	C org.jbpm.context.exe.converter.CharacterToStringConverter
	A org.jbpm.context.exe.converter.DateToLongConverter
	D org.jbpm.context.exe.converter.DoubleToStringConverter
	F org.jbpm.context.exe.converter.FloatToStringConverter
	G org.jbpm.context.exe.converter.FloatToDoubleConverter
	I org.jbpm.context.exe.converter.IntegerToLongConverter
	R org.jbpm.context.exe.converter.SerializableToByteArrayConverter
	H org.jbpm.context.exe.converter.ShortToLongConverter*/
	
	
	private String name;
	private String type;
	
	private Long longVal;
	private Integer intVal;
	private String stringVal;
	private Double doubleVal;
	private Date dateVal;	
	private Boolean booleanVal;
	
	public VariableInstanceDTO() {}
	
	
	public VariableInstanceDTO(String name) {
		super();
		this.name = name;
	}
	/**
	 * 设置值
	 * @param value
	 * @return
	 */
	public boolean setValue(Object value){
		if (value instanceof String){
			this.setStringVal((String)value);
			this.setType(Type_String);
		}else if(value instanceof Double){
			this.setDoubleVal((Double)value);
			this.setType(Type_Double);
		}else if(value instanceof Long){
			this.setLongVal((Long)value);
			this.setType(Type_Long);
		}else if(value instanceof Integer){
			this.setIntVal((Integer)value);
			this.setType(Type_Integer);
		}else if(value instanceof Boolean){
			this.setBooleanVal((Boolean)value);
			this.setType(Type_Boolean);
		}else if(value instanceof Date){
			this.setDateVal((Date)value);
			this.setType(Type_Date);
			
		}else{
			return false;
		}
		
		return true;
		
	}
	
	public Object getValue() {
		if (Type_String.equals(this.getType())){
			return this.getStringVal();
		}
		if (Type_Long.equals(this.getType())){
			return this.getLongVal();
		}
		if (Type_Boolean.equals(this.getType())){
			return this.getBooleanVal();
		}
		if (Type_Integer.equals(this.getType())){
			return this.getIntVal();
		}
		if (Type_Double.equals(this.getType())){
			return this.getDoubleVal();
		}
		if (Type_Date.equals(this.getType())){
			return this.getDateVal();
		}
		return null;
	}
	
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getLongVal() {
		return longVal;
	}
	public void setLongVal(Long longVal) {
		this.longVal = longVal;
	}
	
	public Integer getIntVal() {
		return intVal;
	}
	public void setIntVal(Integer intVal) {
		this.intVal = intVal;
	}
	public String getStringVal() {
		return stringVal;
	}
	public void setStringVal(String stringVal) {
		this.stringVal = stringVal;
	}
	public Double getDoubleVal() {
		return doubleVal;
	}
	public void setDoubleVal(Double doubleVal) {
		this.doubleVal = doubleVal;
	}
	public Date getDateVal() {
		return dateVal;
	}
	public void setDateVal(Date dateVal) {
		this.dateVal = dateVal;
	}
	public Boolean getBooleanVal() {
		if (booleanVal == null)
			return Boolean.FALSE;
		return booleanVal;
	}
	public void setBooleanVal(Boolean booleanVal) {
		this.booleanVal = booleanVal;
	}
	
}

 