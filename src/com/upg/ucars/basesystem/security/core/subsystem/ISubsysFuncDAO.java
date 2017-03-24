/*
 * 源程序名称: ISubsysFuncDAO.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 技术平台：XCARS
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.basesystem.security.core.subsystem;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.security.ReSubsysFunc;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
/**
 * 
 * 功能说明：子系统与权限中间实体数据层
 * @author shentuwy  
 * @date 2011-5-31 上午08:59:55 
 *
 */
public interface ISubsysFuncDAO extends IBaseDAO<ReSubsysFunc, Long> {

	/**
	 * 根据子系统id获取子系统拥有的权限集合
	 * @param subsysId 子系统id
	 * @return
	 * @throws DAOException
	 */
	public List<Sysfunc> getSysfuncBySubsysId(Long subsysId)throws DAOException;
	
	/**
	 * 
	 * 查询菜单权限集合
	 *
	 * @param subsysId
	 * @return
	 * @throws DAOException
	 */
	public List<Sysfunc> getMenuSysfuncBySubsysId(Long subsysId) throws DAOException;
	
	/**
	 * 
	 * @param subsysId
	 * @param funcId
	 * @return
	 * @throws DAOException
	 */
	public ReSubsysFunc findReSubsysFunc(Long subsysId, Long funcId)throws DAOException;
	/**
	 * 
	 * @param sysIdList
	 * @return
	 * @throws DAOException
	 */
	public List<Sysfunc> getSysfuncBySubsysId(List<Long> sysIdList)throws DAOException;
	
	/**
	 * 
	 * 获取子系统的子菜单
	 *
	 * @param subsysId
	 * @param parentFuncId
	 * @return
	 * @throws DAOException
	 */
	public List<Sysfunc> getSubMenuList(Long subsysId,Long parentFuncId) throws DAOException;
	
	/**
	 * 是否有子系统引用权限
	 * @param funcId
	 * @return true:有 false:无
	 * @throws DAOException
	 */
	public boolean hasSubSysRefFunc(Long funcId) throws DAOException;
	
	/**
	 * 校验子系统是否还引用权限
	 * @param subsysId
	 * @return true:有 false:无
	 * @throws DAOException
	 */
	public boolean hasFunc(Long subsysId) throws DAOException;

}
