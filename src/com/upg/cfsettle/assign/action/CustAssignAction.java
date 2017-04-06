package com.upg.cfsettle.assign.action;

import com.upg.cfsettle.assign.core.IBuserService;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.mapping.basesystem.security.Buser;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zuo on 2017/4/6.
 */
public class CustAssignAction extends BaseAction{


    @Autowired
    private IBuserService buserService;

    private Buser searchBean;

    public String main(){
        return MAIN;
    }

    public String list(){
        return setDatagridInputStreamData(buserService.queryBuser(searchBean,getPg()),getPg());
    }
}
