package com.upg.cfsettle.order.order;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.cust.core.ICfsPrjOrderService;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjOrder;
import com.upg.cfsettle.mapping.prj.CfsPrjOrderAuditLog;
import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.cfsettle.util.CfsConstant;
import com.upg.ucars.basesystem.UcarsHelper;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.util.DateTimeUtil;

/**
 * Created by zuo on 2017/4/5.
 */
@Service
public class CfsPrjOrderAuditLogServiceImpl implements ICfsPrjOrderAuditLogService {

    @Autowired
    private ICfsPrjOrderAuditLogDao prjOrderAuditLogDao;
    @Autowired
    private ICfsPrjOrderService prjOrderService;
    @Autowired
    private IPrjService prjService;

    @Override
    public void prjOrderAudit(CfsPrjOrder prjOrder, CfsPrjOrderAuditLog prjOrderAuditLog) {
        if(prjOrder.getId() == null){
            UcarsHelper.throwServiceException("订单ID为空");
        }
        CfsPrjOrder cfsPrjOrder =  prjOrderService.getPrjOrderById(prjOrder.getId());
        if(cfsPrjOrder == null){
            UcarsHelper.throwServiceException("订单不存在");
        }
        if(!cfsPrjOrder.getStatus().equals(CfsConstant.PRJ_ORDER_STATUS_AUDIT)){
            UcarsHelper.throwServiceException("合同不是待审核状态");
        }
        CfsPrj prj = prjService.getPrjById(cfsPrjOrder.getPrjId());
        if(prjOrderAuditLog != null){
            prjOrderAuditLog.setAuditSysid(SessionTool.getUserLogonInfo().getSysUserId());
            prjOrderAuditLog.setCtime(DateTimeUtil.getNowDateTime());
            prjOrderAuditLog.setCsysid(SessionTool.getUserLogonInfo().getSysUserId());
            prjOrderAuditLog.setContractNo(cfsPrjOrder.getContractNo());
            prjOrderAuditLog.setPrjId(cfsPrjOrder.getPrjId());
            prjOrderAuditLog.setPrjOrderId(prjOrder.getId());
            prjOrderAuditLog.setPrjName(prj.getPrjName());
            prjOrderAuditLog.setAuditTime(DateTimeUtil.getNowDateTime());
            prjOrderAuditLogDao.save(prjOrderAuditLog);
        }
        //update prjOrder
        //审核通过
        if(prjOrderAuditLog.getStatus().equals(CfsConstant.CONTRACT_LOG_STATUS_PASS)){
            cfsPrjOrder.setStatus(CfsConstant.PRJ_ORDER_STATUS_PAY);//待打款审核
        }
        //审核驳回
        if(prjOrderAuditLog.getStatus().equals(CfsConstant.CONTRACT_LOG_STATUS_REJECT)){
            cfsPrjOrder.setStatus(CfsConstant.PRJ_ORDER_STATUS_REJECT);//退回重签
        }
        cfsPrjOrder.setContractAuditTime(DateTimeUtil.getNowDateTime());
        cfsPrjOrder.setContractAuidtSysid(SessionTool.getUserLogonInfo().getSysUserId());
        cfsPrjOrder.setMtime(DateTimeUtil.getNowDateTime());
        prjOrderService.updatePrjOrder(cfsPrjOrder);
    }

    @Override
    public List<Map<String, Object>> findOrderLog(OrderAuditLogBean searchBean,Page page) {
        return prjOrderAuditLogDao.findOrderLog(searchBean,page);
    }
}
