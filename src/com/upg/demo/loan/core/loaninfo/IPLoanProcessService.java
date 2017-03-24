package com.upg.demo.loan.core.loaninfo;

import java.util.List;

import org.jbpm.graph.exe.ExecutionContext;

import com.upg.demo.mapping.loan.LoanInfo;
import com.upg.ucars.framework.base.IProcessBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ProcessException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.model.BooleanResult;

/**
 * 贷款流程服务
 *
 * @author shentuwy
 */
public interface IPLoanProcessService extends IProcessBaseService{
	/**
	 * 启动流程
	 * @param entity
	 * @return 流程实例ID
	 */
	public Long startLoanPrcocess(LoanInfo entity) throws ProcessException;	
	/**
	 * 查询受理任务
	 * @param userNo 用户编号
	 * @param queryComponent 查询组件
	 * @param page 分页
	 * @return List< LoanInfo >
	 * @throws DAOException 查询时产生异常
	 */
	public List queryAcceptTask(Long userId, QueryComponent queryComponent, Page page)throws DAOException;
	/**
	 * 受理业务  
	 *
	 * @param taskId 任务实例ID
	 * @param br 是否受理及其意见
	 * @throws ProcessException
	 */
	public void dealAcceptTask(Long taskId, BooleanResult br) throws ProcessException;
	/**
	 * 查询审核任务 
	 *
	 * @param userNo 用户编号
	 * @param queryComponent 查询组件
	 * @param page 分页
	 * @return List< LoanInfo >
	 * @throws DAOException 查询时产生异常
	 */
	public List queryCommonAuditTask(Long userId, QueryComponent queryComponent, Page page)throws DAOException;
	/**
	 * 审核
	 * @param taskId 任务实例ID
	 * @param br 审核是否通过及审核意见
	 * @throws ProcessException
	 */
	public void dealCommonAuditTask(Long taskId, BooleanResult br) throws ProcessException;
	/**
	 * 查询高级审核任务
	 *
	 * @param userNo 用户编号
	 * @param queryComponent 查询组件
	 * @param page 分页
	 * @return List< LoanInfo >
	 * @throws DAOException 查询时产生异常
	 */
	public List queryAdvancedAuditTask(Long userId, QueryComponent queryComponent, Page page)throws DAOException;
	/**
	 * 高级审核
	 * @param taskId 任务实例ID
	 * @param br 审核是否通过及审核意见
	 */
	public void dealAdvancedAuditTask(Long taskId, BooleanResult br) throws ProcessException;	
	/**
	 * 查询等待划款结果的业务信息
	 *
	 * @param userNo 用户编号
	 * @param queryComponent 查询组件
	 * @param page 分页
	 * @return List< LoanInfo >
	 * @throws DAOException 查询时产生异常
	 */
	public List queryWaitPaymentResultBusiInfo(Long userId, QueryComponent queryComponent, Page page)throws DAOException;
	/**
	 * 响应划款结果
	 * @param tokenId 令牌ID
	 */
	public void notifyPaymentResult(Long tokenId) throws ProcessException;
	/**
	 * 自动付款节点功能
	 *
	 * @param context 工作流上下文
	 * @throws ServiceException
	 */
	public void payment(ExecutionContext context) throws ServiceException;
	

}
