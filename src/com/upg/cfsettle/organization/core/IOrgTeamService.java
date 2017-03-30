package com.upg.cfsettle.organization.core;

import com.upg.cfsettle.mapping.organization.CfsOrgTeam;
import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by zuo on 2017/3/29.
 */
public interface IOrgTeamService extends IBaseService {

    List<Map<String, Object>> findByCondition(OrgTeamBean searchBean, Page page);

    void addOrgTeam(CfsOrgTeam orgTeam);

	List<CfsOrgTeam> find(OrgTeamBean searchBean, Page page);
}
