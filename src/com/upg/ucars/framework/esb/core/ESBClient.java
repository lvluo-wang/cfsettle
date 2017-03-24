package com.upg.ucars.framework.esb.core;

import java.util.Map;

import org.mule.DefaultMuleMessage;
import org.mule.api.ExceptionPayload;
import org.mule.api.FutureMessageResult;
import org.mule.api.MuleContext;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.context.notification.MuleContextNotificationListener;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.context.notification.MuleContextNotification;
import org.mule.module.client.MuleClient;
import org.mule.util.concurrent.Latch;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.util.SourceTemplate;

import edu.emory.mathcs.backport.java.util.concurrent.ExecutionException;
/**
 * ESB客户端实现
 * @author hezw
 *
 */
public class ESBClient  {
	
	private ESBClient(){}
	
	private static MuleClient client;
	
	private static MuleContext mc;
	/**
	 * 获取ESB 客户端实例
	 * @return	MuleClient
	 */
	public static MuleClient getClientInstance() {
		if (client == null) {
			try {
				IESBServer esbServer = (IESBServer) SourceTemplate.getBean("muleServer");
				mc = esbServer.getMuleContext();
				mc.registerListener(new MuleContextNotificationListener<MuleContextNotification>() {
							public void onNotification(MuleContextNotification notification) {
								if (notification.getAction() == MuleContextNotification.CONTEXT_STARTED) {
									new Latch().countDown();
								}
							}
						});
				mc.start();
				client = new MuleClient(mc);
			} catch (MuleException e) {
				ExceptionManager.throwException(ServiceException.class,
						ErrorCodeConst.ESB_CLIENT_CREATE_ERROR, e);
			}
		}
		return client;
	}
	/**
	 * 发送同步响应消息
	 * 
	 * @param url
	 *            目标url
	 * @param msg
	 *            消息
	 */
	public static MuleMessage send(String epName, MuleMessage msg) {
		try {
			String url = ((InboundEndpoint)client.getMuleContext().getRegistry().lookupObject(epName)).getAddress();
			MuleMessage mm = client.send(url, msg);
			ExceptionPayload ep = mm.getExceptionPayload();
			if(ep != null) {
				throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR, new Exception(ep.getMessage()));
			} else {
				return mm;				
			}
		} catch (MuleException e) {
			throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR, e);
		}
	}
	/**
	 * 发送同步响应消息
	 * @param url	目标url
	 * @param msg	消息
	 * @param timeout	等待响应时间
	 */
	public static MuleMessage send(String epName, MuleMessage msg, int timeout){
		try {
			String url = ((InboundEndpoint)client.getMuleContext().getRegistry().lookupObject(epName)).getAddress();
			MuleMessage mm = client.send(url, msg, timeout);
			ExceptionPayload ep = mm.getExceptionPayload();
			if(ep != null) {
				throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR, new Exception(ep.getMessage()));
			} else {
				return mm;
			}
		} catch (MuleException e) {
			throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR, e);
		}
	}
	/**
	 * 发送同步响应消息
	 * @param url	目标url
	 * @param payload	消息负载
	 * @param msgProperties	消息属性
	 */
	public static MuleMessage send(String epName, Object payload, Map msgProperties){
		try {
			String url = ((InboundEndpoint)client.getMuleContext().getRegistry().lookupObject(epName)).getAddress();
			MuleMessage mm = client.send(url, new DefaultMuleMessage(payload, mc), msgProperties);
			ExceptionPayload ep = mm.getExceptionPayload();
			if(ep != null) {
				throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR, new Exception(ep.getMessage()));
			} else {
				return mm;
			}
		} catch (MuleException e) {
			throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR, e);
		}
	}
	/**
	 * 发送同步响应消息
	 * @param url	目标url
	 * @param payload	消息负载
	 * @param msgProperties	消息属性
	 * @param timeout	等待响应时间
	 */
	public static MuleMessage send(String epName, Object payload, Map msgProperties, int timeout){
		try {
			String url = ((InboundEndpoint)client.getMuleContext().getRegistry().lookupObject(epName)).getAddress();
			MuleMessage mm = client.send(url, payload, msgProperties, timeout);
			ExceptionPayload ep = mm.getExceptionPayload();
			if(ep != null) {
				throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR, new Exception(ep.getMessage()));
			} else {
				return mm;
			}
		} catch (MuleException e) {
			throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR, e);
		}
	}
	/**
	 * 发送异步消息
	 * @param url	目标url
	 * @param msg	消息
	 * @return	FutureMessageResult
	 */
	public static FutureMessageResult sendAsync(String epName, MuleMessage msg) {
		FutureMessageResult result = null;
		try {
			String url = ((InboundEndpoint)client.getMuleContext().getRegistry().lookupObject(epName)).getAddress();
			result = client.sendAsync(url, msg);
			ExceptionPayload ep = result.getMessage().getExceptionPayload();
			if(ep != null) {
				ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR, new Exception(ep.getMessage()));
			}
		} catch (MuleException e) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR, e);
		} catch (InterruptedException e) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR);
		} catch (ExecutionException e) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR);
		}
		return result;
	}
	/**
	 * 发送异步消息
	 * @param url	目标url
	 * @param msg	消息
	 * @param timeout	等待响应时间
	 * @return	FutureMessageResult
	 */
	public static FutureMessageResult sendAsync(String epName, MuleMessage msg, int timeout){
		FutureMessageResult result = null;
		try {
			String url = ((InboundEndpoint)client.getMuleContext().getRegistry().lookupObject(epName)).getAddress();
			result = client.sendAsync(url, msg, timeout);
			ExceptionPayload ep = result.getMessage().getExceptionPayload();
			if(ep != null) {
				ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR, new Exception(ep.getMessage()));
			}
		} catch (MuleException e) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR, e);
		} catch (InterruptedException e) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR);
		} catch (ExecutionException e) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR);
		}
		return result;
	}
	/**
	 * 发送异步消息
	 * @param url	目标url
	 * @param payload	消息负载
	 * @param msgProperties	消息属性
	 * @return	FutureMessageResult
	 */
	public static FutureMessageResult sendAsync(String epName, Object payload, Map msgProperties){
		FutureMessageResult result = null;
		try {
			String url = ((InboundEndpoint)client.getMuleContext().getRegistry().lookupObject(epName)).getAddress();
			result = client.sendAsync(url, payload, msgProperties);
			ExceptionPayload ep = result.getMessage().getExceptionPayload();
			if(ep != null) {
				ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR, new Exception(ep.getMessage()));
			}
		} catch (MuleException e) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR, e);
		} catch (InterruptedException e) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR);
		} catch (ExecutionException e) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR);
		}
		return result;
	}
	/**
	 * 发送异步消息
	 * @param url	目标url
	 * @param payload	消息负载
	 * @param msgProperties	消息属性
	 * @param timeout	等待响应时间
	 * @return	FutureMessageResult
	 */
	public static FutureMessageResult sendAsync(String epName, Object payload, Map msgProperties, int timeout){
		FutureMessageResult result = null;
		try {
			String url = ((InboundEndpoint)client.getMuleContext().getRegistry().lookupObject(epName)).getAddress();
			result = client.sendAsync(url, payload, msgProperties, timeout);
			ExceptionPayload ep = result.getMessage().getExceptionPayload();
			if(ep != null) {
				ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR, new Exception(ep.getMessage()));
			}
		} catch (MuleException e) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR, e);
		} catch (InterruptedException e) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR);
		} catch (ExecutionException e) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.ESB_CLIENT_SEND_ERROR);
		}
		return result;
	}
}
