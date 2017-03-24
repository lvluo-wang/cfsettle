package com.upg.ucars.mapping.basesystem.product;

import java.io.Serializable;
/**
 * 接入产品信息
 * @author shentuwy
 * @date 2011-3-20 下午09:35:20
 *
 */
public class MemberProductInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6486744285218219509L;
	
	/** ID */
	private Long id;
	/** 产品id */
	private Long prodId;
	/** 接入编号 */
	private String miNo;
	/** 产品别名 */
	private String prodAlias;
	/** 同一父节点下的子节点顺序 */
	private Long sortNo;
	/** 父级节点id */
	private Long parentProdId;
	/** 版本号 */
	private Long version;
	/** 是否需要审批 */
	private String isAudit;
	/** 是否需要复核 */
	private String isCheck;
	
	public MemberProductInfo(){}

	public String getProdAlias() {
		return prodAlias;
	}
	public void setProdAlias(String prodAlias) {
		this.prodAlias = prodAlias;
	}
	public Long getSortNo() {
		return sortNo;
	}
	public void setSortNo(Long sortNo) {
		this.sortNo = sortNo;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProdId() {
		return prodId;
	}

	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}

	public String getMiNo() {
		return miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}

	public Long getParentProdId() {
		return parentProdId;
	}

	public void setParentProdId(Long parentProdId) {
		this.parentProdId = parentProdId;
	}

	public String getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}



}
