package com.upg.ucars.framework.security;



import javax.servlet.http.HttpServletRequest;


/**
 * (用户权限接口)
 * 
 * @date 2011-1-13 下午03:39:22
 * @version V1.0
 */
public interface ISecurityUserService {
	/**
	 * 是否通过身份认证
	 * @param httpRequest http请求
	 * @return 是否通过身份认证
	 */
	public boolean isAuthenticated(HttpServletRequest httpRequest);
	/**
	 * session失效之后
	 * @param userId 用户id
	 */
	public void afterSessionRelease(Long userId);

}
