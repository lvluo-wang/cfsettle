package com.upg.ucars.basesystem.security.core.sysfunc;

import java.util.List;
import java.util.Map;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.security.Role;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;

public interface ISysfuncDAO extends IBaseDAO<Sysfunc,Long>{
	/**
	 * 根据角色集合获取指定上级权限id的子菜单
	 * @param roles
	 * @param funcId
	 * @return
	 */
	public List<Sysfunc>  getSubMenuByRoles(List<Role> roles,Long funcId)throws DAOException;
	/**
	 * 根据角色集合获取指定上级权限id的子菜单
	 * @param roles
	 * @param funcId
	 * @return
	 */
	public List<Sysfunc>  getSubMenuByRoleIds(List<Long> roles,Long funcId)throws DAOException;
	/**
	 * 判断指定角色集合和上级权限id是否拥有子菜单
	 * @param roles
	 * @param funcId
	 * @return
	 */
	public boolean hasSubMenuByRoles(List<Role> roles,Long funcId)throws DAOException;
	/**
	 * 判断指定角色集合和上级权限id是否拥有子菜单
	 * @param roles
	 * @param funcId
	 * @return
	 */
	public boolean hasSubMenuByRoleIds(List<Long> roles,Long funcId)throws DAOException;
	/**
	 * 获取指定上级权限id的子权限集合
	 * @param parentFuncId
	 * @return
	 */
	public List<Sysfunc>  findFuncByParentId(Long parentFuncId)throws DAOException;
	/**
	 * 判断指定的权限id是否拥有子权限
	 * @param parentFuncId
	 * @return
	 */
	public boolean hasSubFuncByPraentId(Long parentFuncId)throws DAOException;
	/**
	 *判断是否同id
	 * @param funcId
	 * @return
	 */
	public boolean hasSameFuncId(Long funcId)throws DAOException;
	/**
	 * 判断是否同名
	 * @param funcName
	 * @return
	 */
	public boolean hasSameFuncName(String funcName,Long funcId)throws DAOException;
	/**
	 * 判断是否同名
	 * @param funcNameEn
	 * @return
	 */
	public boolean hasSameFuncNameKey(String funcNameEn,Long funcId)throws DAOException;
	
	/**
	 * 判断是否同名
	 * @param funcName
	 * @return
	 */
	public boolean hasSameFuncName(String funcName,Long funcId,Long excludefuncId)throws DAOException;
	/**
	 * 判断是否同名
	 * @param funcNameEn
	 * @return
	 */
	public boolean hasSameFuncNameKey(String funcNameEn,Long funcId,Long excludefuncId)throws DAOException;
	
	/**
	 * 获取所有拥有URL属性的系统功能
	 * @return
	 */
	public List<Sysfunc>  findAllURLFunc()throws DAOException;
	/**
	 * 获取指定功能的角色绑定者
	 * @param funcId
	 * @return
	 */
	public List fundRolesByfuncId(Long funcId)throws DAOException;
	/**
	 * 获取去指定权限id的子节点中最大排序序号
	 * @param targetFunc
	 */
	public Long getMaxSortNo(Long targetFunc)throws DAOException;
	/**
	 * 获取指定的权限的同子集中排序级别级的
	 * @param target
	 * @return
	 */
	public List<Sysfunc>  getButtomFunc(Sysfunc target)throws DAOException;
	/**
	 * 根据角色集合获取系统权限
	 * @param roles
	 * @return
	 */
	public List<Sysfunc>  getSyfuncByRoleList(List<Role> roles)throws DAOException;
	/**
	 * 根据角色集合获取系统权限
	 * @param roles
	 * @return
	 */
	public List<Sysfunc>  getSyfuncByRoleIdList(List<Long> roles)throws DAOException;
	/**
	 * 获取角色集合对应的菜单
	 * @param roles
	 * @return
	 * @throws DAOException
	 */
	public List<Sysfunc>  getMenuByRoleList(List<Role> roles)throws DAOException;
	/**
	 * 获取角色集合对应的菜单
	 * @param roles
	 * @return
	 * @throws DAOException
	 */
	public List<Sysfunc>  getMenuByRoleIdList(List<Long> roles)throws DAOException;
	/**
	 * 获取已复核的权限及角色对应关系
	 * @return
	 * @throws DAOException
	 */
	public List<Map<String,Object>> findCheckedRoleWithFunc()throws DAOException;
	
	/**
	 * 
	 * 获取权限列表
	 *
	 * @param param
	 * @return
	 * @throws DAOException
	 */
	public List<Sysfunc> getSysfuncList(Map<String,Object> param) throws DAOException;
	
	/**
	 * 
	 * 按使用类型和权限类型查询权限列表
	 * @param usetypes  使用类型list
	 * @param functypes 权限类型list
	 * @return List<Sysfunc>
	 * @throws DAOException
	 */
	public List<Sysfunc> querySysfuncs(List<String> usetypes,List<String> functypes) throws DAOException;
	
	/**
	 * 是否有权限
	 * 
	 * @param roleId
	 * @param resourceId
	 * @return
	 */
	public boolean hasResourcePermission(List<Long> roleIds,String resourceId);
	
}
