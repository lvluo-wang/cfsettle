package com.upg.ucars.basesystem.security.core.userlog;

import java.util.Calendar;
import java.util.List;

import com.upg.ucars.basesystem.security.core.branch.IBranchService;
import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.UserActivityLog;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.SourceTemplate;

/**
 * 
 * UserLogServiceImpl
 *
 * @author shentuwy
 *
 */
public class UserLogServiceImpl extends BaseService implements IUserLogService {
	
	/** 日志DAO */
	private IUserLogDAO logDao;
	
	public void addUserLog(String message){
		UserLogonInfo uli = SessionTool.getUserLogonInfo();
		if( uli != null ){
			UserActivityLog ual = new UserActivityLog();
			ual.setBrchId(uli.getBranchId());
			ual.setLogInfo(message);
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MILLISECOND, 0);
			ual.setLogTm(cal.getTime());
			ual.setMiNo(uli.getMiNo());
			ual.setSysUserId(uli.getSysUserId());
			logDao.save(ual);
		}
	}
	
	public Long addUserLog(UserActivityLog log) {
		return logDao.save(log);
	}
	
	public void delUserLog(UserActivityLog log) {
		logDao.delete(log);
	}
	
	public void delUserLog(Long logId) {
		logDao.delete(logId);
	}
	
	public void editUserLog(UserActivityLog log) {
		logDao.update(log);
	}
	
	public List<UserActivityLog> findUserLogByPage(UserActivityLog log, Page page) {
		Branch branch = null;
		if( log != null && log.getBrchId() != null ){
			branch = SourceTemplate.getBean(IBranchService.class, BeanNameConstants.BRANCH_SERVICE).getBranchByBrchId(log.getBrchId());
		}
		return logDao.findUserLogByPage(log,branch, page);
	}

	
	public UserActivityLog getUserLog(Long pkId) {
		return logDao.get(pkId);
	}
	
	public void delUserLog(List<UserActivityLog> logs) {
		logDao.delAll(logs);
	}
	
	public void setLogDao(IUserLogDAO logDao) {
		this.logDao = logDao;
	}

}
