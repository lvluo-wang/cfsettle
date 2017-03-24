package com.upg.demo.loan.esb.transformers;

import java.util.HashMap;
import java.util.Map;

import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;

import com.upg.demo.loan.core.loaninfo.SearchBean;

public class StringToSearchBeanTransformer extends AbstractTransformer {

	@Override
	protected Object doTransform(Object source, String encode)
			throws TransformerException {
		String strSource = new String((byte[])source);
		String[] args = null;
		Map<String,String> paramap = new HashMap<String,String>();
		if(strSource!=null && !"".equals(strSource)){
			args = strSource.split("&");
		}
		if(args != null && args.length > 0){
			int len = args.length;
			for(int i=0;i<len;i++){
				String[] param = args[i].split("=");
				if(param != null & param.length >1){
					paramap.put(param[0], param[1]);
				}
			}
		}

		SearchBean searchBean = new SearchBean();
		String no = paramap.get("no");
		String userName = paramap.get("userName");
		String status = paramap.get("status");
		String currentPage = paramap.get("currentPage");
		if(currentPage==null || "".equals(currentPage))
			currentPage = "1";
		int reqPage = 1;
		try {
			reqPage = Integer.parseInt(currentPage);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			reqPage = 1;
		}
		String pageSize = paramap.get("pageSize");
		if(pageSize==null || "".equals(pageSize))
			pageSize = "50";
		int pSize = 50;
		try {
			pSize = Integer.parseInt(pageSize);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pSize = 50;
		}
		searchBean.setNo(no);
		searchBean.setUserName(userName);
		searchBean.setStatus(status);
		searchBean.setCurrentPage(reqPage);
		searchBean.setPageSize(pSize);
//		LoanInfo li = new LoanInfo();
//		if(args != null & args.length !=0){
//			System.out.println("贷款号:"+args[0]);
//			li.setLoanNo(args[0]);
//			//用户名
//			if(args.length >= 2){
//				System.out.println("用户名:"+args[1]);
//				li.setUserName(args[1]);
//			}
//			if(args.length >= 3){
//				System.out.println("状态:"+args[2]);
//				li.setStatus(args[2]);
//			}
//		}
		return searchBean;
	}

}
