package com.upg.ucars.mapping.basesystem.audit;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 审批任务
 * 
 * @author shentuwy
 * @date 2011-3-31 下午01:47:35
 *
 */
public class AuditTask implements Serializable {
	
	private static final long serialVersionUID = -2763844400762784425L;
	/** 已撤销 */
	public static final String STATUS_REVOKED="9";
	/** 审批中 */
	public static final String STATUS_AUDITING="1";
	/** 审批通过 */
	public static final String STATUS_AUDIT_PASS="2";
	/** 审批不通过 */
	public static final String STATUS_AUDIT_FAIL="3";
	/** 字典 */
	public static final String CODE_AUDIT_STATUS="B013";
	/** 审批任务id */
	private Long id;
	/** 审批路线id */
	private Long auditRouteId;
	/** 审批状态1-审批中，2-审批通过，3-审批不通过 */
	private String auditStatus;
	/** 审批权限 */
	private BigDecimal auditPrivilege;
	/** 审批任务创建者 */
	private Long auditTaskAuthor;
	/** 审批任务创建时间 */
	private Date auditTaskCreateTime;
	/** 审批任务结束时间 */
	private Date auditTaskDoneTime;
	/** 实体名称 */
	private String auditEntityName;
	/** 实体id */
	private Long auditEntityId;
	/** 实体服务 */
	private String auditEntityService;
	/** 审批描述信息 */
	private String auditRemark;
	/** 审批产品编号 */
	private String prodNo;
	/** 机构id */
	private Long brchId;
	/** 产品id */
	private Long prodId;
	/** 版本号 */
	private Long version;
	
	
	public AuditTask(){}
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
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	public BigDecimal getAuditPrivilege() {
		return auditPrivilege;
	}
	public void setAuditPrivilege(BigDecimal auditPrivilege) {
		this.auditPrivilege = auditPrivilege;
	}
	public Long getAuditTaskAuthor() {
		return auditTaskAuthor;
	}
	public void setAuditTaskAuthor(Long auditTaskAuthor) {
		this.auditTaskAuthor = auditTaskAuthor;
	}
	public Date getAuditTaskCreateTime() {
		return auditTaskCreateTime;
	}
	public void setAuditTaskCreateTime(Date auditTaskCreateTime) {
		this.auditTaskCreateTime = auditTaskCreateTime;
	}
	public String getAuditEntityName() {
		return auditEntityName;
	}
	public void setAuditEntityName(String auditEntityName) {
		this.auditEntityName = auditEntityName;
	}
	public Long getAuditEntityId() {
		return auditEntityId;
	}
	public void setAuditEntityId(Long auditEntityId) {
		this.auditEntityId = auditEntityId;
	}
	public String getAuditEntityService() {
		return auditEntityService;
	}
	public void setAuditEntityService(String auditEntityService) {
		this.auditEntityService = auditEntityService;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	public Date getAuditTaskDoneTime() {
		return auditTaskDoneTime;
	}
	public void setAuditTaskDoneTime(Date auditTaskDoneTime) {
		this.auditTaskDoneTime = auditTaskDoneTime;
	}
	public Long getBrchId() {
		return brchId;
	}
	public void setBrchId(Long brchId) {
		this.brchId = brchId;
	}
	public Long getProdId() {
		return prodId;
	}
	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}
	public String getProdNo() {
		return prodNo;
	}
	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}
	public String getAuditRemark() {
		return auditRemark;
	}
	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	
}
