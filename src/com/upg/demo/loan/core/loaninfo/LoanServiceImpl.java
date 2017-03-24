package com.upg.demo.loan.core.loaninfo;

import java.util.List;

import com.upg.demo.mapping.loan.LoanInfo;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BusiBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
/**
 * 借款业务服务
 * 
 * @author shentuwy
 */
public class LoanServiceImpl extends BusiBaseService implements IBLoanService {
	private ILoanDAO loanDAO;//spring注入

	/**
	 * (non-Javadoc)
	 * @see demo.bpm.loan.core.ILoanService#save(demo.bpm.fbo.LoanInfo)
	 */
	public void save(LoanInfo loanInfo) throws ServiceException, DAOException {
		loanInfo.setStatus(LoanInfo.STATUS_NEW);
		//用以演示一个异常的效果
		if (loanInfo.getUserName().equals("aaa")){
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.DEMO_LOAN_INFO_SAVE, new String[]{loanInfo.getUserName()});			
		}
		
		this.loanDAO.save(loanInfo);	
	}

	public void update(LoanInfo loanInfo) throws ServiceException{
		try {
			this.loanDAO.update(loanInfo);			
		} catch (Exception e) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.DEMO_LOAN_INFO_UPDATE, e);
		}		
		
	}

	public LoanInfo getById(Long id)throws DAOException {		
		return this.loanDAO.get(id);	
				
	}

	public void deleteById(Long id)throws ServiceException {		
		try {
			this.loanDAO.delete(id);	
		} catch (Exception e) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.DEMO_LOAN_INFO_DEL);
		}

	}

	public List<LoanInfo> queryLoanInfo(QueryComponent qcpt, Page page)throws ServiceException {	
		return this.loanDAO.queryLoanInfo(qcpt, page);
	}

	public ILoanDAO getLoanDAO() {
		return loanDAO;
	}

	public void setLoanDAO(ILoanDAO loanDAO) {
		this.loanDAO = loanDAO;
	}

	public void payment(LoanInfo loanInfo) throws ServiceException {
		try {
			//进行划款....
			System.out.println(loanInfo.getUserName() + ",贷款了:" + loanInfo.getMoney());
			System.out.println(loanInfo.toStr());
		} catch (Exception e) {
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.DEMO_LOAN_PAYMENT_ERROR, new String[]{loanInfo.getUserName(), loanInfo.getMoney().toString()}, e);
			
		}
		
		
	}

	public List<LoanInfo> query(SearchBean searchBean) throws ServiceException {
		return this.loanDAO.query(searchBean);
	}

}
