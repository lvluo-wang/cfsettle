package com.upg.demo.loan.core.loaninfo;

import java.util.HashMap;
import java.util.List;

import org.jbpm.graph.exe.ExecutionContext;

import com.upg.demo.constant.ProductConstant;
import com.upg.demo.mapping.loan.LoanInfo;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.bpm.base.BPQueryCondition;
import com.upg.ucars.framework.bpm.base.BpmConstants;
import com.upg.ucars.framework.bpm.base.ProcessBaseService;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.model.BooleanResult;
/**
 * 贷款流程服务
 * 
 * @author shentuwy
 */
public class LoanProcessServiceImpl extends ProcessBaseService implements
		IPLoanProcessService {
	
	private final static String Process_Name = "loan";
	private final static String Task_Accept = "accept";
	private final static String Task_Common_Audit = "commonAudit";
	private final static String Task_Advanced_Audit = "advancedAudit";
	private final static String Node_Wait_Payment_Result = "waitPaymentResult";
	
	private IBLoanService loanService;//spring注入
	

	public Long startLoanPrcocess(LoanInfo entity) {
		try {
			Long brchId = SessionTool.getUserLogonInfo().getBranchId();
			String procName = this.getProcessDefName(brchId, ProductConstant.DEMO_LOAN);			
			if (procName == null)
				return super.startProcessInstance(Process_Name, entity, null);	
			else
				return super.startProcessInstance(brchId, ProductConstant.DEMO_LOAN , entity, null); //根据产品与机构获取对应流程
			
			
		} catch (ServiceException e) {			
			throw e;
		} catch (Exception e) {	
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.DEMO_LOAN_START_ERROR, e);
		}
		
		return null;
	}

	public List<LoanInfo> queryAcceptTask(Long userId, QueryComponent queryComponent, Page page) {
		String hql = "from LoanInfo loanInfo";
		BPQueryCondition condition = new BPQueryCondition(hql);
		condition.setEntityAlias("loanInfo");
		condition.setProcessName(Process_Name);
		condition.setEntityPkName("");
		condition.read(queryComponent);
		
		List list = this.getBpmQueryDAO().queryTaskOfAssignAndEntityByConditon(condition, userId, Task_Accept, page);
		
		
		return list;
	}

	public void dealAcceptTask(Long taskId, BooleanResult br) {
		HashMap<String, Object> variableMap = new HashMap<String, Object>(2);
		variableMap.put("isAgree", br.isSuccess());
		if (!br.isSuccess())
			variableMap.put("remark", br.getInfo());
		try {
			super.dealTask(taskId, variableMap);
		} catch (ServiceException e) {			
			throw e;
		} catch (Exception e) {			
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.DEMO_LOAN_TASK_ERROR, e);
		}
		
		
	}

	public List<LoanInfo> queryCommonAuditTask(Long userId, QueryComponent queryComponent, Page page) {
		String hql = "from LoanInfo loanInfo";
		BPQueryCondition condition = new BPQueryCondition(hql);
		condition.setEntityAlias("loanInfo");
		condition.setProcessName(Process_Name);
		condition.setEntityPkName("");
		condition.read(queryComponent);
		long a1 = System.currentTimeMillis();
		List<LoanInfo> loanInfoList = this.getBpmQueryDAO().queryTaskAndEntityByConditon(condition, userId, Task_Common_Audit, page);
		long a2 = System.currentTimeMillis();
		System.err.println("********query time :"+(a2-a1));
		return loanInfoList;
	}

	public void dealCommonAuditTask(Long taskId, BooleanResult br) {
		HashMap<String, Object> variableMap = new HashMap<String, Object>(2);
		variableMap.put("isAgree", br.isSuccess());
		if (!br.isSuccess())
			variableMap.put("remark", br.getInfo());
		try {
			super.dealTask(taskId, variableMap);
		} catch (ServiceException e) {			
			throw e;
		} catch (Exception e) {			
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.DEMO_LOAN_TASK_ERROR, e);
		}
		
	}

	public List<LoanInfo> queryAdvancedAuditTask(Long userId, QueryComponent queryComponent, Page page) {
		String hql = "from LoanInfo loanInfo";
		BPQueryCondition condition = new BPQueryCondition(hql);
		condition.setEntityAlias("loanInfo");
		condition.setProcessName(Process_Name);
		condition.setEntityPkName("");
		condition.read(queryComponent);
		
		List<LoanInfo> loanInfoList = this.getBpmQueryDAO().queryTaskAndEntityByConditon(condition, userId, Task_Advanced_Audit, page);
		
		return loanInfoList;
	}

	public void dealAdvancedAuditTask(Long taskId, BooleanResult br) {
		HashMap<String, Object> variableMap = new HashMap<String, Object>(2);
		variableMap.put("isAgree", br.isSuccess());
		if (!br.isSuccess())
			variableMap.put("remark", br.getInfo());
		try {
			super.dealTask(taskId, variableMap);
		} catch (ServiceException e) {			
			throw e;
		} catch (Exception e) {			
			ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.DEMO_LOAN_TASK_ERROR, e);
		}
		
	}

	public List<LoanInfo> queryWaitPaymentResultBusiInfo(Long userId, QueryComponent queryComponent, Page page) {
		String hql = "from LoanInfo loanInfo";
		BPQueryCondition condition = new BPQueryCondition(hql);
		condition.setEntityAlias("loanInfo");
		condition.setProcessName(Process_Name);
		condition.setEntityPkName("");
		condition.read(queryComponent);
		
		List<LoanInfo> loanInfoList = this.getBpmQueryDAO().queryTokenAndEntityOnNode(condition, Node_Wait_Payment_Result, page);
		
		return loanInfoList;
	}
	
	public void payment(ExecutionContext context) throws ServiceException {
		
		LoanInfo loanInfo = (LoanInfo)context.getContextInstance().getVariable(BpmConstants.VAR_ENTITY);		
		loanService.payment(loanInfo);
		
	}	

	public void notifyPaymentResult(Long tokenId) {
		super.signalToken(tokenId, null);		
	}
	

	public IBLoanService getLoanService() {
		return loanService;
	}

	public void setLoanService(IBLoanService loanService) {
		this.loanService = loanService;
	}

	

	
}
