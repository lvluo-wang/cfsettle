package com.upg.ucars.basesystem.audit.core.auditStation;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.audit.AuditStation;

public interface IAuditStationDAO extends IBaseDAO<AuditStation, Long> {
	
	/**
	 * 
	 * 获取同一节点下的所有岗位
	 *
	 * @param auditNodeId
	 * @return
	 */
	List<AuditStation> getAuditStationListByNodeId(Long auditNodeId);
	
	/**
	 * 根据审批节点id获取机构创建的审批岗位
	 * @param auditNodeId 审批节点
	 * @param createBrchId 岗位创建机构
	 * @return
	 */
	List<AuditStation> findAuditStationByAuditNodeId(Long auditNodeId,Long createBrchId)throws DAOException;
	/**
	 * 获取同审批节点下的岗位最大序号
	 * @param auditNodeId 审批节点id
	 * @param createBrchId 审批节点的创建结构
	 * @return
	 */
	Long getMaxSortNo(Long auditNodeId,Long createBrchId)throws DAOException;
	/**
	 * 获取同节点下审批岗位后续岗位
	 * @param target
	 * @return
	 */
	List<AuditStation> getButtomAuditStation(AuditStation target)throws DAOException;
	/**
	 * 获取指定路线下的所有审批岗位
	 * @param auditRouteId 审批路线id
	 * @param brchId 机构id
	 * @return
	 */
	List<AuditStation> findAuditStationByAuditRouteId(Long auditRouteId,Long brchId)throws DAOException;
	/**
	 * 查询该机构下被绑定的审批岗位
	 * 
	 * @param as
	 * @param pg 分页
	 * @return
	 */
	List<AuditStation> findBindedAuditStation(AuditStation as, Page pg);
	/**
	 * 按照节点的的顺序和岗位的顺序进行排序获取所有岗位
	 * @param auditRouteId
	 * @param brchId
	 * @return
	 * @throws DAOException
	 */
	List<AuditStation> findAllStationOrderByNodeAndSort(Long auditRouteId,
			Long brchId)throws DAOException;
	
	/**
	 * 
	 *  获取默认的岗位列表，即没有机构的信息
	 *
	 * @param auditRouteId	审批路线ID
	 * @param isSysDef 是否系统默认
	 * @return
	 * @throws DAOException
	 */
	public List<AuditStation> getAuditStationsByAridAndBrchNull(Long auditRouteId,boolean isSysDef) throws DAOException;

}
