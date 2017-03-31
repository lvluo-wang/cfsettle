package com.upg.cfsettle.cust.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.mapping.prj.CfsCust;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.StringUtil;

@Service
public class CfsCustServiceImpl implements ICfsCustService{
	
	@Autowired
	private ICfsCustDao cfsCustDao;

	@Override
	public List<CfsCust> findByCondition(CfsCust searchBean, Page page) {
		String hql = "from CfsCust cfsCust";
		QueryCondition condition = new QueryCondition(hql);
		if (searchBean != null) {
			String realName = searchBean.getRealName();
			if (!StringUtil.isEmpty(realName) || realName != null) {
				condition.addCondition(new ConditionBean("cfsCust.title", ConditionBean.LIKE, realName));
			}
			String idCard = searchBean.getIdCard();
			if (!StringUtil.isEmpty(idCard) || idCard != null) {
				condition.addCondition(new ConditionBean("cfsCust.idCard", ConditionBean.LIKE, idCard));
			}
			String mobile = searchBean.getMobile();
			if (!StringUtil.isEmpty(mobile) || idCard != null) {
				condition.addCondition(new ConditionBean("cfsCust.mobile", ConditionBean.LIKE, mobile));
			}
		}
		return cfsCustDao.queryEntity( condition.getConditionList(), page);
	}

	@Override
	public CfsCust queryCfsCustById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateCfsCust(CfsCust banner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCfsCust(CfsCust banner) {
		// TODO Auto-generated method stub
		
	}
	
}
