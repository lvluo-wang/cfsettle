package com.upg.ucars.basesystem.security.core.role;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.security.ReRoleSysfunc;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
/**
 * 角色权限
 * 
 * @author shentuwy
 */
public interface IRoleFuncDAO extends IBaseDAO<ReRoleSysfunc, Long>{
	
	public void addRoleFunc(ReRoleSysfunc rrs) throws DAOException;
	public void updateRole(ReRoleSysfunc rrs) throws DAOException;
	public void deleteRole(ReRoleSysfunc rrs)throws DAOException;
	/**
	 * 获取角色关联的权限ID
	 *
	 * @param roleId
	 * @return
	 * @throws DAOException
	 */
	public List<Long> getReFuncIdsByRoleId(Long roleId) throws DAOException;
	
	/**
	 * 获取角色关联的权限	
	 * 
	 * @param roleId
	 * @return
	 * @throws DAOException
	 */
	public List<Sysfunc> findSysfuncByRoleId(Long roleId) throws DAOException;
	/**
	 * 复核角色权限
	 * @param roleId
	 * @throws DAOException
	 */
	public void checkRoleSyfuncs(Long roleId) throws DAOException;
	
	
	public List<ReRoleSysfunc> getReRoleSysfuncByRoleId(Long roleId) throws DAOException;
	/**
	 * 根据角色Id删除与权限的关联信息
	 * @param roleId
	 * @throws DAOException
	 */
	public void deleteByRoleId(Long roleId) throws DAOException;
	/**
	 * 获取所有的权限
	 * @return
	 * @throws DAOException
	 */
	public List<Sysfunc> getAllSysfuncs() throws DAOException;
	/**
	 * 根据权限id获取角色id集合
	 * @param roleId
	 * @return
	 * @throws DAOException
	 */
	public List<Long> findSysfuncIdsByRoleId(Long roleId)throws DAOException;
	/**
	 * 根据角色id获取权限id集合
	 * @param funcId
	 * @return
	 * @throws DAOException
	 */
	public List<Long> getRoleIdsByFuncId(Long funcId)throws DAOException;

	public ReRoleSysfunc findByRoleIdAndFuncId(Long roleId, Long unFuncId)throws DAOException;
	
	/**
	 * 是否有角色引用权限
	 * @param funcId
	 * @return true:有 false:无
	 * @throws DAOException
	 */
	public boolean hasRoleRefFunc(Long funcId) throws DAOException;
	
}
