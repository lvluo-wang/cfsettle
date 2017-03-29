package com.upg.cfsettle.organization.core;

import com.upg.cfsettle.mapping.organization.CfsOrgDept;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SysBaseDao;
import com.upg.ucars.util.SQLCreater;
import com.upg.ucars.util.StringUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by zuo on 2017/3/29.
 */
@Dao
public class OrgDeptDaoImpl extends SysBaseDao<CfsOrgDept,Long> implements IOrgDeptDao{

    @Override
    public List<Map<String, Object>> findByCondition(CfsOrgDept searchBean, Page page) {
        StringBuffer sql = new StringBuffer("select dept.*,area.area_name\n");
        sql.append("from cfs_org_dept dept left join cfs_org_area area\n");
        sql.append(" on dept.owned_area=area.id\n");
        SQLCreater sqlCreater = new SQLCreater(sql.toString(),false);
        if(searchBean != null){
            String deptName = searchBean.getDeptName();
            if(!StringUtil.isEmpty(deptName)){
                sqlCreater.and("dept.dept_name like :deptName","deptName","%"+deptName+"%",true);
            }
            Byte status = searchBean.getStatus();
            if(null != status){
                sqlCreater.and("dept.status=:status","status",status,true);
            }
        }
        sqlCreater.orderBy("dept.ctime",true);
        return getMapListByStanderdSQL(sqlCreater.getSql(),sqlCreater.getParameterMap(),page);
    }
}
