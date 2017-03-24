package com.upg.cfsettle.org.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.mapping.org.OrgUser;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.StringUtil;

/**
 * OrgOrganization service实现
 * @author renzhuolun
 * @date 2014年8月5日 上午10:27:27
 * @version <b>1.0.0</b>
 */
@Service
public class OrgUserServiceImpl implements IOrgUserService{
	
	@Autowired
	private IOrgUserDao orgUserDao;

	@Override
	public List<OrgUser> findByCondition(OrgUser searchBean, Page page) {
		String hql = "from OrgUser orgUser";
		QueryCondition condition = new QueryCondition(hql);
		if (searchBean != null) {
			String name = searchBean.getName();
			if (!StringUtil.isEmpty(name) || name != null) {
				condition.addCondition(new ConditionBean("orgUser.name", ConditionBean.LIKE, name));
			}
		}
		return orgUserDao.queryEntity(condition.getConditionList(), page);
	}
}
