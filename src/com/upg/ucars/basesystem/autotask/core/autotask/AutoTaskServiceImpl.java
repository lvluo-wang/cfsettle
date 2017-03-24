package com.upg.ucars.basesystem.autotask.core.autotask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.quartz.CronExpression;

import com.upg.ucars.basesystem.autotask.core.AbstractAutoTask;
import com.upg.ucars.basesystem.autotask.core.AutoTaskCurrentMonitor;
import com.upg.ucars.basesystem.autotask.core.ScheduleHelper;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.autotask.AutoTask;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;

public class AutoTaskServiceImpl extends BaseService implements IAutoTaskService{
	private IAutoTaskDAO autoTaskDAO;
	
	
	private void validateAutoTask(AutoTask autoTask){
		if (autoTask != null) {
			if (!CronExpression.isValidExpression(autoTask.getCronExpr())) {// 不通过
				ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.COMMON_ERROR_CODE, "表达式不符");
			}
			Class<?> clazz = null;
			try {
				clazz = Class.forName(autoTask.getClassName());
			} catch (Exception ex) {
				ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.COMMON_ERROR_CODE, "类不存在，或路径不符");
			}
			
			if (!AbstractAutoTask.class.isAssignableFrom(clazz)) {
				ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.COMMON_ERROR_CODE, "不是AbstractAutoTask的子类");
			}
			//TODO 通用和接入点两种类型验证
		}
	}
	
	public void addAutoTask(AutoTask autoTask){
		validateAutoTask(autoTask);
		autoTask.setStatus(AutoTask.STATUS_CLOSE);
		this.getAutoTaskDAO().save(autoTask);	
		AutoTaskCurrentMonitor.getInstance().addNewTask(autoTask);
	}


	public AutoTask getAutoTask(Long id) throws ServiceException {
		return this.getAutoTaskDAO().get(id);
		
	}
	
	public void delAutoTask(AutoTask autoTask) throws ServiceException {
		try {
			autoTaskDAO.delete(autoTask);
		} catch (DAOException e) {
			throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.AUTO_TASK_001, "", e);
		}
	}


	public List<AutoTask> getAutoTasks() throws ServiceException {
		List<AutoTask> list= autoTaskDAO.queryEntity(new ArrayList<ConditionBean>(0), null);
		return list;
	}

	public void updateAutoTask(AutoTask autoTask) {
		validateAutoTask(autoTask);
		this.autoTaskDAO.saveOrUpdate(autoTask);
	}


	public void closeAutoTask(Long taskId) throws ServiceException {
		AutoTask autoTask=this.getAutoTask(taskId);
		ScheduleHelper.deleteJob(autoTask);
		
		autoTask.setStatus(AutoTask.STATUS_CLOSE);
		this.updateAutoTask(autoTask);		
	}

	public void openAutoTask(Long taskId) throws ServiceException {
		AutoTask autoTask=this.getAutoTask(taskId);		
		autoTask.setStatus(AutoTask.STATUS_OPEN);
		ScheduleHelper.addJob(autoTask);
		this.updateAutoTask(autoTask);
	}

	public List getDependAutoTasks(Long taskId) throws ServiceException {
		List<Long> dependIdList = this.getAutoTaskDAO().get(taskId).getDependTaskIdList();
		
		if (dependIdList.isEmpty()){
			return new ArrayList(0);
		}
		
		List dependTasks = new ArrayList();
		List<AutoTask> allTasks = this.getAutoTasks();
		Iterator<AutoTask> allIt = allTasks.iterator();
		while(allIt.hasNext()){
			AutoTask autoTask = allIt.next();
			if (dependIdList.contains(autoTask.getId())){
				dependTasks.add(autoTask);
			}
		}
		
		return dependTasks;
	}

	public List getUnDependAutoTasks(Long taskId) throws ServiceException {
		String dependStr = this.getAutoTaskDAO().get(taskId).getDependTasks();
		List allTasks = this.getAutoTaskDAO().queryEntity(new ArrayList<ConditionBean>(0), null);
		if (StringUtils.isEmpty(dependStr)){
			return allTasks;
		}
		
		String[] depends = dependStr.split(",");
		Set set = new HashSet(depends.length);
		for (int i = 0; i < depends.length; i++) {
			set.add(depends[i]);
		}
		
		List unDependTasks = new ArrayList();
		
		Iterator allIt = (Iterator)allTasks.iterator();
		while(allIt.hasNext()){
			AutoTask autoTask = (AutoTask)allIt.next();
			if (!set.contains(autoTask.getId().toString())){
				unDependTasks.add(autoTask);
			}
		}
		
		return unDependTasks;
	}

	public List<AutoTask> getCloseAutoTasks() throws ServiceException {
		return this.autoTaskDAO.getCloseAutoTasks();
		
	}

	public List<AutoTask> getOpenAutoTasks() throws ServiceException {	
		return this.autoTaskDAO.getOpenAutoTasks();
	}
	
	
	public IAutoTaskDAO getAutoTaskDAO() {
		return autoTaskDAO;
	}

	public void setAutoTaskDAO(IAutoTaskDAO autoTaskDAO) {
		this.autoTaskDAO = autoTaskDAO;
	}


	public void deleteAutoTask(Long taskId) throws ServiceException {
		//先关闭任务再删除
		this.closeAutoTask(taskId);
		
		this.autoTaskDAO.delete(taskId);
		
	}


	public List<AutoTask> queryAutoTasks(QueryCondition qc, Page page) {
		if (qc == null){
			qc = new QueryCondition();
			qc.addOrder(new OrderBean("name"));
		}
		return this.getAutoTaskDAO().queryEntity(qc.getConditionList(), qc.getOrderList(), page);
	}

}
