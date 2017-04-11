package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsPrjOrderPaybackLog;
import com.upg.cfsettle.mapping.prj.CfsPrjOrderRepayPlan;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.util.DateTimeUtil;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by zuobaoshi on 2017/4/4.
 */
@Service
public class CfsPrjOrderRepayPlanServiceImpl implements ICfsPrjOrderRepayPlanService {

    @Autowired
    private ICfsPrjOrderRepayPlanDao prjOrderRepayPlanDao;

    @Autowired
    private IUserService userService;

    @Override
    public List<CfsPrjOrderRepayPlan> findByOrderIdAndType(Long prjOrderId,Byte ptype) {
        List<CfsPrjOrderRepayPlan> prjOrderRepayPlanList =  prjOrderRepayPlanDao.findByOrderIdAndType(prjOrderId,ptype);
        //还款审核人姓名
        for(CfsPrjOrderRepayPlan prjOrderRepayPlan : prjOrderRepayPlanList){
            if(prjOrderRepayPlan.getPaybackAuditSysid() != null){
                Buser buser = userService.getUserById(prjOrderRepayPlan.getPaybackAuditSysid());
                if(buser != null){
                    prjOrderRepayPlan.setPaybackAuditName(buser.getUserName());
                }
            }
            prjOrderRepayPlan.setTotalPeriods(prjOrderRepayPlanList.size());
        }
        return prjOrderRepayPlanList;
    }

    @Override
    public CfsPrjOrderRepayPlan getRaiseOrderRepayPlan(Long prjOrderId) {
        List<CfsPrjOrderRepayPlan> prjOrderRepayPlanList =  prjOrderRepayPlanDao.findByOrderIdAndType(prjOrderId,Byte.valueOf("2"));
        if(prjOrderRepayPlanList != null && prjOrderRepayPlanList.size()>0){
            return prjOrderRepayPlanList.get(0);
        }
        return null;
    }

	@Override
	public void addPrjOrderRepayPlan(CfsPrjOrderRepayPlan orderPlan) {
		prjOrderRepayPlanDao.save(orderPlan);
	}

	@Override
	public List<Map<String, Object>> findByCondition(CfsPrjOrderPaybackLog searchBean, Page page) {
		return prjOrderRepayPlanDao.findByCondition(searchBean,page);
	}

	@Override
	public CfsPrjOrderRepayPlan getprjOrderPlanById(Long id) {
		return prjOrderRepayPlanDao.get(id);
	}

	@Override
	public void updatePrjOrderRepayPlan(CfsPrjOrderRepayPlan plan) {
		plan.setMtime(DateTimeUtil.getNowDateTime());
		prjOrderRepayPlanDao.update(plan);
	}
}
