package com.upg.ucars.framework.bpm.ext;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.model.DealOpinionInfo;
/**
 * 使用工作流引擎需要实现的接口
 *
 * @author shentuwy
 */
public interface IBpmPlantfromClientService extends IBaseService{
	/**
	 * 根据机构和产品获取对应流程名
	 * 
	 * @param brchId
	 * @param prodNo
	 * @return
	 * @throws ServiceException 没有对应流程时抛出ServiceException异常
	 */
	String getProcessName(Long brchId, String prodNo);
	
	/**
	 * 根据机构和产品获取流程名
	 * <br/>
	 * 产品没有对应的流程不会抛异常 
	 * 
	 * @param brchId
	 * @param prodNo
	 * @return
	 */
	
	String getProcessNameIgnoreNoProcess(Long brchId, String prodNo);

	/**
	 * 记录任务处理信息 
	 *
	 * @param taskId
	 * @param entityId
	 * @param opinionInfo
	 */
	public void traceTaskDeal(Long taskId, Long entityId, DealOpinionInfo opinionInfo);
	
	
}
