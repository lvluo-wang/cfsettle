package com.upg.cfsettle.organization.core;

import java.io.Serializable;

/**
 * Created by zuo on 2017/3/29.
 */
public class OrgTeamBean implements Serializable{

    /**
	 * TODO（用一句话描述这个变量表示什么）
	 */
	private static final long serialVersionUID = 6514856931798487180L;
	private Long teamId;
    private String teamName;
    private Long ownedArea;
    private Long ownedDept;
    private Byte status;
    private String posCode;
    
    private String havBuser;
    
    private String oldBuserStr;
    
    private String newBuserStr;

    public OrgTeamBean(Byte status) {
        this.status = status;
    }

    public OrgTeamBean(Long ownedDept, Byte status) {
        this.ownedDept = ownedDept;
        this.status = status;
    }

    public OrgTeamBean(Byte status, Long teamId) {
        this.status = status;
        this.teamId = teamId;
    }

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

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public OrgTeamBean() {
	}

	public String getPosCode() {
		return posCode;
	}

	public void setPosCode(String posCode) {
		this.posCode = posCode;
	}

	public String getHavBuser() {
		return havBuser;
	}

	public void setHavBuser(String havBuser) {
		this.havBuser = havBuser;
	}

	public String getOldBuserStr() {
		return oldBuserStr;
	}

	public void setOldBuserStr(String oldBuserStr) {
		this.oldBuserStr = oldBuserStr;
	}

	public String getNewBuserStr() {
		return newBuserStr;
	}

	public void setNewBuserStr(String newBuserStr) {
		this.newBuserStr = newBuserStr;
	}
}