/*
 * 源程序名称: ITranItemService.java
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称: 账务配置-交易项
 * 
 */
package com.upg.ucars.basesystem.acctrecord.config.tranitem;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.acctrecord.TranItem;
import com.upg.ucars.model.ConditionBean;

/**
 * 
 * 功能说明：交易项服务层接口
 * @author shentuwy  
 * @date Jun 24, 2011 11:52:28 AM 
 *
 */
public interface ITranItemService extends IBaseService{
	/**
	 * 新增交易项
	 * @param tranItem
	 * @throws DAOException
	 */
	public void createTranItem(TranItem tranItem) throws DAOException;
	
	/**
	 * 修改交易项
	 * @param tranItem
	 * @throws DAOException
	 */
	public void modifyTranItem(TranItem tranItem) throws DAOException, ServiceException;
	
	/**
	 * 删除交易项
	 * @param id
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public void deleteTranItem(Long id) throws DAOException, ServiceException;
	
	
	/**
	 * 查找交易项
	 * @param id
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public TranItem findTranItem(Long id) throws DAOException, ServiceException;
	
	/**
	 * 根据查询条件和分页信息查询交易项
	 * @param cblist 查询条件
	 * @param page 分页信息
	 * @return
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public List<TranItem> queryTranItem(List<ConditionBean> cblist, Page page) throws DAOException, ServiceException;
	
	/**
	 * 删除一行交易 
	 *
	 * @param tranId
	 * @param groupNo
	 * @param rowIndex
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public void deleteRowTranItem(Long tranId, String groupNo, int rowNo) throws DAOException, ServiceException;
	/**
	 * 两行互换 
	 *
	 * @param tranId
	 * @param groupNo
	 * @param rowNo1
	 * @param rowNo2
	 * @throws DAOException
	 */
	public void exchangeRowIndex(Long tranId, String groupNo, int rowNo1, int rowNo2) throws DAOException;
	/**
	 * 获取最大行号
	 * @param tranId
	 * @param groupNo
	 * @return
	 * @throws DAOException
	 */
	public int getMaxRowIndex(Long tranId, String groupNo) throws DAOException;

}
