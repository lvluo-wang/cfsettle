package com.upg.cfsettle.prj.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjPaybackLog;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.DateTimeUtil;

@Service
public class PrjPaybackLogServiceImpl implements IPrjPaybackLogService {
	
	@Autowired
	private IPrjPaybackLogDao paybackLogDao;
	
	@Autowired
	private IPrjService prjService;

	@Override
	public void addPrjPayBackLog(CfsPrjPaybackLog payBackLog) {
		CfsPrj prj = prjService.getPrjById(payBackLog.getPrjId());
		prjService.updateCfsPrj(prj);
		payBackLog.setCtime(DateTimeUtil.getNowDateTime());
		payBackLog.setCsysid(SessionTool.getUserLogonInfo().getSysUserId());
		payBackLog.setMtime(DateTimeUtil.getNowDateTime());
		payBackLog.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
		paybackLogDao.save(payBackLog);
	}

	@Override
	public List<CfsPrjPaybackLog> findByCondition(CfsPrjPaybackLog searchBean,Page page) {
		String hql = "from CfsPrjPaybackLog cfsPrjPaybackLog";
		QueryCondition condition = new QueryCondition(hql);
		if (searchBean != null) {
			Long prjId = searchBean.getPrjId();
			if (prjId != null) {
				condition.addCondition(new ConditionBean("cfsPrjPaybackLog.prjId", ConditionBean.EQUAL, prjId));
			}
		}
		return paybackLogDao.queryEntity( condition.getConditionList(), page);
	}

	@Override
	public CfsPrjPaybackLog getCfsPrjPaybackLogById(Long id) {
		return paybackLogDao.get(id);
	}
}
