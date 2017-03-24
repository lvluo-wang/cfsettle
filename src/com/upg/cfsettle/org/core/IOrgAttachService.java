package com.upg.cfsettle.org.core;

import java.util.List;

import com.upg.cfsettle.mapping.org.OrgAttach;
import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;

/**
 * OrgAttach Service接口
 * @author renzhuolun
 * @date 2016年8月22日 下午4:04:00
 * @version <b>1.0.0</b>
 */
public interface IOrgAttachService extends IBaseService {
	
	/**
	 * 查询组织机构附件
	 * @author renzhuolun
	 * @date 2016年8月23日 下午2:39:45
	 * @param orgId
	 * @param attachType
	 * @return
	 */
	OrgAttach getOrgAttachByOrgId(Long orgId, Short attachType);
	
	/**
	 * 条件查询
	 * @author renzhuolun
	 * @date 2016年8月23日 下午2:42:41
	 * @param searchBean
	 * @param page
	 * @return
	 */
	List<OrgAttach> findByCondition(OrgAttach searchBean,Page page);
	
}
