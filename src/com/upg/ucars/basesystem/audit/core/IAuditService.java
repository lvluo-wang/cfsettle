package com.upg.ucars.basesystem.audit.core;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.upg.ucars.framework.base.Page;
import com.upg.ucars.mapping.basesystem.audit.AuditProcess;
import com.upg.ucars.mapping.basesystem.audit.AuditTask;
import com.upg.ucars.model.security.UserLogonInfo;

/**
 * 审批服务
 * 
 * 
 * @author shentuwy
 * @date 2011-4-1 上午09:34:47
 * 
 */
public interface IAuditService {

	/**
	 * 提交审批任务（在业务服务中，需要进行审批时，根据机构及产品、查找配置的审批路线，启动审批任务并记录相关实体和回调的服务）
	 * 
	 * @param logonInfo
	 *            用户登录信息
	 * @param prodNo
	 *            产品号
	 * @param entity
	 *            审批实体
	 * @param auditPrivilege
	 *            审批需要的最大权限
	 * @param callBackServiceBean
	 *            回调服务IAuditCallBack的接口实现bean名称（当审批任务完成后，需要处理实现者指定的动作）
	 * @param auditRemark
	 *            审批提交备注
	 * @return 审批任务id
	 */
	public Long commitAuditTask(UserLogonInfo logonInfo, String prodNo,
			Object entity, BigDecimal auditPrivilege,
			String callBackServiceBean, String auditRemark);

	/**
	 * 提交审批
	 * 
	 * @param logonInfo
	 *            用户登录信息
	 * @param prodNo
	 *            产品编号
	 * @param entity
	 *            审批实体
	 * @param auditRemark
	 *            审批附言
	 * @return 任务id
	 */
	public Long commitAuditTask(UserLogonInfo logonInfo, String prodNo,
			Object entity, String auditRemark);

	/**
	 * 提交审批
	 * 
	 * @param logonInfo
	 *            用户登录信息
	 * @param prodNo
	 *            产品编号
	 * @param entity
	 *            审批实体
	 * @return 任务id
	 */
	public Long commitAuditTask(UserLogonInfo logonInfo, String prodNo,
			Object entity);

	/**
	 * 提交审批
	 * 
	 * @param logonInfo
	 *            用户登录信息
	 * @param prodNo
	 *            产品编号
	 * @param entity
	 *            审批实体
	 * @param auditPrivilege
	 *            审批权限
	 * @param auditRemark
	 *            审批附言
	 * @return 任务id
	 */
	public Long commitAuditTask(UserLogonInfo logonInfo, String prodNo,
			Object entity, BigDecimal auditPrivilege, String auditRemark);

	/**
	 * 撤销审批任务
	 * 
	 * @param logonInfo
	 *            用户登录信息
	 * @param prodNo
	 *            产品编号
	 * @param entity
	 *            审批实体
	 * @return 撤销审批结果
	 */
	public AuditTaskRevokeResult revokeAuditTask(UserLogonInfo logonInfo,
			String prodNo, Object entity);

	/**
	 * 根据审批任务id撤销审批任务
	 * 
	 * @param auditTaskId
	 *            审批任务id
	 */
	public AuditTaskRevokeResult revokeAuditTask(Long auditTaskId);

	/**
	 * 受理审批过程（同一审批岗位下的审批人员，在审批之前先做受理动作，其他审批人员就不能再受理和审批）
	 * 
	 * @param logonInfo
	 *            用户登录信息
	 * @param process
	 *            审批过程(id,version)
	 */
	public void acceptAuditProcess(UserLogonInfo logonInfo,
			AuditProcess auditProcess);

	/**
	 * 
	 * 批量受理审批
	 * 
	 * @param logonInfo
	 * @param idList
	 */
	public void acceptAuditProcess(UserLogonInfo logonInfo, List<Long> idList);

	/**
	 * 审批过程撤销受理（取消后，其他同岗位人员可以受理）
	 * 
	 * @param logonInfo
	 *            登录信息
	 * @param auditProcessId
	 *            审批过程id
	 */
	public void revokeAcceptAuditProcess(UserLogonInfo logonInfo,
			Long auditProcessId);

	/**
	 * 
	 * 批量撤销受理
	 * 
	 * @param logonInfo
	 * @param idList
	 */
	public void revokeAcceptAuditProcess(UserLogonInfo logonInfo,
			List<Long> idList);

	/**
	 * 查询审批任务中已完成的审批过程（主要用于审批人员查看前面的审批结果）
	 * 
	 * @param auditTaskId
	 *            审批任务id
	 * @return 已完成的审批过程集合
	 */
	public List<AuditProcess> queryAlreadyDoneAuditProcess(Long auditTaskId);

	/**
	 * 提交审批过程（审批人员，填写完审批意见及结果后，提交）
	 * 
	 * @param process
	 *            审批过程
	 */
	public void commitAuditProcess(AuditProcess process);

	/**
	 * 获取用户可操作审批列表
	 * 
	 * @param userLogonInfo
	 *            用户登录信息
	 * @param page
	 *            分页
	 * @param prodNo
	 *            产品编号
	 * @return
	 */
	public List<Map<String, Object>> queryCanOperateAuditList(
			UserLogonInfo userLogonInfo, Page page, String prodNo);

	/**
	 * 
	 * 获取各个产品的审批数量
	 *
	 * @param userId
	 * @param prodNos
	 * @param brchId
	 * @return
	 */
	public Map<String,Integer> getCanOperateAPCount(Long userId,List<String> prodNos,Long brchId);
	
	
	/**
	 * 根据审批任务id获取审批任务
	 * 
	 * @param id
	 *            审批任务id
	 * @return
	 */
	public AuditTask getAuditTask(Long id);

	/**
	 * 查询任务下所有审批过程
	 * 
	 * @param id
	 *            任务id
	 * @return
	 */
	public List<Map<String, Object>> queryAllAuditProcess(Long id);

	/**
	 * 根据审批过程id查询审批过程
	 * 
	 * @param id
	 *            审批过程id
	 * @return
	 */
	public AuditProcess getauditProcess(Long id);

	/**
	 * 保存审批意见
	 * 
	 * @param auditProcess
	 *            审批过程
	 */
	public void saveAuditResult(AuditProcess auditProcess);

	/**
	 * 保存并提交审批意见
	 * 
	 * @param auditProcess
	 *            审批过程
	 */
	public void saveAndCommitAuditResult(AuditProcess auditProcess);

	/**
	 * 查询最后一笔审批任务
	 * 
	 * @param prodNo
	 * @param entityId
	 * @param cls
	 * @return
	 */
	public AuditTask getLastAuditTask(String prodNo, Long entityId, Class<?> cls);

	/**
	 * 分页查询审批历史记录
	 * 
	 * @param auditHistSearch
	 *            审批历史查询对象
	 * @param page
	 *            分页
	 * @return
	 */
	public List<Map<String, Object>> queryAuditHist(
			AuditHistSearchBean auditHistSearch, Page page);

	/**
	 * 
	 * 拷贝默认的产品路线的关系
	 * 
	 * @param productId
	 *            产品ID
	 * @param branchId
	 *            机构ID
	 */
	public void copyDefaultProductInfoAuditRoute(Long branchId);

	/**
	 * 
	 * 同一机构下是否有多个产品同时绑定一个审批路线
	 * 
	 * @param auditRouteId
	 * @param branchId
	 * @return
	 */
	public boolean hasMulProductBindAuditRoute(Long auditRouteId, Long branchId);
}
