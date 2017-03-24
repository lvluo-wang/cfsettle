package com.upg.ucars.mapping.basesystem.acctrecord;

/**
 * AcctItem entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class AcctItem  implements
		java.io.Serializable {
	
	public final static String BelongType_Point = "1";
	public final static String BelongType_Memeber = "2";
	public final static String BelongType_MemberPoint = "3";
	
	public final static String ValueType_String = "1";
	public final static String ValueType_Number = "2";
	public final static String ValueType_Money = "3";
	public final static String ValueType_Date = "4";
	
	// Fields

	private Long id;
	private String itemNo;
	private String itemName;
	private Long pointId;
	private String miNo;
	private String belongType;
	private String express;
	private String valueType;

	// Constructors

	/** default constructor */
	public AcctItem() {
	}

	/** minimal constructor */
	public AcctItem(String belongType, String express, String valueType) {
		this.belongType = belongType;
		this.express = express;
		this.valueType = valueType;
	}

	/** full constructor */
	public AcctItem(String itemNo, String itemName, Long pointId, String miNo,
			String belongType, String express, String valueType) {
		this.itemNo = itemNo;
		this.itemName = itemName;
		this.pointId = pointId;
		this.miNo = miNo;
		this.belongType = belongType;
		this.express = express;
		this.valueType = valueType;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getItemNo() {
		return this.itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Long getPointId() {
		return this.pointId;
	}

	public void setPointId(Long pointId) {
		this.pointId = pointId;
	}

	public String getMiNo() {
		return this.miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}

	public String getBelongType() {
		return this.belongType;
	}

	public void setBelongType(String belongType) {
		this.belongType = belongType;
	}

	public String getExpress() {
		return this.express;
	}

	public void setExpress(String express) {
		this.express = express;
	}

	public String getValueType() {
		return this.valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}

}