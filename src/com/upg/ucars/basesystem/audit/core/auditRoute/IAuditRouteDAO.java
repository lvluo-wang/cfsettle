package com.upg.ucars.basesystem.audit.core.auditRoute;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.mapping.basesystem.audit.AuditRoute;

public interface IAuditRouteDAO extends IBaseDAO<AuditRoute, Long> {

	/**
	 * 根据接入编号获取审批路线
	 * 
	 * @param auditRoute
	 * @param page
	 * @return
	 */
	List<AuditRoute> findAuditRouteByMiNo(AuditRoute auditRoute, Page page);

}
