package com.upg.demo.loan.esb.client;


import java.util.List;

import javax.jws.WebService;

import com.upg.demo.loan.core.loaninfo.SearchBean;
import com.upg.demo.mapping.loan.LoanInfo;

/**
 * 贷款服务接口
 * @author hezw
 *
 */
@WebService
public interface ILoanClientService {
	/**
	 * 按id获取贷款信息(LoadInfo)
	 * @param id	主键id
	 * @return	ClientResultInfo WS-API标准返回结果
	 */
	//public ClientResultInfo<LoanInfo> getById(Long id);
	/**
	 * 根据查询组件实现分页查询
	 * @param qcpt	查询组件对象
	 * @param page	分页信息
	 * @return	ClientResultInfo WS-API标准返回结果
	 */
	//public ClientResultInfo<LoanInfo> queryLoanInfo(QueryComponent qcpt, Page page);
	/**
	 * 划款
	 * @param loanInfo	贷款信息
	 * @return	ClientResultInfo WS-API标准返回结果
	 */
	//public ClientResultInfo<LoanInfo> payment(LoanInfo loanInfo);
	
	//public TestObj sayHi(String name);
	
	public List<LoanInfo> query(SearchBean searchBean);

}
