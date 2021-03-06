package com.upg.cfsettle.prj.action;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.upg.cfsettle.code.core.ICodeItemService;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjExt;
import com.upg.cfsettle.mapping.prj.CfsPrjLoanLog;
import com.upg.cfsettle.prj.core.IPrjExtService;
import com.upg.cfsettle.prj.core.IPrjLoanLogService;
import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.cfsettle.util.CfsConstant;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.factory.DynamicPropertyTransfer;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.util.PropertyTransVo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zuo on 2017/3/30.
 */
public class PrjAction extends BaseAction {

    private CfsPrj searchBean;

    private ICodeItemService codeItemService;

    private IPrjService prjService;

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
        prjStatusList = codeItemService.getCodeItemByKey(UtilConstant.CFS_PRJ_STATUS);
        return MAIN;
    }

    public String list(){
        return setDatagridInputStreamData(prjService.findByCondition(searchBean,getPg()),getPg());
    }

    public String toAdd(){
        bankList = codeItemService.getCodeItemByKey(UtilConstant.CFS_BANK_TYPE);
        repaymentTypeList = codeItemService.getCodeItemByKey(UtilConstant.CFS_REPAYMENT_TYPE);
        timeLimitUnitList = codeItemService.getCodeItemByKey(UtilConstant.CFS_TIMELIMIT_UNIT);
        return ADD;
    }

    public void doApply(){
        prjService.savePrjAndPrjExt(prj,prjExt);

    }

    public String toEdit(){
        bankList = codeItemService.getCodeItemByKey(UtilConstant.CFS_BANK_TYPE);
        repaymentTypeList = codeItemService.getCodeItemByKey(UtilConstant.CFS_REPAYMENT_TYPE);
        timeLimitUnitList = codeItemService.getCodeItemByKey(UtilConstant.CFS_TIMELIMIT_UNIT);
        prj = prjService.getPrjById(getPKId());
        prjExt = prjExtService.getPrjExtByPrjId(getPKId());
        return EDIT;
    }

    public void doEdit(){
        try {
            prjService.updatePrjAndPrjExt(prj,prjExt);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public String toView(){
        prj = prjService.getPrjById(getPKId());
        prjExt = prjExtService.getPrjExtByPrjId(getPKId());
        return VIEW;
    }

    public String auditMain(){
        prjStatusList = codeItemService.getCodeItemByKey(UtilConstant.CFS_PRJ_STATUS);
        return "auditMain";
    }

    public String toReview(){
        prj = prjService.getPrjById(getPKId());
        prjExt = prjExtService.getPrjExtByPrjId(getPKId());
        return "toReview";
    }

    public String toBuild(){
        prj = prjService.getPrjById(getPKId());
        return "toBuild";
    }

    /**
     * 成立项目
     */
    public void doBuild(){
        prjService.buildPrj(prj);
    }


    /**
     * 放款记录
     * @return
     */
    public String loanIssue(){
        List<CfsPrjLoanLog> list = loanLogService.findByCondition(prjLoanLog,getPg());
        CfsPrj prj = prjService.getPrjById(prjLoanLog.getPrjId());
        BigDecimal remainingLoanedAmount = prj.getDemandAmount().subtract(prj.getRemainingAmount()).subtract(prj.getLoanedAmount());
        for(CfsPrjLoanLog prjLoanLog : list){
            prjLoanLog.setLoanedAmount(prj.getLoanedAmount());
            prjLoanLog.setRemainingLoanedAmount(remainingLoanedAmount);
        }
        List<PropertyTransVo> trans = new ArrayList<PropertyTransVo>();
        trans.add(new PropertyTransVo("csysid", Buser.class, "userId", "userName","sysUserName"));
        list = DynamicPropertyTransfer.transform(list, trans);
        return setDatagridInputStreamData(list,getPg());
    }

    /**
     * 项目审核通过
     */
    public void doReview(){
        prj.setStatus(CfsConstant.PRJ_STATUS_INVESTING);
        prjService.auditPrjAndPrjExt(prj,prjExt);
    }

    /**
     * 项目审核拒绝
     */
    public void doReviewReject(){
        prj.setStatus(CfsConstant.PRJ_STATUS_REFUSE);
        prjService.auditPrjAndPrjExt(prj,prjExt);
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

    public ICodeItemService getCodeItemService() {
        return codeItemService;
    }

    public void setCodeItemService(ICodeItemService codeItemService) {
        this.codeItemService = codeItemService;
    }

    public IPrjService getPrjService() {
        return prjService;
    }

    public void setPrjService(IPrjService prjService) {
        this.prjService = prjService;
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
