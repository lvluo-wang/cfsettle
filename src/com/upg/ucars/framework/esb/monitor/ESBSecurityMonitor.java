package com.upg.ucars.framework.esb.monitor;

import org.mule.api.context.notification.SecurityNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.context.notification.SecurityNotification;

import com.upg.ucars.util.LogUtil;

/**
 * class description
 * 
 * @author shentuwy
 * @date 2011-9-19
 **/
public class ESBSecurityMonitor implements SecurityNotificationListener {

	public void onNotification(ServerNotification notification) {
		SecurityNotification securityNotification = (SecurityNotification)notification;
		StringBuffer sb = new StringBuffer();
		sb.append(securityNotification.EVENT_NAME).append("{").append("\r\n");
		sb.append("serverId=").append(securityNotification.getServerId()).append("\r\n");
		sb.append("resourceId=").append(securityNotification.getResourceIdentifier()).append("\r\n");
		sb.append("action=").append(securityNotification.getActionName()).append("\r\n");
		sb.append("timestamp=").append(securityNotification.getTimestamp()).append("\r\n");
		sb.append("message=").append(securityNotification.getSource()).append("\r\n");
		sb.append("}");
		LogUtil.getESBCommLog().info(sb.toString());
	}

}
