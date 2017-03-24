package com.upg.ucars.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
/**
 * 
 * 功能说明：TODO(系统访问第一页面过滤器，解决个别应用服务器不能正常使用web。xml中欢迎页面的配置问题)
 * @author shentuwy  
 * @date 2012-1-30 上午10:10:17 
 *
 */
public class WelcomePageFilter implements Filter {
	private String indexPage;
	public void destroy() {
		indexPage=null;

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest httpReq=(HttpServletRequest)arg0;
		HttpServletResponse httpResp=(HttpServletResponse)arg1;
		String contextPath=httpReq.getContextPath();
		String requestUrl=httpReq.getRequestURI();
		if(contextPath.equalsIgnoreCase(requestUrl)||(contextPath+"/").equalsIgnoreCase(requestUrl)){
			String url="";
			if(indexPage.startsWith("/")){
				url=contextPath+indexPage;
			}else{
				url=contextPath+"/"+indexPage;
			}
			httpResp.sendRedirect(url);
		}else{
			arg2.doFilter(arg0, arg1);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		indexPage="index.html";
		String page=arg0.getInitParameter("indexPage");
		if(StringUtils.isNotBlank(page)){
			indexPage=page;
		}
		
	}

}
