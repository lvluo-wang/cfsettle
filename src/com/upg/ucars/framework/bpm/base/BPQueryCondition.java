package com.upg.ucars.framework.bpm.base;

import java.util.ArrayList;
import java.util.List;

import com.upg.ucars.framework.base.QueryCondition;

/**
 * 工作流查询条件
 *
 * @author shentuwy
 */
public class BPQueryCondition extends QueryCondition {
	private String processName;//流程名
	
	private Class entityClass;//实体对象名
	
	private String entityAlias;//实体在hql中的别名
	
	private String entityPkName;//实体类的主键属性名,一般为id
	
	/** 是否已被领用 */
	private Boolean hasActor;
	
	/**
	 * 无参构造器
	 * <br>采用此构造器至少还需要设置processName, hql, entityAlias及entityPkName属性
	 */
	public BPQueryCondition() {
		super();
	}
	/**
	 * 带hql参数的构造器
	 * <br>采用此构造器至少还需要设置processName, entityAlias及entityPkName属性
	 * @param hql
	 */
	public BPQueryCondition(String hql) {
		super(hql);
	}
	
	public BPQueryCondition(String hql, String processName,	String entityAlias, String entityPkName) {
		super(hql);
		this.processName = processName;
		this.entityAlias = entityAlias;
		this.entityPkName = entityPkName;
	}
	/**
	 * 实体类的主键属性名。
	 * <br>一般为id
	 * @return 如果未设置则返回'id'
	 */
	public String getEntityPkName(){
		if (entityPkName==null || "".equals(entityPkName))
			return "id";
		return entityPkName;
	}
	/**
	 * 获取流程名
	 * @return
	 */
	public String getProcessName(){
		return processName;
	}
	
	
	public void setEntityPkName(String entityPkName) {
		this.entityPkName = entityPkName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	
	public Class getEntityClass() {
		return entityClass;
	}
	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}
	/**
	 * hql中实体的别名
	 * @return
	 */
	public String getEntityAlias(){
		return this.entityAlias;
	}
	/**
	 * 设置实体别名
	 * @param entityAlias
	 */
	public void setEntityAlias(String entityAlias) {
		this.entityAlias = entityAlias;
	}	
	
	private List<String[]> exQueryTaskList =  new ArrayList<String[]>(3);
	/**
	 * 增加要查询的任务 
	 *
	 * @param procName 流程名
	 * @param taskName 任务名
	 */
	public void addQueryProcTask(String procName, String taskName){
		exQueryTaskList.add(new String[]{procName, taskName});
	}
	/**
	 * 要查询的流程任务组 
	 *
	 * @return
	 */
	public List<String[]> getQueryProcTasks(){
		return exQueryTaskList;
	}
	
	private List<String[]> exQueryNodeList = new ArrayList<String[]>(3);
	/**
	 * 增加要查询的节点 
	 *
	 * @param procName 流程名
	 * @param nodeName 节点名
	 */
	public void addQueryProcNode(String procName, String nodeName){
		exQueryNodeList.add(new String[]{procName, nodeName});
	}
	/**
	 * 要查询的流程节点组
	 *
	 * @return
	 */
	public List<String[]> getQueryProcNodes(){
		return exQueryNodeList;
	}
	public Boolean getHasActor() {
		return hasActor;
	}
	public void setHasActor(Boolean hasActor) {
		this.hasActor = hasActor;
	}
	
}
