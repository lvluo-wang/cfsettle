package com.upg.cfsettle.organization.core;

import com.upg.cfsettle.mapping.organization.CfsOrgDept;
import com.upg.cfsettle.organization.bean.OrganizationBean;
import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.exception.DAOException;

import java.util.List;
import java.util.Map;

/**
 * Created by zuo on 2017/3/29.
 */
public interface IOrgDeptDao extends IBaseDAO<CfsOrgDept,Long> {

    List<Map<String,Object>> findByCondition(CfsOrgDept searchBean, Page page);


    public <T> List<T> queryByCondition(QueryCondition qc, Page page) throws DAOException;

    //根据营业部对应的区域
    OrganizationBean getByDeptId(Long deptId);

}
