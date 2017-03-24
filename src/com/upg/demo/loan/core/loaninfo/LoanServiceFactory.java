package com.upg.demo.loan.core.loaninfo;

import com.upg.ucars.util.SourceTemplate;

/**
 * 贷款模块服务工厂
 * @author shentuwy
 */
public class LoanServiceFactory {
	/**
	 * 获取贷款业务服务实例
	 * @return
	 */
	public static IBLoanService getLoanService(){
		return (IBLoanService) SourceTemplate.getBean("loanService");
	}
	/**
	 * 获取借款流程服务实例
	 * @return
	 */
	public static IPLoanProcessService getLoanProcessService(){
		return (IPLoanProcessService) SourceTemplate.getBean("loanProcessService");
	}

}
