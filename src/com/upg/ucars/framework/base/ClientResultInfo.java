package com.upg.ucars.framework.base;

import java.util.List;
import java.util.Map;

/**
 * Web Service API返回信息
 * @author hezw
 *
 */
@SuppressWarnings("unchecked")
public class ClientResultInfo<E> {
	/*
	 * 自定义异常码
	 */
	private String exceptionCode;
	/*
	 * 异常信息
	 */
	private String exceptionInfo;
	/*
	 * 返回对象集
	 */
	private E data;
	private List<E> listData;
	private Map mapData;
	/*
	 * 分页信息
	 */
	private Page page;
	/**
	 * 构造器
	 */
	public ClientResultInfo() {
		
	}
	public String getExceptionCode() {
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	public String getExceptionInfo() {
		return exceptionInfo;
	}
	public void setExceptionInfo(String exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public E getData() {
		return data;
	}
	public void setData(E data) {
		this.data = data;
	}
	public List<E> getListData() {
		return listData;
	}
	public void setListData(List<E> listData) {
		this.listData = listData;
	}
	public Map getMapData() {
		return mapData;
	}
	public void setMapData(Map mapData) {
		this.mapData = mapData;
	}
	
	
}
