package com.upg.ucars.framework.result;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.dispatcher.StreamResult;

import com.opensymphony.xwork2.ActionInvocation;

/**
 * 
 * UcarsStreamResult.java
 * 
 * @author shentuwy
 * @date 2012-8-23
 * 
 */

public class DownLoadStreamResult extends StreamResult {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3806249048431057147L;

	@Override
	protected void doExecute(String finalLocation, ActionInvocation invocation)
			throws Exception {
		try {
			super.doExecute(finalLocation, invocation);
		} catch (Exception t) {
			if (StringUtils.equals(t.getClass().getName(),
					"org.apache.catalina.connector.ClientAbortException")) {
				//ignore
				
			} else {
				throw t;
			}
		}
	}

}
