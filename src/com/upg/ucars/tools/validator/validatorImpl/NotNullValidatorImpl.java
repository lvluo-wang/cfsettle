/*
 * 源程序名称: NotNullValidatorImpl.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.tools.validator.validatorImpl;


import org.apache.commons.lang.StringUtils;

import bsh.EvalError;
import bsh.Interpreter;

import com.upg.ucars.tools.validator.Validator;
import com.upg.ucars.tools.validator.annotation.NotNull;

/**
 * 
 * 功能说明：非空元数据校验实现类
 * @author shentuwy  
 * @date Dec 19, 2011 3:59:56 PM 
 *
 */

public class NotNullValidatorImpl implements Validator<NotNull,Object> {
	String condition;
	String[] paramValue;
	Object bean;

	public void init(NotNull annotation,Object bean) {
		this.condition = annotation.condition();
		this.paramValue = annotation.paramValues();
		this.bean = bean;
	}


	public boolean isValidatePass(Object value) throws Exception{
		Interpreter it  = null;
		if(StringUtils.isNotBlank(condition)){
			it = initInterpreter();
			if((Boolean)it.eval(condition)){
				if(value == null){
					return false;
				}
			}
		}else{
			if(value == null){
				return false;
			}
		}
		return true;
	}


	private Interpreter initInterpreter() throws EvalError {
		Interpreter it;
		String[] paramNames;
		StringBuilder paramName;
		it  = new Interpreter();
		condition = StringUtils.replaceEachRepeatedly(condition,new String[]{"this."}, new String[]{"obj."});
		it.set("obj", bean);
		if(paramValue != null && paramValue.length > 0){
			paramNames = StringUtils.substringsBetween(condition, "$", "$");
			if(paramNames != null){
				for(int i = 0; i < paramNames.length; i++){
					paramName = new StringBuilder(15);
					paramName.append("$");
					paramName.append(paramNames[i]);
					paramName.append("$");
					it.set(paramName.toString(), paramValue[i]);
				}
			}
		}
		return it;
	}
	
	
	

}
