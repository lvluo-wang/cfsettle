/*
 * 源程序名称: VelocityClasspathResourceLoader.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: 供应链金融服务平台(X-FOSC)
 * 模块名称：模板工具
 * 
 */

package com.upg.ucars.tools.template;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.util.ClassUtils;
import org.apache.velocity.util.ExceptionUtils;

import com.upg.ucars.util.PropertiesReader;

/**
 * 
 * 功能说明：velocity框架的类加载的重写，支持类资源的根路径配置，与FreeMarker保持一致
 * @author shentuwy  
 * @date Jun 16, 2011 9:44:23 AM 
 *
 */
public class VelocityClasspathResourceLoader extends ClasspathResourceLoader{
	
	
/**
 * 重写 ClasspathResourceLoader 的getResourceStream方法，获取内容时前面加上 template.properties中定义的路径
 */  
  public InputStream getResourceStream(String name) throws ResourceNotFoundException
  {
    InputStream result = null;

    if (StringUtils.isEmpty(name))
    {
      throw new ResourceNotFoundException("No template name provided");
    }

    try
    {
     // 获取模板根路径
     String templatePath = PropertiesReader.getProperty(TemplateProperties.TEMPLATE_FILE_PATH,TemplateProperties.TEMPLATE_PROPERTIES_FILE);
     
      result = ClassUtils.getResourceAsStream(getClass(), templatePath.substring(7)+File.separatorChar+ name);
    }
    catch (Exception fnfe)
    {
      throw ((ResourceNotFoundException)ExceptionUtils.createWithCause(ResourceNotFoundException.class, "problem with template: " + name, fnfe));
    }

    if (result == null)
    {
      String msg = "ClasspathResourceLoader Error: cannot find resource " + name;

      throw new ResourceNotFoundException(msg);
    }

    return result;
  }
}
