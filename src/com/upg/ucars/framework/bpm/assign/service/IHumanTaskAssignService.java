package com.upg.ucars.framework.bpm.assign.service;
import java.util.List;

import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.framework.bpm.HumnTaskActr;

public interface IHumanTaskAssignService {
	/**
	 * 保存授权信息
	 * @param taskActr
	 * @throws ServiceException
	 */
	public void save(HumnTaskActr taskActr) throws ServiceException;
	/**
	 * 删除按用户授权
	 * @param brchId
	 * @param taskId
	 * @param userId
	 * @throws ServiceException
	 */
	public void deleteByUserId(Long brchId, Long taskId, Long userId) throws ServiceException;
	/**
	 * 删除按角色授权
	 *
	 * @param brchId
	 * @param taskId
	 * @param roleBrchId
	 * @param roleId
	 * @throws ServiceException
	 */
	public void deleteByBrchRoleId(Long brchId, Long taskId, Long roleBrchId, Long roleId) throws ServiceException;
	/**
	 * 获取所有被权限的用户编号
	 * @param processName
	 * @param taskName
	 * @param brchId
	 * @return List< Buser.userNo >
	 * @throws ServiceException
	 */
	public List<String> getTaskActors(String processName,String taskName, Long brchId) throws ServiceException;
	/**
	 * 获取角色参与者 
	 *
	 * @param taskId
	 * @param brchId
	 * @param roleBrchId 角色所属的机构
	 * @return List< Role.roleId>
	 * @throws ServiceException
	 */
	public List<Long> getRoleActorsByBrch(Long taskId, Long brchId, Long roleBrchId) throws ServiceException;
	
	/**
	 * 获取用户参与者 
	 *
	 * @param taskId
	 * @param brchId
	 * @return List< Buser.userId>
	 * @throws ServiceException
	 */
	public List<Long> getUserActors(Long taskId, Long brchId) throws ServiceException;
	
	/**
	 * 获取机构参与者 
	 *
	 * @param taskId
	 * @param brchId
	 * @return List< Branch.brchId>
	 * @throws ServiceException
	 */
	public List<Long> getBrchActors(Long taskId, Long brchId) throws ServiceException;
	/**
	 * 批量设置机构参与者
	 * <li>本机构任务，授权于本机构；同时可以将子机构任务授权于子机构本身。
	 * 
	 * @param taskId
	 * @param beBrchIdList
	 * @param unBrchIdList
	 * @param isSameSubBrch 同步作用于子机构
	 */
	public void batchBuildBrchActor(Long taskId, List<Long> beBrchIdList, List<Long> unBrchIdList, boolean isSameSubBrch);
	/**
	 * 批量设置角色参与者
	 * <li>本机构任务，授权于本机构的若干角色；同时可以将子机构任务授权于子机构的若干角色。
	 * @param taskId
	 * @param takeBrchId
	 * @param brchId
	 * @param beRoleIdList
	 * @param unRoleIdList
	 * @param isSameSubBrch
	 */
	public void batchBuildRoleActor(Long taskId,Long takeBrchId, Long toBrchId, List<Long> beRoleIdList, List<Long> unRoleIdList, boolean isSameSubBrch);
	
	
	
	/**
	 * 领取任务
	 *
	 * @param tid 任务实例ID
	 * @param userId 用户ID
	 */
	public void holdTask(Long tid, Long userId) ;
	
	/**
	 * 领取任务
	 * 
	 * @param tid
	 * @param userId
	 * @param force 是否强制修改
	 */
	public void holdTask(Long tid, Long userId,boolean force);
	
	/**
	 * 领取任务
	 * 
	 * @param idList
	 * @param userId
	 */
	public void holdTask(List<Long> idList,Long userId);
	/**
	 * 撤销对任务的领用
	 * @param tid
	 * @param userId 用户ID
	 */
	public void cancelHoldTask(Long tid, Long userId);
	
	/**
	 * 转发给其它人处理
	 * 
	 * @param tid
	 * @param userId
	 */
	public void transfor(Long tid,Long userId);
	
	/**
	 * 转发给其它人处理
	 * 
	 * @param tids
	 * @param userId
	 */
	public void transfor(List<Long> tids,Long userId);
	
	/**
	 * 撤销任务领用
	 * 
	 * @param idList
	 * @param userId
	 */
	public void cancelHoldTask(List<Long> idList,Long userId);
	
	/**
	 * 获取任务的领用人
	 * @param tid
	 * @return userId
	 */
	public Long getTaskHoldActor(Long tid);
	
	public void copyTaskRoleActor(Long srcBranchId,List<Long> processIds,List<Long> destBranchIds);
}
