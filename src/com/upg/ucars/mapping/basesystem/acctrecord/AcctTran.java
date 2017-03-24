package com.upg.ucars.mapping.basesystem.acctrecord;

/**
 * AcctTran entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AcctTran implements java.io.Serializable {

	// Fields

	private Long id;
	private String miNo;
	private String tranNo;
	private String tranName;
	private Long pointId;
	private String express;//表达式，判断场景是否满足此交易

	// Constructors

	/** default constructor */
	public AcctTran() {
	}

	/** minimal constructor */
	public AcctTran(String tranNo) {
		this.tranNo = tranNo;
	}

	/** full constructor */
	public AcctTran(String miNo, String tranNo, String tranName, Long pointId) {
		this.miNo = miNo;
		this.tranNo = tranNo;
		this.tranName = tranName;
		this.pointId = pointId;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMiNo() {
		return this.miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}

	public String getTranNo() {
		return this.tranNo;
	}

	public void setTranNo(String tranNo) {
		this.tranNo = tranNo;
	}

	public String getTranName() {
		return this.tranName;
	}

	public void setTranName(String tranName) {
		this.tranName = tranName;
	}

	public Long getPointId() {
		return this.pointId;
	}

	public void setPointId(Long pointId) {
		this.pointId = pointId;
	}

	public String getExpress() {
		return express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

}