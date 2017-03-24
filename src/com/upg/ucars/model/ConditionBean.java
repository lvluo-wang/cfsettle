package com.upg.ucars.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询条件实体
 * <p>
 * 支持带括号的查询,且支持多层级的括号<br>
 * hasPartner()返回false生成类似 'and filed1 = value1' 条件<br>
 * hasPartner()返回true 生成类似 'and (filed1  = value1  or   filed2  = value2)条件
 * 
 * 
 * @author shentuwy
 */
public class ConditionBean {
	/**and*/
	public static final String LOGIC_AND		  = "and";
	/**or*/
	public static final String LOGIC_OR  		  = "or";
	
	/**比较符-等于*/
	public static final String EQUAL		  = "=";
	/**比较符-大于*/
	public static final String MORE  		  = ">";
	/**比较符-大于等于*/
	public static final String MORE_AND_EQUAL = ">=";
	/**比较符-小于*/
	public static final String LESS			  = "<";
	/**比较符-小于等于*/
	public static final String LESS_AND_EQUAL = "<=" ;
	/**比较符-不等于*/
	public static final String NOT_EQUAL	  = "!=";
	/**比较符-like*/
	public static final String LIKE			  = "like";
	/**比较符-包含*/
	public static final String IN 			  = "in";
	/**比较符-不包含*/
	public static final String NOT_IN 		  = "not in";
	/**比较符-为空*/
	public static final String IS_NULL 		  = "is null";
	/**比较符-不空*/
	public static final String IS_NOT_NULL 	  = "is not null";
	/** 比较符-存在 */
	public static final String EXISTS		  = "exists";
	/** 比较符-不存在 */
	public static final String NOT_EXISTS	  = "not exists";
		
	private String logic=LOGIC_AND;
	
	private String field;//变量
	private String operate;//比较符
	private Object value;//值
	
	private List<ConditionBean> partnerList;//搭档条件集
	
	
	public ConditionBean() {
		
	}
	
	/**
	 * 
	 * @param staticCondition 静态条件
	 * @param value
	 */
	public ConditionBean(String staticCondition) {
		this.field = staticCondition;
	}
	
	/**
	 * 比较符默认为'='的构造器
	 *
	 * @param field 字符属性名，如： role.roleId | buser.userType ,单表查询可不用别名
	 * @param value
	 */
	public ConditionBean(String field, Object value) {
		this(field, EQUAL ,value);
	}
	/**
	 * 构造器
	 * @param field 字符属性名，如： role.roleId | buser.userType ,单表查询可不用别名
	 * @param operate 比较符：常量定义在ConditionBean类中,如ConditionBean.EQUAL
	 * @param value
	 */
	public ConditionBean(String field, String operate, Object value) {
		super();
		this.field = field;
		this.operate = operate;
		this.value = value;
	}
	
	public String getField() {
		return field;
	}
	/**
	 * 设置查询的属性名
	 *
	 * @param field 字符属性名，如： role.roleId | buser.userType ,单表查询可不用别名
	 */
	public ConditionBean setField(String field) {
		this.field = field;
		return this;
	}
	public String getOperate() {		
		return operate;
	}
	/**
	 * 设置比较符
	 *
	 * @param operate 比较符：常量定义在ConditionBean类中,如ConditionBean.EQUAL
	 */
	public ConditionBean setOperate(String operate) {
		this.operate = operate;
		return this;
	}
	public Object getValue() {
		return value;
	}
	public ConditionBean setValue(Object value) {
		this.value = value;
		return this;
	}
	
	/**
	 * 条件逻辑关系  
	 * 
	 * @return ConditionBean.LOGCI_AND | ConditionBean.LOGIC_OR
	 */
	public String getLogic() {
		return logic;
	}
	/**
	 * 设置条件逻辑关系  (如不设置,默认为ConditionBean.LOGIC_AND)
	 * 
	 * @param logic ConditionBean.LOGIC_AND | ConditionBean.LOGIC_OR
	 */
	public ConditionBean setLogic(String logic) {
		this.logic = logic;
		return this;
	}	
	
	/**
	 * 增加搭档查询条件
	 * <p>
	 * hql将用括号将自身条件与搭档条件括起来 
	 *
	 * @param cb
	 */
	public ConditionBean addPartner(ConditionBean cb){
		if (this.partnerList==null)
			this.partnerList = new ArrayList<ConditionBean>(3);
		this.partnerList.add(cb);
		return this;
	}
	
	public List<ConditionBean> getPartners(){
		return this.partnerList;
	}
	/**
	 * 是否存在搭档条件 
	 *
	 * @return
	 */
	public boolean hasPartner(){
		return this.partnerList!=null && !this.partnerList.isEmpty();
	}
	
	

}
