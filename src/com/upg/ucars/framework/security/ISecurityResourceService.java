package com.upg.ucars.framework.security;

import java.util.Map;
import java.util.Set;

import org.springframework.util.PathMatcher;

/**
 * (资源权限服务接口)
 * 
 * @date 2011-1-13 下午03:38:29
 * @version V1.0
 */
public interface ISecurityResourceService {
	/**
	 * (获取所有需要进行权限控制的资源及其对应的角色集合)
	 * @return  Map<String,List<Long>> String 资源，List<Long> 角色集合
	 */
	public Map<String, Set<Long>> getAllSecurityResource();
	/**
	 * (指定的资源是否已授权访问)
	 * @param uri 资源
	 * @return 是否可以访问
	 */
	public boolean isAuthorized(String uri);
	/**
	 * 获取资源解析器
	 * @return 资源解析器
	 */
	public PathMatcher getPathMatcher();
}
