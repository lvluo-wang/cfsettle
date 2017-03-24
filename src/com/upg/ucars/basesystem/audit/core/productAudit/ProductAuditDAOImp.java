package com.upg.ucars.basesystem.audit.core.productAudit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.mapping.basesystem.audit.AuditRoute;
import com.upg.ucars.mapping.basesystem.audit.ReAuditRouteProduct;
import com.upg.ucars.model.ConditionBean;

public class ProductAuditDAOImp extends BaseDAO<ReAuditRouteProduct, Long>
		implements IProductAuditDAO {

	@Override
	public Class<ReAuditRouteProduct> getEntityClass() {
		return ReAuditRouteProduct.class;
	}

	public AuditRoute findAuditRoute(Long prodId, Long brchId) {
		String hql = "select ar from AuditRoute ar,ReAuditRouteProduct re where re.auditRouteId=ar.id and re.prodId=:prodId and re.brchId=:brchId";
		try {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("brchId", brchId);
			parameterMap.put("prodId", prodId);
			List list = queryByParam(hql, parameterMap, null);
			if (list != null && list.size() > 0) { return (AuditRoute) list
					.get(0); }
		} catch (Exception e) {
			ExceptionManager.throwException(DAOException.class,
					ErrorCodeConst.DB_OPERATION_ERROR, hql, e);
		}
		return null;
	}

	public Object[] findReArProdAndAr(Long prodId, Long brchId) {
		Object[] ret = null;
		if (prodId != null && brchId != null) {
			String hql = "select re,ar from AuditRoute ar,ReAuditRouteProduct re where re.auditRouteId=ar.id and re.prodId=:prodId and re.brchId=:brchId";
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("brchId", brchId);
			parameterMap.put("prodId", prodId);
			List list = queryByParam(hql, parameterMap, null);
			if (list != null && list.size() == 1) {
				ret = (Object[]) list.get(0);
			}
		}
		return ret;
	}

	public ReAuditRouteProduct findReAuditRouteProduct(Long prodId, Long brchId) {
		String hql = "from ReAuditRouteProduct re where  re.prodId=:prodId and re.brchId=:brchId";
		try {
			Map parameterMap = new HashMap();
			parameterMap.put("brchId", brchId);
			parameterMap.put("prodId", prodId);
			List list = queryByParam(hql, parameterMap, null);
			if (list != null && list.size() > 0) { return (ReAuditRouteProduct) list
					.get(0); }
		} catch (Exception e) {
			ExceptionManager.throwException(DAOException.class,
					ErrorCodeConst.DB_OPERATION_ERROR, hql, e);
		}
		return null;
	}

	public List<ReAuditRouteProduct> findAuditProductByAuditRouteId(Long id) {
		String hql = "from ReAuditRouteProduct re where re.auditRouteId=:auditRouteId";
		try {
			Map parameterMap = new HashMap();
			parameterMap.put("auditRouteId", id);
			return queryByParam(hql, parameterMap, null);
		} catch (Exception e) {
			ExceptionManager.throwException(DAOException.class,
					ErrorCodeConst.DB_OPERATION_ERROR, hql, e);
		}
		return null;
	}

	public List<ReAuditRouteProduct> findBindedProduct(Long brchId,
			Long auditRouteId) {
		String hql = "from ReAuditRouteProduct re where re.auditRouteId=:auditRouteId and re.brchId=:brchId";
		try {
			Map parameterMap = new HashMap();
			parameterMap.put("auditRouteId", auditRouteId);
			parameterMap.put("brchId", brchId);
			return queryByParam(hql, parameterMap, null);
		} catch (Exception e) {
			ExceptionManager.throwException(DAOException.class,
					ErrorCodeConst.DB_OPERATION_ERROR, hql, e);
		}
		return null;
	}

	public List<ReAuditRouteProduct> getAuditRouteProdByBrchNull() {
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
		conditionList.add(new ConditionBean(ReAuditRouteProduct.PROD_ID,
				ConditionBean.IS_NOT_NULL, null));
		conditionList.add(new ConditionBean(ReAuditRouteProduct.AUDIT_ROUTE_ID,
				ConditionBean.IS_NOT_NULL, null));
		conditionList.add(new ConditionBean(ReAuditRouteProduct.BRCH_ID,
				ConditionBean.IS_NULL, null));
		return queryEntity(conditionList, null);
	}

	public int getReAuditRouteProductCount(Long auditRouteId, Long productId,
			Long branchId) {
		int ret = 0;
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>(3);
		conditionList.add(new ConditionBean(ReAuditRouteProduct.AUDIT_ROUTE_ID,
				auditRouteId));
		conditionList.add(new ConditionBean(ReAuditRouteProduct.BRCH_ID,
				branchId));
		if (productId != null) {
			conditionList.add(new ConditionBean(ReAuditRouteProduct.PROD_ID,
					productId));
		}
		List<ReAuditRouteProduct> list = queryEntity(conditionList, null);
		if (list != null) {
			ret = list.size();
		}
		return ret;
	}

}
