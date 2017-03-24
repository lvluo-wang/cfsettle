package com.upg.demo.loan.bpm;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.upg.demo.loan.core.loaninfo.LoanServiceFactory;
import com.upg.demo.mapping.loan.LoanInfo;
import com.upg.ucars.framework.bpm.base.BpmConstants;
/**
 * 修改流程实体状态
 * @author shentuwy
 */
public class EntityStateAction implements ActionHandler {

	private static final long serialVersionUID = 644914068441496550L;

	public void execute(ExecutionContext executionContext) throws Exception {
		LoanInfo info = (LoanInfo)executionContext.getContextInstance().getVariable(BpmConstants.VAR_ENTITY);
		String nodeName = executionContext.getNode().getName();
		if (nodeName.equals("accept"))
			info.setStatus(LoanInfo.STATUS_ACCEPT);
		else if (nodeName.equals("commonAudit"))
			info.setStatus(LoanInfo.STATUS_COMMON_AUDIT);
		else if (nodeName.equals("advancedAudit"))
			info.setStatus(LoanInfo.STATUS_ADVANCED_AUDIT);
		else if (nodeName.equals("waitPaymentResult"))
			info.setStatus(LoanInfo.STATUS_PAYMENT_RESULT);
		else if (nodeName.equals("end-state1"))
			info.setStatus(LoanInfo.STATUS_END);
		
		LoanServiceFactory.getLoanService().update(info);

	}

}
