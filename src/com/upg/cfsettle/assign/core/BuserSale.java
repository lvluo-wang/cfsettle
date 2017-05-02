package com.upg.cfsettle.assign.core;

import com.upg.ucars.mapping.basesystem.security.Buser;

/**
 * Created by zuo on 2017/4/6.
 */
public class BuserSale extends Buser {

    private Integer custNum;
    private String teamName;
    private String deptName;
    private String areaName;

    public Integer getCustNum() {
        return custNum;
    }

    public void setCustNum(Integer custNum) {
        this.custNum = custNum;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
