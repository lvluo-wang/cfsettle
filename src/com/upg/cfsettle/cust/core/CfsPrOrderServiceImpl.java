package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.organization.CfsOrgArea;
import com.upg.cfsettle.mapping.organization.CfsOrgDept;
import com.upg.cfsettle.mapping.organization.CfsOrgTeam;
import com.upg.cfsettle.mapping.prj.CfsCustBuserRelate;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjOrder;
import com.upg.cfsettle.organization.core.IOrgAreaDao;
import com.upg.cfsettle.organization.core.IOrgDeptDao;
import com.upg.cfsettle.organization.core.IOrgTeamDao;
import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.ucars.basesystem.UcarsHelper;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
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
    @Autowired
    private ICfsCustBuserRelateDao custBuserRelateDao;
    @Autowired
    private IUserService userService;


    @Override
    public List<Map<String, Object>> findByCondition(CustOrderBean searchBean, Page page) {
        return prjOrderDao.findByCondition(searchBean,page);
    }

    @Override
    public void addPrjOrder(CfsPrjOrder prjOrder) {
        Long prjId = prjOrder.getPrjId();
        if(prjId == null) {
            UcarsHelper.throwServiceException("项目ID为空");
        }
            CfsPrj prj = prjService.getPrjById(prjId);
            if(prj !=null){
                //募集中状态
                if(prj.getStatus().equals(Byte.valueOf("3"))){
                    if(prj.getRemainingAmount().compareTo(prjOrder.getMoney())<0){
                        UcarsHelper.throwServiceException("项目剩余投资金额不足");
                    }
                    if(prjOrder.getInvestTime().after(prj.getEndBidTime())){
                        UcarsHelper.throwServiceException("投资时间大于项目募集期截止时间");
                    }
                    if(prjOrder.getInvestTime().before(prj.getStartBidTime())){
                        UcarsHelper.throwServiceException("投资时间小于项目募集开始时间");
                    }
                    prjOrder.setPrjName(prj.getPrjName());
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
                    List<CfsCustBuserRelate> custBuserRelateList = custBuserRelateDao.findByCustId(prjOrder.getCustId());
                    if(custBuserRelateList != null && custBuserRelateList.size()>0){
                        CfsCustBuserRelate cfsCustBuserRelate = custBuserRelateList.get(0);
                        prjOrder.setServiceSysid(cfsCustBuserRelate.getSysId());
                        //服务人员类型和名称
                        Buser user = userService.getUserById(cfsCustBuserRelate.getSysId());
                        if(user != null){
                            prjOrder.setServiceSysName(user.getUserName());
                        }
                        //todo 服务类型

                    }
                    prjOrderDao.save(prjOrder);
                    //update prj remainingAmount
                    BigDecimal remainingAmount = prj.getRemainingAmount().subtract(prjOrder.getMoney());
                    prj.setRemainingAmount(remainingAmount);
                    if(remainingAmount.compareTo(BigDecimal.ZERO) == 0){
                        //募资完成，待放款
                        prj.setStatus(Byte.valueOf("4"));
                    }
                    prjService.updatePrj(prj);

                }else{
                    UcarsHelper.throwServiceException("项目不在募集中");
                }
            }
    }

    @Override
    public CfsPrjOrder getPrjOrderById(Long id) {
        return prjOrderDao.get(id);
    }
}
