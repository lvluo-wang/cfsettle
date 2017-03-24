package com.upg.ucars.mapping.basesystem.product;

import java.io.Serializable;

/**
 * 
 * MemberProductAttribute
 *
 * @author shentuwy
 *
 */
public class MemberProductAttribute implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6544863044082641856L;
	
	/** 主键 */
	private Long id;
	/** 接入点编号 */
	private String miNo;
	/** 产品ID */
	private Long productId;
	/** 属性ID */
	private Long attributeId;
	/** 属性KEY */
	private String attributeKey;
	/** 属性值 */
	private String attributeValue;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMiNo() {
		return miNo;
	}
	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(Long attributeId) {
		this.attributeId = attributeId;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	public String getAttributeKey() {
		return attributeKey;
	}
	public void setAttributeKey(String attributeKey) {
		this.attributeKey = attributeKey;
	}
	
}
