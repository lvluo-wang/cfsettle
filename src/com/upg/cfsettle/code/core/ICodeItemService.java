package com.upg.cfsettle.code.core;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;

public interface ICodeItemService extends IBaseService {
	public void addCodeItem(FiCodeItem codeItem);
	public void editCodeItem(FiCodeItem codeItem);
	public void deleteCodeItem(Long id);
	public void deleteByList(List<Long> ids);
	public List<FiCodeItem> findAllByPage(Page page);
	public FiCodeItem getCodeItemById(Long id);
	public List<FiCodeItem> getCodeItemByKey(String key);
	public String getCodeItemNameByKey(String codeKey,String codeNo);
}
