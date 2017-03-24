package com.upg.ucars.mapping.basesystem.product;

import java.io.Serializable;

/**
 * 
 * 产品权限关系实体 
 *
 * @author shentuwy
 *
 */

public class ProductSysfunc  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2514536292423891627L;
	
	/** 有效 */
	public static final String STATUS_EFFECITVE = "1";
	
	/** 无效 */
	public static final String STATUS_UNEFFECTIVE = "0";
	
	/** ID */
	private Long id;
	/** 产品ID */
	private Long productId;
	/** 权限ID */
	private Long funcId;
	/** 状态 */
	private String status;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getFuncId() {
		return funcId;
	}
	public void setFuncId(Long funcId) {
		this.funcId = funcId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
