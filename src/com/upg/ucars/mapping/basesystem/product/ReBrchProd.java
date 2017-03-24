package com.upg.ucars.mapping.basesystem.product;

/**
 * ReUserRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ReBrchProd implements java.io.Serializable {
	/**状态-增加待审批*/
	public final static String STATUS_AUDITING = "1";
	/**状态-取消待审批*/
	public final static String STATUS_CANCLEING = "2";
	/**状态-审批通过*/
	public final static String STATUS_ENABLE = "8";
		
	private Long id;
	private Long brchId;
	private Long prodId;
	private String status;
	
	public ReBrchProd(){}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBrchId() {
		return brchId;
	}
	public void setBrchId(Long brchId) {
		this.brchId = brchId;
	}
	public Long getProdId() {
		return prodId;
	}
	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	};




}