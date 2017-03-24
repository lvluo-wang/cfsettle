package com.upg.ucars.mapping.framework.bpm;

import java.util.Date;

/**
 * 任务追踪信息实体类
 * 
 * @author shentuwy
 * 
 */
public class TaskTraceInfo implements java.io.Serializable {

	private static final long	serialVersionUID	= -2238075549978574888L;

	private Long				id;
	private Long				taskId;
	/** 任务名称 */
	private String				taskName;
	/** 任务处理人 */
	private Long				dealUserId;
	/** 处理人名字 */
	private String				dealUserName;
	/** 流程名称 */
	private String				processName;
	private String				isAgree;
	private String				remark;
	private Long				entityId;
	/** 领用时间 */
	private Date				holdTime;
	/** 处理时间 */
	private Date				dealTime;
	/** 是否审批任务 */
	private String				isAudit;
	/** 审批路线的岗位ID */
	private Long				stationId;
	/** 业务扩展数据 */
	private String				businessData;
	/** 业务扩展类型 */
	private String				businessType;
	/** 业务格式化数据 */
	private transient String	businessFormatMessage;
	/** 审批意见 */
	private String				approvalOpinion;
	/** 委托ID */
	private Long				delegateId;
	/** 委托人 */
	private String				delegateUser;

	public TaskTraceInfo() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public Date getHoldTime() {
		return holdTime;
	}

	public void setHoldTime(Date holdTime) {
		this.holdTime = holdTime;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public String getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}

	public Long getDealUserId() {
		return dealUserId;
	}

	public void setDealUserId(Long dealUserId) {
		this.dealUserId = dealUserId;
	}

	public String getDealUserName() {
		return dealUserName;
	}

	public void setDealUserName(String dealUserName) {
		this.dealUserName = dealUserName;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getBusinessData() {
		return businessData;
	}

	public void setBusinessData(String businessData) {
		this.businessData = businessData;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessFormatMessage() {
		return businessFormatMessage;
	}

	public void setBusinessFormatMessage(String businessFormatMessage) {
		this.businessFormatMessage = businessFormatMessage;
	}

	public String getApprovalOpinion() {
		return approvalOpinion;
	}

	public void setApprovalOpinion(String approvalOpinion) {
		this.approvalOpinion = approvalOpinion;
	}

	public String getDelegateUser() {
		return delegateUser;
	}

	public void setDelegateUser(String delegateUser) {
		this.delegateUser = delegateUser;
	}

	public Long getDelegateId() {
		return delegateId;
	}

	public void setDelegateId(Long delegateId) {
		this.delegateId = delegateId;
	}
	
}
