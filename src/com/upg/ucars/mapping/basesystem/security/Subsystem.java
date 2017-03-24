/*
 * 源程序名称: Subsystem.java 
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
 * 功能说明：子系统实体类
 * @author shentuwy  
 * @date 2011-5-30 下午03:55:03 
 *
 */
public class Subsystem implements Serializable {
	public static final String STATUS_OPEN="1";
	public static final String STATUS_CLOSE="0";
	/** 子系统id */
	private Long subsysId;
	/** 子系统名称 */
	private String subsysName;
	/** 子系统状态，0-关闭；1-开启 */
	private String subsysStatus;
	public static final String SUBSYS_STATUS = "subsysStatus";
	/** 子系统类型  */
	private String type;
	public static final String TYPE = "type";
	
	/** 对外 */
	public static final String TYPE_OUTER = "1";
	/** 对内 */
	public static final String TYPE_INNER = "0";
	
	
	private String remark;//备注
	private Integer version;//版本号
	public Subsystem(){}
	public Subsystem(Long subsysId, String subsysName, String subsysStatus) {
		super();
		this.subsysId = subsysId;
		this.subsysName = subsysName;
		this.subsysStatus = subsysStatus;
	}
	public Long getSubsysId() {
		return subsysId;
	}
	public void setSubsysId(Long subsysId) {
		this.subsysId = subsysId;
	}
	public String getSubsysName() {
		return subsysName;
	}
	public void setSubsysName(String subsysName) {
		this.subsysName = subsysName;
	}
	public String getSubsysStatus() {
		return subsysStatus;
	}
	public void setSubsysStatus(String subsysStatus) {
		this.subsysStatus = subsysStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
