/*
 * 源程序名称: DateAddOrSub.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.tools.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.upg.ucars.tools.validator.constant.Operator;
import com.upg.ucars.tools.validator.validatorImpl.DateAddOrSubValidatorImpl;


/**
 * 
 * 功能说明：日期加减校验
 * 即：startDate + (days) operator curDate
 * operator可以为Operator指定的比较运算符
 * days可以正数，也可以为负数
 * curDate为被DateAddOrSub标志的字段的值
 * @author shentuwy
 * @date Dec 22, 2011 3:59:58 PM 
 *
 */
@Documented
@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@ValidatorClass(DateAddOrSubValidatorImpl.class)
public @interface DateAddOrSub {

	
	/**
	 * 当前工作日常量
	 */
	static final String busiDate = "busiDate";

	
	/**
	 * 开始日期(当前bean字段名)
	 * @return
	 */
	String startDate();
	
	/**
	 * 间隔天数
	 * @return
	 */
	String days();
	
	/**
	 * 比较运算符
	 * @return
	 */
	Operator operator();
	
	/**
	 * 错误信息
	 * 
	 * @return
	 */
	String message() default "当前日期必须满足 {operator} {startDate}+({days})";

}
