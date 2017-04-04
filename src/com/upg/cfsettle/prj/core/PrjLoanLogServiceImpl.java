package com.upg.cfsettle.prj.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjLoanLog;
import com.upg.ucars.basesystem.UcarsHelper;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.SessionTool;
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
		loanLog.setLoanTimes(CollectionUtils.isEmpty(list)?1:list.size()+1L);
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
}
