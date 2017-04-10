package com.upg.cfsettle.order.order;

import java.util.List;

import com.upg.cfsettle.mapping.prj.CfsPrjOrderPaybackLog;
import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;


public interface ICfsPrjOrderPaybackLogService extends IBaseService{

	List<CfsPrjOrderPaybackLog> findByOrderRepayPlanId(Long pkId, Page page);
	
	List<CfsPrjOrderPaybackLog> findByCondition(CfsPrjOrderPaybackLog searchBean,Page page);
}
