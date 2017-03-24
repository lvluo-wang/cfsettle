package com.upg.ucars.tools.msg;

/**
 * 抽象报文转换器
 * 
 * @author shentuwy
 * @date 2011-10-14
 **/
public abstract class AbsTransformer {
	
	/**
	 * 转换,由子类实现
	 * 如xml->定长(分隔符)文本,或定长(分隔符)文本->xml直接转换
	 * @param msg       原始报文
	 * @param configKey 见/msg-transform-config.xml配置的元素cfg的key属性
	 * @return String   转化后的报文
	 * @date 2011-10-14
	 **/
	public abstract String doTransform(final String msg, final String configKey);
}
