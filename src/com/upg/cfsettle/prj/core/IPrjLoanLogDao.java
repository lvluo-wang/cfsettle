package com.upg.cfsettle.prj.core;

import java.util.List;

import com.upg.cfsettle.mapping.prj.CfsPrjLoanLog;
import com.upg.ucars.framework.base.IBaseDAO;

public interface IPrjLoanLogDao extends IBaseDAO<CfsPrjLoanLog,Long> {

	List<CfsPrjLoanLog> getByPrjId(Long prjId);
}
