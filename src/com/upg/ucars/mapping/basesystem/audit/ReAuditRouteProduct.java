package com.upg.ucars.mapping.basesystem.audit;

import java.io.Serializable;
/**
 * 审批路线与机构下的产品关联
 * 
 * @author shentuwy
 * @date 2011-3-31 下午01:35:35
 *
 */
public class ReAuditRouteProduct implements Serializable {
	private static final long serialVersionUID = 3888870317554012183L;
	
	/** 产品的审批路线设置异常（该产品存在审批中的审批任务） */
	public static final String HAS_AUDITING_TASK_ERROR="PRODUCT_AUDIT_ROUTE_001";
	/** 审批路线已绑定产品，不能删除 */
	public static final String HAS_AUDITING_PRODUCT_ERROR="PRODUCT_AUDIT_ROUTE_002";
	
	/** 主键 */
	private Long id;
	public static final String ID = "id";
	/** 审批路线id */
	private Long auditRouteId;
	public static final String AUDIT_ROUTE_ID = "auditRouteId";
	/** 产品id */
	private Long prodId;
	public static final String PROD_ID = "prodId";
	/** 机构id */
	private Long brchId;
	public static final String BRCH_ID = "brchId";
	/** 版本号 */
	private Long version;
	public static final String VERSION = "version";
	
	public ReAuditRouteProduct(){}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Long getAuditRouteId() {
		return auditRouteId;
	}
	public void setAuditRouteId(Long auditRouteId) {
		this.auditRouteId = auditRouteId;
	}
	public Long getProdId() {
		return prodId;
	}
	public void setProdId(Long prodId) {
		this.prodId = prodId;
	}
	public Long getBrchId() {
		return brchId;
	}
	public void setBrchId(Long brchId) {
		this.brchId = brchId;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
}
