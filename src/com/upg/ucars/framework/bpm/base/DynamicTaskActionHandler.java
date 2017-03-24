package com.upg.ucars.framework.bpm.base;

import java.util.List;
import java.util.Set;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

import com.upg.ucars.bpm.core.IUcarsProcessService;
import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.constant.CommonConst;
import com.upg.ucars.util.SourceTemplate;

/**
 * 根据任务模板动态创建任务实例
 * 
 * 
 */
public abstract class DynamicTaskActionHandler implements ActionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8528244707130660449L;

	protected IUcarsProcessService ucarsProcessService = SourceTemplate
			.getBean(IUcarsProcessService.class,
					BeanNameConstants.UCARS_PROCESS_SERVICE);

	@SuppressWarnings("unchecked")
	public void execute(ExecutionContext context) throws Exception {

		List<Long> actorList = getActors(context);

		if (actorList != null && actorList.size() > 0) {

			Token token = context.getToken();
			TaskMgmtInstance tmi = context.getTaskMgmtInstance();

			TaskNode taskNode = (TaskNode) context.getNode();
			Set<Task> tasks = taskNode.getTasks();

			Task task = null;
			if (tasks != null && tasks.size() > 0) {
				task = tasks.iterator().next();
			}
			if (task != null) {
				for (Long actorId : actorList) {
					TaskInstance ti = tmi.createTaskInstance(task, token);
					ti.setActorId(actorId.toString());
					ti.setPreActor(actorId.toString()+CommonConst.COMMA);
				}
			}
		}
	}

	protected abstract List<Long> getActors(ExecutionContext context);
}
