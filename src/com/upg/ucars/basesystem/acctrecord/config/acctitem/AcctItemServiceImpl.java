/*
 * 源程序名称: AcctItemServiceImpl.java
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称: 账务配置-记账项
 * 
 */
package com.upg.ucars.basesystem.acctrecord.config.acctitem;

import java.util.ArrayList;
import java.util.List;

import com.upg.ucars.basesystem.acctrecord.config.tranitem.ITranItemDAO;
import com.upg.ucars.constant.AcctRecordErrorConst;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctItem;
import com.upg.ucars.mapping.basesystem.acctrecord.TranItem;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;

/**
 * 
 * 功能说明：记账项服务层实现
 * @author shentuwy  
 * @date Jun 27, 2011 08:46:20 AM 
 *
 */
public class AcctItemServiceImpl extends BaseService implements IAcctItemService{
	private IAcctItemDAO acctItemDAO;
	private ITranItemDAO tranItemDAO;

	public ITranItemDAO getTranItemDAO() {
		return tranItemDAO;
	}

	public void setTranItemDAO(ITranItemDAO tranItemDAO) {
		this.tranItemDAO = tranItemDAO;
	}

	public IAcctItemDAO getAcctItemDAO() {
		return acctItemDAO;
	}

	public void setAcctItemDAO(IAcctItemDAO acctItemDAO) {
		this.acctItemDAO = acctItemDAO;
	}

	public void createAcctItem(AcctItem acctItem) throws DAOException {
		acctItemDAO.save(acctItem);
	}

	public void deleteAcctItem(Long id) throws DAOException, ServiceException {
		List list = tranItemDAO.queryTranItemByAcctItemId(id);
		if(list != null && list.size()>0){
			ExceptionManager.throwException(ServiceException.class, AcctRecordErrorConst.ACCT_ITEM_ALREADY_USED);
		}
		acctItemDAO.delete(id);
	}

	public void modifyAcctItem(AcctItem acctItem) throws DAOException {
		List<TranItem> list = tranItemDAO.queryTranItemByAcctItemId(acctItem.getId());
		if(!acctItem.getExpress().startsWith("{") || !acctItem.getExpress().endsWith("}")){
			if(list != null){
				for(int i=0; i<list.size(); i++){
					list.get(i).setValue(acctItem.getExpress());
				}
				tranItemDAO.saveOrUpdateAll(list);
			}
		}
		acctItemDAO.update(acctItem);
	}

	public List<AcctItem> queryAcctItem(List<ConditionBean> cblist, Page page)
			throws DAOException, ServiceException {
		List<OrderBean> oblist = new ArrayList<OrderBean>();
		oblist.add(new OrderBean("itemNo"));
		return acctItemDAO.queryEntity(cblist, oblist, page);
	}

	public AcctItem findAcctItem(Long id) throws DAOException, ServiceException {
		return acctItemDAO.get(id);
	}
}
