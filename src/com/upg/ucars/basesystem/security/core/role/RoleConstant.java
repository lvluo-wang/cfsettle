package com.upg.ucars.basesystem.security.core.role;

/**
 * 角色域常量定义
 * 
 * @author shentuwy
 * @date 2012-7-27
 * 
 */
public class RoleConstant {
	/** 角色类型-实施 */
	public static final String	ROLE_TYPE_APP		= "1";
	/** 角色类型-SAAS运维 */
	public static final String	ROLE_TYPE_SAAS_OPER	= "2";
	/** 角色类型-SAAS管理 */
	public static final String	ROLE_TYPE_SAAS_MNG	= "3";
	/** 角色类型-总部管理 */
	public static final String	ROLE_TYPE_CENTRE	= "4";
	/** 角色类型-机构管理 */
	public static final String	ROLE_TYPE_BRANCH	= "5";
	/** 角色类型-普通 */
	public static final String	ROLE_TYPE_COMMON	= "6";
	/** 角色类型-评委 */
	public static final String	ROLE_TYPE_JUDGMENT	= "7";

	/** 角色状态-未复核 */
	public static final String	ROLE_STATUS_UNCHECK	= "0";
	/** 角色状态-复核 */
	public static final String	ROLE_STATUS_CHECK	= "1";

}
