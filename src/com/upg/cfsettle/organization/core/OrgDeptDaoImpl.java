package com.upg.cfsettle.organization.core;

import com.upg.cfsettle.mapping.organization.CfsOrgDept;
import com.upg.cfsettle.organization.bean.OrganizationBean;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SysBaseDao;
import com.upg.ucars.util.SQLCreater;
import com.upg.ucars.util.StringUtil;
import org.apache.commons.collections.map.HashedMap;

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

    @Override
    public OrganizationBean getByDeptId(Long deptId) {
        String sql = "select dept.dept_name as deptName,area.area_name as areaName from cfs_org_dept dept,cfs_org_area area" +
                " where dept.owned_area=area.id" +
                " and dept.id=:deptId";
        Map<String,Object> param = new HashedMap();
        param.put("deptId",deptId);
        List<OrganizationBean> list = (List<OrganizationBean>) this.findListByMap(sql,param,null,OrganizationBean.class);
        if(list != null && list.size() >0){
            return list.get(0);
        }
        return null;
    }
}
