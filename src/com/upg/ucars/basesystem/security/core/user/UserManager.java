package com.upg.ucars.basesystem.security.core.user;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.model.security.UserLogonInfo;

/**
 * 用户管理类
 * 
 * @author shentuwy
 *
 */
public final class UserManager {

	/**
	 * 
	 * 当前登录者是不是指定的类型
	 *
	 * @param userType
	 * @return
	 */
	private static boolean isSpecialUserType(String userType){
		boolean ret = false;
		UserLogonInfo uli = SessionTool.getUserLogonInfo();
		if( uli != null ){
			ret = StringUtils.equals(uli.getUserType(),userType);
		}
		return ret;
	}
	/**
	 * 
	 * 当前登录者是不是实施人员
	 *
	 * @return
	 */
	public static boolean isImplementation() {
		return isSpecialUserType(Buser.TYPE_IMPLEMENTATION);
	}
	/**
	 * 
	 * 当前登录者是不是saas维护人员
	 *
	 * @return
	 */
	public static boolean isSaasMaintenance() {
		return isSpecialUserType(Buser.TYPE_SAAS_MAINTENANCE);
	}
	/**
	 * 
	 * 当前登录者是不是saas管理员
	 *
	 * @return
	 */
	public static boolean isSaasManager() {
		return isSpecialUserType(Buser.TYPE_SAAS_MANAGER);
	}
	/**
	 * 
	 * 当前登录者是不是机构总部管理员
	 *
	 * @return
	 */
	public static boolean isBrchGlobalManager() {
		return isSpecialUserType(Buser.TYPE_BRCH_GLOBAL_MANAGER);
	}
	/**
	 * 
	 * 当前登录者是不是结构管理员
	 *
	 * @return
	 */
	public static boolean isBrchLocalManager() {
		return isSpecialUserType(Buser.TYPE_BRCH_LOCAL_MANAGER);
	}
	/**
	 * 
	 * 当前登录者是不是普通用户
	 *
	 * @return
	 */
	public static boolean isBrchNormalUser() {
		return isSpecialUserType(Buser.TYPE_BRCH_NOMAL_USER);
	}
	
	
	
}
