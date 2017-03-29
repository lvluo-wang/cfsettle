package com.upg.cfsettle.organization.core;

/**
 * Created by zuo on 2017/3/29.
 */
public class OrgTeamBean {

    private String teamName;
    private Long ownedArea;
    private Long ownedDept;
    private Byte status;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Long getOwnedArea() {
        return ownedArea;
    }

    public void setOwnedArea(Long ownedArea) {
        this.ownedArea = ownedArea;
    }

    public Long getOwnedDept() {
        return ownedDept;
    }

    public void setOwnedDept(Long ownedDept) {
        this.ownedDept = ownedDept;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
