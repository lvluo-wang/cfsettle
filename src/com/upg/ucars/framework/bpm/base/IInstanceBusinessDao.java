package com.upg.ucars.framework.bpm.base;

import java.util.List;

import com.upg.ucars.bpm.core.InstanceBusinessSearchBean;
import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.mapping.framework.bpm.InstanceBusiness;

public interface IInstanceBusinessDao extends IBaseDAO<InstanceBusiness, Long> {

	/** 审批中 */
	public static final String INSTANCE_STATUS_AUDIT = "02";
	/** 普通结束 */
	public static final String INSTANCE_STATUS_END_NORMAL = "04";
	/** 驳回结束 */
	public static final String INSTANCE_STATUS_END_REJECT = "90";
	
	/**
	 * 获取相关的真实实体
	 * 
	 * @param entityId
	 * @return
	 */
	public Object getEntityById(Long id);
	
	/**
	 * 根据流程实例获取实例业务数据
	 * 
	 * @param instanceId
	 * @return
	 */
	public InstanceBusiness getInstanceBusinessByInstanceId(Long instanceId);
	
	
	public List<InstanceBusiness> getInstanceBusinessList(InstanceBusinessSearchBean searchBean,Page page);

}
