
package com.upg.ucars.basesystem.autotask.core.autotasklog;

import java.util.Date;

public class LogSearchBean {
	private Long taskId;
	private String taskType;
	private String isMainTask;//是否主任务
	private Date startDate;
	private Date endDate;
	
	
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getIsMainTask() {
		return isMainTask;
	}
	public void setIsMainTask(String isMainTask) {
		this.isMainTask = isMainTask;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	

}
