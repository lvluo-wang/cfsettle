package com.upg.cfsettle.prj.core;

import java.util.List;

import com.upg.cfsettle.mapping.prj.CfsPrjPaybackLog;
import com.upg.ucars.framework.base.IBaseDAO;

public interface IPrjPaybackLogDao extends IBaseDAO<CfsPrjPaybackLog,Long> {

	List<CfsPrjPaybackLog> getByPrjId(Long prjId);
}
