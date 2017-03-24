/*
 * 源程序名称: SecurityPwdHandler.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 技术平台：XCARS
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.framework.security;

import com.upg.ucars.util.DigestUtil;
/**
 * 
 * 功能说明：MD%加密器，实现权限加密接口
 * @author shentuwy  
 * @date 2011-8-3 下午8:36:11 
 *
 */
public class MD5Encryptor implements ISecurityPwdEncryptor {

	public String encryption(String pwd) {

		return DigestUtil.getMD5(pwd);
	}

}
