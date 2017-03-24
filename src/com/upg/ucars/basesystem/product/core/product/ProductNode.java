package com.upg.ucars.basesystem.product.core.product;

import java.io.Serializable;

public class ProductNode implements Serializable {
	private Long prodId=null;
	private String prodNo=null;
	private String prodName=null;
	private String prodNameKey=null;
	private Long parentProdId=null;
	private String prodType=null;
	private String miNo=null;

	public ProductNode(Long prodId, String prodName, String prodNameKey,
			Long parentProdId, String prodType, String miNo,String prodNo) {
		super();
		this.prodId = prodId;
		this.prodName = prodName;
		this.prodNameKey = prodNameKey;
		this.parentProdId = parentProdId;
		this.prodType = prodType;
		this.miNo = miNo;
		this.prodNo=prodNo;
	}
	public ProductNode(){}
	public Long getProdId() {
		return prodId;
	}
	public void setProdId(Long prodId) {
		this.prodId = prodId;
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
	public Long getParentProdId() {
		return parentProdId;
	}
	public void setParentProdId(Long parentProdId) {
		this.parentProdId = parentProdId;
	}
	public String getMiNo() {
		return miNo;
	}
	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}
	public String getProdType() {
		return prodType;
	}
	public void setProdType(String prodType) {
		this.prodType = prodType;
	}
	public String getProdNo() {
		return prodNo;
	}
	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}
	
}
