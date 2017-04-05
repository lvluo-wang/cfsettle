package com.upg.cfsettle.prj.action;

import java.util.ArrayList;
import java.util.List;

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
import com.upg.ucars.util.PropertyTransVo;

@SuppressWarnings("serial")
public class PrjPayBackAction extends BaseAction {

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