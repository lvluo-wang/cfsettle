package com.upg.cfsettle.cust.core;

import java.util.Date;

/**
 * Created by zuobaoshi on 2017/4/9.
 */
public class CfsCustInfo {
    private Integer id;
    private String realName;
    private String mobile;
    private String idCard;
    private Byte sex;
    private Byte isValid;
    private Integer ctime;

    private Integer sysId;
    private Long sysIdLong;

    private String teamName;
    private String deptName;
    private String areaName;


    public Integer getCtime() {
        return ctime;
    }

    public Long getSysIdLong() {
        return sysIdLong;
    }

    public void setSysIdLong(Long sysIdLong) {
        this.sysIdLong = sysIdLong;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Byte getIsValid() {
        return isValid;
    }

    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
    }


    public void setCtime(Integer ctime) {
        this.ctime = ctime;
    }

    public Integer getSysId() {
        return sysId;
    }

    public void setSysId(Integer sysId) {
        this.sysId = sysId;
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
