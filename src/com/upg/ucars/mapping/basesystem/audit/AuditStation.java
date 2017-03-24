package com.upg.ucars.mapping.basesystem.audit;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 审批岗位
 * 
 * 
 * @author shentuwy
 * @date 2011-3-31 下午01:48:09
 * 
 */
public class AuditStation implements Serializable {

	private static final long	serialVersionUID	= -2789386466732451996L;

	public static final String	TYPE				= "station";

	public static final String	AUDIT_ROUTE_SET_ERR	= "AUDIT_ROUTE_002";

	/** 主键 */
	private Long				id;

	/** 审批节点id */
	private Long				auditNodeId;

	/** 审批路线id */
	private Long				auditRouteId;

	/** 审批岗位名称 */
	private String				auditStationName;

	/** 岗位审批权限 */
	private BigDecimal			auditStationPrivilege;

	/** 创建审批岗位的机构 */
	private Long				createBrchId;

	/** 使用审批岗位的机构 */
	private Long				bindBrchId;

	/** 顺序号 */
	private Long				sortNo;

	/** 岗位描述 */
	private String				auditStationRemark;

	/** 版本号 */
	private Long				version;

	public AuditStation() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAuditNodeId() {
		return auditNodeId;
	}

	public void setAuditNodeId(Long auditNodeId) {
		this.auditNodeId = auditNodeId;
	}

	public String getAuditStationName() {
		return auditStationName;
	}

	public void setAuditStationName(String auditStationName) {
		this.auditStationName = auditStationName;
	}

	public BigDecimal getAuditStationPrivilege() {
		return auditStationPrivilege;
	}

	public void setAuditStationPrivilege(BigDecimal auditStationPrivilege) {
		this.auditStationPrivilege = auditStationPrivilege;
	}

	public Long getCreateBrchId() {
		return createBrchId;
	}

	public void setCreateBrchId(Long createBrchId) {
		this.createBrchId = createBrchId;
	}

	public Long getBindBrchId() {
		return bindBrchId;
	}

	public void setBindBrchId(Long bindBrchId) {
		this.bindBrchId = bindBrchId;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getSortNo() {
		return sortNo;
	}

	public void setSortNo(Long sortNo) {
		this.sortNo = sortNo;
	}

	public Long getAuditRouteId() {
		return auditRouteId;
	}

	public void setAuditRouteId(Long auditRouteId) {
		this.auditRouteId = auditRouteId;
	}

	public String getAuditStationRemark() {
		return auditStationRemark;
	}

	public void setAuditStationRemark(String auditStationRemark) {
		this.auditStationRemark = auditStationRemark;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
