package com.upg.demo.loan.esb.transformers;

import org.mule.api.transformer.TransformerException;
import org.mule.transformer.AbstractTransformer;

import com.upg.demo.mapping.loan.LoanInfo;
/**
 * 数据转换类 
 * @author hezw
 *
 */
public class LoanInfoToStringTransfomer extends AbstractTransformer {

	@Override
	protected Object doTransform(Object source, String arg1) throws TransformerException {
		//源数据
//		String strSource = (String)source;
		String strSource = new String((byte[])source);
		String[] args = null;
		if(strSource!=null && !"".equals(strSource)){
			args = strSource.split("&");
		}
		LoanInfo li = new LoanInfo();
		if(args != null && args.length !=0){
			System.out.println("贷款号:"+args[0]);
			li.setLoanNo(args[0]);
			//用户名
			if(args.length >= 2){
				System.out.println("用户名:"+args[1]);
				li.setUserName(args[1]);
			}
			if(args.length >= 3){
				System.out.println("状态:"+args[2]);
				li.setStatus(args[2]);
			}
		}

		//贷款号
//		System.out.println("贷款号:"+strSource.substring(0,12));
//		li.setLoanNo(strSource.substring(0,12));
//		//用户名
//		System.out.println("用户名:"+strSource.substring(20,30));
//		li.setUserName(strSource.substring(20,30));
		
		
		return li;
	}

}
