package com.upg.cfsettle.order.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.cust.core.CustOrderBean;
import com.upg.cfsettle.cust.core.ICfsPrjOrderService;
import com.upg.cfsettle.mapping.prj.CfsPrjOrder;
import com.upg.ucars.framework.base.BaseAction;

@SuppressWarnings("serial")
public class CfsPrjOrderAction extends BaseAction {
	
	private CustOrderBean searchBean;
	
	@Autowired
	private ICfsPrjOrderService prjOrderService;
	
	private CfsPrjOrder cfsPrjOrder;
	
	/**
	 * 跳转CfsPrjOrder主页
	 * @author renzhuolun
	 * @date 2017年4月4日 下午2:08:53
	 * @return
	 */
	public String main(){
		return MAIN;
	}
	
	/**
	 * 查询CfsPrjOrder信息
	 * @author renzhuolun
	 * @date 2014年8月5日 下午12:36:58
	 * @return
	 */
	public String list(){
		return setDatagridInputStreamData(prjOrderService.findByCondition(searchBean, getPg()), getPg());
	}
	
	/**
	 * 跳转新增页面
	 * @author renzhuolun
	 * @date 2014年8月5日 下午12:38:01
	 * @return
	 */
	public String toAdd(){
		return ADD;
	}
	
	/**
	 * 跳转编辑页面
	 * @author renzhuolun
	 * @date 2014年8月8日 上午9:35:23
	 * @return
	 */
	public String toEdit(){
		return EDIT;
	}
	
	/**
	 * 批量删除CfsPrjOrder信息
	 * @author renzhuolun
	 * @date 2014年8月8日 上午9:28:16
	 */
	public void batchDelete(){
		prjOrderService.batchDelete(getIdList());
	}
	
	/**
	 * 修改CfsPrjOrder信息
	 * @author renzhuolun
	 * @date 2014年8月8日 上午10:58:10
	 */
	public void doEdit(){
		prjOrderService.updatePrjOrder(cfsPrjOrder);
	}
	
	/**
	 * 新增CfsPrjOrder信息
	 * @author renzhuolun
	 * @date 2014年8月11日 上午10:42:31
	 */
	public void doAdd(){
		prjOrderService.addPrjOrder(cfsPrjOrder);
	}
	
	/**
	 * 查看CfsPrjOrder
	 * @author renzhuolun
	 * @date 2016年8月19日 下午2:33:51
	 * @return
	 */
	public String toView(){
		return VIEW;
	}
	
	public CustOrderBean getSearchBean() {
		return searchBean;
	}

	public void setSearchBean(CustOrderBean searchBean) {
		this.searchBean = searchBean;
	}

	public CfsPrjOrder getCfsPrjOrder() {
		return cfsPrjOrder;
	}

	public void setCfsPrjOrder(CfsPrjOrder cfsPrjOrder) {
		this.cfsPrjOrder = cfsPrjOrder;
	}
	
}