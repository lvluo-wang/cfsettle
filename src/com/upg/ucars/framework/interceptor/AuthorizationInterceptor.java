package com.upg.ucars.framework.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.util.PathMatcher;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.RightsException;
import com.upg.ucars.framework.security.ISecurityManager;
import com.upg.ucars.util.SourceTemplate;
import com.upg.ucars.util.StringUtil;

/**

 * 访问授权拦截器:
 * 基于web的请求最终都成为对系统某个功能的调用,考虑到权限控制不能跨事务,故对action的方法进行授权检查.
 * 在授权检查时,可能抛出安全相关的异常,故此拦截器在配置时,应放在异常拦截器的后面
 * 
 * @date 2011-1-12 下午03:46:04
 * @version V1.0
 */
public class AuthorizationInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	private static final String URL_SPLIT=",";
	public static final String OFF="OFF";
	public static final String ON="ON";
	ISecurityManager securityManager;
	
	private ISecurityManager ebankSecurityManager;;
	/**
	 * 拦截器生效开关，OFF 为关闭，其他都为开启
	 */
	private String onOff;
	private String debug;
	/**
	 * 绿色通道，配置不需要拦截器检查的action方法
	 */
	private String greenChannel;
	/**
	 * 绿色列表
	 */
	private List<String> greenList=new ArrayList<String>();
	/**
	 * url校验匹配器，支撑
	 * ?：单个字符
	 * *：多个字符，不能跨目录/
	 * **：多个字符，且跨目录
	 */
	private PathMatcher pathMatcher;
	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String userAgent = request.getHeader("user-agent");
		if (StringUtils.isNotBlank(userAgent)) {
			userAgent = userAgent.toLowerCase();
			if (userAgent.contains("android") || userAgent.contains("iphone") ) {
				request.setAttribute("mobile", true);
				request.setAttribute("tabStyle", "width:700px;");
			} else if (userAgent.contains("ipad")) {
				request.setAttribute("ipad", true);
				request.setAttribute("tabStyle", "width:750px;");
			}
		}
		
		
		/**
		 * 如果拦截器配置为关闭状态，则不拦截处理（OFF 为关闭，其他都为开启）
		 */
		if(isInterceptorClosed()){
			return arg0.invoke();
		}
		Object actionObj=arg0.getAction();
		/**
		 * 系统自定义的action都是从BaseAction继承而来，所以针对BaseAction就可以拦截所以自定义的Action
		 */
		if(actionObj instanceof BaseAction){
//			BaseAction action=(BaseAction)actionObj;
//			HttpServletRequest request=action.getHttpRequest();
			String uri=request.getServletPath()+(request.getPathInfo()==null?"":request.getPathInfo());
			/**
			 * 如果与绿色通道的配置项匹配，则放行
			 */
			if(isGreenChannel(uri)){
				return arg0.invoke();
			}
			
			
			//如果与用户已授权访问的资源相匹配，则放行
			if(securityManager.isAuthorized(uri)){	
				return arg0.invoke();
			}
			
			
			/**
			 * 如果上述授权检查都没有得到放行，则抛出没有权限访问的安全异常
			 */
			if(OFF.equals(debug))
				uri="";
			ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_NO_RIGHT_ERROR, uri);
		}
		return arg0.invoke();

		
	}
	@Override
	public void init() {
		super.init();
		securityManager=SourceTemplate.getBean(ISecurityManager.class, BeanNameConstants.SECURITY_MANAGER_BEAN);
		pathMatcher=securityManager.getPathMatcher();
	}
	
	@Override
	public void destroy() {
		securityManager=null;
		pathMatcher=null;
		super.destroy();
	}
	/**
	 * 
	 * @Description: TODO(判断拦截器是否为关闭状态)
	 * @return  boolean
	 */
	private boolean isInterceptorClosed(){
		return OFF.equals(onOff);
	}
	/**
	 * 
	 * @Description: TODO(根据绿色通道配置项与请求的资源进行匹配)
	 * @param uri 请求的资源
	 * @return  boolean 返回匹配结果
	 */
	private boolean isGreenChannel(String uri){
		for(String pattern:greenList){
			if(pathMatcher.match(pattern, uri)){
				return true;
			}
		}
		return false;
	}
	

	public String getOnOff() {
		return onOff;
	}
	public void setOnOff(String onOff) {
		this.onOff = onOff;
	}
	public String getGreenChannel() {
		return greenChannel;
	}
	public void setGreenChannel(String greenChannel) {
		this.greenChannel = greenChannel;
		greenList.clear();
		if(!StringUtil.isEmpty(greenChannel)){
			String[] array=greenChannel.split(URL_SPLIT);
			for(int i=0;i<array.length;i++){
				greenList.add(array[i]);
			}
		}
	}
	public String getDebug() {
		return debug;
	}
	public void setDebug(String debug) {
		this.debug = debug;
	}
	public void setEbankSecurityManager(ISecurityManager ebankSecurityManager) {
		this.ebankSecurityManager = ebankSecurityManager;
	}
	
	
	
}
