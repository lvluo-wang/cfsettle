package com.upg.ucars.basesystem.autotask.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.upg.ucars.mapping.basesystem.autotask.AutoTask;

/**
 * 日终自动任务监控器 
 *
 * @author shentuwy
 */
public class AutoTaskCurrentMonitor {
	private final static AutoTaskCurrentMonitor monitor = new AutoTaskCurrentMonitor();
	
	private Map<Long, AutoTaskInstance> taskMap = new HashMap<Long, AutoTaskInstance>();//key=taskId.toString(), value=AutoTaskInstance;
	
	private Comparator<AutoTaskInstance> instanceComparator = new Comparator<AutoTaskInstance>(){

		public int compare(AutoTaskInstance a1, AutoTaskInstance a2) {
			
			return a1.getName().compareTo(a2.getName());
			
		}};
	/**
	 * 单例构造器
	 */
	private AutoTaskCurrentMonitor(){
		//全部注册为未启动
		List<AutoTask> list = AutoTaskServiceFactory.getAutoTaskService().getAutoTasks();
		Iterator<AutoTask> taskIt = list.iterator();
		while(taskIt.hasNext()){
			AutoTask autoTask = taskIt.next();
			addNewTask(autoTask);
		}
		
	}
	/**
	 * 添加新任务监控 
	 *
	 * @param autoTask
	 */
	public void addNewTask(AutoTask autoTask){
		AutoTaskInstance ati = new AutoTaskInstance();
		ati.setStatus(AutoTaskInstance.STATUS_UNUSED);	
		ati.setTaskType(autoTask.getTaskType());
		ati.setName(autoTask.getName());
		ati.setTaskId(autoTask.getId());
		//
		this.addAutoTaskInstance(ati);
	}
	/**
	 * 实例
	 * @return
	 */
	public static AutoTaskCurrentMonitor getInstance(){
		return monitor;
	}
	/**
	 * 增加任务实例
	 * @param ati
	 */
	public void addAutoTaskInstance(AutoTaskInstance ati){				
		taskMap.put(ati.getTaskId(), ati);		
	}
	/**
	 * 删除任务实例
	 * @param taskId
	 */
	public void delAutoTaskInstance(Long taskId){		
		taskMap.remove(taskId);
	}
	
	public AutoTaskInstance getAutoTaskInstance(Long taskId){
		return (AutoTaskInstance)taskMap.get(taskId);
	}
	/**
	 * 获取任务列表
	 * @return List < AutoTaskInstance >
	 */
	public List<AutoTaskInstance> getAutoTaskInstances(){
		
		List<AutoTaskInstance> list = new ArrayList<AutoTaskInstance>();		
		list.addAll(taskMap.values());		
		Collections.sort(list, instanceComparator);
		return list;
	}
	/**
	 * 清空
	 */
	public void clear(){
		this.taskMap.clear();
	}
	/**
	 * 还原所有任务状态
	 */
	public void reset(){
		Iterator<AutoTaskInstance> taskIt = taskMap.values().iterator();
		while (taskIt.hasNext()){
			AutoTaskInstance ati = (AutoTaskInstance)taskIt.next();
			this.reset(ati);
		}
	}
	/**
	 * 还原单个任务状态 
	 *
	 * @param ati
	 */
	public void reset(AutoTaskInstance ati){
		ati.setStatus(AutoTaskInstance.STATUS_UNUSED);
		ati.setRunDate(null);
		ati.setEndTime(null);
		ati.setRate(0);			
		ati.setStartTime(null);
		ati.setThread(null);
	}
	

}
