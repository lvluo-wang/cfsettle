package com.upg.ucars.basesystem.webUtil.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ComponentTagSupport;

import com.opensymphony.xwork2.util.ValueStack;

public class ButtonTag extends ComponentTagSupport {
	/**
	 * DOM id
	 */
	private String id;
	/**
	 * 文本，支持国际化
	 */
	private String text;
	/**
	 * 显示的图标
	 */
	private String icon;
	private String iconCls;
	/**
	 * 效果，plain 简朴，round 圆形，默认为plain
	 */
	private String effect;
	/**
	 * 点击事件
	 */
	private String click;
	
	private String onclick;
	private String onClick;
	/**
	 * 是否为不可用，默认为false
	 */
	private boolean disabled;
	/**
	 * 自定义样式
	 */
	private String style;
	private String acl;
	@Override
	public Component getBean(ValueStack arg0, HttpServletRequest arg1,
			HttpServletResponse arg2) {
		Button btn=new Button(arg0);
		btn.setLocale(arg1.getLocale());
		return btn;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public void setEffect(String effect) {
		this.effect = effect;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getClick() {
		return click;
	}
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}
	public void setClick(String click) {
		this.click = click;
	}
	
	public void setAcl(String acl) {
		this.acl = acl;
	}
	@Override
	protected void populateParams() {
		// TODO Auto-generated method stub
		super.populateParams();
		Button button=(Button)component;
		button.setDisabled(disabled);
		button.setEffect(effect);
		button.setIcon(icon);
		button.setId(id);
		button.setAcl(acl);
		button.setText(text);
		button.setClick(click);
		button.setStyle(style);
		if(StringUtils.isNotBlank(iconCls)){
			button.setIcon(iconCls);
		}
		if(StringUtils.isNotBlank(onclick)){
			button.setClick(onclick);
		}
		if(StringUtils.isNotBlank(onClick)){
			button.setClick(onClick);
		}
	}

}
