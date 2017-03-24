package com.upg.cfsettle.banner.action;

import java.util.List;

import com.upg.cfsettle.banner.core.IBannerService;
import com.upg.cfsettle.code.core.ICodeItemService;
import com.upg.cfsettle.mapping.banner.Banner;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.util.FiMemberUtils;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.framework.base.BaseAction;
/**
 * Banner action
 * @author renzhuolun
 * @date 2014年8月5日 上午10:26:02
 * @version <b>1.0.0</b>
 */
@SuppressWarnings("serial")
public class BannerAction extends BaseAction {
	
	private Banner searchBean;
	
	private IBannerService bannerService;
	
	private List<FiCodeItem> bannerSortList;
	
	private List<FiCodeItem> bannerPositionList;
	
	private List<FiCodeItem> bannerIsAvtiveList;
	
	private List<FiCodeItem> bannerMiNoList;
	
	private Banner banner;
	
	private ICodeItemService codeItemService;
	
	/**
	 * 跳转banner主页
	 * @author renzhuolun
	 * @date 2014年8月5日 上午10:33:05
	 * @return
	 */
	public String main(){
		return MAIN;
	}
	
	/**
	 * 查询banner信息
	 * @author renzhuolun
	 * @date 2014年8月5日 下午12:36:58
	 * @return
	 */
	public String list(){
		return setDatagridInputStreamData(bannerService.findByCondition(searchBean, getPg()), getPg());
	}
	
	/**
	 * 跳转新增页面
	 * @author renzhuolun
	 * @date 2014年8月5日 下午12:38:01
	 * @return
	 */
	public String toAdd(){
		bannerSortList = codeItemService.getCodeItemByKey(UtilConstant.XXH_BANNER_SORT);
		bannerPositionList = codeItemService.getCodeItemByKey(UtilConstant.XXH_BANNER_POSITION);
		bannerIsAvtiveList = codeItemService.getCodeItemByKey(UtilConstant.YES_NO);
		bannerMiNoList = codeItemService.getCodeItemByKey(UtilConstant.XXH_CUSTOMER_MINO);
		return ADD;
	}
	
	/**
	 * 跳转编辑页面
	 * @author renzhuolun
	 * @date 2014年8月8日 上午9:35:23
	 * @return
	 */
	public String toEdit(){
		bannerSortList = codeItemService.getCodeItemByKey(UtilConstant.XXH_BANNER_SORT);
		bannerPositionList = codeItemService.getCodeItemByKey(UtilConstant.XXH_BANNER_POSITION);
		bannerIsAvtiveList = codeItemService.getCodeItemByKey(UtilConstant.YES_NO);
		bannerMiNoList = codeItemService.getCodeItemByKey(UtilConstant.XXH_CUSTOMER_MINO);
		banner = bannerService.queryBannerById(getPKId());
		return EDIT;
	}
	
	/**
	 * 批量删除banner信息
	 * @author renzhuolun
	 * @date 2014年8月8日 上午9:28:16
	 */
	public void batchDelete(){
		bannerService.batchDelete(getIdList());
	}
	
	/**
	 * 修改banner信息
	 * @author renzhuolun
	 * @date 2014年8月8日 上午10:58:10
	 */
	public void doEdit(){
		bannerService.updateBanner(banner);
	}
	
	/**
	 * 新增banner信息
	 * @author renzhuolun
	 * @date 2014年8月11日 上午10:42:31
	 */
	public void doAdd(){
		bannerService.addBanner(banner);
	}
	
	/**
	 * 查看banner
	 * @author renzhuolun
	 * @date 2016年8月19日 下午2:33:51
	 * @return
	 */
	public String toView(){
		banner = bannerService.queryBannerById(getPKId());
		//根据img字段的前缀id,从fi_attach表获取服务器路径
		String basePath[]=banner.getImg().split(COLON);
		banner.setMiName(FiMemberUtils.getMiNameByNo(banner.getMiNoCode()));
		return VIEW;
	}
	
	public Banner getSearchBean() {
		return searchBean;
	}

	public void setSearchBean(Banner searchBean) {
		this.searchBean = searchBean;
	}

	public void setBannerService(IBannerService bannerService) {
		this.bannerService = bannerService;
	}

	public Banner getBanner() {
		return banner;
	}

	public void setBanner(Banner banner) {
		this.banner = banner;
	}

	public List<FiCodeItem> getBannerSortList() {
		return bannerSortList;
	}

	public void setBannerSortList(List<FiCodeItem> bannerSortList) {
		this.bannerSortList = bannerSortList;
	}

	public List<FiCodeItem> getBannerPositionList() {
		return bannerPositionList;
	}

	public void setBannerPositionList(List<FiCodeItem> bannerPositionList) {
		this.bannerPositionList = bannerPositionList;
	}

	public List<FiCodeItem> getBannerIsAvtiveList() {
		return bannerIsAvtiveList;
	}

	public void setBannerIsAvtiveList(List<FiCodeItem> bannerIsAvtiveList) {
		this.bannerIsAvtiveList = bannerIsAvtiveList;
	}

	public List<FiCodeItem> getBannerMiNoList() {
		return bannerMiNoList;
	}

	public void setBannerMiNoList(List<FiCodeItem> bannerMiNoList) {
		this.bannerMiNoList = bannerMiNoList;
	}

	public void setCodeItemService(ICodeItemService codeItemService) {
		this.codeItemService = codeItemService;
	}
	
}
