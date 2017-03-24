package com.upg.ucars.framework.bpm.base;

import java.util.List;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.upg.ucars.constant.CommonConst;

/**
 * 
 * DefaultTaskAssignActionHandler.java
 * 
 * @author shentuwy
 * @date 2012-7-25
 * 
 */
public class DefaultTaskAssignActionHandler implements ActionHandler {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1002060727477612812L;

	protected static Logger		log					= LoggerFactory.getLogger(DefaultTaskAssignActionHandler.class);

	public void execute(ExecutionContext context) throws Exception {
		List<Long> processUserIds = getTaskProcessUserIds(context);
		if (processUserIds != null && !processUserIds.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (Long userId : processUserIds) {
				sb.append(userId).append(CommonConst.COMMA);
			}
			context.getTaskInstance().setPreActor(sb.toString());
		} else {
			prepareExecution(context);
			Long nextUserId = getTaskProcessUserId(context);
			if (nextUserId == null) {
				nextUserId = (Long) context.getProcessInstance().getContextInstance()
						.getVariable(BpmConstants.VAR_NEXT_USER_ID);
			}
			if (log.isInfoEnabled()) {
				log.info("processinstanceid=" + context.getProcessInstance().getId() + ",node="
						+ context.getToken().getNode().getName() + ",nextUserId=" + String.valueOf(nextUserId));
			}
			if (nextUserId != null) {
				context.getTaskInstance().setActorId(nextUserId.toString());
				context.getTaskInstance().setPreActor(nextUserId.toString() + CommonConst.COMMA);
				context.getContextInstance().deleteVariable(BpmConstants.VAR_NEXT_USER_ID);
			}
		}
	}

	protected void prepareExecution(ExecutionContext context) {

	}

	public Long getTaskProcessUserId(ExecutionContext context) {
		return null;
	}

	protected List<Long> getTaskProcessUserIds(ExecutionContext context) {
		return null;
	}

}
