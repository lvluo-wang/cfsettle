package com.upg.cfsettle.comm.core;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zuobaoshi on 2017/4/12.
 */
public interface CfsCommOrderRelateService extends IBaseService {

    /**
     * 佣金明细
     * @param sysUserId
     * @param date
     * @param page
     * @return
     */
    List<Map<String,Object>> findCommDetailBySysid(Long sysUserId, Date date,Page page);
}
