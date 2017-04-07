package com.upg.cfsettle.prj.core;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.cust.core.ICfsPrjOrderRepayPlanService;
import com.upg.cfsettle.cust.core.ICfsPrjOrderService;
import com.upg.cfsettle.cust.core.ICfsPrjRepayPlanService;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjExt;
import com.upg.cfsettle.mapping.prj.CfsPrjOrder;
import com.upg.cfsettle.mapping.prj.CfsPrjOrderRepayPlan;
import com.upg.cfsettle.mapping.prj.CfsPrjRepayPlan;
import com.upg.cfsettle.mapping.prj.RepayPlanInfo;
import com.upg.cfsettle.util.CfsConstant;
import com.upg.cfsettle.util.CfsUtils;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.basesystem.UcarsHelper;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.BeanUtils;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.StringUtil;

/**
 * Created by zuo on 2017/3/30.
 */
@Service
public class PrjServiceImpl implements IPrjService {


    @Autowired
    private IPrjDao prjDao;
    @Autowired
    private IPrjExtDao prjExtDao;

    @Autowired
    private IPrjExtService prjExtService;
    @Autowired
    private ICfsPrjOrderService prjOrderService;
    @Autowired
    private ICfsPrjRepayPlanService prjRepayPlan;
    @Autowired
    private ICfsPrjOrderRepayPlanService prjOrderRepayPlan;

    @Override
    public List<Map<String, Object>> findByCondition(CfsPrj searchBean, Page page) {
        return prjDao.findByCondition(searchBean,page);
    }

    @Override
    public void addPrjApply(CfsPrj prj) {
        prjDao.save(prj);
    }

    @Override
    public void savePrjAndPrjExt(CfsPrj prj, CfsPrjExt prjExt) {
        prj.setMtime(DateTimeUtil.getNowDateTime());
        prj.setCsysid(SessionTool.getUserLogonInfo().getSysUserId());
        prj.setStatus(Byte.valueOf("1"));
        prj.setRemainingAmount(prj.getDemandAmount());
        dealPrjTimeLimitDay(prj);
        //prj.setYearRate(RateUtil.rateToPercent(prj.getYearRate()));
        //prj.setPeriodRate(RateUtil.rateToPercent(prj.getPeriodRate()));
        prjDao.save(prj);
        prjExt.setPrjId(prj.getId());
        prjExt.setCtime(DateTimeUtil.getNowDateTime());
        prjExt.setCsysid(SessionTool.getUserLogonInfo().getSysUserId());
        prjExtService.addPrjExt(prjExt);
    }

    private void dealPrjTimeLimitDay(CfsPrj prj) {
        if(prj.getTimeLimitUnit() == "Y"){
            prj.setTimeLimitDay(prj.getTimeLimit() * 365);
        }
        if(prj.getTimeLimitUnit() == "M"){
            prj.setTimeLimitDay(prj.getTimeLimit() * 30);
        }
    }

    @Override
    public CfsPrj getPrjById(Long id) {
        CfsPrj prj =  prjDao.get(id);
        //prj.setYearRate(RateUtil.percentToRate(prj.getYearRate()));
        //prj.setPeriodRate(RateUtil.percentToRate(prj.getPeriodRate()));
        //回款截止时间
        getRepayEndDate(prj);
        return prj;
    }

    private void getRepayEndDate(CfsPrj prj) {
        Date valueDate = prj.getEndBidTime();
        String timeLimitUnit = prj.getTimeLimitUnit();
        Integer timeLimit = prj.getTimeLimit();
        if(valueDate != null){
            Calendar calender = Calendar.getInstance();
            if(timeLimitUnit.equals("Y")){
                calender.setTime(valueDate);
                calender.add(Calendar.YEAR, timeLimit);
                prj.setRepayEndTime(calender.getTime());
            }
            if(timeLimitUnit.equals("M")){
                calender.setTime(valueDate);
                calender.add(Calendar.MONTH, timeLimit);
                prj.setRepayEndTime(calender.getTime());
            }
        }
    }

    @Override
    public void updatePrjAndPrjExt(CfsPrj prj, CfsPrjExt prjExt) {
        if(prj != null){
            prj.setMtime(DateTimeUtil.getNowDateTime());
            prj.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
            prjDao.update(prj);
        }
        if(prjExt != null){
            prjExt.setCtime(DateTimeUtil.getNowDateTime());
            prjExt.setCsysid(SessionTool.getUserLogonInfo().getSysUserId());
            prjExtDao.update(prjExt);
        }
    }

