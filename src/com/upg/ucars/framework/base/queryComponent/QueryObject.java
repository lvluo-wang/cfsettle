package com.upg.ucars.framework.base.queryComponent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询对象
 * @author cuckoo
 *
 */
public class QueryObject implements Serializable,Cloneable{
	private String label;
	private String queryName;//查询名称
	private String queryValue;//查询值
	private String valueType;//值的类型
	private String valueList;//如果值类型为list，在页面端是select
	private String queryOperate;//查询操作 =，like
	private List<String> operate=new ArrayList<String>();//显示操作
	
	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public String getQueryValue() {
		return queryValue;
	}

	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

	public String getQueryOperate() {
		return queryOperate;
	}

	public void setQueryOperate(String queryOperate) {
		this.queryOperate = queryOperate;
	}
	
	public String getValueList() {
		return valueList;
	}

	public void setValueList(String valueList) {
		this.valueList = valueList;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public QueryObject(){}

	public List<String> getOperate() {
		return operate;
	}
	public void addOperate(String op){
		operate.add(op);
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		QueryObject qo=(QueryObject)super.clone();
		qo.operate=new ArrayList<String>();
		if(operate!=null){
			for(String op:operate){
				qo.addOperate(op);
			}
		}
		return qo;
	}
	
}
