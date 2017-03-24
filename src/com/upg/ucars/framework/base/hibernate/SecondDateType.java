package com.upg.ucars.framework.base.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

public class SecondDateType implements UserType {

	@Override
	public Object assemble(Serializable arg0, Object arg1) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		Date result = null;
		if (value != null) {
			Date d = (Date) value;
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(d.getTime());
			result = cal.getTime();
		}
		return result;
	}

	@Override
	public Serializable disassemble(Object arg0) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object obj1, Object obj2) throws HibernateException {
		if (obj1 == null || obj2 == null) {
			if (obj1 != null) {
				return false;
			} else if (obj2 != null) {
				return false;
			}
			return true;
		}
		Date d1 = (Date) obj1;
		Date d2 = (Date) obj2;
		return d1.equals(d2);
	}

	@Override
	public int hashCode(Object value) throws HibernateException {
		return value.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, Object arg2) throws HibernateException, SQLException {
		Date result = null;
		int value = rs.getInt(names[0]);
		if (value > 0) {
			result = new Date();
			result.setTime(value * 1000l);
		}
		return result;
	}

	@Override
	public void nullSafeSet(PreparedStatement statement, Object value, int index) throws HibernateException,
			SQLException {
		if (value == null) {
			statement.setNull(index, Types.INTEGER);
		} else {
			Date date = (Date) value;
			int v = (int) (date.getTime() / 1000);
			statement.setInt(index, v);
		}
	}

	@Override
	public Object replace(Object arg0, Object arg1, Object arg2) throws HibernateException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class returnedClass() {
		return Date.class;
	}

	@Override
	public int[] sqlTypes() {
		return SQL_TYPES;
	}

	private static final int[]	SQL_TYPES	= new int[] { Types.INTEGER };

}
