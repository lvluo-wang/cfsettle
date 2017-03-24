package com.upg.ucars.basesystem.autotask.core;

import java.util.ArrayList;
import java.util.List;

import com.upg.ucars.basesystem.busidate.util.BusiDateUtil;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.autotask.AutoTask;
import com.upg.ucars.model.BooleanResult;
import com.upg.ucars.util.DateTimeUtil;


/**
 * 成员行任务的基类
 * @author shentuwy
 */
public abstract class AbstractMemberAutoTask extends AbstractAutoTask{
	
	public void execute() {	
		if (this.getMemberNo() == null){//执行整个任务，为每个接入点执行			
			this.executeWhole();
			
		}else{//执行单个接入点任务			
			this.executeSingle(this.getMemberNo());
		}
		
	}
	/**
	 * 执行整个任务
	 *
	 * @throws ServiceException
	 */
	private  void executeWhole() throws ServiceException {
		if (this.getAutoTask().getMemberNoList().isEmpty()){
			//设置任务实例
			AutoTaskInstance ati = new AutoTaskInstance(Thread.currentThread());			
			ati.setName(getAutoTask().getName());
			ati.setRunDate(DateTimeUtil.getNowDateTime());
			ati.setStartTime(DateTimeUtil.getNowDateTime());
			ati.setEndTime(DateTimeUtil.getNowDateTime());
			ati.setTaskId(getTaskId());
			ati.setTaskType(AutoTask.Task_Type_Member);
			ati.setStatus(AutoTaskInstance.STATUS_FINISH);
			ati.setErrMessage("没有指定任何接入点");
			AutoTaskCurrentMonitor.getInstance().addAutoTaskInstance(ati);
			return;
		}
		//设置任务实例
		AutoTaskInstance ati = new AutoTaskInstance(Thread.currentThread());
		ati.setEndTime(null);
		ati.setName(getAutoTask().getName());
		ati.setRunDate(DateTimeUtil.getNowDateTime());
		ati.setStartTime(DateTimeUtil.getNowDateTime());
		ati.setTaskId(getTaskId());
		ati.setTaskType(AutoTask.Task_Type_Member);
		ati.setStatus(AutoTaskInstance.STATUS_RUN);				
		
		AutoTaskCurrentMonitor.getInstance().addAutoTaskInstance(ati);
		
		Long waitingTime = this.getAutoTask().getDependOutTime();
		if (waitingTime == null)
			waitingTime = new Long(0);
		try {
			int count = 0;
			while (!isDependFinished()) {//5's循环一次,超过等待时间则抛出异常
				try {
					Thread.sleep(5000);
					count++;
					if (count > waitingTime.longValue() * 60 / 5)
						throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.AUTO_TASK_RUN_COMMON, "任务[" + ati.getName()	+ "]:等待依赖任务超时.");
				} catch (InterruptedException e) {
					throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.AUTO_TASK_RUN_COMMON, "任务[" + ati.getName()	+ "]:等待依赖任务发生异常.");
				}				
				
			}
			List<String> memberNoList = this.getAutoTask().getMemberNoList();
			
			ArrayList<MemberTaskProcessor> processorList = new ArrayList<MemberTaskProcessor>(memberNoList.size());
			//创建子线程
			for(String memberNo : memberNoList){				
				//为每个成员行创建一个线程
				MemberTaskProcessor processor = new MemberTaskProcessor(ati, memberNo);
				processorList.add(processor);							
			}

