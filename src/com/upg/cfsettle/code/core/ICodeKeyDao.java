package com.upg.cfsettle.code.core;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.cfsettle.mapping.ficode.FiCodeKey;


public interface ICodeKeyDao extends IBaseDAO<FiCodeKey, Long> {

	/*
	 * 分页查询功能
	 */
	public List<FiCodeKey> findAllByPage(Page page);
}
