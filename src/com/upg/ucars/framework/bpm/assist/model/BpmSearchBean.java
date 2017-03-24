package com.upg.ucars.framework.bpm.assist.model;

import com.upg.ucars.util.StringUtil;


/** 
 * 查询条件
 *
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */

public class BpmSearchBean {
	
	public final static String Type_Entity_Id="1"; 
	public final static String Type_Entity_No="2";
	
	private String procName;
	private String brchId;
	
	private String type=Type_Entity_Id;
	private String value;
	
	
	public String getProcName() {
		return procName;
	}

	public void setProcName(String procName) {
		this.procName = procName;
	}

	public Long getEntityId() {
		if (StringUtil.isEmpty(value))
			return null;
		return new Long(value);
	}

	public String getEntityNo() {
		return value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getBrchId() {
		return brchId;
	}

	public void setBrchId(String brchId) {
		this.brchId = brchId;
	}
	
	

}

 
