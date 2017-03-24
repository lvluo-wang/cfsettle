package com.upg.ucars.basesystem.autotask.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.upg.ucars.mapping.basesystem.autotask.AutoTask;
import com.upg.ucars.util.DateTimeUtil;

public class AutoTaskInstance {	
	/**未启动*/
	public final static String STATUS_UNUSED = "1";
	/**运行中*/
	public final static String STATUS_RUN = "2";
	/**运行中(部分异常)*/
	public final static String STATUS_RUN_ERROR = "3";
	/**已完成(部分异常)*/
	public final static String STATUS_FINISH_ERROR = "4";
	/**异常*/
	public final static String STATUS_ERROR = "5";
	/**已完成*/
	public final static String STATUS_FINISH = "6";
	

	private Long taskId;
	private String name;
	private String status;	
	private Date startTime;
	private Date endTime;
	private Date runDate;
	private int rate;
	private String taskType;//AutoTask.Task_Type_Common 或  AutoTask.Task_Type_Member
	private String memberNo;//
	private String errMessage;
	
	private AutoTaskInstance parentInstance;//父级任务实例
	
	private String memberName;
	
	private Thread thread;
	
	
	private Map<String, AutoTaskInstance> subTaskMap = new TreeMap<String, AutoTaskInstance>();//成员行任务
	
	//private Set<String> runSubTaskSet = new HashSet<String>();
	private Set<String> errorSubTaskSet = new HashSet<String>();
	private Set<String> finishSubTaskSet = new HashSet<String>();

	public AutoTaskInstance(){};
	
	public AutoTaskInstance(Thread thread) {
		super();
		this.thread = thread;
	}	
	
	/**
	 * 获取成员行子任务
	 * @return
	 */
	public List<AutoTaskInstance> getSubTaskList(){		
		Collection<AutoTaskInstance> colls = subTaskMap.values();
		ArrayList<AutoTaskInstance> atiList = new ArrayList<AutoTaskInstance>(colls.size());
		atiList.addAll(colls);
		return atiList;
	}
	/**
	 * 获取指定行的任务实例
	 * @param memberNo
	 * @return
	 */
	public AutoTaskInstance getSubInstance(String memberNo){
		return subTaskMap.get(memberNo);
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
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置运行状态,见常量:STATUS_*
	 * @param status The status to set.
	 */
	public void setStatus(String status) {		
		if (this.isSubTask()){//通知父任务
			this.getParentInstance().notifySubTaskStatus(memberNo, status);
		}	
		this.status = status;
	}
	/**
	 * 设置简单的状态，为成员行任务单独执行时使用
	 * @param status 见AutoTaskInstance.STATUS_常量
	 */
	public void setSimpleStatus(String status) {		
		this.status = status;
	}
	/**
	 * 响应子任务的状态变化
	 *
	 * @param memberNo
	 * @param subStatus
	 */
	public synchronized void notifySubTaskStatus(String memberNo, String subStatus){
		if (STATUS_RUN.equals(subStatus)){
			this.errorSubTaskSet.remove(memberNo);
			this.finishSubTaskSet.remove(memberNo);
			return;
		}
		
		if (STATUS_ERROR.equals(subStatus)){
			this.errorSubTaskSet.add(memberNo);
			
			if (this.errorSubTaskSet.size() + this.finishSubTaskSet.size() >= this.subTaskMap.size()){//全部结束	
				this.setStatus(STATUS_FINISH_ERROR);
				if (this.finishSubTaskSet.isEmpty())
					this.setStatus(STATUS_ERROR);			
				
				this.setEndTime(DateTimeUtil.getNowDateTime());
				//写入数据库			
				AutoTaskServiceFactory.getAutoTaskLogService().saveAutoTaskLog(this);
			}else
				this.setStatus(STATUS_RUN_ERROR);
			return;
		}
		
		if (STATUS_FINISH.equals(subStatus)){
			this.finishSubTaskSet.add(memberNo);
			
			if (this.errorSubTaskSet.size() + this.finishSubTaskSet.size() >= this.subTaskMap.size()){//全部结束
				if (this.errorSubTaskSet.isEmpty())
					this.setStatus(STATUS_FINISH);
				else
					this.setStatus(STATUS_FINISH_ERROR);
				this.setEndTime(DateTimeUtil.getNowDateTime());
				//写入数据库			
				AutoTaskServiceFactory.getAutoTaskLogService().saveAutoTaskLog(this);				
				
			}else{
				//尚有运行的，不改变状态
			}
			return;			
		}		
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


	/**
	 * @return Returns the rate.
	 */
	public int getRate() {
		return rate;
	}

	/**
	 * @param rate The rate to set.
	 */
	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}
	public AutoTaskInstance getParentInstance() {
		return parentInstance;
	}
	public void setParentInstance(AutoTaskInstance parentInstance) {
		this.parentInstance = parentInstance;
	}
	/**
	 * 创建子任务实例
	 * @return
	 */
	public AutoTaskInstance createSubInstance(String memberNo, Thread thread){
		AutoTaskInstance ati = new AutoTaskInstance(thread);
		ati.setParentInstance(this);
		ati.setTaskId(this.getTaskId());
		ati.setMemberNo(memberNo);
		ati.setName(this.getName());
		ati.setRate(0);
		ati.setTaskType(AutoTask.Task_Type_Member);
		subTaskMap.put(memberNo, ati);		
		return ati;
	}
	/**
	 * 移除成员行子任务实例
	 * @param memberNo
	 */
	public void removeSubInstance(String memberNo){		
		subTaskMap.remove(memberNo);
	}
	/**
	 * 是否子任务
	 * @return
	 */
	public boolean isSubTask(){
		return this.getParentInstance()!=null;
	}

	/**
	 * @return Returns the startTimeStr.
	 */
	/*public String getStartTimeStr() {
		if (this.startTime == null)
			return "";
		return DateTimeUtil.getTime(startTime, "yyyy-MM-dd HH:mm:ss");
	}*/

	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Thread getThread() {
		return thread;
	}
	public void setThread(Thread thread) {
		this.thread = thread;
	}

}
