package com.upg.ucars.basesystem.autotask.core;

import java.text.ParseException;
import java.util.List;

import org.quartz.CronTrigger;

import com.upg.ucars.basesystem.autotask.core.autotask.IAutoTaskService;
import com.upg.ucars.basesystem.busidate.util.BusiDateUtil;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.autotask.AutoTask;
import com.upg.ucars.util.DateTimeUtil;
/**
 * 定时任务操作服务
 *
 * @author shentuwy
 */
public class AutoTaskCurrentService implements IAutoTaskCurrentService {
	
	private IAutoTaskService autoTaskService;

	public List<AutoTaskInstance> getAllAutoTasks() {
		List<AutoTaskInstance> list = AutoTaskCurrentMonitor.getInstance().getAutoTaskInstances();
		return list;
	}

	public void runTask(Long taskId)throws ServiceException {
		AutoTask autoTask = this.autoTaskService.getAutoTask(taskId);
		ScheduleHelper.immediateJob(autoTask);
	}
	
	public void runSubTask(Long taskId, String memberNo)throws ServiceException {
		AutoTask autoTask = this.autoTaskService.getAutoTask(taskId);
		ScheduleHelper.immediateMemberJob(autoTask, memberNo);		
	}
	
	public void stopTask(Long taskId)throws ServiceException {
		try {
			AutoTask autoTask = this.autoTaskService.getAutoTask(taskId);
			ScheduleHelper.stopJob(autoTask);
			AutoTaskInstance ati = AutoTaskCurrentMonitor.getInstance().getAutoTaskInstance(taskId);
			if (AutoTask.Task_Type_Common.equals(autoTask.getTaskType())){//通用任务				
				ati.getThread().stop();					
			}else{//接入点任务
				List<AutoTaskInstance> atiList = ati.getSubTaskList();
				ati.setErrMessage("任务被强制终止");
				for (AutoTaskInstance autoTaskInstance : atiList) {
					if (AutoTaskInstance.STATUS_RUN.equals(autoTaskInstance.getStatus())){
						autoTaskInstance.getThread().stop();
					}					
				}
			}
			 
		} catch (Throwable e) {
			//throw ExceptionManager.getException(ServiceException.class, "", e);
		}
		
	}
	
	public void stopSubTask(Long taskId, String memberNo) throws ServiceException{
		try {
			AutoTask autoTask = this.autoTaskService.getAutoTask(taskId);
			ScheduleHelper.stopMemberJob(autoTask, memberNo);
			
			AutoTaskInstance pti = AutoTaskCurrentMonitor.getInstance().getAutoTaskInstance(taskId);
			AutoTaskInstance ati = pti.getSubInstance(memberNo);
			ati.getThread().stop();			
			
		} catch (Exception e) {
			throw ExceptionManager.getException(ServiceException.class, "", e);
		}
		
	}

	/**
	 * @return Returns the autoTaskService.
	 */
	public IAutoTaskService getAutoTaskService() {
		return autoTaskService;
	}

	/**
	 * @param autoTaskService The autoTaskService to set.
	 */
	public void setAutoTaskService(IAutoTaskService autoTaskService) {
		this.autoTaskService = autoTaskService;
	}

	public boolean isTodayTask(Long taskId) {
		String cronExpr = autoTaskService.getAutoTask(taskId).getCronExpr();
		CronTrigger triger=new CronTrigger();
		try {
			triger.setCronExpression(cronExpr);
			int i = DateTimeUtil.getDaysBetween(triger.getFireTimeAfter(BusiDateUtil.getCurBusiDate()), BusiDateUtil.getCurBusiDate());
			if (i==0)
				return true;			
		} catch (ParseException e) {
			return false;
		}
		
		return false;
	}
	
	
	public void triggerNextTask(AutoTask thisTask) throws ServiceException {
		String nt = thisTask.getNextTask();
		if (nt!=null && !"".equals(nt)){
			IAutoTaskService autoTaskService = AutoTaskServiceFactory.getAutoTaskService();
			AutoTask nextTask = autoTaskService.getAutoTask(Long.valueOf(nt));
			ScheduleHelper.immediateJob(nextTask);			
		}
		
	}

	public void setInitStatus(Long taskId) throws ServiceException {
		AutoTaskInstance ati = AutoTaskCurrentMonitor.getInstance().getAutoTaskInstance(taskId);
		ati.setStatus(AutoTaskInstance.STATUS_UNUSED);
		ati.setStartTime(null);
		ati.setEndTime(null);
		ati.setRunDate(null);
		
		if (AutoTask.Task_Type_Member.equals(ati.getTaskType())){//设置接入点子任务
			 List<AutoTaskInstance> atiList = ati.getSubTaskList();
			 for (AutoTaskInstance sati : atiList) {
				 sati.setStatus(AutoTaskInstance.STATUS_UNUSED);
				 sati.setStartTime(null);
				 sati.setEndTime(null);
				 sati.setRunDate(null);
			}
		}
		
	}

	public void setFinishStatus(Long taskId) throws ServiceException {
		AutoTaskInstance ati = AutoTaskCurrentMonitor.getInstance().getAutoTaskInstance(taskId);
		if (AutoTaskInstance.STATUS_UNUSED.equals(ati.getStatus())){
			ati.setStartTime(DateTimeUtil.getNowDateTime());
			ati.setRunDate(BusiDateUtil.getCurBusiDate());
		 }
		ati.setStatus(AutoTaskInstance.STATUS_FINISH);
		ati.setEndTime(DateTimeUtil.getNowDateTime());
		
		if (AutoTask.Task_Type_Member.equals(ati.getTaskType())){//设置接入点子任务
			 List<AutoTaskInstance> atiList = ati.getSubTaskList();
			 for (AutoTaskInstance sati : atiList) {
				 if (AutoTaskInstance.STATUS_UNUSED.equals(sati.getStatus())){
					 sati.setStartTime(DateTimeUtil.getNowDateTime());
					 sati.setRunDate(BusiDateUtil.getCurBusiDate());
				 }
				 sati.setStatus(AutoTaskInstance.STATUS_FINISH);
				 sati.setEndTime(DateTimeUtil.getNowDateTime());
			}
		}
		
	}	
	
	

}
