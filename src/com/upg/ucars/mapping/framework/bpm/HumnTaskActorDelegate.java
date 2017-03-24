package com.upg.ucars.mapping.framework.bpm;

import java.util.Date;

import com.upg.ucars.framework.base.BaseEntity;

@SuppressWarnings("serial")
public class HumnTaskActorDelegate extends BaseEntity {

	private Long	id;
	private Long	creator;
	private Long	modifier;

	private Long	actor;
	private Long	delegator;
	private Date	startTime;
	private Date	endTime;
	private String  inEffect;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreator() {
		return creator;
	}

	public void setCreator(Long creator) {
		this.creator = creator;
	}

	public Long getModifier() {
		return modifier;
	}

	public void setModifier(Long modifier) {
		this.modifier = modifier;
	}

	public Long getActor() {
		return actor;
	}

	public void setActor(Long actor) {
		this.actor = actor;
	}

	public Long getDelegator() {
		return delegator;
	}

	public void setDelegator(Long delegator) {
		this.delegator = delegator;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getInEffect() {
		return inEffect;
	}

	public void setInEffect(String inEffect) {
		this.inEffect = inEffect;
	}

}
