package com.upg.cfsettle.org.core;

import com.upg.cfsettle.mapping.org.OrgOrganization;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.util.DateTimeUtil;

/**
 * OrgOrganization dao
 * @author renzhuolun
 * @date 2016年8月22日 下午4:04:44
 * @version <b>1.0.0</b>
 */
@Dao
public class OrgOrganizationDaoImpl extends BaseDAO<OrgOrganization, Long> implements IOrgOrganizationDao{

	@Override
	public void updateOrg(OrgOrganization org) {
		org.setMtime(DateTimeUtil.getNowDateTime());
		this.update(org);
	}
}
