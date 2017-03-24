package com.upg.ucars.framework.bpm.assist.model;

import java.util.Date;

/** 
 * 任务实例对象
 * @author shentuwy
 */
public class TaskInstanceDTO {
	private Long id;
	private String name;
	private String desc;
	private Date start;
	private Date end;
	private boolean isSuspended = false;//终止
	
	private String actorId;//领用人
	private String holdUserName;//停用者用户名称
	
	public TaskInstanceDTO() {}
	
	
	/**
	 * Creates a new instance of TaskInstanceDTO.
	 *
	 * @param id
	 * @param name
	 * @param start
	 * @param end
	 */
	
	public TaskInstanceDTO(Long id, String name, String desc, Date start, Date end, boolean isSuspended) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.start = start;
		this.end = end;
		this.isSuspended = isSuspended;
	}
	
	public TaskInstanceDTO(Long id, String name, String desc, Date start, Date end, boolean isSuspended, String actorId) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.start = start;
		this.end = end;
		this.isSuspended = isSuspended;
		this.actorId = actorId;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}


	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public boolean isSuspended() {
		return isSuspended;
	}


	public void setSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}



	public Long getHoldUserId() {
		if (this.getActorId() == null)
			return null;
		return Long.valueOf(this.getActorId());
	}

	public String getActorId() {
		return actorId;
	}


	public void setActorId(String actorId) {
		this.actorId = actorId;
	}


	public String getHoldUserName() {
		return holdUserName;
	}


	public void setHoldUserName(String holdUserName) {
		this.holdUserName = holdUserName;
	}
	

}

 