package com.upg.ucars.util;

import java.util.ArrayList;
import java.util.List;


/**
 * (系统消息发布器)
 * 
 * @date 2010-12-17 上午11:14:43
 *
 */
public class SysNewsPublisher {
	public static final String SESSION_KEY="SysNewsPublisher";
	//消息集合
	private List<String> news=new ArrayList<String>();
	//消息产生的时间集合
	private List<String> times=new ArrayList<String>();
	//最大包含的消息条数
	private static int cache=8;
	/**
	 * (发布一条消息)
	 * @param info  void
	 */
	public void publishNews(String info){
		if(news.size()>cache){
			news.remove(0);//
			times.remove(0);
		}
		news.add(info);
		times.add(getNowDateTime());
	}
	/**
	 * (移除所用信息)  
	 */
	public void removeAll(){
		news.clear();
		times.clear();
	}
	/**
	 * (获取html格式的消息)
	 * @return  String
	 */
	public String getHtmlNewsContent(){
		StringBuffer sb=new StringBuffer();
		int length=news.size();
		for(int i=length-1;i>=0;i--){
			sb.append("<span>"+times.get(i)+"</span>&nbsp;<i>"+news.get(i)+"</i><br/>\n");
		}		
		return sb.toString();
	}
	/**
	 * (获取系统当前时间yyyy MM DD HHMMSS)
	 * @return  String 系统当前时间yyyy MM DD HHMMSS
	 */
	private static String getNowDateTime(){		
		return DateTimeUtil.get_YYYY_MM_DD_Date(DateTimeUtil.getNowDateTime())+" "+DateTimeUtil.get_HHmmss_Time();
	}

}
