package com.upg.cfsettle.banner.core;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.cfsettle.mapping.banner.Banner;

/**
 * Banner Service接口
 * @author renzhuolun
 * @date 2014年8月5日 上午10:26:35
 * @version <b>1.0.0</b>
 */
public interface IBannerService extends IBaseService {
	
	/**
	 * 查询信息
	 * @author renzhuolun
	 * @date 2014年8月5日 上午10:35:05
	 * @param searchBean
	 * @param pg
	 * @return
	 */
	List<Banner> findByCondition(Banner searchBean, Page pg);
	
	/**
	 * 批量删除banner信息
	 * @author renzhuolun
	 * @date 2014年8月8日 上午9:29:10
	 * @param idList
	 */
	void batchDelete(List<Long> idList);
	
	/**
	 * 通过主键Id查询Banner详细信息
	 * @author renzhuolun
	 * @date 2014年8月8日 上午9:35:53
	 * @param id
	 * @return
	 */
	Banner queryBannerById(Long id);
	
	/**
	 * 修改banner信息
	 * @author renzhuolun
	 * @date 2014年8月8日 上午10:58:47
	 * @param banner
	 */
	void updateBanner(Banner banner);
	
	/**
	 * 新增banner信息
	 * @author renzhuolun
	 * @date 2014年8月11日 上午10:42:56
	 * @param banner
	 */
	void addBanner(Banner banner);

}
