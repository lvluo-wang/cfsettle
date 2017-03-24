package com.upg.ucars.framework.bpm.assign.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.ucars.constant.CommonConst;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.bpm.assign.dao.IHumanTaskActorDelegatorDao;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.framework.bpm.HumnTaskActorDelegate;
@Service
public class HumnTaskActorDelegateServiceImpl extends BaseService implements IHumnTaskActorDelegateService{
	@Autowired
	private IHumanTaskActorDelegatorDao humanTaskActorDelegatorDao;
	@Override
	public List<HumnTaskActorDelegate> findAllTaskDelegatesByCreator(Long delegator,Page page) {
		return humanTaskActorDelegatorDao.findAllTaskDelegatesByCreator(delegator,page);
	}
	@Override
	public void doAdd(HumnTaskActorDelegate humnTaskActorDelegate) {
		humanTaskActorDelegatorDao.save(humnTaskActorDelegate);
	}
	@Override
	public void doEdit(HumnTaskActorDelegate humnTaskActorDelegate) {
		humanTaskActorDelegatorDao.update(humnTaskActorDelegate);
	}
	@Override
	public HumnTaskActorDelegate getTaskDelegateById(Long id) {
		return humanTaskActorDelegatorDao.get(id);
	}
	@Override
	public void startDelegate(Long id) {
		HumnTaskActorDelegate humnTaskActorDelegate = getTaskDelegateById(id);
		if(new Date().after(humnTaskActorDelegate.getEndTime())){
			ExceptionManager.throwException(ServiceException.class, 
					ErrorCodeConst.COMMON_ERROR_CODE, "委托的有效期已经过期");
		}
		if(!CommonConst.isFalse.equals(humnTaskActorDelegate.getInEffect())){
			ExceptionManager.throwException(ServiceException.class, 
					ErrorCodeConst.COMMON_ERROR_CODE, "未启动的委托才能被启动");
		}
		humnTaskActorDelegate.setInEffect(CommonConst.isTrue);
		doEdit(humnTaskActorDelegate);
		
	}
	@Override
	public void endDelegate(Long id) {
		HumnTaskActorDelegate humnTaskActorDelegate = getTaskDelegateById(id);
		if(!CommonConst.isTrue.equals(humnTaskActorDelegate.getInEffect())){
			ExceptionManager.throwException(ServiceException.class, 
					ErrorCodeConst.COMMON_ERROR_CODE, "委托尚未启动");
		}
		humnTaskActorDelegate.setInEffect(CommonConst.isFalse);
		doEdit(humnTaskActorDelegate);
	}
	@Override
	public void batchDelete(List<Long> idList) {
		if(idList == null || idList.isEmpty()){
			return;
		}
		for(Long id : idList){
			HumnTaskActorDelegate humnTaskActorDelegate = getTaskDelegateById(id);
			if(!CommonConst.isFalse.equals(humnTaskActorDelegate.getInEffect())){
				if(new Date().after(humnTaskActorDelegate.getEndTime())){
					continue;
				}
				ExceptionManager.throwException(ServiceException.class, 
						ErrorCodeConst.COMMON_ERROR_CODE, "委托已经启动，不能删除");
			}
			humanTaskActorDelegatorDao.delete(humnTaskActorDelegate);
		}
	}

}
