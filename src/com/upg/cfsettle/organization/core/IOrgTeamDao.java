package com.upg.cfsettle.organization.core;

import com.upg.cfsettle.mapping.organization.CfsOrgTeam;
import com.upg.cfsettle.organization.bean.OrganizationBean;
import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by zuo on 2017/3/29.
 */
public interface IOrgTeamDao extends IBaseDAO<CfsOrgTeam,Long> {

    List<Map<String, Object>> findByCondition(OrgTeamBean searchBean, Page page);

    /**
     * 查询团队关联的营业部和区域
     * @param teamId
     * @return
     */
    OrganizationBean getByTeamId(Long teamId);



}
