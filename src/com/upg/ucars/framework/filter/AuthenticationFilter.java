package com.upg.ucars.framework.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.constant.SessionKeyConst;
import com.upg.ucars.framework.security.ISecurityManager;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.LogUtil;
import com.upg.ucars.util.SourceTemplate;

/**
 * 身份验证过滤器
 * 验证失败禁止访问系统，并跳转到失败页面
 * @author shentuwy
 * @date 2011-1-11 下午04:27:42
 * @version V1.0
 */
public class AuthenticationFilter implements Filter {
	
	private static final String AUTHENTICATION_FAILED="AUTHENTICATION_FAILED";
	private static final String EBANK_AUTHENTICATION_FAILED="EBANK_AUTHENTICATION_FAILED";
	private static final String AUTHENTICATION_FAILED_AJAX="AUTHENTICATION_FAILED_AJAX";
	private static final String EBANK_AUTHENTICATION_FAILED_AJAX="EBANK_AUTHENTICATION_FAILED_AJAX";
	private static final String ON_OFF="ON_OFF";
	private static final String GREEN_CHANNEL="GREEN_CHANNEL";
	private static final String URL_SPLIT=",";
	private static final String OFF="OFF";
	private static final String ON="ON";
	private static final String ACCESS_LOG="ACCESS_LOG";
	private static final String LOG_KEY="LOG_KEY";
	/** 普通请求失败的默认页面 */
	private static final String DEFAULT_FAILED_PAGE = "/";
	
	/** 网银普通请求失败的默认页面 */
	private static final String DEFAULT_EBANK_FAILED_PAGE = "/ebank";
	
	/** 普通请求认证失败的页面 */
	private String failedPage;
	
	/** 网银普通请求认证失败的页面 */
	private String ebankFailedPage;
	
	/** ajax请求认证失败的页面 */
	private String failedPageAjax;
	
	/** 网银ajax请求认证失败的页面 */
	private String ebankFailedPageAjax;
	/**
	 * 过滤器生效开关
	 */
	private String onOff;
	/**
	 * 访问日志
	 */
	private String accessLog;
	private List<String> logKeys=new ArrayList<String>(0);
	/**
	 * 绿色通道配置，配置不需要过滤器验证的地址关键字列表
	 */
	private List<String> greenChannel=new ArrayList<String>(0);
	
	/** 静态资源 */
	private List<String> staticResources = new ArrayList<String>(0);
	/**
	 * url校验匹配器，支撑
	 * ?：单个字符
	 * *：多个字符，不能跨目录/
	 * **：多个字符，且跨目录
	 */
	private PathMatcher pathMatcher=new AntPathMatcher();
	
