package com.upg.cfsettle.cust.core;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.common.CodeItemUtil;
import com.upg.cfsettle.mapping.prj.CfsPrjRepayPlan;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.tools.imp.excel.ExcelUtil;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.StringUtil;

/**
 * Created by zuobaoshi on 2017/4/4.
 */
@Service
public class CfsPrjRepayPlanServiceImpl implements ICfsPrjRepayPlanService {

    @Autowired
    private ICfsPrjRepayPlanDao prjRepayPlanDao;

    @Override
    public Integer getTotalPeriodByPrjId(Long prjId) {
        List<CfsPrjRepayPlan> prjRepayPlanList = prjRepayPlanDao.findNoRaisePlanByPrjId(prjId);
        return prjRepayPlanList == null ? null : prjRepayPlanList.size();
    }

	@Override
	public void addPrjRepayPlan(CfsPrjRepayPlan plan) {
		prjRepayPlanDao.save(plan);
	}

	@Override
	public void updatePrjRepayPlan(CfsPrjRepayPlan plan) {
		prjRepayPlanDao.update(plan);
	}

	@Override
	public List<Map<String, Object>> findByCondition(CfsPrjRepayPlan searchBean, Page page) {
		if(!StringUtil.isEmpty(searchBean.getQueryType())){
			searchBean.setStartDate(DateTimeUtil.getNowDateTime());
			if(UtilConstant.TIME_LIMIT_WEEK.equals(searchBean.getQueryType())){
				searchBean.setEndDate(DateTimeUtil.addDay(DateTimeUtil.getNowDateTime(), 7));
			}else if(UtilConstant.TIME_LIMIT_MONTH.equals(searchBean.getQueryType())){
				searchBean.setEndDate(DateTimeUtil.getStringToDateMinute(DateTimeUtil.LastDateOfMonth(DateTimeUtil.toDateString(new Date()))));
			}
		}
		return prjRepayPlanDao.findByCondition(searchBean,page);
	}

	@Override
	public CfsPrjRepayPlan getPrjRepayPlanById(Long id) {
		return prjRepayPlanDao.get(id);
	}

	@Override
	public CfsPrjRepayPlan getPrjPlanByPrjIdAndPeriod(Long prjId, Long repayPeriod) {
        return prjRepayPlanDao.getPrjPlanByPrjIdAndPeriod(prjId,repayPeriod);
	}

	@Override
	public List<CfsPrjRepayPlan> getPrjPlanByPrjId(Long prjId) {
		return prjRepayPlanDao.getPrjPlanByPrjId(prjId);
	}

	@Override
	public HSSFWorkbook generatPrjPayBackData(OutputStream os, CfsPrjRepayPlan searchBean, String[] headers, String title, Page pg) {
		List<List<Object>> dataRows = new ArrayList<List<Object>>();
		List<Map<String, Object>> dataList = this.findByCondition(searchBean, null);
		Iterator<Map<String, Object>> it = dataList.iterator();
		while(it.hasNext()){
			List<Object> row = new ArrayList<Object>();
			Map<String, Object> exportData = it.next();
			row.add(exportData.get("PRJ_NAME"));
			row.add(exportData.get("PRJ_MOBILE"));
			row.add(exportData.get("ACT_AMOUNT"));
			row.add(exportData.get("YEAR_RATE")+"%");
			row.add(exportData.get("TIME_LIMIT")+CodeItemUtil.getCodeNameByKey(UtilConstant.CFS_TIMELIMIT_UNIT,exportData.get("TIME_LIMIT_UNIT").toString()));
			row.add(exportData.get("REPAY_DATE").toString().substring(0, 10));
			row.add(exportData.get("PRI_INTEREST"));
			row.add(exportData.get("PRINCIPAL"));
			row.add(exportData.get("YIELD"));
			row.add(exportData.get("REPAY_DATE"));
			row.add(exportData.get("REPAY_PERIODS").toString().equals("0")?"募集期":"第"+exportData.get("REPAY_PERIODS")+"期");
			row.add(CodeItemUtil.getCodeNameByKey(UtilConstant.CFS_PRJ_REPAY_PLAN_STATUS,exportData.get("STATUS").toString()));
			dataRows.add(row);
		}
 		return ExcelUtil.createHSSFWorkbook(title, headers, dataRows);
	}
}
