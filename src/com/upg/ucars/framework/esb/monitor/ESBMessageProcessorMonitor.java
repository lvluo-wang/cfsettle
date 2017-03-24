package com.upg.ucars.framework.esb.monitor;

import org.mule.api.context.notification.MessageProcessorNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.context.notification.MessageProcessorNotification;

import com.upg.ucars.util.LogUtil;

/**
 * class description
 * 
 * @author shentuwy
 * @date 2011-9-19
 **/
public class ESBMessageProcessorMonitor implements MessageProcessorNotificationListener {

	public void onNotification(ServerNotification notification) {
		MessageProcessorNotification msgProcessorNotify = (MessageProcessorNotification)notification;
		StringBuffer sb = new StringBuffer();
		sb.append(msgProcessorNotify.EVENT_NAME).append("{").append("\r\n");
		sb.append("serverId=").append(msgProcessorNotify.getServerId()).append("\r\n");
		sb.append("messageProcessorName=").append(msgProcessorNotify.getFriendlyProcessorName()).append("\r\n");
		sb.append("action=").append(msgProcessorNotify.getActionName()).append("\r\n");
		sb.append("timestamp=").append(msgProcessorNotify.getTimestamp()).append("\r\n");
		sb.append("message=").append(msgProcessorNotify.getSource()).append("\r\n");
		sb.append("}");
		LogUtil.getESBCommLog().info(sb.toString());
	}

}
