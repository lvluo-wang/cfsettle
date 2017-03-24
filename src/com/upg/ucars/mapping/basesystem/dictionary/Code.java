package com.upg.ucars.mapping.basesystem.dictionary;

/**
 * Code entity.
 * 
 * @author MyEclipse Persistence Tools
 * @author shentuwy
 */

public class Code implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2725243165948217691L;

	// Fields

	/** ID */
	private Long				id;

	public static final String	ID					= "id";

	/** 字典KEY */
	private String				codeKey;

	public static final String	CODE_KEY			= "codeKey";

	/** 编号 */
	private String				codeNo;

	public static final String	CODE_NO				= "codeNo";

	/** 名称 */
	private String				codeName;

	public static final String	CODE_NAME			= "codeName";

	/** 语言 */
	private String				langType;

	public static final String	LANG_TYPE			= "langType";

	/** 接入点编号 */
	private String				miNo;

	public static final String	MI_NO				= "miNo";

	private Integer				sortNo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodeKey() {
		return codeKey;
	}

	public void setCodeKey(String codeKey) {
		this.codeKey = codeKey;
	}

	public String getCodeNo() {
		return codeNo;
	}

	public void setCodeNo(String codeNo) {
		this.codeNo = codeNo;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	public String getLangType() {
		return langType;
	}

	public void setLangType(String langType) {
		this.langType = langType;
	}

	public String getMiNo() {
		return miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	
}
