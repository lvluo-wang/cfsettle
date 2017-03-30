package com.upg.cfsettle.prj.action;

import com.upg.cfsettle.code.core.ICodeItemService;
import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.prj.core.IPrjService;
import com.upg.ucars.framework.base.BaseAction;

/**
 * Created by zuo on 2017/3/30.
 */
public class PrjAction extends BaseAction {


    private CfsPrj searchBean;

    private ICodeItemService codeItemService;

    private IPrjService prjService;

    public String main(){
        return MAIN;
    }

    public String list(){
        return setDatagridInputStreamData(prjService.findByCondition(searchBean,getPg()),getPg());
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
}
