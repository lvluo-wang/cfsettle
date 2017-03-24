/*******************************************************************************
 * Leadmind Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Jan 11, 2009
 *******************************************************************************/


package com.upg.ucars.framework.bpm.base;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.AssignmentHandler;
import org.jbpm.taskmgmt.exe.Assignable;

import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ProcessException;
/**
 * 工作流审批路线审核任务分配处理类
 * 
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */
public class AuditRouteAssignmentHandler implements AssignmentHandler {

	private static final long serialVersionUID = 1L;

	public void assign(Assignable assignable, ExecutionContext executionContext) throws Exception {
		String auditTaskDescription = executionContext.getTaskInstance().getDescription();
		String auditTaskName = executionContext.getTaskInstance().getName();		
		Object obj = executionContext.getContextInstance().getVariableLocally(AuditRouteDecisionHandler.Val_Audit_Users, executionContext.getToken());
		String[] actors = (String[])obj;
		
		if (actors==null || actors.length==0){
			executionContext.getTaskInstance().end();
			//TODO 国际化
			throw ExceptionManager.getException(ProcessException.class, "BC_AUDIT_ROUTE_ERR0001", new String[]{auditTaskName+auditTaskDescription});
			
		}else{
			assignable.setPooledActors(actors);
		}
		//删除
		executionContext.getContextInstance().deleteVariable(AuditRouteDecisionHandler.Val_Audit_Users, executionContext.getToken());
		
	}

	
}
