/*
 * 源程序名称: Valitator.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.tools.validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.StringTokenizer;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.upg.ucars.tools.validator.annotation.Name;
import com.upg.ucars.tools.validator.annotation.ValidatorClass;

/**
 * 
 * 功能说明：对象属性值验证类
 * 
 * @author shentuwy
 * @date Dec 20, 2011 1:46:00 PM
 * 
 */
public class ValidatorFactory {

	private static final ValidatorFactory	INSTANCE	= new ValidatorFactory();

	private ValidatorFactory() {

	}

	public static final ValidatorFactory getInstance() {
		return INSTANCE;
	}

	@SuppressWarnings("unchecked")
	public ValidateResult validate(Object obj) throws Exception {
		StringBuilder allFieldMessage = new StringBuilder();
		Field[] fields = obj.getClass().getDeclaredFields();
		int fieldLen = fields.length;
		for (int i = 0 ; i < fieldLen; i++) {
			Field f = fields[i];
			StringBuilder fieldMessage = new StringBuilder();
			Annotation[] annotations = f.getDeclaredAnnotations();
			if (annotations == null || annotations.length < 1) {
				continue;
			}
			Object value = getValue(obj, f.getName());
			String filedName = null;
			Name name = f.getAnnotation(Name.class);
			if (name != null && StringUtils.isNotBlank(name.value())) {
				filedName = name.value();
			}
			int len = annotations.length;
			for (int j = 0; j < len; j++) {
				Annotation a = annotations[j];
				Validator validator = getValidator(a, obj);
				if (validator == null || validator.isValidatePass(value)) {
					continue;
				}
				String msgTemplate = getmsgTemplate(a);
				appendMessage(fieldMessage, msgTemplate, a, value, filedName,j<len-1);
			}
			String fieldName = f.getName();
			appendFieldMessage(allFieldMessage, fieldName, fieldMessage,i < fieldLen-1);
		}
		if (allFieldMessage.length() > 0) {
			return new ValidateResult(false, allFieldMessage.toString());
		} else {
			return new ValidateResult(true, null);
		}
	}

	@SuppressWarnings("unchecked")
	private static final Validator getValidator(Annotation a, Object obj) throws Exception {
		Validator validator = null;
		ValidatorClass validatorClass = null;
		validatorClass = a.annotationType().getAnnotation(ValidatorClass.class);
		// if (validatorClass == null) {
		// throw new Exception("can't find validatorClass of " +
		// a.getClass().getName());
		// }
		if (validatorClass != null) {
			validator = validatorClass.value().newInstance();
			validator.init(a, obj);
		}
		return validator;
	}

	private static final String getmsgTemplate(Annotation a) throws Exception {
		return (String) invokeAnnotationMethod(a, "message");
	}

	private static Object invokeAnnotationMethod(Annotation a, String methodName) throws Exception {
		Method method = null;
		method = a.getClass().getMethod(methodName, (Class[]) null);
		if (method == null) {
			throw new NoSuchMethodException("can't find " + methodName + " method");
		}
		return method.invoke(a);
	}

	private static final void appendMessage(StringBuilder fieldMessage, String msgTemplate, Annotation a, Object value,
			String fieldName,boolean appendSuffix) {
		if (fieldMessage == null) {
			fieldMessage = new StringBuilder();
		}
		if (StringUtils.isNotBlank(msgTemplate)) {
			if (fieldName == null) {
				fieldName = "";
			}
			msgTemplate = msgTemplate.replaceAll("\\{fieldName\\}", fieldName);
		}
		String str = replaceMessage(msgTemplate, a);
		fieldMessage.append(str);
		if (appendSuffix) {
			fieldMessage.append(",");
		}
		/*
		 * fieldMessage.append("当前值为 "); if (value instanceof java.util.Date ||
		 * value instanceof java.sql.Date) {
		 * fieldMessage.append(DateTimeUtil.get_YYYY_MM_DD_Date((java.util.Date)
		 * value)); } else { fieldMessage.append("'");
		 * fieldMessage.append(value); fieldMessage.append("'"); }
		 */

	}

	private static final void appendFieldMessage(StringBuilder allFieldMessage, String fieldName,
			StringBuilder fieldMessage,boolean appendSuffix) {
		if (fieldMessage.length() > 0) {
			// allFieldMessage.append("{");
			// allFieldMessage.append(fieldName);
			// allFieldMessage.append("：");
			allFieldMessage.append(fieldMessage);
			// allFieldMessage.append("}");
			if (appendSuffix) {
				allFieldMessage.append("\n<br>");
			}
		}
	}

	private static final Object getValue(Object obj, String propertyName) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		return PropertyUtils.getProperty(obj, propertyName);
	}

	private static final String replaceMessage(String message, Annotation annotation) {
		StringBuilder error = new StringBuilder();
		String token = null;
		boolean flag = false;
		Method method = null;
		Object paramValue = null;
		StringTokenizer tokens = new StringTokenizer(message, "{}", true);
		while (tokens.hasMoreTokens()) {
			token = tokens.nextToken();
			if ("{".equals(token)) {
				flag = true;
			} else if ("}".equals(token)) {
				flag = false;
			} else if (!flag) {
				error.append(token);
			} else {
				try {
					method = annotation.getClass().getMethod(token, (Class[]) null);
				} catch (Exception e) {
					method = null;
				}
				try {
					if (method != null) {
						paramValue = method.invoke(annotation);
						if (paramValue != null && paramValue.getClass().isArray()) {
							error.append(Arrays.toString((Object[]) paramValue));
						} else {
							error.append(paramValue);
						}

					}
				} catch (Exception e) {
					throw new IllegalArgumentException("could not get message parameter[" + token + "],", e);
				}
			}
		}
		return error.toString();
	}

}
