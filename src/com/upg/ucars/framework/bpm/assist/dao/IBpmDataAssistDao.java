package com.upg.ucars.framework.bpm.assist.dao;

import java.util.Date;
import java.util.List;

import org.jbpm.graph.exe.ProcessInstance;

import com.upg.ucars.framework.base.IBaseDAO;

/** 
 * 流程信息辅助DAO
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */

public interface IBpmDataAssistDao extends IBaseDAO<ProcessInstance, Long>{
	/**
	 * 查询指定日期及指定日期之前已结束流程主键
	 *
	 * @param endDate
	 * @param firstNum 从第条开始 按结束时间顺序排序
	 * @param maxNum 条数 
	 * @return List < Long:processInstanceId >
	 */
	public List<Long> queryEndProcessInstanceIdsByDate(Date endDate,Long firstNum, Long maxNum);
	/**
	 * 查询总流程数量
	 *
	 * @param endDate
	 * @return
	 */
	public Long queryProcessInstanceCount();
	/**
	 * 查询已结束总流程数量
	 */
	public Long queryEndProcessInstanceCount();
	/**
	 * 查询未结束流程数量
	 *
	 * @param endDate
	 * @return
	 */
	public Long queryUnEndProcessInstanceCount();
	/**
	 * 查询指定日期及指定日期之前已结束流程数量
	 *
	 * @param endDate
	 * @return
	 */
	public Long queryEndProcessInstanceCountByDate(Date endDate);
	

}
 
 