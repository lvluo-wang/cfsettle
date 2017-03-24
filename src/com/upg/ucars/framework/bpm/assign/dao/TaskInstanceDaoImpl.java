
package com.upg.ucars.framework.bpm.assign.dao;

import org.jbpm.taskmgmt.exe.TaskInstance;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.exception.DAOException;

/**
 * 任务实例持久化实现类
 * 
 * @author shentuwy
 * @date 2012-7-16
 *
 */
public class TaskInstanceDaoImpl extends BaseDAO<TaskInstance, Long> implements ITaskInstanceDao{

	public void updateTaskHoldActor(Long tid, Long userId) throws DAOException {
		TaskInstance ti = this.getTaskInstance(tid);
		if (userId==null)
			ti.setActorId(null);
		else
			ti.setActorId(userId.toString());	//从Long-->String	
		this.getHibernateTemplate().update(ti);		
	}
		
	public TaskInstance getTaskInstance(Long tid){
		TaskInstance ti = (TaskInstance)this.getHibernateTemplate().get(TaskInstance.class, tid);
		ti.getActorId();//防止懒加载
		return ti;
	}

	public Long getTaskHoldActor(Long tid) {
		String pa = this.getTaskInstance(tid).getActorId();
		if (pa==null){
			return null;
		}
		
		return Long.valueOf(pa);//从String -->Long
	}

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
