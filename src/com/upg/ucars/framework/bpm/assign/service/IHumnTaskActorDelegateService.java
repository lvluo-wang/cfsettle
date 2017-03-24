package com.upg.ucars.framework.bpm.assign.service;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.mapping.framework.bpm.HumnTaskActorDelegate;

public interface IHumnTaskActorDelegateService extends IBaseService{
	
	List<HumnTaskActorDelegate> findAllTaskDelegatesByCreator(Long delegator,Page page);
	
	void doAdd(HumnTaskActorDelegate humnTaskActorDelegate);
	
	void doEdit(HumnTaskActorDelegate humnTaskActorDelegate);
	
	void batchDelete(List<Long> idList);
	
	HumnTaskActorDelegate getTaskDelegateById(Long id);
	
	void startDelegate(Long id);
	
	void endDelegate(Long id);
	
}
