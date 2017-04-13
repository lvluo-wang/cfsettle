package com.upg.cfsettle.comm.core;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.mapping.prj.CfsCommDetail;
import com.upg.cfsettle.mapping.prj.CfsMyCommInfo;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.StringUtil;

@Service
public class CfsMyCommInfoServiceImpl implements ICfsMyCommInfoService{
	
	@Autowired
	private ICfsMyCommInfoDao cfsMyCommInfoDao;

	@Override
	public List<CfsMyCommInfo> findByCondition(CfsMyCommInfo searchBean, Page page) {
		String hql = "from CfsMyCommInfo cfsMyCommInfo";
		QueryCondition condition = new QueryCondition(hql);
		if (searchBean != null) {
			Date commSettleDate = searchBean.getCommSettleDate();
			if (commSettleDate != null) {
				condition.addCondition(new ConditionBean("cfsMyCommInfo.commSettleDate", ConditionBean.EQUAL, commSettleDate));
			}
			Byte payStatus = searchBean.getPayStatus();
			if (payStatus != null) {
				condition.addCondition(new ConditionBean("cfsMyCommInfo.payStatus", ConditionBean.EQUAL, payStatus));
			}
			String sysUserName = searchBean.getSysUserName();
			if (StringUtil.isEmpty(sysUserName)) {
				condition.addCondition(new ConditionBean("cfsMyCommInfo.sysUserName", ConditionBean.LIKE, sysUserName));
			}
			String posCode = searchBean.getPosCode();
			if (StringUtil.isEmpty(posCode)) {
				condition.addCondition(new ConditionBean("cfsMyCommInfo.posCode", ConditionBean.EQUAL, posCode));
			}
			String mobile = searchBean.getMobile();
			if (StringUtil.isEmpty(mobile)) {
				condition.addCondition(new ConditionBean("cfsMyCommInfo.mobile", ConditionBean.LIKE, mobile));
			}
			Long sysid = searchBean.getSysid();
			if (sysid != null) {
				condition.addCondition(new ConditionBean("cfsMyCommInfo.sysid", ConditionBean.EQUAL, sysid));
			}
		}
		return cfsMyCommInfoDao.queryEntity( condition.getConditionList(), page);
	}

	@Override
	public CfsMyCommInfo queryCfsMyCommInfoById(Long id) {
		return cfsMyCommInfoDao.get(id);
	}

	@Override
	public void updateCfsMyCommInfo(CfsMyCommInfo myCommInfo) {
		cfsMyCommInfoDao.update(myCommInfo);
	}

	@Override
	public void addCfsMyCommInfo(CfsMyCommInfo myCommInfo) {
		cfsMyCommInfoDao.save(myCommInfo);
	}

	@Override
	public void deleteById(Long pkId) {
		cfsMyCommInfoDao.delete(pkId);
	}

	@Override
	public List<CfsCommDetail> findByCommDetail(CfsMyCommInfo searchBean, Page page) {
		return cfsMyCommInfoDao.findByCommDetail(searchBean,page);
	}
}