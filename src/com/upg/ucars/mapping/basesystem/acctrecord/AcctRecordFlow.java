package com.upg.ucars.mapping.basesystem.acctrecord;

/**
 * AcctRecordFlow entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AcctRecordFlow implements java.io.Serializable {

	// Fields

	private Long id;
	private Long acctFlowId;
	private Long tranId;
	private Long entityId;
	private String prodNo;
	private String miNo;

	// Constructors

	/** default constructor */
	public AcctRecordFlow() {
	}

	/** full constructor */
	public AcctRecordFlow(Long acctFlowId, Long tranId, Long entityId,
			String prodNo, String miNo) {
		this.acctFlowId = acctFlowId;
		this.tranId = tranId;
		this.entityId = entityId;
		this.prodNo = prodNo;
		this.miNo = miNo;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Long getTranId() {
		return this.tranId;
	}

	public void setTranId(Long tranId) {
		this.tranId = tranId;
	}

	public Long getEntityId() {
		return this.entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getProdNo() {
		return this.prodNo;
	}

	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}

	public String getMiNo() {
		return this.miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}

	public Long getAcctFlowId() {
		return acctFlowId;
	}

	public void setAcctFlowId(Long acctFlowId) {
		this.acctFlowId = acctFlowId;
	}

}