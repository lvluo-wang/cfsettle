/*******************************************************************************
 * Leadmind Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Oct 29, 2008
 *******************************************************************************/

package com.upg.ucars.basesystem.acctrecord.model;

import java.util.List;

import com.upg.ucars.mapping.basesystem.acctrecord.AcctRecordInfo;

/**
 * 记帐分录返回结果
 *
 * @author shentuwy
 */
public abstract class AcctRecordResult {

	private Long acctFlowId;// 记帐流水ID
	
	public Long getAcctFlowId() {
		return acctFlowId;
	}

	public void setAcctFlowId(Long acctFlowId) {
		this.acctFlowId = acctFlowId;
	}
	/**
	 * 结构化交易分录 
	 *
	 * @return
	 */
	public abstract List<AcctTranRecordAdapt> getAcctTranRecordList();
	/**
	 * 分录信息
	 *
	 * @return
	 */
	public abstract List<AcctRecordInfo> getAcctRecordInfos();
		
}
