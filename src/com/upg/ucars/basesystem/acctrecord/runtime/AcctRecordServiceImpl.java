
package com.upg.ucars.basesystem.acctrecord.runtime;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import bsh.Interpreter;

import com.upg.ucars.basesystem.acctrecord.analyse.AcctPoint2TranAnalyser;
import com.upg.ucars.basesystem.acctrecord.analyse.AcctTran2RecordAnalyser;
import com.upg.ucars.basesystem.acctrecord.config.acctrecordbody.IAcctRecordBodyService;
import com.upg.ucars.basesystem.acctrecord.model.AcctGroupRecordAdapt;
import com.upg.ucars.basesystem.acctrecord.model.AcctRecordMultiResult;
import com.upg.ucars.basesystem.acctrecord.model.AcctRecordResult;
import com.upg.ucars.basesystem.acctrecord.model.AcctRowRecordAdapt;
import com.upg.ucars.basesystem.acctrecord.model.AcctTranRecordAdapt;
import com.upg.ucars.constant.AcctRecordConst;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctRecordBody;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctRecordFlow;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctRecordInfo;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctTran;
import com.upg.ucars.model.ConditionBean;
/**
 * 分录服务实现类
 *
 * @author shentuwy
 */
public class AcctRecordServiceImpl implements IAcctRecordService {
	
	private IAcctRecordBodyService acctRecordBodyService = null;
	
	private AcctRecordInfoServiceImpl acctRecordInfoService = null;

	public AcctRecordResult buildAcctRecord(Long acctFlowId, String miNo, String prodNo, String eventNo, AcctInfoCollector infoColler, boolean isSave) throws ServiceException {
		if (infoColler == null)
			return null;
		
		List<AcctInfoCollector> acctCollerList = new ArrayList<AcctInfoCollector>(1);
		acctCollerList.add(infoColler);
		
		return buildAcctRecord(acctFlowId, miNo, prodNo, eventNo, acctCollerList, isSave);
		
	}
	
	public AcctRecordResult buildAcctRecord(Long acctFlowId, String miNo, String prodNo, String eventNo, List<AcctInfoCollector> infoCollerList, boolean isSave) throws ServiceException {
		if (infoCollerList==null || infoCollerList.isEmpty())
			return null;
		
		AcctRecordMultiResult result =new AcctRecordMultiResult();
		result.setAcctFlowId(acctFlowId);
		
		for (int i = 0; i < infoCollerList.size(); i++) {
			AcctInfoCollector infoColler = infoCollerList.get(i);
			//配置数据项
			AcctTranRecordAdapt tranRecord = this.parseAcctPoint(miNo, prodNo, eventNo, infoColler);
			tranRecord.setMiNo(miNo);
			tranRecord.setProdNo(prodNo);
			tranRecord.setEventNo(eventNo);
			tranRecord.setEntityId(infoColler.getEntityId());
			if (isSave){
				
				this.saveAcctRecordInfos(tranRecord, acctFlowId);
			}			
			
			result.addTranRecordAdapt(tranRecord);			
		}			
		
		return result;
	}
	/**
	 * 保存分录信息
	 * @param recordResults
	 * @return
	 * @throws ServiceException
	 */
	public void saveAcctRecords(AcctTranRecordAdapt[] tranRecords, Long acctFlowId) throws ServiceException {
		
		for (int i = 0; i < tranRecords.length; i++) {
			AcctTranRecordAdapt tranRecord = tranRecords[i];			
			
			this.saveAcctRecordInfos(tranRecord, acctFlowId);				
		}			
		
	}
	
	

	public AcctRecordResult getAcctRecord(Long acctFlowId) {
		
		List flowList = acctRecordInfoService.queryAcctRecordFlowByAcctFlowId(acctFlowId);
		AcctRecordMultiResult result = new AcctRecordMultiResult();
		result.setAcctFlowId(acctFlowId);
		for (int k = 0; k < flowList.size(); k++) {
			AcctRecordFlow flow = (AcctRecordFlow)flowList.get(k);
			AcctTranRecordAdapt tranRecord = this.getAcctTranRecordAdaptByRecordFlow(flow);
			result.addTranRecordAdapt(tranRecord);
		}		
		
		

		return result;		
	}
	/**
	 * 
	 * @param service
	 * @param flow
	 * @return
	 */
	public AcctTranRecordAdapt getAcctTranRecordAdaptByRecordFlow(AcctRecordFlow flow) {
	
			Long flowId = flow.getId();	
			
			List infoList = acctRecordInfoService.queryAcctRecordInfoByTranFlowId(flowId);
			
			Map groupMap = new TreeMap();
			//
			for (int i = 0; i < infoList.size(); i++) {
				AcctRecordInfo info = (AcctRecordInfo) infoList.get(i);
				{//	
					AcctGroupRecordAdapt groupRecordAda = (AcctGroupRecordAdapt)groupMap.get(info.getGroupNo());
					if (groupRecordAda == null){
						groupRecordAda = new AcctGroupRecordAdapt();
						groupRecordAda.setGroupNo(info.getGroupNo());
						groupMap.put(info.getGroupNo(), groupRecordAda);//
					}
					
					{//
						AcctRowRecordAdapt rowRecordAda = groupRecordAda.getRowRecordByRowNo(info.getRowNo());
						if (rowRecordAda == null){
							rowRecordAda = new AcctRowRecordAdapt();
							rowRecordAda.setRowInfo(info);
							groupRecordAda.addRowRecord(rowRecordAda);
						}
					}
				}
				
			}

			
			AcctTranRecordAdapt tranRecord = new AcctTranRecordAdapt();
			tranRecord.setRecordFlowId(flow.getId());
			tranRecord.setProdNo(flow.getProdNo());//
			tranRecord.setEntityId(flow.getEntityId());//
			tranRecord.setMiNo(flow.getMiNo());
			Iterator it = groupMap.values().iterator();
			while (it.hasNext()) {
				AcctGroupRecordAdapt groupRecord = (AcctGroupRecordAdapt) it.next();
				tranRecord.addGroupRecord(groupRecord);
			}		

		return tranRecord;
		
		
	}
	
