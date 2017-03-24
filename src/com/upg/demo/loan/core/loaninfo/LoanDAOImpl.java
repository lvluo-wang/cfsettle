package com.upg.demo.loan.core.loaninfo;

import java.util.ArrayList;
import java.util.List;

import com.upg.demo.mapping.loan.LoanInfo;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.model.ConditionBean;

public class LoanDAOImpl extends BaseDAO<LoanInfo, Long> implements ILoanDAO {	

	public List<LoanInfo> queryLoanInfo(List<ConditionBean> conditionList, Page page) throws DAOException{
		String hql = "from LoanInfo";
		QueryCondition qc = new QueryCondition(hql, "id");
		qc.addConditionList(conditionList);
		List<LoanInfo> list = super.queryByCondition(qc, page);
		return list;	
		
	}

	public List<LoanInfo> queryLoanInfo(QueryComponent qcpt, Page page) throws DAOException {		
		String hql = "from LoanInfo loanInfo ";	
		QueryCondition qc = new QueryCondition(hql, "id");
		
		List<LoanInfo> list = super.queryByCondition(qc, page , qcpt );
		return list;
	}

	public LoanInfo loadById(Long id) throws DAOException {
		LoanInfo info = this.get(id);
		if (info == null)
			throw ExceptionManager.getException(DAOException.class, ErrorCodeConst.DB_COMMON_ERROR, new String[]{"LoanIfo entity no exist. id="+id});
			
		return null;
	}

	@Override
	public Class getEntityClass() {
		return LoanInfo.class;
	}

	public List<LoanInfo> query(SearchBean searchBean) throws DAOException {
		String hql = " from LoanInfo t where 1=1 ";
		List params = new ArrayList();
		Page page_ = new Page();
		if(searchBean != null){
			if(searchBean.getNo() != null && !"".equals(searchBean.getNo())){
				hql = hql + " and t.loanNo like ? ";
				params.add(searchBean.getNo());
			}
			if(searchBean.getUserName() != null && !"".equals(searchBean.getUserName())){
				hql = hql + " and t.userName like ? ";
				params.add(searchBean.getUserName());
			}
			if(searchBean.getStatus() != null && !"".equals(searchBean.getStatus())){
				hql = hql + " and t.status = ? ";
				params.add(searchBean.getStatus());
			}
			page_.setCurrentPage(searchBean.getCurrentPage());
			page_.setPageSize(searchBean.getPageSize());
		}
		final String hql_ = hql;
		final Object[] param_arr = params.toArray();
		final Page page = page_;
		List<LoanInfo> lis = this.find(hql_, param_arr, page);
		return lis;
	}

}
