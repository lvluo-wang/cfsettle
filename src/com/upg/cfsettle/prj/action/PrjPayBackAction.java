package com.upg.cfsettle.prj.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.upg.ucars.framework.base.Page;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.common.CodeItemUtil;
import com.upg.cfsettle.cust.core.ICfsPrjRepayPlanService;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjExt;
import com.upg.cfsettle.mapping.prj.CfsPrjPaybackLog;
import com.upg.cfsettle.mapping.prj.CfsPrjRepayPlan;
import com.upg.cfsettle.prj.core.IPrjExtService;
import com.upg.cfsettle.prj.core.IPrjPaybackLogService;
import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.util.DateTimeUtil;

@SuppressWarnings("serial")
public class PrjPayBackAction extends BaseAction {

    private CfsPrjRepayPlan searchBean;
    
    @Autowired
    private IPrjService prjService;
    @Autowired
    private IPrjExtService prjExtService;
    @Autowired 
    private IPrjPaybackLogService paybackLogService;
    @Autowired
    private ICfsPrjRepayPlanService prjRepayPlanService;

    private CfsPrj prj;
    private CfsPrjExt prjExt;
    
    private CfsPrjPaybackLog paybackLog;
    
    private CfsPrjRepayPlan repayPlan;

    private List<FiCodeItem> bankList;
    private List<FiCodeItem> repaymentTypeList;
    private List<FiCodeItem> timeLimitUnitList;
    private List<FiCodeItem> prjStatusList;
    //用于项目详情中的回款记录查询
    private Long prjId;
    
    private Integer totalPeriod;
    

    public String main(){
        prjStatusList = CodeItemUtil.getCodeItemsByKey(UtilConstant.CFS_PRJ_REPAY_PLAN_STATUS);
        return SUCCESS;
    }

    public String list(){
        return setDatagridInputStreamData(prjRepayPlanService.findByCondition(searchBean,getPg()),getPg());
    }

    public String toAdd(){
        bankList = CodeItemUtil.getCodeItemsByKey(UtilConstant.CFS_BANK_TYPE);
        repaymentTypeList = CodeItemUtil.getCodeItemsByKey(UtilConstant.CFS_REPAYMENT_TYPE);
        timeLimitUnitList = CodeItemUtil.getCodeItemsByKey(UtilConstant.CFS_TIMELIMIT_UNIT);
        repayPlan =  prjRepayPlanService.getPrjRepayPlanById(getPKId());
        totalPeriod = prjRepayPlanService.getTotalPeriodByPrjId(repayPlan.getPrjId());
        prj = prjService.getPrjById(repayPlan.getPrjId());
        prjExt = prjExtService.getPrjExtByPrjId(repayPlan.getPrjId());
        return SUCCESS;
    }

    public void doPayBackAdd(){
    	paybackLogService.addPrjPayBackLog(paybackLog);

    }

    public String toView(){
    	repayPlan =  prjRepayPlanService.getPrjRepayPlanById(getPKId());
        prj = prjService.getPrjById(repayPlan.getPrjId());
        totalPeriod = prjRepayPlanService.getTotalPeriodByPrjId(repayPlan.getPrjId());
        prjExt = prjExtService.getPrjExtByPrjId(getPKId());
        return SUCCESS;
    }
    
    public String listPayBack(){
    	List<Map<String,Object>> list = paybackLogService.findByCondition(paybackLog,getPg());
        return setDatagridInputStreamData(list,getPg());
    }

    public String listPayBackLog(){
        List<Map<String,Object>> list = paybackLogService.findByPrjPaybackLogByPrjId(prjId);
        return setDatagridInputStreamData(list,new Page());
    }
    
    public void doExport() throws Exception{
    	HttpServletResponse response = getHttpResponse();
		HSSFWorkbook workbook = null;
		OutputStream os = null;
		try {
			os = response.getOutputStream(); // 取得输出流
			response.reset();// 清空输出流
			String fileName = "回款录入" + DateTimeUtil.getCurDate() + ".xls";
			response.setHeader("Content-disposition","attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			String title = "回款录入";
			String[] headers = new String[] {"项目名", "项目电话", "实际募集金额(元)","项目利率","项目期限","还款时间", "回款本息(元)","回款本金(元)",
					"回款利息(元)","回款截止时间","回款期数","状态"};
			workbook = prjRepayPlanService.generatPrjPayBackData(os, searchBean,headers,title,getPg());
			workbook.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			os.flush();
		}
    }

    public List<FiCodeItem> getPrjStatusList() {
        return prjStatusList;
    }

    public void setPrjStatusList(List<FiCodeItem> prjStatusList) {
        this.prjStatusList = prjStatusList;
    }

    public IPrjExtService getPrjExtService() {
        return prjExtService;
    }

    public void setPrjExtService(IPrjExtService prjExtService) {
        this.prjExtService = prjExtService;
    }

    public CfsPrjRepayPlan getSearchBean() {
		return searchBean;
	}

	public void setSearchBean(CfsPrjRepayPlan searchBean) {
		this.searchBean = searchBean;
	}

	public List<FiCodeItem> getBankList() {
        return bankList;
    }

    public void setBankList(List<FiCodeItem> bankList) {
        this.bankList = bankList;
    }

    public List<FiCodeItem> getRepaymentTypeList() {
        return repaymentTypeList;
    }

    public void setRepaymentTypeList(List<FiCodeItem> repaymentTypeList) {
        this.repaymentTypeList = repaymentTypeList;
    }

    public List<FiCodeItem> getTimeLimitUnitList() {
        return timeLimitUnitList;
    }

    public void setTimeLimitUnitList(List<FiCodeItem> timeLimitUnitList) {
        this.timeLimitUnitList = timeLimitUnitList;
    }

    public CfsPrj getPrj() {
        return prj;
    }

    public void setPrj(CfsPrj prj) {
        this.prj = prj;
    }

    public CfsPrjExt getPrjExt() {
        return prjExt;
    }

    public void setPrjExt(CfsPrjExt prjExt) {
        this.prjExt = prjExt;
    }

	public CfsPrjPaybackLog getPaybackLog() {
		return paybackLog;
	}

	public void setPaybackLog(CfsPrjPaybackLog paybackLog) {
		this.paybackLog = paybackLog;
	}

	public CfsPrjRepayPlan getRepayPlan() {
		return repayPlan;
	}

	public void setRepayPlan(CfsPrjRepayPlan repayPlan) {
		this.repayPlan = repayPlan;
	}

    public Long getPrjId() {
        return prjId;
    }

    public void setPrjId(Long prjId) {
        this.prjId = prjId;
    }

	public Integer getTotalPeriod() {
		return totalPeriod;
	}

	public void setTotalPeriod(Integer totalPeriod) {
		this.totalPeriod = totalPeriod;
	}
}