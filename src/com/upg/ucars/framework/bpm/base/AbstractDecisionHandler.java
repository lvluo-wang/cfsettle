package com.upg.ucars.framework.bpm.base;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.upg.ucars.bpm.core.IUcarsProcessService;
import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.util.SourceTemplate;

public abstract class AbstractDecisionHandler implements DecisionHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2964423934586025057L;

	protected static final String DECISION_YES_NAME = "是";
	protected static final String DECISION_NO_NAME = "否";

	protected IUcarsProcessService ucarsProcessService;

	public String decide(ExecutionContext context) {

		ucarsProcessService = SourceTemplate.getBean(
				IUcarsProcessService.class,
				BeanNameConstants.UCARS_PROCESS_SERVICE);

		String result = doDecide(context);

		return result;
	}

	protected Object getEntity(ExecutionContext context) {
		return ucarsProcessService.getEntityById(context);
	}

	protected abstract String doDecide(ExecutionContext context);

}
