package com.upg.cfsettle.prj.action;

import com.upg.cfsettle.code.core.ICodeItemService;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjExt;
import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.framework.base.BaseAction;

import java.util.List;

/**
 * Created by zuo on 2017/3/30.
 */
public class PrjAction extends BaseAction {

    private CfsPrj searchBean;

    private ICodeItemService codeItemService;

    private IPrjService prjService;

    private CfsPrj prj;
    private CfsPrjExt prjExt;

    private List<FiCodeItem> bankList;
    private List<FiCodeItem> repaymentTypeList;
    private List<FiCodeItem> timeLimitUnitList;

    public String main(){
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
        //TODO
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
}
