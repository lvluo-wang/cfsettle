package com.upg.ucars.basesystem.audit.core.auditRoute;

import java.util.List;
import java.util.Map;

import com.upg.ucars.framework.base.Page;
import com.upg.ucars.mapping.basesystem.audit.AuditNode;
import com.upg.ucars.mapping.basesystem.audit.AuditRoute;
import com.upg.ucars.mapping.basesystem.audit.AuditStation;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.Role;
import com.upg.ucars.model.security.UserLogonInfo;
/**
 * 
 * 功能说明：TODO(审批路线服务层接口)
 * @author shentuwy  
 * @date 2012-1-29 上午11:20:50 
 *
 */
public interface IAuditRouteService {

	/**
	 * 获取接入者拥有的审批路线
	 * 
	 * @param auditRoute
	 * @param page
	 * @return
	 */
	public List<AuditRoute> findAuditRoute(AuditRoute auditRoute, Page page);

	/**
	 * 创建审批路线
	 * 
	 * @param auditRoute
	 */
	public void createAuditRoute(AuditRoute auditRoute);

	/**
	 * 修改审批路线
	 * 
	 * @param auditRoute
	 */
	public void modifyAuditRoute(AuditRoute auditRoute);

	/**
	 * 删除审批路线
	 * 
	 * @param auditRoute
	 *            审批路线
	 */
	public void removeAuditRoute(AuditRoute auditRoute);

	/**
	 * 为审批路线添加审批节点
	 * 
	 * @param auditNode
	 */
	public void addAuditNode(AuditNode auditNode);

	/**
	 * 修改审批节点
	 * 
	 * @param auditNode
	 */
	public void modifyAuditNode(AuditNode auditNode);

	/**
	 * 删除审批节点
	 * 
	 * @param auditNode
	 */
	public void removeAuditNode(AuditNode auditNode);

	/**
	 * 为审批节点添加审批岗位
	 * 
	 * @param auditStation
	 */
	public void addAuditStation(AuditStation auditStation);

	/**
	 * 修改审批岗位
	 * 
	 * @param auditStation
	 */
	public void modifyAuditStation(AuditStation auditStation);

	/**
	 * 删除审批岗位
	 * 
	 * @param auditStation
	 */
	public void removeAuditStation(AuditStation auditStation);

	/**
	 * 移动审批节点或审批岗位
	 * 
	 * @param source
	 *            源
	 * @param sourceType
	 *            源类型（route,node,station）
	 * @param target
	 *            目标
	 * @param targetType
	 *            目标类型（route,node,station）
	 * @param point
	 *            位置（top,append,bottom）
	 */
	public void moveNodeOrStation(Long source, String sourceType, Long target,
			String targetType, String point);

	/**
	 * 根据路线id获取审批路线
	 * 
	 * @param id
	 *            审批路线id
	 * @return 审批路线
	 */
	public AuditRoute findAuditRoute(Long id);

	/**
	 * 根据审批路线获取审批节点
	 * 
	 * @param auditRoute
	 *            审批路线
	 * @return 节点集合
	 */
	public List<AuditNode> findAuditNode(AuditRoute auditRoute);

	/**
	 * 根据审批节点获取
	 * 
	 * @param node
	 *            审批节点
	 * @return
	 */
	public List<AuditStation> findAuditStation(AuditNode node, Long brchId);

	/**
	 * 根据审批节点id获取审批节点
	 * 
	 * @param id
	 *            审批节点id
	 * @return
	 */
	public AuditNode findAuditNode(Long id);

	/**
	 * 根据审批岗位id获取审批岗位
	 * 
	 * @param id
	 *            审批岗位id
	 * @return
	 */
	public AuditStation findAuditStation(Long id);

	/**
	 * 获取本机构的产品审批路线列表
	 * 
	 * @param logonInfo
	 *            登录信息
	 * @param product
	 *            产品信息
	 * @param pg
	 *            分页
	 * @return
	 */
	public List<Map<String, Object>> findProductAuditRoute(
			UserLogonInfo logonInfo, ProductInfo product, Page pg);

