package com.upg.cfsettle.cust.core;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.common.CodeItemUtil;
import com.upg.cfsettle.mapping.organization.CfsOrgArea;
import com.upg.cfsettle.mapping.organization.CfsOrgDept;
import com.upg.cfsettle.mapping.organization.CfsOrgTeam;
import com.upg.cfsettle.mapping.prj.CfsCust;
import com.upg.cfsettle.mapping.prj.CfsCustBuserRelate;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjOrder;
import com.upg.cfsettle.organization.core.IOrgAreaDao;
import com.upg.cfsettle.organization.core.IOrgDeptDao;
import com.upg.cfsettle.organization.core.IOrgTeamDao;
import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.cfsettle.util.CfsConstant;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.basesystem.UcarsHelper;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.tools.imp.excel.ExcelUtil;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.StringUtil;

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
    @Autowired
    private ICfsCustService custService;


    @Override
    public List<Map<String, Object>> findByCondition(CustOrderBean searchBean, Page page) {
        return prjOrderDao.findByCondition(searchBean,page);
    }

    @Override
    public void addPrjOrder(CfsPrjOrder prjOrder) {
        if(!StringUtil.isEmpty(prjOrder.getContractNo())){
            CfsPrjOrder cfsPrjOrder = getPrjOrderByContractNo(prjOrder.getContractNo());
            if(cfsPrjOrder != null){
                UcarsHelper.throwServiceException("该合同编号已作废");
            }
        }
        Long prjId = prjOrder.getPrjId();
        if(prjId == null) {
            UcarsHelper.throwServiceException("项目ID为空");
        }
            CfsPrj prj = prjService.getPrjById(prjId);
            if(prj !=null){
                if(DateTimeUtil.compareDate(prjOrder.getInvestTime(),prj.getStartBidTime()) !=1){
                    UcarsHelper.throwServiceException("投资时间小于融资开始时间");
                }
                //投资中状态
                if(prj.getStatus().equals(CfsConstant.PRJ_STATUS_INVESTING)){
                    if(prj.getRemainingAmount().compareTo(prjOrder.getMoney())<0){
                        UcarsHelper.throwServiceException("项目剩余投资金额不足");
                    }
                    if(prjOrder.getInvestTime().after(prj.getEndBidTime())){
                        UcarsHelper.throwServiceException("投资时间大于项目募集期截止时间");
                    }
                    if(prjOrder.getInvestTime().before(prj.getStartBidTime())){
                        UcarsHelper.throwServiceException("投资时间小于项目募集开始时间");
                    }
                    prjOrder.setStatus(CfsConstant.PRJ_ORDER_STATUS_AUDIT);//待合同审核
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
                            prjOrder.setServiceSysType(user.getPosCode());
                        }
                    }
                    prjOrderDao.save(prjOrder);
                    //update prj remainingAmount
                    BigDecimal remainingAmount = prj.getRemainingAmount().subtract(prjOrder.getMoney());
                    prj.setRemainingAmount(remainingAmount);
                   /* if(remainingAmount.compareTo(BigDecimal.ZERO) == 0){
                        //募资完成，待放款
                        prj.setStatus(CfsConstant.PRJ_STATUS_TO_LOAN);
                    }*/
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

	@Override
	public void batchDelete(List<Long> ids) {
		for(Long id:ids){
			prjOrderDao.delete(id);
		}
	}

	@Override
	public void updatePrjOrder(CfsPrjOrder cfsPrjOrder) {
		cfsPrjOrder.setMtime(DateTimeUtil.getNowDateTime());
		cfsPrjOrder.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
		prjOrderDao.update(cfsPrjOrder);
	}

    @Override
    public CfsPrjOrder getPrjOrderByContractNo(String contractNo) {
        return prjOrderDao.getPrjOrderByContractNo(contractNo);
    }

	@Override
	public void doAuditPrjOrder(CfsPrjOrder cfsPrjOrder) {
		CfsPrjOrder order = this.getPrjOrderById(cfsPrjOrder.getId());
		order.setRemark(cfsPrjOrder.getRemark());
		order.setPayNotesAttid(cfsPrjOrder.getPayNotesAttid());
		order.setPaySerialNum(cfsPrjOrder.getPaySerialNum());
		order.setCollectAuditTime(DateTimeUtil.getNowDateTime());
		order.setCollectAuditSysid(SessionTool.getUserLogonInfo().getSysUserId());
		order.setStatus(cfsPrjOrder.getStatus());
		if(CfsConstant.PRJ_ORDER_STATUS_INVESTING.equals(cfsPrjOrder.getStatus())){
			CfsCust cust = custService.queryCfsCustById(order.getCustId());
			if(cust.getIsValid().equals(Byte.valueOf("0"))){
				cust.setIsValid(Byte.valueOf("1"));
				custService.updateCfsCust(cust);
			}
		}else{
			CfsPrj cfsPrj = prjService.getPrjById(order.getPrjId());
			cfsPrj.setRemainingAmount(cfsPrj.getRemainingAmount().add(order.getMoney()));
			cfsPrj.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
			cfsPrj.setMtime(DateTimeUtil.getNowDateTime());
			prjService.updateCfsPrj(cfsPrj);
		}
		this.updatePrjOrder(order);
	}

	@Override
	public List<CfsPrjOrder> getPrjOrdersByPrjId(Long prjId) {
		return prjOrderDao.getPrjOrdersByPrjId(prjId);
	}

	@Override
	public List<CfsPrjOrder> findCommByCondition(CfsPrjOrder prjOrder,Page page) {
		String hql = "from CfsPrjOrder cfsPrjOrder";
		QueryCondition condition = new QueryCondition(hql);
		if (prjOrder != null) {
			Long prjId = prjOrder.getPrjId();
			if (prjId == null) {
				condition.addCondition(new ConditionBean("cfsPrjOrder.prjId", ConditionBean.EQUAL, prjId));
			}
		}
		return prjOrderDao.queryEntity( condition.getConditionList(), page);
	}

	@Override
	public List<CfsPrjOrder> getOKPrjOrdersByPrjId(Long prjId) {
		return prjOrderDao.getOKPrjOrdersByPrjId(prjId);
	}

	@Override
	public HSSFWorkbook generatOrderAuidtData(OutputStream os, CustOrderBean searchBean, String[] headers, String title, Page pg) {
		List<List<Object>> dataRows = new ArrayList<List<Object>>();
		List<Map<String, Object>> dataList = this.findByCondition(searchBean, null);
		Iterator<Map<String, Object>> it = dataList.iterator();
		while(it.hasNext()){
			List<Object> row = new ArrayList<Object>();
			Map<String, Object> exportData = it.next();
			row.add(exportData.get("CONTRACT_NO"));
			row.add(exportData.get("INVEST_TIME"));
			row.add(exportData.get("REAL_NAME"));
			row.add(exportData.get("PRJ_NAME"));
			row.add(exportData.get("MONEY"));
			row.add(exportData.get("PAY_BANK"));
			row.add(exportData.get("PAY_ACCOUNT_NO"));
			row.add(exportData.get("SERVICE_SYS_NAME"));
			row.add(CodeItemUtil.getCodeNameByKey(UtilConstant.CFS_PRJ_ORDER_STATUS,exportData.get("STATUS").toString()));
			dataRows.add(row);
		}
 		return ExcelUtil.createHSSFWorkbook(title, headers, dataRows);
	}
}