package com.upg.cfsettle.cust.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.cust.core.ICfsCustService;
import com.upg.cfsettle.mapping.prj.CfsCust;
import com.upg.ucars.framework.base.BaseAction;

@SuppressWarnings("serial")
public class CfsCustAction extends BaseAction {
	
	private CfsCust searchBean;
	
	@Autowired
	private ICfsCustService cfsCustService;
	
	
	/**
	 * 跳转主页面
	 * @return
	 */
	public String main(){
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
	 * 批量删除banner信息
	 * @author renzhuolun
	 * @date 2014年8月8日 上午9:28:16
	 */
	public void batchDelete(){
	}
	
	/**
	 * 修改banner信息
	 * @author renzhuolun
	 * @date 2014年8月8日 上午10:58:10
	 */
	public void doEdit(){
	}
	
	/**
	 * 新增banner信息
	 * @author renzhuolun
	 * @date 2014年8月11日 上午10:42:31
	 */
	public void doAdd(){
	}
	
	/**
	 * 查看banner
	 * @author renzhuolun
	 * @date 2016年8月19日 下午2:33:51
	 * @return
	 */
	public String toView(){
		return VIEW;
	}
}