package com.upg.ucars.framework.base.queryComponent;

import java.io.Serializable;

public class SortObject implements Serializable, Cloneable {
	
	/** 显示名 */
	private String viewName;
	
	/** 字段名 */
	private String sortName;
	
	/** 升序降序: asc 升序，desc 降序 */
	private String sortType;

	public SortObject() {

	}

	public SortObject(String sortName) {
		super();
		this.sortName = sortName;
		this.sortType = "asc";
	}
	
	/**
	 * 
	 * @param sortName 
	 * @param sortType 
	 */
	public SortObject(String sortName, String sortType) {
		super();
		this.sortName = sortName;
		this.sortType = sortType;
	}

	public SortObject(String viewName, String sortName, String sortType) {
		super();
		this.viewName = viewName;
		this.sortName = sortName;
		this.sortType = sortType;
	}

	public String getSortName() {
		return sortName;
	}

	public void setSortName(String sortName) {
		this.sortName = sortName;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
