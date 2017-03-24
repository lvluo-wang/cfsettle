package com.upg.ucars.basesystem.webUtil.tag;

import java.io.Writer;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.components.Component;
import org.apache.struts2.util.TextProviderHelper;

import com.opensymphony.xwork2.util.ValueStack;
import com.upg.ucars.framework.security.SecurityFactory;
import com.upg.ucars.util.LogUtil;

public class Button extends Component {
	private Locale locale;
	/**
	 * DOM id
	 */
	private String id;
	/**
	 * 文本，支持国际化
	 */
	private String text="";
	/**
	 * 显示的图标
	 */
	private String icon="";
	/**
	 * 效果，plain 简朴，round 圆形，默认为plain
	 */
	private String effect;
	/**
	 * 是否为不可用，默认为false
	 */
	private boolean disabled=false;
	private String style;
	
	private String acl;
	/**
	 * 点击事件名
	 */
	private String click;
	
	public String getId() {
		return id;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getClick() {
		return click;
	}

	public void setClick(String click) {
		this.click = click;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}



	public String getAcl() {
		return acl;
	}

	public void setAcl(String acl) {
		this.acl = acl;
	}

	@Override
	public boolean start(Writer writer) {
		boolean result = super.start(writer);

		if(StringUtils.isBlank(acl)||SecurityFactory.getSecurityManager().isAuthorized(acl)){
			try{
				StringBuffer buffer=new StringBuffer("<a href=\"javascript:void(0);\"");
				buffer.append(getIdText());
				buffer.append(getStyleText());
				buffer.append(" class=\"l-btn ");
				buffer.append(getEffectStyle());
				buffer.append(getDisableStyle());
				buffer.append("\" ");
				buffer.append(getClickText());
				buffer.append("><span class=\"l-btn-left\">");
				buffer.append(getButtonInnerText());
				buffer.append("</span></a>");
				writer.write(buffer.toString());
			}catch(Exception e){
				LogUtil.getExceptionLog().error(e);
			}
		}else{
			result=false;
		}

		
		
		return result;
	}
	private String getStyleText(){
		if(StringUtils.isBlank(style)){
			return "";
		}
		return " style=\""+style+"\" ";
	}
	private String getDisableStyle(){
		String style="";
		if(disabled){
			style=" l-btn-disabled ";
		}
		return style;
	}
	private String getIdText(){
		if(StringUtils.isBlank(id)){
			return "";
		}
		return " id=\""+id+"\" ";
	}
	private String getAclText(){
		if(StringUtils.isBlank(acl)){
			return "";
		}
		return " acl=\""+acl+"\" ";
	}
	private String getButtonInnerText(){
		String iconStyle="";
		String iconPaddingText="";
		String i18n="";
		if(StringUtils.isNotBlank(text)){
			if(text.indexOf(",")>-1){
				String[] texts=text.split(",");
				for(String str:texts){
					if(StringUtils.isNotBlank(str)){
						i18n+=TextProviderHelper.getText(str, str, getStack());
					}
				}
			}else{
				i18n=TextProviderHelper.getText(text, text, getStack());
			}
		}
		if(StringUtils.isBlank(i18n)){
			i18n=text;
		}
		if(!StringUtils.isBlank(icon)){
			iconStyle=icon;
			iconPaddingText=" style=\"PADDING-LEFT:20px\" ";
		}
		if(StringUtils.isBlank(text)){
			return "<span class=l-btn-text><span class=\"l-btn-empty "+iconStyle+"\">&nbsp;</span></span>";
		}else{
			return "<span "+iconPaddingText+" class=\"l-btn-text "+iconStyle+"\">"+i18n+"</span>";
		}
	}
	private String getEffectStyle(){
			/*
		if("round".equalsIgnoreCase(effect)){
			return "";
		}else{
		}
		*/
		return "l-btn-plain";
	}
	private String getClickText(){
		if(!StringUtils.isBlank(click)&&(!disabled)){
			String clickFuncText=click;
			if(click.indexOf("(")<0&&click.indexOf(")")<0){
				clickFuncText+="()";
			}
			return " onClick=\""+clickFuncText+"\" ";
		}
		return "";
	}
	public Button(ValueStack stack) {
		super(stack);
		// TODO Auto-generated constructor stub
	}

}
