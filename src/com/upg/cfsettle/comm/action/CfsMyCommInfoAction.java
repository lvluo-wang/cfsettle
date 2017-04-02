package com.upg.cfsettle.comm.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.comm.core.ICfsMyCommInfoService;
import com.upg.cfsettle.common.CodeItemUtil;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.prj.CfsMyCommInfo;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.framework.base.BaseAction;

@SuppressWarnings("serial")
public class CfsMyCommInfoAction extends BaseAction {
	
	private CfsMyCommInfo searchBean;
	
	private CfsPrj cfsPrj;
	
	@Autowired
	private ICfsMyCommInfoService myCommInfoService;
	
	private CfsMyCommInfo commInfo;
	@Autowired
	private IPrjService prjService;
	
	private List<FiCodeItem> commStatus;
	
	
	
	/**
	 * 跳转主页面
	 * @return
	 */
	public String main(){
		commStatus = CodeItemUtil.getCodeItemsByKey(UtilConstant.CFS_COMM_PAY_STATUS);
		return MAIN;
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
	public String toEdit(){
		return EDIT;
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
}