package com.upg.ucars.framework.esb.monitor;

import org.mule.api.context.notification.ExceptionNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.context.notification.ExceptionNotification;

import com.upg.ucars.util.LogUtil;
/**
 * ESB异常监控
 * @author hezw
 *
 */
public class ESBExceptionMonitor implements ExceptionNotificationListener {

	public void onNotification(ServerNotification notification) {
//		ComponentException ce = (ComponentException)notification.getSource();
		ExceptionNotification exceptionNotification = (ExceptionNotification)notification;
		StringBuffer sb = new StringBuffer();
		sb.append(exceptionNotification.EVENT_NAME).append("{").append("\r\n");
		sb.append("serverId=").append(exceptionNotification.getServerId()).append("\r\n");
		sb.append("resourceId=").append(exceptionNotification.getResourceIdentifier()).append("\r\n");
		sb.append("action=").append(exceptionNotification.getActionName()).append("\r\n");
		sb.append("timestamp=").append(exceptionNotification.getTimestamp()).append("\r\n");
		sb.append("message=").append(exceptionNotification.getException()).append("\r\n");
		sb.append("}");
		LogUtil.getESBCommLog().info(sb.toString());
//		LogUtil.getESBCommLog().error(ce.getDetailedMessage());
	}

}
