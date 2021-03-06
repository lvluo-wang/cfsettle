package com.upg.cfsettle.prj.core;

import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.upg.cfsettle.common.CodeItemUtil;
import com.upg.cfsettle.cust.core.ICfsPrjOrderDao;
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
import com.upg.ucars.tools.imp.excel.ExcelUtil;
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
	@Autowired
	private ICfsPrjOrderDao prjOrderDao;

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
        prj.setStatus(CfsConstant.PRJ_STATUS_AUDIT);
        prj.setRemainingAmount(prj.getDemandAmount());
		prj.setLoanedAmount(BigDecimal.ZERO);
		prj.setLoanTimes((long)0);
		prj.setPayBackAmount(BigDecimal.ZERO);
		prj.setPayBackTimes((long)0);
        dealPrjTimeLimitDay(prj);
        prjDao.save(prj);
        prjExt.setPrjId(prj.getId());
        prjExt.setCtime(DateTimeUtil.getNowDateTime());
        prjExt.setCsysid(SessionTool.getUserLogonInfo().getSysUserId());
        prjExtService.addPrjExt(prjExt);
    }

    private void dealPrjTimeLimitDay(CfsPrj prj) {
        if(prj.getTimeLimitUnit().equals("Y")){
            prj.setTimeLimitDay(prj.getTimeLimit() * 365);
        }
        if(prj.getTimeLimitUnit().equals("M")){
            prj.setTimeLimitDay(prj.getTimeLimit() * 30);
        }
    }

    @Override
    public CfsPrj getPrjById(Long id) {
        CfsPrj prj =  prjDao.get(id);
        //回款截止时间
       // getRepayEndDate(prj);
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
    public void updatePrjAndPrjExt(CfsPrj prj, CfsPrjExt prjExt) throws InvocationTargetException, IllegalAccessException {
        if(prj != null){
			CfsPrj updatePrj = prjDao.get(prj.getId());
			BeanUtils.copyNoNullProperties(updatePrj,prj);
			updatePrj.setMtime(DateTimeUtil.getNowDateTime());
			updatePrj.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
            prjDao.update(updatePrj);
        }
        if(prjExt != null){
			CfsPrjExt updatePrjExt = prjExtDao.get(prjExt.getPrjId());
			BeanUtils.copyNoNullProperties(updatePrjExt,prjExt);
			updatePrjExt.setCtime(DateTimeUtil.getNowDateTime());
			updatePrjExt.setCsysid(SessionTool.getUserLogonInfo().getSysUserId());
            prjExtDao.update(updatePrjExt);
        }
    }

    @Override
    public void auditPrjAndPrjExt(CfsPrj prj, CfsPrjExt prjExt) {
        CfsPrj auditPrj = prjDao.get(prj.getId());
        if(!(auditPrj.getStatus().equals(CfsConstant.PRJ_STATUS_AUDIT)
                || auditPrj.getStatus().equals(CfsConstant.PRJ_STATUS_REFUSE))){
            UcarsHelper.throwServiceException("项目已审核通过，不能再次审核");
        }
        try {
            BeanUtils.copyNoNullProperties(auditPrj,prj);
            //auditPrj.setStatus(CfsConstant.PRJ_STATUS_INVESTING);
            auditPrj.setMtime(DateTimeUtil.getNowDateTime());
            auditPrj.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
            prjDao.update(auditPrj);
            CfsPrjExt updateExt = prjExtDao.get(prj.getId());
			BeanUtils.copyNoNullProperties(updateExt,prjExt);
			updateExt.setCtime(DateTimeUtil.getNowDateTime());
			updateExt.setCsysid(SessionTool.getUserLogonInfo().getSysUserId());
            prjExtDao.update(updateExt);
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
//		prj.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
		prjDao.update(prj);
	}

	@Override
	public void genRepayPlanAutoTask() {
		List<CfsPrj> list = prjDao.findRepayPlanPrj();
		for(final CfsPrj prj :list){
			genPrjRepayPlan(prj);
		}
	}
	
	/**
	 * 生成到期还本付息还款计划
	 * @author renzhuolun
	 * @date 2017年4月6日 下午12:53:59
	 * @param prj
	 */
	private void genRepayWayA(CfsPrj prj) {
		List<CfsPrjOrder> orders = prjOrderService.getOKPrjOrdersByPrjId(prj.getId());
		Integer countMonth = 0;
		if(UtilConstant.TIME_LIMIT_YEAR.equals(prj.getTimeLimitUnit())){
			countMonth = prj.getTimeLimit()*12;
		}else{
			countMonth = prj.getTimeLimit();
		}
		List<RepayPlanInfo> planInfos = new ArrayList<RepayPlanInfo>();
	    planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_PERIODS,prj.getStartBidTime(), prj.getBuildTime(),0L));
		planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_NORMAL, prj.getBuildTime(),DateTimeUtil.addMonth(prj.getBuildTime(), countMonth),1L));
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
		Integer countMonth = 0;
		if(UtilConstant.TIME_LIMIT_YEAR.equals(prj.getTimeLimitUnit())){
			countMonth = prj.getTimeLimit()*12;
		}else{
			countMonth = prj.getTimeLimit();
		}
		List<CfsPrjOrder> orders = prjOrderService.getOKPrjOrdersByPrjId(prj.getId());
		List<RepayPlanInfo> planInfos = new ArrayList<RepayPlanInfo>();
		planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_PERIODS, prj.getStartBidTime(),prj.getBuildTime(),0L));
		Date firstRepayDate = null;
		if(prj.getBuildTime().getTime() < DateTimeUtil.getMixedDate(DateTimeUtil.getYear(prj.getBuildTime()), DateTimeUtil.getMonth(prj.getBuildTime()), 20).getTime()){
			firstRepayDate = DateTimeUtil.getMixedDate(DateTimeUtil.getYear(prj.getBuildTime()), DateTimeUtil.getMonth(prj.getBuildTime()), 20);
		}else{
			firstRepayDate = DateTimeUtil.getMixedDate(DateTimeUtil.getYear(prj.getBuildTime()), DateTimeUtil.getMonth(prj.getBuildTime())+1, 20);
		}
		Long  isFirst = 1L;//是否第一条正常还款
		while(firstRepayDate.getTime() <=DateTimeUtil.addMonth(prj.getBuildTime(), countMonth).getTime()){
			if(isFirst==1){
				planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_NORMAL, prj.getBuildTime(),firstRepayDate,isFirst));
				isFirst ++;
				continue;
			}
			if(DateTimeUtil.addMonth(firstRepayDate, 1).getTime() > DateTimeUtil.addMonth(prj.getBuildTime(), countMonth).getTime()){
				planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_NORMAL,firstRepayDate,DateTimeUtil.addMonth(prj.getBuildTime(), countMonth),isFirst));
			}else{
				planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_NORMAL, firstRepayDate,DateTimeUtil.addMonth(firstRepayDate, 1),isFirst));
			}
			isFirst++;
			firstRepayDate = DateTimeUtil.addMonth(firstRepayDate, 1);
		}
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
		List<CfsPrjOrder> orders = prjOrderService.getOKPrjOrdersByPrjId(prj.getId());
		List<RepayPlanInfo> planInfos = new ArrayList<RepayPlanInfo>();
		Integer countMonth = null;
		if(UtilConstant.TIME_LIMIT_YEAR.equals(prj.getTimeLimitUnit())){
			countMonth = prj.getTimeLimit()*12;
		}else{
			countMonth = prj.getTimeLimit();
		}
		planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_PERIODS, prj.getStartBidTime(),prj.getBuildTime(),0L));
		Date firstRepayDate = null;
		if(prj.getBuildTime().getTime() < DateTimeUtil.getMixedDate(DateTimeUtil.getYear(prj.getBuildTime()), 2, 20).getTime()){
			firstRepayDate = DateTimeUtil.getMixedDate(DateTimeUtil.getYear(prj.getBuildTime()), 2, 20);
		}else if(prj.getBuildTime().getTime() < DateTimeUtil.getMixedDate(DateTimeUtil.getYear(prj.getBuildTime()), 5, 20).getTime()){
			firstRepayDate = DateTimeUtil.getMixedDate(DateTimeUtil.getYear(prj.getBuildTime()), 5, 20);
		}else if(prj.getBuildTime().getTime() < DateTimeUtil.getMixedDate(DateTimeUtil.getYear(prj.getBuildTime()), 8, 20).getTime()){
			firstRepayDate = DateTimeUtil.getMixedDate(DateTimeUtil.getYear(prj.getBuildTime()), 8, 20);
		}else if(prj.getBuildTime().getTime() < DateTimeUtil.getMixedDate(DateTimeUtil.getYear(prj.getBuildTime()), 11, 20).getTime()){
			firstRepayDate = DateTimeUtil.getMixedDate(DateTimeUtil.getYear(prj.getBuildTime()), 11, 20);
		}else{
			firstRepayDate = DateTimeUtil.getMixedDate(DateTimeUtil.getYear(prj.getBuildTime())+1, 2, 20);
		}
		Long  isFirst = 1L;//是否第一条正常还款
		if(firstRepayDate.getTime() > DateTimeUtil.addMonth(prj.getBuildTime(), countMonth).getTime()){
			planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_NORMAL,prj.getBuildTime(),DateTimeUtil.addMonth(prj.getBuildTime(), countMonth),isFirst));
		}
		while(firstRepayDate.getTime() <=DateTimeUtil.addMonth(prj.getBuildTime(), countMonth).getTime()){
			if(isFirst==1){
				planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_NORMAL, prj.getBuildTime(),firstRepayDate,isFirst));
				isFirst ++;
				continue;
			}
			if(DateTimeUtil.addMonth(firstRepayDate, 3).getTime() > DateTimeUtil.addMonth(prj.getBuildTime(), countMonth).getTime()){
				planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_NORMAL,firstRepayDate,DateTimeUtil.addMonth(prj.getBuildTime(), countMonth),isFirst));
			}else{
				planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_NORMAL, firstRepayDate,DateTimeUtil.addMonth(firstRepayDate, 3),isFirst));
			}
			isFirst++;
			firstRepayDate = DateTimeUtil.addMonth(firstRepayDate, 3);
		}
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
		List<CfsPrjOrder> orders = prjOrderService.getOKPrjOrdersByPrjId(prj.getId());
		List<RepayPlanInfo> planInfos = new ArrayList<RepayPlanInfo>();
		Integer countMonth = null;
		if(UtilConstant.TIME_LIMIT_YEAR.equals(prj.getTimeLimitUnit())){
			countMonth = prj.getTimeLimit()*12;
		}else{
			countMonth = prj.getTimeLimit();
		}
		planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_PERIODS, prj.getStartBidTime(),prj.getBuildTime(),0L));
		Date firstRepayDate = null;
		if(prj.getBuildTime().getTime() < DateTimeUtil.getMixedDate(DateTimeUtil.getYear(prj.getBuildTime()), 5, 20).getTime()){
			firstRepayDate = DateTimeUtil.getMixedDate(DateTimeUtil.getYear(prj.getBuildTime()), 5, 20);
		}else if(prj.getBuildTime().getTime() < DateTimeUtil.getMixedDate(DateTimeUtil.getYear(prj.getBuildTime()), 11, 20).getTime()){
			firstRepayDate = DateTimeUtil.getMixedDate(DateTimeUtil.getYear(prj.getBuildTime()), 11, 20);
		}else{
			firstRepayDate = DateTimeUtil.getMixedDate(DateTimeUtil.getYear(prj.getBuildTime())+1, 5, 20);
		}
		Long  isFirst = 1L;//是否第一条正常还款
		if(firstRepayDate.getTime() > DateTimeUtil.addMonth(prj.getBuildTime(), countMonth).getTime()){
			planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_NORMAL,prj.getBuildTime(),DateTimeUtil.addMonth(prj.getBuildTime(), countMonth),isFirst));
		}
		while(firstRepayDate.getTime() <=DateTimeUtil.addMonth(prj.getBuildTime(), countMonth).getTime()){
			if(isFirst==1){
				planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_NORMAL, prj.getBuildTime(),firstRepayDate,isFirst));
				isFirst ++;
				continue;
			}
			if(DateTimeUtil.addMonth(firstRepayDate, 6).getTime() > DateTimeUtil.addMonth(prj.getBuildTime(), countMonth).getTime()){
				planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_NORMAL,firstRepayDate,DateTimeUtil.addMonth(prj.getBuildTime(), countMonth),isFirst));
			}else{
				planInfos.add(new RepayPlanInfo(UtilConstant.PTYPE_NORMAL, firstRepayDate,DateTimeUtil.addMonth(firstRepayDate, 6),isFirst));
			}
			isFirst++;
			firstRepayDate = DateTimeUtil.addMonth(firstRepayDate, 6);
		}
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
		int countPeriod = 0;
		for(RepayPlanInfo info :planInfos){
			countPeriod++;
			for(CfsPrjOrder order :orders){
				BigDecimal yield = new BigDecimal(0);
				CfsPrjOrderRepayPlan orderPlan = new CfsPrjOrderRepayPlan();
				if(UtilConstant.PTYPE_PERIODS.equals(info.getPtype())){
					orderPlan.setCountDay(Long.valueOf(DateTimeUtil.getDaysBetween(DateTimeUtil.getSpecifiedDayAfter(order.getInvestTime()), info.getRepayDate())));
					yield = CfsUtils.calcSumRealAmount(order.getMoney(), new BigDecimal(DateTimeUtil.getDaysBetween(DateTimeUtil.getSpecifiedDayAfter(order.getInvestTime()), info.getRepayDate())), prj.getPeriodRate());
					orderPlan.setPrincipal(new BigDecimal(0));
				}else{
					orderPlan.setCountDay(Long.valueOf(DateTimeUtil.getDaysBetween(info.getLastRepayDate(),info.getRepayDate())));
					yield = CfsUtils.calcSumRealAmount(order.getMoney(), new BigDecimal(DateTimeUtil.getDaysBetween(info.getLastRepayDate(),info.getRepayDate())), prj.getYearRate());
					orderPlan.setRepayDate(DateTimeUtil.addDay(prj.getBuildTime(),prj.getTimeLimitDay()));
					if(countPeriod ==planInfos.size() ){
						orderPlan.setPrincipal(order.getMoney());
					}else{
						orderPlan.setPrincipal(new BigDecimal(0));
					}
				}
				orderPlan.setRepayDate(info.getRepayDate());
				orderPlan.setPtype(info.getPtype());
				orderPlan.setPrjId(order.getPrjId());
				orderPlan.setPrjOrderId(order.getId());
				orderPlan.setRepayPeriods(info.getRepayPeriods());
				orderPlan.setPriInterest(yield.add(orderPlan.getPrincipal()));
				orderPlan.setYield(yield);
				orderPlan.setRestPrincipal(order.getMoney().subtract(orderPlan.getPrincipal()));
				if(orderPlan.getPriInterest().doubleValue() == 0){
					orderPlan.setStatus(UtilConstant.REPAY_STATUS_2);
				}else{
					orderPlan.setStatus(UtilConstant.REPAY_STATUS_1);
				}
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
				if(info.getRepayDate().equals(orderPlan.getRepayDate())){
					plan.setPriInterest(plan.getPriInterest().add(orderPlan.getPriInterest()));
					plan.setPrincipal(plan.getPrincipal().add(orderPlan.getPrincipal()));
					plan.setYield(plan.getYield().add(orderPlan.getYield()));
					plan.setRestPrincipal(plan.getRestPrincipal().add(orderPlan.getRestPrincipal()));
					orderPlan.setPrjRepayPlanId(plan.getId());
					plan.setCsysid(SessionTool.getUserLogonInfo().getSysUserId());
					prjOrderRepayPlan.addPrjOrderRepayPlan(orderPlan);
				}
			}
			plan.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
			if(plan.getPriInterest().longValue() ==0l){
				plan.setStatus(UtilConstant.REPAY_STATUS_2);
			}
			plan.setMtime(DateTimeUtil.getNowDateTime());
			prjRepayPlan.updatePrjRepayPlan(plan);
		}
		prj.setLastRepayTime(planInfos.get(0).getRepayDate());
		this.updateCfsPrj(prj);
	}

	@Override
	public List<CfsPrj> findAllSucceedPrjLastMonth(){
		return prjDao.findAllSucceedPrjLastMonth();
	}

	@Override
	public void buildPrj(CfsPrj prj) {
		CfsPrj updatePrj = prjDao.get(prj.getId());
		if(!updatePrj.getStatus().equals(CfsConstant.PRJ_STATUS_INVESTING)){
			UcarsHelper.throwServiceException("该状态不允许成立项目");
		}
		BigDecimal raiseAmount = updatePrj.getDemandAmount().subtract(updatePrj.getRemainingAmount());
		if(raiseAmount.compareTo(updatePrj.getMinLoanAmount()) < 0){
			UcarsHelper.throwServiceException("该项目实际募集金额小于项目成立金额");
		}
		updatePrj.setBuildTime(prj.getBuildTime());
		//成立时间
		Date buildTime = updatePrj.getBuildTime();
		//融资开始时间
		Date startBidTime = updatePrj.getStartBidTime();
		if(DateTimeUtil.compareDate(buildTime,startBidTime) != 1){
			UcarsHelper.throwServiceException("项目成立时间不能小于融资开始时间");
		}
		List<CfsPrjOrder> prjOrderList = prjOrderDao.getPrjOrdersByPrjIdDesc(updatePrj.getId());
		if(!CollectionUtils.isEmpty(prjOrderList)){
			for(CfsPrjOrder prjOrder : prjOrderList){
				if(prjOrder.getStatus().equals(CfsConstant.PRJ_ORDER_STATUS_AUDIT)
						|| prjOrder.getStatus().equals(CfsConstant.PRJ_ORDER_STATUS_PAY)
						|| prjOrder.getServiceSysid().equals(CfsConstant.PRJ_ORDER_STATUS_REJECT)){
					UcarsHelper.throwServiceException("存在未处理完成订单");
				}
			}
			Date lastInvestTime = prjOrderList.get(0).getInvestTime();
			if(DateTimeUtil.compareDate(buildTime,lastInvestTime) != 1){
				UcarsHelper.throwServiceException("项目成立时间不能小于最后一笔订单的投资时间");
			}
		}else{
			UcarsHelper.throwServiceException("项目订单不存在");
		}
		updatePrj.setMtime(DateTimeUtil.getNowDateTime());
		updatePrj.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
		updatePrj.setStatus(CfsConstant.PRJ_STATUS_TO_LOAN);
		prjDao.update(updatePrj);
		genPrjRepayPlan(updatePrj);
	}

	@Override
	public void genPrjRepayPlan(final CfsPrj prj) {
		List<CfsPrjRepayPlan> list = prjRepayPlan.getPrjPlanByPrjId(prj.getId());
		if(CollectionUtils.isEmpty(list)){
			if(CfsConstant.PRJ_REPAY_WAY_A.equals(prj.getRepayWay())){
//				UcarsHelper.asyncExecute(new Runnable() {
//					@Override
//					public void run() {
//						genRepayWayA(prj);
//					}
//				});
				try {
					genRepayWayA(prj);
				} catch (Exception e) {
					UcarsHelper.throwServiceException("项目成立出错,请联系管理员");
				}
				
			}else if(CfsConstant.PRJ_REPAY_WAY_B.equals(prj.getRepayWay())){
//				UcarsHelper.asyncExecute(new Runnable() {
//					@Override
//					public void run() {
//						genRepayWayB(prj);
//					}
//				});
				try {
					genRepayWayB(prj);
				} catch (Exception e) {
					UcarsHelper.throwServiceException("项目成立出错,请联系管理员");
				}
			}else if(CfsConstant.PRJ_REPAY_WAY_C.equals(prj.getRepayWay())){
//				UcarsHelper.asyncExecute(new Runnable() {
//					@Override
//					public void run() {
//						genRepayWayC(prj);
//					}
//				});
				try {
					genRepayWayC(prj);
				} catch (Exception e) {
					UcarsHelper.throwServiceException("项目成立出错,请联系管理员");
				}
			}else if(CfsConstant.PRJ_REPAY_WAY_D.equals(prj.getRepayWay())){
//				UcarsHelper.asyncExecute(new Runnable() {
//					@Override
//					public void run() {
//						genRepayWayD(prj);
//					}
//				});
				try {
					genRepayWayD(prj);
				} catch (Exception e) {
					UcarsHelper.throwServiceException("项目成立出错,请联系管理员");
				}
			}
		}
	}

	@Override
	public HSSFWorkbook generatLoanData(OutputStream os, CfsPrj searchBean, String[] headers, String title, Page page) {
		List<List<Object>> dataRows = new ArrayList<List<Object>>();
		List<Map<String, Object>> dataList = this.findLoanPrjByCondition(searchBean, null);
		Iterator<Map<String, Object>> it = dataList.iterator();
		while(it.hasNext()){
			List<Object> row = new ArrayList<Object>();
			Map<String, Object> exportData = it.next();
			row.add(exportData.get("PRJ_NAME"));
			row.add(exportData.get("DEMAND_AMOUNT"));
			row.add(new BigDecimal(exportData.get("DEMAND_AMOUNT").toString()).subtract(new BigDecimal(exportData.get("REMAINING_AMOUNT").toString())));
			row.add(exportData.get("TIME_LIMIT")+CodeItemUtil.getCodeNameByKey(UtilConstant.CFS_TIMELIMIT_UNIT,exportData.get("TIME_LIMIT_UNIT").toString()));
			row.add(exportData.get("YEAR_RATE")+"%");
			row.add(exportData.get("LAST_REPAY_TIME")==null?"":DateTimeUtil.getFormatDate(DateTimeUtil.getSecondToDate(Integer.valueOf(exportData.get("LAST_REPAY_TIME").toString())), "yyyy-MM-dd"));
			row.add(CodeItemUtil.getCodeNameByKey(UtilConstant.CFS_BANK_TYPE,exportData.get("TENANT_BANK").toString()));
			row.add(exportData.get("SUB_BANK"));
			row.add(exportData.get("ACCOUNT_NO"));
			row.add(exportData.get("LOANED_AMOUNT"));
			row.add(exportData.get("LOANING_AMOUNT"));
			row.add(CodeItemUtil.getCodeNameByKey(UtilConstant.CFS_PRJ_STATUS,exportData.get("STATUS").toString()));
			dataRows.add(row);
		}
 		return ExcelUtil.createHSSFWorkbook(title, headers, dataRows);
	}

	@Override
	public void prjFailedAutoTask() {
		List<CfsPrj> prjs = prjDao.findFailedPrj();
		for(CfsPrj prj:prjs){
			List<CfsPrjOrder> orders = prjOrderDao.getPrjOrdersByPrjId(prj.getId());
			for(CfsPrjOrder order :orders){
				order.setStatus(CfsConstant.PRJ_ORDER_STATUS_FAILED);
				order.setRemark(DateTimeUtil.getCurDateTime()+"订单自动流标");
			}
			prjOrderDao.saveOrUpdateAll(orders);
			prj.setRemark(DateTimeUtil.getCurDateTime()+"项目自动流标");
			prj.setStatus(CfsConstant.PRJ_STATUS_FAILED);
		}
		prjDao.saveOrUpdateAll(prjs);
	}
}
