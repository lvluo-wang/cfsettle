package com.upg.cfsettle.code.core;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SysBaseDao;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;

@Dao
public class CodeItemDaoImpl extends SysBaseDao<FiCodeItem, Long> implements ICodeItemDao  {

	@Override
	public List<FiCodeItem> findAllByPage(Page page) {
		String hql="FROM FiCodeItem";
		return this.queryByParam(hql.toString(), null, page);
	}

	@Override
	public List<FiCodeItem> getCodeItemByKey(String key) {
		String hql="FROM FiCodeItem where codeKey=? ";
		return super.find(hql, new Object[]{key});
	}

	@Override
	public String getCodeItemNameByKey(String codeKey, String codeNo) {
		String hql="FROM FiCodeItem where codeKey=? and codeNo=?";
		List<FiCodeItem> items=super.find(hql, new Object[]{codeKey,codeNo});
		if(!items.isEmpty()){
			FiCodeItem fiCodeItem=items.get(0);
			if(fiCodeItem!=null&&StringUtils.isNotEmpty(fiCodeItem.getCodeName())){
				return fiCodeItem.getCodeName();
			}else{
				return "";
			}
		}else{
			return "";
		}
	}

}
