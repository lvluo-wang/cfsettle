package com.upg.ucars.basesystem.security.core.role;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.security.Role;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.security.UserLogonInfo;


/**
 * 
 * 定义角色服务 
 * 
 * @author shentuwy
 * @date 2012-7-27
 *
 */
public interface IRoleService extends IBaseService {
	/**
	 * 添加角色
	 *
	 * @param role
	 * @throws ServiceException
	 */
	public void saveRole(Role role) throws ServiceException;
	/**
	 * 修改角色
	 * @param role
	 * @throws ServiceException
	 */
	public void updateRole(Role role) throws ServiceException;	

	/**
	 * 删除角色
	 *
	 * @param id
	 * @throws ServiceException
	 */
	public void deleteById(Long id)throws ServiceException;
	/**
	 * 
	 * 删除多个角色
	 *
	 * @param idList
	 */
	public void deleteByIdList(List<Long> idList);
	/**
	 * 根据ID获取角色对象
	 *
	 * @param roleId
	 * @throws ServiceException
	 */
	public Role getRoleById(Long roleId) throws ServiceException;
	/**
	 * 获取指定机构管理的角色
	 *
	 * @param brchId
	 * @return
	 * @throws ServiceException
	 */
	public List<Role> getRolesByBrchId(Long brchId) throws ServiceException;
		
	/**
	 * 按条件查询角色
	 *
	 * @param conditionList
	 * @param page
	 * @return
	 * @throws ServiceException
	 */
	public List<Role> queryRoles(List<ConditionBean> conditionList, Page page)throws ServiceException;	
	/**
	 * 按类型查询角色 
	 *
	 * @param roleTypes
	 * @param conditionList
	 * @param page
	 * @return
	 * @throws ServiceException
	 */
	public List<Role> queryRolesByType(String[] roleTypes, List<ConditionBean> conditionList, Page page)throws ServiceException;
	/**
	 * 组件查询角色
	 *
	 * @param qcpt
	 * @param page
	 * @return
	 * @throws ServiceException
	 */
	public List<Role> queryRoles(QueryComponent qcpt, Page page)throws ServiceException;
	
	/**
	 * 为角色设置权限
	 *
	 * @param roleId
	 * @param sysfuncIdList Sysfunc属性Id
	 * @throws ServiceException
	 */
	public void buildRoleSysfuncs(Long roleId, List<Long> sysfuncIdList) throws ServiceException;

	
	/**
	 * 查询当前登录者可用于分配的权限
	 * @param  
	 *
	 * @param logoner 登录者
	 * @return
	 * @throws ServiceException
	 */
	public List<Sysfunc> findAllowAssignFunction(String roleType, UserLogonInfo logoner) throws ServiceException;
	/**
	 * 获取角色关联的权限	
	 * 
	 * @param roleId
	 * @return
	 * @throws DAOException
	 */
	public List<Sysfunc> findSysfuncByRoleId(Long roleId) throws ServiceException;
	
	/**
	 * 按条件查询
	 *
	 * @param qc
	 * @param page
	 * @return
	 * @throws DAOException
	 */
	public List<Role> queryByCondition(QueryCondition qc, Page page) throws DAOException;
	/**
	 * 根据角色查询权限id集合
	 * @param roleId
	 * @return
	 * @throws DAOException
	 */
	public List<Long> findSysfuncIdsByRoleId(Long roleId)throws DAOException;
	/**
	 * 根据权限id获取角色id集合
	 * @param funcId
	 * @return
	 * @throws DAOException
	 */
	public List<Long> findRoleIdsByFuncId(Long funcId)throws DAOException;
	/**
	 * 指定角色添加权限
	 * @param roleId
	 * @param funcIdList
	 * @throws DAOException
	 */
	public void addFunc(Long roleId, List<Long> funcIdList)throws DAOException;
	/**
	 * 指定角色取消权限
	 * @param roleId
	 * @param funcIdList
	 * @throws DAOException
	 */
	public void removeFunc(Long roleId, List<Long> funcIdList)throws DAOException;
	/**
	 * 获取机构可用角色列表
	 * @param brchId 机构id
	 * @param containParent 是否包含上级结构
	 * @param page 分页
	 * @return 角色集合
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public List<Role>  findAvailableRolesByBranch(Long brchId,boolean containParent,Page page)throws ServiceException,DAOException;
	
}
