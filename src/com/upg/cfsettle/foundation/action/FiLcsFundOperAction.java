package com.upg.cfsettle.foundation.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.ucars.basesystem.UcarsHelper;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.cfsettle.common.CodeItemUtil;
import com.upg.cfsettle.foundation.core.IFiLcsFundAttachService;
import com.upg.cfsettle.foundation.core.IFiLcsFundService;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.filcs.FiLcsFund;
import com.upg.cfsettle.mapping.filcs.FiLcsFundAttach;
import com.upg.cfsettle.util.LcsConstant;


@SuppressWarnings("serial")
public class FiLcsFundOperAction extends BaseAction {
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
	 * 基金信息置顶
	 * @author renzhuolun
	 * @date 2016年6月6日 上午11:12:43
	 * @return
	 */
	public void toUpper() {
		fiLcsFundService.doUpperLcsFundById(getPKId());
	}
	
	/**
	 * 设为活动产品
	 * @author renzhuolun
	 * @date 2016年6月14日 上午11:29:15
	 */
	public void toActive(){
		fiLcsFundService.doActiveLcsFundById(getPKId());
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
	 * 跳转运作信息修改页面
	 * @author renzhuolun
	 * @date 2016年6月13日 下午10:32:52
	 */
	public String toEditOperInfo() {
		fiLcsFund = fiLcsFundService.getFiLcsFundById(getPKId());
		return SUCCESS;
	}
	
	/**
	 * 修改运作信息
	 * @author renzhuolun
	 * @date 2016年6月13日 下午10:39:54
	 */
	public void doEditOperInfo(){
		fiLcsFundService.doEditOperInfo(fiLcsFund);
	}
	
	/**
	 * 条件查询
	 * @author renzhuolun
	 * @date 2016年6月6日 上午11:45:14
	 * @return
	 */
	public String list(){
		List<FiLcsFund> list = fiLcsFundService.findOperByCondition(searchBean, getPg());
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
	
	/**
	 * 跳转修改预售开始时间
	 * @author renzhuolun
	 * @date 2016年6月14日 上午11:47:29
	 * @return
	 */
	public String toEditStartTime() {
		fiLcsFund = fiLcsFundService.getFiLcsFundById(getPKId());
		if(Byte.valueOf("0").equals(fiLcsFund.getFundStatus())){
			UcarsHelper.throwActionException("封闭期不允许修改预售开始时间");
		}
		return SUCCESS;
	}
	
	/**
	 * 修改预售开始时间
	 * @author renzhuolun
	 * @date 2016年6月13日 下午10:39:54
	 */
	public void doEditStartTime(){
		fiLcsFundService.doEditStartTime(fiLcsFund);
	}
	
	/**
	 * 跳转修改预售结束时间
	 * @author renzhuolun
	 * @date 2016年6月14日 上午11:47:29
	 * @return
	 */
	public String toEditEndTime() {
		fiLcsFund = fiLcsFundService.getFiLcsFundById(getPKId());
		if(fiLcsFund.getFundStatus().equals(Byte.valueOf("0"))){
			UcarsHelper.throwActionException("封闭期不允许修改预售结束时间");
		}
		return SUCCESS;
	}
	
	/**
	 * 修改预售结束时间
	 * @author renzhuolun
	 * @date 2016年6月13日 下午10:39:54
	 */
	public void doEditEndTime(){
		fiLcsFundService.doEditEndTime(fiLcsFund);
	}
	
	/**
	 * 撤销基金项目
	 * @author renzhuolun
	 * @date 2016年6月14日 下午5:10:42
	 */
	public  void undoFund(){
		fiLcsFundService.undoFund(getPKId());
	}
	
	/**
	 * 跳转修改基金状态页面
	 * @author renzhuolun
	 * @date 2016年7月12日 下午2:52:05
	 * @return
	 */
	public String toEditFundStatus() {
		fiLcsFund = fiLcsFundService.getFiLcsFundById(getPKId());
		fundStatus = CodeItemUtil.getCodeItemsByKey(LcsConstant.LCS_FUND_STATUS);
		return SUCCESS;
	}
	
	/**
	 * 修改基金状态页面
	 * @author renzhuolun
	 * @date 2016年7月12日 下午2:52:49
	 */
	public void doEditFundStatus(){
		fiLcsFundService.doEditFundStatus(fiLcsFund);
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