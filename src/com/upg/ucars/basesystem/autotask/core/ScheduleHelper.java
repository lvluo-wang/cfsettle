package com.upg.ucars.basesystem.autotask.core;

import java.text.ParseException;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;

import com.upg.ucars.basesystem.autotask.AutoTaskInitializerServlet;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.framework.task.SpringJobFactory;
import com.upg.ucars.mapping.basesystem.autotask.AutoTask;

/**
 * 调度控制器
 * 
 * @author shentuwy
 */
public final class ScheduleHelper {
	/** 变量KEY--任务对象 */
	public final static String	VAR_KEY_AUTOTASK	= "AutoTask";
	/** 变量KEY--成员行行号 */
	public final static String	VAR_KEY_MEMBERNO	= "MemberNo";

	private static Scheduler	scheduler			= null;

	static {
		System.setProperty(StdSchedulerFactory.PROP_SCHED_JOB_FACTORY_CLASS, SpringJobFactory.class.getName());
	}

	public static Scheduler getScheduler() {
		if (scheduler == null) {
			init();
		}
		return scheduler;
	}

	private static final synchronized void init() {
		if (scheduler == null) {
			try {
				scheduler = StdSchedulerFactory.getDefaultScheduler();
				((StdScheduler) scheduler).setJobFactory(new SpringJobFactory());
			} catch (SchedulerException ex) {
				ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.COMMON_ERROR_CODE, "获取任务调度器失败",
						ex);
			}
		}
	}

	/**
	 * 打开任务
	 * 
	 * @param autoTask
	 * @return
	 * @throws ServiceException
	 */
	public static void addJob(AutoTask autoTask) {
		Scheduler scheduler = getScheduler();
		deleteJob(autoTask);
		try {
			JobDetail jobDetail = createJobDetail(autoTask);
			CronTrigger triger = createCronTrigger(autoTask);
			scheduler.scheduleJob(jobDetail, triger);
			if (!scheduler.isStarted()) {
				scheduler.start();
			}
		} catch (Exception ex) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.COMMON_ERROR_CODE, "打开任务失败", ex);
		}
	}

	/**
	 * 关闭任务
	 * 
	 * @param autoTask
	 * @return
	 */
	public static void deleteJob(AutoTask autoTask) {
		try {
			Scheduler scd = getScheduler();
			scd.deleteJob(autoTask.getName(), String.valueOf(autoTask.getId()));
		} catch (Exception ex) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.COMMON_ERROR_CODE, "关闭任务失败", ex);
		}
	}

	/**
	 * 立即运行任务
	 * 
	 * @param autoTask
	 * @return
	 * @throws ServiceException
	 */
	public static void immediateJob(AutoTask autoTask) throws ServiceException {
		String _TEMP = "temp";
		try {
			Scheduler scd = getScheduler();

			if (!scd.isStarted())
				scd.start();

			scd.deleteJob(_TEMP + autoTask.getName(), autoTask.getId().toString());// 删除已有的。
			JobDetail jobDetail = ScheduleHelper.createJobDetail(autoTask);
			jobDetail.setName(_TEMP + autoTask.getName());

			Trigger trigger = TriggerUtils.makeImmediateTrigger(0, 1);
			trigger.setName(autoTask.getId().toString());

			scd.scheduleJob(jobDetail, trigger);

		} catch (Exception e) {
			throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.AUTO_TASK_001, "", e);
		}
	}

	/**
	 * 立即运行接入点任务
	 * 
	 * @param autoTask
	 * @return
	 * @throws ServiceException
	 */
	public static void immediateMemberJob(AutoTask autoTask, String memberNo) throws ServiceException {

		try {
			Scheduler scd = StdSchedulerFactory.getDefaultScheduler();
			scd.deleteJob(autoTask.getName() + memberNo, autoTask.getId().toString());// 删除已有的。
			JobDetail jobDetail = ScheduleHelper.createJobDetail(autoTask);
			jobDetail.setName(jobDetail.getName() + memberNo);
			jobDetail.getJobDataMap().put(VAR_KEY_MEMBERNO, memberNo);

			Trigger trigger = TriggerUtils.makeImmediateTrigger(0, 1);
			trigger.setName(autoTask.getId().toString() + memberNo);

			//
			// scd.addJobListener(new AutoTaskListener());
			// jobDetail.addJobListener("autoTaskListener");
			scd.scheduleJob(jobDetail, trigger);

		} catch (Exception e) {
			throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.AUTO_TASK_001, "", e);
		}

	}

	public static void stopJob(AutoTask autoTask) {
		// 占位无操作

	}

	public static void stopMemberJob(AutoTask autoTask, String memberNo) {
		// 占位无操作

	}

	private static JobDetail createJobDetail(AutoTask autoTask) throws ClassNotFoundException {
		String className = autoTask.getClassName();
		Class<?> obj = Class.forName(className);
		JobDetail jobDetail = new JobDetail();
		jobDetail.setName(autoTask.getName());
		jobDetail.setGroup(autoTask.getId().toString());
		jobDetail.setJobClass(obj);
		jobDetail.getJobDataMap().put(VAR_KEY_AUTOTASK, autoTask);
		return jobDetail;
	}

	private static CronTrigger createCronTrigger(AutoTask autoTask) throws ParseException {
		if (autoTask == null) {
			throw new IllegalArgumentException("autoTask is null");
		}
		CronTrigger result = new CronTrigger();
		result.setName(autoTask.getName());
		result.setGroup(String.valueOf(autoTask.getId()));
		result.setCronExpression(autoTask.getCronExpr());
		return result;
	}

	/**
	 * TODO 只能运行在特定的服务器上
	 * 
	 */
	private static final void validateCanRunTask() {
		if (!AutoTaskInitializerServlet.isRunTask()) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.COMMON_ERROR_CODE, "定时任务不是运行在本服务器上");
		}
	}
}
