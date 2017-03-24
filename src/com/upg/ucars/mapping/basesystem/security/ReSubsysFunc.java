/*
 * 源程序名称: ReSubsysFunc.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 技术平台：XCARS
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.mapping.basesystem.security;

import java.io.Serializable;
/**
 * 
 * 功能说明：子系统与权限中间实体
 * @author shentuwy  
 * @date 2011-5-30 下午04:05:13 
 *
 */
public class ReSubsysFunc implements Serializable {
	private Long id;
	private Long subsysId;//子系统id
	private Long funcId;//权限id
	private Integer version;
	public ReSubsysFunc(){}
	public ReSubsysFunc(Long subsysId, Long funcId) {
		super();
		this.subsysId = subsysId;
		this.funcId = funcId;
	}
	public Long getSubsysId() {
		return subsysId;
	}
	public void setSubsysId(Long subsysId) {
		this.subsysId = subsysId;
	}
	public Long getFuncId() {
		return funcId;
	}
	public void setFuncId(Long funcId) {
		this.funcId = funcId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}

	
}
