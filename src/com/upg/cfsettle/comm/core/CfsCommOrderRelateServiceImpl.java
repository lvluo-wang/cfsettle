package com.upg.cfsettle.comm.core;

import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

/**
 * Created by zuobaoshi on 2017/4/12.
 */
@Service
public class CfsCommOrderRelateServiceImpl implements CfsCommOrderRelateService{

    @Autowired
    private ICfsCommOrderRelateDao commOrderRelateDao;

    @Override
    public List<Map<String, Object>> findCommDetailByCommId(Long commId, Page page) {
        return commOrderRelateDao.findCommDetailByCommId(commId,page);
    }
}
