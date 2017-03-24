package com.upg.cfsettle.foundation.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.ucars.framework.base.BaseAction;
import com.upg.cfsettle.common.CodeItemUtil;
import com.upg.cfsettle.foundation.core.IFiLcsFundAttachService;
import com.upg.cfsettle.foundation.core.IFiLcsFundService;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.filcs.FiLcsFund;
import com.upg.cfsettle.mapping.filcs.FiLcsFundAttach;
import com.upg.cfsettle.util.LcsConstant;


@SuppressWarnings("serial")
public class FiLcsFundAction extends BaseAction {
	@Autowired
	private IFiLcsFundService fiLcsFundService;
	
	@Autowired
	private IFiLcsFundAttachService fundAttachService;
	
	private FiLcsFund fiLcsFund;
	
	private FiLcsFund searchBean;
	
	private FiLcsFundAttach  fiLcsFundAttach;
	
	private List<FiCodeItem> fundStatus;
	private List<FiCodeItem> fundType;
	
	private List<FiLcsFundAttach> fundAttachs;
	/**
	 * 基金查询主页
	 * @author renzhuolun
	 * @date 2016年6月6日 上午11:12:17
	 * @return
	 */
	public String main() {
		fundStatus = CodeItemUtil.getCodeItemsByKey(LcsConstant.LCS_FUND_STATUS);
		fundType = CodeItemUtil.getCodeItemsByKey(LcsConstant.LCS_FUND_TYPE);
		return SUCCESS;
	}
	
	/**
	 * 跳转新增页面
	 * @author renzhuolun
	 * @date 2016年6月6日 上午11:12:43
	 * @return
	 */
	public String toAdd() {
		fundType = CodeItemUtil.getCodeItemsByKey(LcsConstant.LCS_FUND_TYPE);
		return SUCCESS;
	}
	
	/**
	 * 新增基金信息
	 * @author renzhuolun
	 * @date 2016年6月6日 上午11:13:10
	 */
	public void doAdd() {
		fiLcsFundService.addFiLcsFund(fiLcsFund, fiLcsFundAttach);
	}
	
	/**
	 * 条件查询
	 * @author renzhuolun
	 * @date 2016年6月6日 上午11:45:14
	 * @return
	 */
	public String list(){
		List<FiLcsFund> list = fiLcsFundService.findByCondition(searchBean, getPg());
		return setDatagridInputStreamData(list, getPg());
	}
	
	/**
	 * 修改基金信息
	 * @author renzhuolun
	 * @date 2016年6月7日 下午5:37:07
	 * @return
	 */
	public String toEdit() {
		fundType = CodeItemUtil.getCodeItemsByKey(LcsConstant.LCS_FUND_TYPE);
		fiLcsFund = fiLcsFundService.getFiLcsFundById(getPKId());
		fundAttachs = fundAttachService.getFundAttachsByFundId(getPKId());
		fiLcsFundAttach = fundAttachs.size()==0?new FiLcsFundAttach():fundAttachs.get(0);
		return SUCCESS;
	}
	
	/**
	 * 修改基金信息
	 * @author renzhuolun
	 * @date 2016年6月8日 下午4:20:40
	 */
	public void doEdit() {
		fiLcsFundService.editFiLcsFund(fiLcsFund);
	}
	
	/**
	 * 项目信息查看
	 * @author renzhuolun
	 * @date 2016年6月12日 下午5:00:17
	 */
	public String doView() {
		fiLcsFund = fiLcsFundService.getFiLcsFundById(getPKId());
		fundAttachs = fundAttachService.getFundAttachsByFundId(getPKId());
		fiLcsFundAttach = fundAttachs.size()==0?new FiLcsFundAttach():fundAttachs.get(0);
		return SUCCESS;
	}
	
	public FiLcsFund getFiLcsFund() {
		return fiLcsFund;
	}

	public void setFiLcsFund(FiLcsFund fiLcsFund) {
		this.fiLcsFund = fiLcsFund;
	}

	public FiLcsFund getSearchBean() {
		return searchBean;
	}

	public void setSearchBean(FiLcsFund searchBean) {
		this.searchBean = searchBean;
	}

	public List<FiCodeItem> getFundStatus() {
		return fundStatus;
	}

	public void setFundStatus(List<FiCodeItem> fundStatus) {
		this.fundStatus = fundStatus;
	}

	public List<FiCodeItem> getFundType() {
		return fundType;
	}

	public void setFundType(List<FiCodeItem> fundType) {
		this.fundType = fundType;
	}

	public FiLcsFundAttach getFiLcsFundAttach() {
		return fiLcsFundAttach;
	}

	public void setFiLcsFundAttach(FiLcsFundAttach fiLcsFundAttach) {
		this.fiLcsFundAttach = fiLcsFundAttach;
	}

	public List<FiLcsFundAttach> getFundAttachs() {
		return fundAttachs;
	}

	public void setFundAttachs(List<FiLcsFundAttach> fundAttachs) {
		this.fundAttachs = fundAttachs;
	}
}