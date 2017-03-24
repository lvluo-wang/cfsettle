package com.upg.cfsettle.banner.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.StringUtil;
import com.upg.cfsettle.mapping.banner.Banner;

/**
 * Banner service实现
 * @author renzhuolun
 * @date 2014年8月5日 上午10:27:27
 * @version <b>1.0.0</b>
 */
@Service
public class BannerServiceImpl implements IBannerService{
	
	@Autowired
	private IBannerDao bannerDao;
	
	/**
	 * 
	 * 查询信息
	 * @date 2014年8月5日 上午10:35:47
	 * @see com.upg.cfsettle.banner.core.IBannerService#findByCondition(com.upg.cfsettle.mapping.BannerSearchBean, com.upg.ucars.framework.base.Page)
	 * @param searchBean
	 * @param pg
	 * @return
	 */
	public List<Banner> findByCondition(Banner searchBean, Page page) {
		String hql = "from Banner banner";
		QueryCondition condition = new QueryCondition(hql);
		if (searchBean != null) {
			String title = searchBean.getTitle();
			if (!StringUtil.isEmpty(title) || title != null) {
				condition.addCondition(new ConditionBean("banner.title", ConditionBean.LIKE, title));
			}
			String content = searchBean.getContent();
			if (!StringUtil.isEmpty(content) || content != null) {
				condition.addCondition(new ConditionBean("banner.content", ConditionBean.LIKE, content));
			}
		}
		return bannerDao.queryEntity( condition.getConditionList(), page);
	}

	public void setBannerDao(IBannerDao bannerDao) {
		this.bannerDao = bannerDao;
	}
	
	/**
	 * 批量删除banner信息
	 * @author renzhuolun
	 * @date 2014年8月8日 上午9:29:35
	 * @see com.upg.cfsettle.banner.core.IBannerService#batchDelete(java.util.List)
	 * @param idList
	 */
	public void batchDelete(List<Long> idList) {
		for (Long id : idList) {
			bannerDao.delete(id);
		}
	}
	
	/**
	 * 通过主键Id查询Banner详细信息
	 * @author renzhuolun
	 * @date 2014年8月8日 上午9:37:12
	 * @see com.upg.cfsettle.banner.core.IBannerService#queryBannerById(java.lang.String)
	 * @param id
	 * @return
	 */
	public Banner queryBannerById(Long id) {
		return bannerDao.get(id);
	}
	
	/**
	 * 修改banner信息
	 * @author renzhuolun
	 * @date 2014年8月8日 上午10:59:05
	 * @see com.upg.cfsettle.banner.core.IBannerService#updateBanner(com.upg.cfsettle.mapping.Banner)
	 * @param banner
	 */
	public void updateBanner(Banner banner) {
		banner.setMtime(DateTimeUtil.getNowSeconds());
		bannerDao.update(banner);
	}
	
	/**
	 * 新增banner信息
	 * @author renzhuolun
	 * @date 2014年8月11日 上午10:43:17
	 * @see com.upg.cfsettle.banner.core.IBannerService#addBanner(com.upg.cfsettle.mapping.Banner)
	 * @param banner
	 */
	public void addBanner(Banner banner) {
		banner.setCtime(DateTimeUtil.getNowSeconds());
		banner.setMtime(DateTimeUtil.getNowSeconds());
		bannerDao.save(banner);
	}
}
