package com.upg.cfsettle.cust.core;

import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by zuobaoshi on 2017/4/2.
 */
@Service
public class CfsPrOrderServiceImpl implements ICfsPrjOrderService {

    @Autowired
    private ICfsPrjOrderDao prjOrderDao;

    @Override
    public List<Map<String, Object>> findByCondition(CustOrderBean searchBean, Page page) {
        return prjOrderDao.findByCondition(searchBean,page);
    }
}
