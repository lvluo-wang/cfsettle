package com.upg.ucars.basesystem.security.core.user;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.AdapterRuntimeException;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.RightsException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.audit.AuditTask;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.Role;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.security.UserLogonInfo;

/**
 * 用户相关服务
 * 
 * @author shentuwy
 * @date 2012-7-27
 * 
 */
public interface IUserService extends IBaseService {

	/**
	 * 修改密码
	 * 
	 * @param userId
	 *            用户id
	 * @param oldPwd
	 *            旧密码
	 * @param newPwd
	 *            新密码
	 * @param confirmPwd
	 *            重复新密码
	 * @param miNo
	 *            接入点
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public void changePassword(String userNo, String oldPwd, String newPwd,
			String confirmPwd, String miNo) throws ServiceException;

	/**
	 * 重置用户密码
	 * 
	 * @param userId
	 *            用户id
	 */
	public void resetPassword(Long userId) throws ServiceException;

	/**
	 * 批量用户重置密码
	 * 
	 * @param idList
	 * @throws ServiceException
	 */
	public void batchResetPassword(List<Long> idList) throws ServiceException;

	/**
	 * 保存用户信息
	 * 
	 * @param user
	 */
	public void saveUser(Buser user) throws ServiceException;

	/**
	 * 
	 * 根据用户ID获取用户
	 * 
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	public Buser getUserById(Long userId) throws DAOException;
	
	public Buser getUserByName(String userName) throws DAOException;
	public Buser getUserByNo(String userNo) throws DAOException;

	/**
	 * 
	 * 更新用户信息
	 * 
	 * @param user
	 * @throws ServiceException
	 */
	public void updateUser(Buser user) throws ServiceException;

	/**
	 * 
	 * 删除用户信息
	 * 
	 * @param userId
	 * @throws ServiceException
	 */
	public void deleteById(Long userId) throws ServiceException;

	/**
	 * 置为离线
	 * 
	 * @param userId
	 */
	public void offLine(Long userId) throws ServiceException;

	/**
	 * 按条件查询
	 * 
	 * @param queryComponent
	 * @param condList
	 * @param page
	 * @return
	 */
	public List<Buser> queryUserByLogonCondition(UserLogonInfo logonInfo,
			QueryComponent queryComponent, Page page, String roleStatus)
			throws DAOException;

	/**
	 * 按条件查询 2
	 * 
	 * @param logonInfo哦
	 * @param queryComponent
	 * @param page
	 * @param bu
	 * @param isAllUser
	 *            是否要看到下面的所有人 <br/>
	 *            目前实现类里只有对saas角色进行了控制
	 * @return
	 */
	public List<Buser> queryUserByLogonCondition(UserLogonInfo logonInfo,
			QueryComponent queryComponent, Page page, Buser bu,UserSearchBean searchBean,
			boolean isAllUser) throws DAOException;

	/**
	 * 查询当前登录者可用于分配的角色
	 * 
	 * @param logoner
	 *            登录者
	 * @param user
	 *            角色赋于者
	 * @return
	 * @throws ServiceException
	 */
	public List<Role> queryAllowAssignRole(UserLogonInfo logoner, Buser user)
			throws DAOException;

	/**
	 * 查询用户具有的角色
	 * 
	 * @param userId
	 * @return
	 */
	public List<Role> queryUserRole(Long userId) throws DAOException;

	/**
	 * 查询具有角色的用户
	 * 
	 * @param roleId
	 * @param conditionList
	 * @param page
	 * @return
	 * @throws DAOException
	 */
	public List<Buser> queryRoleUser(Long roleId,
			List<ConditionBean> conditionList, Page page) throws DAOException;

	/**
	 * 查询机构下用户
	 * 
	 * @param brchId
	 * @param conditionList
	 * @param isSubBrch
	 *            是否包含子机构用户
	 * @param page
	 * @return
	 * @throws DAOException
	 */
	public List<Buser> queryBranchUser(Long brchId,
			List<ConditionBean> conditionList, boolean isSubBrch, Page page)
			throws DAOException;

	/**
	 * 查询用户权限
	 * 
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	public List<Sysfunc> queryUserFunction(Long userId) throws DAOException;

	/**
	 * 查询用户权限 使用hibernate二级缓存
	 * 
	 * @param userId
	 * @return funcId列表
	 * @throws DAOException
	 */
	public List<Long> queryUserFunctionWithCache(Long userId)
			throws DAOException;

