package com.upg.ucars.mapping.basesystem.security;

/**
 * 机构
 * 
 * @author shentuwy
 * 
 */
public class Branch implements java.io.Serializable {

	private static final long	serialVersionUID	= -6018399241431811470L;

	/** 机构id */
	private Long				brchId;
	public static final String	BRCH_ID				= "brchId";
	/** 机构名称 */
	private String				brchName;
	public static final String	BRCH_NAME			= "brchName";
	/** 机构级别 */
	private Long				brchLevel;
	public static final String	BRCH_LEVEL			= "brchLevel";
	/** 机构编号 */
	private String				brchNo;
	public static final String	BRCH_NO				= "brchNo";
	/** 省份 */
	private String				province;
	public static final String	PROVINCE			= "province";
	/** 城市 */
	private String				city;
	public static final String	CITY				= "city";
	/** 地址 */
	private String				address;
	public static final String	ADDRESS				= "address";
	/** 机构状态1：有效，2：无效 */
	private String				brchStatus;
	public static final String	BRCH_STATUS			= "brchStatus";
	/** 权限状态：0-未分配，1-分配待审核，2-已分配，3-审核中 */
	private String				funcStatus;
	/** 归属接入编号 */
	private String				miNo;
	public static final String	MI_NO				= "miNo";
	/** 树形码 */
	private String				treeCode;
	public static final String	TREE_CODE			= "treeCode";
	/** 上级数型码 */
	private String				parentTreeCode;
	public static final String	PARENT_TREE_CODE	= "parentTreeCode";
	/** 上级名称 */
	private String				parentBrchName;
	/** 接入者名称 */
	private String				miName;
	/** 版本号 */
	private Integer				version;
	/** 机构属性 */
	private String				ledgerAttr;

	public Branch() {
	}

	public Branch(Long brchId, String brchName, Long brchLevel, String brchNo, String brchStatus, String miNo,
			String treeCode, Integer version) {
		super();
		this.brchId = brchId;
		this.brchName = brchName;
		this.brchLevel = brchLevel;
		this.brchNo = brchNo;
		this.brchStatus = brchStatus;
		this.miNo = miNo;
		this.treeCode = treeCode;
		this.version = version;
	}

	public Long getBrchId() {
		return brchId;
	}

	public void setBrchId(Long brchId) {
		this.brchId = brchId;
	}

	public String getBrchNo() {
		return brchNo;
	}

	public void setBrchNo(String brchNo) {
		this.brchNo = brchNo;
	}

	public String getMiNo() {
		return miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}

	public String getTreeCode() {
		return treeCode;
	}

	public void setTreeCode(String treeCode) {
		this.treeCode = treeCode;
	}

	public String getBrchName() {
		return brchName;
	}

	public void setBrchName(String brchName) {
		this.brchName = brchName;
	}

	public Long getBrchLevel() {
		return brchLevel;
	}

	public void setBrchLevel(Long brchLevel) {
		this.brchLevel = brchLevel;
	}

	public String getBrchStatus() {
		return brchStatus;
	}

	public void setBrchStatus(String brchStatus) {
		this.brchStatus = brchStatus;
	}

	public String getMiName() {
		return miName;
	}

	public void setMiName(String miName) {
		this.miName = miName;
	}

	public String getParentTreeCode() {
		return parentTreeCode;
	}

	public void setParentTreeCode(String parentTreeCode) {
		this.parentTreeCode = parentTreeCode;
	}

	public String getParentBrchName() {
		return parentBrchName;
	}

	public void setParentBrchName(String parentBrchName) {
		this.parentBrchName = parentBrchName;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getFuncStatus() {
		return funcStatus;
	}

	public void setFuncStatus(String funcStatus) {
		this.funcStatus = funcStatus;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLedgerAttr() {
		return ledgerAttr;
	}

	public void setLedgerAttr(String ledgerAttr) {
		this.ledgerAttr = ledgerAttr;
	}
	
}
