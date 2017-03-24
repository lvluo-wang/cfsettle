/*
 * 源程序名称: IAcctPointService.java
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称: 账务配置-记账点
 * 
 */
package com.upg.ucars.basesystem.acctrecord.config.acctpoint;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctPoint;
import com.upg.ucars.model.ConditionBean;

/**
 * 
 * 功能说明：记账点服务层接口
 * @author shentuwy  
 * @date Jun 24, 2011 11:52:28 AM 
 *
 */
public interface IAcctPointService extends IBaseService{

	/**
	 * 新增记账点
	 * @param acctPoint
	 * @throws DAOException
	 */
	public void createAcctPoint(AcctPoint acctPoint) throws DAOException;
	
	/**
	 * 删除记账点
	 * @param id
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public void deleteAcctPoint(Long id) throws DAOException, ServiceException;
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public AcctPoint findAcctPoint(Long id) throws DAOException;
	
	/**
	 * 根据查询条件和分页信息查询记账点
	 * @param cblist 查询条件
	 * @param page 分页信息
	 * @return
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public List<AcctPoint> queryAcctPoint(List<ConditionBean> cblist, Page page) throws DAOException, ServiceException;
	
}
