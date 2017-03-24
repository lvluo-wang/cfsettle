package com.upg.ucars.framework.bpm.assist.model;

/** 
 * 流程定义
 *
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */
public class ProcDefDTO {
	private String procName;
	private String procDesc;
	private String clzName;
	private String idName;//主键字段号
	private String noName;//编号字段名
	
	/**
	 * Creates a new instance of ProcDefDTO.
	 *
	 * @param procName
	 * @param procDesc
	 * @param clzName
	 * @param idName
	 */
	
	public ProcDefDTO(String procName, String procDesc, String clzName,
			String idName, String noName) {
		super();
		this.procName = procName;
		this.procDesc = procDesc;
		this.clzName = clzName;
		this.idName = idName;
		this.noName = noName;
	}
	public String getProcName() {
		return procName;
	}
	public void setProcName(String procName) {
		this.procName = procName;
	}
	public String getProcDesc() {
		return procDesc;
	}
	public void setProcDesc(String procDesc) {
		this.procDesc = procDesc;
	}
	public String getClzName() {
		return clzName;
	}
	public void setClzName(String clzName) {
		this.clzName = clzName;
	}
	public String getIdName() {
		return idName;
	}
	public void setIdName(String idName) {
		this.idName = idName;
	}
	public String getNoName() {
		return noName;
	}
	public void setNoName(String noName) {
		this.noName = noName;
	}
	
	
	

}

 