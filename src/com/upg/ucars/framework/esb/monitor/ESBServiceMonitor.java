package com.upg.ucars.framework.esb.monitor;

import org.mule.api.context.notification.ServerNotification;
import org.mule.api.context.notification.ServiceNotificationListener;
import org.mule.context.notification.ServiceNotification;

import com.upg.ucars.util.LogUtil;

/**
 * Service Monitor
 * @author shentuwy
 * @date 2011-9-19
 **/
public class ESBServiceMonitor implements ServiceNotificationListener {

	public void onNotification(ServerNotification notification) {
		ServiceNotification serviceNotification = (ServiceNotification)notification;
		StringBuffer sb = new StringBuffer();
		sb.append(serviceNotification.EVENT_NAME).append("{").append("\r\n");
		sb.append("serverId=").append(serviceNotification.getServerId()).append("\r\n");
		sb.append("resourceId=").append(serviceNotification.getResourceIdentifier()).append("\r\n");
		sb.append("action=").append(serviceNotification.getActionName()).append("\r\n");
		sb.append("timestamp=").append(serviceNotification.getTimestamp()).append("\r\n");
		sb.append("message=").append(serviceNotification.getSource()).append("\r\n");
		sb.append("}");
		LogUtil.getESBCommLog().info(sb.toString());
	}

}
