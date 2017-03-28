package com.upg.cfsettle.organization.core;

import com.upg.cfsettle.mapping.organization.CfsOrgArea;
import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;

import java.util.List;

/**
 * Created by zuo on 2017/3/28.
 */
public interface IOrgAreaService extends IBaseService {

    List<CfsOrgArea> findByCondition(CfsOrgArea searchBean, Page pg);

    void addOrgArea(CfsOrgArea orgArea);

    void updateOrgArea(CfsOrgArea orgArea);

    CfsOrgArea getOrgAreaById(Long id);
}
