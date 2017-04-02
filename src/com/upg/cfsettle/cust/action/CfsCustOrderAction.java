package com.upg.cfsettle.cust.action;

import com.upg.cfsettle.cust.core.CustOrderBean;
import com.upg.cfsettle.cust.core.ICfsPrjOrderService;
import com.upg.ucars.framework.base.BaseAction;

/**
 * Created by zuobaoshi on 2017/4/2.
 */
public class CfsCustOrderAction extends BaseAction {

    private ICfsPrjOrderService prjOrderService;

    private CustOrderBean searchBean;

    public String main(){
        return MAIN;
    }


    public String list(){
        System.out.print("xxx");
        return setDatagridInputStreamData(prjOrderService.findByCondition(searchBean,getPg()),getPg());
    }

    public String toView(){
        //TODO
        return VIEW;
    }

    public ICfsPrjOrderService getPrjOrderService() {
        return prjOrderService;
    }

    public void setPrjOrderService(ICfsPrjOrderService prjOrderService) {
        this.prjOrderService = prjOrderService;
    }

    public CustOrderBean getSearchBean() {
        return searchBean;
    }

    public void setSearchBean(CustOrderBean searchBean) {
        this.searchBean = searchBean;
    }
}
