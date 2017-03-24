package com.upg.ucars.basesystem.autotask.core;

import java.util.List;

import com.upg.ucars.framework.exception.ServiceException;
/**
 * 定时任务操作服务
 *
 * @author shentuwy
 */
public interface IAutoTaskCurrentService {
	
	public List<AutoTaskInstance> getAllAutoTasks();	
	/**
	 * 启动
	 * @param taskId
	 */
	public void runTask(Long taskId) throws ServiceException;	
	
	/**
	 * 启动成员行子任务
	 * @param taskId
	 * @param memberBrchId
	 */
	public void runSubTask(Long taskId, String memberNo) throws ServiceException;
	
	/**
	 * 停止
	 * @param taskId
	 */
	public void stopTask(Long taskId) throws ServiceException;
	/**
	 * 停止成员行子任务
	 * @param taskId
	 * @param memberBrchId
	 */
	public void stopSubTask(Long taskId, String memberNo) throws ServiceException;
	
	/**
	 * 置为初始状态 
	 *
	 * @param taskId
	 * @throws ServiceException
	 */
	public void setInitStatus(Long taskId) throws ServiceException;
	/**
	 * 置为完成状态 
	 *
	 * @param taskId
	 * @throws ServiceException
	 */
	public void setFinishStatus(Long taskId) throws ServiceException;
		
	/**
	 * 
	 * @param taskId
	 * @return
	 */
	public boolean isTodayTask(Long taskId) throws ServiceException;	
	

}
	