package com.upg.ucars.framework.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

@SuppressWarnings("serial")
public class ExposeServiceInterceptor extends AbstractInterceptor {

	private static final Logger	LOG	= Logger.getLogger(ExposeServiceInterceptor.class);

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// 记录日志
		HttpServletRequest request = ServletActionContext.getRequest();
		String url = request.getRequestURL().toString();
		String queryString = request.getQueryString();
		if (StringUtils.isNotBlank(queryString)) {
			url += "?" + queryString;
		}
		Map<Object, Object> parametersMap = new HashMap<Object, Object>();
		Enumeration<?> en = null;
		for (en = request.getParameterNames(); en.hasMoreElements();) {
			Object name = en.nextElement();
			Object value = request.getParameter((String) name);
			parametersMap.put(name, value);
		}
		LOG.info("url=" + url + ",parameters=" + parametersMap);
		// 认证
		return invocation.invoke();
	}

}
