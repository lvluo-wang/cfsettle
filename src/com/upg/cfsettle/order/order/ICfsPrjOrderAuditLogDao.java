package com.upg.cfsettle.order.order;

import com.upg.cfsettle.mapping.prj.CfsPrjOrderAuditLog;
import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by zuo on 2017/4/5.
 */
public interface ICfsPrjOrderAuditLogDao extends IBaseDAO<CfsPrjOrderAuditLog,Long> {

    List<Map<String,Object>> findOrderLog(OrderAuditLogBean searchBean,Page page);

}
