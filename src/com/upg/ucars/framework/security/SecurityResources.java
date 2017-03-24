/*
 * 源程序名称: SecurityResources.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.framework.security;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.util.PathMatcher;

import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.util.SourceTemplate;

/**
 * 
 * 功能说明：TODO(权限资源)
 * @author shentuwy  
 * @date 2011-7-27 下午5:12:14 
 *
 */
public class SecurityResources {
	private static Map<String,Set<Long>> securityResources=new HashMap<String,Set<Long>>();
	/**
	 * 初始化权限资源
	 * @param map 权限资源
	 */
	public static synchronized void initResources(){
		ISecurityManager securityManager=SourceTemplate.getBean(ISecurityManager.class,BeanNameConstants.SECURITY_MANAGER_BEAN);
		if(securityManager!=null){
			securityResources=securityManager.getAllSecurityResource();
		}
	}
	/**
	 * 获取访问指定资源所需的角色集合，满足一个即可
	 * @param res 资源
	 * @return 角色集合
	 */
	public static Set<Long> getNeededRoles(String res){
		PathMatcher pm=SecurityFactory.getSecurityManager().getPathMatcher();
		Set<String> allRes=securityResources.keySet();
		for(String p:allRes){
			if(pm.match(p+"*", res)){
				return securityResources.get(p);
			}
		}
		return null;
	}
	/**
	 * 获取所有的权限资源
	 * @return 权限集合
	 */
	public static Set<String> getAllPrivilege(){
		return securityResources.keySet();
	}
	/**
	 * 更新权限资源与角色的绑定关系
	 * @param resources 资源集合
	 * @param roleId 角色
	 */
	public static synchronized void updateResourcesSecurity(Set<String> resources,Long roleId){
		Set<String> resSet=securityResources.keySet();
		for(String res:resSet){
			Set<Long> roleList=securityResources.get(res);
			if(resources.contains(res)){
				//如果待处理权限集合中包含此权限
				if(!roleList.contains(roleId)){
					//如果以绑定的角色中不包含待处理角色，则将待处理角色加入到绑定中
					roleList.add(roleId);
				}
			}else{
				//如果待处理权限集合中没有此权限，但此权限绑定的角色却包含待处理角色
				if(roleList.contains(roleId)){
					roleList.remove(roleId);
				}
			}
		}
	}
	/**
	 * 更新单个资源与角色集合的绑定关系
	 * @param res 资源
	 * @param roleIds 角色集合
	 */
	public static synchronized void updateResourcesSecurity(String res,Set<Long> roleIds){
		if(securityResources.containsKey(res)){
			securityResources.get(res).addAll(roleIds);
		}else{
			securityResources.put(res, roleIds);
		}
	}
	/**
	 * 添加一个新的权限资源，默认赋予一个空的角色绑定
	 * @param secsrc 权限资源
	 */
	public static synchronized void addSecurityResource(String secsrc){
		Set<String> resSet=securityResources.keySet();
		if(!resSet.contains(secsrc)){
			securityResources.put(secsrc, new HashSet<Long>(1));
		}
	}
}
