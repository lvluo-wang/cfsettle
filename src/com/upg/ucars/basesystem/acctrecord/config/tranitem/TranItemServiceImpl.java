/*
 * 源程序名称: AcctPointServiceImpl.java
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称: 账务配置-交易项
 * 
 */
package com.upg.ucars.basesystem.acctrecord.config.tranitem;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.upg.ucars.constant.AcctRecordErrorConst;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.acctrecord.TranItem;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;
import com.upg.ucars.util.BeanUtils;

/**
 * 
 * 功能说明：交易项服务层实现
 * @author shentuwy  
 * @date Jun 27, 2011 08:46:20 AM 
 *
 */
public class TranItemServiceImpl extends BaseService implements ITranItemService{
	private ITranItemDAO tranItemDAO;

	public ITranItemDAO getTranItemDAO() {
		return tranItemDAO;
	}

	public void setTranItemDAO(ITranItemDAO tranItemDAO) {
		this.tranItemDAO = tranItemDAO;
	}

	public void createTranItem(TranItem tranItem) throws DAOException {
		List<ConditionBean> cblist = new ArrayList<ConditionBean>();
		cblist.add(new ConditionBean("tranId",tranItem.getTranId()));
		cblist.add(new ConditionBean("groupNo",tranItem.getGroupNo()));
		cblist.add(new ConditionBean("rowNo",tranItem.getRowNo()));
		cblist.add(new ConditionBean("serialNo",tranItem.getSerialNo()));
		List<TranItem> list = tranItemDAO.queryEntity(cblist, null);
		if(list!=null && list.size()>0){
			ExceptionManager.throwException(ServiceException.class, AcctRecordErrorConst.TRAN_ITEM_ALREADY_EXISTS);
		}
		
		tranItemDAO.save(tranItem);
	}

	public void deleteTranItem(Long id) throws DAOException, ServiceException {
		tranItemDAO.delete(id);
	}

	public TranItem findTranItem(Long id) throws DAOException, ServiceException {
		return tranItemDAO.get(id);
	}

	public void modifyTranItem(TranItem tranItem) throws DAOException, ServiceException {
		TranItem tmp = tranItemDAO.get(tranItem.getId());
		if(tmp.getTranId().equals(tranItem.getTranId()) && tmp.getGroupNo().equals(tranItem.getGroupNo()) && tmp.getSerialNo()==tranItem.getSerialNo()){
			tmp.setAcctItemId(tranItem.getAcctItemId());
			tmp.setValue(tranItem.getValue());
			tranItemDAO.update(tmp);
			return;
		}
		
		List<ConditionBean> cblist = new ArrayList<ConditionBean>();
		cblist.add(new ConditionBean("tranId",tranItem.getTranId()));
		cblist.add(new ConditionBean("groupNo",tranItem.getGroupNo()));
		cblist.add(new ConditionBean("rowNo",tranItem.getRowNo()));
		cblist.add(new ConditionBean("serialNo",tranItem.getSerialNo()));
		List<TranItem> list = tranItemDAO.queryEntity(cblist, null);
		if(list!=null && list.size()>0){
			ExceptionManager.throwException(ServiceException.class, AcctRecordErrorConst.TRAN_ITEM_ALREADY_EXISTS);
		}
		try{
			BeanUtils.copyNoNullProperties(tmp, tranItem);
		}
		catch(InvocationTargetException e){
			ExceptionManager.throwException(ServiceException.class, null, e);
		}
		catch (IllegalAccessException e) {
			ExceptionManager.throwException(ServiceException.class, null, e);
		}
		tranItemDAO.update(tmp);
	}

	public List<TranItem> queryTranItem(List<ConditionBean> cblist, Page page)
			throws DAOException, ServiceException {
		List<OrderBean> oblist = new ArrayList<OrderBean>();
		oblist.add(new OrderBean("tranItem.tranId"));
		oblist.add(new OrderBean("tranItem.groupNo"));
		oblist.add(new OrderBean("tranItem.rowNo"));
		oblist.add(new OrderBean("tranItem.serialNo"));
		return tranItemDAO.queryTranItem(cblist, oblist, page);
	}

	public void deleteRowTranItem(Long tranId, String groupNo, int rowNo)
			throws DAOException, ServiceException {
		this.tranItemDAO.deleteRowTranItem(tranId, groupNo, rowNo);
		
	}

	public void exchangeRowIndex(Long tranId, String groupNo, int rowNo1,
			int rowNo2) throws DAOException {
		this.tranItemDAO.exchangeRowIndex(tranId, groupNo, rowNo1, rowNo2);
		
	}

	public int getMaxRowIndex(Long tranId, String groupNo) throws DAOException {
		return this.getTranItemDAO().getMaxRowIndex(tranId, groupNo);
	}
	
}
