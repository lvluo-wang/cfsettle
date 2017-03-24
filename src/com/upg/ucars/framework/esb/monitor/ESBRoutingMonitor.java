package com.upg.ucars.framework.esb.monitor;

import org.mule.api.context.notification.RoutingNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.context.notification.RoutingNotification;

import com.upg.ucars.util.LogUtil;

/**
 * class description
 * 
 * @author shentuwy
 * @date 2011-9-19
 **/
public class ESBRoutingMonitor implements RoutingNotificationListener {

	public void onNotification(ServerNotification notification) {
		RoutingNotification routingNotification = (RoutingNotification)notification;
		StringBuffer sb = new StringBuffer();
		sb.append(routingNotification.EVENT_NAME).append("{").append("\r\n");
		sb.append("serverId=").append(routingNotification.getServerId()).append("\r\n");
		sb.append("resourceId=").append(routingNotification.getResourceIdentifier()).append("\r\n");
		sb.append("action=").append(routingNotification.getActionName()).append("\r\n");
		sb.append("timestamp=").append(routingNotification.getTimestamp()).append("\r\n");
		sb.append("message=").append(routingNotification.getSource()).append("\r\n");
		sb.append("}");
		LogUtil.getESBCommLog().info(sb.toString());
	}

}
