package com.upg.ucars.framework.base;

import java.io.Serializable;
import java.util.List;

public class WorkFollowView implements Serializable {
	private String currentIndex;
	private List list;
	public WorkFollowView(){}
	public String getCurrentIndex() {
		return currentIndex;
	}
	public void setCurrentIndex(String currentIndex) {
		this.currentIndex = currentIndex;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	};
	
}
