package com.upg.cfsettle.organization.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.upg.cfsettle.mapping.organization.CfsOrgArea;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.basesystem.security.core.user.IUserDAO;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.StringUtil;

/**
 * Created by zuo on 2017/3/28.
 */
@Service
public class OrgAreaServiceImpl implements IOrgAreaService {

    @Autowired
    private IOrgAreaDao orgAreaDao;
    @Autowired
    private IUserDAO userDAO;

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

	@Override
	public List<CfsOrgArea> getCombobox(CfsOrgArea searchBean) {
		List<CfsOrgArea> list = this.findByCondition(searchBean, null);
		List<CfsOrgArea> result = new ArrayList<CfsOrgArea>();
		if(searchBean != null){
			if(UtilConstant.CFS_AREA_MANAGER.equals(searchBean.getPosCode())){
				for(CfsOrgArea area:list){
					if(userDAO.getUserByAreaIdAndPosCode(area.getId(), UtilConstant.CFS_AREA_MANAGER) == null){
						result.add(area);
					}
				}
				return result;
			}else{
				return list;
			}
		}
        return list;
	}
}


