package com.upg.ucars.framework.base;


import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.upg.ucars.constant.SessionKeyConst;
import com.upg.ucars.framework.base.message.MessageLevel;
import com.upg.ucars.framework.security.SecurityFactory;
import com.upg.ucars.model.SysStatus;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.SysNewsPublisher;


/**
 * (管理用户session中的对象，对系统提供用户session的操作访问入口)
 * 
 * @date 2010-12-17 上午09:48:34 update
 *
 */
public class SessionTool {
	private static Map<String,Long> userSessionMap=new HashMap<String,Long>();
	private static Map<Long,String> sessionUserMap=new HashMap<Long,String>();
	private static Map<String,HttpSession> sessionMap=new HashMap<String,HttpSession>();
	/**
	 * 添加session
	 * @param session
	 */
	public static synchronized void AddSession(HttpSession session){
		if(session!=null){
			sessionMap.put(session.getId(), session);
		}
	}
	/**
	 * 移除session
	 * @param session
	 */
	public static synchronized void RemoveSession(HttpSession session){
		if(session!=null){
			sessionMap.remove(session.getId());
		}
	}
	/**
	 * 获取session
	 * @param sessionId
	 * @return
	 */
	public static synchronized HttpSession getSession(String sessionId){
		if(sessionId!=null){
			return sessionMap.get(sessionId);
		}
		return null;
	}
	/**
	 * (数据对象存储到当前用户的session中)
	 * @param key 数据对象在session中的唯一标识
	 * @param param  将要存储的数据对象
	 */
	public static void setAttribute(String key,Object param){
		HttpServletRequest request=ServletActionContext.getRequest();
		if(request!=null){
			request.getSession().setAttribute(key, param);
		}
	}
	/**
	 * (获取session中存储的数据对象)
	 * @param key 数据对象在session中的唯一标识
	 * @return  存储在session中的数据对象
	 * @date 
	 */
	public static Object getAttribute(String key){
		Object ret=null;
		try{
			HttpServletRequest request=ServletActionContext.getRequest();
			if(request!=null){
				ret=request.getSession().getAttribute(key);
			}
		}catch(NullPointerException e){
			return ret;
		}
		
		return ret;
	}
	/**
	 * (判断session中是否存储了制定的数据对象)
	 * @param key 数据对象在session中的唯一标识
	 * @return  boolean 是否存在，不存在返回false
	 */
	public static boolean isExist(String key){
		boolean ret=false;
		HttpServletRequest request=ServletActionContext.getRequest();
		if(request!=null){
			HttpSession session=request.getSession(false);
			if(session!=null){
				Object param=session.getAttribute(key);
				if(param!=null){
					ret=true;
				}
			}
		}
		return ret;
	}
	/**
	 * (从session中移除制定的数据对象)
	 * @param key 数据对象在session中的唯一标识
	 *  void
	 */
	public static void removeAttribute(String key){
		HttpServletRequest request=ServletActionContext.getRequest();
		if(request!=null){
			request.getSession().removeAttribute(key);
		}
	}

	/**
	 * (设置当前用户session的本地化标识)
	 * @param lo    本地化对象 
	 */
	public static void setLocale(Locale lo){
		setAttribute(SessionKeyConst.SESSION_LOCAL, lo);

	}
	/**
	 * 设置默认的语言到session中
	 */
	public static void setDefaultLocale(){
		setAttribute(SessionKeyConst.SESSION_LOCAL,new Locale("zh","CN"));
	}
	/**
	 * (获取当前用户session中设置的本地化标识，如果为空则采用中文) 
	 * @return Locale    当前用户session中设置的本地化对象
	 */
	public static Locale getLocale(){
		Locale lo=(Locale)getAttribute(SessionKeyConst.SESSION_LOCAL);
		if(lo==null){
			lo=new Locale("zh","CN");
		}
		return lo;
	}
	/**
	 * (获取当前请求的发起方远程地址) 
	 * @return String    远程地址
	 */
	public static String getCurrentRequestIP(){
		HttpServletRequest request = getRequest();
		if (request == null) {
			return "";
		}
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getHeader("WL-Proxy-Client-IP");
		if (ip == null || ip.length() == 0 || ip.equalsIgnoreCase("unknown"))
			ip = request.getRemoteAddr();
		return ip;
	}
	/**
	 * (获取用户请求对象)
	 * @return  HttpServletRequest
	 */
	public static HttpServletRequest getRequest(){
		HttpServletRequest request =ServletActionContext.getRequest ();
		return request;
	}
	/**
	 * (获取用户相应对象)
	 * @return  HttpServletResponse
	 */
	public static HttpServletResponse getResponse(){
		HttpServletResponse response=ServletActionContext.getResponse();
		return response;
	}
	/**
	 * 获取当前session
	 * @return httpSession
	 */
	public static HttpSession getCurrentSession(){
		HttpSession session=null;
		HttpServletRequest request=getRequest();
		if(request!=null){
			session=request.getSession();
		}
		return session;
	}
	/**
	 * (获取消息发布器)
	 * @return  SysNewsPublisher
	 */
	public static SysNewsPublisher getSysNewsPublisher(){
		return (SysNewsPublisher)getAttribute(SessionKeyConst.SYS_NEWS_PUBLISHER);
	}
	
