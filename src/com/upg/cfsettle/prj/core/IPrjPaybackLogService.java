package com.upg.cfsettle.prj.core;

import java.util.List;
import java.util.Map;

import com.upg.cfsettle.mapping.prj.CfsPrjPaybackLog;
import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;


public interface IPrjPaybackLogService extends IBaseService {

    void addPrjPayBackLog(CfsPrjPaybackLog paybackLog);

    CfsPrjPaybackLog getCfsPrjPaybackLogById(Long id);
    
    /**
     * 条件查询
     * @author renzl123
     * @date 2017年4月4日 下午8:48:17
     * @param searchBean
     * @param page
     * @return
     */
    List<Map<String,Object>> findByCondition(CfsPrjPaybackLog searchBean,Page page);


    List<Map<String, Object>> findByPrjPaybackLogByPrjId(Long prjId);
}
