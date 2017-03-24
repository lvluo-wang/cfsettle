package com.upg.demo.loan.core.loaninfo;

import java.io.Serializable;

public class SearchBean implements Serializable {
	private static final long serialVersionUID = -9066749239681575074L;
	private String no;
	private String userName;
	private String status;
	private int pageSize = 50;
	private int currentPage = 1;
	
	public String getNo() {
		return no;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
