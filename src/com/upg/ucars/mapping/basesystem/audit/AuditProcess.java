package com.upg.ucars.mapping.basesystem.audit;

import java.io.Serializable;
import java.util.Date;
/**
 * 审批过程
 * 
 * @author shentuwy
 * @date 2011-3-31 下午02:26:13
 *
 */
public class AuditProcess implements Serializable {
	
	private static final long serialVersionUID = -2006759396752985247L;
	/** 未受理 */
	public static final String STATUS_UN_ACCEPT="0";
	/** 受理中 */
	public static final String STATUS_ACCEPING="1";
	/** 受理完毕 */
	public static final String STATUS_ACCEPED="2";
	/** 同意 */
	public static final String RESULT_AGREE="1";
	/** 拒绝 */
	public static final String RESULT_REFUSED="2";
	/** 驳回 */
	public static final String RESULT_REJECT="3";
	/** 字典-受理状态 */
	public static final String CODE_ACCEPT_STATUS="B014";
	/** 字典-受理结果 */
	public static final String CODE_ACCEPT_RESULT="B015";
	
	/** 主键 */
	private Long id;
	/** 归属审批任务id */
	private Long auditTaskId;
	/** 提交岗位 */
	private Long auditProcessCommitStation;
	/** 审批提交人 */
	private Long auditProcessCommitPerson;
	/** 审批过程受理岗位 */
	private Long auditProcessExecStation;
	/** 审批过程受理人 */
	private Long auditProcessExecPerson;
	/** 审批过程状态0-未处理，1-处理中，2-处理完毕 */
	private String auditProcessStatus;
	/** 审批过程受理结果1-同意，2-拒绝,3-驳回 */
	private String auditProcessExecResult;
	/** 审批过程受理意见 */
	private String auditProcessExecRemark;
	/** 审批过程受理完成时间 */
	private Date auditProcessDoneTime;
	/** 顺序号 */
	private Long sortNo;
	/** 版本号 */
	private Long version;
	
	
	public AuditProcess(){}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAuditTaskId() {
		return auditTaskId;
	}
	public void setAuditTaskId(Long auditTaskId) {
		this.auditTaskId = auditTaskId;
	}

	public Long getAuditProcessCommitStation() {
		return auditProcessCommitStation;
	}
	public void setAuditProcessCommitStation(Long auditProcessCommitStation) {
		this.auditProcessCommitStation = auditProcessCommitStation;
	}
	public Long getAuditProcessCommitPerson() {
		return auditProcessCommitPerson;
	}
	public void setAuditProcessCommitPerson(Long auditProcessCommitPerson) {
		this.auditProcessCommitPerson = auditProcessCommitPerson;
	}
	public Long getAuditProcessExecStation() {
		return auditProcessExecStation;
	}
	public void setAuditProcessExecStation(Long auditProcessExecStation) {
		this.auditProcessExecStation = auditProcessExecStation;
	}
	public Long getAuditProcessExecPerson() {
		return auditProcessExecPerson;
	}
	public void setAuditProcessExecPerson(Long auditProcessExecPerson) {
		this.auditProcessExecPerson = auditProcessExecPerson;
	}
	public String getAuditProcessStatus() {
		return auditProcessStatus;
	}
	public void setAuditProcessStatus(String auditProcessStatus) {
		this.auditProcessStatus = auditProcessStatus;
	}
	public String getAuditProcessExecResult() {
		return auditProcessExecResult;
	}
	public void setAuditProcessExecResult(String auditProcessExecResult) {
		this.auditProcessExecResult = auditProcessExecResult;
	}
	public String getAuditProcessExecRemark() {
		return auditProcessExecRemark;
	}
	public void setAuditProcessExecRemark(String auditProcessExecRemark) {
		this.auditProcessExecRemark = auditProcessExecRemark;
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
	public Date getAuditProcessDoneTime() {
		return auditProcessDoneTime;
	}
	public void setAuditProcessDoneTime(Date auditProcessDoneTime) {
		this.auditProcessDoneTime = auditProcessDoneTime;
	}
	
}
