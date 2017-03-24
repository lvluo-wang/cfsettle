package com.upg.ucars.framework.bpm.assign.dao;

import org.jbpm.taskmgmt.exe.TaskInstance;

import com.upg.ucars.framework.exception.DAOException;

public interface ITaskInstanceDao {	
	/**
	 * 修改任务的领取人
	 *
	 * @param tid 任务实例ID
	 * @param userId 用户ID
	 * @throws DAOException
	 */
	public void updateTaskHoldActor(Long tid, Long userId) throws DAOException;
	
	/**
	 * 获取任务的领用人
	 * @param tid
	 * @return userId
	 */
	public Long getTaskHoldActor(Long tid);
	
	/**
	 * 获取任务
	 * 
	 * @param tid
	 * @return
	 */
	public TaskInstance getTaskInstance(Long tid);

}
