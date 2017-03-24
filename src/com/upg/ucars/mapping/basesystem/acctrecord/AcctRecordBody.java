package com.upg.ucars.mapping.basesystem.acctrecord;

/**
 * AcctRecordBody entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AcctRecordBody implements java.io.Serializable {

	// Fields

	private Long id;
	private String miNo;
	private String groupNo;
	private int serialNo;
	private String name;
	//private String recordType;
	private String valueType;

	// Constructors

	/** default constructor */
	public AcctRecordBody() {
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

	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

	public int getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

}