/*******************************************************************************
 * Leadmind Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Feb 28, 2009
 *******************************************************************************/


package com.upg.ucars.framework.bpm.base;

import java.util.Iterator;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

public class RemoveProcessTaskActionHandler  implements ActionHandler {

	private static final long serialVersionUID = 1L;

	public void execute(ExecutionContext executionContext) throws Exception {
		TaskMgmtInstance tmgi = executionContext.getProcessInstance().getTaskMgmtInstance();
		Iterator iterator = tmgi.getTaskInstances().iterator();
		while(iterator.hasNext()){
			TaskInstance ti = (TaskInstance)iterator.next();
			if (ti.isOpen()){
				ti.setSignalling(false);//令牌不再流转
				ti.cancel();//任务取消
			}
		}		
	}

}
