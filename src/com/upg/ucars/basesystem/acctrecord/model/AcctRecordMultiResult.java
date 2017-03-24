/*******************************************************************************
 * Leadmind Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Oct 29, 2008
 *******************************************************************************/

package com.upg.ucars.basesystem.acctrecord.model;

import java.util.ArrayList;
import java.util.List;

import com.upg.ucars.mapping.basesystem.acctrecord.AcctRecordInfo;
/**
 * 记帐分录返回结果
 *
 * @author shentuwy
 */
public class AcctRecordMultiResult extends AcctRecordResult{
	
	private List<AcctTranRecordAdapt> tranRecordList = new ArrayList<AcctTranRecordAdapt>();
	/**
	 * 
	 * @param resultAdapt
	 */
	public void addTranRecordAdapt(AcctTranRecordAdapt tranRecord){
		this.tranRecordList.add(tranRecord);
	}
	
	
	public List<AcctTranRecordAdapt> getAcctTranRecordList(){
		return tranRecordList;
	}


	@Override
	public List<AcctRecordInfo> getAcctRecordInfos() {
		ArrayList<AcctRecordInfo> list = new ArrayList<AcctRecordInfo>(tranRecordList.size());
		for (AcctTranRecordAdapt tranRecord : tranRecordList) {
			list.addAll(tranRecord.getAcctRecordInfos());
		}
		return list;
	}
	
}
