/*
 * 源程序名称: BigDecimal_MoneyFormatter.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：Excel导入
 * 
 */

package com.upg.ucars.tools.imp.excel.format;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import com.upg.ucars.framework.exception.SysException;
import com.upg.ucars.tools.imp.excel.ColumnFormatter;

/**
 * 
 * 功能说明：Excel导入时，BigDecimal类型金额格式转换和校验类
 * @author shentuwy
 * @date Aug 26, 2011 1:55:45 PM 
 *
 */
public class BigDecimal_MoneyFormatter extends ColumnFormatter {

	@Override
	public BigDecimal format(Object columnValue) throws SysException {
		return new BigDecimal(columnValue.toString());
	}

	@Override
	public String getFormat() throws SysException {
		return "#,##0.00";
	}

	@Override
	public Boolean validate(Object columnValue) throws SysException {
		boolean isValid = false;
		String pattern = "#,##0.00";
		if(columnValue instanceof String){
			try {
				DecimalFormat df = new DecimalFormat(pattern);
				String value = df.format(df.parse(columnValue.toString()));
				if (value.equalsIgnoreCase(columnValue.toString())) {
					isValid = true;
				}
				new BigDecimal(columnValue.toString());
				isValid=true;
			} catch (Exception e) {
				isValid = false;
			}
		}
		if(columnValue instanceof Double){
			return true;
		}
		return isValid;
	}

}
