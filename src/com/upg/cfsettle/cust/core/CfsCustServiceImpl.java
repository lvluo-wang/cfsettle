package com.upg.cfsettle.cust.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.upg.cfsettle.mapping.prj.CfsCust;
import com.upg.cfsettle.mapping.prj.CfsCustBuserRelate;
import com.upg.ucars.basesystem.UcarsHelper;
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
	@Autowired
	private ICfsCustBuserRelateDao custBuserRelateDao;

	@Override
	public List<CfsCust> findByCondition(CfsCust searchBean, Page page) {
		String hql = "select cfsCust from CfsCust cfsCust ,CfsCustBuserRelate relate where cfsCust.id =relate.custId ";
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
			condition.addCondition(new ConditionBean("relate.sysId", ConditionBean.EQUAL, SessionTool.getUserLogonInfo().getSysUserId()));
		}
		return cfsCustDao.queryEntityByCustomerHQL(hql,condition.getConditionList(),null,page);
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
		QueryCondition condition = new QueryCondition();
		condition.addCondition(new ConditionBean("cfsCust.idCard", ConditionBean.EQUAL, cust.getIdCard()));
		List<CfsCust> custs = cfsCustDao.queryEntity(condition.getConditionList(),null);
		if(CollectionUtils.isEmpty(custs)){
			cfsCustDao.save(cust);
			CfsCustBuserRelate relate = new CfsCustBuserRelate(cust.getId(), SessionTool.getUserLogonInfo().getSysUserId(), 
					SessionTool.getUserLogonInfo().getSysUserId(), DateTimeUtil.getNowDateTime());
			custBuserRelateDao.save(relate);
		}else{
			UcarsHelper.throwServiceException("该用户已添加,不能重复添加");
		}
	}

	@Override
	public void deleteById(Long id) {
		
		cfsCustDao.delete(id);
	}

	@Override
	public List<CfsCust> findAllCustByBuserId(Long buserId) {
		return cfsCustDao.findAllCustByBuserId(buserId);
	}

	@Override
	public List<CfsCustInfo> findByConditionAndPosCode(CustSearchBean searchBean, Page page) {
		return cfsCustDao.findByConditionAndPosCode(searchBean,page);
	}
}