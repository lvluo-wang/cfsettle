package com.upg.cfsettle.prj.core;

import java.util.List;

import com.upg.cfsettle.mapping.prj.CfsPrjPaybackLog;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.SysBaseDao;

@Dao
public class PrjPaybackLogDaoImpl extends SysBaseDao<CfsPrjPaybackLog,Long> implements IPrjPaybackLogDao {

	@Override
	public List<CfsPrjPaybackLog> getByPrjId(Long prjId) {
		String hql = "from CfsPrjPaybackLog where prjId=? order by loanTimes desc";
		return this.find(hql, prjId);
	}
}
