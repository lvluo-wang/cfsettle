package com.upg.ucars.framework.bpm.assist.action;

import java.text.ParseException;
import java.util.Date;

import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.bpm.assist.service.IBpmDataClearService;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.SourceTemplate;

public class BpmDataClearAction extends BaseAction{
	
	private Date clearDate;
	private Long clearPiCount;
	private Long piCount;
	private Long endPiCount;
	private Long unEndCount;
	
	private Long[] rsCount;
	/**
	 * 进入主页面
	 * @return
	 */
	public String main(){
		//默认删除一周前结束流程实例
		clearDate = DateTimeUtil.getDate(DateTimeUtil.getNowDateTime(), -7);
		return MAIN;
	}
	
	
	public String toClearConfirm(){
		IBpmDataClearService service = SourceTemplate.getBean(IBpmDataClearService.class,BeanNameConstants.BPM_DATA_CLEAR_SERVICE);		
		clearPiCount = service.queryEndProcessInstanceCountByDate(clearDate);	
		piCount = service.queryProcessInstanceCount();
		endPiCount = service.queryEndProcessInstanceCount();		
		unEndCount = service.queryUnEndProcessInstanceCount();		
		
		return "confirm";
	}
	
	public String dataClear(){
		IBpmDataClearService service = SourceTemplate.getBean(IBpmDataClearService.class,BeanNameConstants.BPM_DATA_CLEAR_SERVICE);
		rsCount = service.clearEndProcessInstanceData(clearDate);
		return "result";
	}


	public Date getClearDate() {
		return clearDate;
	}


	public void setClearDate(Date clearDate) {
		this.clearDate = clearDate;
	}
	
	public void setClearDate(String clearDate) {
		
		try {
			this.clearDate = DateTimeUtil.parse(clearDate, "yyyy-MM-dd");
		} catch (ParseException e) {
			
		}
	}


	public Long getClearPiCount() {
		return clearPiCount;
	}


	public void setClearPiCount(Long clearPiCount) {
		this.clearPiCount = clearPiCount;
	}


	public Long getPiCount() {
		return piCount;
	}


	public void setPiCount(Long piCount) {
		this.piCount = piCount;
	}


	public Long getEndPiCount() {
		return endPiCount;
	}


	public void setEndPiCount(Long endPiCount) {
		this.endPiCount = endPiCount;
	}


	public Long getUnEndCount() {
		return unEndCount;
	}


	public void setUnEndCount(Long unEndCount) {
		this.unEndCount = unEndCount;
	}


	public Long[] getRsCount() {
		return rsCount;
	}


	public void setRsCount(Long[] rsCount) {
		this.rsCount = rsCount;
	}

}
