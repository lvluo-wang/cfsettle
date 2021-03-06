package com.upg.cfsettle.comm.core;

import java.util.List;
import java.util.Map;

import com.upg.cfsettle.mapping.prj.CfsCommOrderRelate;
import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;

/**
 * Created by zuobaoshi on 2017/4/12.
 */
public interface ICfsCommOrderRelateDao extends IBaseDAO<CfsCommOrderRelate,Long> {

    /**
     * 员工未支付佣金，未关联到comm_info
     * @param sysId
     * @return
     */
    List<CfsCommOrderRelate> findBySysIdAndCommIdIsNull(Long sysId);


    List<Map<String,Object>> findTotalAmountGroupBySysId();

    /**
     * 佣金明细
     */

    public List<Map<String, Object>> findCommDetailByCommId(Long commId, Page page);

    /**
     * 判断此订单的佣金明细是否已生成
     * @param cfsCommOrderRelate
     * @return
     */
    CfsCommOrderRelate findIsExits(CfsCommOrderRelate cfsCommOrderRelate);
}
