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

import com.upg.ucars.mapping.basesystem.acctrecord.AcctRecordInfo;

/**
 * 单个实体对应交易的分录信息
 * 
 * @author shentuwy
 */
public class AcctTranRecordAdapt {
	private String tranNo;//交易号

	private Long recordFlowId;// 分录流水ID ACCT_RECORD_FLOW.ACCT_FLOW_ID
	private String miNo;//接入编号
	private String prodNo;//产品编号
	
	private String eventNo;// 事件编号
	
	private Long entityId;//实体ID
	
	private ArrayList<AcctGroupRecordAdapt> groupList = new ArrayList<AcctGroupRecordAdapt>(5);

	/**
	 * 增加组
	 *
	 * @param groupRecord
	 */
	public void addGroupRecord(AcctGroupRecordAdapt groupRecord) {
		groupList.add(groupRecord);
	}

	/**
	 * 获取组信息列表
	 * 
	 * @return
	 */
	public List<AcctGroupRecordAdapt> getAcctGroupRecordList() {
	
		return groupList;
	}

	/**
	 * 获取指定组信息
	 * @param groupNo
	 * @return
	 */
	public AcctGroupRecordAdapt getAcctGroupRecordByNo(String groupNo){
		Iterator groupIt = groupList.iterator();
		while (groupIt.hasNext()){
			AcctGroupRecordAdapt groupRecord = (AcctGroupRecordAdapt)groupIt.next();
			if (groupRecord.getGroupNo().equals(groupNo)){
				return groupRecord;
			}
		}
		return null;
	}


	public String getTranNo() {
		return tranNo;
	}

	public void setTranNo(String tranNo) {
		this.tranNo = tranNo;
	}

	public String getEventNo() {
		return eventNo;
	}

	public void setEventNo(String eventNo) {
		this.eventNo = eventNo;
	}

	

	public Long getRecordFlowId() {
		return recordFlowId;
	}

	public void setRecordFlowId(Long recordFlowId) {
		this.recordFlowId = recordFlowId;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	/**
	 * @return Returns the prodNo.
	 */
	public String getProdNo() {
		return prodNo;
	}

	/**
	 * @param prodNo The prodNo to set.
	 */
	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}

	public String getMiNo() {
		return miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}
	
	
	public List<AcctRecordInfo> getAcctRecordInfos(){
		ArrayList<AcctRecordInfo> list = new ArrayList<AcctRecordInfo>(groupList.size());
		for (AcctGroupRecordAdapt groupRecord : groupList) {
			list.addAll(groupRecord.getAcctRecordInfos());
		}
		return list;
	}
	
	
}
