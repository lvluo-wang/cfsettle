package com.upg.ucars.framework.esb.monitor;

import org.mule.api.context.notification.ModelNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.context.notification.ModelNotification;

import com.upg.ucars.util.LogUtil;

/**
 * class description
 * 
 * @author shentuwy
 * @date 2011-9-19
 **/
public class ESBModelMonitor implements ModelNotificationListener {

	public void onNotification(ServerNotification notification) {
		ModelNotification modelNotification = (ModelNotification)notification;
		StringBuffer sb = new StringBuffer();
		sb.append(modelNotification.EVENT_NAME).append("{").append("\r\n");
		sb.append("serverId=").append(modelNotification.getServerId()).append("\r\n");
		sb.append("resourceId=").append(modelNotification.getResourceIdentifier()).append("\r\n");
		sb.append("action=").append(modelNotification.getActionName()).append("\r\n");
		sb.append("timestamp=").append(modelNotification.getTimestamp()).append("\r\n");
		sb.append("message=").append(modelNotification.getSource()).append("\r\n");
		sb.append("}");
		LogUtil.getESBCommLog().info(sb.toString());
	}

}
