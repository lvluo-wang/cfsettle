package com.upg.ucars.basesystem.webUtil.tag;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.components.Property;

import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import com.upg.ucars.util.StringUtil;

/**
 * 
 * shentuwy
 * 
 */

public class XProperty extends Property {

	private static final Logger LOG = LoggerFactory.getLogger(XProperty.class);
	
	private static final Map<String,Method> SUPER_CLASS_METHODS = new HashMap<String,Method>();
	

	private boolean crlfHtml = true;

	private String xvalue;

	public XProperty(ValueStack stack) {
		super(stack);
	}

	@Override
	public void setValue(String value) {
		super.setValue(value);
		xvalue = value;
	}

	@Override
	public boolean start(Writer writer) {

		String actualValue = null;

		if (xvalue == null) {
			xvalue = "top";
		} else {
			xvalue = stripExpressionIfAltSyntax(xvalue);
		}

		// exception: don't call findString(), since we don't want the
		// expression parsed in this one case. it really
		// doesn't make sense, in fact.
		actualValue = (String) getStack().findValue(xvalue, String.class,
				throwExceptionOnELFailure);

		try {
			if (actualValue != null) {
				writer.write(xprepare(actualValue));
			} else {

				String defaultValue = (String) getSuperFieldValue("defaultValue");

				if (defaultValue != null) {
					writer.write(xprepare(defaultValue));
				}
			}
		} catch (IOException e) {
			LOG.info("Could not print out value '" + xvalue + "'", e);
		}

		return true;
	}

	private String xprepare(String value) {
		String result = (String)invokeSuperMethod("prepare", value);
		if (crlfHtml) {
			result = StringUtil.convertHTML(result);
		}
		return result;
	}

	private Object getSuperFieldValue(String fieldName) {
		try {
			Field field = getClass().getSuperclass().getDeclaredField(fieldName);
			if (field != null) {
				field.setAccessible(true);
				return field.get(this);
			} else {
				throw new RuntimeException("父类没有对应的字段[" + fieldName + "]");
			}
		} catch (Exception ex) {
			throw new RuntimeException("获取父类字段值出错[" + fieldName + "]", ex);
		}
	}
	
	private Object invokeSuperMethod(String methodName, String value) {
		try {
			Method method =	SUPER_CLASS_METHODS.get(methodName); 
			if (method == null) {
				method = getClass().getSuperclass().getDeclaredMethod(methodName,
					new Class[] { String.class });
			}
			if (method != null) {
				method.setAccessible(true);
				return method.invoke(this, value);
			} else {
				throw new RuntimeException("父类没有方法[" + methodName + "]");
			}
		} catch (Exception ex) {
			throw new RuntimeException("调用父类的方法[" + methodName + "]出错 ", ex);
		}
	}

	public void setCrlfHtml(boolean crlfHtml) {
		this.crlfHtml = crlfHtml;
	}

}
