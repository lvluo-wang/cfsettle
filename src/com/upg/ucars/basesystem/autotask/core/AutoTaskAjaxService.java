/*******************************************************************************
 * Leadmind Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Jan 21, 2009
 *******************************************************************************/


package com.upg.ucars.basesystem.autotask.core;

import java.util.List;

/**
 * 供AJAX调用
 * @deprecated
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */
public class AutoTaskAjaxService {
	
	public List getCurrentAutoTaskInstances(){
		List list = AutoTaskCurrentMonitor.getInstance().getAutoTaskInstances();
		
		
		return list;
	}
	
	
	public List getSubMemberTaskInstances(Long taskId){
		List list = AutoTaskCurrentMonitor.getInstance().getAutoTaskInstance(taskId).getSubTaskList();
		
		
		return list;
	}
	
}
