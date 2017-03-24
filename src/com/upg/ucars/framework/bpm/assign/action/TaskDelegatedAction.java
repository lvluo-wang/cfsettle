package com.upg.ucars.framework.bpm.assign.action;

import java.util.ArrayList;
import java.util.List;

import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.constant.CommonConst;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.factory.DynamicPropertyTransfer;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.bpm.assign.service.IHumnTaskActorDelegateService;
import com.upg.ucars.framework.bpm.tasktrace.service.ITaskTraceInfoService;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.framework.bpm.HumnTaskActorDelegate;
import com.upg.ucars.mapping.framework.bpm.InstanceBusiness;
import com.upg.ucars.mapping.framework.bpm.TaskTraceInfo;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.PropertyTransVo;

@SuppressWarnings("serial")
public class TaskDelegatedAction extends BaseAction{
	private IHumnTaskActorDelegateService humnTaskActorDelegateService;
	
	private IUserService userService;
	
	private HumnTaskActorDelegate humnTaskActorDelegate;
	
	private UserLogonInfo user;
	
	private ITaskTraceInfoService		taskTraceInfoService;
	
	public String main(){
		return SUCCESS;
	}
	
	public String list(){
		Long userId = SessionTool.getUserLogonInfo().getSysUserId();
		List<HumnTaskActorDelegate> humnTaskActorDelegates = humnTaskActorDelegateService.findAllTaskDelegatesByCreator(userId,getPg());
		List<PropertyTransVo> transVoList = new ArrayList<PropertyTransVo>();
		transVoList.add(new PropertyTransVo("delegator", Buser.class, "userId", "userName", "delegatorName"));
		return setDatagridInputStreamData(DynamicPropertyTransfer.transform(humnTaskActorDelegates, transVoList), getPg());
	}

	public String toAdd(){
		user = SessionTool.getUserLogonInfo();
		return SUCCESS;
	}
	
	public void doAdd(){
		humnTaskActorDelegate.setInEffect(CommonConst.isFalse);
		humnTaskActorDelegateService.doAdd(humnTaskActorDelegate);
	}
	
	public String toEdit(){
		humnTaskActorDelegate = humnTaskActorDelegateService.getTaskDelegateById(getPKId());
		if(!CommonConst.isFalse.equals(humnTaskActorDelegate.getInEffect())){
			ExceptionManager.throwException(ServiceException.class, 
					ErrorCodeConst.COMMON_ERROR_CODE, "委托已经启动");
		}
		humnTaskActorDelegate = (HumnTaskActorDelegate)DynamicPropertyTransfer.dynamicAddProperty(humnTaskActorDelegate, "delegatorName", userService.getUserById(humnTaskActorDelegate.getDelegator()).getUserName());
		return SUCCESS;
	}
	
	public String toView(){
		humnTaskActorDelegate = humnTaskActorDelegateService.getTaskDelegateById(getPKId());
		humnTaskActorDelegate = (HumnTaskActorDelegate)DynamicPropertyTransfer.dynamicAddProperty(humnTaskActorDelegate, "delegatorName", userService.getUserById(humnTaskActorDelegate.getDelegator()).getUserName());
		return SUCCESS;
	}
	
	public String toDelegateTask(){
		return SUCCESS;
	}
	
	public String listDelegateTask(){
		List<TaskTraceInfo> list = taskTraceInfoService.getTaskTraceInfoByDelegateId(getPKId());
		List<PropertyTransVo> propertyConfigs = new ArrayList<PropertyTransVo>();
		propertyConfigs.add(new PropertyTransVo("entityId",InstanceBusiness.class,"id","col1#col9#processCnName","col1#col9#processCnName"));
		list = DynamicPropertyTransfer.transform(list, propertyConfigs);
		return setDatagridInputStreamData(list, getPg());
	}
	
	public void doEdit(){
		humnTaskActorDelegateService.doEdit(humnTaskActorDelegate);
	}
	
	public void batchDelete(){
		humnTaskActorDelegateService.batchDelete(getIdList());
	}
	
	public void startDelegate(){
		humnTaskActorDelegateService.startDelegate(getPKId());
	}
	
	public void endDelegate(){
		humnTaskActorDelegateService.endDelegate(getPKId());
	}
	//===================================
	public HumnTaskActorDelegate getHumnTaskActorDelegate() {
		return humnTaskActorDelegate;
	}

	public void setHumnTaskActorDelegate(HumnTaskActorDelegate humnTaskActorDelegate) {
		this.humnTaskActorDelegate = humnTaskActorDelegate;
	}

	public void setHumnTaskActorDelegateService(IHumnTaskActorDelegateService humnTaskActorDelegateService) {
		this.humnTaskActorDelegateService = humnTaskActorDelegateService;
	}

	public UserLogonInfo getUser() {
		return user;
	}

	public void setUser(UserLogonInfo user) {
		this.user = user;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setTaskTraceInfoService(ITaskTraceInfoService taskTraceInfoService) {
		this.taskTraceInfoService = taskTraceInfoService;
	}
	
}
