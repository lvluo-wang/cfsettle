package com.upg.ucars.basesystem.security.core.user;



import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.ReUserRole;
import com.upg.ucars.mapping.basesystem.security.Role;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;



public interface IUserDAO extends IBaseDAO<Buser,Long> {
	/**
	 * 通过用户编号获取用户信息
	 * @param userNo
	 * @return
	 */
	public Buser getUserByUserNo(String userNo)throws DAOException;
	
	public Buser getUserByUserName(String userName)throws DAOException;
	/**
	 * 通过用户编号获取该用户拥有的角色集合
	 * @param userNo
	 * @return
	 */
	public List getRolesByUserNo(String userNo)throws DAOException;
	
	
	public void addReUserRole(ReUserRole rur) throws DAOException;
	public void updateReUserRole(ReUserRole rur) throws DAOException;
	public void deleteReUserRole(ReUserRole rur)throws DAOException;
	/**
	 * 删除用户相关的角色关联
	 *
	 * @param userId
	 * @throws DAOException
	 */
	public void deleteReUserRoleByUser(Long userId)throws DAOException;
	
	/**
	 * 查询用户拥有的角色
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	public List<Role> queryUserRole(Long userId)throws DAOException;
	/**
	 * 查询用户拥有的权限
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	public List<Sysfunc> queryUserFunction(Long userId)throws DAOException;
	
	/**
	 * 查询用户拥有的权限
	 * @param userId
	 * @return funcId列表
	 * @throws DAOException
	 */
	public List<Long> queryUserFunctionWithCache(Long userId)throws DAOException;
	
	/**
	 * 修改指定用户角色关联信息的状态
	 * @param userId
	 * @param roleStatus 见UserStatus.RE_ROLE_STATUS_*
	 * @throws DAOException
	 */
	public void updateReUserRoleStatusByUserId(Long userId, String roleStatus)throws DAOException;
	/**
	 * 根据用户id获取其角色映射关系列表
	 * @param userId 用户id
	 * @return
	 * @throws DAOException
	 */
	public List<ReUserRole> findReUserRoleByUserId(Long userId)throws DAOException;
	
	public List getAllredCheckedRolesByUserNo(String userNo)throws DAOException;
	/**
	 * 根据用户编号和接入编号查询用户
	 * @param userNo 用户编号
	 * @param miNo 接入编号
	 * 
	 * @return
	 * 
	 */
	public Buser getUserByUserNoAndMiNo(String userNo, String miNo);

	public boolean isExistUserByUserNo(String userNo)throws DAOException;
	
	public boolean isExistUserByuserNoAndMiNo(String userNo,String miNo)throws DAOException;
	/**
	 * 更新用户前的校验
	 * @param user
	 * @return
	 * @throws DAOException
	 */
	public boolean updateUserRegxUserNo(Buser user)throws DAOException;
	/**
	 * 获取用户的角色集合
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	public List<Role> getRolesByUserId(Long userId)throws DAOException;
	/**
	 * 获取用户的角色Id集合
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	public List<Long> getRoleIdsByUserId(Long userId)throws DAOException;;
	/**
	 * 获取用户的已符合角色集合
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	public List<Role> getAllredCheckedRolesByUserId(Long userId)throws DAOException;
	/**
	 * 获取用户的已符合角色Id集合
	 * @param userId
	 * @return
	 * @throws DAOException
	 */
	public List<Long> getAllredCheckedRoleIdsByUserId(Long userId)throws DAOException;
	
	/**
	 * 根据用户id和用户状态获取用户列表一
	 * 
	 * @param ids
	 * @param status
	 * @return
	 * @throws DAOException
	 */
	public List<Buser> getUserListByIds(List<Long> ids) throws DAOException;
	
	/**
	 * 根据用户id和用户角色状态查询权限列表
	 * @param userId
	 * @param userRoleStatus
	 * @return
	 * @throws DAOException
	 */
	public List<Sysfunc> queryUserFunc(Long userId,String userRoleStatus) throws DAOException;
	
}
