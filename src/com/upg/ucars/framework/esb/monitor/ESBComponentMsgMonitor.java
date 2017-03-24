package com.upg.ucars.framework.esb.monitor;

import org.mule.api.context.notification.ComponentMessageNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.context.notification.ComponentMessageNotification;

import com.upg.ucars.util.LogUtil;

/**
 * class description
 * 
 * @author shentuwy
 * @date 2011-9-19
 **/
public class ESBComponentMsgMonitor implements ComponentMessageNotificationListener {

	public void onNotification(ServerNotification notification) {
		ComponentMessageNotification componetMsgNotify = (ComponentMessageNotification)notification;
		StringBuffer sb = new StringBuffer();
		sb.append(componetMsgNotify.EVENT_NAME).append("{").append("\r\n");
		sb.append("serverId=").append(componetMsgNotify.getServerId()).append("\r\n");
		sb.append("serviceName=").append(componetMsgNotify.getServiceName()).append("\r\n");
		sb.append("action=").append(componetMsgNotify.getActionName()).append("\r\n");
		sb.append("timestamp=").append(componetMsgNotify.getTimestamp()).append("\r\n");
		sb.append("message=").append(componetMsgNotify.getSource()).append("\r\n");
		sb.append("}");
		LogUtil.getESBCommLog().info(sb.toString());
	}

}
