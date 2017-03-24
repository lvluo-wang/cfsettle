/*
 * 源程序名称: IAcctItemService.java
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称: 账务配置-记账项
 * 
 */
package com.upg.ucars.basesystem.acctrecord.config.acctitem;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctItem;
import com.upg.ucars.model.ConditionBean;

/**
 * 
 * 功能说明：记账项服务层接口
 * @author shentuwy  
 * @date Jun 24, 2011 11:52:28 AM 
 *
 */
public interface IAcctItemService extends IBaseService{
	/**
	 * 新增记账项
	 * @param acctItem
	 * @throws DAOException
	 */
	public void createAcctItem(AcctItem acctItem) throws DAOException;
	
	/**
	 * 修改记账项
	 * @param acctItem
	 * @throws DAOException
	 */
	public void modifyAcctItem(AcctItem acctItem) throws DAOException;
	
	/**
	 * 删除记账项
	 * @param id
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public void deleteAcctItem(Long id) throws DAOException, ServiceException;
	
	/**
	 * 查找记账项
	 * @param id
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public AcctItem findAcctItem(Long id) throws DAOException, ServiceException;
	
	/**
	 * 根据查询条件和分页信息查询记账项
	 * @param cblist 查询条件
	 * @param page 分页信息
	 * @return
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public List<AcctItem> queryAcctItem(List<ConditionBean> cblist, Page page) throws DAOException, ServiceException;

}
