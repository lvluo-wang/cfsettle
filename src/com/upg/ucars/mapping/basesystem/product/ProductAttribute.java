package com.upg.ucars.mapping.basesystem.product;

import java.io.Serializable;

/**
 * 
 * ProductAttribute
 * 
 * @author shentuwy
 * 
 */

public class ProductAttribute implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 71832135578250875L;

	/** 主键 */
	private Long				id;
	/** 属性key */
	private String				key;
	public static final String	KEY					= "key";
	/** 名称 */
	private String				name;
	/** 属性值 */
	private String				value;
	/** 字典元KEY */
	private String				codeMetaKey;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCodeMetaKey() {
		return codeMetaKey;
	}

	public void setCodeMetaKey(String codeMetaKey) {
		this.codeMetaKey = codeMetaKey;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
