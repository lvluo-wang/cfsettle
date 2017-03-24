/*
 * 源程序名称: EnumRange.java 
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

import com.upg.ucars.tools.validator.validatorImpl.EnumRangeValidatorImpl;

/**
 * 
 * 功能说明：枚举值范围校验
 * 
 * @author shentuwy  
 * @date Dec 20, 2011 10:24:49 AM 
 *
 */
@Documented
@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
@ValidatorClass(EnumRangeValidatorImpl.class)
public @interface EnumRange {
	/**
	 * 枚举值
	 * @return
	 */
	String[] enumValue();
	
	/**
	 * 错误信息
	 * 
	 * @return
	 */
	String message() default "当前值必须是 {enumValue}";
	

}
