package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsPrjOrderPaybackLog;
import com.upg.cfsettle.mapping.prj.CfsPrjOrderRepayPlan;
import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by zuobaoshi on 2017/4/2.
 */
public interface ICfsPrjOrderRepayPlanDao extends IBaseDAO<CfsPrjOrderRepayPlan,Long> {


    /**
     *根据orderid和type查询订单还款计划
     * @param
     * @return
     */
    List<CfsPrjOrderRepayPlan> findByOrderIdAndType(Long prjOrderId,Byte ptype);
    
    /**
     * 订单回款查询
     * @author renzhuolun
     * @date 2017年4月9日 下午5:51:14
     * @param searchBean
     * @param page
     * @return
     */
	List<Map<String, Object>> findByCondition(CfsPrjOrderPaybackLog searchBean, Page page);

}
