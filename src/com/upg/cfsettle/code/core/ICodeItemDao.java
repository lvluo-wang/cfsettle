package com.upg.cfsettle.code.core;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;


public interface ICodeItemDao extends IBaseDAO<FiCodeItem, Long> {

	/*
	 * 分页查询功能
	 */
	public List<FiCodeItem> findAllByPage(Page page);
	
	public List<FiCodeItem> getCodeItemByKey(String key);

	public String getCodeItemNameByKey(String codeKey, String codeNo);
	
}
