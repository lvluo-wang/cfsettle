package com.upg.ucars.framework.security;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.PathMatcher;



/**
 * (权限管理器的接口，定义了权限管理器需要的功能规格)
 * 
 * @date 2011-1-12 下午08:00:08
 * @version V1.0
 */
public interface ISecurityManager {
	/**
	 * (获取所有需要进行权限控制的资源及与资源相对应的角色)
	 * @return  Map<String,List<Long>>
	 */
	public Map<String,Set<Long>> getAllSecurityResource();

	/**
	 * (当前请求是否已通过身份验证)
	 * @return  boolean
	 */
	public boolean isAuthenticated(HttpServletRequest httpRequest);
	/**
	 * 当前访问的资源是否已授权
	 * @param uri
	 * @return
	 */
	public boolean isAuthorized(String uri);
	/**
	 * 获取路径匹配器
	 * @return
	 */
	public PathMatcher getPathMatcher();
	/**
	 * 获取密码加密器
	 * @return
	 */
	public ISecurityPwdEncryptor getPwdEncryptor();
	
	/**
	 * session失效之后
	 * @param userId
	 */
	public void afterSessionRelease(Long userId);
	
}
