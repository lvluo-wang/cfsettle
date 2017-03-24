package com.upg.finance.xhh.interceptor;

import java.util.Calendar;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("serial")
public class SpendTimeInterceptor extends AbstractInterceptor {

	private static final Logger	LOG	= Logger.getLogger(SpendTimeInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Long start = Calendar.getInstance().getTimeInMillis();
		String methodName = invocation.getProxy().getMethod();
		String result = invocation.invoke();
		Long end = Calendar.getInstance().getTimeInMillis();
		LOG.info(methodName + ",speed time:" + (end - start) + "ms;");
		return result;
	}

}
