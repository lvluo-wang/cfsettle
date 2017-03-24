package com.upg.ucars.framework.bpm.assist.service;

import java.util.Date;
import java.util.List;

/** 
 * 流程数据清理服务
 *
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */
public interface IBpmDataClearService {
	/**
	 * 查询流程汇总数据
	 * @return
	 */
	public Long queryProcessInstanceCount();
	/**
	 * 查询已结束流程汇总数据
	 * @return
	 */
	public Long queryEndProcessInstanceCount();
	/**
	 * 查询未结束流程汇总数据
	 * @return
	 */
	public Long queryUnEndProcessInstanceCount();
	/**
	 * 查询指定日期及指定日期之前已结束流程汇总数据
	 * @param endDate 结束日期
	 * @return
	 */
	public Long queryEndProcessInstanceCountByDate(Date endDate);
	
	/**
	 * 清除指定日期及指定日期之前已结束流程数据
	 * @param endDate
	 * @return Long[]{可删总数,实际删除数}
	 */
	public Long[] clearEndProcessInstanceData(Date endDate);
	
	/**
	 * 清除指定流程实例数据
	 * @param piList
	 * @return
	 */
	public int clearEndProcessInstance(List<Long> piList);

}

 