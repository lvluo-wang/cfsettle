package com.upg.ucars.basesystem.autotask.core;

import java.util.List;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.autotask.AutoTask;
import com.upg.ucars.model.BooleanResult;
import com.upg.ucars.util.DateTimeUtil;

/**
 * 通用任务的基类
 * 
 * @author shentuwy
 */
public abstract class AbstractCommonAutoTask extends AbstractAutoTask {

	public void execute() {
		AutoTask autoTask = this.getAutoTask();
		//设置任务实例
		AutoTaskInstance ati = new AutoTaskInstance(Thread.currentThread());
		Long waitingTime = autoTask.getDependOutTime();
		if (waitingTime == null)
			waitingTime = new Long(0);

		ati.setTaskId(autoTask.getId());
		ati.setName(autoTask.getName());
		ati.setRunDate(DateTimeUtil.getNowDateTime());//通用任务运行日期为系统时间。
		ati.setStartTime(DateTimeUtil.getNowDateTime());
		ati.setTaskType(AutoTask.Task_Type_Common);
		ati.setStatus(AutoTaskInstance.STATUS_RUN);

		// 将本任务加入监控器中
		AutoTaskCurrentMonitor.getInstance().addAutoTaskInstance(ati);

		BooleanResult br = new BooleanResult(true);

		try {
			int count = 0;
			while (!isDependFinished()) {//5's循环一次,查看依赖任务是否完成
				try {
					Thread.sleep(5000);
					count++;
					if (count > waitingTime.longValue() * 60 / 5){//超过等待时间则抛出异常
						throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.AUTO_TASK_RUN_COMMON, "任务[" + autoTask.getName()	+ "]:等待依赖任务超时.");
					}
				} catch (InterruptedException e) {
					throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.AUTO_TASK_RUN_COMMON, "任务[" + autoTask.getName()	+ "]:等待依赖任务发生异常.");
				}
				
			}
			br = run();
			if (br == null)
				 br = new BooleanResult(true);
			ati.setEndTime(DateTimeUtil.getNowDateTime());
			ati.setRate(100);
			ati.setStatus(br.isSuccess()? AutoTaskInstance.STATUS_FINISH : AutoTaskInstance.STATUS_ERROR);
			ati.setErrMessage(br.getInfo());
			//写入数据库			
			AutoTaskServiceFactory.getAutoTaskLogService().saveAutoTaskLog(ati);
						
		} catch (ThreadDeath e) {
			ati.setStatus(AutoTaskInstance.STATUS_ERROR);
			ati.setEndTime(DateTimeUtil.getNowDateTime());
			ati.setErrMessage("被强制终止");
			//写入数据库			
			AutoTaskServiceFactory.getAutoTaskLogService().saveAutoTaskLog(ati);
		} catch (Throwable e) {
			ati.setStatus(AutoTaskInstance.STATUS_ERROR);
			ati.setEndTime(DateTimeUtil.getNowDateTime());
			ati.setErrMessage(e.getMessage());			
			//写入数据库			
			AutoTaskServiceFactory.getAutoTaskLogService().saveAutoTaskLog(ati);
		}finally{
			ati.setThread(null);
		}
	}

	/**
	 * 任务处理
	 * 
	 * @throws ServiceException
	 */
	public abstract BooleanResult run() throws Exception;
	/**
	 * 所依赖的任务是否完成 
	 *
	 * @return
	 */
	private boolean isDependFinished() {
		List<AutoTask> dependTasks = this.getDependTasks();
		for (AutoTask autoTask : dependTasks) {
			AutoTaskInstance ati = (AutoTaskInstance) AutoTaskCurrentMonitor
			.getInstance().getAutoTaskInstance(autoTask.getId());
			if (ati == null
					|| (!AutoTaskInstance.STATUS_FINISH.equals(ati.getStatus()) && !AutoTaskInstance.STATUS_FINISH_ERROR.equals(ati.getStatus()))
				) {
				return false;
			}			
		}
		return true;
	}

}
