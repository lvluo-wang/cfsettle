package com.upg.ucars.util;

/**
 * 数据转换时属性设置vo
 * 
 * @author alw
 * @author shentuwy
 *
 */
public class PropertyTransVo {
	/** 对应需要转换的源数据集合对象的属性 */
	private String origProperty;
	/** 传换需用到的关联对象PO类 */
	private Class<?> cls;
	/** 对应转换时关联对象的属性  */
	private String destKeyProperty;
	/** 转换时关联对象的目标属性，如果是多个用#分隔 */
	private String destValueProperty;
	/** 转换后动态属性   用于自己制定转换后新增的属性名，如果是多个用#分隔,按顺序与destValueProperty中的对应 */
	private String translatedProperty;
	
	public PropertyTransVo(String origProperty,Class<?> cls,String destKeyProperty,String destValueProperty,String translatedProperty)
	{
		this.origProperty=origProperty;
		this.cls=cls;		
		this.destKeyProperty=destKeyProperty;
		this.destValueProperty= destValueProperty;
		this.translatedProperty = translatedProperty;
	}
	
	public PropertyTransVo(String origProperty,Class<?> cls,String destKeyProperty,String destValueProperty)
	{
		this.origProperty=origProperty;
		this.cls=cls;
		this.destKeyProperty=destKeyProperty;
		this.destValueProperty= destValueProperty;
		
	}

	public String getOrigProperty() {
		return origProperty;
	}


	public void setOrigProperty(String origProperty) {
		this.origProperty = origProperty;
	}


	public String getDestKeyProperty() {
		return destKeyProperty;
	}


	public void setDestKeyProperty(String destKeyProperty) {
		this.destKeyProperty = destKeyProperty;
	}


	public String getDestValueProperty() {
		return destValueProperty;
	}


	public void setDestValueProperty(String destValueProperty) {
		this.destValueProperty = destValueProperty;
	}


	public String getTranslatedProperty() {
		return translatedProperty;
	}


	public void setTranslatedProperty(String translatedProperty) {
		this.translatedProperty = translatedProperty;
	}

	public Class<?> getCls() {
		return cls;
	}

	public void setCls(Class<?> cls) {
		this.cls = cls;
	}
}
