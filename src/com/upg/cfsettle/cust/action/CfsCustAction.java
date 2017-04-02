package com.upg.cfsettle.cust.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.common.CodeItemUtil;
import com.upg.cfsettle.cust.core.ICfsCustService;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.prj.CfsCust;
import com.upg.cfsettle.util.LcsConstant;
import com.upg.ucars.framework.base.BaseAction;

@SuppressWarnings("serial")
public class CfsCustAction extends BaseAction {
	
	private CfsCust searchBean;
	
	@Autowired
	private ICfsCustService cfsCustService;
	
	private List<FiCodeItem>  yseNo;
	
	private List<FiCodeItem>  sexList;
	
	private CfsCust cfsCust;
	
	
	/**
	 * 跳转主页面
	 * @return
	 */
	public String main(){
		yseNo = CodeItemUtil.getCodeItemsByKey(LcsConstant.CFS_COMM_YSE_NO);
		return MAIN;
	}
	
	/**
	 * 查询信息
	 * @date 2014年8月5日 下午12:36:58
	 * @return
	 */
	public String list(){
		return setDatagridInputStreamData(cfsCustService.findByCondition(searchBean, getPg()), getPg());
	}
	
	/**
	 * 跳转新增页面
	 * @author renzhuolun
	 * @date 2017年3月31日 下午10:16:38
	 * @return
	 */
	public String toAdd(){
		sexList = CodeItemUtil.getCodeItemsByKey(LcsConstant.CFS_COMM_SEX);
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
	 * 批量删除cfsCust信息
	 */
	public void batchDelete(){
	}
	
	/**
	 * 修改cfsCust信息
	 */
	public void doEdit(){
		cfsCustService.updateCfsCust(cfsCust);
	}
	
	/**
	 * 新增cfsCust信息
	 */
	public void doAdd(){
		cfsCustService.addCfsCust(cfsCust);
	}
	
	public CfsCust getSearchBean() {
		return searchBean;
	}

	public void setSearchBean(CfsCust searchBean) {
		this.searchBean = searchBean;
	}

	public List<FiCodeItem> getYseNo() {
		return yseNo;
	}

	public void setYseNo(List<FiCodeItem> yseNo) {
		this.yseNo = yseNo;
	}

	public List<FiCodeItem> getSexList() {
		return sexList;
	}

	public void setSexList(List<FiCodeItem> sexList) {
		this.sexList = sexList;
	}

	public CfsCust getCfsCust() {
		return cfsCust;
	}

	public void setCfsCust(CfsCust cfsCust) {
		this.cfsCust = cfsCust;
	}
}