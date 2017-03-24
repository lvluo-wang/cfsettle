package com.upg.ucars.bpm.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.bpm.core.IUcarsProcessService;
import com.upg.ucars.constant.CommonConst;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.framework.bpm.InstanceBusiness;
import com.upg.ucars.model.TaskProcessResult;
import com.upg.ucars.util.JsonUtils;

public class DefaultTaskProcessAction extends BaseAction {

	private static final long		serialVersionUID	= 7565200301382597411L;

	protected static final Logger	LOG					= LoggerFactory
																.getLogger(DefaultTaskProcessAction.class);

	protected IUcarsProcessService	ucarsProcessService;

	/** 处理意见 */
	protected TaskProcessResult		tpr;
	/** 任务ID */
	protected Long					taskId;
	/** 任务处理的viewUrl */
	protected String				viewUrl;
	/** 返回url */
	private String					backUrl;
	/** 任务处理url */
	private String					commitTaskUrl;
	/** 实例业务 */
	protected InstanceBusiness		ib;
	/** 处理选项 */
	private Map<String,String>		approvalOption = new LinkedHashMap<String,String>();
	/** 审批意见 */
	protected List<Code>				approvalOpinion;
	

	public String viewProcess() {
		approvalOpinion = DictionaryUtil.getCodesByKey(CommonConst.APPROVAL_OPINION);
		prepare();
		if (StringUtils.isNotBlank(viewUrl)) {
			viewUrl = viewUrl.replaceAll("\\$", "?").replaceAll("~", "&");

		}
		processViewUrl();
		processCommitTaskUrl();
		processApprovalOption();
		return SUCCESS;
	}
	
	public String viewDoneTask(){
		viewProcess();
		return "viewDoneTask";
	}
	
	private void processApprovalOption(){
		if (taskId != null) {
			Map<String,String> transitions = ucarsProcessService.getLeaveTransitionMapByTask(taskId);
			if (transitions != null && transitions.size() > 0) {
				Map<String,String> hasKeyMap = new HashMap<String,String>();
				Map<String,String> noKeyMap = new HashMap<String,String>();
				for (Iterator<String> it = transitions.keySet().iterator();it.hasNext();) {
					String key = it.next();
					if (StringUtils.isNotBlank(key)) {
						hasKeyMap.put(key, transitions.get(key));
					}else{
						noKeyMap.put("1", "通过");
					}
				}
				if (!noKeyMap.isEmpty()) {
					approvalOption.putAll(noKeyMap);
				}
				if (!hasKeyMap.isEmpty()) {
					approvalOption.putAll(hasKeyMap);
				}
			}
		}
	}

	private void processViewUrl() {
		if (StringUtils.isBlank(viewUrl)) {
			if (ib != null && ib.getId() != null) {
				ib = ucarsProcessService.getInstanceBusiness(ib.getId());
			}
		}
	}

	private void processCommitTaskUrl() {
		if (StringUtils.isBlank(commitTaskUrl)) {
			if (ib != null && ib.getId() != null) {
				ib = ucarsProcessService.getInstanceBusiness(ib.getId());
			}
		}
	}

	protected void prepare() {

	}

	/**
	 * 通用审批
	 * 
	 * @return
	 */
	public String tyspProcess() {
		tpr = ucarsProcessService.getTaskProcessResult(taskId);
		return viewProcess();
	}

	public String dealTask() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String,Object> callbackMap = new HashMap<String,Object>();
		boolean isSuccess = true;
		try {
			beforeDealTask(map,callbackMap);
			if (tpr.isDeal()) {
				ucarsProcessService.dealTask(taskId, tpr, map,callbackMap);
			} else {
				ucarsProcessService.saveProcessResult(taskId, tpr,callbackMap);
			}
		} catch (Exception ex) {
			isSuccess = false;
			LOG.error(ex.getMessage(), ex);
			throw ex;
		}
		return setInputStreamData(isSuccess ? SUCCESS_1 : FAIL_0);
	}

	protected void beforeDealTask(Map<String, Object> map,Map<String,Object> callbackMap) {

	}

	@SuppressWarnings("unchecked")
	protected static final <T> T convertBean(String json, Class<T> clazz) {
		T ret = null;
		if (StringUtils.isNotBlank(json) && clazz != null) {
			String trimJson = json.trim();
			try {
				ret = (T) JsonUtils.stringToObject(trimJson, clazz);
			} catch (Exception e) {
				LOG.error("json=" + json + ",class=" + clazz.getName()
						+ ",error:" + e.getMessage(), e);
			}
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	protected static final <T> List<T> convertList(String json, Class<T> clazz) {
		List<T> list = null;
		if (StringUtils.isNotBlank(json) && clazz != null) {
			String trimJson = json.trim();
			try {
				list = (List<T>) JsonUtils.stringToObject(trimJson, clazz);
			} catch (Exception e) {
				LOG.error("json=" + json + ",class=" + clazz.getName()
						+ ",error:" + e.getMessage(), e);
			}
		}
		return list;
	}

	public void setUcarsProcessService(IUcarsProcessService ucarsProcessService) {
		this.ucarsProcessService = ucarsProcessService;
	}

	public TaskProcessResult getTpr() {
		return tpr;
	}

	public void setTpr(TaskProcessResult tpr) {
		this.tpr = tpr;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public InstanceBusiness getIb() {
		return ib;
	}

	public void setIb(InstanceBusiness ib) {
		this.ib = ib;
	}

	public String getViewUrl() {
		return viewUrl;
	}

	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}

	public String getBackUrl() {
		return backUrl;
	}

	public void setBackUrl(String backUrl) {
		this.backUrl = backUrl;
	}

	public String getCommitTaskUrl() {
		return commitTaskUrl;
	}

	public void setCommitTaskUrl(String commitTaskUrl) {
		this.commitTaskUrl = commitTaskUrl;
	}

	public Map<String, String> getApprovalOption() {
		return approvalOption;
	}
	
	public List<Code> getApprovalOpinion() {
		return approvalOpinion;
	}

}
