package com.upg.ucars.basesystem.security.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.upg.ucars.basesystem.security.core.sysfunc.ISysfuncService;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.basesystem.security.core.userlog.IUserLogService;
import com.upg.ucars.constant.SessionKeyConst;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.exception.AdapterRuntimeException;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.DateTimeUtil;

/**
 * 功能说明：登录action
 * 
 * @author shentuwy
 * @date 2012-7-3
 *
 */
public class LoginAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 54626616021142632L;
	
	/** 返回登录成功结果 */
	public static final String LOGIN_SUCCESS = "1";
	
	private static final String AFTER_LOGOUT="after_logout";
	private static final String TARGETPAGE="targetPage";
	private Buser user;
	private IUserService userService;
	private ISysfuncService sysfuncService;
	private String isEncrypt;
	private String userMenu;
	private String targetUrl;
	private String xtheme=ServletActionContext.getServletContext().getInitParameter("default_theme");
	/*
	 * 用户日志服务
	 */
	private IUserLogService userLogService;
	public String getXtheme() {
		return xtheme;
	}
	public void setXtheme(String xtheme) {
		this.xtheme = xtheme;
	}

	/**
	 * 登录处理
	 * @return
	 * @throws Exception
	 */
	public String logon()throws Exception{
		if(user==null){
			user=new Buser();
		}
		user.setLastLoginIP(SessionTool.getCurrentRequestIP());
		user.setLastLoginTm(DateTimeUtil.getNowDateTime());
		UserLogonInfo logonInfo = null;
		try {
			logonInfo = userService.logon(user,StringUtils.equals("1", isEncrypt) ? true : false);
		} catch (AdapterRuntimeException le) {
			throw le.getCause();
			/*if (ErrorCodeConst.LOGON_ERR_INVALID.equals(le.getErrCode())){
				Buser dbuser = userService.getUserByUserNoAndMiNo(user.getUserNo(), user.getMiNo());
				if (dbuser != null){
					if(Buser.TYPE_BRCH_GLOBAL_MANAGER.equals(dbuser.getUserType())||Buser.TYPE_BRCH_LOCAL_MANAGER.equals(dbuser.getUserType())||Buser.TYPE_BRCH_NOMAL_USER.equals(dbuser.getUserType())){
						
					}
				}
				
			}*/
		}
		
		if(logonInfo!=null){
			logonInfo.setTheme(xtheme);
			//踢掉废弃的session
			SessionTool.removeUserOldSession(logonInfo.getSysUserId());
			//用户登录信息保存到当前session中
			SessionTool.setAttribute(SessionKeyConst.USER_LOGON_INFO, logonInfo);
			SessionTool.bindingUserSession(logonInfo.getSysUserId());
			addCookie(SessionTool.getResponse(), "userNo",
					logonInfo.getUserNo(), Integer.MAX_VALUE);
		}
		userLogService.addUserLog(IUserLogService.MESSAGE_LOGIN);
		return getResultAndSetData(LOGIN_SUCCESS);
	}
	
	/**
	 * 跳转到登录页面
	 * 
	 * @return
	 */
	public String login(){
		return "login";
	}
	
	/**
	 * 跳转到登录页面
	 * @return
	 */
	public String loginPage(){
		return AFTER_LOGOUT;
	}
	public String success()throws Exception{
		//增加初始化用户菜单部分
		UserLogonInfo logonInfo=SessionTool.getUserLogonInfo();
		List<Map<String,Object>> menus=new ArrayList<Map<String,Object>>();
		List<Sysfunc> nodes=null;
		nodes=sysfuncService.getSubMenu(logonInfo,Sysfunc.ROOT_FUNC_ID);
		for(Sysfunc node:nodes){
			Map<String,Object> item=new HashMap<String,Object>();
			item.put("id", node.getFuncId());
			String funcName=node.getFuncName();
			String nameKey=node.getFuncNameKey();
			if(nameKey!=null&&nameKey.length()>0){
				String keyValue=getText(nameKey);
				if(!nameKey.equals(keyValue)){
					funcName=keyValue;
				}
			}
			item.put("text", funcName);
			menus.add(item);
		}
		xtheme=logonInfo.getTheme();
		userMenu= getJsonData(menus);
		getHttpResponse().setHeader("Pragma", "No-cache");
		getHttpResponse().setHeader("Cache-Control", "no-cache");
		getHttpResponse().setHeader("Cache-Control", "no-store");
		getHttpResponse().setDateHeader("Expires", 0);
		return MAIN;
	}
	public String targetPage()throws Exception{
		return TARGETPAGE;
	}
	/**
	 * 退出处理
	 * @return
	 * @throws Exception
	 */
	public String logout()throws Exception{
		userLogService.addUserLog(IUserLogService.MESSAGE_LOGOUT);
		UserLogonInfo logonInfo=SessionTool.getUserLogonInfo();
		if(logonInfo!=null){
			userService.offLine(logonInfo.getSysUserId());
			SessionTool.removeUserOldSession(logonInfo.getSysUserId());
			getHttpRequest().getSession().removeAttribute(SessionKeyConst.USER_LOGON_INFO);
		}
		return AFTER_LOGOUT;
	}
	
	
	
	public void noRights(){
		userService.noLogon();
	}
	public Buser getUser() {
		return user;
	}
	public void setUser(Buser user) {
		this.user = user;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public String getIsEncrypt() {
		return isEncrypt;
	}
	public void setIsEncrypt(String isEncrypt) {
		this.isEncrypt = isEncrypt;
	}
	public ISysfuncService getSysfuncService() {
		return sysfuncService;
	}
	public void setSysfuncService(ISysfuncService sysfuncService) {
		this.sysfuncService = sysfuncService;
	}
	public IUserService getUserService() {
		return userService;
	}
	public String getUserMenu() {
		return userMenu;
	}
	public void setUserMenu(String userMenu) {
		this.userMenu = userMenu;
	}
	public String getTargetUrl() {
		return targetUrl;
	}
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	public void setUserLogService(IUserLogService userLogService) {
		this.userLogService = userLogService;
	}

	
}
