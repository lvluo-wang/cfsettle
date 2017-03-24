package com.upg.cfsettle.org.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.upg.cfsettle.mapping.org.OrgAttach;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.model.ConditionBean;

/**
 * OrgAttach service实现
 * @author renzhuolun
 * @date 2014年8月5日 上午10:27:27
 * @version <b>1.0.0</b>
 */
@Service
public class OrgAttachServiceImpl implements IOrgAttachService{
	
	@Autowired
	private IOrgAttachDao orgAttachDao;

	@Override
	public OrgAttach getOrgAttachByOrgId(Long orgId, Short attachType) {
		if(!CollectionUtils.isEmpty(this.findByCondition(new OrgAttach(orgId,attachType),null))){
			return this.findByCondition(new OrgAttach(orgId,attachType),null).get(0);
		}
		return null;
	}

	@Override
	public List<OrgAttach> findByCondition(OrgAttach searchBean, Page page) {
		String hql = "from OrgAttach orgAttach";
		QueryCondition condition = new QueryCondition(hql);
		if (searchBean != null) {
			Long orgId = searchBean.getOrgId();
			if (orgId != null) {
				condition.addCondition(new ConditionBean("orgAttach.orgId", ConditionBean.EQUAL, orgId));
			}
			Short property = searchBean.getProperty();
			if (property != null) {
				condition.addCondition(new ConditionBean("orgAttach.property", ConditionBean.EQUAL, property));
			}
		}
		return orgAttachDao.queryEntity(condition.getConditionList(), page);
	}
}
