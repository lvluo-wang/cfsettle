/*
 * 源程序名称: NotNull.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：网银
 * 
 */

package com.upg.ucars.tools.validator.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.upg.ucars.tools.validator.validatorImpl.NotNullValidatorImpl;

/**
 * 
 * 功能说明：非空校验 注意：如果condition的值不为空，那么当满足condition时， 当前被验证的值不能为空。
 * 
 * @author shentuwy
 * @date Jul 29, 2011 1:40:15 PM
 * 
 */
@Documented
@Target(value = { ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
@ValidatorClass(NotNullValidatorImpl.class)
public @interface NotNull {

	/**
	 * 条件表达式(如果表达式中有字段名，请用"this.字段名"表示,参数值请用“$变量名$”)
	 * 
	 * @return
	 */
	String condition() default "";

	/**
	 * 条件表达式参数值
	 * 
	 * @return
	 */
	String[] paramValues() default {};

	/**
	 * 错误信息
	 * 
	 * @return
	 */
	String message() default "{fieldName}不能为空";

}
