package com.upg.cfsettle.assign.core;

import com.upg.cfsettle.cust.core.ICfsCustBuserRelateDao;
import com.upg.cfsettle.mapping.organization.CfsOrgArea;
import com.upg.cfsettle.mapping.organization.CfsOrgDept;
import com.upg.cfsettle.mapping.organization.CfsOrgTeam;
import com.upg.cfsettle.mapping.prj.CfsCustBuserRelate;
import com.upg.cfsettle.organization.bean.OrganizationBean;
import com.upg.cfsettle.organization.core.IOrgAreaDao;
import com.upg.cfsettle.organization.core.IOrgDeptDao;
import com.upg.cfsettle.organization.core.IOrgTeamDao;
import com.upg.cfsettle.util.CfsConstant;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.factory.DynamicPropertyTransfer;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.ICommonDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.mapping.basesystem.other.Attachment;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;
import com.upg.ucars.util.BeanUtils;
import com.upg.ucars.util.PropertyTransVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zuo on 2017/4/6.
 */
@Service
public class BuserServiceImpl implements IBuserService {

    @Autowired
    private ICommonDAO commonDAO;
    @Autowired
    private ICfsCustBuserRelateDao buserRelateDao;
    @Autowired
    private IOrgTeamDao teamDao;
    @Autowired
    private IOrgDeptDao deptDao;
    @Autowired
    private IOrgAreaDao areaDao;

    @Override
    public List<Buser> queryBuser(Buser user, Page page) {
        String hql="select user from Buser user where " +
                " user.userId >3 ";
        List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
        if(user!=null && user.getUserName() != null)
        {
            conditionList.add(new ConditionBean("user.userName", ConditionBean.LIKE, user.getUserName()));
        }
        QueryCondition qc = new QueryCondition(hql);
        qc.addConditionList(conditionList);
        qc.addOrder(new OrderBean("userId"));
        List<Buser> list = commonDAO.queryByCondition(qc, page);
        for(Buser buser : list){
                //员工名下客户人员
                List<CfsCustBuserRelate> buserRelateList = buserRelateDao.findByBuserId(buser.getUserId());
                Integer custNum = buserRelateList == null ? 0 : buserRelateList.size();
            buser = (Buser) DynamicPropertyTransfer.dynamicAddProperty(buser, "custNum", custNum.toString());
            if(buser.getPosCode().equals(UtilConstant.CFS_CUST_MANAGER)
                    || buser.getPosCode().equals(UtilConstant.CFS_TEAM_MANAGER)){
                //客户经理/团队长
                if(buser.getTeamId() != null) {
                    OrganizationBean organizationBean = teamDao.getByTeamId(buser.getTeamId());
                    if(organizationBean != null){
                        buser = (Buser) DynamicPropertyTransfer.dynamicAddProperty(buser, "teamName", organizationBean.getTeamName());
                        buser = (Buser) DynamicPropertyTransfer.dynamicAddProperty(buser, "deptName", organizationBean.getDeptName());
                        buser = (Buser) DynamicPropertyTransfer.dynamicAddProperty(buser, "areaName", organizationBean.getAreaName());
                    }
                }
            }
            if(buser.getPosCode().equals(UtilConstant.CFS_DEPT_MANAGER)){
                //营业部负责人
                if(buser.getDeptId() != null) {
                    OrganizationBean organizationBean = deptDao.getByDeptId(buser.getDeptId());
                    if(organizationBean != null){
                        buser = (Buser) DynamicPropertyTransfer.dynamicAddProperty(buser, "teamName", "-");
                        buser = (Buser) DynamicPropertyTransfer.dynamicAddProperty(buser, "deptName", organizationBean.getDeptName());
                        buser = (Buser) DynamicPropertyTransfer.dynamicAddProperty(buser, "areaName", organizationBean.getAreaName());
                    }
                }
            }
            if(buser.getPosCode().equals(UtilConstant.CFS_AREA_MANAGER)){
                //区域经理
                if(buser.getAreaId() != null) {
                    CfsOrgArea area = areaDao.get(buser.getDeptId());
                    if(area != null){
                        buser = (Buser) DynamicPropertyTransfer.dynamicAddProperty(buser, "teamName", "-");
                        buser = (Buser) DynamicPropertyTransfer.dynamicAddProperty(buser, "deptName", "-");
                        buser = (Buser) DynamicPropertyTransfer.dynamicAddProperty(buser, "areaName", area.getAreaName());
                    }
                }
            }
        }
        List<PropertyTransVo> trans = new ArrayList<PropertyTransVo>();
       /* trans.add(new PropertyTransVo("areaId", CfsOrgArea.class, "id", "areaName","areaName"));
        trans.add(new PropertyTransVo("deptId", CfsOrgDept.class, "id", "deptName","deptName"));
        trans.add(new PropertyTransVo("teamId", CfsOrgTeam.class, "id", "teamName","teamName"));
        list = DynamicPropertyTransfer.transform(list, trans);*/
        return list;
    }
}
