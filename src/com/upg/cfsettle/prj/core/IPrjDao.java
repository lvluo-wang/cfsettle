package com.upg.cfsettle.prj.core;

import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.finance.mapping.yrzif.FiPrj;
import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by zuo on 2017/3/30.
 */
public interface IPrjDao extends IBaseDAO<CfsPrj,Long> {

    List<Map<String, Object>> findByCondition(CfsPrj searchBean, Page page);

    List<CfsPrj> findPrjByStatus(Byte status);
    
	List<Map<String, Object>> findLoanPrjByCondition(CfsPrj searchBean, Page pg);

	List<CfsPrj> findRepayPlanPrj();
}
