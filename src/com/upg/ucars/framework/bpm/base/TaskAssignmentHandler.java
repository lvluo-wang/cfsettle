package com.upg.ucars.framework.bpm.base;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;
import org.jbpm.taskmgmt.exe.TaskActor;
/**
 * 任务分配
 * 
 * @author shentuwy
 */
public abstract class TaskAssignmentHandler implements AssignmentHandler {

	private static final long serialVersionUID = 8852465471295035182L;
	
	public void assign(Assignable assign, ExecutionContext context)
			throws Exception {
		Long tid = context.getTaskInstance().getId();
		Long[] userIds = this.getTaskActor(context);
		
		for (int i = 0; i < userIds.length; i++) {
			TaskActor ta = new TaskActor(null,tid, userIds[i]);
			context.getJbpmContext().getSession().save(ta);
		}		 

	}
	/**
	 * 获取执行任务的用户ID集
	 * @param context 上下文
	 * @return 执行任务的userId集合[userIds]
	 */
	protected abstract Long[] getTaskActor(ExecutionContext context);
	
	
	
	
}