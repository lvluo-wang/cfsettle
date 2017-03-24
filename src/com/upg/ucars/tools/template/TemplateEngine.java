/*
 * 源程序名称: TemplateEngine.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: 供应链金融服务平台(X-FOSC)
 * 模块名称：模板工具
 * 
 */

package com.upg.ucars.tools.template;


import java.util.Map;

/**
 * 
 * 功能说明：模板处理引擎接口，对外定义模板生成的标准操作
 * @author shentuwy  
 * @date Jun 24, 2011 11:06:31 AM 
 *
 */
public interface TemplateEngine {

	public static final String ERR_TEMPLATE_001="TEMPLATE_001";
	public static final String ERR_TEMPLATE_002="TEMPLATE_002";
	
	/**
	 * 根据指定的模板文件 生成内容
	 * @param templateName  模板文件
	 * @param data          模板使用的数据
	 * @return              生成内容
	 */
	public  String generateContent(String templateFile,Map<String,Object> data);
	
	/**
	 * 根据指定的模板文件 生成目标文件
	 * @param templateName   对应模板文件
	 * @param data           模板使用的数据
	 * @param destFile       生成的目标文件
	 * @return               文件的全路径   
	 */
	public  String generateFile(String templateFile,Map<String,Object> data, String destFile);
	
	public 	String getDestFileDir();
	
	public  String generateFile(String templateFile,Map<String,Object> data, String destFile,boolean isAppendBaseDir);
	
	
}
