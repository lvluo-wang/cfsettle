/*******************************************************************************
 * Leadmind Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Nov 4, 2008
 *******************************************************************************/


package com.upg.ucars.basesystem.acctrecord.analyse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.beanutils.MethodUtils;

import bsh.Interpreter;

import com.upg.ucars.basesystem.acctrecord.model.AcctGroupRecordAdapt;
import com.upg.ucars.basesystem.acctrecord.model.AcctRowRecordAdapt;
import com.upg.ucars.basesystem.acctrecord.model.AcctTranRecordAdapt;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctRecordInfo;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctTran;
import com.upg.ucars.mapping.basesystem.acctrecord.TranItem;

/**
 * 解析分录信息
 *
 * @author shentuwy
 */
public class AcctTran2RecordAnalyser {
	
	private AcctTran tran; 
	
	public AcctTran2RecordAnalyser(AcctTran tran){
		this.tran = tran;
	}
	
	
	private List<AcctRecordInfo> parseContext(Interpreter itpr) throws Exception{		
		List<TranItem>  itemList = AcctAnalyseFactory.getAcctTranDAO().getAcctTranRecords(tran);
		
		ArrayList<AcctRecordInfo> rowObjList = new ArrayList<AcctRecordInfo>();
		
		for (int i = 0; i < itemList.size(); i++) {
			TranItem item = itemList.get(i);	
			String value ="";
			try {
				value = this.getExpressValue(item.getValue(), itpr);
				AcctRecordInfo rowObj = this.getOrAddRowObject(rowObjList, item);
				MethodUtils.invokeMethod(rowObj, "setItem"+item.getSerialNo(), value);//按交易项的序号设置在相应的字段上
				
			} catch (Exception e) {
				//e.printStackTrace();
			}		
			
		}		
		
		return rowObjList;
	}	
	/*
	 * 按组及行的序号从集合中得到相应的分录对象，如果没有则创建一个分录对象并添加到集合中。
	 */
	private AcctRecordInfo getOrAddRowObject(ArrayList<AcctRecordInfo> rowObjList, TranItem item){
		AcctRecordInfo rowObj = null;
		for (AcctRecordInfo acctRecordInfo : rowObjList) {
			if (item.getGroupNo().equals(acctRecordInfo.getGroupNo()) && (""+item.getRowNo()).equals(acctRecordInfo.getRowNo())){
				rowObj = acctRecordInfo;
				break;
			}			
			
		}
		if (rowObj == null){
			rowObj = new AcctRecordInfo();
			rowObj.setGroupNo(item.getGroupNo());
			rowObj.setRowNo(""+item.getRowNo());	
			rowObjList.add(rowObj);
		}
		return rowObj;
	}
	
	
	
	/**
	 * 解析表达式
	 * @param express
	 * @param itpr
	 * @return
	 * @throws Exception
	 */
	private String getExpressValue(String express, Interpreter itpr) throws Exception{
		String compareExpress =	AnalyseUtil.getBSHScript(express);
		Object o = itpr.eval(compareExpress);
		if (o == null){
			//throw new ServiceException("BC_ACCT_RECORD_ERR0001", null , new String[]{"express"});
		}
		return o.toString();
	}
		
	/**
	 * 解析出分录信息
	 * @param eventColler
	 * @return
	 * @throws Exception
	 */
	public AcctTranRecordAdapt buildRecord(Interpreter itpr) throws Exception{
		List<AcctRecordInfo> infoList = parseContext(itpr);
		TreeMap groupMap = new TreeMap();
		//
		for (int i = 0; i < infoList.size(); i++) {
			AcctRecordInfo rowInfo = infoList.get(i);
			{//
				AcctGroupRecordAdapt groupRecordAda = (AcctGroupRecordAdapt)groupMap.get(rowInfo.getGroupNo());
				if (groupRecordAda == null){
					groupRecordAda = new AcctGroupRecordAdapt();
					groupRecordAda.setGroupNo(rowInfo.getGroupNo());
					groupMap.put(rowInfo.getGroupNo(), groupRecordAda);//
				}
				
				{//
					AcctRowRecordAdapt rowRecordAda = groupRecordAda.getRowRecordByRowNo(rowInfo.getRowNo());
					if (rowRecordAda == null){
						rowRecordAda = new AcctRowRecordAdapt();
						rowRecordAda.setRowInfo(rowInfo);
						groupRecordAda.addRowRecord(rowRecordAda);
					}
					
				}
			}
			
		}
		
		AcctTranRecordAdapt tranRecordAda = new AcctTranRecordAdapt();
		//
		tranRecordAda.setTranNo(tran.getTranNo());
		//
		Iterator it = groupMap.values().iterator();
		while(it.hasNext()){
			AcctGroupRecordAdapt groupRecord = (AcctGroupRecordAdapt)it.next();
			tranRecordAda.addGroupRecord(groupRecord);
		}
		
		return tranRecordAda;
		
	}
	
		
}
