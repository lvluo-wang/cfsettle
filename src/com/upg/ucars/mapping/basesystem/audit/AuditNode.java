package com.upg.ucars.mapping.basesystem.audit;

import java.io.Serializable;
/**
 * 审批节点
 * 
 * @author shentuwy
 * @date 2011-3-31 下午02:29:58
 *
 */
public class AuditNode implements Serializable {
	
	private static final long serialVersionUID = 5148026169030385253L;
	public static final String TYPE="node";
	public static final String AUDIT_ROUTE_SET_ERR="AUDIT_ROUTE_002";
	public static final String IS_PRIVILEGE_CTRL="1";
	public static final String NOT_PRIVILEGE_CTRL="2";
	
	/** 主键 */
	private Long id;
	/** 审批路线id */
	private Long auditRouteId;
	/** 审批节点名称 */
	private String auditNodeName;
	/** 是否启用岗位权限控制，1是-启用权限控制则权限满足后后续岗位不用执行；2否-同节点下的岗位都要执行 */
	private String isPrivilegeCtrl;
	/** 顺序号 */
	private Long sortNo;
	/** 版本号 */
	private Long version;
	
	public AuditNode(){}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAuditRouteId() {
		return auditRouteId;
	}
	public void setAuditRouteId(Long auditRouteId) {
		this.auditRouteId = auditRouteId;
	}
	public String getAuditNodeName() {
		return auditNodeName;
	}
	public void setAuditNodeName(String auditNodeName) {
		this.auditNodeName = auditNodeName;
	}
	public Long getSortNo() {
		return sortNo;
	}
	public void setSortNo(Long sortNo) {
		this.sortNo = sortNo;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public String getIsPrivilegeCtrl() {
		return isPrivilegeCtrl;
	}
	public void setIsPrivilegeCtrl(String isPrivilegeCtrl) {
		this.isPrivilegeCtrl = isPrivilegeCtrl;
	}

}
