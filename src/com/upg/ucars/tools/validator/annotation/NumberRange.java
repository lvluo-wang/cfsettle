/*
 * 源程序名称: DecimalRange.java 
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

import com.upg.ucars.tools.validator.validatorImpl.NumberRangeValidatorImpl;

/**
 * 
 * 功能说明：取值范围校验 
 * 支持的数据类型：BigDecimal/int/Integer/long/Long/float/Float
 * 注意：参数min、max的值只能是当前bean的字段名，
 * 如果在校验过程中找不到对应的字段，将抛出异常
 * @author shentuwy
 * @date Dec 20, 2011 9:02:04 AM
 * 
 */
@Documented
@Target(value = { ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
@ValidatorClass(NumberRangeValidatorImpl.class)
public @interface NumberRange {
	/**
	 * 最小值
	 * 
	 * @return
	 */
	String min();

	/**
	 * 最大值
	 * 
	 * @return
	 */
	String max();

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
	String message() default "最小值为 '{min}',最大值为 '{max}'";
}
