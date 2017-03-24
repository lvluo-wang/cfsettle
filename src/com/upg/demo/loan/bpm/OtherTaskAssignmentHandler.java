package com.upg.demo.loan.bpm;

import org.jbpm.graph.exe.ExecutionContext;

import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.bpm.base.TaskAssignmentHandler;
/**
 * 任务分配
 * 
 * @author shentuwy
 */
public class OtherTaskAssignmentHandler extends TaskAssignmentHandler {

	private static final long serialVersionUID = 2430370210846936696L;

	@Override
	protected Long[] getTaskActor(ExecutionContext context) {
		
		return new Long[]{SessionTool.getUserLogonInfo().getSysUserId(), 1L};
	}
	
	
}