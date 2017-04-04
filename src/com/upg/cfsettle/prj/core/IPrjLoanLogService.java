package com.upg.cfsettle.prj.core;

import com.upg.cfsettle.mapping.prj.CfsPrjLoanLog;
import com.upg.ucars.framework.base.IBaseService;


public interface IPrjLoanLogService extends IBaseService {

    void addPrjLoanLog(CfsPrjLoanLog loanLog);

    CfsPrjLoanLog getCfsPrjLoanLogById(Long id);
}
