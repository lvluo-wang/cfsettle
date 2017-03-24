package com.upg.ucars.basesystem.security.core.role;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.security.Role;

public interface IRoleDAO extends IBaseDAO<Role, Long> {
	/**
	 * 根据机构编号查询角色
	 * 
	 * @param brchId
	 * @return
	 * @throws DAOException
	 */
	public List<Role> getRolesByBrchId(Long brchId);

	/**
	 * 按条件查询
	 * 
	 */
	public <T> List<T> queryByCondition(QueryCondition qc, Page page);

}
