package com.upg.cfsettle.order.order;

import com.sun.xml.internal.ws.server.sei.SEIInvokerTube;
import com.upg.cfsettle.mapping.prj.CfsPrjOrderAuditLog;
import com.upg.cfsettle.mapping.prj.CfsPrjRepayPlan;
import com.upg.ucars.basesystem.security.core.user.IUserDAO;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SysBaseDao;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.util.SQLCreater;
import com.upg.ucars.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by zuo on 2017/4/5.
 */
@Dao
public class CfsPrjOrderAuditLogDaoImpl extends SysBaseDao<CfsPrjOrderAuditLog,Long> implements ICfsPrjOrderAuditLogDao {

    @Autowired
    private IUserDAO userDAO;

    @Override
    public List<Map<String, Object>> findOrderLog(OrderAuditLogBean searchBean,Page page) {
        String sql = "select prjOrder.*,cust.real_name,log.audit_sysid,log.audit_time,log.status as log_status from cfs_prj_order_audit_log log left join " +
                " cfs_prj_order prjOrder log.contract_no=prjOrder.contract_no" +
                " left join cfs_prj prj on log.prj_id=prj.id" +
                " left join cfs_cust cust on prjOrder.cust_id=cust.id";
        SQLCreater sqlCreater = new SQLCreater(sql,false);
        if(searchBean != null){
            Long prjId = searchBean.getPrjId();
            if(prjId != null){
                sqlCreater.and("log.prj_id =:prjId","prjId",prjId,true);
            }
            String contractNO = searchBean.getContractNo();
            if(!StringUtil.isEmpty(contractNO)){
                sqlCreater.and("log.contract_no =:contractNO","contractNO",contractNO,true);
            }
        }
        sqlCreater.orderBy("log.ctime",false);
        return getMapListByStanderdSQL(sqlCreater.getSql(),sqlCreater.getParameterMap(),page);
    }

    private void addRepayDetail(List<Map<String, Object>> result) {
        if (result != null && result.size() > 0) {
            for (Map<String, Object> map : result) {
                Object auditId = (Object) map.get("AUDIT_SYSID");
                if(auditId != null){
                    //…Û∫À»À–’√˚
                    Buser buser = userDAO.get(Long.valueOf(auditId.toString()));
                    map.put("AUDIT_NAME",buser.getUserName());
                }
            }
        }
    }
}
