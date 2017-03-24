/*******************************************************************************
 * Leadmind Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Nov 5, 2008
 *******************************************************************************/


package com.upg.ucars.basesystem.acctrecord.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.upg.ucars.mapping.basesystem.acctrecord.AcctRecordBody;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctRecordInfo;
/**
 * 
 * <br>
 * 
 * @author shentuwy
 */
public class AcctGroupRecordAdapt {
	private String groupNo;
	private List<AcctRecordBody> groupBodyList; 
	
	
	private List<AcctRowRecordAdapt> rowList = new ArrayList<AcctRowRecordAdapt>();//
	/**
	 *
	 * @param item
	 */
	public void addRowRecord(AcctRowRecordAdapt rowRecord){
		rowList.add(rowRecord);
	}
	
	/**
	 * 获取行信息
	 * @return
	 */
	public List<AcctRowRecordAdapt> getRowRecordList(){		
		return rowList;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}	
	/**
	 *
	 * @param rowNo 
	 * @return
	 */
	public AcctRowRecordAdapt getRowRecordByRowNo(String rowNo){
		Iterator it = rowList.iterator();
		while (it.hasNext()){
			AcctRowRecordAdapt rowRecord = (AcctRowRecordAdapt)it.next();
			if (rowNo.equals(rowRecord.getRowNo())){
				return rowRecord;
			}
		}
		return null;
	}
	
	public List<AcctRecordInfo> getAcctRecordInfos(){
		
		ArrayList<AcctRecordInfo> list = new ArrayList<AcctRecordInfo>(rowList.size());
		for (AcctRowRecordAdapt rowRecord : rowList) {
			list.add(rowRecord.getRowInfo());
		}
		return list;
	}

	public List<AcctRecordBody> getGroupBodyList() {
		return groupBodyList;
	}

	public void setGroupBodyList(List<AcctRecordBody> groupBodyList) {
		this.groupBodyList = groupBodyList;
	}

	
}