package com.upg.cfsettle.code.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;

@Service
public class CodeItemServiceImpl implements ICodeItemService {
	@Autowired
	private ICodeItemDao codeItemDao;
	

	@Override
	public void addCodeItem(FiCodeItem codeItem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void editCodeItem(FiCodeItem codeItem) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCodeItem(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public FiCodeItem getCodeItemById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FiCodeItem> getCodeItemByKey(String key) {
		return codeItemDao.getCodeItemByKey(key);
	}

	@Override
	public void deleteByList(List<Long> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<FiCodeItem> findAllByPage(Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setCodeItemDao(ICodeItemDao codeItemDao) {
		this.codeItemDao = codeItemDao;
	}

	public String getCodeItemNameByKey(String codeKey, String codeNo) {
		// TODO Auto-generated method stub
		return codeItemDao.getCodeItemNameByKey(codeKey,codeNo);
	}

	public ICodeItemDao getCodeItemDao() {
		return codeItemDao;
	}
	
}
