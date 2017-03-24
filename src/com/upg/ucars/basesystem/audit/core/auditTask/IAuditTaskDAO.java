package com.upg.ucars.basesystem.audit.core.auditTask;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.upg.ucars.basesystem.audit.core.AuditHistSearchBean;
import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.mapping.basesystem.audit.AuditTask;

public interface IAuditTaskDAO extends IBaseDAO<AuditTask, Long> {
	/**
	 * 获取审批中的审批任务
	 * @param brchId 机构id
	 * @param prodId 产品id
	 * @return
	 */
	List<AuditTask> findAuditingTask(Long brchId, Long prodId);

	/**
	 * 查询指定机构下的指定产品中的指定实体的审批任务
	 * @param brchId 机构id
	 * @param prodId 产品id
	 * @param pk 主键id
	 * @return
	 */
	List<AuditTask> findAuditTask(Long brchId, Long prodId, Long pk);
	
	/**
	 * 查询最后一次审批任务
	 * @param brchId 机构id
	 * @param prodId 产品id
	 * @param entityId 实体id
	 * @return
	 */
	AuditTask findLastAuditTask(Long brchId, Long prodId, Long entityId);
	
	/**
	 * 
	 * 查询最后一次审批任务
	 *
	 * @param prodId
	 * @param entityId
	 * @param cls
	 * @return
	 */
	public AuditTask getLastAuditTask(Long prodId,Serializable entityId,Class<?> cls);
	/**
	 * 查询审批历史
	 * @param auditHistSearch
	 * @param page
	 * @return
	 */
	List<Map<String,Object>> findAuditHist(AuditHistSearchBean auditHistSearch, Page page);

}
