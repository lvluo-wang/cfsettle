/*
 * 源程序名称: IAcctItemDAO.java
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称: 账务配置-交易项
 * 
 */
package com.upg.ucars.basesystem.acctrecord.config.tranitem;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.acctrecord.TranItem;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;

/**
 * 
 * 功能说明：交易项数据库操作接口
 * @author shentuwy  
 * @date Jun 27, 2011 08:46:20 AM 
 *
 */
public interface ITranItemDAO extends IBaseDAO<TranItem, Long>{

	/**
	 * 根据组合条件查询交易项
	 * @param cblist
	 * @param oblist
	 * @param page
	 * @return
	 * @throws DAOException
	 */
	public List queryTranItem(List<ConditionBean> cblist, List<OrderBean> oblist, Page page) throws DAOException;
	
	/**
	 * 根据记账项id查询交易项
	 * @param acctItemId
	 * @return
	 * @throws DAOException
	 */
	public List<TranItem> queryTranItemByAcctItemId(Long acctItemId) throws DAOException;
	
	/**
	 * 根据记账交易ID查询交易项
	 * @param acctTranId
	 * @return
	 * @throws DAOException
	 */
	public List<TranItem> queryTranItemByAcctTranId(Long acctTranId) throws DAOException;
	
	/**
	 * 根据分录结构体ID查询交易项
	 * @param acctTranId
	 * @return
	 * @throws DAOException
	 */
	public List<TranItem> queryTranItemByRecordBodyId(Long recordBodyId) throws DAOException;
	/**
	 * 删除一行分录信息
	 *
	 * @param tranId
	 * @param groupNo
	 * @param rowNo
	 * @throws DAOException
	 */
	public void deleteRowTranItem(Long tranId, String groupNo, int rowNo) throws DAOException;
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
