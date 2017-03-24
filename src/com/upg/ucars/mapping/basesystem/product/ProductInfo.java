package com.upg.ucars.mapping.basesystem.product;


/**
 * 产品信息
 * 
 * @author shentuwy
 * @date 2012-7-4
 *
 */
public class ProductInfo implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1404758814627502560L;
	
	/**
	 * 业务
	 */
	public static final String BUSI="1";
	/**
	 * 主业务
	 */
	public static final String MAIN_BUSI="2";
	/**
	 * 其他
	 */
	public static final String OTHER="3";
	
	/** ID */
	private Long id;
	/** 产品编号 */
	private String prodNo;
	/** 产品名称 */
	private String prodName;
	/** 产品名称国际化key */
	private String prodNameKey;
	/** 产品url */
	private String prodUrl;
	/** 产品类型1-业务产品，2-其他 */
	private String prodType;
	/** 父级产品id */
	private Long parentProdId;
	/** 同一父级下的子节点间用于确定先后顺序 */
	private Long sortNo;
	/** 说明与备注 */
	private String remark;
	/** 版本号 */
	private Long version;
	public ProductInfo(){};


	public String getProdNo() {
		return prodNo;
	}

	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getProdNameKey() {
		return prodNameKey;
	}

	public void setProdNameKey(String prodNameKey) {
		this.prodNameKey = prodNameKey;
	}

	public String getProdUrl() {
		return prodUrl;
	}

	public void setProdUrl(String prodUrl) {
		this.prodUrl = prodUrl;
	}

	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	public Long getParentProdId() {
		return parentProdId;
	}

	public void setParentProdId(Long parentProdId) {
		this.parentProdId = parentProdId;
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

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		if (remark!=null && remark.length()>100){//长度控制
			remark = remark.substring(0, 100);
		}
		this.remark = remark;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductInfo other = (ProductInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
}