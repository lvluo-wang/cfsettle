package com.upg.ucars.basesystem.autotask.core;

import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.upg.ucars.basesystem.autotask.core.autotask.IAutoTaskService;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.autotask.AutoTask;

/**
 * 定时自动任务基类<br>
 * 
 * @author shentuwy
 */
public abstract class AbstractAutoTask implements Job {
	private AutoTask		autoTask;
	private List<AutoTask>	dependTasks;
	private String			memberNo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// 获取任务的一般性内容信息
		autoTask = (AutoTask) context.getJobDetail().getJobDataMap().get(ScheduleHelper.VAR_KEY_AUTOTASK);
		this.memberNo = (String) context.getJobDetail().getJobDataMap().get(ScheduleHelper.VAR_KEY_MEMBERNO);

		// 设置营业日期
		// workDate = BusiDateUtil.getCurBusiDate();

		// 加载依赖任务
		IAutoTaskService autoTaskService = AutoTaskServiceFactory.getAutoTaskService();
		dependTasks = autoTaskService.getDependAutoTasks(this.getTaskId());
		//
		try {
			// 执行
			this.execute();
		} catch (Exception e) {
			LogFactory.getLog(this.getClass()).error(e);
			AutoTaskInstance ati = AutoTaskCurrentMonitor.getInstance().getAutoTaskInstance(getTaskId());
			if (ati != null) {
				ati.setStatus(AutoTaskInstance.STATUS_UNUSED);
				ati.setErrMessage(null);
				ati.setStartTime(null);
				ati.setEndTime(null);
				ati.setRunDate(null);
			}
		}

		this.triggerEndEvent();

	}

	/**
	 * 任务处理
	 * 
	 * @throws ServiceException
	 */
	public abstract void execute();

	/**
	 * @return Returns the autoTask.
	 */
	public AutoTask getAutoTask() {
		return this.autoTask;
	}

	/**
	 * @return Returns the taskId.
	 */
	public Long getTaskId() {
		return this.getAutoTask().getId();
	}

	/**
	 * @return Returns the taskInfo.
	 */
	public String getTaskPara() {
		return autoTask.getPara();
	}

	/**
	 * 营业日期
	 * 
	 * @return
	 */
	// public Date getWorkDate() {
	// return workDate;
	// }

	/**
	 * 获取所依赖的任务
	 * 
	 * @return
	 */
	protected List<AutoTask> getDependTasks() {

		return dependTasks;

	}

	/**
	 * 指定接入点运行时，传入的接入点编号
	 * 
	 * @return
	 */
	public String getMemberNo() {
		return memberNo;
	}

	/**
	 * 触发结束事件
	 * 
	 */
	protected void triggerEndEvent() {

		try {
			// 触发下一任务
			triggerNextTask();
		} catch (Exception e) {
			// 处理掉,触发下一个任务时不影响本身任务
		}
	}

	/**
	 * 触发结束事件
	 * 
	 */
	private void triggerNextTask() {
		if (this.getMemberNo() != null && !this.getMemberNo().equals("")) {
			return;// 单个成员行任务，不触发下个任务
		}

		if (this.getAutoTask().getNextTask() != null && !"".equals(this.getAutoTask().getNextTask())) {// 存在next任务

			AutoTaskInstance ati = AutoTaskCurrentMonitor.getInstance().getAutoTaskInstance(getTaskId());

			if (ati != null && AutoTaskInstance.STATUS_FINISH.equals(ati.getStatus())) {// 本身正常完成

				IAutoTaskService autoTaskService = AutoTaskServiceFactory.getAutoTaskService();
				AutoTask nextTask = autoTaskService.getAutoTask(Long.valueOf(this.getAutoTask().getNextTask()));

				AutoTaskInstance nextInst = AutoTaskCurrentMonitor.getInstance().getAutoTaskInstance(nextTask.getId());
				if (nextInst != null && AutoTaskInstance.STATUS_UNUSED.equals(nextInst.getStatus())) {// 本身正常完成
					ScheduleHelper.immediateJob(nextTask);
				}

			}

		}
	}

}
