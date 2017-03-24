package com.upg.ucars.framework.base.message;

import java.util.Observable;

/**
 * (消息监听器，继承Observable)
 * 
 * @date 2010-12-28 上午11:42:10
 * @version V1.0
 */
public class MessageWatched extends Observable {
	/**
	 * session中用的key
	 */
	public static final String SESSION_KEY="messageWatched";
	/**
	 * (添加一条消息)
	 * @param level 消息级别 参考MessageLevel
	 * @param message  消息内容
	 */
	public void addMessage(MessageLevel level,String message ){
		MessageObject obj=new MessageObject(level,message);
		setChanged();
		notifyObservers(obj);
	}
	/**
	 * (添加一条消息)
	 * @param message  消息内容，默认普通级别MessageLevel.LEVEL_NORMAL
	 */
	public void addMessage(String message ){
		MessageObject obj=new MessageObject(MessageLevel.LEVEL_NORMAL,message);
		setChanged();
		notifyObservers(obj);
	}
}
