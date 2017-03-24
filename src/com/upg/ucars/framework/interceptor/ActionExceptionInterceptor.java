package com.upg.ucars.framework.interceptor;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.core.UserConcemedException;
import com.upg.ucars.util.JsonUtils;
/**
 * action异常拦截器，action调用产生的异常被拦截器捕获，
 * 根据异常类别判断，
 * 如果为用户关心异常（userConcemed）则使用异常定义时配置的信息，展现给用户
 * 如果为用户不关心异常(userNotConcemed)则使用统一的异常信息，展现给用户
 * 
 * @author shentuwy
 *
 */
public class ActionExceptionInterceptor extends AbstractInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4335679645935670107L;
	
	private static Logger log = LoggerFactory.getLogger(ActionExceptionInterceptor.class);
	
	
	/** 关闭 */
	private static final String OFF="OFF";
	/** 打开 */
	private static final String ON="ON";
	private String debug;
	
	public String getDebug() {
		return debug;
	}

	public void setDebug(String debug) {
		this.debug = debug;
	}
	
	private boolean isDebug(){
		return ON.equals(debug);
	}
	
	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		try{
			return arg0.invoke();
		}catch(Throwable e){
			String errMsg="";
			ActionSupport action=(ActionSupport)arg0.getAction();
			ActionContext context=arg0.getInvocationContext();
			HttpServletRequest request=(HttpServletRequest)context.get(StrutsStatics.HTTP_REQUEST);
			ExceptionManager.getSessionInstance(request).collectException(e);
			SessionTool.publishNews("<font color=red>"+action.getText("have_an_error")+"</font>");
			log.error(e.getMessage(),e);
			
			Throwable cause = e;
			while(cause != null && ! (cause instanceof UserConcemedException)){
				cause = cause.getCause();
			}
			
			if(cause instanceof UserConcemedException){
				errMsg=cause.getMessage();
			} else{
				errMsg=ExceptionManager.getCommonExceptionMessage();
			}
			action.addActionError(errMsg);
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("error", errMsg);
			
			context.getValueStack().set("errorStream", new ByteArrayInputStream(JsonUtils.objectToJsonString(map).getBytes("UTF-8")));
			
			String reqType=request.getHeader("X-Requested-With");
			if(reqType!=null){
				if("XMLHTTPREQUEST".equals(reqType.toUpperCase()) && request.getParameter("menuId") == null){
					return "asyncException";
				}
			}
			context.getValueStack().set("error", errMsg);
			if( isDebug() ){
				context.getValueStack().set("errorStack", e.getMessage());
			}
			return "syncException";
		}
	}

}
