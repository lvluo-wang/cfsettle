package com.upg.cfsettle.org.core;

import java.util.List;

import com.upg.cfsettle.mapping.org.OrgUser;
import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;

/**
 * OrgOrganization Service接口
 * @author renzhuolun
 * @date 2016年8月22日 下午4:04:00
 * @version <b>1.0.0</b>
 */
public interface IOrgUserService extends IBaseService {
	
	/**
	 * 条件查询
	 * @author renzhuolun
	 * @date 2016年8月22日 下午4:47:22
	 * @param searchBean
	 * @param page
	 * @return
	 */
	List<OrgUser> findByCondition(OrgUser searchBean, Page page);
}
