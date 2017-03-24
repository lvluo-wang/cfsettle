package com.upg.ucars.framework.security;


import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.PathMatcher;


/**
 * (权限管理器的实现)
 * 
 * @date 2011-1-13 上午11:20:40
 * @version V1.0
 */
public class SecurityManagerImpl implements ISecurityManager {
	/**
	 * 用户权限服务接口
	 */
	private ISecurityUserService securityUserService;
	/**
	 * 资源权限服务接口
	 */
	private ISecurityResourceService securityResourceService;
	/**
	 * 密码加密器
	 */
	private ISecurityPwdEncryptor securityPwdEncryptor;
	
	public ISecurityUserService getSecurityUserService() {
		return securityUserService;
	}

	public void setSecurityUserService(ISecurityUserService securityUserService) {
		this.securityUserService = securityUserService;
	}

	public ISecurityResourceService getSecurityResourceService() {
		return securityResourceService;
	}

	public void setSecurityResourceService(
			ISecurityResourceService securityResourceService) {
		this.securityResourceService = securityResourceService;
	}
	public ISecurityPwdEncryptor getSecurityPwdEncryptor() {
		return securityPwdEncryptor;
	}

	public void setSecurityPwdEncryptor(ISecurityPwdEncryptor securityPwdEncryptor) {
		this.securityPwdEncryptor = securityPwdEncryptor;
	}
	
	public Map<String,Set<Long>> getAllSecurityResource() {
		return securityResourceService.getAllSecurityResource();
	}
	

	


	public boolean isAuthenticated(HttpServletRequest httpRequest) {
		return securityUserService.isAuthenticated(httpRequest);
	}

	public boolean isAuthorized(String uri) {
		return securityResourceService.isAuthorized(uri);
	}

	public PathMatcher getPathMatcher() {
		return securityResourceService.getPathMatcher();
	}

	public ISecurityPwdEncryptor getPwdEncryptor() {
		return securityPwdEncryptor;
	}

	public void afterSessionRelease(Long userId) {
		securityUserService.afterSessionRelease(userId);
		
	}
}
