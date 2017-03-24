package com.upg.ucars.mapping.basesystem.autotask;

import java.util.Date;


public class AutoTaskLog {
	private Long id;
	private Long taskId;
	private String name;
	private String taskType;
	private Date startTime;
	private Date endTime;
	private Date runDate;
	private String status;
	private String errMessage;	
	private String memberNo;//
	private String memberName;
	/**
	 * @return Returns the endTime.
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime The endTime to set.
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return Returns the errMessage.
	 */
	public String getErrMessage() {
		return errMessage;
	}
	/**
	 * @param errMessage The errMessage to set.
	 */
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	/**
	 * @return Returns the id.
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return Returns the runDate.
	 */
	public Date getRunDate() {
		return runDate;
	}
	/**
	 * @param runDate The runDate to set.
	 */
	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}
	/**
	 * @return Returns the startTime.
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime The startTime to set.
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return Returns the taskId.
	 */
	public Long getTaskId() {
		return taskId;
	}
	/**
	 * @param taskId The taskId to set.
	 */
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	
	
	
}
