package com.upg.cfsettle.util;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * 鑫合汇后台工具类
 * @author renzhuolun
 * @date 2014年8月5日 下午3:03:10
 * @version <b>1.0.0</b>
 */
public class LcsUtils {
	
	/**
	 * 字符串数组转long数组
	 * @author renzhuolun
	 * @date 2014年8月5日 下午3:03:32
	 * @param stringList
	 * @return
	 */
	public static List<Long> StringToLong(List<String> stringList) {
		List<Long> reLongList=new ArrayList<Long>();
		for(String str:stringList){
			reLongList.add(Long.valueOf(str));
		}
		return reLongList;
	}
	
	/**
	 * 字符串数组转long数组
	 * @author renzhuolun
	 * @date 2014年8月5日 下午3:03:32
	 * @param stringList
	 * @return
	 */
	public static List<Long> StringToLong(String[] stringList) {
		List<Long> reLongList=new ArrayList<Long>();
		for(String str:stringList){
			reLongList.add(Long.valueOf(str));
		}
		return reLongList;
	}

}
