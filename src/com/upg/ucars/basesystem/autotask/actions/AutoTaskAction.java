package com.upg.ucars.basesystem.autotask.actions;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.upg.ucars.basesystem.autotask.core.AutoTaskCurrentMonitor;
import com.upg.ucars.basesystem.autotask.core.autotask.IAutoTaskService;
import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.basesystem.security.core.member.IMemberService;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.autotask.AutoTask;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.security.MemberInfo;
import com.upg.ucars.model.JQueryTreeNode;
import com.upg.ucars.util.SourceTemplate;

public class AutoTaskAction extends BaseAction {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6175906854865929432L;

	private List<Code>			taskTypeCodeList;

	private AutoTask			autoTask;

	private List<AutoTask>		autoTasks;
	private Long[]				leftTasks;
	private List<AutoTask>		dependTasks;
	private Long[]				rightTasks;
	private List<MemberInfo>	memberInfos;

	private List				selectedBranchs;
	private String[]			leftBranchs;
	private List				unSelectedBranchs;
	private String[]			rightBranchs;

	private IAutoTaskService	autoTaskService;

	private String				idDiv;										// 弹出页面使用
	private String				winId;										// 弹出页面使用
	private String				treeJSONInfo;
	private String				treeJSONInfo1;
	private String				treeJSONInfo2;

	public String main() {
		return MAIN;
	}

	public String list() {
		Page pg = this.getPg();
		autoTasks = autoTaskService.queryAutoTasks(null, pg);
		return setDatagridInputStreamData(autoTasks, pg);
	}

	public String toAdd() throws ServiceException, ParseException {
		taskTypeCodeList = DictionaryUtil.getCodesByKey("B031");
		autoTasks = autoTaskService.queryAutoTasks(null, null);
		return ADD;
	}

	public String toChooseMemberList() {

		IMemberService memberService = SourceTemplate.getBean(IMemberService.class, "memberService");
		List<MemberInfo> memberList = memberService.queryMemberInfo(null, null);

		ArrayList<JQueryTreeNode> nodeList = new ArrayList<JQueryTreeNode>();
		for (MemberInfo member : memberList) {// 省
			JQueryTreeNode node = new JQueryTreeNode();
			node.setId(member.getMiNo());
			node.setText(member.getMiName());
			// node.setState(JQueryTreeNode.STATE_CLOSED);//

			nodeList.add(node);

		}

		JSONArray bejson = JSONArray.fromObject(nodeList);

		this.setTreeJSONInfo1(bejson.toString());

		return "member_list";
	}

	public String toChooseTaskList() {

		List<AutoTask> taskList = this.autoTaskService.queryAutoTasks(null, null);
		ArrayList<JQueryTreeNode> nodeList = new ArrayList<JQueryTreeNode>();
		for (AutoTask task : taskList) {// 省
			JQueryTreeNode node = new JQueryTreeNode();
			node.setId(task.getId().toString());
			node.setText(task.getName());
			// node.setState(JQueryTreeNode.STATE_CLOSED);//

			nodeList.add(node);

		}

		JSONArray bejson = JSONArray.fromObject(nodeList);

		this.setTreeJSONInfo2(bejson.toString());

		return "task_list";
	}

	/**
	 * 保存新任务
	 * 
	 * @return
	 */
	public void save() {
		autoTask.setStatus(AutoTask.STATUS_CLOSE);
		this.getAutoTaskService().addAutoTask(autoTask);
	}

	public String toEdit() throws ServiceException, ParseException {
		autoTask = autoTaskService.getAutoTask(this.getPKId());

		taskTypeCodeList = DictionaryUtil.getCodesByKey("B031");

		autoTasks = autoTaskService.queryAutoTasks(null, null);
		for (int i = 0; i < autoTasks.size(); i++) {
			if (autoTasks.get(i).getId().equals(autoTask.getId())) {
				autoTasks.remove(i);
				break;
			}
		}
		// 依赖任务
		dependTasks = autoTaskService.getDependAutoTasks(this.getPKId());

		// 接入点
		List<String> memberNoList = autoTask.getMemberNoList();
		memberInfos = new ArrayList<MemberInfo>();
		IMemberService memberService = SourceTemplate.getBean(IMemberService.class, "memberService");
		List<MemberInfo> memberList = memberService.queryMemberInfo(null, null);
		for (MemberInfo memberInfo : memberList) {
			if (memberNoList.contains(memberInfo.getMiNo())) {
				memberInfos.add(memberInfo);
			}
		}

		return EDIT;
	}

