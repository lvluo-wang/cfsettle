/*
 * 源程序名称: AcctPointServiceImpl.java
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称: 账务配置-分录结构体
 * 
 */
package com.upg.ucars.basesystem.acctrecord.config.acctrecordbody;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.upg.ucars.basesystem.acctrecord.config.tranitem.ITranItemDAO;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctRecordBody;
import com.upg.ucars.mapping.basesystem.acctrecord.TranItem;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;
import com.upg.ucars.util.BeanUtils;

/**
 * 
 * 功能说明：分录结构体服务层实现
 * @author shentuwy  
 * @date Jun 27, 2011 08:46:20 AM 
 *
 */
public class AcctRecordBodyServiceImpl extends BaseService implements IAcctRecordBodyService{
	private IAcctRecordBodyDAO acctRecordBodyDAO;
	private ITranItemDAO tranItemDAO;

	public ITranItemDAO getTranItemDAO() {
		return tranItemDAO;
	}

	public void setTranItemDAO(ITranItemDAO tranItemDAO) {
		this.tranItemDAO = tranItemDAO;
	}

	public IAcctRecordBodyDAO getAcctRecordBodyDAO() {
		return acctRecordBodyDAO;
	}

	public void setAcctRecordBodyDAO(IAcctRecordBodyDAO acctRecordBodyDAO) {
		this.acctRecordBodyDAO = acctRecordBodyDAO;
	}

	public void createAcctRecordBody(AcctRecordBody acctRecordBody)
			throws DAOException {
		int serialNo = acctRecordBodyDAO.getMaxSerialNoByGroupNo(acctRecordBody.getMiNo(), acctRecordBody.getGroupNo());
		acctRecordBody.setSerialNo(serialNo+1);
		acctRecordBodyDAO.save(acctRecordBody);
	}

	public void deleteAcctRecordBody(Long id) throws DAOException,
			ServiceException {
		List<TranItem>  list = tranItemDAO.queryTranItemByRecordBodyId(id);
		
		if(list != null && list.size()>0){
			tranItemDAO.delAll(list);
			//ExceptionManager.throwException(ServiceException.class, AcctRecordErrorConst.ACCT_RECORD_BODY_ALREADY_USED);
		}
		//序号下降
		AcctRecordBody dbobj = acctRecordBodyDAO.get(id);
		acctRecordBodyDAO.autoAdjustSerialNo(dbobj.getMiNo(), dbobj.getGroupNo(), false, 1, dbobj.getSerialNo(), 65535);
		acctRecordBodyDAO.delete(id);
	}

	public AcctRecordBody findAcctRecordBody(Long id) throws DAOException,
			ServiceException {
		return acctRecordBodyDAO.get(id);
	}

	public void modifyAcctRecordBody(AcctRecordBody acctRecordBody)
			throws DAOException, ServiceException {
		try{
			AcctRecordBody dbobj = acctRecordBodyDAO.get(acctRecordBody.getId());
			if(!dbobj.getMiNo().equals(acctRecordBody.getMiNo()) || !dbobj.getGroupNo().equals(acctRecordBody.getGroupNo())){
				int serialNo = acctRecordBodyDAO.getMaxSerialNoByGroupNo(acctRecordBody.getMiNo(), acctRecordBody.getGroupNo());
				acctRecordBody.setSerialNo(serialNo+1);
				
				//序号下降
				acctRecordBodyDAO.autoAdjustSerialNo(dbobj.getMiNo(), dbobj.getGroupNo(), false, 1, dbobj.getSerialNo(), 65535);
			}
			
			BeanUtils.copyNoNullProperties(dbobj, acctRecordBody);
			acctRecordBodyDAO.update(dbobj);
		}
		catch(InvocationTargetException e){
			ExceptionManager.throwException(ServiceException.class, "", e);
		}
		catch(IllegalAccessException e){
			ExceptionManager.throwException(ServiceException.class, "", e);
		}
	}

	public List<AcctRecordBody> queryAcctRecordBody(List<ConditionBean> cblist,
			Page page) throws DAOException, ServiceException {
		List<OrderBean> oblist = new ArrayList<OrderBean>();
		oblist.add(new OrderBean("miNo"));
		oblist.add(new OrderBean("groupNo"));
		oblist.add(new OrderBean("serialNo"));
		return acctRecordBodyDAO.queryEntity(cblist, oblist, page);
	}

	public List<AcctRecordBody> queryAcctRecordBodyByMember(String miNo,
			List<ConditionBean> cblist, Page page) throws DAOException,
			ServiceException {
		if (cblist == null)
			cblist = new ArrayList<ConditionBean>(1);
		
		cblist.add(new ConditionBean("miNo", miNo));
		
		
		
		List<OrderBean> oblist = new ArrayList<OrderBean>();
		oblist.add(new OrderBean("miNo"));
		oblist.add(new OrderBean("groupNo"));
		oblist.add(new OrderBean("serialNo"));
		return acctRecordBodyDAO.queryEntity(cblist, oblist, page);
	}
	
}
