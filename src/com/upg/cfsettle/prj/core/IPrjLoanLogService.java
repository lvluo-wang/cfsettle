package com.upg.cfsettle.prj.core;

import java.util.List;

import com.upg.cfsettle.mapping.prj.CfsPrjLoanLog;
import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;


public interface IPrjLoanLogService extends IBaseService {

    void addPrjLoanLog(CfsPrjLoanLog loanLog);

    CfsPrjLoanLog getCfsPrjLoanLogById(Long id);
    
    /**
     * 条件查询
     * @author renzl123
     * @date 2017年4月4日 下午8:48:17
     * @param searchBean
     * @param page
     * @return
     */
    List<CfsPrjLoanLog> findByCondition(CfsPrjLoanLog searchBean,Page page);
}