	/**
	 * 
	 * 获取初始化参数
	 * 
	 * @param config
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	private String getInitParameter(FilterConfig config,String key,String defaultValue){
		String ret = null;
		if( config != null && key != null ){
			ret = config.getInitParameter(key);
		}
		if( ret == null ){
			ret = defaultValue;
		}
		return ret;
	}
	
	public void init(FilterConfig config) throws ServletException {
		failedPage= getInitParameter(config,AUTHENTICATION_FAILED, DEFAULT_FAILED_PAGE) ;
		ebankFailedPage = getInitParameter(config, EBANK_AUTHENTICATION_FAILED, DEFAULT_EBANK_FAILED_PAGE);
		failedPageAjax = getInitParameter(config, AUTHENTICATION_FAILED_AJAX, null);
		ebankFailedPageAjax = getInitParameter(config, EBANK_AUTHENTICATION_FAILED_AJAX, null);
		onOff = getInitParameter(config,ON_OFF,null);
		//访问日志
		accessLog=getInitParameter(config,ACCESS_LOG,null);
		//日志关键字
		String keys=getInitParameter(config,LOG_KEY,null);
		if(StringUtils.isNotBlank(keys)){
			String[] temps=keys.split(URL_SPLIT);
			if(temps!=null&&temps.length>0){
				for(String key:temps){
					logKeys.add(key);
				}
			}
		}
		//绿色通道
		String temp= getInitParameter(config,GREEN_CHANNEL,null);
		if(StringUtils.isNotBlank(temp)){
			String[] temps=temp.split(URL_SPLIT);
			if(temps!=null&&temps.length>0){
				for(int i=0;i<temps.length;i++){
					greenChannel.add(temps[i].trim());
				}
			}
		}
		//静态资源
		staticResources.add("/**/*.css");
		staticResources.add("/**/*.js");
		staticResources.add("/**/*.gif");
		staticResources.add("/**/*.png");
		staticResources.add("/**/*.jpg");
		
	}
	
	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq=(HttpServletRequest)request;
		HttpServletResponse httpResp=(HttpServletResponse)response;
		accessLog(httpReq);
		if(isFilterClosed()||isStaticResource(httpReq)||isGreenChannel(httpReq)){
			chain.doFilter(request, response);
		}else{
			if(SourceTemplate.getBean(ISecurityManager.class, BeanNameConstants.SECURITY_MANAGER_BEAN).isAuthenticated(httpReq)){
				chain.doFilter(request, response);
			}else{
				gotoFailedPage(httpReq,httpResp);
			}
		}
		
	}
	
	
	/**
	 * 
	 * 判断过滤器是否已关闭，没有配置则默认开启
	 * 
	 * @return  boolean
	 */
	private boolean isFilterClosed(){
		return OFF.equals(onOff);
	}
	/**
	 * 
	 * 判断请求是否包含绿色通道中配置的关键字,使用ant url匹配器，
	 * ?:单个字符、*：多个目录内的字符、**：多个包括目录的字符
	 * @param request
	 * @return  boolean
	 */
	private boolean isGreenChannel(HttpServletRequest request){
		String url=request.getServletPath()+(request.getPathInfo()==null?"":request.getPathInfo());
		return isMatchPath(greenChannel, url);
	}
	private void accessLog(HttpServletRequest request){
		if(ON.equals(accessLog)){
			String url=request.getServletPath()+(request.getPathInfo()==null?"":request.getPathInfo());
			if(isMatchPath(logKeys, url)){
				UserLogonInfo uinfo=(UserLogonInfo)request.getSession().getAttribute(SessionKeyConst.USER_LOGON_INFO);
				String ip=getIP(request);
				String user="";
				if(uinfo!=null){
					user+=uinfo.getUserName();
					user+="["+uinfo.getUserNo()+"]";
				}
				LogUtil.getUserLog().error(ip+" "+user+"\""+url+"\"");
			}
		}
		
	}
	private String getIP(HttpServletRequest request){
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getRemoteAddr();
		return ip;
	}
	/**
	 * <pre>
	 * ?：单个字符
	 * *：多个字符，不能跨目录
	 * **：多个字符，且跨目录
	 *	</pre>哦
	 * @param list 模式列表
	 * @param path 匹配的字符串
	 * @return
	 */
	private boolean isMatchPath(List<String> list,String path){
		boolean ret = false;
		if( list != null && list.size() > 0 && StringUtils.isNotBlank(path) ){
			for( String pattern : list ){
				if( pathMatcher.match(pattern, path) ){
					ret = true;
					break;
				}
			}
		}
		return ret;
	}
	
	/**
	 * 
	 * 跳转到身份验证失败的页面
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void gotoFailedPage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String reqType=request.getHeader("X-Requested-With");
		if(StringUtils.equalsIgnoreCase("XMLHTTPREQUEST", reqType)){
			response.sendRedirect(request.getContextPath()+failedPageAjax+"?ajax_request=async&flag="+Math.random());
		}else{
			response.sendRedirect(request.getContextPath()+ failedPage);
		}
	}
	
	
	/**
	 * 判断是否为静态资源
	 * @param request
	 * @return
	 */
	private boolean isStaticResource(HttpServletRequest request){
		String url=request.getServletPath()+(request.getPathInfo()==null?"":request.getPathInfo());
		return isMatchPath(staticResources, url);
	}

}
