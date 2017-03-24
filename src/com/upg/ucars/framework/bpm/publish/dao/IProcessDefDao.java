/*******************************************************************************
 * Copyright (c) 2003-2008 leadmind Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2008-11-4
 *******************************************************************************/


package com.upg.ucars.framework.bpm.publish.dao;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.framework.bpm.ProcessDef;
import com.upg.ucars.model.ConditionBean;

/**
 * TODO 
 *
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */
public interface IProcessDefDao extends IBaseDAO<ProcessDef, Long>{
	
	/**
	 * 根据名称获取流程
	 */
	public ProcessDef getProcessDefByName(String name);
	/**
	 * 取出所有流程
	 */
	public List<ProcessDef> getAllProcessDef();
	
	/**
	 * 条件查询
	 *
	 * @param conditionList
	 * @param page
	 * @return
	 * @throws DAOException
	 */
	public List<ProcessDef> queryProcessDef(List<ConditionBean> conditionList, Page page) throws DAOException;
	/**
	 * 页面组件查询
	 *
	 * @param qcpt 页面查询组件
	 * @param page 分页对象
	 * @return
	 * @throws DAOException
	 */
	public List<ProcessDef> queryProcessDef(QueryComponent qcpt, Page page) throws DAOException;
	
}
