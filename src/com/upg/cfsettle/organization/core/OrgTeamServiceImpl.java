package com.upg.cfsettle.organization.core;

import com.upg.cfsettle.mapping.organization.CfsOrgArea;
import com.upg.cfsettle.mapping.organization.CfsOrgDept;
import com.upg.cfsettle.mapping.organization.CfsOrgTeam;
import com.upg.cfsettle.organization.bean.OrganizationBean;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by zuo on 2017/3/29.
 */
@Service
public class OrgTeamServiceImpl implements IOrgTeamService {

    @Autowired
    private IOrgTeamDao orgTeamDao;
    @Autowired
    private IOrgDeptDao orgDeptDao;
    @Autowired
    private IOrgAreaDao orgAreaDao;

    @Override
    public List<Map<String, Object>> findByCondition(OrgTeamBean searchBean, Page page) {
        return orgTeamDao.findByCondition(searchBean,page);
    }

    @Override
    public void addOrgTeam(CfsOrgTeam orgTeam) {
        orgTeam.setCtime(DateTimeUtil.getNowDateTime());
        orgTeam.setCsysid(SessionTool.getUserLogonInfo().getSysUserId());
        orgTeamDao.save(orgTeam);
    }

    @Override
    public CfsOrgTeam getOrgTeam(Long id) {
        CfsOrgTeam orgTeam = orgTeamDao.get(id);
        if(orgTeam != null && orgTeam.getOwnedDept() != null){
            CfsOrgDept orgDept = orgDeptDao.get(orgTeam.getOwnedDept());
            if(orgDept != null){
                orgTeam.setDeptName(orgDept.getDeptName());
            }
        }
        if(orgTeam != null && orgTeam.getOwnedArea() != null){
            CfsOrgArea orgArea = orgAreaDao.get(orgTeam.getOwnedArea());
            if(orgArea != null){
                orgTeam.setAreaName(orgArea.getAreaName());
            }
        }
        return orgTeam;
    }

    @Override
    public void updateOrgTeam(CfsOrgTeam orgTeam) {
        orgTeam.setMtime(DateTimeUtil.getNowDateTime());
        orgTeam.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
        orgTeamDao.update(orgTeam);
    }

	@Override
	public List<CfsOrgTeam> find(OrgTeamBean searchBean, Page page) {
		 String hql = "from CfsOrgTeam cfsOrgTeam";
	        QueryCondition condition = new QueryCondition(hql);
	        if (searchBean != null) {
	            if(searchBean.getOwnedDept() != null){
	                condition.addCondition(new ConditionBean("cfsOrgTeam.ownedDept",ConditionBean.EQUAL,searchBean.getOwnedDept()));
	            }
	            if(null != searchBean.getStatus()){
	                condition.addCondition(new ConditionBean("cfsOrgTeam.status",ConditionBean.EQUAL,searchBean.getStatus()));
	            }
	        }
	        condition.addOrder(new OrderBean("cfsOrgTeam.ctime",true));
	        return orgTeamDao.queryEntity( condition.getConditionList(), page);
	}

    @Override
    public OrganizationBean getByTeamId(Long teamId) {
        return orgTeamDao.getByTeamId(teamId);
    }
}