	/**
	 * 
	 * @param eventColler
	 * @return
	 * @throws ServiceException
	 */
	private AcctTranRecordAdapt parseAcctPoint(String miNo, String prodNo, String eventNo, AcctInfoCollector infoColler) throws ServiceException {
		
		AcctPoint2TranAnalyser analyser = new AcctPoint2TranAnalyser();
		AcctTran tran = null;
		try {
			tran = analyser.getAcctTran(miNo, prodNo, eventNo, infoColler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (tran == null) {
			return tempTestData();//TODO 
			//throw new ServiceException("");
		}
		//
		AcctTran2RecordAnalyser tranAlser = new AcctTran2RecordAnalyser(tran);
		AcctTranRecordAdapt resultAdapt=null;		
		try {
			//
			Interpreter itpr = infoColler.getContextInterpreter();							
			resultAdapt = tranAlser.buildRecord(itpr);

		} catch (Exception e) {
			//TODO throw new ServiceException(e.getMessage());
		}
		return resultAdapt;
	}
	
	//TODO 待删除
	private AcctTranRecordAdapt tempTestData(){
		AcctTranRecordAdapt record = new AcctTranRecordAdapt();
		record.setTranNo("testTran");
		
		{
			AcctGroupRecordAdapt group = new AcctGroupRecordAdapt();
			group.setGroupNo("1");
			
			{
				AcctRowRecordAdapt row= new AcctRowRecordAdapt();
				AcctRecordInfo rowInfo = new AcctRecordInfo();
				rowInfo.setFlowId(1L);
				rowInfo.setGroupNo("1");
				rowInfo.setRowNo("1");
				rowInfo.setItem1("J");
				rowInfo.setItem2("001001");
				rowInfo.setItem3("300.00");
				
				row.setRowInfo(rowInfo);
				
				group.addRowRecord(row);
			}
			{
				AcctRowRecordAdapt row= new AcctRowRecordAdapt();
				AcctRecordInfo rowInfo = new AcctRecordInfo();
				rowInfo.setFlowId(1L);
				rowInfo.setGroupNo("1");
				rowInfo.setRowNo("2");
				rowInfo.setItem1("D");
				rowInfo.setItem2("001002");
				rowInfo.setItem3("200.00");
				
				row.setRowInfo(rowInfo);
				
				group.addRowRecord(row);
			}
			{
				AcctRowRecordAdapt row= new AcctRowRecordAdapt();
				AcctRecordInfo rowInfo = new AcctRecordInfo();
				rowInfo.setFlowId(1L);
				rowInfo.setGroupNo("1");
				rowInfo.setRowNo("3");
				rowInfo.setItem1("D");
				rowInfo.setItem2("001003");
				rowInfo.setItem3("100.00");
				
				row.setRowInfo(rowInfo);
				
				group.addRowRecord(row);
			}
			record.addGroupRecord(group);
		}		
		
		return record;
	}
	/**
	 * 保存分录信息 
	 *
	 * @param tranRecord
	 * @param acctFlowId
	 */
	private void saveAcctRecordInfos(AcctTranRecordAdapt tranRecord, Long recordFlowId) {
		List<AcctGroupRecordAdapt> groupRecordList = tranRecord.getAcctGroupRecordList();
		// 
		AcctRecordFlow flow = new AcctRecordFlow();
		flow.setAcctFlowId(recordFlowId);
		flow.setProdNo(tranRecord.getProdNo());
		flow.setEntityId(tranRecord.getEntityId());
		flow.setMiNo(tranRecord.getMiNo());
		
		Long flowId = acctRecordInfoService.addAcctRecordFlow(flow);
		tranRecord.setRecordFlowId(recordFlowId);
		
		//
		for (int i = 0; i < groupRecordList.size(); i++) {
			AcctGroupRecordAdapt groupRecord = groupRecordList.get(i);			
			List<AcctRowRecordAdapt> rowRecords = groupRecord.getRowRecordList();
			for (int k = 0; k < rowRecords.size(); k++) {
				AcctRowRecordAdapt rowRecord = rowRecords.get(k);
				rowRecord.getRowInfo().setFlowId(flowId);
				acctRecordInfoService.addAcctRecordInfo(rowRecord.getRowInfo());
			}
			
		}
		
		return ;
	}

	public AcctRecordResult buildNegativeAcctRecord(Long preAcctFlowId, Long acctFlowId, boolean isSave) throws ServiceException {
		
		
		AcctRecordResult result = this.getAcctRecord(preAcctFlowId);
		List<AcctTranRecordAdapt> tranRecordList = result.getAcctTranRecordList();		
		
		if (tranRecordList==null)
			return result;
		
		String miNo = tranRecordList.get(0).getMiNo();
		
		ArrayList<ConditionBean> cblist = new ArrayList<ConditionBean>();
		cblist.add(new ConditionBean("miNo", miNo));
		cblist.add(new ConditionBean("valueType", AcctRecordConst.RECORD_TYPE_MONEY));
		List<AcctRecordBody> moneyBodyList = acctRecordBodyService.queryAcctRecordBody(cblist, null);
		
		result.getAcctTranRecordList();
		
		for (AcctTranRecordAdapt acctTranRecordAdapt : tranRecordList) {
			
			 for (AcctGroupRecordAdapt acctGroupRecordAdapt : acctTranRecordAdapt.getAcctGroupRecordList()) {
				 
				 for (AcctRecordBody acctRecordBody : moneyBodyList) {
					 for (AcctRowRecordAdapt acctRowRecordAdapt : acctGroupRecordAdapt.getRowRecordList()) {
						 
						 AcctRecordInfo newInfo = acctRowRecordAdapt.getRowInfo().clone();
						 acctRowRecordAdapt.setRowInfo(newInfo);
						 String val = newInfo.getItemValue(acctRecordBody.getSerialNo());
							if (val!=null)
								val="-" + val;//为负数
							newInfo.setItemValue(acctRecordBody.getSerialNo(), val);
					 }
						
				}
				 
			}
			//持久化
			if (isSave)
				this.saveAcctRecordInfos(acctTranRecordAdapt, acctFlowId);
		}
				
		return result;
		
	}

	public AcctRecordResult buildReverseAcctRecord(Long preAcctFlowId, Long acctFlowId, boolean isSave) throws ServiceException {
		
		AcctRecordResult result = this.getAcctRecord(preAcctFlowId);
		List<AcctTranRecordAdapt> tranRecordList = result.getAcctTranRecordList();		
		
		if (tranRecordList==null)
			return result;
		
		String miNo = tranRecordList.get(0).getMiNo();
		
		ArrayList<ConditionBean> cblist = new ArrayList<ConditionBean>();
		cblist.add(new ConditionBean("miNo", miNo));
		cblist.add(new ConditionBean("valueType", AcctRecordConst.RECORD_TYPE_MONEY));
		List<AcctRecordBody> moneyBodyList = acctRecordBodyService.queryAcctRecordBody(cblist, null);
		
		result.getAcctTranRecordList();
		
		for (AcctTranRecordAdapt acctTranRecordAdapt : tranRecordList) {
			
			 for (AcctGroupRecordAdapt acctGroupRecordAdapt : acctTranRecordAdapt.getAcctGroupRecordList()) {
				 
				 for (AcctRecordBody acctRecordBody : moneyBodyList) {
					 for (AcctRowRecordAdapt acctRowRecordAdapt : acctGroupRecordAdapt.getRowRecordList()) {
						 
						 AcctRecordInfo newInfo = acctRowRecordAdapt.getRowInfo().clone();
						 acctRowRecordAdapt.setRowInfo(newInfo);
						 String val = newInfo.getItemValue(acctRecordBody.getSerialNo());
							if (val!=null)
								val="-" + val;//为负数
							newInfo.setItemValue(acctRecordBody.getSerialNo(), val);
					 }
						
				}
				 
			}
			//持久化
			if (isSave)
				this.saveAcctRecordInfos(acctTranRecordAdapt, acctFlowId);
		}
				
		return result;

	}

	public void deleteAcctRecord(Long acctFlowId) throws ServiceException {
		acctRecordInfoService.deleteRecordInfoByFlow(acctFlowId);
	}

	public AcctRecordInfoServiceImpl getAcctRecordInfoService() {
		return acctRecordInfoService;
	}

	public void setAcctRecordInfoService(
			AcctRecordInfoServiceImpl acctRecordInfoService) {
		this.acctRecordInfoService = acctRecordInfoService;
	}

	public IAcctRecordBodyService getAcctRecordBodyService() {
		return acctRecordBodyService;
	}

	public void setAcctRecordBodyService(
			IAcctRecordBodyService acctRecordBodyService) {
		this.acctRecordBodyService = acctRecordBodyService;
	}
}
