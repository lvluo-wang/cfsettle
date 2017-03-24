package com.upg.ucars.mapping.framework.bpm;

import java.math.BigDecimal;


/**
 * HumnTask 
 * 
 * @author shentuwy
 * @date 2012-7-4
 *
 */
public class HumnTask implements java.io.Serializable {

	private static final long	serialVersionUID	= -1138088490491950982L;

	/** 任务类型-普通 */
	public final static String	TASK_TYPE_COMMON	= "1";
	/** 任务类型-撤销任务 */
	public final static String	TASK_TYPE_CANCEL	= "2";
	/** 任务类型-审批任务 */
	public final static String	TASK_TYPE_AUDIT		= "3";
	// Fields

	private Long				id;
	private String				taskName;
	private String				taskCnName;
	private Long				funcId;
	private Long				procId;
	private String				status;
	private String				taskType;										// 1普通任务，2撤销任务，3审批任务
	private BigDecimal			sortNo;
	private String 				remark;

	/** 任务处理的URL */
	private String				url;

	// Constructors

	public HumnTask(Long id, String taskName, String taskCnName, Long funcId,
			Long procId, String status) {
		this.id = id;
		this.taskName = taskName;
		this.taskCnName = taskCnName;
		this.funcId = funcId;
		this.procId = procId;
		this.status = status;
	}

	/** default constructor */
	public HumnTask() {
	}

	/** minimal constructor */
	public HumnTask(Long id) {
		this.id = id;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskCnName() {
		return this.taskCnName;
	}

	public void setTaskCnName(String taskCnName) {
		this.taskCnName = taskCnName;
	}

	public Long getProcId() {
		return this.procId;
	}

	public void setProcId(Long procId) {
		this.procId = procId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getFuncId() {
		return funcId;
	}

	public void setFuncId(Long funcId) {
		this.funcId = funcId;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public BigDecimal getSortNo() {
		return sortNo;
	}

	public void setSortNo(BigDecimal sortNo) {
		this.sortNo = sortNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}