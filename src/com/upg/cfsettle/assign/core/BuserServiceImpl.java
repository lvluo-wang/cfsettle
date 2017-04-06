package com.upg.cfsettle.assign.core;

import com.upg.cfsettle.cust.core.ICfsCustBuserRelateDao;
import com.upg.cfsettle.mapping.organization.CfsOrgArea;
import com.upg.cfsettle.mapping.prj.CfsCustBuserRelate;
import com.upg.cfsettle.organization.bean.OrganizationBean;
import com.upg.cfsettle.organization.core.IOrgAreaDao;
import com.upg.cfsettle.organization.core.IOrgDeptDao;
import com.upg.cfsettle.organization.core.IOrgTeamDao;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.ICommonDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;
import com.upg.ucars.util.BeanUtils;
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
    public List<BuserSale> queryBuser(Buser user, Page page) {
        String hql = "select user from Buser user where " +
                " user.userId >3 ";
        List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
        if (user != null && user.getUserName() != null) {
            conditionList.add(new ConditionBean("user.userName", ConditionBean.LIKE, user.getUserName()));
        }
        QueryCondition qc = new QueryCondition(hql);
        qc.addConditionList(conditionList);
        qc.addOrder(new OrderBean("userId"));
        List<Buser> list = commonDAO.queryByCondition(qc, page);
        List<BuserSale> buserSaleList = new ArrayList<>(list.size());
        BuserSale buserSale = null;
        for (Buser buser : list) {
            buserSale = new BuserSale();
            try {
                BeanUtils.copyProperties(buserSale, buser);
                //客户数量
                List<CfsCustBuserRelate> buserRelateList = buserRelateDao.findByBuserId(buser.getUserId());
                Integer custNum = buserRelateList == null ? 0 : buserRelateList.size();
                buserSale.setCustNum(custNum);
                if (buser.getPosCode().equals(UtilConstant.CFS_CUST_MANAGER)
                        || buser.getPosCode().equals(UtilConstant.CFS_TEAM_MANAGER)) {
                    //客户经理、团队长
                    if (buser.getTeamId() != null) {
                        OrganizationBean organizationBean = teamDao.getByTeamId(buser.getTeamId());
                        if (organizationBean != null) {
                            buserSale.setTeamName(organizationBean.getTeamName());
                            buserSale.setDeptName(organizationBean.getDeptName());
                            buserSale.setAreaName(organizationBean.getAreaName());
                        }
                    }
                }
                if (buser.getPosCode().equals(UtilConstant.CFS_DEPT_MANAGER)) {
                    //营业部负责人
                    if (buser.getDeptId() != null) {
                        OrganizationBean organizationBean = deptDao.getByDeptId(buser.getDeptId());
                        if (organizationBean != null) {
                            buserSale.setDeptName(organizationBean.getDeptName());
                            buserSale.setAreaName(organizationBean.getAreaName());
                        }
                    }
                }
                if (buser.getPosCode().equals(UtilConstant.CFS_AREA_MANAGER)) {
                    //区域经理
                    if (buser.getAreaId() != null) {
                        CfsOrgArea area = areaDao.get(buser.getDeptId());
                        if (area != null) {
                            buserSale.setAreaName(area.getAreaName());
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            buserSaleList.add(buserSale);
        }
        return buserSaleList;
    }
}
