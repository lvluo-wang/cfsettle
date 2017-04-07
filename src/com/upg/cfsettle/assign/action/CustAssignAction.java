package com.upg.cfsettle.assign.action;

import com.upg.cfsettle.assign.core.IBuserService;
import com.upg.cfsettle.cust.core.ICfsCustService;
import com.upg.cfsettle.mapping.prj.CfsCust;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.mapping.basesystem.security.Buser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by zuo on 2017/4/6.
 */
public class CustAssignAction extends BaseAction{


    @Autowired
    private IBuserService buserService;
    @Autowired
    private ICfsCustService custService;

    private Buser searchBean;

    private String loginPosCode;
    private List<CfsCust> custList;
    private Long buserId;

    public String main(){
        loginPosCode = SessionTool.getUserLogonInfo().getPosCode();
        return SUCCESS;
    }

    public String list(){
        return setDatagridInputStreamData(buserService.queryBuser(searchBean,getPg()),getPg());
    }

    public String  toAssign(){
         buserId = getPKId();
        return SUCCESS;
    }

    //员工下关联的客户
    public String custList(){
        return setInputStreamData(custService.findAllCustByBuserId(buserId));
    }

    public Buser getSearchBean() {
        return searchBean;
    }

    public void setSearchBean(Buser searchBean) {
        this.searchBean = searchBean;
    }

    public String getLoginPosCode() {
        return loginPosCode;
    }

    public void setLoginPosCode(String loginPosCode) {
        this.loginPosCode = loginPosCode;
    }

    public List<CfsCust> getCustList() {
        return custList;
    }

    public void setCustList(List<CfsCust> custList) {
        this.custList = custList;
    }

    public Long getBuserId() {
        return buserId;
    }

    public void setBuserId(Long buserId) {
        this.buserId = buserId;
    }
}
