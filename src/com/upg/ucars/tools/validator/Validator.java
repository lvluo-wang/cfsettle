/*
 * 源程序名称: Validator.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.tools.validator;

import java.lang.annotation.Annotation;



/**
 * 
 * 功能说明：自定义校验接口 
 * 任何自定义校验器都需要实现该接口
 * 
 * @author shentuwy
 * @date Dec 19, 2011 3:20:31 PM
 * 
 */
public interface Validator<E extends Annotation,T> {
	/**
	 *  验证是否通过
	 * @param value 待校验值
	 * @return
	 */
	public boolean isValidatePass(T value) throws Exception;
	
	/**'
	 * 初始化校验器
	 * @param annotation  该实现类对应的校验Annotation
	 * @param bean 当前待校验bean
	 */
	public void init(E annotation,Object bean);
}
