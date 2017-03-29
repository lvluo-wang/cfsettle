package com.upg.cfsettle.organization.core;

import com.upg.cfsettle.mapping.organization.CfsOrgTeam;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.util.DateTimeUtil;
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
}
