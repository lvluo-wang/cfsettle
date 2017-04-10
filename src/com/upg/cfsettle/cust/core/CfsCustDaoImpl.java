package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.organization.CfsOrgArea;
import com.upg.cfsettle.mapping.prj.CfsCust;
import com.upg.cfsettle.organization.bean.OrganizationBean;
import com.upg.cfsettle.organization.core.IOrgAreaDao;
import com.upg.cfsettle.organization.core.IOrgDeptDao;
import com.upg.cfsettle.organization.core.IOrgTeamDao;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.basesystem.security.core.user.IUserDAO;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.base.SysBaseDao;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.StringUtil;
import edu.emory.mathcs.backport.java.util.Collections;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Dao
public class CfsCustDaoImpl extends SysBaseDao<CfsCust, Long> implements ICfsCustDao{
    @Autowired
    private IUserDAO userDAO;
    @Autowired
    private IOrgTeamDao teamDao;
    @Autowired
    private IOrgDeptDao deptDao;
    @Autowired
    private IOrgAreaDao areaDao;

    @Override
    public List<CfsCust> findAllCustByBuserId(Long buserId) {
        String hql = "select cust from CfsCust cust,CfsCustBuserRelate relate where " +
                " cust.id=relate.custId and relate.sysId=?";
        List<CfsCust> list = this.find(hql,buserId);
        if(list != null && list.size() >0){
            return list;
        }
        return Collections.emptyList();
    }

    @Override
    public List<CfsCustInfo> findByConditionAndPosCode(CustSearchBean searchBean, Page page) {
        StringBuffer sql = new StringBuffer("select cust.id,cust.real_name as realName," +
                " cust.mobile,cust.id_card as idCard,cust.ctime,cust.sex,cust.is_valid as isValid,relate.sys_id as sysId " +
                " from cfs_cust cust left join cfs_cust_buser_relate relate" +
                " on cust.id=relate.cust_id where 1=1 ");
        UserLogonInfo logonInfo = SessionTool.getUserLogonInfo();
        Map<String,Object> param = new HashedMap();
        if(!StringUtil.isEmpty(logonInfo.getPosCode()) && logonInfo.getPosCode().equals(UtilConstant.CFS_CUST_MANAGER)){
            //客户经理
            if(logonInfo.getTeamId() != null){
                sql.append(" and relate.sys_id=:sysId");
                param.put("sysId",logonInfo.getSysUserId());
            }
        }
        StringBuffer busers = new StringBuffer();
        List<Long> buserIds = new ArrayList<>();
        if(!StringUtil.isEmpty(logonInfo.getPosCode()) && logonInfo.getPosCode().equals(UtilConstant.CFS_TEAM_MANAGER)
         && logonInfo.getTeamId() != null){
            //团队长
            //获得该团的所有员工
            buserIds = userDAO.getUserIdByTeamId(logonInfo.getTeamId());
        }
        if(!StringUtil.isEmpty(logonInfo.getPosCode()) && logonInfo.getPosCode().equals(UtilConstant.CFS_DEPT_MANAGER)
                && logonInfo.getDeptId() != null){
            //营业部负责人
            //获得该营业部下的所有员工
            buserIds = userDAO.getUserIdByDeptId(logonInfo.getDeptId());
        }
        if(!StringUtil.isEmpty(logonInfo.getPosCode()) && logonInfo.getPosCode().equals(UtilConstant.CFS_AREA_MANAGER)
                && logonInfo.getAreaId() != null){
            //区域经理
            //获得该营区域下的所有员工
            buserIds = userDAO.getUserIdByAreaId(logonInfo.getAreaId());
        }

        if(searchBean != null){
            String realName = searchBean.getRealName();
            if(!StringUtil.isEmpty(realName)){
                sql.append(" and cust.real_name=:realName");
                param.put("realName",realName);
            }
            String mobile = searchBean.getMobile();
            if(!StringUtil.isEmpty(mobile)){
                sql.append(" and cust.mobile=:mobile");
                param.put("mobile",mobile);
            }
            String custManager = searchBean.getCustManager();
            if(!StringUtil.isEmpty(custManager)){
                //客户经理
                Buser buser = userDAO.getUserByUserName(custManager);
                if(buser != null){
                    sql.append(" and relate.sys_id=:custManager");
                    param.put("custManager",buser.getUserId());
                }
            }
            //todo 团队／营业部／区域
            Set<Long> searchBuserIds = new HashSet<>();
            Long team = searchBean.getTeam();
            if(team != null){
               List<Long> list = userDAO.getUserIdByTeamId(team);
                searchBuserIds.addAll(list);
            }
            Long dept = searchBean.getDept();
            if(dept != null){
                List<Long>  list = userDAO.getUserIdByDeptId(dept);
                searchBuserIds.addAll(list);
            }
            Long area = searchBean.getArea();
            if(dept != null){
                List<Long> list = userDAO.getUserIdByAreaId(area);
                searchBuserIds.addAll(list);
            }
            if(searchBuserIds.size() > 0){
                sql.append(" and relate.sys_id in (:searchBuserIds)");
                param.put("searchBuserIds",searchBuserIds);
            }
            Byte isValid = searchBean.getIsValid();
            if(isValid != null){
                sql.append(" and cust.is_valid=:isValid");
                param.put("isValid",isValid);
            }
        }
        List<CfsCustInfo> reslut = Collections.emptyList();
        if(buserIds.size() > 0){
            sql.append(" and relate.sys_id in (:busers)");
            param.put("busers",buserIds);
            reslut = (List<CfsCustInfo>) this.findListByMap(sql.toString(),param,page,CfsCustInfo.class);
            addOrganization(reslut);
        }else{
            if(logonInfo.getUserType().equals(Buser.TYPE_BRCH_GLOBAL_MANAGER)){
                reslut = (List<CfsCustInfo>) this.findListByMap(sql.toString(),param,page,CfsCustInfo.class);
                addOrganization(reslut);
            }
        }
        return reslut;
    }