	/**
	 * 为指定用户分配角色
	 * 
	 * @param userId
	 *            用户id
	 * @param roleIdList
	 *            角色Id集合
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public void buildUserRoles(Long userId, List<Long> roleIdList)
			throws ServiceException;

	/**
	 * 复核用户权限
	 * 
	 * @param userId
	 */
	public void checkUserFunction(Long userId) throws ServiceException;

	/**
	 * 修改用户状态
	 * 
	 * @param userId
	 * @param userStatus
	 *            见UserStatus类中常量
	 */
	public void updateUserStatus(Long userId, String userStatus)
			throws ServiceException;

	/**
	 * 未登录
	 * 
	 * @throws ServiceException
	 * @throws RightsException
	 */
	public void noLogon() throws ServiceException, RightsException;

	/**
	 * 用户分配角色后提交审核
	 * 
	 * @param logonInfo
	 * @param userId
	 */
	public void commitUserRoles(UserLogonInfo logonInfo, Long userId)
			throws ServiceException, DAOException;

	/**
	 * 批量用户分配角色提交审核
	 * 
	 * @param logonInfo
	 * @param ids
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public void batchCommitUserRoles(UserLogonInfo logonInfo, List<Long> ids)
			throws ServiceException, DAOException;

	/**
	 * 用户分配角色撤销审核
	 * 
	 * @param logonInfo
	 * @param userId
	 */
	public void revokeUserRoles(UserLogonInfo logonInfo, Long userId)
			throws ServiceException, DAOException;

	/**
	 * 批量用户分配角色撤销审核
	 * 
	 * @param logonInfo
	 * @param ids
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public void batchRevokeUserRoles(UserLogonInfo logonInfo, List<Long> ids)
			throws ServiceException, DAOException;

	/**
	 * 获取用户角色分配审批任务
	 * 
	 * @param userId
	 * @return
	 */
	public AuditTask findUserRoleAuditTask(Long userId)
			throws ServiceException, DAOException;

	/**
	 * 设置用户状态
	 * 
	 * @param ids
	 * @param status
	 * 
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public void setUserStatus(List<Long> ids, String status)
			throws ServiceException, DAOException;

	/**
	 * 登录
	 * 
	 * @param user
	 * @param isEncrypt
	 * @return
	 * @throws ProxyException
	 *             为避免事务而抛出的NON-RuntimeException,而在 action中抛出
	 */
	public UserLogonInfo logon(Buser user, boolean isEncrypt)
			throws AdapterRuntimeException;

	/**
	 * 根据用户编号和接入编号查询用户
	 * 
	 * @param userNo
	 *            用户编号
	 * @param miNo
	 *            接入编号
	 * 
	 * @return
	 * 
	 */
	public Buser getUserByUserNoAndMiNo(String userNo, String miNo);

	/**
	 * 断用户是否分配了角色
	 * 
	 * @param userId
	 *            用户id
	 * @return true:分配角色 false:未分配角色
	 */
	public boolean hasRoles(Long userId) throws ServiceException, DAOException;

	/**
	 * 根据用户id和用户角色状态查询权限列表
	 * 
	 * @param userId
	 * @param userRoleStatus
	 * @return
	 * @throws DAOException
	 */
	public List<Sysfunc> queryUserFunc(Long userId, String userRoleStatus)
			throws DAOException;

	/**
	 * 根据角色获取用户
	 * 
	 * @param roleType
	 *            角色类型
	 * @return
	 */
	public List<Buser> getUserByRole(String roleType);
	/**
	 * 根据角色名称获取用户
	 * 
	 * @param roleName
	 * @return
	 */
	public List<Buser> getUserByRoleName(String roleName);
	
	public List<Buser> getUserByRoleNameAndBrch(String roleName,Long brchId);
	
	public List<Buser> getUserByRoleIdAndBrch(Long roleId,Long brchId);
	/**
	 * 获取用户列表
	 * 
	 * @param roleName
	 * @param brchNo
	 * @return
	 */
	public List<Buser> getUsersByRoleAndBrch(String roleName,String brchNo);

	public List<Buser> getUserByIdList(List<Long> idList);

	/**
	 * 营业部下的所有员工
	 * @param deptId
	 * @return
	 */
	public List<Buser> getUserByDeptId(Long deptId);

	List<Buser> getUserByAreaId(Long areaId);

	public Buser getUserByTeamIdAndPosCode(Long teamId, String posCode);

	public Buser getUserByDeptIdAndPosCode(Long deptId, String posCode);

	public Buser getUserByAreaIdAndPosCode(Long areaId, String posCode);

	public List<Buser> getCanSetBuser(String posCode);


}
