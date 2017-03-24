package com.upg.ucars.framework.interceptor;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.PreResultListener;

/**
 * 
 * ChangeActionResult
 *
 * @author shentuwy
 *
 */
public class ChangeActionResult extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4162622004336522476L;
	
	/** resultCode */
	private static final String WRAP_RESULT_CODE = "wrap";
	

	public String intercept(ActionInvocation invocation) throws Exception {
		String wrap = ServletActionContext.getRequest().getParameter("wrap");
		if( StringUtils.equals("1", wrap) ){
			invocation.addPreResultListener(new PreResultListener() {
				public void beforeResult(ActionInvocation ai, String code) {
					if( StringUtils.isNotBlank(ai.getResultCode()) ){
						Map<String,ResultConfig> configParam = ai.getProxy().getConfig().getResults();
						if( configParam != null && configParam.get(code) != null  ){
							String resultLocation = configParam.get(code).getParams().get("location");
							if( StringUtils.isNotBlank(resultLocation) ){
								 ServletActionContext.getRequest().setAttribute("resultLocation", resultLocation);
								ai.setResultCode(WRAP_RESULT_CODE);
							}
						}
					}
				}
			});
		}
		return invocation.invoke();
	}

}
