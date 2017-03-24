package com.upg.ucars.basesystem.webUtil.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 
 * CodeNameTag.java
 * 
 * @author shentuwy
 * @date 2012-9-3
 * 
 */
public class CodeNameTag extends AbstractUITag {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7180366961928303302L;

	/** 数据字典类型 */
	private String				codeKey;
	/** 数据字典编码 */
	private String				codeNo;

	public String getCodeKey() {
		return codeKey;
	}

	public void setCodeKey(String codeKey) {
		this.codeKey = codeKey;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req,
			HttpServletResponse res) {
		return new CodeName(stack);
	}

	@Override
	protected void populateParams() {

		CodeName codeName = (CodeName) component;
		codeName.setCodeKey(codeKey);
		codeName.setCodeNo(codeNo);

	}
}
