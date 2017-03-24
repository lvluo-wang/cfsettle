package com.upg.ucars.constant;


/**
 * 
 * session中key的常量类，程序中所有用到session存储的，统一在这里定义key，以免产生不可预知的冲突
 * 
 * @author shentuwy
 * @date 2012-8-9
 *
 */
public class SessionKeyConst {
	/**
	 * 用户登录信息对象UserLogonInfo
	 */
	public static final String USER_LOGON_INFO="UserLogonInfo";
	/**
	 * 消息发布器
	 */
	public static final String SYS_NEWS_PUBLISHER="SysNewsPublisher";
	/**
	 * 异常管理器
	 */
	public static final String EXCEPTION_MANAGER="exceptionManager";
	/**
	 * 当前会话中的Local对象
	 */
	public static final String SESSION_LOCAL="WW_TRANS_I18N_LOCALE";
	/**
	 * action 异步锁标记,用于控制同一个session中的action请求同步处理化
	 */
	public static final String ASYNC_ACTION_LOCK="asyncActionLock";
	
	/**
	 * 系统运行状态
	 */
	public static final String SYS_RUN_STATUS="sysRunStatus";
	
}
