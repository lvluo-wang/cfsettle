package com.upg.ucars.util;

import java.util.HashMap;
import java.util.Map;

public class SQLCreater {
	private StringBuffer sql;
	private Map<String, Object> parameterMap;
	private boolean isFirst;
	private boolean hasWhere;
	private boolean hasOrderBy;

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public SQLCreater(String baseSQL) {
		this(baseSQL, true);
	}

	public SQLCreater(String baseSQL, boolean hasWhere) {
		this.sql = new StringBuffer();
		parameterMap = new HashMap<String, Object>();
		this.sql.append(baseSQL);
		this.isFirst = true;
		this.hasWhere = hasWhere;
	}

	public void addExpression(String operator, String expression,
			boolean precondition) {
		if (precondition) {
			if (!(this.isFirst)) {
				this.sql.append(" " + operator + " ");
			} else {
				if (!(this.hasWhere))
					this.sql.append(" WHERE ");

				this.isFirst = false;
			}
			this.sql.append(expression);
		}
	}

	public void addExpression(String operator, String expression,
			String parameterName, Object parameterValue, boolean precondition) {
		if (precondition) {
			if (!(this.isFirst)) {
				this.sql.append(" " + operator + " ");
			} else {
				if (!(this.hasWhere))
					this.sql.append(" WHERE ");

				this.isFirst = false;
			}
			this.sql.append(expression);
			this.parameterMap.put(parameterName, parameterValue);
		}
	}

	public void and(String expression, boolean precondition) {
		addExpression("AND", expression, precondition);
	}

	public void and(String expression, String parameterName,
			Object parameterValue, boolean precondition) {
		addExpression("AND", expression, parameterName, parameterValue,
				precondition);
	}

	public void or(String expression, boolean precondition) {
		addExpression("OR", expression, precondition);
	}

	public void or(String expression, String parameterName,
			Object parameterValue, boolean precondition) {
		addExpression("OR", expression, parameterName, parameterValue,
				precondition);
	}
	
	public void orderBy(String columnName) {
		orderBy(columnName, false);
	}

	public void orderBy(String columnName, boolean isDesc) {
		if (!(this.hasOrderBy)) {
			this.sql.append(" ORDER BY ");
		} else {
			this.sql.append(", ");
		}

		this.sql.append(columnName);
		if (isDesc) {
			this.sql.append(" DESC");
		}

		this.hasOrderBy = true;
	}

	public void orderByDesc(String columnName) {
		orderBy(columnName, true);
	}

	public String getSql() {
		return sql.toString();
	}

	public Map<String, Object> getParameterMap() {
		return parameterMap;
	}

}
