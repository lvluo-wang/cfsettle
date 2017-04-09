package com.upg.cfsettle.prj.core;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjPaybackLog;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.StringUtil;

@Service
public class PrjPaybackLogServiceImpl implements IPrjPaybackLogService {
	
	@Autowired
	private IPrjPaybackLogDao paybackLogDao;
	
	@Autowired
	private IPrjService prjService;
	@Autowired
	private IUserService userService;

	@Override
	public void addPrjPayBackLog(CfsPrjPaybackLog payBackLog) {
		CfsPrj prj = prjService.getPrjById(payBackLog.getPrjId());
		prj.setPayBackAmount(prj.getPayBackAmount().add(payBackLog.getPaybackAmount()));
		prj.setPayBackTimes(prj.getPayBackTimes()+1L);
		prjService.updateCfsPrj(prj);
		payBackLog.setCtime(DateTimeUtil.getNowDateTime());
		payBackLog.setCsysid(SessionTool.getUserLogonInfo().getSysUserId());
		payBackLog.setMtime(DateTimeUtil.getNowDateTime());
		payBackLog.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
		paybackLogDao.save(payBackLog);
	}

	@Override
	public List<Map<String,Object>> findByCondition(CfsPrjPaybackLog searchBean,Page page) {
		List<Map<String,Object>> list  = paybackLogDao.findByCondition(searchBean, page);
		for(Map<String,Object> map:list){
			map.put("sysUserName", map.get("CSYSID")==null?"":userService.getUserById(Long.valueOf(map.get("CSYSID").toString())).getUserName());
		}
		return list;
	}

	@Override
	public CfsPrjPaybackLog getCfsPrjPaybackLogById(Long id) {
		return paybackLogDao.get(id);
	}
}
