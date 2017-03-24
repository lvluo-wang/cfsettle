/*
 * 源程序名称: DigitsValidatorImpl.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.tools.validator.validatorImpl;



import java.math.BigDecimal;

import com.upg.ucars.tools.validator.Validator;
import com.upg.ucars.tools.validator.annotation.Digits;

public class DigitsValidatorImpl implements Validator<Digits,Number> {
	private int precision;
	private int scale;

	public void init(Digits annotation, Object bean) {
		try {
			precision = Integer.parseInt(annotation.precision());
		} catch (NumberFormatException e) {
			throw new NumberFormatException("precision of @Digits must be Integer");
		}
		try {
			scale = Integer.parseInt(annotation.scale());
		} catch (NumberFormatException e) {
			throw new NumberFormatException("scale of @Digits must be Integer");
		}
		validateParameters();
	}

	public boolean isValidatePass(Number value) throws Exception {
		BigDecimal bdValue = null;
		if(value == null){
			return true;
		}
		if (value instanceof BigDecimal ) {
			bdValue = ( BigDecimal ) value;
		}
		else {
			bdValue = new BigDecimal( value.toString() ).stripTrailingZeros();
		}
		return bdValue.precision() <= precision && bdValue.scale() <= scale;
	}
	
	private void validateParameters() {
		if ( precision < 0 ) {
			throw new IllegalArgumentException( "The length of the precision part cannot be negative." );
		}
		if ( scale < 0 ) {
			throw new IllegalArgumentException( "The length of the scale part cannot be negative." );
		}
	}

}
