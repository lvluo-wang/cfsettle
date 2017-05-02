package com.upg.cfsettle.organization.core;

import com.upg.cfsettle.mapping.organization.CfsOrgDept;
import com.upg.cfsettle.organization.bean.OrganizationBean;
import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by zuo on 2017/3/29.
 */
public interface IOrgDeptService extends IBaseService {

    List<Map<String,Object>> findByCondition(CfsOrgDept searchBean, Page page);

    void addOrgDept(CfsOrgDept orgDept);

    CfsOrgDept getOrgDeptById(Long id);

    void updateOrgDept(CfsOrgDept orgDept);

    List<CfsOrgDept> find(CfsOrgDept searchBean, Page page);

    OrganizationBean getByDeptId(Long deptId);
}
