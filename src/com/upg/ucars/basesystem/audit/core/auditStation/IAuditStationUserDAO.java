package com.upg.ucars.basesystem.audit.core.auditStation;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.mapping.basesystem.audit.AuditStation;
import com.upg.ucars.mapping.basesystem.audit.ReAuditStationUser;
import com.upg.ucars.mapping.basesystem.security.Buser;

public interface IAuditStationUserDAO extends
		IBaseDAO<ReAuditStationUser, Long> {
	/**
	 * 统计与审批岗位绑定的用户数
	 * @param auditStationId 审批岗位id
	 * @return
	 */
	Long countUserNumberByAuditStationId(Long auditStationId);
	/**
	 * 获取岗位人员
	 * @param auditStationId 审批岗位id
	 * @return
	 */
	List<Buser> findBuserByAuditStationId(Long auditStationId);
	/**
	 * 根据审批岗位id获取岗位人员关系
	 * @param auditStationId 审批岗位id
	 * @return
	 */
	List<ReAuditStationUser> findReAuditStationUserBy(Long auditStationId);
	/**
	 * 判断指定的人员岗位配置是否存在
	 * @param userId 用户id
	 * @param stationId 岗位id
	 * @return
	 */
	boolean existStationUserMap(Long userId, Long stationId);
	/**
	 * 删除岗位上的用户配置
	 * @param userIds
	 * @param auditStation
	 */
	void delete(String[] userIds, AuditStation auditStation);

}
