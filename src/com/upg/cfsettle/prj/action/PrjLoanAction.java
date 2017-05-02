package com.upg.cfsettle.prj.action;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.common.CodeItemUtil;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjExt;
import com.upg.cfsettle.mapping.prj.CfsPrjLoanLog;
import com.upg.cfsettle.prj.core.IPrjExtService;
import com.upg.cfsettle.prj.core.IPrjLoanLogService;
import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.factory.DynamicPropertyTransfer;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.PropertyTransVo;

@SuppressWarnings("serial")
public class PrjLoanAction extends BaseAction {

    private CfsPrj searchBean;
    
    @Autowired
    private IPrjService prjService;
    @Autowired
    private IPrjExtService prjExtService;
    @Autowired
    private IPrjLoanLogService loanLogService;

    private CfsPrj prj;
    private CfsPrjExt prjExt;
    
    private CfsPrjLoanLog prjLoanLog;

    private List<FiCodeItem> bankList;
    private List<FiCodeItem> repaymentTypeList;
    private List<FiCodeItem> timeLimitUnitList;
    private List<FiCodeItem> prjStatusList;


    public String main(){
        prjStatusList = CodeItemUtil.getCodeItemsByKey(UtilConstant.CFS_PRJ_STATUS);
        return SUCCESS;
    }

    public String list(){
        return setDatagridInputStreamData(prjService.findLoanPrjByCondition(searchBean,getPg()),getPg());
    }

    public String toAdd(){
        bankList = CodeItemUtil.getCodeItemsByKey(UtilConstant.CFS_BANK_TYPE);
        repaymentTypeList = CodeItemUtil.getCodeItemsByKey(UtilConstant.CFS_REPAYMENT_TYPE);
        timeLimitUnitList = CodeItemUtil.getCodeItemsByKey(UtilConstant.CFS_TIMELIMIT_UNIT);
        prj = prjService.getPrjById(getPKId());
        prjExt = prjExtService.getPrjExtByPrjId(getPKId());
        return SUCCESS;
    }

    public void doLoanAdd(){
    	loanLogService.addPrjLoanLog(prjLoanLog);

    }

    public String toView(){
        prj = prjService.getPrjById(getPKId());
        prjExt = prjExtService.getPrjExtByPrjId(getPKId());
        return SUCCESS;
    }
    
    public String listLoan(){
    	List<CfsPrjLoanLog> list = loanLogService.findByCondition(prjLoanLog,getPg());
    	List<PropertyTransVo> trans = new ArrayList<PropertyTransVo>();
    	trans.add(new PropertyTransVo("csysid", Buser.class, "userId", "userName","sysUserName"));
    	list = DynamicPropertyTransfer.transform(list, trans);
        return setDatagridInputStreamData(list,getPg());
    }
    
    
    public void doExport() throws Exception{
    	HttpServletResponse response = getHttpResponse();
		HSSFWorkbook workbook = null;
		OutputStream os = null;
		/*searchBean.setStatus(LcsConstant.FUND_ORDER_BOOK_AUDIT);*/
		try {
			os = response.getOutputStream(); // 取得输出流
			response.reset();// 清空输出流
			String fileName = "放款录入" + DateTimeUtil.getCurDate() + ".xls";
			response.setHeader("Content-disposition","attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));// 设定输出文件头
			response.setContentType("application/msexcel");// 定义输出类型
			String title = "放款录入";
			String[] headers = new String[] {"项目名称", "计划募集金额(元)", "实际募集金额(元)","项目期限","年利率","下期还款时间", "项目收款银行","项目收款支行","项目收款账号","已放款金额(元)","待放款金额(元)", "状态"};
			workbook = prjService.generatLoanData(os, searchBean,headers,title,getPg());
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

    public CfsPrj getSearchBean() {
        return searchBean;
    }

    public void setSearchBean(CfsPrj searchBean) {
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

	public CfsPrjLoanLog getPrjLoanLog() {
		return prjLoanLog;
	}

	public void setPrjLoanLog(CfsPrjLoanLog prjLoanLog) {
		this.prjLoanLog = prjLoanLog;
	}
    
}