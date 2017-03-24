package com.upg.ucars.bpm.core;

import java.util.Date;
import java.util.List;

/**
 * 任务查询实体
 * 
 * @author shentuwy
 * 
 */
public class TaskSearchBean {
	
	/** 已领用 */
	public static final String TASK_HOLD_DONE = "1";
	/** 待领用 */
	public static final String TASK_HOLD_TODO = "2";
	
	/** 客户名称*/
	private String			col1;
	/** 项目编号*/
	private String 			col9;
	/** 流程类型 */
	private List<String>	processTypes;
	/** 状态类型 */
	private String			statusType;
	/** 实体类型列表 */
	private List<String>	entityTypeList;
	/** 实体ID */
	private Long 			entityId;
	/** 开始时间 */
	private Date			fromDate;
	/** 结束时间 */
	private Date			toDate;
	/** 任务处理开始时间 */
	private Date			taskEndFrom;
	/** 任务处理结束时间 */
	private Date			taskEndTo;
	/** 事业线 */
	private String			col10;
	/** 接入点 */
	private String			miNo;
	/** 任务名称描述 */
	private String			taskDescription;
	/** 是否需要加入接入点条件 */
	private boolean			miNoCondition = true;
	/** 任务领用条件 */
	private String 			holdCond ;
	/** 流程状态 */
	private String instanceStatus;
	
	private String			delegate;
	
	/** 提交者机构 */
	private String			brchIds;
	
	/** 角色Id*/
	private Long 			roleId;

	public String getStatusType() {
		return statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<String> getEntityTypeList() {
		return entityTypeList;
	}

	public void setEntityTypeList(List<String> entityTypeList) {
		this.entityTypeList = entityTypeList;
	}

	public List<String> getProcessTypes() {
		return processTypes;
	}

	public void setProcessTypes(List<String> processTypes) {
		this.processTypes = processTypes;
	}

	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	public Date getTaskEndFrom() {
		return taskEndFrom;
	}

	public void setTaskEndFrom(Date taskEndFrom) {
		this.taskEndFrom = taskEndFrom;
	}

	public Date getTaskEndTo() {
		return taskEndTo;
	}

	public void setTaskEndTo(Date taskEndTo) {
		this.taskEndTo = taskEndTo;
	}

	public String getCol10() {
		return col10;
	}

	public void setCol10(String col10) {
		this.col10 = col10;
	}

	public String getMiNo() {
		return miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public boolean isMiNoCondition() {
		return miNoCondition;
	}

	public void setMiNoCondition(boolean miNoCondition) {
		this.miNoCondition = miNoCondition;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getHoldCond() {
		return holdCond;
	}

	public void setHoldCond(String holdCond) {
		this.holdCond = holdCond;
	}

	public String getInstanceStatus() {
		return instanceStatus;
	}

	public void setInstanceStatus(String instanceStatus) {
		this.instanceStatus = instanceStatus;
	}

	public String getDelegate() {
		return delegate;
	}

	public void setDelegate(String delegate) {
		this.delegate = delegate;
	}

	public String getBrchIds() {
		return brchIds;
	}

	public void setBrchIds(String brchIds) {
		this.brchIds = brchIds;
	}

	public String getCol9() {
		return col9;
	}

	public void setCol9(String col9) {
		this.col9 = col9;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
}
