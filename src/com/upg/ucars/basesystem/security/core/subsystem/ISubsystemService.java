/*
 * 源程序名称: ISubsysService.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 技术平台：XCARS
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.basesystem.security.core.subsystem;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.security.Subsystem;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.model.ConditionBean;
/**
 * 
 * 功能说明：子系统服务层
 * @author shentuwy  
 * @author shentuwy
 * @date 2011-5-30 下午04:22:35 
 *
 */
public interface ISubsystemService extends IBaseService {
	
	/** 行内 */
	public static final String TYPE_INNER = "1";
	/** 融资企业 */
	public static final String TYPE_RONGZI = "2";
	/** 核心厂商 */
	public static final String TYPE_HEXIN = "3";
	/** 监管公司  */
	public static final String TYPE_JIANGUAN = "4";
	
	/**
	 * 
	 * 是否是行内子系统
	 *
	 * @param subsystem
	 * @return
	 */
	public boolean isInner(Subsystem subsystem);
	
	/**
	 * 创建子系统
	 * @param subsystem 子系统id
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public void createSubsystem(Subsystem subsystem)throws ServiceException,DAOException;
	/**
	 * 重命名子系统
	 * @param subsysId 子系统id
	 * @param subsysName 新的子系统名称
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public void renameSubsystem(Long subsysId,String subsysName)throws ServiceException,DAOException;
	/**
	 * 开启子系统
	 * @param subsysId 子系统id
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public void openSubsystem(Long subsysId)throws ServiceException,DAOException;
	/**
	 * 关闭子系统
	 * @param subsysId 子系统id
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public void closeSubsystem(Long subsysId)throws ServiceException,DAOException;

	/**
	 * 添加权限
	 * @param subsysId 子系统id
	 * @param funcIdList 权限id列表
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public void addFunc(Long subsysId,List<Long> funcIdList)throws ServiceException,DAOException;
	/**
	 * 移除权限
	 * @param subsysId 子系统id
	 * @param funcIdList 权限id列表
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public void removeFunc(Long subsysId,List<Long> funcIdList)throws ServiceException,DAOException;
	/**
	 * 查询子系统已有权限集合
	 * @param subsysId 子系统id
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public List<Sysfunc> findExistingFunc(Long subsysId)throws ServiceException,DAOException;
	
	/**
	 * 
	 * 查询子系统菜单权限
	 *
	 * @param subsysId
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public List<Sysfunc> findMenuFunc(Long subsysId) throws ServiceException,DAOException;
	/**
	 * 查询全部的子系统集合
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public List<Subsystem> findAllSubsystem()throws ServiceException,DAOException;
	/**
	 * 查询已开启的子系统集合
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public List<Subsystem> findOpenedSubsystem()throws ServiceException,DAOException;
	/**
	 * 根据主键查询子系统
	 * @param subsysId 子系统id
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public Subsystem findById(Long subsysId)throws ServiceException,DAOException;
	/**
	 * 根据组件删除子系统
	 * @param subsysId 子系统id
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public void removeById(Long subsysId) throws ServiceException,DAOException;
	/**
	 * 修改子系统
	 * @param subsystem 子系统
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public void mondifySubsystem(Subsystem subsystem)throws ServiceException,DAOException;
	/**
	 * 条件查询子系统
	 * @param beanList
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public List<Subsystem> findSubsystemByCondition(List<ConditionBean> beanList,Page page)throws ServiceException,DAOException;
	/**
	 * 分页查询
	 * @param pg
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public List<Subsystem> findAllSubsystem(Page pg)throws ServiceException,DAOException;
	/**
	 * 查询子系统的权限
	 * @param sysIdList
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public List<Sysfunc> findExistingFunc(List<Long> sysIdList)throws ServiceException,DAOException;
	
}
