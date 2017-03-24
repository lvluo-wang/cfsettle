/*
 * 源程序名称: ReBrchSubsystem.java 
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
 * 功能说明：机构与子系统中间实体
 * @author shentuwy  
 * @date 2011-6-1 下午02:49:07 
 *
 */
public class ReBrchSubsystem implements Serializable {
	private Long id;
	private Long brchId;
	private Long subsysId;
	private Integer version;
	public ReBrchSubsystem() {
	}
	public ReBrchSubsystem(Long brchId, Long subsysId) {
		super();
		this.brchId = brchId;
		this.subsysId = subsysId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBrchId() {
		return brchId;
	}
	public void setBrchId(Long brchId) {
		this.brchId = brchId;
	}
	public Long getSubsysId() {
		return subsysId;
	}
	public void setSubsysId(Long subsysId) {
		this.subsysId = subsysId;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
}
