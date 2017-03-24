package com.upg.ucars.mapping.basesystem.dictionary;

/**
 * CodeMeta entity.
 * 
 * @author MyEclipse Persistence Tools
 * @author shentuwy
 */

public class CodeMeta implements java.io.Serializable {

	private static final long	serialVersionUID	= -3651998055973704696L;

	// Fields
	/** ID */
	private Long				id;

	public static final String	ID					= "id";

	/** KEY */
	private String				key;

	public static final String	KEY					= "key";

	/** 名称 */
	private String				name;

	public static final String	NAME				= "name";

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
