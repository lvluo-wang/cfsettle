package com.upg.ucars.basesystem.security.core.userlog;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.UserActivityLog;

public interface IUserLogDAO extends IBaseDAO<UserActivityLog, Long> {
	/**
	 * 分页查询
	 * @param log	查询bean
	 * @param page	分页信息
	 * @return
	 */
	public List<UserActivityLog> findUserLogByPage(UserActivityLog log,Branch branch, Page page);

}
