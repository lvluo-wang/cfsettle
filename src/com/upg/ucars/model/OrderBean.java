
package com.upg.ucars.model;

/**
 * 排序实体
 * 
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */
public class OrderBean {
	/**是否升序*/
	private boolean asc = true;
	/**排序属性*/
	private String property;
	
	public OrderBean(){
		
	}

	public OrderBean(String property) {
		this(property, true);
	}

	public OrderBean(String property, boolean asc) {
		this.property = property;
		this.asc = asc;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}

	public String getProperty() {
		return property;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}
	

}