package com.upg.ucars.basesystem.webUtil.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.components.UIBean;

import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 
 * XCarsUIBean
 * 
 * @author shentuwy
 * 
 */
public abstract class XCarsUIBean extends UIBean {
	
	private static final String XCARS_UI_TEMPLATEDIR = "xcars.ui.templateDir";
	private static final String XCARS_UI_THEME = "xcars.ui.theme";
	

	public XCarsUIBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}


	@Inject(XCARS_UI_TEMPLATEDIR)
	public void setDefaultTemplateDir(String dir) {
		this.defaultTemplateDir = dir;
	}

	@Inject(XCARS_UI_THEME)
	public void setDefaultUITheme(String theme) {
		this.defaultUITheme = theme;
	}

	public void evaluateParams() {
		addParameter("templateDir", getTemplateDir());
		addParameter("theme", getTheme());
		addParameter("dynamicAttributes", dynamicAttributes);
		
		evaluateValue();
		
		evaluateExtraParams();
	}
	
	private void evaluateValue(){
		if( StringUtils.isNotBlank(name) && StringUtils.isBlank(value) ){
			Object obj = findValue(name);
			if( obj != null ){
				value = String.valueOf(obj);
			}
		}
	}

}
