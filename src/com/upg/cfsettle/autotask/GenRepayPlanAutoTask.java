package com.upg.cfsettle.autotask;

import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.ucars.basesystem.autotask.core.AbstractMemberAutoTask;
import com.upg.ucars.model.BooleanResult;

/**
 * 还款计划定时任务
 * @author renzhuolun
 * @date 2017年4月6日 上午11:12:33
 * @version <b>1.0.0</b>
 */
public class GenRepayPlanAutoTask extends AbstractMemberAutoTask {
	private IPrjService prjService;
	/**
	 * 自动抓取数据
	 */
	@Override
	public BooleanResult runByMember(String memberNo) throws Exception {
		BooleanResult result = new BooleanResult(true);
		prjService.genRepayPlanAutoTask();
		return result;
	}
	public void setPrjService(IPrjService prjService) {
		this.prjService = prjService;
	}
	
	
}
