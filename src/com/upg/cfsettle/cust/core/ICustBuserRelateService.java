package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsCustBuserRelate;
import com.upg.ucars.framework.base.IBaseService;


public interface ICustBuserRelateService extends IBaseService {

	CfsCustBuserRelate getRelateByCustId(Long custId);
}
