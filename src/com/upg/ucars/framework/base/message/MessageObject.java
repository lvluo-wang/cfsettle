package com.upg.ucars.framework.base.message;

import java.io.Serializable;

/**
 * 消息实体，承载消息对象，包含消息类别和消息信息)
 * 
 * @date 2010-12-28 上午10:49:27
 * @version V1.0
 */
public class MessageObject implements Serializable {

	private static final long serialVersionUID = 7124193518526292906L;
	/**
	 * 普通级别信息
	 */
	public static final String LEVEL_NOMAL="1";
	/**
	 * 错误级别信息
	 */
	public static final String LEVEL_ERROR="2";
	/**
	 * 中断级别信息
	 */
	public static final String LEVEL_INTERCEPT="3";
	/**
	 * 信息级别
	 */
	private MessageLevel level;
	/**
	 * 信息内容
	 */
	private String message;
	/**
	 * 构造方法
	 * @param level 消息级别
	 * @param message 消息内容
	 */
	public MessageObject(MessageLevel level, String message) {
		this.level = level;
		this.message = message;
	}
	public MessageObject(){}
	public MessageLevel getLevel() {
		return level;
	}
	public void setLevel(MessageLevel level) {
		this.level = level;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
