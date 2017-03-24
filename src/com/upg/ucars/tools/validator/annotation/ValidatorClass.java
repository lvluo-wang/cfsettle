/*
 * 源程序名称: ValidatorClass.java 
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

import com.upg.ucars.tools.validator.Validator;


/**
 * 
 * 功能说明：指定annotation的校验实现类
 * 
 * @author shentuwy
 * @date Dec 19, 2011 11:10:09 AM
 * 
 */
@Documented
@Target(value = { ElementType.ANNOTATION_TYPE })
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ValidatorClass {
	public Class<? extends Validator> value();
}
