package com.upg.ucars.tools.imp.excel.format;

import java.text.DecimalFormat;

import com.upg.ucars.framework.exception.SysException;
import com.upg.ucars.tools.imp.excel.ColumnFormatter;
/**
 * #,##0.00
 * @author xjj
 *
 */
public class Double_MoneyFormatter1 extends ColumnFormatter {

	@Override
	public Double format(Object columnValue) throws SysException {
		if(columnValue instanceof String){
			return Double.valueOf(columnValue.toString());
		}
		if(columnValue instanceof Double){
			return (Double)columnValue;
		}
		return 0D;
	}

	@Override
	public String getFormat() throws SysException {
		// TODO Auto-generated method stub
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
				Double.valueOf(columnValue.toString());
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
