package com.upg.cfsettle.comm.action;

import java.util.Date;
import java.util.List;

import com.upg.cfsettle.comm.core.CfsCommOrderRelateService;
import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.comm.core.ICfsMyCommInfoService;
import com.upg.cfsettle.common.CodeItemUtil;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.prj.CfsMyCommInfo;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.mapping.basesystem.security.Buser;

@SuppressWarnings("serial")
public class CfsCommSettleAction extends BaseAction {
	
	private CfsMyCommInfo searchBean;
	
	private CfsPrj cfsPrj;
	
	private Buser buser;
	
	@Autowired
	private ICfsMyCommInfoService myCommInfoService;
	@Autowired
	private CfsCommOrderRelateService cfsCommOrderRelateService;
	private Long sysUserId;
	private Date dateMonth;
	
	private CfsMyCommInfo commInfo;
	@Autowired
	private IPrjService prjService;
	
	private List<FiCodeItem> commStatus;
	@Autowired
	private IUserService userService;
	
	
	
	/**
	 * 跳转主页面
	 * @return
	 */
	public String main(){
		commStatus = CodeItemUtil.getCodeItemsByKey(UtilConstant.CFS_COMM_PAY_STATUS);
		return SUCCESS;
	}
	
	/**
	 * 项目佣金
	 * @return
	 */
	public String prjComm(){
		return "prjComm";
	}
	
	/**
	 * 项目佣金List
	 * @return
	 */
	public String prjCommList(){
		List<CfsPrj> list = prjService.findPrjByCondition(cfsPrj, getPg());
		return setDatagridInputStreamData(list, getPg());
	}
	
	/**
	 * 查询信息
	 * @date 2014年8月5日 下午12:36:58
	 * @return
	 */
	public String list(){
		return setDatagridInputStreamData(myCommInfoService.findByCondition(searchBean, getPg()), getPg());
	}

	/**
	 * 佣金明细
	 */
	public String toCommDetail(){

		return "commDetail";
	}

	/**
	 * 佣金明细list
	 */
	public String commDetailList(){
		return setDatagridInputStreamData(cfsCommOrderRelateService.findCommDetailBySysid(sysUserId,dateMonth,getPg()),getPg());
	}
	
	/**
	 * 跳转新增页面
	 * @author renzhuolun
	 * @date 2017年3月31日 下午10:16:38
	 * @return
	 */
	public String toAdd(){
		return ADD;
	}
	
	/**
	 * 跳转编辑页面
	 * @author renzhuolun
	 * @date 2017年3月31日 下午10:16:52
	 * @return
	 */
	public String toView(){
		commInfo = myCommInfoService.queryCfsMyCommInfoById(getPKId());
		buser = userService.getUserById(commInfo.getSysid());
		return SUCCESS;
	}
	
	/**
	 * 修改cfsCust信息
	 */
	public void doEdit(){
		myCommInfoService.updateCfsMyCommInfo(commInfo);
	}
	
	/**
	 * 新增cfsCust信息
	 */
	public void doAdd(){
		myCommInfoService.addCfsMyCommInfo(commInfo);
	}
	
	/**
	 * 查询结算今年明细
	 * @author renzl123
	 * @date 2017年4月12日 下午11:04:12
	 * @return
	 */
	public String listComm(){
		return setDatagridInputStreamData(myCommInfoService.findByCommDetail(searchBean,getPg()),getPg());
	}

	public CfsMyCommInfo getSearchBean() {
		return searchBean;
	}

	public void setSearchBean(CfsMyCommInfo searchBean) {
		this.searchBean = searchBean;
	}

	public CfsMyCommInfo getCommInfo() {
		return commInfo;
	}

	public void setCommInfo(CfsMyCommInfo commInfo) {
		this.commInfo = commInfo;
	}

	public CfsPrj getCfsPrj() {
		return cfsPrj;
	}

	public void setCfsPrj(CfsPrj cfsPrj) {
		this.cfsPrj = cfsPrj;
	}

	public List<FiCodeItem> getCommStatus() {
		return commStatus;
	}

	public void setCommStatus(List<FiCodeItem> commStatus) {
		this.commStatus = commStatus;
	}

	public Buser getBuser() {
		return buser;
	}

	public void setBuser(Buser buser) {
		this.buser = buser;
	}
	public Long getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Long sysUserId) {
		this.sysUserId = sysUserId;
	}

	public Date getDateMonth() {
		return dateMonth;
	}

	public void setDateMonth(Date dateMonth) {
		this.dateMonth = dateMonth;
	}
}
