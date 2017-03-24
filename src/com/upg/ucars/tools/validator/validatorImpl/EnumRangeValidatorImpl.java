/*
 * 源程序名称: EnumRangeValidatorImpl.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.tools.validator.validatorImpl;



import java.util.Arrays;

import org.apache.commons.collections.CollectionUtils;

import com.upg.ucars.tools.validator.Validator;
import com.upg.ucars.tools.validator.annotation.EnumRange;

public class EnumRangeValidatorImpl implements Validator<EnumRange,Object> {
	String[] enumValue = null;
	
	
	public void init(EnumRange annotation, Object bean) {
		this.enumValue = annotation.enumValue();
	}

	public boolean isValidatePass(Object value) throws Exception {
		if(value == null || "".equals(value.toString())){
			return true;
		}
		return CollectionUtils.containsAny(Arrays.asList(enumValue),Arrays.asList(value.toString()));
	}

}
