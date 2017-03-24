package com.upg.ucars.framework.esb.monitor;

import org.apache.commons.logging.Log;
import org.mule.api.context.notification.MuleContextNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.context.notification.MuleContextNotification;

import com.upg.ucars.util.LogUtil;

public class ESBMuleContextMonitor implements MuleContextNotificationListener {

	public void onNotification(ServerNotification notification) {
		MuleContextNotification muleContextNotification = (MuleContextNotification)notification;
		StringBuffer sb = new StringBuffer();
		sb.append(muleContextNotification.EVENT_NAME).append("{").append("\r\n");
		sb.append("clusterId=").append(muleContextNotification.getClusterId()).append("\r\n");
		sb.append("domain=").append(muleContextNotification.getDomain()).append("\r\n");
		sb.append("serverId=").append(muleContextNotification.getServerId()).append("\r\n");
		sb.append("resourceId=").append(muleContextNotification.getResourceIdentifier()).append("\r\n");
		sb.append("action=").append(muleContextNotification.getActionName()).append("\r\n");
		sb.append("timestamp=").append(muleContextNotification.getTimestamp()).append("\r\n");
		sb.append("message=").append(muleContextNotification.getSource()).append("\r\n");
		sb.append("}");
		Log log = LogUtil.getESBCommLog();
		//System.out.println("-----------------"+log.getClass().getName()+"-----------------");
		log.info(sb.toString());
	}

}
