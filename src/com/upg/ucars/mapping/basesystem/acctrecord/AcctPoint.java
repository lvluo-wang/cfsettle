package com.upg.ucars.mapping.basesystem.acctrecord;

/**
 * AcctPoint entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AcctPoint implements java.io.Serializable {

	// Fields

	private Long id;
	private String prodNo;
	private String eventNo;

	// Constructors

	/** default constructor */
	public AcctPoint() {
	}

	/** full constructor */
	public AcctPoint(String prodNo, String eventNo) {
		this.prodNo = prodNo;
		this.eventNo = eventNo;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProdNo() {
		return this.prodNo;
	}

	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}

	public String getEventNo() {
		return this.eventNo;
	}

	public void setEventNo(String eventNo) {
		this.eventNo = eventNo;
	}

}