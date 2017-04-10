package com.upg.cfsettle.organization.core;

import com.upg.cfsettle.mapping.organization.CfsOrgArea;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;
import com.upg.ucars.util.BeanUtils;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

/**
 * Created by zuo on 2017/3/28.
 */
@Service
public class OrgAreaServiceImpl implements IOrgAreaService {

    @Autowired
    private IOrgAreaDao orgAreaDao;

    @Override
    public List<CfsOrgArea> findByCondition(CfsOrgArea searchBean, Page pg) {
        String hql = "from CfsOrgArea cfsOrgArea";
        QueryCondition condition = new QueryCondition(hql);
        if (searchBean != null) {
            if(!StringUtil.isEmpty(searchBean.getAreaName())){
                condition.addCondition(new ConditionBean("cfsOrgArea.areaName",ConditionBean.LIKE,searchBean.getAreaName()));
            }
            if(null != searchBean.getStatus()){
                condition.addCondition(new ConditionBean("cfsOrgArea.status",ConditionBean.EQUAL,searchBean.getStatus()));
            }
            if(null != searchBean.getId()){
                condition.addCondition(new ConditionBean("cfsOrgArea.id",ConditionBean.EQUAL,searchBean.getId()));
            }
        }
        condition.addOrder(new OrderBean("cfsOrgArea.ctime",true));
        return orgAreaDao.queryEntity( condition.getConditionList(), pg);
    }

    @Override
    public void addOrgArea(CfsOrgArea orgArea) {
        orgArea.setCtime(DateTimeUtil.getNowDateTime());
        orgArea.setCsysid(SessionTool.getUserLogonInfo().getSysUserId());
        orgAreaDao.save(orgArea);
    }

    @Override
    public void updateOrgArea(CfsOrgArea orgArea) {
        CfsOrgArea updateOrgArea = orgAreaDao.get(orgArea.getId());
        updateOrgArea.setAreaName(orgArea.getAreaName());
        updateOrgArea.setOverRange(orgArea.getOverRange());
        updateOrgArea.setStatus(orgArea.getStatus());
        updateOrgArea.setMtime(DateTimeUtil.getNowDateTime());
        updateOrgArea.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
        orgAreaDao.update(updateOrgArea);
    }

    @Override
    public CfsOrgArea getOrgAreaById(Long id) {
        return orgAreaDao.get(id);
    }
}


