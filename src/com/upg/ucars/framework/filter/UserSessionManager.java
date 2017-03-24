package com.upg.ucars.framework.filter;

import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.upg.ucars.constant.SessionKeyConst;
import com.upg.ucars.framework.base.SessionTool;
/**
 * 
 * 功能说明：TODO(用户会话管理器)
 * @author shentuwy  
 * @date 2012-1-30 上午10:09:44 
 *
 */
public class UserSessionManager implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent arg0) {
		arg0.getSession().setAttribute(SessionKeyConst.SESSION_LOCAL, new Locale("zh","CN"));
		SessionTool.AddSession(arg0.getSession());
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {
		HttpSession session = arg0.getSession();
		if( session != null ){
			SessionTool.releaseUserResource(session.getId());
			SessionTool.RemoveSession(session);
		}
	}

}
