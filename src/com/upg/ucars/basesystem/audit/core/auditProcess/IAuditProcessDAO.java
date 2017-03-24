package com.upg.ucars.basesystem.audit.core.auditProcess;

import java.util.List;
import java.util.Map;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.audit.AuditProcess;
/**
 * 
 * 功能说明：TODO(审批过程数据层接口)
 * @author shentuwy  
 * @date 2012-1-29 上午11:20:00 
 *
 */
public interface IAuditProcessDAO extends IBaseDAO<AuditProcess, Long> {

	/**
	 * 
	 * @param userId 用户id
	 * @param prodNo 产品编号 
	 * @param page 分页
	 * @param brchId 机构ID
	 * @return
	 * @throws DAOException
	 */
	List<Map<String,Object>> getCanOperateAuditProcess(Long userId,String product,Long brchId, Page page)throws DAOException;
	
	/**
	 * 
	 * 获取各个产品的审批数量
	 *
	 * @param userId
	 * @param prodNos
	 * @param brchId
	 * @return
	 */
	Map<String,Integer> getCanOperateAPCount(Long userId,List<String> prodNos,Long brchId);
	

	/**
	 * 获取审批任务下的这个审批过程
	 * @param auditTaskId 审批任务id
	 * @return
	 */
	List<AuditProcess> findAllProcess(Long auditTaskId);

	/**
	 * 获取任务下所有审批过程（界面端显示）
	 * @param id 审批任务id
	 * @return
	 */
	List<Map<String,Object>> getAllProcess(Long id);

}
