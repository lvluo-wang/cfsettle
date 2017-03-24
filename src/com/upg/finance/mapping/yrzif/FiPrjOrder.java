package com.upg.finance.mapping.yrzif;

public class FiPrjOrder {

	private Long	id;
	private String	orderNo;
	private Long	uid;
	private Long	prjId;
	private Long	money;
	private Long	restMoney;
	private Integer	status;
	private String	trustPayStatus;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getPrjId() {
		return prjId;
	}

	public void setPrjId(Long prjId) {
		this.prjId = prjId;
	}

	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	public Long getRestMoney() {
		return restMoney;
	}

	public void setRestMoney(Long restMoney) {
		this.restMoney = restMoney;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTrustPayStatus() {
		return trustPayStatus;
	}

	public void setTrustPayStatus(String trustPayStatus) {
		this.trustPayStatus = trustPayStatus;
	}

}
