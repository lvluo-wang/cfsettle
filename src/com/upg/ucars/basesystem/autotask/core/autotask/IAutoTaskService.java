package com.upg.ucars.basesystem.autotask.core.autotask;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.autotask.AutoTask;
/**
 *  定时任务实体信息服务
 *  
 * @author shentuwy
 */
public interface IAutoTaskService extends IBaseService{
	/**
	 * 增加AutoTask
	 * @param autoTask
	 */
	public void addAutoTask(AutoTask autoTask);

	/**
	 * 更新AutoTask
	 * @param autoTask
	 */
	public void updateAutoTask(AutoTask autoTask);
	/**
	 * 通过ID获取AutoTask
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	public AutoTask getAutoTask(Long id) throws ServiceException;
	/**
	 * 获取所有AutoTask
	 * @return
	 * @throws ServiceException
	 */
	public List<AutoTask> getAutoTasks() throws ServiceException;
	/**
	 * 自动任务查询
	 * @param qc 只用于增加条件与排序
	 * @param page
	 * @return
	 */
	public List<AutoTask> queryAutoTasks(QueryCondition qc, Page page);
	/**
	 * 获取打开的任务
	 * @return
	 * @throws ServiceException
	 */
	public List<AutoTask> getOpenAutoTasks() throws ServiceException;
	/**
	 * 获取关闭的任务
	 * @return
	 * @throws ServiceException
	 */
	public List<AutoTask> getCloseAutoTasks() throws ServiceException;
	
	/**
	 * 获取依赖的任务
	 * @return
	 * @throws ServiceException
	 */
	public List<AutoTask> getDependAutoTasks(Long taskId) throws ServiceException;
	/**
	 * 获取未依赖的任务
	 * @return
	 * @throws ServiceException
	 */
	public List<AutoTask> getUnDependAutoTasks(Long taskId) throws ServiceException;
	
	/**
	 * 删除定时任务
	 * @param id
	 * @throws ServiceException
	 */
	public void deleteAutoTask(Long taskId) throws ServiceException;
	/**
	 * 关闭日终任务
	 * @param autoTask
	 * @param id
	 * @throws ServiceException
	 */
	public void closeAutoTask(Long taskId) throws ServiceException;
	/**
	 * 启用日终任务
	 * @param autoTask
	 * @param id
	 * @throws ServiceException
	 */
	public void openAutoTask(Long taskId) throws ServiceException;
	
}
