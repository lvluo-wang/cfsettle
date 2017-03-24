package com.upg.ucars.bpm.factory;

import java.util.ArrayList;
import java.util.List;

import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.def.Task;
import org.springframework.beans.factory.annotation.Autowired;

import com.upg.ucars.framework.bpm.assign.dao.IHumanTaskActrDAO;
import com.upg.ucars.mapping.framework.bpm.HumnTaskActr;

public abstract class AbstractCreateTaskListener implements CreateTaskListener {

	@Autowired
	private IHumanTaskActrDAO	humanTaskActrDAO;

	protected List<Long[]> getBrchAndRole(ExecutionContext executionContext) {
		List<Long[]> result = new ArrayList<Long[]>();
		Task task = executionContext.getTask();
		ProcessInstance instance = executionContext.getProcessInstance();
		ProcessDefinition pd = task.getProcessDefinition();

		List<HumnTaskActr> taskActrList = humanTaskActrDAO.getHumnTaskActrs(pd.getName(), task.getName(),
				instance.getBrchId());

		if (taskActrList != null && taskActrList.size() > 0) {
			for (HumnTaskActr hta : taskActrList) {
				if (hta.getActrRoleId() != null) {
					Long[] brchAndRole = new Long[2];
					brchAndRole[0] = hta.getActrBrchId();
					brchAndRole[1] = hta.getActrRoleId();
					result.add(brchAndRole);
				}
			}
		}

		return result;
	}

	protected String getTaskName(ExecutionContext executionContext) {
		String result = null;
		Task task = executionContext.getTask();
		if (task != null) {
			result = task.getName();
		}
		return result;
	}

	protected String getTaskDescription(ExecutionContext executionContext) {
		String result = null;
		Task task = executionContext.getTask();
		if (task != null) {
			result = task.getDescription();
		}
		return result;
	}
}
