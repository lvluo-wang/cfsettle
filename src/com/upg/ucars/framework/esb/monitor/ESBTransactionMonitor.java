package com.upg.ucars.framework.esb.monitor;

import org.mule.api.context.notification.ServerNotification;
import org.mule.api.context.notification.TransactionNotificationListener;
import org.mule.context.notification.TransactionNotification;

import com.upg.ucars.util.LogUtil;

public class ESBTransactionMonitor implements TransactionNotificationListener{

	public void onNotification(ServerNotification notification) {
		TransactionNotification transactionNotification = (TransactionNotification)notification;
		StringBuffer sb = new StringBuffer();
		sb.append(transactionNotification.EVENT_NAME).append("{").append("\r\n");
		sb.append("transactionStringId=").append(transactionNotification.getTransactionStringId()).append("\r\n");
		sb.append("serverId=").append(transactionNotification.getServerId()).append("\r\n");
		sb.append("resourceId=").append(transactionNotification.getResourceIdentifier()).append("\r\n");
		sb.append("action=").append(transactionNotification.getActionName()).append("\r\n");
		sb.append("timestamp=").append(transactionNotification.getTimestamp()).append("\r\n");
		sb.append("message=").append(transactionNotification.getSource()).append("\r\n");
		sb.append("}");
		LogUtil.getESBCommLog().info(sb.toString());
//		LogUtil.getESBCommLog().info(notification.getSource());
	}

}
