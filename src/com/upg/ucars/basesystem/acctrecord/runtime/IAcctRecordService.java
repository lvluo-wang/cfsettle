package com.upg.ucars.basesystem.acctrecord.runtime;

import java.util.List;

import com.upg.ucars.basesystem.acctrecord.model.AcctRecordResult;
import com.upg.ucars.basesystem.acctrecord.model.AcctTranRecordAdapt;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctRecordFlow;
/**
 * 会计分录服务接口
 *
 * @author shentuwy
 */
public interface IAcctRecordService {
	/**
	 * 生成会计分录信息
	 * @param acctFlowId 
	 * @param acctPointCollector  信息收集器
	 * @param isSave 是否保存分录信息
	 * @return 
	 */
	public AcctRecordResult buildAcctRecord(Long acctFlowId, String miNo, String prodNo, String eventNo, AcctInfoCollector acctInfoCollector, boolean isSave) throws ServiceException;
	/**
	 * 生成批量会计分录信息
	 * @param acctFlowId 
	 * @param acctPointCollectorList 信息收集器列表
	 * @param isSave 是否保存分录信息
	 * @return 
	 */
	public AcctRecordResult buildAcctRecord(Long acctFlowId, String miNo, String prodNo, String eventNo, List<AcctInfoCollector> acctInfoCollectorList, boolean isSave) throws ServiceException;
	/**
	 * 根据记帐流水ID查询分录信息
	 * 
	 * @param acctFlowId 记帐流水ID
	 * @return
	 */
	public AcctRecordResult getAcctRecord(Long acctFlowId)throws ServiceException;
	/**
	 * 删除分录信息 
	 *
	 * @param acctFlowId
	 * @throws ServiceException
	 */
	public void deleteAcctRecord(Long acctFlowId)throws ServiceException;
	
	/**
	 * 查询单个实体的分录信息
	 * @param flow
	 * @return
	 * @throws ServiceException
	 */
	public AcctTranRecordAdapt getAcctTranRecordAdaptByRecordFlow(AcctRecordFlow flow) throws ServiceException;
	/**
	 * 蓝字(借贷相反)冲正的分录
	 * @deprecated
	 * @param preAcctFlowId 之前的流水ID
	 * @param acctFlowId 冲正流水ID
	 * @param isSave
	 * @return
	 * @throws ServiceException
	 */
	public AcctRecordResult buildReverseAcctRecord(Long preAcctFlowId, Long acctFlowId, boolean isSave)throws ServiceException;
	/**
	 * 红字冲正(金额为负)的分录
	 * @param preAcctFlowId 之前的流水ID
	 * @param acctFlowId 冲正流水ID
	 * @param isSave
	 * @return
	 * @throws ServiceException
	 */
	public AcctRecordResult buildNegativeAcctRecord(Long preAcctFlowId, Long acctFlowId, boolean isSave) throws ServiceException;

}
