package com.upg.ucars.mapping.framework.bpm;

/**
 *
 * 机构流程关联实体。
 * <br>机构授权的流程信息，如果多个流程对应一个产品号，则需指定默认流程
 * 
 * @author MyEclipse Persistence Tools
 */

public class ProdProcMap implements java.io.Serializable {
	/**关联类型-接入关联流程*/
	public static final String MAP_TYPE_MEMBER_PROC = "1";	
	/**关联类型-机构产品默认流程*/
	public static final String MAP_TYPE_BRCH_PROC = "2";

	private static final long serialVersionUID = 1L;
	
	// Fields

	private Long id;
	private String miNo;
	private Long brchId;
	private Long procId;
	private String mapType;//关联类型见ProcMapConstant.MAP_TYPE_*
	private String isDefault;//是否默认1 是，0否
	


	/** default constructor */
	public ProdProcMap() {
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProcId() {
		return procId;
	}

	public void setProcId(Long procId) {
		this.procId = procId;
	}

	public Long getBrchId() {
		return brchId;
	}

	public void setBrchId(Long brchId) {
		this.brchId = brchId;
	}

	public String getMiNo() {
		return miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}
	/**
	 * 获取关联类型 
	 * @return 见ProdProcMap.MAP_TYPE_*
	 */
	public String getMapType() {
		return mapType;
	}
	/**
	 * 设置关联类型
	 * @param mapType ProdProcMap.MAP_TYPE_*
	 */
	public void setMapType(String mapType) {
		this.mapType = mapType;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
		
}