
package com.upg.ucars.basesystem.autotask.actions;

import java.util.HashMap;
import java.util.List;

import com.upg.ucars.basesystem.autotask.core.autotask.IAutoTaskService;
import com.upg.ucars.basesystem.autotask.core.autotasklog.IAutoTaskLogService;
import com.upg.ucars.basesystem.autotask.core.autotasklog.LogSearchBean;
import com.upg.ucars.basesystem.busidate.util.BusiDateUtil;
import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.constant.CommonConst;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.ICommonDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.autotask.AutoTask;
import com.upg.ucars.mapping.basesystem.autotask.AutoTaskLog;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.security.MemberInfo;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.SourceTemplate;

public class AutoTaskLogAction extends BaseAction {
	private List<AutoTaskLog> logList;	
	private LogSearchBean bean;
	private AutoTaskLog log;
	
	private List<Code> taskTypeList;
	private List<AutoTask> autoTaskList;
	
	private IAutoTaskLogService autoTaskLogService;
	private IAutoTaskService autoTaskService;
	
	public String main() throws ServiceException {		
		taskTypeList = DictionaryUtil.getCodesByKey("B031");
		
		autoTaskList = autoTaskService.queryAutoTasks(null, null);
		bean = new LogSearchBean();
		bean.setStartDate(DateTimeUtil.getNowDateTime());
		bean.setEndDate(DateTimeUtil.getNowDateTime());		
		
		return MAIN;
	}
	
	public String list() throws ServiceException {		
		Page pg = this.getPg();
		if(null == bean){
			bean = new LogSearchBean();
			bean.setStartDate(BusiDateUtil.getCurBusiDate());
			bean.setEndDate(BusiDateUtil.getCurBusiDate());		
			bean.setIsMainTask(CommonConst.LOGIC_YES);
		}
		
		QueryCondition qc = new QueryCondition();
		qc.addCondition(new ConditionBean("runDate", ConditionBean.MORE_AND_EQUAL, bean.getStartDate()));
		qc.addCondition(new ConditionBean("runDate", ConditionBean.LESS_AND_EQUAL, bean.getEndDate()));
		qc.addCondition(new ConditionBean("taskId", bean.getTaskId()));
		qc.addCondition(new ConditionBean("taskType", bean.getTaskType()));
		if (CommonConst.LOGIC_YES.equals(bean.getIsMainTask())){
			qc.addCondition(new ConditionBean("memberNo", ConditionBean.IS_NULL, null));
		}else{
			qc.addCondition(new ConditionBean("memberNo", ConditionBean.IS_NOT_NULL, null));
		}
		
		ICommonDAO dao = SourceTemplate.getBean(ICommonDAO.class, "commonDAO");
		 List<MemberInfo> miList = dao.findByOneProperty(MemberInfo.class, "miNo", null);
		 HashMap<String,String> knMap = new  HashMap<String,String>();
		 for (MemberInfo memberInfo : miList) {
			 knMap.put(memberInfo.getMiNo(), memberInfo.getMiName());
		}
		 
		logList = autoTaskLogService.queryLogs(qc, pg);
		for (AutoTaskLog log : logList) {
			log.setMemberName(knMap.get(log.getMemberNo()));
		}
		
		return this.setDatagridInputStreamData(logList, pg);
	}

	/*public String showSubLog() throws ServiceException{
		Page page = this.getPg();
		//page.setPageCommand(this.getCommand());
		parentLog = AutoTaskServiceFactory.getAutoTaskLogService().getAutoTaskLog(id);
		if(null == bean){
			bean = new LogSearchBean();
		}
		bean.setTaskId(parentLog.getTaskId());
		bean.setRunDate(parentLog.getRunDate());
		logs = AutoTaskServiceFactory.getAutoTaskLogService().getSubLog(bean, page);
		return "subLogList";
	}*/
	
	public String del() throws ServiceException {
		List<Long> logIdList = this.getIdList();
		
		for (Long logId : logIdList) {
			this.autoTaskLogService.delById(logId);
		} 
		
		return null;
	}

	public List<AutoTaskLog> getLogList() {
		return logList;
	}

	public void setLogList(List<AutoTaskLog> logList) {
		this.logList = logList;
	}

	public LogSearchBean getBean() {
		return bean;
	}

	public void setBean(LogSearchBean bean) {
		this.bean = bean;
	}

	public AutoTaskLog getLog() {
		return log;
	}

	public void setLog(AutoTaskLog log) {
		this.log = log;
	}

	public List<Code> getTaskTypeList() {
		return taskTypeList;
	}

	public void setTaskTypeList(List<Code> taskTypeList) {
		this.taskTypeList = taskTypeList;
	}

	public List<AutoTask> getAutoTaskList() {
		return autoTaskList;
	}

	public void setAutoTaskList(List<AutoTask> autoTaskList) {
		this.autoTaskList = autoTaskList;
	}

	public IAutoTaskLogService getAutoTaskLogService() {
		return autoTaskLogService;
	}

	public void setAutoTaskLogService(IAutoTaskLogService autoTaskLogService) {
		this.autoTaskLogService = autoTaskLogService;
	}

	public IAutoTaskService getAutoTaskService() {
		return autoTaskService;
	}

	public void setAutoTaskService(IAutoTaskService autoTaskService) {
		this.autoTaskService = autoTaskService;
	}
}
