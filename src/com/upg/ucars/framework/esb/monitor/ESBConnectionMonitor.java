package com.upg.ucars.framework.esb.monitor;

import org.mule.api.context.notification.ConnectionNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.context.notification.ConnectionNotification;

import com.upg.ucars.util.LogUtil;
/**
 * 传输器连接资源信息(连接/释放及连接失败等信息的监控)
 * @author hezw
 *
 */
public class ESBConnectionMonitor implements ConnectionNotificationListener {

	public void onNotification(ServerNotification notification) {
		ConnectionNotification connectionNotification = (ConnectionNotification)notification;
		StringBuffer sb = new StringBuffer();
		sb.append(connectionNotification.EVENT_NAME).append("{").append("\r\n");
		sb.append("serverId=").append(connectionNotification.getServerId()).append("\r\n");
		sb.append("resourceId=").append(connectionNotification.getResourceIdentifier()).append("\r\n");
		sb.append("action=").append(connectionNotification.getActionName()).append("\r\n");
		sb.append("timestamp=").append(connectionNotification.getTimestamp()).append("\r\n");
		sb.append("message=").append(connectionNotification.getSource()).append("\r\n");
		sb.append("}");
		LogUtil.getESBCommLog().info(sb.toString());
//		LogUtil.getESBCommLog().info(notification.getSource());
	}

}
