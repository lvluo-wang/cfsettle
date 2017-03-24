package com.upg.ucars.model;

/**
 * 处理意见信息
 * 
 * @author shentuwy
 * @date 2012-7-11
 * 
 */
public class DealOpinionInfo {

	/** 任务处理人 用户ID */
	private Long	userId;
	/** 处理人名字 */
	private String	userName;
	/** 任务名称 */
	private String	taskName;
	/** 流程名称 */
	private String	processName;
	/** 是否同意 */
	private String	agree;
	/** 备注 */
	private String	remark;
	/** 业务数据 */
	private String	businessData;
	/** 业务类型 */
	private String	businessType;
	/** 审批意见 */
	private String approvalOpinion;

	public DealOpinionInfo() {

	}

	public DealOpinionInfo(Long userId, String agree, String remark) {
		this.userId = userId;
		this.agree = agree;
		this.remark = remark;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getAgree() {
		return agree;
	}

	public void setAgree(String agree) {
		this.agree = agree;
	}

	public String getApprovalOpinion() {
		return approvalOpinion;
	}

	public void setApprovalOpinion(String approvalOpinion) {
		this.approvalOpinion = approvalOpinion;
	}
	

}
