package com.upg.ucars.framework.bpm.publish.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jbpm.taskmgmt.def.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.factory.DynamicPropertyTransfer;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.bpm.base.BpmQueryDAO;
import com.upg.ucars.framework.bpm.procmap.IProdProcMapService;
import com.upg.ucars.framework.bpm.publish.service.IHumnTaskService;
import com.upg.ucars.framework.bpm.publish.service.IProcessDefService;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.framework.bpm.HumnTask;
import com.upg.ucars.mapping.framework.bpm.ProcessDef;
import com.upg.ucars.mapping.framework.bpm.ProdProcMap;
import com.upg.ucars.model.BooleanResult;
import com.upg.ucars.model.ConditionBean;


/**
 * 流程发布
 * 
 * @author shentuwy
 * @date 2012-7-3
 * 
 */
public class PublishAction extends BaseAction {

	private static final long	serialVersionUID	= 7028729726935964424L;

	private static Logger		log					= LoggerFactory
															.getLogger(PublishAction.class);

	/** 流程定义 */
	private ProcessDef			process;
	/** 流程定义ID */
	private String				procId;
	/** 人工任务 */
	private HumnTask			task;
	/** 任务类型列表 */
	private List<Code>			taskTypeList;
	/** 流程附件 */
	private File				upload;
	/** 附件类型 */
	private String				uploadContentType;
	/** 产品编码 */
	private String				prodNos;

	/** 流程定义服务 */
	IProcessDefService			processService;
	/** 人工任务服务 */
	IHumnTaskService			humnTaskService;
	/** 产品流程映射服务 */
	IProdProcMapService			prodProcMapService;
	
	BpmQueryDAO					bpmQueryDAO;

	/**
	 * 主页面
	 * 
	 */
	public String main() {
		return MAIN;
	}

	public String queryData() {
		List<ProcessDef> list = processService.queryProcessDef(null,
				this.getPg());
		return setDatagridInputStreamData(list, this.getPg());
	}

	/**
	 * 发布页
	 */
	public String toPublish() {
		return "publish";
	}

	/**
	 * 发布
	 * 
	 */
	public String publishProcess() {
		boolean success = false;
		if (upload != null) {
			List<String> prodNoList = null;
			if (StringUtils.isNotBlank(prodNos)) {
				prodNoList = Arrays.asList(prodNos.split(COMMA));
			}
			if (log.isDebugEnabled()) {
				log.debug("prodNos=" + String.valueOf(prodNos) + " prodNoList="
						+ String.valueOf(prodNoList));
			}
			success = processService.publishService(getUploadContentType(),
					getUpload(), prodNoList);
		}
		return setInputStreamData(success ? SUCCESS_1 : FAIL_0);
	}

	public String edit() {
		if (StringUtils.isNotBlank(getId())) {
			process = processService.getProcessDefById(this.getPKId());
		}
		return EDIT;
	}

	public void editSave() {
		processService.update(process);
	}

	public void deleteProcess() {
		ProcessDef process = this.processService.getProcessDefById(this
				.getPKId());
		BooleanResult br = this.processService.checkDeleteProcess(process
				.getProcName());
		if (!br.isSuccess())
			throw ExceptionManager.getException(ServiceException.class,
					ErrorCodeConst.WF_COMMON_ERROR, br.getInfo());
		//
		List<ConditionBean> condList = new ArrayList<ConditionBean>(2);
		condList.add(new ConditionBean("procId", this.getPKId()));
		condList.add(new ConditionBean("miNo", ConditionBean.IS_NOT_NULL, null));
		List<ProdProcMap> mapList = this.prodProcMapService.queryProdProcMap(
				condList, null);
		if (!mapList.isEmpty()) {
			throw ExceptionManager.getException(ServiceException.class,
					ErrorCodeConst.WF_DEF_DEL_001);
		}

		//
		this.processService.deleteProcessDef(process.getProcName());
	}

	public String taskList() {
		return "tasklist";
	}

	public String queryTaskData() {
		ProcessDef pd = this.processService.getProcessDefById(Long.valueOf(this.getProcId()));
		List<HumnTask> taskList = this.humnTaskService.getHumnTasksByProcId(Long.valueOf(this.getProcId()));
		List<Task> jbpmTaskList = bpmQueryDAO.getTaskListByPd(pd.getJbpmPdId());
		Collections.sort(taskList, IHumnTaskService.HUMN_TASK_COMPARATOR);
		taskList = getSubListByPage(taskList, this.getPg());
		if (taskList != null) {
			for (int i = 0; i < taskList.size(); i++) {
				HumnTask ht = taskList.get(i);
				String oldTask = "1";
				for (Task task : jbpmTaskList) {
					if (StringUtils.equals(ht.getTaskName(), task.getName())) {
						oldTask = "0";
						break;
					}
				}
				ht = (HumnTask) DynamicPropertyTransfer.dynamicAddProperty(ht, "oldTask", oldTask);
				taskList.set(i, ht);
			}
		}
		return this.setDatagridInputStreamData(taskList, this.getPg());
	}

	public String toTaskEdit() {
		task = this.humnTaskService.getHumnTaskById(this.getPKId());
		this.taskTypeList = DictionaryUtil.getCodesByField("humn_task",
				"task_type");
		return "taskedit";
	}

	public void taskSave() {
		this.humnTaskService.updateHumnTask(task);
	}

	public ProcessDef getProcess() {
		return process;
	}

	public void setProcess(ProcessDef process) {
		this.process = process;
	}

	public File getUpload() {
		return upload;
	}

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public HumnTask getTask() {
		return task;
	}

	public void setTask(HumnTask task) {
		this.task = task;
	}

	public List<Code> getTaskTypeList() {
		return taskTypeList;
	}

	public void setTaskTypeList(List<Code> taskTypeList) {
		this.taskTypeList = taskTypeList;
	}

	public void setProcessService(IProcessDefService processService) {
		this.processService = processService;
	}

	public void setHumnTaskService(IHumnTaskService humnTaskService) {
		this.humnTaskService = humnTaskService;
	}

	public void setProdProcMapService(IProdProcMapService prodProcMapService) {
		this.prodProcMapService = prodProcMapService;
	}

	public String getProcId() {
		return procId;
	}

	public void setProcId(String procId) {
		this.procId = procId;
	}

	public String getProdNos() {
		return prodNos;
	}

	public void setProdNos(String prodNos) {
		this.prodNos = prodNos;
	}

	public void setBpmQueryDAO(BpmQueryDAO bpmQueryDAO) {
		this.bpmQueryDAO = bpmQueryDAO;
	}
	
}
