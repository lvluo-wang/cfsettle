/*******************************************************************************
 * Leadmind Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Nov 4, 2008
 *******************************************************************************/


package com.upg.ucars.basesystem.acctrecord.analyse;

import java.util.List;

import bsh.Interpreter;

import com.upg.ucars.basesystem.acctrecord.runtime.AcctInfoCollector;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctTran;
import com.upg.ucars.util.StringUtil;
/**
 * 分析出交易
 * <li> 根据接入点及上下文分析出交易
 * <li>
 *
 * @author shentuwy
 */
public class AcctPoint2TranAnalyser {	
	/**
	 * 查找记帐点下的交易
	 *
	 * @param prodNo
	 * @param eventNo
	 * @return
	 */
	private List<AcctTran> getAcctTrans(String miNo, String prodNo, String eventNo){
		AcctPointAnaDAO dao = AcctAnalyseFactory.getAcctPointDAO();
		List<AcctTran>  list = dao.getAcctTrans(miNo, prodNo, eventNo);
		return list;
	}
	
	/**
	 * 记帐交易
	 * @param pointColler
	 * @return
	 * @throws Exception
	 */
	public AcctTran getAcctTran(String miNo, String prodNo, String eventNo, AcctInfoCollector infoColler) throws Exception{
		Interpreter itpr = infoColler.getContextInterpreter();
		
		List<AcctTran> tranList = getAcctTrans(miNo, prodNo, eventNo);
		for (AcctTran acctTran : tranList) {
			boolean isTrue = this.isFitTranExpr(acctTran.getExpress(), itpr);
			if (isTrue)
				return acctTran;
		}				
		return null;
	}
	/**
	 * boolean型表达式结果
	 *
	 * @param express 表达式，如果为null或空串 则返回true
	 * @param itpr
	 * @return
	 * @throws Exception
	 */
	private boolean isFitTranExpr(String express, Interpreter itpr) throws Exception{	
		if (express==null || StringUtil.isEmpty(express.trim()))
			return true;
		
		if (StringUtil.isEmpty(express))
			return true;
		
		String compareExpress =	AnalyseUtil.getBSHBooleanScript(express);
		Object rs = itpr.eval(compareExpress);
		if(Boolean.TRUE.equals(rs)){
			return true;
		}else{
			return false;
		}		
	}	
	
	
	

}
