/*
 * 源程序名称: Digits.java 
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

import com.upg.ucars.tools.validator.validatorImpl.DigitsValidatorImpl;

/**
 * 
 * 功能说明：精度(precision)和准度(scale)校验.对应数据库中的Number(precision,scale)
 * 支持的数据类型：BigDecimal/int/Integer/long/Long/float/Float
 * 
 * @author shentuwy
 * @date Dec 20, 2011 9:48:48 AM
 * 
 */
@Documented
@Target(value = { ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
@ValidatorClass(DigitsValidatorImpl.class)
public @interface Digits {
	/**
	 * 精度
	 * 
	 * @return
	 */
	String precision() default "0";

	/**
	 * 准度
	 * 
	 * @return
	 */
	String scale() default "0";

	/**
	 * 错误信息
	 * 
	 * @return
	 */
	String message() default "当前数字精度必须是 {precision},当前数字准度必须是 {scale}";

}
