package com.upg.ucars.basesystem.security.core.sysfunc;

import java.util.List;
import java.util.Map;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.model.security.UserLogonInfo;

/**
 * 
 * 菜单、权限服务
 * 
 * @author shentuwy
 * @date 2012-7-27
 *
 */
public interface ISysfuncService extends IBaseService{
	
	
	/**
	 * 
	 * 获取所有网银的菜单权限
	 *
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public List<Sysfunc> getAllEBankMenuSysfunc() throws ServiceException, DAOException;
	
	/**
	 * 
	 * 获取所有行内的菜单权限
	 *
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public List<Sysfunc> getAllInnerMenuSysfunc() throws ServiceException,DAOException;
	
	/**
	 * 根据用户的角色和上级权限id异步获取其可用的子菜单
	 * @param logonInfo 用户登录信息
	 * @param parentFuncId 上级权限id
	 * @return
	 */
	public List<Map<String,Object>> getAsyncSubMenus(UserLogonInfo logonInfo,Long parentFuncId)throws ServiceException,DAOException;
	/**
	 * 创建权限
	 * @param func 权限
	 */
	public void createSysfunc(Sysfunc func)throws ServiceException,DAOException;
	/**
	 * 修改权限
	 * @param func
	 */
	public void modifySysfunc(Sysfunc func)throws ServiceException,DAOException;
	/**
	 * 查询权限集合
	 * @param qc 查询组件
	 * @param page 页面
	 * @return
	 */
	public List<Sysfunc> querySysfunc(QueryComponent qc,Page page)throws ServiceException,DAOException;
	/**
	 * 获取子权限
	 * @param funcId
	 * @return
	 */
	public List<Sysfunc> findSubFunc(Long funcId)throws ServiceException,DAOException;
	/**
	 * 异步的获取子权限
	 * @param parentFuncId
	 * @return
	 */
	public List<Map<String,Object>> getAsyncSubFunc(Long parentFuncId)throws ServiceException,DAOException;
	/**
	 * 判断指定权限是否拥有子权限
	 * @param parentFuncId
	 * @return
	 */
	public boolean hasSubFunc(Long parentFuncId)throws ServiceException,DAOException;
	/**
	 * 根据权限id获取权限对象
	 * @param funcId
	 * @return
	 * @throws ServiceException
	 */
	public Sysfunc getSysfunc(Long funcId)throws ServiceException,DAOException;
	/**
	 * 移除一个或多个权限
	 * @param funcId
	 * @throws ServiceException
	 */
	public void removeSysfunc(Long funcId)throws ServiceException,DAOException;
	/**
	 * 校验指定的权限是否可建立子权限
	 * @param funcId
	 */
	public void regxCanAddSubFunc(Long funcId)throws ServiceException,DAOException;
	/**
	 * 将source权限移到target权限的point位置
	 * @param sourceFunc 将要移动的权限
	 * @param targetFunc 目标权限
	 * @param point 位置 'append','top' or 'bottom‘
	 */
	public void moveFunc(Long sourceFunc,Long targetFunc,String point)throws ServiceException,DAOException;
	/**
	 * 根据用户登录信息，获取子菜单
	 * @param logonInfo
	 * @param funcId
	 * @return
	 */
	public List<Sysfunc> getSubMenu(UserLogonInfo logonInfo, Long funcId)throws ServiceException,DAOException;
	/**
	 * 根据用户登录信息，获取子功能
	 * @param funcId
	 * @return
	 */
	public List<Sysfunc> getSubFunc(Long funcId)throws ServiceException,DAOException;
	/**
	 * 判断是否拥有子菜单
	 * @param logonInfo
	 * @param funcId
	 * @return
	 */
	public boolean hasSubMenu(UserLogonInfo logonInfo,Long funcId)throws ServiceException,DAOException;
	/**
	 * 根据用户登录信息获取菜单
	 * @param logonInfo
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public List<Sysfunc> getMenu(UserLogonInfo logonInfo)throws ServiceException,DAOException;
	/**
	 * 获取用户可用系统权限
	 * @param logonInfo
	 * @return
	 */
	public List<Sysfunc> getUserFunc(UserLogonInfo logonInfo)throws ServiceException,DAOException;
	/**
	 * 获取两个树形结构
	 * @param allFunc 所有的
	 * @param beFunc 已有的
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public  Map<String,Object> getTwoTree(List<Sysfunc> allFunc,List<Sysfunc> beFunc)throws ServiceException,DAOException;
	
	/**
	 * 当前登录者是否有权限
	 * 
	 * @param userId
	 * @param resourceId
	 * @return
	 */
	public boolean hasResourcePermission(List<Long> roleIds,String resourceId);
}
