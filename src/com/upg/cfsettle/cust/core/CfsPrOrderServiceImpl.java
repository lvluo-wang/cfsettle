package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.organization.CfsOrgArea;
import com.upg.cfsettle.mapping.organization.CfsOrgDept;
import com.upg.cfsettle.mapping.organization.CfsOrgTeam;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjOrder;
import com.upg.cfsettle.organization.core.IOrgAreaDao;
import com.upg.cfsettle.organization.core.IOrgDeptDao;
import com.upg.cfsettle.organization.core.IOrgTeamDao;
import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.ucars.basesystem.UcarsHelper;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by zuobaoshi on 2017/4/2.
 */
@Service
public class CfsPrOrderServiceImpl implements ICfsPrjOrderService {

    @Autowired
    private ICfsPrjOrderDao prjOrderDao;

    @Autowired
    private IPrjService prjService;
    @Autowired
    private IOrgAreaDao areaDao;
    @Autowired
    private IOrgDeptDao deptDao;
    @Autowired
    private IOrgTeamDao teamDao;

    @Override
    public List<Map<String, Object>> findByCondition(CustOrderBean searchBean, Page page) {
        return prjOrderDao.findByCondition(searchBean,page);
    }

    @Override
    public void addPrjOrder(CfsPrjOrder prjOrder) {
        Long prjId = prjOrder.getPrjId();
        if(prjId == null){
            UcarsHelper.throwServiceException("项目ID为空");
            CfsPrj prj = prjService.getPrjById(prjId);
            if(prj !=null){
                //募集中状态
                if(prj.getStatus() == Byte.valueOf("3")){
                    if(prj.getRemainingAmount().compareTo(prjOrder.getMoney())<0){
                        UcarsHelper.throwServiceException("项目剩余金额不足");
                    }
                }else{
                    UcarsHelper.throwServiceException("项目不在募集中");
                }
            }
            prjOrder.setStatus(Byte.valueOf("1"));//待合同审核
            prjOrder.setCtime(DateTimeUtil.getNowDateTime());
            UserLogonInfo logonInfo = SessionTool.getUserLogonInfo();
            prjOrder.setCsysid(logonInfo.getSysUserId());
            prjOrder.setOwnedArea(logonInfo.getAreaId());
            CfsOrgArea orgArea = areaDao.get(logonInfo.getAreaId());
            prjOrder.setOwnedAreaName(orgArea == null ? null : orgArea.getAreaName());
            prjOrder.setOwnedDept(logonInfo.getDeptId());
            CfsOrgDept orgDept = deptDao.get(logonInfo.getDeptId());
            prjOrder.setOwnedDeptName(orgDept == null ? null : orgDept.getDeptName());
            prjOrder.setOwnedTeam(logonInfo.getTeamId());
            CfsOrgTeam orgTeam = teamDao.get(logonInfo.getDeptId());
            prjOrder.setOwnedTeamName(orgTeam == null ? null : orgTeam.getTeamName());
            //todo 客户对应的服务员工cfs_cust_buser_relate

            prjOrderDao.save(prjOrder);
        }
    }
}
