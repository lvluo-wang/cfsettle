/*******************************************************************************
 * Leadmind Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Nov 5, 2008
 *******************************************************************************/


package com.upg.ucars.basesystem.acctrecord.model;

import com.upg.ucars.mapping.basesystem.acctrecord.AcctRecordInfo;
/**
 * 
 * <br>
 * 
 * @author shentuwy
 */
public class AcctRowRecordAdapt {
	//private String rowNo;
	
	private AcctRecordInfo rowInfo;
	
	public String getRowNo() {		
		return rowInfo.getRowNo();
	}
	public AcctRecordInfo getRowInfo() {
		return rowInfo;
	}
	public void setRowInfo(AcctRecordInfo rowInfo) {
		this.rowInfo = rowInfo;
	}
	
	
	

}