	public void update() {
		AutoTask pt = autoTaskService.getAutoTask(autoTask.getId());
		pt.setClassName(autoTask.getClassName());
		pt.setCronExpr(autoTask.getCronExpr());
		pt.setDependOutTime(autoTask.getDependOutTime());
		pt.setDependTasks(autoTask.getDependTasks());
		pt.setMemberNos(autoTask.getMemberNos());
		pt.setName(autoTask.getName());
		pt.setNextTask(autoTask.getNextTask());
		pt.setPara(autoTask.getPara());
		pt.setTaskType(autoTask.getTaskType());
		this.getAutoTaskService().updateAutoTask(pt);
	}

	public void del() {
		// 删除数据库中的任务
		List<Long> idList = this.getIdList();
		for (Long tid : idList) {
			this.getAutoTaskService().deleteAutoTask(tid);
			// 删除监控器中的任务
			AutoTaskCurrentMonitor.getInstance().delAutoTaskInstance(tid);
		}
	}

	public void openTask() {
		List<Long> idList = this.getIdList();
		for (Long tid : idList) {
			this.getAutoTaskService().openAutoTask(tid);
		}
	}

	public void closeTask() {
		List<Long> idList = this.getIdList();
		for (Long tid : idList) {
			this.getAutoTaskService().closeAutoTask(tid);
		}
	}

	public AutoTask getAutoTask() {
		return autoTask;
	}

	public void setAutoTask(AutoTask autoTask) {
		this.autoTask = autoTask;
		// long waiting=this.autoTask.getWaitingTime().longValue()*60;
		// this.autoTask.setWaitingTime(new Long(waiting));
	}

	public List<MemberInfo> getMemberInfos() {
		return memberInfos;
	}

	public void setMemberInfos(List<MemberInfo> memberInfos) {
		this.memberInfos = memberInfos;
	}

	public List getAutoTasks() {
		return autoTasks;
	}

	public void setAutoTasks(List autoTasks) {
		this.autoTasks = autoTasks;
	}

	/**
	 * @return Returns the dependTasks.
	 */
	public List getDependTasks() {
		return dependTasks;
	}

	/**
	 * @return Returns the leftTasks.
	 */
	public Long[] getLeftTasks() {
		return leftTasks;
	}

	/**
	 * @param leftTasks
	 *            The leftTasks to set.
	 */
	public void setLeftTasks(Long[] leftTasks) {
		this.leftTasks = leftTasks;
	}

	/**
	 * @return Returns the rightTasks.
	 */
	public Long[] getRightTasks() {
		return rightTasks;
	}

	/**
	 * @param rightTasks
	 *            The rightTasks to set.
	 */
	public void setRightTasks(Long[] rightTasks) {
		this.rightTasks = rightTasks;
	}

	public String[] getLeftBranchs() {
		return leftBranchs;
	}

	public void setLeftBranchs(String[] leftBranchs) {
		this.leftBranchs = leftBranchs;
	}

	public String[] getRightBranchs() {
		return rightBranchs;
	}

	public void setRightBranchs(String[] rightBranchs) {
		this.rightBranchs = rightBranchs;
	}

	public List getSelectedBranchs() {
		return selectedBranchs;
	}

	public List getUnSelectedBranchs() {
		return unSelectedBranchs;
	}

	public IAutoTaskService getAutoTaskService() {
		return autoTaskService;
	}

	public void setAutoTaskService(IAutoTaskService autoTaskService) {
		this.autoTaskService = autoTaskService;
	}

	public List<Code> getTaskTypeCodeList() {
		return taskTypeCodeList;
	}

	public void setTaskTypeCodeList(List<Code> taskTypeCodeList) {
		this.taskTypeCodeList = taskTypeCodeList;
	}

	public String getIdDiv() {
		return idDiv;
	}

	public void setIdDiv(String idDiv) {
		this.idDiv = idDiv;
	}

	public String getWinId() {
		return winId;
	}

	public void setWinId(String winId) {
		this.winId = winId;
	}

	public String getTreeJSONInfo() {
		return treeJSONInfo;
	}

	public void setTreeJSONInfo(String treeJSONInfo) {
		this.treeJSONInfo = treeJSONInfo;
	}

	public String getTreeJSONInfo1() {
		return treeJSONInfo1;
	}

	public void setTreeJSONInfo1(String treeJSONInfo1) {
		this.treeJSONInfo1 = treeJSONInfo1;
	}

	public String getTreeJSONInfo2() {
		return treeJSONInfo2;
	}

	public void setTreeJSONInfo2(String treeJSONInfo2) {
		this.treeJSONInfo2 = treeJSONInfo2;
	}

}