    @Override
    public void auditPrjAndPrjExt(CfsPrj prj, CfsPrjExt prjExt) {
        CfsPrj auditPrj = prjDao.get(prj.getId());
        if(!(auditPrj.getStatus() == Byte.valueOf("1")
                || auditPrj.getStatus() == Byte.valueOf("2"))){
            UcarsHelper.throwServiceException("项目已审核通过，不能再次审核");
        }
        try {
            BeanUtils.copyNoNullProperties(auditPrj,prj);
            auditPrj.setStatus(Byte.valueOf("2"));
            auditPrj.setMtime(DateTimeUtil.getNowDateTime());
            auditPrj.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
            prjDao.update(auditPrj);
            prjExt.setCtime(DateTimeUtil.getNowDateTime());
            prjExt.setCsysid(SessionTool.getUserLogonInfo().getSysUserId());
            prjExtDao.update(prjExt);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

	@Override
	public List<CfsPrj> findPrjByCondition(CfsPrj cfsPrj, Page page) {
		String hql = "from CfsPrj cfsPrj";
		QueryCondition condition = new QueryCondition(hql);
		if (cfsPrj != null) {
			String prjName = cfsPrj.getPrjName();
			if (!StringUtil.isEmpty(prjName) || prjName != null) {
				condition.addCondition(new ConditionBean("cfsPrj.prjName", ConditionBean.LIKE, prjName));
			}
		}
		return prjDao.queryEntity( condition.getConditionList(), page);
	}

    @Override
    public List<CfsPrj> findPrjByStatus(Byte status) {
        return prjDao.findPrjByStatus(status);
    }

    @Override
    public void updatePrj(CfsPrj prj) {
        prj.setMtime(DateTimeUtil.getNowDateTime());
        prjDao.update(prj);
    }
	@Override
	public List<Map<String, Object>> findLoanPrjByCondition(CfsPrj searchBean,
			Page pg) {
		return prjDao.findLoanPrjByCondition(searchBean,pg);
	}

	@Override
	public void updateCfsPrj(CfsPrj prj) {
		prj.setMtime(DateTimeUtil.getNowDateTime());
		prj.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
		prjDao.update(prj);
	}

	@Override
	public void genRepayPlanAutoTask() {
		List<CfsPrj> list = prjDao.findRepayPlanPrj();
		for(final CfsPrj prj :list){
			if(CfsConstant.PRJ_REPAY_WAY_A.equals(prj.getRepayWay())){
				UcarsHelper.asyncExecute(new Runnable() {
					@Override
					public void run() {
						genRepayWayA(prj);
					}
				});
			}else if(CfsConstant.PRJ_REPAY_WAY_B.equals(prj.getRepayWay())){
				UcarsHelper.asyncExecute(new Runnable() {
					@Override
					public void run() {
						genRepayWayB(prj);
					}
				});
			}else if(CfsConstant.PRJ_REPAY_WAY_C.equals(prj.getRepayWay())){
				UcarsHelper.asyncExecute(new Runnable() {
					@Override
					public void run() {
						genRepayWayC(prj);
					}
				});
			}else if(CfsConstant.PRJ_REPAY_WAY_D.equals(prj.getRepayWay())){
				UcarsHelper.asyncExecute(new Runnable() {
					@Override
					public void run() {
						genRepayWayD(prj);
					}
				});
			}
		}
	}
	
	/**
	 * 生成到期还本付息还款计划
	 * @author renzhuolun
	 * @date 2017年4月6日 下午12:53:59
	 * @param prj
	 */
	private void genRepayWayA(CfsPrj prj) {
		List<CfsPrjOrder> orders = prjOrderService.getPrjOrdersByPrjId(prj.getId());
		List<RepayPlanInfo> planInfos = new ArrayList<RepayPlanInfo>();
	    planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_PERIODS, prj.getEndBidTime(),0L));
		planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_NORMAL, DateTimeUtil.addDay(prj.getEndBidTime(), prj.getTimeLimitDay()),1L));
		List<CfsPrjOrderRepayPlan> orderPlans = genPrjOrderPlan(planInfos,prj,orders);
		genPrjPlan(planInfos,orderPlans,prj);
	}

	/**
	 *  生成按月付息到期还本还款计划
	 * @author renzhuolun
	 * @date 2017年4月6日 下午12:54:37
	 * @param prj
	 */
	private void genRepayWayB(CfsPrj prj) {
		//TODO  还没有实现
		Integer count = 0;
		if(UtilConstant.TIME_LIMIT_YEAR.equals(prj.getTimeLimitUnit())){
			count = prj.getTimeLimit()*12;
		}else{
			count = prj.getTimeLimit();
		}
		List<CfsPrjOrder> orders = prjOrderService.getPrjOrdersByPrjId(prj.getId());
		List<RepayPlanInfo> planInfos = new ArrayList<RepayPlanInfo>();
		planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_PERIODS, prj.getEndBidTime(),0L));
		if(prj.getEndBidTime().getTime()< DateTimeUtil.getMonth(new Date())){
			
		}
		planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_NORMAL, DateTimeUtil.addDay(prj.getEndBidTime(), prj.getTimeLimitDay()),1L));
		List<CfsPrjOrderRepayPlan> orderPlans = genPrjOrderPlan(planInfos,prj,orders);
		genPrjPlan(planInfos,orderPlans,prj);
	}
	
	/**
	 * 生成按季付息到期还本还款计划
	 * @author renzhuolun
	 * @date 2017年4月6日 下午12:55:09
	 * @param prj
	 */
	private void genRepayWayC(CfsPrj prj) {
		//TODO  还没有实现
		List<CfsPrjOrder> orders = prjOrderService.getPrjOrdersByPrjId(prj.getId());
		List<RepayPlanInfo> planInfos = new ArrayList<RepayPlanInfo>();
		planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_PERIODS, prj.getEndBidTime(),0L));
		planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_NORMAL, DateTimeUtil.addDay(prj.getEndBidTime(), prj.getTimeLimitDay()),1L));
		List<CfsPrjOrderRepayPlan> orderPlans = genPrjOrderPlan(planInfos,prj,orders);
		genPrjPlan(planInfos,orderPlans,prj);
	}
	
	/**
	 *  生成按半年付息到期还本还款计划
	 * @author renzhuolun
	 * @date 2017年4月6日 下午12:55:37
	 * @param prj
	 */
	private void genRepayWayD(CfsPrj prj) {
		//TODO  还没有实现
		List<CfsPrjOrder> orders = prjOrderService.getPrjOrdersByPrjId(prj.getId());
		List<RepayPlanInfo> planInfos = new ArrayList<RepayPlanInfo>();
		planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_PERIODS, prj.getEndBidTime(),0L));
		planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_NORMAL, DateTimeUtil.addDay(prj.getEndBidTime(), prj.getTimeLimitDay()),1L));
		List<CfsPrjOrderRepayPlan> orderPlans = genPrjOrderPlan(planInfos,prj,orders);
		genPrjPlan(planInfos,orderPlans,prj);
	}
	
	/**
	 * 生成订单的还款计划
	 * @author renzhuolun
	 * @date 2017年4月6日 下午4:15:14
	 * @param planInfos
	 * @param orders
	 * @return
	 */
	private List<CfsPrjOrderRepayPlan> genPrjOrderPlan(List<RepayPlanInfo> planInfos,CfsPrj prj, List<CfsPrjOrder> orders) {
		List<CfsPrjOrderRepayPlan> plans = new ArrayList<CfsPrjOrderRepayPlan>();
		for(RepayPlanInfo info :planInfos){
			for(CfsPrjOrder order :orders){
				BigDecimal yield = new BigDecimal(0);
				CfsPrjOrderRepayPlan orderPlan = new CfsPrjOrderRepayPlan();
				if(UtilConstant.PTYPE_PERIODS.equals(info.getPtype())){
					yield = CfsUtils.calcSumRealAmount(order.getMoney(), new BigDecimal(DateTimeUtil.getDaysBetween(order.getInvestTime(), info.getRepayDate())), prj.getPeriodRate());
					orderPlan.setPrincipal(new BigDecimal(0));
				}else{
					yield = CfsUtils.calcSumRealAmount(order.getMoney(), new BigDecimal(prj.getTimeLimitDay()), prj.getYearRate());
					orderPlan.setRepayDate(DateTimeUtil.addDay(prj.getEndBidTime(),prj.getTimeLimitDay()));
					orderPlan.setPrincipal(order.getMoney());
				}
				orderPlan.setRepayDate(info.getRepayDate());
				orderPlan.setPtype(info.getPtype());
				orderPlan.setPrjId(order.getPrjId());
				orderPlan.setPrjOrderId(order.getId());
				orderPlan.setRepayPeriods(info.getRepayPeriods());
				orderPlan.setPriInterest(yield.add(orderPlan.getPrincipal()));
				orderPlan.setYield(yield);
				orderPlan.setRestPrincipal(order.getMoney().subtract(orderPlan.getPrincipal()));
				orderPlan.setStatus(UtilConstant.REPAY_STATUS_1);
				orderPlan.setCtime(DateTimeUtil.getNowDateTime());
				orderPlan.setMtime(DateTimeUtil.getNowDateTime());
				plans.add(orderPlan);
			}
		}
		return plans;
	}
	
	private void genPrjPlan(List<RepayPlanInfo> planInfos, List<CfsPrjOrderRepayPlan> orderPlans, CfsPrj prj) {
		for(RepayPlanInfo info :planInfos){
			CfsPrjRepayPlan plan = new CfsPrjRepayPlan(prj.getId(),info.getRepayPeriods(),info.getRepayDate(),UtilConstant.DEFAULT_ZERO,
					UtilConstant.DEFAULT_ZERO,UtilConstant.DEFAULT_ZERO,UtilConstant.DEFAULT_ZERO,UtilConstant.REPAY_STATUS_1,
				DateTimeUtil.getNowDateTime(),DateTimeUtil.getNowDateTime());
			prjRepayPlan.addPrjRepayPlan(plan);
			for(CfsPrjOrderRepayPlan orderPlan:orderPlans){
				plan.setPriInterest(plan.getPriInterest().add(orderPlan.getPriInterest()));
				plan.setPrincipal(plan.getPrincipal().add(orderPlan.getPrincipal()));
				plan.setYield(plan.getYield().add(orderPlan.getYield()));
				plan.setRestPrincipal(plan.getRestPrincipal().add(orderPlan.getRestPrincipal()));
				orderPlan.setPrjRepayPlanId(plan.getId());
				prjOrderRepayPlan.addPrjOrderRepayPlan(orderPlan);
			}
			prjRepayPlan.updatePrjRepayPlan(plan);
		}
	}
}
