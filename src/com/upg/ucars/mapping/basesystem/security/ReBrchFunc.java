package com.upg.ucars.mapping.basesystem.security;

/**
 * ReBrchFunc entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ReBrchFunc implements java.io.Serializable {

	// Fields
//	public static final String STRUTS_UNCHECK="0";//未审核
//	public static final String STRUTS_CHECK="1";//已审核
//	public static final String STRUTS_CHECKING="2";//审核中
	/** 未分配 */
	public static final String STATUS_UN_ASSIGN = "0";
	/** 分配未审核或是分配中 */
	public static final String STATUS_ASSIGNNING = "1";
	/** 已审核 */
	public static final String STATUS_CHECKED = "2";
	/** 审核中 */
	public static final String STATUS_CHECKING = "3";
	
	private Long id;
	private Long brchId;
	private Long funcId;
	private String status;

	// Constructors

	/** default constructor */
	public ReBrchFunc() {
	}

	/** minimal constructor */
	public ReBrchFunc(Long id) {
		this.id = id;
	}

	/** full constructor */
	public ReBrchFunc(Long id, String status) {
		this.id = id;
		this.status = status;
	}

	public ReBrchFunc(Long brchId, Long funcId) {
		super();
		this.brchId = brchId;
		this.funcId = funcId;
	}

	// Property accessors

	public ReBrchFunc(Long brchId, Long funcId, String status) {
		super();
		this.brchId = brchId;
		this.funcId = funcId;
		this.status = status;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}



	public Long getBrchId() {
		return brchId;
	}

	public void setBrchId(Long brchId) {
		this.brchId = brchId;
	}

	public Long getFuncId() {
		return funcId;
	}

	public void setFuncId(Long funcId) {
		this.funcId = funcId;
	}

}