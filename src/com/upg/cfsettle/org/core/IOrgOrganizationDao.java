package com.upg.cfsettle.org.core;

import com.upg.cfsettle.mapping.org.OrgOrganization;
import com.upg.ucars.framework.base.IBaseDAO;

/**
 * OrgOrganization dao接口
 * @author renzhuolun
 * @date 2016年8月22日 下午4:03:27
 * @version <b>1.0.0</b>
 */
public interface IOrgOrganizationDao extends IBaseDAO<OrgOrganization, Long> {
	
	/**
	 * 更新投资机构信息
	 * @author renzhuolun
	 * @date 2016年8月23日 下午5:15:37
	 * @param org
	 */
	void updateOrg(OrgOrganization org);

}
