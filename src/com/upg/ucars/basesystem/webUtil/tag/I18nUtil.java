package com.upg.ucars.basesystem.webUtil.tag;

import java.util.Locale;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import com.upg.ucars.constant.SessionKeyConst;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.util.StringUtil;

/**
 * class description
 * 
 * @author shentuwy
 * @date 2011-12-21
 **/
public class I18nUtil {
	
	public static String getI18nText(String key,PageContext pageContext){
		if(StringUtil.isEmpty(key)) return "";
		if(".".equals(key)) return ".";
		HttpServletRequest httpRequest = (HttpServletRequest)pageContext.getRequest();
		ActionContext actionContext = ServletActionContext.getActionContext(httpRequest);
		if(actionContext != null){
			ActionInvocation ai = actionContext.getActionInvocation();
			if(ai != null){
				Object action = ai.getAction();
				if(action != null){
					String text = "";
					StringTokenizer strToke = new StringTokenizer(key, ",");
					while (strToke.hasMoreElements()) {
						String k = strToke.nextToken();
						Locale locale = (Locale) SessionTool.getAttribute(SessionKeyConst.SESSION_LOCAL);
						if (locale == null) {
							locale = Locale.CHINA;
						}
						String tmp = LocalizedTextUtil.findText(action.getClass(), k, locale);
						if(StringUtil.isEmpty(tmp))
							text = text + k;
						else
						    text = text + tmp;
					}
					if(StringUtil.isEmpty(text))
						return key;
					else
						return text;
				}
			}
		}
		return key;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
