package com.upg.cfsettle.prj.core;

import java.util.List;

import com.upg.cfsettle.mapping.prj.CfsPrjLoanLog;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.SysBaseDao;

@Dao
public class PrjLoanLogDaoImpl extends SysBaseDao<CfsPrjLoanLog,Long> implements IPrjLoanLogDao {

	@Override
	public List<CfsPrjLoanLog> getByPrjId(Long prjId) {
		String hql = "from CfsPrjLoanLog where prjId=?";
		return this.find(hql, prjId);
	}
}
