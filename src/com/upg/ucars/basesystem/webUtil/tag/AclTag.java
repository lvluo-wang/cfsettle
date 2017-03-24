package com.upg.ucars.basesystem.webUtil.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

public class AclTag extends ComponentTagSupport {
	/**
	 * 受控资源
	 */
	private String uri;
	
	@Override
	public Component getBean(ValueStack arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) {
		Acl btn=new Acl(arg0);
		return btn;
	}
	
	@Override
	protected void populateParams() {
		super.populateParams();
		Acl acl=(Acl)component;
		acl.setUri(uri);
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
