package com.upg.cfsettle.organization.core;

import com.upg.cfsettle.mapping.organization.CfsOrgArea;
import com.upg.cfsettle.mapping.organization.CfsOrgDept;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.mapping.basesystem.product.ReBrchProd;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.SQLCreater;
import com.upg.ucars.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by zuo on 2017/3/29.
 */
@Service
public class OrgDeptServiceImpl implements IOrgDeptService {

    @Autowired
    private IOrgDeptDao orgDeptDao;

    @Autowired
    private IOrgAreaDao orgAreaDao;

    @Override
    public List<Map<String, Object>> findByCondition(CfsOrgDept searchBean, Page page) {
        return orgDeptDao.findByCondition(searchBean,page);
    }

    @Override
    public void addOrgDept(CfsOrgDept orgDept) {
        orgDept.setCtime(DateTimeUtil.getNowDateTime());
        orgDept.setCsysid(SessionTool.getUserLogonInfo().getSysUserId());
        orgDeptDao.save(orgDept);
    }

    @Override
    public CfsOrgDept getOrgDeptById(Long id) {
        CfsOrgDept orgDept =  orgDeptDao.get(id);
        if(orgDept != null && orgDept.getOwnedArea() != null){
            CfsOrgArea orgArea = orgAreaDao.get(orgDept.getOwnedArea());
            if(orgArea != null){
                orgDept.setAreaName(orgArea.getAreaName());
            }
        }
        return orgDept;
    }

    @Override
    public void updateOrgDept(CfsOrgDept orgDept) {
        orgDept.setMtime(DateTimeUtil.getNowDateTime());
        orgDept.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
        orgDeptDao.update(orgDept);
    }

    @Override
    public List<CfsOrgDept> find(CfsOrgDept searchBean, Page page) {
        QueryCondition qc = new QueryCondition();
        String hql = "select distinct dept from CfsOrgDept dept";
        qc.setHql(hql);
        qc.addCondition(new ConditionBean("dept.status", ConditionBean.EQUAL, Byte.valueOf("1")));
        if(searchBean != null){
        	if(searchBean.getOwnedArea() != null){
        		qc.addCondition(new ConditionBean("dept.ownedArea", ConditionBean.EQUAL,searchBean.getOwnedArea()));
        	}
        	if(!StringUtil.isEmpty(searchBean.getDeptName())){
        		qc.addCondition(new ConditionBean("dept.deptName", ConditionBean.LIKE,searchBean.getDeptName()));
        	}
            if(searchBean.getId() != null){
                qc.addCondition(new ConditionBean("dept.id", ConditionBean.EQUAL,searchBean.getId()));
            }
        }
        List list = this.orgDeptDao.queryByCondition(qc, page);
        return list;
    }
}
