package com.upg.cfsettle.organization.core;

import com.upg.cfsettle.mapping.organization.CfsOrgTeam;
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
public class OrgTeamDaoImpl extends SysBaseDao<CfsOrgTeam,Long> implements IOrgTeamDao {

    @Override
    public List<Map<String, Object>> findByCondition(OrgTeamBean searchBean, Page page) {
        StringBuffer sql = new StringBuffer("select team.id,team.team_name,team.ctime,team.status,dept.dept_name,dept.dept_mobile,dept.dept_addr,area.area_name \n");
        sql.append("from cfs_org_team team left join cfs_org_dept dept on team.owned_dept=dept.id\n");
        sql.append("left join cfs_org_area area on team.owned_area=area.id\n");
        SQLCreater sqlCreater = new SQLCreater(sql.toString(),false);
        if(searchBean != null){
            String teamName = searchBean.getTeamName();
            if(!StringUtil.isEmpty(teamName)){
                sqlCreater.and("team.team_name like :teamName","teamName",teamName,true);
            }
            Long ownedDept = searchBean.getOwnedDept();
            if(ownedDept != null){
                sqlCreater.and("team.owned_dept =:ownedDept","ownedDept",ownedDept,true);
            }
            Long ownedArea = searchBean.getOwnedArea();
            if(ownedArea != null){
                sqlCreater.and("team.owned_area =:ownedArea","ownedArea",ownedArea,true);
            }
            Byte status = searchBean.getStatus();
            if(status != null){
                sqlCreater.and("team.status =:status","status",ownedArea,true);
            }
        }
        sqlCreater.orderBy("team.ctime",true);
        return getMapListByStanderdSQL(sqlCreater.getSql(),sqlCreater.getParameterMap(),page);
    }
}
