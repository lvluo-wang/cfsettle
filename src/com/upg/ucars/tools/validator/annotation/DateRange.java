/*
 * 源程序名称: DateRange.java 
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

import com.upg.ucars.tools.validator.validatorImpl.DateRangeValidatorImpl;

/**
 * 
 * 功能说明：日期范围校验
 * 支持数据类型:java.sql.Date/java.util.Date
 * 注意：
 * 1.参数min、max的值只能是当前bean的字段名，
 * 如果在校验过程中找不到对应的字段，将抛出异常.
 * 2.如果min或max的值为当前工作日时，请使用DateRange.busiDate常量
 * @author shentuwy  
 * @date Dec 20, 2011 10:17:39 AM 
 *
 */
@Documented
@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@ValidatorClass(DateRangeValidatorImpl.class)
public @interface DateRange {
	
	/**
	 * 当前工作日常量
	 */
	static final String busiDate = "busiDate";
	
	/**
	 * 最小值(当前bean字段名)
	 * 
	 * @return
	 */
	String min() default "";

	/**
	 * 最大值(当前bean字段名)
	 * 
	 * @return
	 */
	String max() default "";

	/**
	 * 是否包含最小值
	 * 
	 * @return
	 */
	boolean containMin() default true;

	/**
	 * 是否包含最大值
	 * 
	 * @return
	 */
	boolean containMax() default true; 

	/**
	 * 错误信息
	 * 
	 * @return
	 */
	String message() default "最大日期为 '{max}',最小日期为 '{min}'";
}
