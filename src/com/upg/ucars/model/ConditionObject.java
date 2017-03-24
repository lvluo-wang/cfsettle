/*
 * 源程序名称: BaseCondition.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.model;

import java.io.Serializable;

public class ConditionObject<T> implements Serializable {
	
	private String logic = ConditionBean.LOGIC_AND;
	private String field;//变量
	private String operate;//比较符
	private T value;//值对象
	
	public ConditionObject(){
		
	}
	
	/**
	 * 
	 * @param staticCondition 静态条件
	 */
	public ConditionObject(String staticCondition){
		this.field = staticCondition;
	}
	
	/**
	 * 
	 * @param field  变量
	 * @param value 值对象
	 */
	public ConditionObject(String field,T value){
		this(field,ConditionBean.EQUAL,value);
	}
	
	/**
	 * 
	 * @param field  变量
	 * @param operate 比较符
	 * @param value 值对象
	 */
	public ConditionObject(String field,String operate,T value){
		this.field = field;
		this.operate = operate;
		this.value = value;
	}

	public String getLogic() {
		return logic;
	}

	public void setLogic(String logic) {
		this.logic = logic;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
}
