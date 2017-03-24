/*******************************************************************************
 * Leadmind Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Nov 8, 2008
 *******************************************************************************/


package com.upg.ucars.basesystem.acctrecord.analyse;

/**
 * 
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */
public final class AnalyseUtil {
	
	private final static String BSH_SCRIPT_SYMBOL="$:";
	
	/**
	 * 获取要运行的表达式
	 * <li>以$:开头，则去掉$:
	 * <li>以非$:开头，则表示不是表达式，加""返回
	 * 
	 * @param express
	 * @return
	 */
	public static String getBSHScript(String express){
		if (express == null){
			express =  "";
		}
		//
		express = express.trim();
		boolean b = express.startsWith(BSH_SCRIPT_SYMBOL);
		if (b==true){
			return express.substring(BSH_SCRIPT_SYMBOL.length());
		}else{
			return "\""+express+"\"";
		}
	}
	
	/**
	 * 获取要运行的boolean表达式
	 * <li>
	 * <li>
	 * 
	 * @return
	 */
	public static String getBSHBooleanScript(String express){		
		express = express.trim();
		boolean b = express.startsWith(BSH_SCRIPT_SYMBOL);
		if (b==true){
			return express.substring(BSH_SCRIPT_SYMBOL.length());
		}else{
			return express;
		}
	}

}
