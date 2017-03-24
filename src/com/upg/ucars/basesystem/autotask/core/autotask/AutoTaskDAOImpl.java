package com.upg.ucars.basesystem.autotask.core.autotask;

import java.util.List;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.autotask.AutoTask;

public class AutoTaskDAOImpl extends BaseDAO<AutoTask, Long> implements IAutoTaskDAO{
	
	public List<AutoTask> getCloseAutoTasks() throws DAOException {
		String sql="from AutoTask	autotask where autotask.status=? ";
		return this.getHibernateTemplate().find(sql, AutoTask.STATUS_CLOSE);
	}

	public List<AutoTask> getOpenAutoTasks() throws DAOException {
		String sql="from AutoTask	autotask where autotask.status=? ";
		return this.getHibernateTemplate().find(sql, AutoTask.STATUS_OPEN);
	}

	@Override
	public Class<AutoTask> getEntityClass() {		
		return AutoTask.class;
	}
}
