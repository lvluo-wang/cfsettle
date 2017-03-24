package com.upg.ucars.framework.bpm.assign.dao;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.mapping.framework.bpm.HumnTaskActorDelegate;

public interface IHumanTaskActorDelegatorDao extends IBaseDAO<HumnTaskActorDelegate, Long> {

	public List<HumnTaskActorDelegate> findHumnTaskActorDelegateByDelegator(Long delegator);
	
	public List<HumnTaskActorDelegate> findAllTaskDelegatesByCreator(Long creator,Page page);
	
	public List<Long> findActorByTaskIdAndDelegator(Long taskId,List<Long> userIds);
	
}
