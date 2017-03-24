package com.upg.ucars.mapping.basesystem.autotask;

import java.util.ArrayList;
import java.util.List;

	
public class AutoTask {
	/**启用*/
	public final static String STATUS_OPEN = "1";
	/**关闭*/
	public final static String STATUS_CLOSE = "2";
	
	/**任务类型-通用任务*/
	public final static String Task_Type_Common = "1";
	/**任务类型-接入点任务*/
	public final static String Task_Type_Member = "2";

	
	private Long id;
	private String name;
	private String className;
	private String taskType;//任务类型Task_Type_Common 或  Task_Type_Member
	private String para;//任务参数
	private String status;
	private String cronExpr;
	private String nextTask;//下一任务
	private String dependTasks;//依赖任务，以逗号分隔
	private Long dependOutTime;//超时时间		
	private String memberNos;//成员行机构号，以逗号分隔	

	
	/**
	 * @return Returns the className.
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param className The className to set.
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * @return Returns the cronExpr.
	 */
	public String getCronExpr() {
		return cronExpr;
	}
	/**
	 * @param cronExpr The cronExpr to set.
	 */
	public void setCronExpr(String cronExpr) {
		this.cronExpr = cronExpr;
	}
	/**
	 * @return Returns the dependTasks.
	 */
	public String getDependTasks() {
		return dependTasks;
	}
	/**
	 * @param dependTasks The dependTasks to set.
	 */
	public void setDependTasks(String dependTasks) {
		if (dependTasks != null)
			dependTasks = dependTasks.replaceAll(" ", "");
		this.dependTasks = dependTasks;
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
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
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
	
	public String getNextTask() {
		return nextTask;
	}
	public void setNextTask(String nextTask) {
		this.nextTask = nextTask;
	}
	
	/**
	 * @return Returns the para.
	 */
	public String getPara() {
		return para;
	}
	/**
	 * @param para The para to set.
	 */
	public void setPara(String para) {
		this.para = para;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public Long getDependOutTime() {
		return dependOutTime;
	}
	public void setDependOutTime(Long dependOutTime) {
		this.dependOutTime = dependOutTime;
	}
	public String getMemberNos() {
		return memberNos;
	}
	public void setMemberNos(String memberNos) {
		if (memberNos != null)
			memberNos = memberNos.replaceAll(" ", "");
			
		this.memberNos = memberNos;
	}
	
	public List<Long> getDependTaskIdList() {
		if (this.dependTasks==null || this.dependTasks.length()<1){
			return new ArrayList<Long>(0);
		}
		
		ArrayList<Long> idList = new ArrayList<Long>();
		String[] idStrs = this.dependTasks.split(","); 
		for (String ids : idStrs) {
			idList.add(Long.valueOf(ids));
		}
		return idList;
	}
	public List<String> getMemberNoList() {
		if (this.memberNos==null || this.memberNos.length()<1)
			return new ArrayList<String>(0);
		
		ArrayList<String> idList = new ArrayList<String>();
		String[] idStrs = this.memberNos.split(",");
		for (String ids : idStrs) {
			idList.add(ids);
		}
		return idList;
	}
	
	
}
