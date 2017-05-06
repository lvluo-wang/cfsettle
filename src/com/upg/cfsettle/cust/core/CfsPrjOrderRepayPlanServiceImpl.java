package com.upg.cfsettle.cust.core;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.common.CodeItemUtil;
import com.upg.cfsettle.mapping.prj.CfsPrjOrderPaybackLog;
import com.upg.cfsettle.mapping.prj.CfsPrjOrderRepayPlan;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.tools.imp.excel.ExcelUtil;
import com.upg.ucars.util.DateTimeUtil;

/**
 * Created by zuobaoshi on 2017/4/4.
 */
@Service
public class CfsPrjOrderRepayPlanServiceImpl implements ICfsPrjOrderRepayPlanService {

    @Autowired
    private ICfsPrjOrderRepayPlanDao prjOrderRepayPlanDao;

    @Autowired
    private IUserService userService;

    @Override
    public List<CfsPrjOrderRepayPlan> findByOrderIdAndType(Long prjOrderId,Byte ptype) {
        List<CfsPrjOrderRepayPlan> prjOrderRepayPlanList =  prjOrderRepayPlanDao.findByOrderIdAndType(prjOrderId,ptype);
        //还款审核人姓名
        for(CfsPrjOrderRepayPlan prjOrderRepayPlan : prjOrderRepayPlanList){
            if(prjOrderRepayPlan.getPaybackAuditSysid() != null){
                Buser buser = userService.getUserById(Long.valueOf(prjOrderRepayPlan.getPaybackAuditSysid().toString()));
                if(buser != null){
                    prjOrderRepayPlan.setPaybackAuditName(buser.getUserName());
                }
            }
            prjOrderRepayPlan.setTotalPeriods(prjOrderRepayPlanList.size());
        }
        return prjOrderRepayPlanList;
    }

    @Override
    public CfsPrjOrderRepayPlan getRaiseOrderRepayPlan(Long prjOrderId) {
        List<CfsPrjOrderRepayPlan> prjOrderRepayPlanList =  prjOrderRepayPlanDao.findByOrderIdAndType(prjOrderId,Byte.valueOf("2"));
        if(prjOrderRepayPlanList != null && prjOrderRepayPlanList.size()>0){
            return prjOrderRepayPlanList.get(0);
        }
        return null;
    }

	@Override
	public void addPrjOrderRepayPlan(CfsPrjOrderRepayPlan orderPlan) {
		prjOrderRepayPlanDao.save(orderPlan);
	}

	@Override
	public List<Map<String, Object>> findByCondition(CfsPrjOrderPaybackLog searchBean, Page page) {
		return prjOrderRepayPlanDao.findByCondition(searchBean,page);
	}

	@Override
	public CfsPrjOrderRepayPlan getprjOrderPlanById(Long id) {
		return prjOrderRepayPlanDao.get(id);
	}

	@Override
	public void updatePrjOrderRepayPlan(CfsPrjOrderRepayPlan plan) {
		plan.setMtime(DateTimeUtil.getNowDateTime());
		prjOrderRepayPlanDao.update(plan);
	}

	@Override
	public List<CfsPrjOrderRepayPlan> findByOrderId(Long prjOrderId) {
		return prjOrderRepayPlanDao.findByOrderId(prjOrderId);
	}

	@Override
	public HSSFWorkbook generatUsePayBackData(OutputStream os, CfsPrjOrderPaybackLog searchBean, String[] headers, String title, Page pg) {
		List<List<Object>> dataRows = new ArrayList<List<Object>>();
		List<Map<String, Object>> dataList = this.findByCondition(searchBean, null);
		Iterator<Map<String, Object>> it = dataList.iterator();
		while(it.hasNext()){
			List<Object> row = new ArrayList<Object>();
			Map<String, Object> exportData = it.next();
			row.add(exportData.get("CONTRACT_NO"));
			row.add(exportData.get("REAL_NAME"));
			row.add(exportData.get("PRJ_NAME"));
			row.add(exportData.get("INVEST_TIME").toString());
			row.add(exportData.get("BUILD_TIME").toString());
			row.add(exportData.get("REPAY_DATE")==null?"":exportData.get("REPAY_DATE").toString().substring(0, 10));
			row.add(exportData.get("COUNT_DAY"));
			row.add(exportData.get("YEAR_RATE")+"%");
			row.add(exportData.get("PRI_INTEREST"));
			row.add(exportData.get("PRINCIPAL"));
			row.add(exportData.get("YIELD"));
			row.add("第"+exportData.get("REPAY_PERIODS")+"期");
			row.add(exportData.get("REST_PRINCIPAL"));
			row.add(exportData.get("MONEY"));
			row.add(CodeItemUtil.getCodeNameByKey(UtilConstant.CFS_PRJ_STATUS,exportData.get("PRJSTATUS").toString()));
			row.add(CodeItemUtil.getCodeNameByKey(UtilConstant.CFS_PRJ_REPAY_PLAN_STATUS,exportData.get("PLANSTATUS").toString()));
			dataRows.add(row);
		}
 		return ExcelUtil.createHSSFWorkbook(title, headers, dataRows);
	}

	@Override
	public HSSFWorkbook generatPeriodPayBackData(OutputStream os, CfsPrjOrderPaybackLog searchBean, String[] headers, String title, Page pg) {
		List<List<Object>> dataRows = new ArrayList<List<Object>>();
		List<Map<String, Object>> dataList = this.findByCondition(searchBean, null);
		Iterator<Map<String, Object>> it = dataList.iterator();
		while(it.hasNext()){
			List<Object> row = new ArrayList<Object>();
			Map<String, Object> exportData = it.next();
			row.add(exportData.get("CONTRACT_NO"));
			row.add(exportData.get("REAL_NAME"));
			row.add(exportData.get("PRJ_NAME"));
			row.add(exportData.get("INVEST_TIME").toString());
			row.add(exportData.get("BUILD_TIME").toString());
			row.add(exportData.get("REPAY_DATE")==null?"":exportData.get("REPAY_DATE").toString().substring(0, 10));
			row.add(exportData.get("COUNT_DAY"));
			row.add(exportData.get("PERIOD_RATE")+"%");
			row.add(exportData.get("PRI_INTEREST"));
			row.add(exportData.get("PRINCIPAL"));
			row.add(exportData.get("YIELD"));
			row.add("募集期");
			row.add(exportData.get("REST_PRINCIPAL"));
			row.add(exportData.get("MONEY"));
			row.add(CodeItemUtil.getCodeNameByKey(UtilConstant.CFS_PRJ_STATUS,exportData.get("PRJSTATUS").toString()));
			row.add(CodeItemUtil.getCodeNameByKey(UtilConstant.CFS_PRJ_REPAY_PLAN_STATUS,exportData.get("PLANSTATUS").toString()));
			dataRows.add(row);
		}
 		return ExcelUtil.createHSSFWorkbook(title, headers, dataRows);
	}
}
