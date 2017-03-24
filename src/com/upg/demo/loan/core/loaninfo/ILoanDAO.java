package com.upg.demo.loan.core.loaninfo;

import java.util.List;

import com.upg.demo.mapping.loan.LoanInfo;
import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.model.ConditionBean;
/**
 * LoanInfo实体持久化接口
 * 
 * @author shentuwy
 */
public interface ILoanDAO extends IBaseDAO<LoanInfo, Long> {
	
	/**
	 * 条件查询
	 *
	 * @param conditionList
	 * @param page
	 * @return
	 * @throws DAOException
	 */
	public List<LoanInfo> queryLoanInfo(List<ConditionBean> conditionList, Page page) throws DAOException;
	/**
	 * 页面组件查询
	 *
	 * @param qcpt 页面查询组件
	 * @param page 分页对象
	 * @return
	 * @throws DAOException
	 */
	public List<LoanInfo> queryLoanInfo(QueryComponent qcpt, Page page) throws DAOException;
	
	/**
	 * 载入对象
	 * @param id
	 * @return
	 * @throws DAOException while instance is no exist.
	 */
	public LoanInfo loadById(Long id) throws DAOException;
	
	/**
	 * test
	 */
	public List<LoanInfo> query(SearchBean searchBean) throws DAOException;

}
