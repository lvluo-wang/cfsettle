package com.upg.ucars.basesystem.audit.core.productAudit;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.mapping.basesystem.audit.AuditRoute;
import com.upg.ucars.mapping.basesystem.audit.ReAuditRouteProduct;

/**
 * 产品审批路线关系
 * 
 * @author shentuwy
 * 
 */
public interface IProductAuditDAO extends IBaseDAO<ReAuditRouteProduct, Long> {

	/**
	 * 根据产品id及机构id获取审批路线
	 * 
	 * @param prodId
	 *            产品id
	 * @param brchId
	 *            机构id
	 * @return
	 */
	AuditRoute findAuditRoute(Long prodId, Long brchId);

	/**
	 * 
	 * 获取审批路线和产品审批路线数组
	 * 
	 * @param prodId
	 * @param brchId
	 * @return Object[0]是ReAuditRouteProduct,Object[1]是AuditRoute
	 */
	public Object[] findReArProdAndAr(Long prodId, Long brchId);

	/**
	 * 根据产品id及机构id获取机构产品路线
	 * 
	 * @param prodId
	 *            产品id
	 * @param brchId
	 *            机构id
	 * @return
	 */
	ReAuditRouteProduct findReAuditRouteProduct(Long prodId, Long brchId);

	/**
	 * 获取审批路线绑定的审批产品
	 * 
	 * @param id
	 *            审批路线id
	 * @return
	 */
	List<ReAuditRouteProduct> findAuditProductByAuditRouteId(Long id);

	/**
	 * 获取绑定的产品
	 * 
	 * @param brchId
	 * @param auditRouteId
	 * @return
	 */
	List<ReAuditRouteProduct> findBindedProduct(Long brchId, Long auditRouteId);

	/**
	 * 
	 * 获取机构为空的审批路线产品关系列表
	 * 
	 * @return
	 */
	public List<ReAuditRouteProduct> getAuditRouteProdByBrchNull();

	/**
	 * 
	 * 获取审批路线产品关系
	 * 
	 * @param auditRouteId
	 * @param productId
	 * @param branchId
	 * @return
	 */
	public int getReAuditRouteProductCount(Long auditRouteId, Long productId,
			Long branchId);

}