	/**
	 * (发布一条消息，供前台显示)
	 * @param msg  void
	 */
	public static void publishNews(String msg){
		SysNewsPublisher  spublish=getSysNewsPublisher();
		if(spublish==null){
			spublish=new SysNewsPublisher();
			setAttribute(SessionKeyConst.SYS_NEWS_PUBLISHER,spublish);
		}
		spublish.publishNews(msg);
	}
	/**
	 * (发布带级别的消息)
	 * @param level 参考MessageLevel，普通或中断
	 * @param msg  void
	 */
	public static void publishNews(MessageLevel level,String msg){
		if(MessageLevel.LEVEL_NORMAL.equals(level)){
			refreshSysStatus(SysStatus.NORMAL, msg);
		}
		if(MessageLevel.LEVEL_INTERCEPT.equals(level)){
			refreshSysStatus(SysStatus.INTERCEPT, msg);
		}
		publishNews(msg);
	}
	/**
	 * 从session中获取系统最新状态
	 * @return  SysStatus
	 */
	public static SysStatus getSysStatus(){
		SysStatus status=(SysStatus)getAttribute(SessionKeyConst.SYS_RUN_STATUS);
		return status;
	}
	/**
	 * 更新系统当前状态，前台会显示最新状态，并根据级别判断是否锁屏处理，中断级别INTERCEPT锁屏
	 * @param level 参考 SysStatus.NORMAL(正常)或SysStatus.INTERCEPT(中断)
	 * @param info  系统状态信息
	 */
	public static void refreshSysStatus(int level,String info){
		SysStatus  status=getSysStatus();
		if(status==null){
			status=new SysStatus(level,info);
			setAttribute(SessionKeyConst.SYS_RUN_STATUS,status);
		}else{
			status.setInfo(info);
			status.setLevel(level);
		}
	}
	/**
	 * (获取session中的用户登录对象)
	 * @return  UserLogonInfo
	 */
	public static UserLogonInfo getUserLogonInfo(){
		return (UserLogonInfo)getAttribute(SessionKeyConst.USER_LOGON_INFO);
	}
	
	public static UserLogonInfo getUserLogonInfo(boolean defaultUser){
		UserLogonInfo result = getUserLogonInfo();
		if (result == null &&  defaultUser) {
			result = new UserLogonInfo();
			result.setSysUserId(10L);
			result.setUserNo("ucars_auto");
			result.setUserName("后台自动处理");
		}
		return result;
	}
	
	/**
	 * 回收用户资源(将用户置为离线，并去除与session的绑定)
	 */
	public static void releaseUserResource(String sessionId){
		if(sessionId!=null){
			Long sysUserId=userSessionMap.get(sessionId);
			if(sysUserId!=null){
				SecurityFactory.getSecurityManager().afterSessionRelease(sysUserId);
			}
			userSessionMap.remove(sessionId);
		}
		
	}
	/**
	 * 绑定用户id和其登录的session
	 * @param user
	 */
	public static void bindingUserSession(Long userId){
		if(userId!=null){
			HttpSession session=getCurrentSession();
			if(session!=null){
				userSessionMap.put(session.getId(), userId);
				sessionUserMap.put(userId, session.getId());
			}
		}
		
	}
	/**
	 * 移除指定的session
	 * @param sessionId
	 */
	public static void removeSession(String sessionId){
		HttpSession session=getSession(sessionId);
		if(session!=null){
			userSessionMap.remove(sessionId);
			session.invalidate();
		}
	}
	/**
	 * 移除用户废弃的session
	 * @param userId
	 */
	public static void removeUserOldSession(Long userId){
		String sessionId=sessionUserMap.get(userId);
		if(sessionId!=null){
			sessionUserMap.remove(userId);
			removeSession(sessionId);
		}
	}
	
	public static final void releaseCustLogonInfo(){
		HttpSession session = getRequest().getSession(false);
		if( session != null ){
			session.removeAttribute(SessionKeyConst.USER_LOGON_INFO);
		}
	}
}
