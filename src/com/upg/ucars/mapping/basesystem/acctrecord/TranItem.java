package com.upg.ucars.mapping.basesystem.acctrecord;

/**
 * TranItem entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class TranItem implements java.io.Serializable {

	// Fields

	private Long id;
	private Long tranId;
	private Long acctItemId;
	private int serialNo;
	private Long recordBodyId;
	private String value;
	private int rowNo;
	private String groupNo;

	// Constructors

	public Long getRecordBodyId() {
		return recordBodyId;
	}

	public void setRecordBodyId(Long recordBodyId) {
		this.recordBodyId = recordBodyId;
	}

	/** default constructor */
	public TranItem() {
	}

	/** full constructor */
	public TranItem(Long tranId, Long acctItemId, Long recordBodyId, String value,
			int serialNo, int rowNo, String groupNo) {
		this.tranId = tranId;
		this.acctItemId = acctItemId;
		this.recordBodyId = recordBodyId;
		this.value = value;
		this.serialNo = serialNo;
		this.rowNo = rowNo;
		this.groupNo = groupNo;
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

	public Long getAcctItemId() {
		return this.acctItemId;
	}

	public void setAcctItemId(Long acctItemId) {
		this.acctItemId = acctItemId;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public int getRowNo() {
		return this.rowNo;
	}

	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}

	public String getGroupNo() {
		return this.groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}

}