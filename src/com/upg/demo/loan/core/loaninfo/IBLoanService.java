package com.upg.demo.loan.core.loaninfo;

import java.util.List;

import com.upg.demo.mapping.loan.LoanInfo;
import com.upg.ucars.framework.base.IBusiBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ServiceException;
/**
 * 贷款服务接口
 * 
 * @author shentuwy
 */
public interface IBLoanService extends IBusiBaseService {
	/**
	 * 保存
	 * @param loanInfo
	 * @throws ServiceException 业务信息不合法时抛出
	 * @throws DAOException 持久性异常时抛出
	 */
	public void save(LoanInfo loanInfo)throws ServiceException, DAOException ;
	/**
	 * 修改
	 * @param loanInfo
	 * @throws ServiceException
	 */
	public void update(LoanInfo loanInfo)throws ServiceException;
	/**
	 * 根据ID获取实体对象
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public LoanInfo getById(Long id)throws DAOException;
	/**
	 * 根据ID删除实体对象
	 * @param id
	 * @throws DAOException
	 */
	public void deleteById(Long id)throws ServiceException;
	/**
	 * 复合查询
	 *
	 * @param qcpt 页面查询组件
	 * @param page 分页
	 * @return
	 * @throws DAOException 查询时产生异常
	 */
	public List<LoanInfo> queryLoanInfo(QueryComponent qcpt, Page page)throws DAOException;
	/**
	 * 划款
	 *
	 * @throws ServiceException 划款时产生异常
	 */
	public void payment(LoanInfo loanInfo) throws ServiceException;
	
	/**
	 * 测试
	 **/
	public List<LoanInfo> query(SearchBean searchBean) throws ServiceException;

}
