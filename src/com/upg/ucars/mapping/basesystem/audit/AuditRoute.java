package com.upg.ucars.mapping.basesystem.audit;

import java.io.Serializable;
/**
 * 审批路线
 * 
 * @author shentuwy
 * @date 2011-3-31 下午02:13:47
 *
 */
public class AuditRoute implements Serializable {
	private static final long serialVersionUID = 1359629782888533137L;
	
	public static final String TYPE="route";
	public static final String AUDIT_ROUTE_SET_ERR="AUDIT_ROUTE_001";
	
	/** 节点执行模式-全部执行 */
	public static final String NODE_EXEC_MODE_FULL_EXEC="2";
	/** 节点执行模式-条件执行 */
	public static final String NODE_EXEC_MODE_COND_EXEC="1";
	/** 审批模式-互斥 */
	public static final String AUDIT_MODE_MUTUALLY="1";
	/** 审批模式-兼容 */
	public static final String AUDIT_MODE_COMPATIBLE="2";
	/** ID */
	private Long id;
	/** 审批路线名称 */
	private String auditRouteName;
	public static final String AUDIT_ROUTE_NAME = "auditRouteName";
	/** 1全部执行-每个节点都执行；2条件执行-当前节点审批条件满足后，后续节点不执行 */
	private String auditNodeExecMode;
	/** 1排斥-业务提交人不能审批自己的业务，2兼容-业务提交人可以审批自己的业务 */
	private String auditMode;
	/** 审批路线描述 */
	private String auditRouteRemark;
	/** 接入编号 */
	private String miNo;
	public static final String MI_NO = "miNo";
	/** 版本号 */
	private Long version;
	
	public AuditRoute(){}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAuditRouteName() {
		return auditRouteName;
	}
	public void setAuditRouteName(String auditRouteName) {
		this.auditRouteName = auditRouteName;
	}
	public String getAuditNodeExecMode() {
		return auditNodeExecMode;
	}
	public void setAuditNodeExecMode(String auditNodeExecMode) {
		this.auditNodeExecMode = auditNodeExecMode;
	}
	public String getMiNo() {
		return miNo;
	}
	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}

	public String getAuditMode() {
		return auditMode;
	}
	public void setAuditMode(String auditMode) {
		this.auditMode = auditMode;
	}
	public String getAuditRouteRemark() {
		return auditRouteRemark;
	}
	public void setAuditRouteRemark(String auditRouteRemark) {
		this.auditRouteRemark = auditRouteRemark;
	}
	
}
