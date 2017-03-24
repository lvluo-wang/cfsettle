/*
 * 源程序名称: Size.java 
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

import com.upg.ucars.tools.validator.validatorImpl.SizeValidatorImpl;

/**
 * 
 * 功能说明：长度校验 注意：如果是字符串，min、max的单位为“字节” 支持的数据类型：String /Collection/Map/arrays
 * 
 * @author shentuwy
 * @date Dec 19, 2011 4:27:45 PM
 * 
 */
@Documented
@Target(value = { ElementType.FIELD })
@Retention(value = RetentionPolicy.RUNTIME)
@ValidatorClass(SizeValidatorImpl.class)
public @interface Size {
	/**
	 * 最小值
	 * 
	 * @return
	 */
	String min() default "0";

	/**
	 * 最大值
	 * 
	 * @return
	 */
	String max() default "";

	/**
	 * 错误信息
	 * 
	 * @return
	 */
	String message() default "{fieldName}最小长度为 {min},最大长度为 {max}";

}
