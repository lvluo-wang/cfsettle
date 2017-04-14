package com.upg.cfsettle.comm.core;

import java.math.BigDecimal;
import java.util.*;

import com.upg.cfsettle.cust.core.ICfsPrjOrderService;
import com.upg.cfsettle.mapping.prj.CfsCommOrderRelate;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjOrder;
import com.upg.cfsettle.prj.core.IPrjExtService;
import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.basesystem.security.core.user.IUserDAO;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.model.security.UserLogonInfo;
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
	@Autowired
	IUserDAO userDAO;

	@Override
	public List<CfsMyCommInfo> findByCondition(CfsMyCommInfo searchBean, Page page) {
		String hql = "from CfsMyCommInfo cfsMyCommInfo";
		QueryCondition condition = new QueryCondition(hql);
		if (searchBean != null) {
			String commSettleDateStr = searchBean.getCommSettleDateStr();
			if (!StringUtil.isEmpty(commSettleDateStr)) {
				String fromDateStr = commSettleDateStr+"-1 00:00:00";
				Date fromDate = DateTimeUtil.getStringToDate(fromDateStr);
				condition.addCondition(new ConditionBean("cfsMyCommInfo.commSettleDate", ConditionBean.MORE_AND_EQUAL, fromDate));
				Date toDate = DateTimeUtil.getLastDateOfMonth(fromDate);
				condition.addCondition(new ConditionBean("cfsMyCommInfo.commSettleDate", ConditionBean.LESS_AND_EQUAL, toDate));
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
			//Long sysid = searchBean.getSysid();
			UserLogonInfo logonInfo = SessionTool.getUserLogonInfo();
			if (logonInfo != null) {
				condition.addCondition(new ConditionBean("cfsMyCommInfo.sysid", ConditionBean.EQUAL, logonInfo.getSysUserId()));
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
		List<CfsPrj> prjList = prjService.findAllSucceedPrjLastMonth();
		if(!CollectionUtils.isEmpty(prjList)){
			for(CfsPrj prj : prjList){
				List<CfsPrjOrder> orderList = prjOrderService.getPrjOrdersByPrjId(prj.getId());
				for(CfsPrjOrder prjOrder : orderList){
					saveCommOrderRelateList(prj,prjOrder);
				}
			}
			List<Map<String, Object>> result = commOrderRelateDao.findTotalAmountGroupBySysId();
			Date now = DateTimeUtil.getNowDateTime();
			Date date = DateTimeUtil.getSpecifiedDayBefore(now);
			for(Map<String,Object> map :result){
				Long sysId = Long.valueOf(map.get("SYSID").toString());
				BigDecimal sumCommAmount = (BigDecimal) map.get("SUM_COMM_AMOUNT");
				CfsMyCommInfo myCommInfo = new CfsMyCommInfo();
				myCommInfo.setSysid(sysId);
				Buser buser = userDAO.get(sysId);
				myCommInfo.setSysUserName(buser.getUserName());
				myCommInfo.setPosCode(buser.getPosCode());
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


	private void saveCommOrderRelateList(CfsPrj prj, CfsPrjOrder prjOrder){
		List<CfsCommOrderRelate> cfsCommOrderRelateList = calculateCommOrderRelate(prj,prjOrder);
		if(!CollectionUtils.isEmpty(cfsCommOrderRelateList)){
		      commOrderRelateDao.saveOrUpdateAll(cfsCommOrderRelateList);
		}
	}

	private List<CfsCommOrderRelate> calculateCommOrderRelate(CfsPrj prj, CfsPrjOrder prjOrder){
		List<CfsCommOrderRelate> cfsCommOrderRelateList = new ArrayList<>();
		List<Buser> buserList = new ArrayList<>();
		BigDecimal areaRate = prj.getAreaRate() == null ? BigDecimal.ZERO :prj.getAreaRate();
		BigDecimal deptRate = prj.getDeptRate()== null ? BigDecimal.ZERO :prj.getDeptRate();
		BigDecimal teamRate = prj.getTeamRate()== null ? BigDecimal.ZERO :prj.getTeamRate();
		BigDecimal sysUserRate = prj.getSysuserRate() == null ? BigDecimal.ZERO :prj.getSysuserRate();
		BigDecimal amount = prjOrder.getMoney()== null ? BigDecimal.ZERO :prjOrder.getMoney();
		String posCode = prjOrder.getServiceSysType();
		BigDecimal commAmount = BigDecimal.ZERO;
		BigDecimal commRate = BigDecimal.ZERO;
		Buser sysUser = null;
		Buser teamUser = null;
		Buser deptUser = null;
		Buser areaUser = null;
		switch (posCode){
			case UtilConstant.CFS_CUST_MANAGER:
				 sysUser = userDAO.get(prjOrder.getServiceSysid());
				 teamUser = userDAO.getUserByTeamIdAndPosCode(prjOrder.getOwnedTeam(),UtilConstant.CFS_TEAM_MANAGER);
				 deptUser = userDAO.getUserByDeptIdAndPosCode(prjOrder.getOwnedDept(),UtilConstant.CFS_DEPT_MANAGER);
				 areaUser = userDAO.getUserByAreaIdAndPosCode(prjOrder.getOwnedArea(),UtilConstant.CFS_AREA_MANAGER);
				if(sysUser!=null) buserList.add(sysUser);
				if(teamUser!=null) buserList.add(teamUser);
				if(deptUser!=null) buserList.add(deptUser);
				if(areaUser!=null) buserList.add(areaUser);
				break;
			case UtilConstant.CFS_TEAM_MANAGER:
				 sysUser = userDAO.get(prjOrder.getServiceSysid());
				 deptUser = userDAO.getUserByDeptIdAndPosCode(prjOrder.getOwnedDept(),UtilConstant.CFS_DEPT_MANAGER);
				 areaUser = userDAO.getUserByAreaIdAndPosCode(prjOrder.getOwnedArea(),UtilConstant.CFS_AREA_MANAGER);
				if(sysUser!=null) buserList.add(sysUser);
				buserList.add(new Buser(prjOrder.getServiceSysid(),UtilConstant.CFS_CUST_MANAGER));
				if(deptUser!=null) buserList.add(deptUser);
				if(areaUser!=null) buserList.add(areaUser);
				break;
			case UtilConstant.CFS_DEPT_MANAGER:
				sysUser = userDAO.get(prjOrder.getServiceSysid());
				buserList.add(new Buser(prjOrder.getServiceSysid(),UtilConstant.CFS_CUST_MANAGER));
				areaUser = userDAO.getUserByAreaIdAndPosCode(prjOrder.getOwnedArea(),UtilConstant.CFS_AREA_MANAGER);
				if(sysUser!=null) buserList.add(sysUser);
				if(areaUser!=null) buserList.add(areaUser);
				break;
			case UtilConstant.CFS_AREA_MANAGER:
				sysUser = userDAO.get(prjOrder.getServiceSysid());
				buserList.add(new Buser(prjOrder.getServiceSysid(),UtilConstant.CFS_CUST_MANAGER));
				if(sysUser!=null) buserList.add(sysUser);
				break;
			default:
				break;
		}
		CfsCommOrderRelate cfsCommOrderRelate = null;
		for(Buser buser: buserList){
			if(!StringUtil.isEmpty(buser.getPosCode())){
				if(buser.getPosCode().equals(UtilConstant.CFS_CUST_MANAGER)){
					commRate = sysUserRate;
					commAmount = amount.multiply(commRate).setScale(2);
				}
			if(buser.getPosCode().equals(UtilConstant.CFS_TEAM_MANAGER)){
				commRate = teamRate;
				commAmount = amount.multiply(commRate).setScale(2);
			}
			if(buser.getPosCode().equals(UtilConstant.CFS_DEPT_MANAGER)){
				commRate = deptRate;
				commAmount = amount.multiply(commRate).setScale(2);
			}
			if(buser.getPosCode().equals(UtilConstant.CFS_AREA_MANAGER)){
				commRate = areaRate;
				commAmount = amount.multiply(commRate).setScale(2);
			}
			cfsCommOrderRelate = setValueCommOrderRelate(commAmount,commRate,buser.getUserId(),prj.getId(),prjOrder.getId());
			cfsCommOrderRelateList.add(cfsCommOrderRelate);
			}
		}
		return cfsCommOrderRelateList;
	}

	private CfsCommOrderRelate setValueCommOrderRelate(BigDecimal commAmount,BigDecimal commRate,Long sysId,Long prjId,Long prjOrderId){
		CfsCommOrderRelate cfsCommOrderRelate = new CfsCommOrderRelate();
		cfsCommOrderRelate.setCommAccount(commAmount);
		cfsCommOrderRelate.setSysid(sysId);
		cfsCommOrderRelate.setCommRate(commRate);
		cfsCommOrderRelate.setPrjId(prjId);
		cfsCommOrderRelate.setPrjOrderId(prjOrderId);
		cfsCommOrderRelate.setStatus(UtilConstant.COMM_STATUS_1);//待付款
		cfsCommOrderRelate.setCtime(DateTimeUtil.getNowDateTime());
		cfsCommOrderRelate.setMtime(DateTimeUtil.getNowDateTime());
		return cfsCommOrderRelate;
	}
}
