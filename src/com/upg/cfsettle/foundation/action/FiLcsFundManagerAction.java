package com.upg.cfsettle.foundation.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.ucars.framework.base.BaseAction;
import com.upg.cfsettle.foundation.core.IFiLcsFundManagerService;
import com.upg.cfsettle.mapping.filcs.FiLcsFundAttach;
import com.upg.cfsettle.mapping.filcs.FiLcsFundManager;
import com.upg.cfsettle.mapping.filcs.FiLcsFundManagerExt;

@SuppressWarnings("serial")
public class FiLcsFundManagerAction extends BaseAction {
	@Autowired
	private IFiLcsFundManagerService fundManagerService;
	
	private FiLcsFundManager fundManager;
	
	private FiLcsFundManagerExt fundManagerExt;
	
	private List<FiLcsFundManagerExt> fundManagerExts;
	
	private FiLcsFundAttach fundAttach;
	
	private Long fundId;
	/**
	 * 跳转新增经理页面
	 * @author renzhuolun
	 * @date 2016年6月6日 上午11:12:43
	 * @return
	 */
	public String toAddPer() {
		fundId = getPKId();
		return "per";
	}
	
	/**
	 * 跳转新增团队页面
	 * @author renzhuolun
	 * @date 2016年6月6日 上午11:12:43
	 * @return
	 */
	public String toAddTeam() {
		fundId = getPKId();
		return "team";
	}
	
	/**
	 * 新增个人基金信息
	 * @author renzhuolun
	 * @date 2016年6月6日 上午11:13:10
	 */
	public void doAddPer() {
		fundManagerService.addLcsFundManagerOfPer(fundManager);
	}
	
	/**
	 * 新增团队基金信息
	 * @author renzhuolun
	 * @date 2016年6月6日 上午11:13:10
	 */
	public void doAddTeam() {
		fundManagerService.addLcsFundManagerOfTeam(fundManager);
	}
	
	/**
	 * 查询基金经理
	 * @author renzhuolun
	 * @date 2016年6月12日 下午5:44:41
	 * @return
	 */
	public String list(){
		List<FiLcsFundManager> list = fundManagerService.findByCondition(fundManager, getPg());
		return setDatagridInputStreamData(list, getPg());
	}
	
	/**
	 * 跳转修改经理信息
	 * @author renzhuolun
	 * @date 2016年6月13日 上午10:04:38
	 * @return
	 */
	public String toEdit() {
		fundManager = fundManagerService.getFundManagerById(getPKId());
		fundManagerExts = fundManagerService.getFundManagerExtById(getPKId());
		fundAttach = fundManagerService.getFundAttachByHeadAttachId(fundManager.getHeadAttach());
		return SUCCESS;
	}
	
	/**
	 * 修改经理信息
	 * @author renzhuolun
	 * @date 2016年6月15日 下午2:44:08
	 */
	public void doEditPer(){
		fundManagerService.doEditFundManager(fundManager);
	}
	
	public IFiLcsFundManagerService getFundManagerService() {
		return fundManagerService;
	}

	public void setFundManagerService(IFiLcsFundManagerService fundManagerService) {
		this.fundManagerService = fundManagerService;
	}

	public FiLcsFundManager getFundManager() {
		return fundManager;
	}

	public void setFundManager(FiLcsFundManager fundManager) {
		this.fundManager = fundManager;
	}

	public FiLcsFundManagerExt getFundManagerExt() {
		return fundManagerExt;
	}

	public void setFundManagerExt(FiLcsFundManagerExt fundManagerExt) {
		this.fundManagerExt = fundManagerExt;
	}

	public Long getFundId() {
		return fundId;
	}

	public void setFundId(Long fundId) {
		this.fundId = fundId;
	}

	public List<FiLcsFundManagerExt> getFundManagerExts() {
		return fundManagerExts;
	}

	public void setFundManagerExts(List<FiLcsFundManagerExt> fundManagerExts) {
		this.fundManagerExts = fundManagerExts;
	}

	public FiLcsFundAttach getFundAttach() {
		return fundAttach;
	}

	public void setFundAttach(FiLcsFundAttach fundAttach) {
		this.fundAttach = fundAttach;
	}
}