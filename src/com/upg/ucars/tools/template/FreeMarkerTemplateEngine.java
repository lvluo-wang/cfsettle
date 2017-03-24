/*
 * 源程序名称: FreeMarkerTemplateEngine.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: 供应链金融服务平台(X-FOSC)
 * 模块名称：模板工具
 * 
 */

package com.upg.ucars.tools.template;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;

import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.util.PropertiesReader;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * 功能说明：功能说明：采用Freemarker框架的模板解析引擎的封装实现
 * @author shentuwy  
 * @date Jun 16, 2011 9:48:43 AM 
 *
 */
@Service
public class FreeMarkerTemplateEngine implements TemplateEngine {

	private static Logger log = Logger.getLogger(FreeMarkerTemplateEngine.class);   

	private static Configuration cfg = null;

	/**
	 * 根据模板存放路径创建注册信息
	 * @param templateDir
	 * @return
	 * @throws IOException
	 */
	private  Configuration getConfiguration(String templatePath) throws IOException {
		if (null == cfg) {
			cfg = new Configuration();
	       // File templateDirFile = new File(templateDir);
			
			
	        //cfg.setDirectoryForTemplateLoading(templateDirFile);
	        cfg.setLocale(Locale.CHINA);
	        cfg.setDefaultEncoding("UTF-8");
	        
	        // 构造模板加载器
	        TemplateLoader templatePathLoader = null;
		   
		      if (templatePath.startsWith("class://"))
		      {
		        templatePathLoader = new ClassTemplateLoader(FreeMarkerTemplateEngine.class, templatePath.substring(7));
		      } else if (templatePath.startsWith("file://"))
		        templatePathLoader = new FileTemplateLoader(new File(templatePath.substring(6)));
		  
	        cfg.setTemplateLoader(templatePathLoader);
	        
		}
		return cfg;
	}
	
	/**
	 * 根据模板文件取得模板对象
	 * @param templateFile
	 * @return
	 * @throws IOException
	 */
	private  Template getTemplate(String templateFile) throws IOException
	{
		if (null == cfg) {
		   String templateDir = PropertiesReader.getProperty(TemplateProperties.TEMPLATE_FILE_PATH,TemplateProperties.TEMPLATE_PROPERTIES_FILE);
		   cfg = getConfiguration(templateDir);
		}
		
		return cfg.getTemplate(templateFile);
	}
	/**
	 * 根据模板内容取得模板对象
	 * @param templateName
	 * @param content
	 * @return
	 * @throws IOException
	 */
	private  Template getPlainTextTemplate(String templateName,String content) throws IOException
	{
		if (null == cfg) {
		   String templateDir = PropertiesReader.getProperty(TemplateProperties.TEMPLATE_FILE_PATH,TemplateProperties.TEMPLATE_PROPERTIES_FILE);
		   cfg = getConfiguration(templateDir);
		}
		return Template.getPlainTextTemplate(templateName, content, cfg);
		
	}
	
	
	/**
	 * 根据指定的模板文件 生成内容
	 * @param templateName  模板文件
	 * @param data          模板使用的数据
	 * @return              生成内容
	 */
	
	public String generateContent(String templateFile, Map<String,Object> data) {
		 
		 String result =null;
		 try{
			//ByteArrayOutputStream bout = new ByteArrayOutputStream();
			//Writer writer = new OutputStreamWriter(bout);
			Writer writer = new StringWriter();
			 
			getTemplate(templateFile).process(data, writer);
		    writer.close();
		    result = writer.toString();
		     
		}catch(Exception e)
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
	public String generateFile(String templateFile, Map<String,Object> data, String destFile) {
		
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
		     
//		     Writer writer = new FileWriter(output);
		     Writer writer = new OutputStreamWriter(new FileOutputStream(output), "UTF-8");
		     getTemplate(templateFile).process(data, writer);
		     
		     writer.close(); 
		 
		 }catch(Exception e)
		 {
			 throw ExceptionManager.getException(ServiceException.class, TemplateEngine.ERR_TEMPLATE_002, templateFile,e);
		 }
		 return localDestFile;
	}
	
}
