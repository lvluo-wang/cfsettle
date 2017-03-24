/*
 * 源程序名称: DecimalRangeValidatorImpl.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.tools.validator.validatorImpl;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.upg.ucars.tools.validator.Validator;
import com.upg.ucars.tools.validator.annotation.NumberRange;

public class NumberRangeValidatorImpl implements Validator<NumberRange, Number> {
	NumberRange annotation = null;
	Object bean = null;
	boolean containMin = true;
	boolean containMax = true;

	public void init(NumberRange annotation, Object bean) {
		this.annotation = annotation;
		this.bean = bean;
		this.containMin = annotation.containMin();
		this.containMax = annotation.containMax();

	}

	public boolean isValidatePass(Number value) throws Exception {
		BigDecimal bdValue = null;
		BigDecimal min = null;
		BigDecimal max = null;

		if (value == null) {
			return true;
		}
		bdValue = getBigDecimal(value);
		if (StringUtils.isNotBlank(annotation.min())) {
			min = getBigDecimal(PropertyUtils.getProperty(bean, annotation.min()));
		}
		if (StringUtils.isNotBlank(annotation.max())) {
			max = getBigDecimal(PropertyUtils.getProperty(bean, annotation.max()));
		}
		if (containMin) {
			if (bdValue.compareTo(min) < 0) {
				return false;
			}
		} else {
			if (bdValue.compareTo(min) <= 0) {
				return false;
			}
		}
		if (containMax) {
			if (bdValue.compareTo(max) > 0) {
				return false;
			}
		} else {
			if (bdValue.compareTo(max) >= 0) {
				return false;
			}
		}
		return true;
	}

	private BigDecimal getBigDecimal(Object obj) {
		// StringBuilder error = null;
		// if(obj instanceof Integer){
		// return new BigDecimal(((Integer)obj).intValue());
		// }else if(obj instanceof Long){
		// return new BigDecimal(((Long)obj).longValue());
		// }else if(obj instanceof Float){
		// return new BigDecimal(((Float)obj).floatValue());
		// }else if(obj instanceof BigDecimal){
		// return (BigDecimal)obj;
		// }else{
		// error = new StringBuilder();
		// error.append("value is ");
		// error.append(obj);
		// error.append(",type is ");
		// error.append(obj.getClass().getName());
		// throw new IllegalArgumentException(error.toString());
		// }

		if (obj instanceof BigDecimal) {
			return (BigDecimal) obj;
		} else if (obj instanceof BigInteger) {
			return new BigDecimal((BigInteger) obj);
		} else {
			return new BigDecimal(obj.toString()).stripTrailingZeros();
		}
	}

}
