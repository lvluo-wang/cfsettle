package com.upg.ucars.tools.imp.excel;

import java.io.Serializable;
import java.util.HashMap;
/**
 * 
 * 功能说明：TODO(excel文件的页对象及配置信息)
 * @author shentuwy  
 * @date 2012-1-29 下午2:57:22 
 *
 */
public class SheetObject implements Serializable {
	private Integer sheetNumber;//页编号
	private Integer headRow;//头部所在行
	private Integer dataStartRow;//数据起始行
	private String className;//实体对应的类名（包含包结构）
	private String sheetName;//页面，等价于excel中的一页的页面
	private HashMap<Integer,ColumnObject> columnMap=new HashMap<Integer,ColumnObject>();//列集合
	public SheetObject() {
	}
	public Integer getSheetNumber() {
		return sheetNumber;
	}
	public void setSheetNumber(Integer sheetNumber) {
		this.sheetNumber = sheetNumber;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public HashMap<Integer, ColumnObject> getColumnMap() {
		return columnMap;
	}
	public void setColumnMap(HashMap<Integer, ColumnObject> columnMap) {
		this.columnMap = columnMap;
	}
	public Integer getDataStartRow() {
		return dataStartRow;
	}
	public void setDataStartRow(Integer dataStartRow) {
		this.dataStartRow = dataStartRow;
	}
	public Integer getHeadRow() {
		return headRow;
	}
	public void setHeadRow(Integer headRow) {
		this.headRow = headRow;
	}
	
}