			//启动线程
			for (MemberTaskProcessor processor : processorList ){
				
				processor.start();	
			}
			
			
		} catch (Throwable e) {
			ati.setStatus(AutoTaskInstance.STATUS_ERROR);
			ati.setEndTime(DateTimeUtil.getNowDateTime());
			ati.setErrMessage(e.getMessage());
			
			//写入数据库			
			AutoTaskServiceFactory.getAutoTaskLogService().saveAutoTaskLog(ati);
		}	
		
				
	}
	/**
	 * 执行单个成员行任务			
	 * @param memberNo
	 * @throws ServiceException
	 */
	private void executeSingle(String memberNo) throws ServiceException {		
		
		AutoTaskInstance parentInstance = AutoTaskCurrentMonitor.getInstance().getAutoTaskInstance(getTaskId());
		AutoTaskInstance ati = parentInstance.createSubInstance(memberNo, Thread.currentThread());		
		ati.setName(getAutoTask().getName());
		ati.setRunDate(BusiDateUtil.getCurBusiDate(memberNo));
		ati.setStartTime(DateTimeUtil.getNowDateTime());
		ati.setTaskId(getTaskId());
		ati.setTaskType(AutoTask.Task_Type_Member);
		ati.setSimpleStatus(AutoTaskInstance.STATUS_RUN);
		ati.setEndTime(null);
		
		try {		
			Long waitingTime = this.getAutoTask().getDependOutTime();
			if (waitingTime == null)
				waitingTime = new Long(0);
			int count = 0;
			while (!isDependFinished() || !isDependFinishedBySingle(memberNo)) {//5's循环一次,超过等待时间则抛出异常
				try {
					Thread.sleep(5000);
					count++;
					if (count > waitingTime.longValue() * 60 / 5)
						throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.AUTO_TASK_RUN_COMMON, "任务[" + getAutoTask().getName()	+ "]:等待依赖任务超时.");

				} catch (InterruptedException e) {
					throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.AUTO_TASK_RUN_COMMON, "任务[" + getAutoTask().getName()	+ "]:等待依赖任务发生异常.");
					
				}
			}
				
			//调用执行
			BooleanResult br = runByMember(memberNo);		
			if (br == null)
				br = new BooleanResult(true);			
			ati.setEndTime(DateTimeUtil.getNowDateTime());
			ati.setSimpleStatus(br.isSuccess()? AutoTaskInstance.STATUS_FINISH : AutoTaskInstance.STATUS_ERROR);
			ati.setErrMessage(br.getInfo());	
			ati.setSimpleStatus(AutoTaskInstance.STATUS_FINISH);			
		}catch (ThreadDeath e) {				
			ati.setSimpleStatus(AutoTaskInstance.STATUS_ERROR);
			ati.setErrMessage("被强制中止");	
			ati.setEndTime(DateTimeUtil.getNowDateTime());
			//写入数据库			
			AutoTaskServiceFactory.getAutoTaskLogService().saveAutoTaskLog(ati);
		} catch (Throwable e) {	
			ati.setEndTime(DateTimeUtil.getNowDateTime());
			ati.setSimpleStatus(AutoTaskInstance.STATUS_ERROR);			
			ati.setErrMessage(e.getMessage());
			//写入数据库			
			AutoTaskServiceFactory.getAutoTaskLogService().saveAutoTaskLog(ati);
						
		}
		
		
		
		
	}	
		
	
	
	/**
	 * 按每个成员行执行
	 * @param memberNo
	 * @return
	 * @throws Exception
	 */
	public abstract BooleanResult runByMember(String  memberNo) throws Exception;
	
	
	/**
	 * 为每个成员行启动单独线程进行执行
	 *
	 * @author shentuwy
	 */
	class MemberTaskProcessor extends Thread{
		String memberNo = null;
		AutoTaskInstance parentInstance;
		
		public MemberTaskProcessor(AutoTaskInstance parentInstance, String memberNo){
			this.parentInstance = parentInstance;
			this.memberNo = memberNo;	
			
		}

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {		
			AutoTaskInstance ati = parentInstance.createSubInstance(memberNo, Thread.currentThread());
			ati.setEndTime(null);
			ati.setRunDate(BusiDateUtil.getCurBusiDate(memberNo));
			ati.setStartTime(DateTimeUtil.getNowDateTime());
			ati.setStatus(AutoTaskInstance.STATUS_RUN);	
			
			
			try {			
				Long waitingTime = getAutoTask().getDependOutTime();
				if (waitingTime == null)
					waitingTime = new Long(0);
				int count = 0;
		
				while (!isDependFinishedBySingle(memberNo)) {//5's循环一次,超过等待时间则抛出异常
					try {
						Thread.sleep(5000);
						count++;
						if (count > waitingTime.longValue() * 60 / 5)
							throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.DB_COMMON_ERROR, "任务[" + ati.getName()	+ "]:等待依赖任务超时.");
					} catch (InterruptedException e) {
						throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.DB_COMMON_ERROR, "任务[" + ati.getName()	+ "]:等待依赖任务发生异常.");

					}
				}
				
				BooleanResult br = runByMember(memberNo);
				if (br == null)
					br = new BooleanResult(true);
				ati.setStatus(br.isSuccess()?AutoTaskInstance.STATUS_FINISH:AutoTaskInstance.STATUS_ERROR);
				ati.setErrMessage(br.getInfo());	
				ati.setEndTime(DateTimeUtil.getNowDateTime());
				//写入数据库			
				AutoTaskServiceFactory.getAutoTaskLogService().saveAutoTaskLog(ati);
				
			} catch (ThreadDeath e) {				
				ati.setStatus(AutoTaskInstance.STATUS_ERROR);
				ati.setErrMessage("被强制中止");	
				ati.setEndTime(DateTimeUtil.getNowDateTime());
				//写入数据库			
				AutoTaskServiceFactory.getAutoTaskLogService().saveAutoTaskLog(ati);
			}catch (Throwable e) {				
				ati.setStatus(AutoTaskInstance.STATUS_ERROR);
				ati.setErrMessage(e.getMessage());	
				ati.setEndTime(DateTimeUtil.getNowDateTime());
				//写入数据库			
				AutoTaskServiceFactory.getAutoTaskLogService().saveAutoTaskLog(ati);
			}
				
			
			triggerEndEvent();
		
			
			
		}
	}
	
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
			if (AutoTask.Task_Type_Common.equals(autoTask.getTaskType())){
				if (ati == null	|| !AutoTaskInstance.STATUS_FINISH.equals(ati.getStatus())){
					return false;
				}
			}
			
			if (AutoTask.Task_Type_Member.equals(autoTask.getTaskType())){
				if (ati == null	|| AutoTaskInstance.STATUS_UNUSED.equals(ati.getStatus())){
					return false;
				}
			}			
						
		}
		return true;
	}
	
	/**
	 * 该成员行所依赖的任务是否完成 
	 *
	 * @param memberNo
	 * @return
	 */
	private boolean isDependFinishedBySingle(String memberNo) {		
		List<AutoTask> dependTasks = this.getDependTasks();
		for (AutoTask autoTask : dependTasks) {
			
			if (AutoTask.Task_Type_Member.equals(autoTask.getTaskType())){
				AutoTaskInstance ati = (AutoTaskInstance) AutoTaskCurrentMonitor
					.getInstance().getAutoTaskInstance(autoTask.getId());	
				AutoTaskInstance  subAti = ati.getSubInstance(memberNo);
				if (subAti != null	&& !AutoTaskInstance.STATUS_FINISH.equals(subAti.getStatus())){
					return false;
				}
			}
						
		}
		return true;
	}
	
	
}
