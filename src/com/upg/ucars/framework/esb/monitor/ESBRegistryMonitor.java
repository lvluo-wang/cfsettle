package com.upg.ucars.framework.esb.monitor;

import org.mule.api.context.notification.RegistryNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.context.notification.RegistryNotification;

import com.upg.ucars.util.LogUtil;

/**
 * class description
 * 
 * @author shentuwy
 * @date 2011-9-19
 **/
public class ESBRegistryMonitor implements RegistryNotificationListener {

	public void onNotification(ServerNotification notification) {
		RegistryNotification registryNotification = (RegistryNotification)notification;
		StringBuffer sb = new StringBuffer();
		sb.append(registryNotification.EVENT_NAME).append("{").append("\r\n");
		sb.append("resourceId=").append(registryNotification.getResourceIdentifier()).append("\r\n");
		sb.append("action=").append(registryNotification.getActionName()).append("\r\n");
		sb.append("timestamp=").append(registryNotification.getTimestamp()).append("\r\n");
		sb.append("message=").append(registryNotification.getSource()).append("\r\n");
		sb.append("}");
		LogUtil.getESBCommLog().info(sb.toString());
	}

}
