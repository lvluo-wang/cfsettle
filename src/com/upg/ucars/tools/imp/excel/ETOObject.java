package com.upg.ucars.tools.imp.excel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * 功能说明：TODO(excel文件中一行数据及对应的实体配置信息)
 * @author shentuwy  
 * @date 2012-1-29 下午1:29:04 
 *
 */
public class ETOObject implements Serializable {
	/**文件配置编码*/
	private String code;
	/**文件解析成功后的回调配置信息*/
	private String callBack;
	/**行数据对应的实体类描述（包含包结构）*/
	private String className;
	/**列配置信息集合*/
	private List<ColumnObject> columnList=new ArrayList<ColumnObject>();//列集合
	public ETOObject() {
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCallBack() {
		return callBack;
	}
	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List<ColumnObject> getColumnList() {
		return columnList;
	}
	public void setColumnList(List<ColumnObject> columnList) {
		this.columnList = columnList;
	}

	
}
