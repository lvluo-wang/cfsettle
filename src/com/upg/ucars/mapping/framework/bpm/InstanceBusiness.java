package com.upg.ucars.mapping.framework.bpm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import com.upg.ucars.framework.bpm.base.BaseBpmEntity;

/**
 * 任务业务信息
 * 
 * @author shentuwy
 * @date 2012-7-6
 * 
 */
public class InstanceBusiness extends BaseBpmEntity implements Serializable {

	private static final long	serialVersionUID	= 7819294077270157499L;

	/** 实体标识符 */
	private Long				id;
	/** 创建人 */
	private Long				creator;
	/** 创建名字 */
	private String				creatorName;
	/** 业务实体ID */
	private Long				entityId;
	/** 业务实体类型 */
	private String				entityType;
	/** 流程实例ID */
	private Long				instanceId;
	/** 流程状态 */
	private String				instanceStatus;
	/** 产品代码 */
	private String				prodNo;
	/** 产品名称 */
	private String				prodName;
	/** 接入点 */
	private String 				miNo;
	
	/** 发起人机构 */
	private transient Long		brchId;
	

	private String				col1;
	private String				col2;
	private String				col3;
	private BigDecimal			col4;
	private Long				col5;
	private Long				col6;
	private Date				col7;
	private Date				col8;
	private String				col9;
	private String				col10;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEntityId() {
		return entityId;
	}

	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public Long getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	public String getCol2() {
		return col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2;
	}

	public String getCol3() {
		return col3;
	}

	public void setCol3(String col3) {
		this.col3 = col3;
	}

	public BigDecimal getCol4() {
		return col4;
	}

	public void setCol4(BigDecimal col4) {
		this.col4 = col4;
	}

	public String getInstanceStatus() {
		return instanceStatus;
	}

	public void setInstanceStatus(String instanceStatus) {
		this.instanceStatus = instanceStatus;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public Long getCol5() {
		return col5;
	}

	public void setCol5(Long col5) {
		this.col5 = col5;
	}

	public Long getCol6() {
		return col6;
	}

	public void setCol6(Long col6) {
		this.col6 = col6;
	}

	public Date getCol7() {
		return col7;
	}

	public void setCol7(Date col7) {
		this.col7 = col7;
	}

	public Date getCol8() {
		return col8;
	}

	public void setCol8(Date col8) {
		this.col8 = col8;
	}

	public String getCol9() {
		return col9;
	}

	public void setCol9(String col9) {
		this.col9 = col9;
	}

	public String getProdNo() {
		return prodNo;
	}

	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public String getMiNo() {
		return miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}

	public String getCol10() {
		return col10;
	}

	public void setCol10(String col10) {
		this.col10 = col10;
	}
	
	public Long getBrchId() {
		return brchId;
	}

	public void setBrchId(Long brchId) {
		this.brchId = brchId;
	}

	@Override
	public InstanceBusiness clone() {
		InstanceBusiness instanceBusiness = new InstanceBusiness();

		// BaseEntity
		instanceBusiness.setCreateTime(this.getCreateTime());
		instanceBusiness.setModifyTime(this.getModifyTime());

		// BaseBpmEntity
		instanceBusiness.setProcessName(this.getProcessName());
		instanceBusiness.setProcessCnName(this.getProcessCnName());
		instanceBusiness.setProdNo(this.prodNo);
		instanceBusiness.setProdName(this.prodName);
		instanceBusiness.setTaskId(this.getTaskId());
		instanceBusiness.setTaskName(this.getTaskName());
		instanceBusiness.setTaskCnName(this.getTaskCnName());
		instanceBusiness.setTokenId(this.getTokenId());
		instanceBusiness.setHoldUserId(this.getHoldUserId());
		instanceBusiness.setExtInfo(new HashMap<String,Object>(this.getExtInfo()));

		// InstanceBusiness
		instanceBusiness.setId(this.getId());
		instanceBusiness.setCreator(this.getCreator());
		instanceBusiness.setCreatorName(this.getCreatorName());
		instanceBusiness.setEntityId(this.getEntityId());
		instanceBusiness.setEntityType(this.getEntityType());
		instanceBusiness.setInstanceId(this.getInstanceId());
		instanceBusiness.setInstanceStatus(this.getInstanceStatus());
		instanceBusiness.setBrchId(this.getBrchId());
		instanceBusiness.setCol1(col1);
		instanceBusiness.setCol2(col2);
		instanceBusiness.setCol3(col3);
		instanceBusiness.setCol4(col4);
		instanceBusiness.setCol5(col5);
		instanceBusiness.setCol6(col6);
		instanceBusiness.setCol7(col7);
		instanceBusiness.setCol8(col8);
		instanceBusiness.setCol9(col9);
		instanceBusiness.setCol10(col10);

		return instanceBusiness;
	}
	
}
