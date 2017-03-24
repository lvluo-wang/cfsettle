package com.upg.cfsettle.org.core;

import java.util.List;

import com.upg.cfsettle.mapping.org.OrgOrganization;
import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;

/**
 * OrgOrganization Service接口
 * @author renzhuolun
 * @date 2016年8月22日 下午4:04:00
 * @version <b>1.0.0</b>
 */
public interface IOrgOrganizationService extends IBaseService {
	
	/**
	 * 条件查询
	 * @author renzhuolun
	 * @date 2016年8月22日 下午4:47:22
	 * @param searchBean
	 * @param page
	 * @return
	 */
	List<OrgOrganization> findByCondition(OrgOrganization searchBean, Page page);
	
	/**
	 * 审核通过
	 * @author renzhuolun
	 * @date 2016年8月23日 下午4:55:00
	 * @param idList
	 */
	void doPass(List<Long> idList);
	
	/**
	 * 审核不通过
	 * @author renzhuolun
	 * @date 2016年8月23日 下午4:55:13
	 * @param idList
	 */
	void doUnPass(List<Long> idList);
	
	/**
	 * 主键查询
	 * @author renzhuolun
	 * @date 2016年8月23日 下午4:56:50
	 * @param id
	 * @return
	 */
	OrgOrganization getOrgOrganizationById(Long id);
	
	/**
	 * 更新
	 * @author renzhuolun
	 * @date 2016年8月23日 下午5:13:41
	 * @param org
	 * @return
	 */
	void update(OrgOrganization org);
}
