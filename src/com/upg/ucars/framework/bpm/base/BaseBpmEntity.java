package com.upg.ucars.framework.bpm.base;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.upg.ucars.framework.base.BaseEntity;

/**
 * 供流程实体继承
 * 
 * @author shentuwy
 * @date 2012-7-10
 * 
 */
public abstract class BaseBpmEntity extends BaseEntity implements
		java.io.Serializable {
	private static final long	serialVersionUID	= 1L;
	/** 流程名 */
	private String				processName;
	/** 流程中文名 */
	private String				processCnName;
	private Long				taskId;
	private String				taskName;
	private String				taskCnName;
	private Long				tokenId;
	private Long				holdUserId;				// 任务领用人userId
	
	private Long 				taskActorId;
	private String				taskActorName;
	private Date				taskEnd;
	
	private Map<String,Object>	extInfo = new HashMap<String,Object>();

	/**
	 * 获取工作流任务实例ID
	 * 
	 * @return
	 */
	public Long getTaskId() {
		return taskId;
	}

	/**
	 * 设置工作流任务实例ID
	 * 
	 * @param taskId
	 *            任务实例ID
	 */
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * 获取工作流中令牌ID
	 * 
	 * @return
	 */
	public Long getTokenId() {
		return tokenId;
	}

	/**
	 * 设置工作流中令牌ID
	 * 
	 * @param tokenId
	 *            令牌ID
	 */
	public void setTokenId(Long tokenId) {
		this.tokenId = tokenId;
	}

	public Long getHoldUserId() {
		return holdUserId;
	}

	public void setHoldUserId(Long holdUserId) {
		this.holdUserId = holdUserId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessCnName() {
		return processCnName;
	}

	public void setProcessCnName(String processCnName) {
		this.processCnName = processCnName;
	}

	public String getTaskCnName() {
		return taskCnName;
	}

	public void setTaskCnName(String taskCnName) {
		this.taskCnName = taskCnName;
	}

	/**
	 * 任务是否被领用
	 * 
	 * @return
	 */
	public boolean isTaskHolded() {
		return this.holdUserId != null;
	}

	public Long getTaskActorId() {
		return taskActorId;
	}

	public void setTaskActorId(Long taskActorId) {
		this.taskActorId = taskActorId;
	}

	public String getTaskActorName() {
		return taskActorName;
	}

	public void setTaskActorName(String taskActorName) {
		this.taskActorName = taskActorName;
	}

	public Date getTaskEnd() {
		return taskEnd;
	}

	public void setTaskEnd(Date taskEnd) {
		this.taskEnd = taskEnd;
	}

	public Map<String, Object> getExtInfo() {
		return extInfo;
	}

	public void setExtInfo(Map<String, Object> extInfo) {
		this.extInfo = extInfo;
	}
	
}
