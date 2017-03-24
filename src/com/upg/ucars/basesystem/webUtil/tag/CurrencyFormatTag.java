package com.upg.ucars.basesystem.webUtil.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 
 * MoneyFormatTag.java
 * 
 * @author shentuwy
 * @date 2012-9-17
 * 
 */
public class CurrencyFormatTag extends AbstractUITag {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -420217219445859922L;

	/** 小数位数 */
	private int					scale;

	@Override
	public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
		return new CurrencyFormat(stack);
	}

	@Override
	protected void populateParams() {
		CurrencyFormat currencyFormat = (CurrencyFormat) component;
		currencyFormat.setValue(value);
		currencyFormat.setScale(scale);
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

}
