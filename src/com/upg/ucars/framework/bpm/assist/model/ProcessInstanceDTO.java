package com.upg.ucars.framework.bpm.assist.model;

import java.util.Date;

/** 
 * 流程实例对象 
 *
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */
public class ProcessInstanceDTO {
	private Long id;
	private Date start;
	private Date end;
	private boolean isSuspended;
	
	public ProcessInstanceDTO() {
		
	}
	
	/**
	 * Creates a new instance of ProcessInstanceDTO.
	 *
	 * @param id
	 * @param start
	 * @param end
	 */
	
	public ProcessInstanceDTO(Long id, Date start, Date end, boolean isSuspended) {
		super();
		this.id = id;
		this.start = start;
		this.end = end;
		this.isSuspended = isSuspended;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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

	public boolean isSuspended() {
		return isSuspended;
	}

	public void setSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}
	

	
}

 