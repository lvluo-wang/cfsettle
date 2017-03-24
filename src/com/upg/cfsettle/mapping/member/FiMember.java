package com.upg.cfsettle.mapping.member;

import java.util.Date;


public class FiMember {

	private Long id;
	/** 名称 */
	private String miName;
	/** 接入点类型 1：银行，2：财务公司，3：企业 */
	private String miType;
	/** 对应USC的编码 */
	private String uscMiNo;
	/** 接入点编号 */
	private String fiMiNo;
	/** 接入点密钥 */
	private String miSecret;
	/** 测试密钥 */
	private String miSecretTest;
	/** 接入点地址 */
	private String miHost;
	private Date ctime;
	/** 状态 1可用 2不可用 */
	private String status;
	/** 是否是共享标投放站点 **/
	private Integer miProjectType;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMiName() {
		return miName;
	}
	public void setMiName(String miName) {
		this.miName = miName;
	}
	public String getMiType() {
		return miType;
	}
	public void setMiType(String miType) {
		this.miType = miType;
	}
	public String getUscMiNo() {
		return uscMiNo;
	}
	public void setUscMiNo(String uscMiNo) {
		this.uscMiNo = uscMiNo;
	}
	public String getFiMiNo() {
		return fiMiNo;
	}
	public void setFiMiNo(String fiMiNo) {
		this.fiMiNo = fiMiNo;
	}
	public String getMiSecret() {
		return miSecret;
	}
	public void setMiSecret(String miSecret) {
		this.miSecret = miSecret;
	}
	public String getMiSecretTest() {
		return miSecretTest;
	}
	public void setMiSecretTest(String miSecretTest) {
		this.miSecretTest = miSecretTest;
	}
	public String getMiHost() {
		return miHost;
	}
	public void setMiHost(String miHost) {
		this.miHost = miHost;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getMiProjectType() {
		return miProjectType;
	}

	public void setMiProjectType(Integer miProjectType) {
		this.miProjectType = miProjectType;
	}
}