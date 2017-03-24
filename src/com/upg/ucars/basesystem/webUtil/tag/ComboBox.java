package com.upg.ucars.basesystem.webUtil.tag;

import java.io.Writer;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.components.UIBean;

import com.opensymphony.xwork2.util.ValueStack;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.util.JsonUtils;

/**
 * 
 * ComboBox
 * 
 * @author shentuwy
 * 
 */
public class ComboBox extends XCarsUIBean {

	private static final String				TEMPLATE			= "comboBox";

	private static final String				DEFAULT_VALUE_FIELD	= "value";
	private static final String				DEFAULT_TEXT_FIELD	= "text";

	/** 数据来源的url */
	protected String						url;
	/** json数据 */
	protected String						data;
	/** action属性 */
	protected String						list;
	/** 值key */
	protected String						valueField;
	/** 文本key */
	protected String						textField;
	/** 下拉面板高度 */
	protected String						panelHeight;
	/** 是否显示请选择 */
	protected String						pleaseSelect;
	/** 只读 */
	protected String						readonly;
	/** 验证 */
	protected String						isValidateBox;
	/** 请选择的值 */
	protected String						psValue;
	/** 多选 */
	protected String						multiple;
	/** 是否多选 */
	protected String						required;

	private Collection<?>					dataList;
	private String							initComboxText;

	private static final Map<String, Field>	FIELD_MAP			= new HashMap<String, Field>();

	static {
		try {
			FIELD_MAP.put("id", UIBean.class.getDeclaredField("id"));
			FIELD_MAP.put("name", UIBean.class.getDeclaredField("name"));
			FIELD_MAP.put("value", UIBean.class.getDeclaredField("value"));
			FIELD_MAP.put("disabled", UIBean.class.getDeclaredField("disabled"));
			FIELD_MAP.put("required", ComboBox.class.getDeclaredField("required"));
			FIELD_MAP.put("cssStyle", UIBean.class.getDeclaredField("cssStyle"));
			FIELD_MAP.put("cssClass", UIBean.class.getDeclaredField("cssClass"));
			FIELD_MAP.put("url", ComboBox.class.getDeclaredField("url"));
			FIELD_MAP.put("data", ComboBox.class.getDeclaredField("data"));
			FIELD_MAP.put("valueField", ComboBox.class.getDeclaredField("valueField"));
			FIELD_MAP.put("textField", ComboBox.class.getDeclaredField("textField"));
			FIELD_MAP.put("panelHeight", ComboBox.class.getDeclaredField("panelHeight"));
			FIELD_MAP.put("pleaseSelect", ComboBox.class.getDeclaredField("pleaseSelect"));
			FIELD_MAP.put("readonly", ComboBox.class.getDeclaredField("readonly"));
			FIELD_MAP.put("isValidateBox", ComboBox.class.getDeclaredField("isValidateBox"));
			FIELD_MAP.put("psValue", ComboBox.class.getDeclaredField("psValue"));
			FIELD_MAP.put("multiple", ComboBox.class.getDeclaredField("multiple"));
			FIELD_MAP.put("initComboxText", ComboBox.class.getDeclaredField("initComboxText"));

			// events
			FIELD_MAP.put("onchange", UIBean.class.getDeclaredField("onchange"));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public ComboBox(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	public boolean start(Writer writer) {
		return false;
	}

	public void evaluateExtraParams() {
		evaluateDisabled();
		evaluateDataList();
		if (dataList != null && dataList.size() > 0) {
			data = JsonUtils.objectToJsonString(dataList);
		}
		evaluateTextAndValueField();
		processInitComboxText();
		Map<String, Object> propConfig = getPropertyConfigInfos();
		addParameter("propConfig", propConfig);
	}

	public Map<String, Object> getPropertyConfigInfos() {
		Map<String, Object> ret = new HashMap<String, Object>();
		evaluateIsValidate();
		if (FIELD_MAP != null && FIELD_MAP.size() > 0) {
			for (Iterator<Map.Entry<String, Field>> it = FIELD_MAP.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, Field> entry = it.next();
				String key = entry.getKey();
				Object value = getPropertyValue(key);
				if (value != null) {
					addParameter(key, value);
					ret.put(key, value);
				}
			}
		}
		if (dynamicAttributes != null && dynamicAttributes.size() > 0) {
			for (Iterator<Map.Entry<String, Object>> it = dynamicAttributes.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, Object> entry = it.next();
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value != null) {
					ret.put(key, value);
				}
			}
		}
		return ret;
	}

	private Object getPropertyValue(String propertyName) {
		Object ret = null;
		if (StringUtils.isNotBlank(propertyName)) {
			Field field = FIELD_MAP.get(propertyName);
			if (field != null) {
				try {
					ret = field.get(this);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		return ret;
	}

	private void evaluateIsValidate() {
		if (isValidateBox == null) {
			if (StringUtils.equals(required, "true")) {
				isValidateBox = "true";
			}
		}
	}

	private void evaluateDisabled() {
		if (StringUtils.equals("true", disabled) || StringUtils.equals("disabled", disabled)) {
			if (StringUtils.isNotBlank(cssClass)) {
				cssClass = cssClass.replaceAll("xcarsui-disable", "");
			}
		} else {
			if (StringUtils.isNotBlank(cssClass) && cssClass.contains("xcarsui-disable")) {
				disabled = "disabled";
			}
		}
	}

	private void evaluateDataList() {
		if (StringUtils.isNotBlank(data)) {
			Collection<Map<String, Object>> tmp = JsonUtils.stringToCollection(data);
			if (tmp != null && tmp.size() > 0) {
				dataList = tmp;
			}
		} else {
			if (StringUtils.isNotBlank(list)) {
				Object obj = findValue(list);
				if (obj instanceof Collection) {
					dataList = (Collection<?>) findValue(list);
				}
			}
		}
	}

	private void processInitComboxText() {
		if (StringUtils.isNotBlank(value) && dataList != null && dataList.size() > 0) {
			String valueProp = StringUtils.isNotBlank(valueField) ? valueField : DEFAULT_VALUE_FIELD;
			String textProp = StringUtils.isNotBlank(textField) ? textField : DEFAULT_TEXT_FIELD;
			for (Object obj : dataList) {
				try {
					Object v = BeanUtils.getProperty(obj, valueProp);
					if (value.equals(v)) {
						initComboxText = BeanUtils.getProperty(obj, textProp);
						break;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	private void evaluateTextAndValueField() {
		if (dataList != null && dataList.size() > 0) {
			Object obj = dataList.iterator().next();
			if (obj instanceof Code) {
				textField = Code.CODE_NAME;
				valueField = Code.CODE_NO;
			}
		}
	}
	

	// -------------------------

	protected String getDefaultTemplate() {
		return TEMPLATE;
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

	public Map<String, Object> getDynamicAttributes() {
		return dynamicAttributes;
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
