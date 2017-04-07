package com.upg.cfsettle.cust.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.mapping.prj.CfsCust;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.DateTimeUtil;
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
				condition.addCondition(new ConditionBean("cfsCust.realName", ConditionBean.LIKE, realName));
			}
			String idCard = searchBean.getIdCard();
			if (!StringUtil.isEmpty(idCard) || idCard != null) {
				condition.addCondition(new ConditionBean("cfsCust.idCard", ConditionBean.LIKE, idCard));
			}
			String mobile = searchBean.getMobile();
			if (!StringUtil.isEmpty(mobile) || idCard != null) {
				condition.addCondition(new ConditionBean("cfsCust.mobile", ConditionBean.LIKE, mobile));
			}
			Byte isValid = searchBean.getIsValid();
			if (isValid != null) {
				condition.addCondition(new ConditionBean("cfsCust.isValid", ConditionBean.EQUAL, isValid));
			}
		}
		return cfsCustDao.queryEntity( condition.getConditionList(), page);
	}

	@Override
	public CfsCust queryCfsCustById(Long id) {
		return cfsCustDao.get(id);
	}

	@Override
	public void updateCfsCust(CfsCust cust) {
		cust.setMtime(DateTimeUtil.getNowDateTime());
		cust.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
		cfsCustDao.update(cust);
	}

	@Override
	public void addCfsCust(CfsCust cust) {
		cust.setCtime(DateTimeUtil.getNowDateTime());
		cust.setCsysid(SessionTool.getUserLogonInfo().getSysUserId());
		cust.setMtime(DateTimeUtil.getNowDateTime());
		cust.setMsysid(SessionTool.getUserLogonInfo().getSysUserId());
		cust.setIsValid(Byte.valueOf("0"));//默认未验证
		cfsCustDao.save(cust);
	}

	@Override
	public void deleteById(Long id) {
		
		cfsCustDao.delete(id);
	}

	@Override
	public List<CfsCust> findAllCustByBuserId(Long buserId) {
		return cfsCustDao.findAllCustByBuserId(buserId);
	}
}