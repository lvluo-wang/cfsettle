/*
 * 源程序名称: DateAddOrSubValidatorImpl.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.tools.validator.validatorImpl;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;

import com.upg.ucars.basesystem.busidate.util.BusiDateUtil;
import com.upg.ucars.tools.validator.Validator;
import com.upg.ucars.tools.validator.annotation.DateAddOrSub;
import com.upg.ucars.tools.validator.constant.Operator;

public class DateAddOrSubValidatorImpl implements Validator<DateAddOrSub, Date> {
	String days;
	String startDate;
	Operator operator;
	Object bean;

	public void init(DateAddOrSub annotation, Object bean) {
		this.days = annotation.days();
		this.startDate = annotation.startDate();
		this.operator = annotation.operator();
		this.bean = bean;

	}

	public boolean isValidatePass(Date value) throws Exception {
		Date startDt = null;
		int intervalDays = 0;
		Calendar sc = Calendar.getInstance();
		Calendar curC = Calendar.getInstance();
		if (value == null) {
			return true;
		}
		if (DateAddOrSub.busiDate.equals(startDate)) {
			startDt = BusiDateUtil.getCurBusiDate();
		} else {
			startDt = (Date) PropertyUtils.getProperty(bean, startDate);
		}
		
		try {
			intervalDays = Integer.parseInt(days);
		} catch (NumberFormatException e) {
			intervalDays = (Integer) PropertyUtils.getProperty(bean, days);
		}
		sc.setTime(startDt);
		curC.setTime(value);
		curC.add(Calendar.DAY_OF_MONTH, intervalDays);
		if (Operator.gt.equals(operator)) {
			return curC.compareTo(sc) > 0 ? true : false;
		} else if (Operator.lt.equals(operator)) {
			return curC.compareTo(sc) < 0 ? true : false;
		} else if (Operator.eq.equals(operator)) {
			return curC.compareTo(sc) == 0 ? true : false;
		} else if (Operator.ge.equals(operator)) {
			return curC.compareTo(sc) >= 0 ? true : false;
		} else if (Operator.le.equals(operator)) {
			return curC.compareTo(sc) <= 0 ? true : false;
		}
		return false;
	}

}
