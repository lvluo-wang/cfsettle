package com.upg.demo.loan.bpm;

import org.jbpm.graph.exe.ExecutionContext;

import com.upg.demo.loan.core.loaninfo.LoanServiceFactory;
import com.upg.demo.mapping.loan.LoanInfo;
import com.upg.ucars.framework.bpm.base.AutoNodeActionHandler;
import com.upg.ucars.framework.bpm.base.BpmConstants;

public class PaymentActionHandler extends AutoNodeActionHandler {

	private static final long serialVersionUID = 414860730185508860L;

	@Override
	public String executeAction(ExecutionContext context) throws Exception {
		LoanInfo loanInfo = (LoanInfo)context.getContextInstance().getVariable(BpmConstants.VAR_ENTITY);
		LoanServiceFactory.getLoanService().payment(loanInfo);
		
		return null;
	}
}
