/*
 * 源程序名称: VelocityTemplateEngine.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: 供应链金融服务平台(X-FOSC)
 * 模块名称：模板工具
 * 
 */

package com.upg.ucars.tools.template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.util.PropertiesReader;




/**
 * 
 * 功能说明：采用Velocity框架的模板解析引擎的封装实现
 * @author shentuwy  
 * @date Jun 16, 2011 9:48:01 AM 
 *
 */
public class VelocityTemplateEngine implements TemplateEngine {
	private static Logger log = Logger.getLogger(VelocityTemplateEngine.class); 
	
	private static VelocityEngine ve = new VelocityEngine();
	
	
	static{
		String templatePath = PropertiesReader.getProperty(TemplateProperties.TEMPLATE_FILE_PATH,TemplateProperties.TEMPLATE_PROPERTIES_FILE);
		Properties p = new Properties();
		
		p.setProperty("input.encoding","UTF-8");
		p.setProperty("output.encoding","UTF-8");
		
	    if (templatePath.startsWith("class://"))
	    {
	    	p.setProperty("file.resource.loader.class", "com.hs.xfosc.tools.template.VelocityClasspathResourceLoader");
	    	p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, templatePath.substring(7)); 
	    	  
	    } else if (templatePath.startsWith("file://"))
	    {
	    	p.setProperty("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.FileResourceLoader");
		    p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, templatePath.substring(6)); 
	    }
		ve.init(p);
		
	}
	
	
	
	/**
	 * 根据指定的模板文件 生成内容
	 * @param templateName  模板文件
	 * @param data          模板使用的数据
	 * @return              生成内容
	 */
	
	public String generateContent(String templateFile, Map<String,Object> data) {

        String result = null;
        try{
        	Template template = ve.getTemplate(templateFile);
    		VelocityContext context = new VelocityContext();
    		Iterator<String> it = data.keySet().iterator();
    		while(it.hasNext())
    		{
    			String key = it.next();
    			Object value = data.get(key);
    			context.put(key, value);
    		}
    		
    		Writer writer = new StringWriter();
    		 
    		template.merge(context, writer);
    	    writer.close();
    	    result = writer.toString();
        }catch(IOException e)
        {
        	throw ExceptionManager.getException(ServiceException.class, TemplateEngine.ERR_TEMPLATE_002, templateFile,e);
        }
		
	    return result;
	}
	/**
	 * 根据指定的模板文件 生成目标文件
	 * @param templateName   对应模板文件
	 * @param data           模板使用的数据
	 * @param destFile       生成的目标文件
	 * @return               
	 */
	public String generateFile(String templateFile, Map data, String destFile) {
		
		return generateFile(templateFile, data, destFile, true);
        
	}
	
	@Override
	public String getDestFileDir() {
		String result = null;
		String destDir = PropertiesReader.getProperty(TemplateProperties.DEST_FILE_PATH,TemplateProperties.TEMPLATE_PROPERTIES_FILE);
		if(!destDir.endsWith("/")){
			result = destDir += "/";
		}
		return result;
	}
	@Override
	public String generateFile(String templateFile, Map<String, Object> data, String destFile, boolean isAppendBaseDir) {
		 String destDir = getDestFileDir();
			
		 String localDestFile = destFile;
		 if (isAppendBaseDir) {
			localDestFile = destDir+destFile;
		} 
		
		 int pos = localDestFile.lastIndexOf("/");
		 if(pos<0) pos = localDestFile.lastIndexOf(File.separatorChar);
	
		 String genFileDir=localDestFile.substring(0, pos);
		 
        try{
        	 org.apache.commons.io.FileUtils.forceMkdir(new File(genFileDir));
		     File output = new File(localDestFile);
		     
		     Writer writer = new FileWriter(output);
		     
		     
        	Template template = ve.getTemplate(templateFile);
    		VelocityContext context = new VelocityContext();
    		Iterator<String> it = data.keySet().iterator();
    		while(it.hasNext())
    		{
    			String key = it.next();
    			Object value = data.get(key);
    			context.put(key, value);
    		}
 
    		template.merge(context, writer);
    	    writer.close();
    	   
        }catch(IOException e)
        {
        	throw ExceptionManager.getException(ServiceException.class, TemplateEngine.ERR_TEMPLATE_002, templateFile,e);
        }
        return localDestFile;
	}

}
