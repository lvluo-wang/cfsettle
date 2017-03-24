
package com.upg.ucars.basesystem.autotask.core.autotask;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.autotask.AutoTask;

public interface IAutoTaskDAO extends  IBaseDAO<AutoTask, Long>{
	
	/**
	 * 获取打开的任务
	 * @return
	 * @throws ServiceException
	 */
	public List<AutoTask> getOpenAutoTasks() throws DAOException;
	/**
	 * 获取关闭的任务
	 * @return
	 * @throws ServiceException
	 */
	public List<AutoTask> getCloseAutoTasks() throws DAOException;
	
}
