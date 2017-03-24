package com.upg.ucars.basesystem.webUtil.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.PropertyTag;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * shentuwy
 * 
 */
public class XPropertyTag extends PropertyTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7875214687986378438L;

	private boolean crlfHtml = true;

	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		return new XProperty(stack);
	}

	@Override
	public void populateParams() {
		super.populateParams();
		XProperty tag = (XProperty) component;
		tag.setCrlfHtml(crlfHtml);
	}

	public void setCrlfHtml(boolean crlfHtml) {
		this.crlfHtml = crlfHtml;
	}
	
	

}
