package com.upg.cfsettle.cust.core;

import java.util.List;

import com.upg.cfsettle.mapping.prj.CfsCust;
import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;


public interface ICfsCustService extends IBaseService {
	
	List<CfsCust> findByCondition(CfsCust searchBean, Page page);
	
	CfsCust queryCfsCustById(Long id);
	
	void updateCfsCust(CfsCust banner);
	
	void addCfsCust(CfsCust banner);

}
