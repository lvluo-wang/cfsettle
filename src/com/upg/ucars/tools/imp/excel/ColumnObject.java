package com.upg.ucars.tools.imp.excel;

import java.io.Serializable;
/**
 * excel导入导出，列对象
 * 功能说明：TODO(列对象)
 * @author shentuwy  
 * @date 2012-1-29 下午1:14:34 
 *
 */
public class ColumnObject implements Serializable {
	private boolean required;
	private String columnName;
	private String fieldName;//字段名称
	private ColumnFormatter columnFormatter;//列转换器
	public ColumnObject() {
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public ColumnFormatter getColumnFormatter() {
		return columnFormatter;
	}
	public void setColumnFormatter(ColumnFormatter columnFormatter) {
		this.columnFormatter = columnFormatter;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
}
