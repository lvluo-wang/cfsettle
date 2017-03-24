package com.upg.ucars.basesystem.audit.core.auditNode;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.mapping.basesystem.audit.AuditNode;
/**
 * 
 * 功能说明：TODO(审批节点数据层接口)
 * @author shentuwy  
 * @date 2012-1-29 上午11:19:00 
 *
 */
public interface IAuditNodeDAO extends IBaseDAO<AuditNode,Long> {
	/**
	 * 根据审批路线id获取审批节点
	 * @param auditRouteId 审批路线id
	 * @return
	 */
	List<AuditNode> findAuditNodeByAuditRouteId(Long auditRouteId);
	/**
	 * 获取审批路线下节点的最大序号
	 * @param auditRouteId 审批路线
	 * @return 序号
	 */
	Long getMaxSortNo(Long auditRouteId);
	/**
	 * 获取指定审批节点后续节点
	 * @param target
	 * @return
	 */
	List<AuditNode> getButtomAuditNode(AuditNode target);

}