	/**
	 * 保存产品与审批路线的绑定关系
	 * 
	 * @param prodId
	 *            产品id的集合
	 * @param auditRoute
	 *            审批路线（包含id）
	 * @param brchId
	 *            机构
	 * 
	 */
	public void assignAuditRouteForProduct(String[] prodId,
			AuditRoute auditRoute, Long brchId);

	/**
	 * 
	 * 取消产品的分配路线
	 * 
	 * @param rapIdList
	 *            产品审批路线关联Id列表
	 */
	public void cancelAuditRouteForProduct(List<Long> rapIdList);

	/**
	 * 
	 * 拷贝默认的岗位
	 * 
	 * @param arIdList
	 *            审批路线列表
	 * @param branchId
	 *            机构
	 * @param isSysDef
	 *            是否是系统默认审批
	 * 
	 * @param auditRouteId
	 */
	public void copyDefaultAuditStationByAuditRouteId(List<Long> arIdList,
			Long branchId, boolean isSysDef);
	/**
	 * 拷贝默认的岗位和默认岗位并定的默认配置
	 * @param arIdList 审批路线列表
	 * @param branchId 机构
	 * @param isSysDef 是否系统默认审批
	 */
	public void copyDefaultAuditStationAndBindConfigByAuditRouteId(List<Long> arIdList,
			Long branchId, boolean isSysDef);
	/**
	 * 查詢指定機構下被綁定的崗位
	 * 
	 * @param as
	 *            岗位
	 * @param pg
	 *            分页
	 * @return
	 */
	public List<Map<String, Object>> findBindedAuditStation(AuditStation as,
			Page pg);

	/**
	 * 获取岗位人员
	 * 
	 * @param auditStationId
	 *            岗位id
	 * @return
	 */
	public List<Buser> findBuserByAuditStationId(Long auditStationId);

	/**
	 * 审批岗位分配人员
	 * 
	 * @param userIds
	 *            用户id数组[string]
	 * @param auditStation
	 *            审批岗位
	 */
	public void assignAuditStationWithUsers(String[] userIds,
			AuditStation auditStation);
	
	/**
	 * 取消岗位上已分配的用户
	 * @param userIds
	 * @param auditStation
	 */
	public void cancelAssignAuditStationWithUsers(String[] userIds,AuditStation auditStation);
	/**
	 * 
	 * 审批路线是否可以被修改
	 * 
	 * @param auditRouteId
	 * @return
	 */
	public boolean isEditAudit(Long auditRouteId);
	/**
	 * 添加审批人员
	 * @param userIds
	 * @param auditStation
	 */
	public void addAssignAuditStationWithUsers(String[] userIds,
			AuditStation auditStation);
	/**
	 * 查询岗位绑定的角色集合
	 * @param stationId 岗位id
	 * @return
	 */
	public List<Role> findRoleByAuditStationId(Long stationId);
	/**
	 * 添加审批角色
	 * @param rids
	 * @param auditStation
	 */
	public void addAssignAuditStationWithRoles(String[] rids,
			AuditStation auditStation);
	/**
	 * 取消审批角色
	 * @param rids
	 * @param auditStation
	 */
	public void cancelAssignAuditStationWithRoles(String[] rids,
			AuditStation auditStation);
	/**
	 * 获取指定审批路线下归属于机构的岗位集合
	 * @param auditRouteId
	 * @param branchId
	 * @return
	 */
	public List<AuditStation> findAuditStationByAuditRouteId(Long auditRouteId,
			Long branchId);
	/**
	 * 获取审批岗位上通过人员和角色绑定的用户
	 * @param auditStationid
	 * @return
	 */
	public List<Buser> findBuserByAuditStationIdContainRole(Long auditStationid);
	/**
	 * 获取审批路线的岗位模板（创建机构为空）
	 * @param id 审批路线id
	 * @return
	 */
	public List<AuditStation> findAuditStationTemplateByAuditRouteId(Long id);

}
