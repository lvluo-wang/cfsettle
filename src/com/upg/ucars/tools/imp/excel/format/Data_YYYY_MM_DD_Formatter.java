package com.upg.ucars.tools.imp.excel.format;

import java.text.ParseException;
import java.util.Date;

import org.apache.poi.ss.usermodel.DateUtil;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.SysException;
import com.upg.ucars.tools.imp.excel.ColumnFormatter;
import com.upg.ucars.util.DateTimeUtil;

public class Data_YYYY_MM_DD_Formatter extends ColumnFormatter {

	@Override
	public Boolean validate(Object columnValue) throws SysException {
		if(columnValue instanceof String){
			return DateTimeUtil.isValidDate(columnValue.toString(), null);
		}
		if(columnValue instanceof Double){
			return true;
		}
		return false;
	}

	@Override
	public Date format(Object columnValue) throws SysException {
		try {
			if(columnValue instanceof String){
				return DateTimeUtil.parse(columnValue.toString(), "yyyy-MM-dd");
			}
			if(columnValue instanceof Double){
				return DateUtil.getJavaDate((Double)columnValue);
			}
			
		} catch (ParseException e) {
			ExceptionManager.throwException(SysException.class, ErrorCodeConst.SYS_ERROR,e);
		}
		return null;
	}

	@Override
	public String getFormat() throws SysException {
		// TODO Auto-generated method stub
		return "yyyy-MM-dd";
	}

}
