package com.upg.cfsettle.assign.core;

import com.upg.ucars.mapping.basesystem.security.Buser;

/**
 * Created by zuo on 2017/4/6.
 */
public class BuserSale extends Buser {

    private Integer custNum;

    public Integer getCustNum() {
        return custNum;
    }

    public void setCustNum(Integer custNum) {
        this.custNum = custNum;
    }
}
