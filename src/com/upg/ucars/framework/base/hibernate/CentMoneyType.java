package com.upg.ucars.framework.base.hibernate;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.type.BigDecimalType;

@SuppressWarnings("serial")
public class CentMoneyType extends BigDecimalType {

	private static final BigDecimal	HUNDRED	= new BigDecimal(100);

	@Override
	public void set(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		if (value != null && value instanceof BigDecimal) {
			value = ((BigDecimal) value).multiply(HUNDRED);
		}
		super.set(st, value, index);
	}

	@Override
	public Object get(ResultSet rs, String name) throws HibernateException, SQLException {
		Object result = super.get(rs, name);
		if (result != null && result instanceof BigDecimal) {
			result = new BigDecimal(BigDecimal.valueOf(Double.parseDouble(((BigDecimal)result).divide(HUNDRED, 2, BigDecimal.ROUND_HALF_UP).toString()))  
		            .stripTrailingZeros().toPlainString());
		}
		return result;
	}

}
