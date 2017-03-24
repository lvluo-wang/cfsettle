package com.upg.ucars.basesystem.webUtil.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 
 * ComboBoxTag
 * 
 * @author shentuwy
 * 
 */
public class ComboBoxTag extends AbstractUITag {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -794769851443815450L;

	/** 数据来源的url */
	private String				url;
	/** json数据 */
	private String				data;
	/** action属性 */
	private String				list;
	/** 值key */
	private String				valueField;
	/** 文本key */
	private String				textField;
	/** 下拉面板高度 */
	private String				panelHeight;
	/** 是否显示请选择 */
	private String				pleaseSelect;
	/** 只读 */
	private String				readonly;
	/** 验证 */
	private String				isValidateBox;
	/** 请选择的值 */
	private String				psValue;
	/** 多选 */
	private String				multiple;
	/** 是否多选 */
	private	String 				required;

	public Component getBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		return new ComboBox(stack, request, response);
	}

	public void populateParams() {

		doPrepare();

		super.populateParams();

		ComboBox cb = (ComboBox) component;
		cb.setUrl(url);
		cb.setData(data);
		cb.setList(list);
		cb.setValueField(valueField);
		cb.setTextField(textField);
		cb.setPanelHeight(panelHeight);
		cb.setPleaseSelect(pleaseSelect);
		cb.setReadonly(readonly);
		cb.setIsValidateBox(isValidateBox);
		cb.setPsValue(psValue);
		cb.setMultiple(multiple);
		cb.setRequired(required);
	}

	private void doPrepare() {

		if (StringUtils.isBlank(cssStyle)) {
			cssStyle = "height:20px;border:0px;";
		} else {
			if (cssStyle.indexOf("height:") < 0) {
				cssStyle += "height:20px;";
			}
			if (cssStyle.indexOf("border:") < 0) {
				cssStyle += "border:0px;";
			}
		}

		if (StringUtils.isNotBlank(url)) {
			String tmp = url.trim();
			if (tmp.startsWith("/")) {
				String cp = ((HttpServletRequest) pageContext.getRequest()).getContextPath();
				if (StringUtils.isNotBlank(cp)) {
					url = cp + tmp;
				}
			}
		}

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getValueField() {
		return valueField;
	}

	public void setValueField(String valueField) {
		this.valueField = valueField;
	}

	public String getTextField() {
		return textField;
	}

	public void setTextField(String textField) {
		this.textField = textField;
	}

	public String getPanelHeight() {
		return panelHeight;
	}

	public void setPanelHeight(String panelHeight) {
		this.panelHeight = panelHeight;
	}

	public String getPleaseSelect() {
		return pleaseSelect;
	}

	public void setPleaseSelect(String pleaseSelect) {
		this.pleaseSelect = pleaseSelect;
	}

	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	public String getIsValidateBox() {
		return isValidateBox;
	}

	public void setIsValidateBox(String isValidateBox) {
		this.isValidateBox = isValidateBox;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public String getPsValue() {
		return psValue;
	}

	public void setPsValue(String psValue) {
		this.psValue = psValue;
	}

	public String getMultiple() {
		return multiple;
	}

	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}
	
}