    private void addOrganization(List<CfsCustInfo> reslut) {
        for(CfsCustInfo custInfo : reslut){
            custInfo.setSysIdLong(custInfo.getSysId().longValue());
            if(custInfo.getSysId() != null){
                Buser buser = userDAO.get(Long.valueOf(custInfo.getSysId()));
                if(buser != null){
                    if (buser.getPosCode().equals(UtilConstant.CFS_CUST_MANAGER)
                            || buser.getPosCode().equals(UtilConstant.CFS_TEAM_MANAGER)) {
                        //客户经理、团队长
                        if (buser.getTeamId() != null) {
                            OrganizationBean organizationBean = teamDao.getByTeamId(buser.getTeamId());
                            if (organizationBean != null) {
                                custInfo.setTeamName(organizationBean.getTeamName());
                                custInfo.setDeptName(organizationBean.getDeptName());
                                custInfo.setAreaName(organizationBean.getAreaName());
                            }
                        }
                    }
                    if (buser.getPosCode().equals(UtilConstant.CFS_DEPT_MANAGER)) {
                        //营业部负责人
                        if (buser.getDeptId() != null) {
                            OrganizationBean organizationBean = deptDao.getByDeptId(buser.getDeptId());
                            if (organizationBean != null) {
                                custInfo.setDeptName(organizationBean.getDeptName());
                                custInfo.setAreaName(organizationBean.getAreaName());
                            }
                        }
                    }
                    if (buser.getPosCode().equals(UtilConstant.CFS_AREA_MANAGER)) {
                        //区域经理
                        if (buser.getAreaId() != null) {
                            CfsOrgArea area = areaDao.get(buser.getDeptId());
                            if (area != null) {
                                custInfo.setAreaName(area.getAreaName());
                            }
                        }
                    }
                }
            }

        }
    }
}
