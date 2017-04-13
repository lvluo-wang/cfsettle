package com.upg.cfsettle.comm.core;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.upg.cfsettle.cust.core.ICfsPrjOrderService;
import com.upg.cfsettle.mapping.prj.CfsCommOrderRelate;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjOrder;
import com.upg.cfsettle.prj.core.IPrjExtService;
import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.mapping.prj.CfsCommDetail;
import com.upg.cfsettle.mapping.prj.CfsMyCommInfo;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.StringUtil;
import org.springframework.util.CollectionUtils;

@Service
public class CfsMyCommInfoServiceImpl implements ICfsMyCommInfoService{
	
	@Autowired
	private ICfsMyCommInfoDao cfsMyCommInfoDao;
	@Autowired
	private IPrjService prjService;
	@Autowired
	private IPrjExtService prjExtService;
	@Autowired
	ICfsPrjOrderService prjOrderService;
	@Autowired
	ICfsCommOrderRelateDao commOrderRelateDao;

	@Override
	public List<CfsMyCommInfo> findByCondition(CfsMyCommInfo searchBean, Page page) {
		String hql = "from CfsMyCommInfo cfsMyCommInfo";
		QueryCondition condition = new QueryCondition(hql);
		if (searchBean != null) {
			Date commSettleDate = searchBean.getCommSettleDate();
			if (commSettleDate != null) {
				condition.addCondition(new ConditionBean("cfsMyCommInfo.commSettleDate", ConditionBean.EQUAL, commSettleDate));
			}
			Byte payStatus = searchBean.getPayStatus();
			if (payStatus != null) {
				condition.addCondition(new ConditionBean("cfsMyCommInfo.payStatus", ConditionBean.EQUAL, payStatus));
			}
			String sysUserName = searchBean.getSysUserName();
			if (StringUtil.isEmpty(sysUserName)) {
				condition.addCondition(new ConditionBean("cfsMyCommInfo.sysUserName", ConditionBean.LIKE, sysUserName));
			}
			String posCode = searchBean.getPosCode();
			if (StringUtil.isEmpty(posCode)) {
				condition.addCondition(new ConditionBean("cfsMyCommInfo.posCode", ConditionBean.EQUAL, posCode));
			}
			String mobile = searchBean.getMobile();
			if (StringUtil.isEmpty(mobile)) {
				condition.addCondition(new ConditionBean("cfsMyCommInfo.mobile", ConditionBean.LIKE, mobile));
			}
			Long sysid = searchBean.getSysid();
			if (sysid != null) {
				condition.addCondition(new ConditionBean("cfsMyCommInfo.sysid", ConditionBean.EQUAL, sysid));
			}
		}
		return cfsMyCommInfoDao.queryEntity( condition.getConditionList(), page);
	}

	@Override
	public CfsMyCommInfo queryCfsMyCommInfoById(Long id) {
		return cfsMyCommInfoDao.get(id);
	}

	@Override
	public void updateCfsMyCommInfo(CfsMyCommInfo myCommInfo) {
		cfsMyCommInfoDao.update(myCommInfo);
	}

	@Override
	public void addCfsMyCommInfo(CfsMyCommInfo myCommInfo) {
		cfsMyCommInfoDao.save(myCommInfo);
	}

	@Override
	public void deleteById(Long pkId) {
		cfsMyCommInfoDao.delete(pkId);
	}

	@Override
	public List<CfsCommDetail> findByCommDetail(CfsMyCommInfo searchBean, Page page) {
		return cfsMyCommInfoDao.findByCommDetail(searchBean,page);
	}

	@Override
	public synchronized  void runCommTask() {
		//计算上一个月的佣金
		List<CfsPrj> prjList = prjService.findAllSuccedPrjLastMonth();
		if(!CollectionUtils.isEmpty(prjList)){
			for(CfsPrj prj : prjList){
				List<CfsPrjOrder> orderList = prjOrderService.getPrjOrdersByPrjId(prj.getId());
				for(CfsPrjOrder prjOrder : orderList){
					getCommAmount(prj,prjOrder);
				}
			}
			List<Map<String, Object>> result = commOrderRelateDao.findTotalAmountGroupBySysId();
			Date now = DateTimeUtil.getNowDateTime();
			Date date = DateTimeUtil.getSpecifiedDayBefore(now);
			for(Map<String,Object> map :result){
				Long sysId = Long.valueOf(map.get("sys_id").toString());
				BigDecimal sumCommAmount = (BigDecimal) map.get("sumCommAmount");
				CfsMyCommInfo myCommInfo = new CfsMyCommInfo();
				myCommInfo.setSysid(sysId);
				myCommInfo.setCommMoney(sumCommAmount);
				myCommInfo.setCommSettleDate(date);//页面上显示到月
				myCommInfo.setCtime(now);
				myCommInfo.setMtime(now);
				myCommInfo.setPayStatus(UtilConstant.COMM_STATUS_1);
				cfsMyCommInfoDao.save(myCommInfo);
				List<CfsCommOrderRelate> list = commOrderRelateDao.findBySysIdAndCommIdIsNull(sysId);
				for(CfsCommOrderRelate orderRelate: list){
					orderRelate.setCommId(myCommInfo.getId());
					commOrderRelateDao.update(orderRelate);
				}
			}
		}

	}


	private void getCommAmount(CfsPrj prj, CfsPrjOrder prjOrder){
		BigDecimal areaRate = prj.getAreaRate();
		BigDecimal deptRate = prj.getDeptRate();
		BigDecimal teamRate = prj.getTeamRate();
		BigDecimal sysUserRate = prj.getSysuserRate();
		BigDecimal amount = prjOrder.getMoney();
		String posCode = prjOrder.getServiceSysType();
		BigDecimal commAmount = BigDecimal.ZERO;
		BigDecimal commRate = BigDecimal.ZERO;
		switch (posCode){
			case UtilConstant.CFS_CUST_MANAGER:
				commRate = sysUserRate;
				commAmount = amount.multiply(sysUserRate).setScale(2);
					break;
			case UtilConstant.CFS_TEAM_MANAGER:
				commRate = teamRate;
				commAmount = amount.multiply(teamRate).setScale(2);
				break;
			case UtilConstant.CFS_DEPT_MANAGER:
				commRate = deptRate;
				commAmount = amount.multiply(deptRate).setScale(2);
				break;
			case UtilConstant.CFS_AREA_MANAGER:
				commRate = areaRate;
				commAmount = amount.multiply(areaRate).setScale(2);
				break;
			default:
				break;
		}
		CfsCommOrderRelate cfsCommOrderRelate = new CfsCommOrderRelate();
		cfsCommOrderRelate.setCommAccount(commAmount);
		cfsCommOrderRelate.setSysid(prjOrder.getServiceSysid());
		cfsCommOrderRelate.setCommRate(commRate);
		cfsCommOrderRelate.setPrjId(prj.getId());
		cfsCommOrderRelate.setPrjOrderId(prjOrder.getId());
		cfsCommOrderRelate.setStatus(UtilConstant.COMM_STATUS_1);//待付款
		cfsCommOrderRelate.setCtime(DateTimeUtil.getNowDateTime());
		cfsCommOrderRelate.setMtime(DateTimeUtil.getNowDateTime());
		commOrderRelateDao.save(cfsCommOrderRelate);
	}
}
