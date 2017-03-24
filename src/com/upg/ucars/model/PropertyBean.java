package com.upg.ucars.model;

public class PropertyBean {
	//属性名称
	private String 	propertyName;
	//字段名称
	private String 	columnName;
	//属性中文名
	private String 	propertyCnName;
	//属性类别
	private String 	propertyType;
	
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getPropertyCnName() {
		return propertyCnName;
	}
	public void setPropertyCnName(String propertyCnName) {
		this.propertyCnName = propertyCnName;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

}
