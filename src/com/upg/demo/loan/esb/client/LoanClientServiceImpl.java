package com.upg.demo.loan.esb.client;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upg.demo.loan.core.loaninfo.IBLoanService;
import com.upg.demo.loan.core.loaninfo.SearchBean;
import com.upg.demo.mapping.loan.LoanInfo;
import com.upg.ucars.framework.base.ClientResultInfo;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;

public class LoanClientServiceImpl implements ILoanClientService {

	//贷款服务对象
	private IBLoanService loadService; 
	/**
	 * 构造方法 - 初始化贷款服务对象
	 */
//	public LoanClientServiceImpl() {
//		//初始化
//		loadService = (IBLoanService)SourceTemplate.getBean("loanService");
//	}
	
	public ClientResultInfo<LoanInfo> getById(Long id) {
		ClientResultInfo<LoanInfo> result = new ClientResultInfo<LoanInfo>();
		try{
			//业务实现服务查询调用,并添加查询结果到返回对象集
			LoanInfo info = loadService.getById(id);
			Page p = new Page();
			List<LoanInfo> resultObj = new ArrayList<LoanInfo>();
			resultObj.add(info);
			result.setListData(resultObj);
			result.setData(info);
			Map map = new HashMap();
			map.put("info", info);
			map.put("info2", "hello");
			map.put("info3", p);
			ClientResultInfo<LoanInfo> result2 = new ClientResultInfo<LoanInfo>();
			result2.setExceptionCode("TTT");
			result2.setListData(resultObj);
			map.put("info4", result2);
			//map.put("info2", resultObj);
			result.setMapData(map);
			result.setPage(p); 
		} catch (Exception e) {
			//出现异常,设置异常码
			result.setExceptionCode("LOAD_001");
			//设置异常提示信息
			result.setExceptionInfo(ExceptionInfoConst.ERR_DATA_QUERY);
		}
		return result;
	}

	/**
	 * 注:无返回结果可以没有范型定义
	 */
	public ClientResultInfo<LoanInfo> payment(LoanInfo loanInfo) {
		ClientResultInfo<LoanInfo> result = new ClientResultInfo<LoanInfo>();
		try{
			//业务实现服务查询调用,并添加查询结果到返回对象集
			LoanInfo li = loadService.getById(loanInfo.getId());
			result.setData(li);
		} catch (Exception e) {
			//出现异常,设置异常码
			result.setExceptionCode("LOAD_001");
			//设置异常提示信息
			result.setExceptionInfo(ExceptionInfoConst.ERR_DATA_QUERY);
		}
		return result;
	}

	public ClientResultInfo<LoanInfo> queryLoanInfo(QueryComponent qcpt, Page page) {
		ClientResultInfo<LoanInfo> result = new ClientResultInfo<LoanInfo>();
		try {
			//调用业务服务查询,并把结果设置到返回对象的对象集中
			//result.setObjs(loadService.queryLoanInfo(qcpt, page));
			//设置页面信息
			result.setPage(page);
		} catch (Exception e) {
			//出现异常,设置异常码
			result.setExceptionCode("LOAD_003");
			//设置异常提示信息
			result.setExceptionInfo(ExceptionInfoConst.ERR_DATA_QUERY);
		}
		return result;
	}
	public TestObj sayHi(String name) {
		String words = " hello " + name;
		System.out.println(words);
		TestObj obj = new TestObj();
		obj.setName(name);
		obj.setWords(words);
		return obj;
	}
	
	public List<LoanInfo> query(SearchBean searchBean){
		List<LoanInfo> lis = loadService.query(searchBean);
		return lis;
	}

	public IBLoanService getLoadService() {
		return loadService;
	}

	public void setLoadService(IBLoanService loadService) {
		this.loadService = loadService;
	}

}
