package com.upg.ucars.mapping.basesystem.acctrecord;

/**
 * AcctTranExpr entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AcctTranExpr implements java.io.Serializable {

	// Fields

	private Long id;
	private Long tranId;
	private String name;
	private String express;

	// Constructors

	/** default constructor */
	public AcctTranExpr() {
	}

	/** minimal constructor */
	public AcctTranExpr(Long tranId, String express) {
		this.tranId = tranId;
		this.express = express;
	}

	/** full constructor */
	public AcctTranExpr(Long tranId, String name, String express) {
		this.tranId = tranId;
		this.name = name;
		this.express = express;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExpress() {
		return this.express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

}