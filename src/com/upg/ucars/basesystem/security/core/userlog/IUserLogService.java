package com.upg.ucars.basesystem.security.core.userlog;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.mapping.basesystem.security.UserActivityLog;

/**
 * 
 * IUserLogService
 *
 * @author shentuwy
 *
 */
public interface IUserLogService extends IBaseService {
	
	/** 登陆信息 */
	public static final String MESSAGE_LOGIN = "登陆";
	/** 退出信息 */
	public static final String MESSAGE_LOGOUT = "退出";
	
	
	/**
	 * 
	 * 增加用户活动日志
	 *
	 * @param message
	 */
	public void addUserLog(String message);
	
	/**
	 * 添加用户活动日志
	 * @param log	用户活动日志
	 * @return 记录ID
	 */
	public Long addUserLog(UserActivityLog log);
	/**
	 * 删除用户活动日志
	 * @param log	用户活动日志
	 */
	public void delUserLog(UserActivityLog log);
	/**
	 * 删除用户活动日志
	 * @param logId	日志主键
	 */
	public void delUserLog(Long logId);
	/**
	 * 删除用户活动日志
	 * @param logs	List<UserActivityLog> 日志列表
	 */
	public void delUserLog(List<UserActivityLog> logs);
	/**
	 * 通过主键ID获得用户活动日志
	 * @param pkId	主键ID
	 * @return	UserActivityLog
	 */
	public UserActivityLog getUserLog(Long pkId);
	/**
	 * 分页查询
	 * @param log	查询bean
	 * @param page	分页信息
	 * @return
	 */
	public List<UserActivityLog> findUserLogByPage(UserActivityLog log, Page page);

}
