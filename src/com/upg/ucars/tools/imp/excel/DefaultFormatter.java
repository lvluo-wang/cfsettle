package com.upg.ucars.tools.imp.excel;


public class DefaultFormatter extends ColumnFormatter {
	public static String CODE="STRING";

	@Override
	public Boolean validate(Object columnValue) throws Exception {
		return true;
	}

	@Override
	public Object format(Object columnValue) throws Exception {
		// TODO Auto-generated method stub
		return columnValue;
	}

	@Override
	public String getFormat() throws Exception {
		// TODO Auto-generated method stub
		return CODE;
	}



}
