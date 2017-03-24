/*
 * 源程序名称: TemplateAction.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：基础类
 * 
 */

package com.upg.ucars.factory;

import com.upg.ucars.basesystem.product.core.busidef.IBusiTemplateDefService;
import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.util.SourceTemplate;

/**
 * 
 * 功能说明：提供模板文件的统一结果处理
 *         具体路径的配置参照 strtus.xml中freemarkerResult和velocityResult配置
 * @author shentuwy  
 * @date Jun 20, 2011 2:12:11 PM 
 *
 */
public class TemplateAction extends BaseAction{

	
	private String templateFile = null;

	public String getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

	/**
	 * 针对不同模板文件定位不同类型的结果，暂时提供freemarker和velocity两种方式的支持
	 * @param newTemplateFile
	 * @return
	 */
	private String dispatchResult(String newTemplateFile)
	{
		if(this.templateFile==null||"".equals(this.templateFile))
		   this.templateFile = newTemplateFile;
		
		String  fileExt = this.templateFile.substring(this.templateFile.lastIndexOf("."));
		if(".ftl".equals(fileExt))
			return "freemarkerResult";
		else if(".vm".equals(fileExt))
			return "velocityResult";
		else
			return newTemplateFile;
		
	}
	
	/**
	 * 根据交易号定位不同模板对应结果
	 * @param busiNo
	 * @return
	 */
	public String dispatchTempldate(String busiNo)
	{
		IBusiTemplateDefService busiDefService = SourceTemplate.getBean(IBusiTemplateDefService.class,BeanNameConstants.BUSI_TEMPLATE_DEF_SERVICE);
		String miNo = SessionTool.getUserLogonInfo().getMiNo();
		String tempfile = busiDefService.findUsingBusiDef(busiNo, miNo).getTemplateFile();
		return dispatchResult(tempfile);
		
	}
	
}
