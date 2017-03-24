/*
 * 源程序名称: AcctPointServiceImpl.java
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称: 账务配置-记账点
 * 
 */
package com.upg.ucars.basesystem.acctrecord.config.acctpoint;

import java.util.ArrayList;
import java.util.List;

import com.upg.ucars.basesystem.acctrecord.config.acctitem.IAcctItemDAO;
import com.upg.ucars.basesystem.acctrecord.config.accttran.IAcctTranDAO;
import com.upg.ucars.constant.AcctRecordErrorConst;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctPoint;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;

/**
 * 
 * 功能说明：记账点服务层实现
 * @author shentuwy  
 * @date Jun 27, 2011 08:46:20 AM 
 *
 */
public class AcctPointServiceImpl extends BaseService implements IAcctPointService{
	private IAcctPointDAO acctPointDAO;
	private IAcctItemDAO acctItemDAO;
	private IAcctTranDAO acctTranDAO;

	public IAcctItemDAO getAcctItemDAO() {
		return acctItemDAO;
	}

	public void setAcctItemDAO(IAcctItemDAO acctItemDAO) {
		this.acctItemDAO = acctItemDAO;
	}

	public IAcctTranDAO getAcctTranDAO() {
		return acctTranDAO;
	}

	public void setAcctTranDAO(IAcctTranDAO acctTranDAO) {
		this.acctTranDAO = acctTranDAO;
	}

	public IAcctPointDAO getAcctPointDAO() {
		return acctPointDAO;
	}

	public void setAcctPointDAO(IAcctPointDAO acctPointDAO) {
		this.acctPointDAO = acctPointDAO;
	}

	public void createAcctPoint(AcctPoint acctPoint) throws DAOException {
		acctPointDAO.save(acctPoint);
	}

	public void deleteAcctPoint(Long id) throws DAOException, ServiceException {
		List list = acctItemDAO.queryAcctItemByAcctPointId(id);
		if(list != null && list.size()>0){
			ExceptionManager.throwException(ServiceException.class, AcctRecordErrorConst.ACCT_POINT_ALREADY_USED);
		}
		list = acctTranDAO.queryAcctTranByAcctPointId(id);
		if(list != null && list.size()>0){
			ExceptionManager.throwException(ServiceException.class, AcctRecordErrorConst.ACCT_POINT_ALREADY_USED);
		}
		acctPointDAO.delete(id);
	}
	
	public List<AcctPoint> queryAcctPoint(List<ConditionBean> cblist, Page page)
			throws DAOException, ServiceException {
		List<OrderBean> oblist = new ArrayList<OrderBean>();
		oblist.add(new OrderBean("id"));
		return acctPointDAO.queryEntity(cblist, oblist, page);
	}

	public AcctPoint findAcctPoint(Long id) throws DAOException {
		return this.acctPointDAO.get(id);
	}
	
}
