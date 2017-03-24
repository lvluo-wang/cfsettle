package com.upg.ucars.framework.esb.monitor;

import org.mule.api.context.notification.ManagementNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.context.notification.ManagementNotification;

import com.upg.ucars.util.LogUtil;

/**
 * class description
 * 
 * @author shentuwy
 * @date 2011-9-19
 **/
public class ESBManagementMonitor implements ManagementNotificationListener {

	public void onNotification(ServerNotification notification) {
		ManagementNotification managementNotify = (ManagementNotification)notification;
		StringBuffer sb = new StringBuffer();
		sb.append(managementNotify.EVENT_NAME).append("{").append("\r\n");
		sb.append("serverId=").append(managementNotify.getServerId()).append("\r\n");
		sb.append("action=").append(managementNotify.getActionName()).append("\r\n");
		sb.append("timestamp=").append(managementNotify.getTimestamp()).append("\r\n");
		sb.append("message=").append(managementNotify.getSource()).append("\r\n");
		sb.append("}");
		LogUtil.getESBCommLog().info(sb.toString());
	}

}
