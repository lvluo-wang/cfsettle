package com.upg.cfsettle.code.core;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.cfsettle.mapping.ficode.FiCodeKey;

public interface ICodeKeyService extends IBaseService {
	public void addCodeKey(FiCodeKey codeKey);
	public void editCodeKey(FiCodeKey codeKey);
	public void deleteCodeKey(Long id);
	public void deleteByList(List<Long> ids);
	public List<FiCodeKey> findAllByPage(Page page);
	public FiCodeKey getCodeKeyById(Long id);
}
