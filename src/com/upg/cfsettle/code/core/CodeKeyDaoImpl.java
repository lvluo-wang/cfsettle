package com.upg.cfsettle.code.core;

import java.util.List;

import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SysBaseDao;
import com.upg.cfsettle.mapping.ficode.FiCodeKey;

@Dao
public class CodeKeyDaoImpl extends SysBaseDao<FiCodeKey, Long> implements ICodeKeyDao  {


	@Override
	public List<FiCodeKey> findAllByPage(Page page) {
		String hql="FROM FiCodeKey";
		return this.queryByParam(hql.toString(), null, page);
	}

}
