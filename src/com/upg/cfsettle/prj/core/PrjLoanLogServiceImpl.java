package com.upg.cfsettle.prj.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjLoanLog;
import com.upg.cfsettle.util.CfsConstant;
import com.upg.ucars.basesystem.UcarsHelper;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;
import com.upg.ucars.util.DateTimeUtil;

@Service
public class PrjLoanLogServiceImpl implements IPrjLoanLogService {
	
	@Autowired
	private IPrjLoanLogDao prjLoanLogDao;
	
	@Autowired
	private IPrjService prjService;

	@Override
	public void addPrjLoanLog(CfsPrjLoanLog loanLog) {
		CfsPrj prj = prjService.getPrjById(loanLog.getPrjId());
		prj.setLoanedAmount(prj.getLoanedAmount().add(loanLog.getLoanAmount()));
		if(prj.getLoanedAmount().compareTo(prj.getDemandAmount().subtract(prj.getRemainingAmount()))>0){
			UcarsHelper.throwServiceException("可放款额度超过项目可放宽的余额,请重新填写放款金额");
		}
		List<CfsPrjLoanLog> list = prjLoanLogDao.getByPrjId(loanLog.getPrjId());
		if(CollectionUtils.isEmpty(list)){
			loanLog.setLoanTimes(1L);
			if(loanLog.getLoanTime().getTime() < prj.getBuildTime().getTime()){
				UcarsHelper.throwServiceException("放款时间必须大于项目的成立时间");
			}
		}else{
			CfsPrjLoanLog log = list.get(0);
			if(loanLog.getLoanTime().getTime() < log.getLoanTime().getTime()){
				UcarsHelper.throwServiceException("放款时间必须大于项目的最后一次放款时间");
			}
		}
		if(prj.getLoanedAmount().compareTo(prj.getDemandAmount().subtract(prj.getRemainingAmount()))==0){
			prj.setStatus(CfsConstant.PRJ_STATUS_TO_PAYBACK);
		}
		loanLog.setRemark("员工"+SessionTool.getUserLogonInfo().getUserName()+"在"+DateTimeUtil.getCurDateTime()+"对项目"+loanLog.getPrjName()+"放款"+loanLog.getLoanAmount()+"万");
		prjService.updateCfsPrj(prj);
		loanLog.setCtime(DateTimeUtil.getNowDateTime());
		loanLog.setCsysid(SessionTool.getUserLogonInfo().getSysUserId());
		loanLog.setMtime(DateTimeUtil.getNowDateTime());
		loanLog.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
		prjLoanLogDao.save(loanLog);
	}

	@Override
	public CfsPrjLoanLog getCfsPrjLoanLogById(Long id) {
		return prjLoanLogDao.get(id);
	}

	@Override
	public List<CfsPrjLoanLog> findByCondition(CfsPrjLoanLog searchBean,Page page) {
		String hql = "from CfsPrjLoanLog cfsPrjLoanLog";
		QueryCondition condition = new QueryCondition(hql);
		if (searchBean != null) {
			Long prjId = searchBean.getPrjId();
			if (prjId != null) {
				condition.addCondition(new ConditionBean("cfsPrjLoanLog.prjId", ConditionBean.EQUAL, prjId));
			}
		}
		List<OrderBean> orderList = new ArrayList<OrderBean>();
		orderList.add(new OrderBean("cfsPrjLoanLog.loanTimes", false));
		return prjLoanLogDao.queryEntity( condition.getConditionList(),orderList, page);
	}
}
