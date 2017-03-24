package com.upg.demo.loan.esb.adaptor;



import java.util.List;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import com.upg.demo.loan.core.loaninfo.IBLoanService;
import com.upg.demo.loan.core.loaninfo.LoanServiceFactory;
import com.upg.demo.loan.core.loaninfo.SearchBean;
import com.upg.demo.mapping.loan.LoanInfo;

/**
 * 外部服务通讯适配
 * @author hezw
 *
 */

public class TcpProxy implements Callable{
	public Object onCall(MuleEventContext ctx) throws Exception{
		//获取消息负载
//		Object obj = ctx.getMessageAsString();
		Object obj = ctx.getMessage().getPayload();
		SearchBean searchBean = (SearchBean)obj;
//		LoanInfo loanInfo = (LoanInfo)obj;
//		SearchBean searchBean = new SearchBean();
//		if(loanInfo!=null){
//			System.out.println(">>>>>贷款号:"+loanInfo.getLoanNo()+",用户名:"+loanInfo.getUserName()+",状态:"+loanInfo.getStatus());
//			searchBean.setNo(loanInfo.getLoanNo());
//			searchBean.setUserName(loanInfo.getUserName());
//			searchBean.setStatus(loanInfo.getStatus());
//		}
		
		IBLoanService loadService = LoanServiceFactory.getLoanService();
		//调用内部服务
//		loadService.payment(loanInfo);
		StringBuffer sb = new StringBuffer("response's result:");
//		sb.append("\r\n");
		List<LoanInfo> lis = loadService.query(searchBean);
		if(lis != null && !lis.isEmpty()){
			int i = 0;
			for(LoanInfo li : lis){
//				sb.append("\r\n");
				sb.append("LoanInfo["+i+"]::::"+li.toString());
				i = i + 1;
			}
		}
//		StringBuffer ret = new StringBuffer();
//		int len = sb.length();
//		int total = len + 20;
//		int len_len = (String.valueOf(len)).length();
//		for(int j=0;j<(20-len_len);j++){
//			ret.append("0");
//		}
//		ret.append(total).append(sb);
//		return ret.toString();
		
		try {
			StringBuffer ret = new StringBuffer();
			int len = sb.toString().getBytes("GBK").length;
//			int total = len + 20;
			int len_len = (String.valueOf(len)).length();
			for(int j=0;j<(20-len_len);j++){
				ret.append("0");
			}
			ret.append(len).append(sb);
			return ret.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
}
