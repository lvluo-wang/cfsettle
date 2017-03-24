/*
 * 源程序名称: TemplateEngineManager.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: 供应链金融服务平台(X-FOSC)
 * 模块名称：模板工具
 * 
 */

package com.upg.ucars.tools.template;

import org.apache.commons.lang.ClassUtils;

import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.util.PropertiesReader;


/**
 * 
 * 功能说明：模板引擎管理，依据具体的模板产生模板解析引擎实例
 * @author shentuwy  
 * @date Jun 14, 2011 5:21:53 PM 
 *
 */
public class TemplateEngineManager {

	
	
	public static TemplateEngine getTemplateEngine(String templateFile)
	{
		String  fileExt = templateFile.substring(templateFile.lastIndexOf("."));
		if(".ftl".equals(fileExt))
			return new FreeMarkerTemplateEngine();
		else if(".vm".equals(fileExt))
			return new VelocityTemplateEngine();
		else
			return getTemplateEngine();
		
	}
	
	public static TemplateEngine getTemplateEngine()
	{
		TemplateEngine te = null;
		String defaultEngine = PropertiesReader.getProperty(TemplateProperties.DEFAULT_ENGINE_CLASS,TemplateProperties.TEMPLATE_PROPERTIES_FILE);
		try {
			te = (TemplateEngine)ClassUtils.getClass(defaultEngine).newInstance();
		} catch (Exception e) {
			throw ExceptionManager.getException(ServiceException.class, TemplateEngine.ERR_TEMPLATE_001, e);
		} 
		return te;
	}
	
}
