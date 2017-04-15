package com.upg.cfsettle.cust.core;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.mapping.prj.CfsPrjRepayPlan;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.StringUtil;

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
		prjRepayPlanDao.update(plan);
	}

	@Override
	public List<Map<String, Object>> findByCondition(CfsPrjRepayPlan searchBean, Page page) {
		if(!StringUtil.isEmpty(searchBean.getQueryType())){
			searchBean.setStartDate(DateTimeUtil.getNowDateTime());
			if(UtilConstant.TIME_LIMIT_WEEK.equals(searchBean.getQueryType())){
				searchBean.setEndDate(DateTimeUtil.addDay(DateTimeUtil.getNowDateTime(), 7));
			}else if(UtilConstant.TIME_LIMIT_MONTH.equals(searchBean.getQueryType())){
				searchBean.setEndDate(DateTimeUtil.getStringToDateMinute(DateTimeUtil.LastDateOfMonth(DateTimeUtil.toDateString(new Date()))));
			}
		}
		return prjRepayPlanDao.findByCondition(searchBean,page);
	}

	@Override
	public CfsPrjRepayPlan getPrjRepayPlanById(Long id) {
		return prjRepayPlanDao.get(id);
	}

	@Override
	public CfsPrjRepayPlan getPrjPlanByPrjIdAndPeriod(Long prjId, Long repayPeriod) {
        return prjRepayPlanDao.getPrjPlanByPrjIdAndPeriod(prjId,repayPeriod);
	}
}
