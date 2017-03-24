package com.upg.ucars.framework.esb.monitor;

import org.mule.api.context.notification.EndpointMessageNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.context.notification.EndpointMessageNotification;

import com.upg.ucars.util.LogUtil;
/**
 * ESB发出/接收消息通讯监控
 * @author hezw
 *
 */
public class ESBEndpointMsgMonitor implements EndpointMessageNotificationListener {

	public void onNotification(ServerNotification notification) {
		EndpointMessageNotification endpointMessageNotification = (EndpointMessageNotification)notification;
		StringBuffer sb = new StringBuffer();
		sb.append(endpointMessageNotification.EVENT_NAME).append("{").append("\r\n");
		sb.append("serverId=").append(endpointMessageNotification.getServerId()).append("\r\n");
		sb.append("resourceId=").append(endpointMessageNotification.getResourceIdentifier()).append("\r\n");
		sb.append("endpoint=").append(endpointMessageNotification.getEndpoint()).append("\r\n");
		sb.append("action=").append(endpointMessageNotification.getActionName()).append("\r\n");
		sb.append("timestamp=").append(endpointMessageNotification.getTimestamp()).append("\r\n");
		sb.append("message=").append(endpointMessageNotification.getSource()).append("\r\n");
		sb.append("}");
		LogUtil.getESBCommLog().info(sb.toString());
//		LogUtil.getESBCommLog().info(notification.getSource());
	}

}
