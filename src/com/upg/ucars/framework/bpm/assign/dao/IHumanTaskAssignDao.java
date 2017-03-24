/*******************************************************************************
 * Copyright (c) 2003-2008 leadmind Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2008-11-11
 *******************************************************************************/


package com.upg.ucars.framework.bpm.assign.dao;

import java.util.List;

import com.upg.ucars.framework.exception.DAOException;
public interface IHumanTaskAssignDao {

	/**
	 *	2008-11-20
	 * @author penghui (mailto:penghui@leadmind.com.cn)
	 * 根据流程名，任务名，机构ID得到所有配置的部门ID，角色ID，用户ID
	 */
	public List[] getRefIdsByBrchId(String processName,String taskName,Long brchId) throws DAOException;

	
	/**
	 *	2008-11-20
	 * @author penghui (mailto:penghui@leadmind.com.cn)
	 * 取出所有根据流程名和任务名机构ID，角色下的，部门下的，所有用户
	 */
	public String[] getTaskActorsByBrchId(String processName,String taskName,Long brchId) throws DAOException;
	
	public List<String> getTaskActors(String processName,String taskName, Long brchId) throws DAOException;

}
