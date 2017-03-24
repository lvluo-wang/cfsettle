package com.upg.ucars.basesystem.security.action;

import java.util.ArrayList;
import java.util.List;

import com.upg.ucars.basesystem.security.core.branch.IBranchService;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.basesystem.security.core.userlog.IUserLogService;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.UserActivityLog;
/**
 * 
 * 功能说明：用户活动日志action
 * @author shentuwy  
 * @author shentuwy
 * @date 2011-8-4 上午8:53:46 
 *
 */
public class UserLogAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8586033143136867330L;
	
	/** 用户日志 */
	private UserActivityLog userLog;
	
	/** 机构服务 */
	private IBranchService branchService;

	/** 用户日志服务 */
	private IUserLogService userLogService;
	/** 用户操作服务 */
	private IUserService userService;
	private String member_branch="1";
	/**
	 * 主页面
	 */
	public String list() {
		String userType=SessionTool.getUserLogonInfo().getUserType();
		if(Buser.TYPE_SAAS_MANAGER.equals(userType)||Buser.TYPE_SAAS_MAINTENANCE.equals(userType)){
			member_branch="1";
		}else if(Buser.TYPE_IMPLEMENTATION.equals(userType)){
			member_branch="0";
		}else{
			member_branch="2";
		}
		return MAIN;
	}
	/**
	 * 删除
	 */
	public void del() {
		List<Long> idList = getIdList();
		if( idList != null && idList.size() > 0 ){
			List<UserActivityLog> delList = new ArrayList<UserActivityLog>();
			for(Long id : idList) {
				delList.add(userLogService.getUserLog(id));
			}
			userLogService.delUserLog(delList);
		}
	}
	
	/**
	 * 查询
	 */
	public String query() {
		
		if(userLog.getBrchId()==null){
			Long myBrchId=SessionTool.getUserLogonInfo().getBranchId();
			if(myBrchId!=null){
				userLog.setBrchId(myBrchId);
			}
		}
		List<UserActivityLog> logs = userLogService.findUserLogByPage(userLog, getPg());
		for(UserActivityLog info : logs) {
			Buser u = userService.getUserById(info.getSysUserId());
			if(u != null) {
				info.setSysUserName(u.getUserName());
			}
			
			if(info.getBrchId() != null) {
				Branch brch = branchService.getBranchByBrchId(info.getBrchId());
				if(brch != null) {
					info.setBrchName(brch.getBrchName());
				}
			}
		}
		return setDatagridInputStreamData(logs, getPg());
	}

	
	public void setUserLog(UserActivityLog userLog) {
		this.userLog = userLog;
	}
	public UserActivityLog getUserLog() {
		return userLog;
	}
	public void setUserLogService(IUserLogService userLogService) {
		this.userLogService = userLogService;
	}
	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public String getMember_branch() {
		return member_branch;
	}
	public void setMember_branch(String member_branch) {
		this.member_branch = member_branch;
	}
	

}
