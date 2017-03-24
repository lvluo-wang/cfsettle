package com.upg.ucars.basesystem.security.core.user;
/**
 * 修改密码实体 
 *
 * @author shentuwy
 * 
 */
public class PasswordChg {
	/** 旧密码 */
	private String oldPassword;
	/** 新密码 */
	private String newPassword;
	/** 确认密码 */
	private String confirmPassword;
	/** 用户ID */
	private Long sysUserID;
	/** 用户编号 */
	private String userNo;
	
	/** 接入点 */
	private String miNo;
	
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public Long getSysUserID() {
		return sysUserID;
	}
	public void setSysUserID(Long sysUserID) {
		this.sysUserID = sysUserID;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getMiNo() {
		return miNo;
	}
	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}
	
}
