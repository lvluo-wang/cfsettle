package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsPrjOrder;
import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by zuobaoshi on 2017/4/2.
 */
public interface ICfsPrjOrderDao extends IBaseDAO<CfsPrjOrder,Long> {

    List<Map<String, Object>> findByCondition(CustOrderBean searchBean, Page page);

}
