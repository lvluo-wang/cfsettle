/*
 * 源程序名称: SecurityFactory.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.framework.security;

import com.upg.ucars.util.SourceTemplate;
/**
 * 
 * 功能说明：TODO(权限管理器工厂)
 * @author shentuwy  
 * @date 2012-1-30 上午10:37:26 
 *
 */
public class SecurityFactory {
	public static ISecurityManager getSecurityManager(){
		ISecurityManager security=null;
		security=(ISecurityManager)SourceTemplate.getBean(SecurityConstants.SECURITY_MANAGER_BEAN);
		return security;
	}
}
