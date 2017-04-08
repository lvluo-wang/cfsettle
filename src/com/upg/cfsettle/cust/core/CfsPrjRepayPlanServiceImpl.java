package com.upg.cfsettle.cust.core;

import com.upg.cfsettle.mapping.prj.CfsPrjRepayPlan;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * Created by zuobaoshi on 2017/4/4.
 */
@Service
public class CfsPrjRepayPlanServiceImpl implements ICfsPrjRepayPlanService {

    @Autowired
    private ICfsPrjRepayPlanDao prjRepayPlanDao;

    @Override
    public Integer getTotalPeriodByPrjId(Long prjId) {
        List<CfsPrjRepayPlan> prjRepayPlanList = prjRepayPlanDao.findNoRaisePlanByPrjId(prjId);
        return prjRepayPlanList == null ? null : prjRepayPlanList.size();
    }

	@Override
	public void addPrjRepayPlan(CfsPrjRepayPlan plan) {
		prjRepayPlanDao.save(plan);
	}

	@Override
	public void updatePrjRepayPlan(CfsPrjRepayPlan plan) {
		prjRepayPlanDao.saveOrUpdate(plan);
	}

	@Override
	public List<Map<String, Object>> findByCondition(CfsPrjRepayPlan searchBean, Page page) {
		return prjRepayPlanDao.findByCondition(searchBean,page);
	}

	@Override
	public CfsPrjRepayPlan getPrjRepayPlanById(Long id) {
		return prjRepayPlanDao.get(id);
	}
}
