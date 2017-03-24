package com.upg.ucars.framework.bpm.base;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ProcessException;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.Role;
import com.upg.ucars.util.SourceTemplate;

/**
 * 基础的工作流查询相关DAO
 * 
 * @author shentuwy
 * @date 2012-7-9
 *
 */
public class BpmQueryDAO<T,PK extends Serializable> extends BaseDAO<T, PK>{
	/**
	 * 查询指定委派人工作流任务及关联实体
	 *
	 * @param condition
	 * @param userId 
	 * @param taskName 任务名 <li>单个任务：任务名  <li>多个任务（同属一个流程）：任务名|任务名|...|任务名。以'|'分隔
	 * @param page 分页对象，如为null则查全部
	 * @return List< Object[]{Entity1,Entity2,...,Long,Long} > 其中第一个Long为TaskInstance的主键ID, 第二个Long为 TaskInstance.actorId
	 */
	public List queryTaskOfAssignAndEntityByConditon(BPQueryCondition condition, Long userId, String taskName, Page page) throws ProcessException {
		condition.contextInitialized();		
		String userHql = condition.getQueryHql();
		String userLowerHql = userHql.toLowerCase();
		int selectIndex = userLowerHql.indexOf("select ");
		int fromIndex = userLowerHql.indexOf("from ");
		int whereIndex = userLowerHql.indexOf("where ");
		int orderIndex = userLowerHql.indexOf("order by");
		
		String userSelectHql = condition.getEntityAlias();
		if (selectIndex>=0){
			userSelectHql = userHql.substring(selectIndex+"select".length(), fromIndex);			
		}
		int tableEndIndex = whereIndex>0?whereIndex:(orderIndex>0?orderIndex:userHql.length());
		String userFromHql = userHql.substring(fromIndex+"from".length(), tableEndIndex);
		
		String userWhereHql = "";
		if (whereIndex>0){
			userWhereHql = userHql.substring(whereIndex+"where".length(), orderIndex>0?orderIndex:userHql.length());
		}	
		String userOrderByHql = orderIndex>0?userHql.substring(orderIndex+"order by".length()):"";
		
		//多个任务名以'|'分隔。
		String[] taskNames= taskName.split("\\|");
		String taskNameCond = null;
		if (taskNames.length == 1)
			taskNameCond = " = '"+taskName+"'";
		else{
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < taskNames.length; i++) {
				sb.append("'"+taskNames[i]+"',");
			}
			taskNameCond = " in (" + sb.deleteCharAt(sb.length()-1) + ")";
		}
		
		String countHeadSql = "select count(distinct ti.id)  ";
		String queryHeadSql = "select distinct "+userSelectHql+",ti.actorId,ti.end, ti.id, ti.name, ti.preActor,ti.description ";
		if (userSelectHql.indexOf(" distinct ") < 0)
			queryHeadSql = "select distinct "+userSelectHql+",ti.actorId,ti.end, ti.id, ti.name, ti.preActor,ti.description ";
		StringBuffer hqlBuffer = new StringBuffer();		
		hqlBuffer.append(" from org.jbpm.taskmgmt.exe.TaskInstance ti ");		
		hqlBuffer.append(" , TaskActor ta ");//追踪信息	
		hqlBuffer.append(" ,  "+userFromHql);//清单类
					
		hqlBuffer.append(" where ta.userId ="+userId);
		hqlBuffer.append(" and ta.tiId=ti.id ");
		hqlBuffer.append(" and ti.isSuspended != true and ti.isOpen=true and ti.end is null ");
		hqlBuffer.append(" and ta.tiId=ti.id ");
		
		
		List<String[]> procTaskList = condition.getQueryProcTasks();
		if (procTaskList.isEmpty()){//单个流程的任务
			hqlBuffer.append(" and ti.procNameKey='"+condition.getProcessName()+"' ");
			hqlBuffer.append(" and ti.name " + taskNameCond);			
		}else{//多个流程的任务
			hqlBuffer.append(" and (");
			for (int i=0; i<procTaskList.size(); i++) {
				String procNm = procTaskList.get(i)[0];
				String taskNm = procTaskList.get(i)[1];
				if (i!=0)
					hqlBuffer.append(" or ");
				
				hqlBuffer.append("(ti.name='"+taskNm+ "' and ti.procNameKey='"+procNm+"' ) ");	
								
			}
			hqlBuffer.append(" )");
		}
		hqlBuffer.append(" and "+condition.getEntityAlias()+"."+condition.getEntityPkName()+"=ti.entityId");
		hqlBuffer.append(" and "+userWhereHql);		
		String countHql = countHeadSql + hqlBuffer.toString();
		if (countHql.toLowerCase().indexOf("left join") > 1){//统计总数时去掉左连接
			int leftJoinIndex = countHql.toLowerCase().indexOf("left join");
			int firstWhereIndex = countHql.indexOf(" where ");
			countHql = countHql.substring(0,leftJoinIndex) + countHql.substring(firstWhereIndex);
		}
		if (orderIndex<1){//默认的排序
			hqlBuffer.append(" order by ti.id desc");
		}else{			
			hqlBuffer.append(" order by " +userOrderByHql);
		}
		String queryHql = queryHeadSql + hqlBuffer.toString();
		
		List<Object[]> queryRsList = this.queryByParam(countHql, queryHql, condition.getParameterMap(), page);
		
		List rsList = this.setTaskToEntity(queryRsList);
		
		return rsList;
		
	}
	/**
	 * 分页查询工作流任务及关联实体
	 *
	 * @param condition
	 * @param actor 
	 * @param taskName 任务名 <li>单个任务：任务名  <li>多个任务（同属一个流程）：任务名|任务名|...|任务名。以'|'分隔
	 * @param page 分页对象，如为null则查全部
	 * @return List< Object[]{Entity1,Entity2,...,Long,Long} > 其中第一个Long为TaskInstance的主键ID, 第二个Long为 TaskInstance.actorId
	 */
	public List queryTaskAndEntityByConditon(BPQueryCondition condition, Long userId, String taskName, Page page) throws ProcessException {
			
		List<Object[]> procTaskAuthList= null;//授权的任务名组  【机构ID,流程名,任务名】
		StringBuffer hqlsb = new StringBuffer(" from ProcessDef p, HumnTask t, HumnTaskActr ta where p.id=t.procId and t.id=ta.taskId");		
		{//查询用户拥有的任务权限
			IUserService userService = SourceTemplate.getBean(IUserService.class,BeanNameConstants.USER_SERVICE);
			
			Long userBrchId=userService.getUserById(userId).getBrchId();//所在机构
			List<Role> roleList = userService.queryUserRole(userId);
			StringBuffer roleSb = new StringBuffer();
			for (Role role : roleList) {
				roleSb.append(role.getRoleId()+",");
			}
			String userRoleIds=roleSb.append("-1").toString();//拥有的角色集合
			
			
			hqlsb.append(" ");
			
			List<String[]> procTaskList = condition.getQueryProcTasks();
			if (procTaskList.isEmpty()){//单个流程的任务
				//多个任务名以'|'分隔。
				if( StringUtils.isNotBlank(taskName) ){
					String[] taskNames= taskName.split("\\|");
					String taskNameCond = null;
					if (taskNames.length == 1)
						taskNameCond = " = '"+taskName+"'";
					else{
						StringBuffer sb = new StringBuffer();
						for (int i = 0; i < taskNames.length; i++) {
							sb.append("'"+taskNames[i]+"',");
						}
						taskNameCond = " in (" + sb.deleteCharAt(sb.length()-1) + ")";
					}
					hqlsb.append(" and t.taskName  " + taskNameCond);	
					if (condition.getProcessName() != null) {
						hqlsb.append(" and p.procNameKey = '"+condition.getProcessName()+"' ");
					}
				}
			}else{//多个流程的任务
				for (int i=0; i<procTaskList.size(); i++) {
					String procNm = procTaskList.get(i)[0];
					String taskNm = procTaskList.get(i)[1];
					if (i==0)
						hqlsb.append(" and  (1=0  ");
					
					hqlsb.append(" 	or (t.taskName = '"+taskNm+ "' and p.procNameKey = '"+procNm+"' ) ");	
					
					if (i==procTaskList.size()-1)
						hqlsb.append(" )");
					
				}
			}
			hqlsb.append(" and (ta.actrUserId="+userId+" or (ta.actrBrchId="+userBrchId+" and ta.actrRoleId in ("+userRoleIds+")) or (ta.actrBrchId="+userBrchId+" and ta.actrRoleId is null and ta.actrUserId is null)  ) ");
			
			procTaskAuthList = this.getHibernateTemplate().find("select distinct ta.brchId, p.procNameKey, t.taskName " + hqlsb.toString());
			//
		}
		
		/*
		if (procTaskAuthList.isEmpty()){//没有任何任务的权限
			return new ArrayList(0);
		}
		*/
		
		//---------组装查询
		condition.contextInitialized();		
		String userHql = condition.getQueryHql();
		
		List<String> userHqlParts = getUserHqlParts(userHql);
		String userSelectHql = userHqlParts.get(0);
		if (StringUtils.isBlank(userSelectHql)) {
			userSelectHql = condition.getEntityAlias();
		}
		String userFromHql = userHqlParts.get(1);
		String userWhereHql = userHqlParts.get(2);
		String userOrderByHql = userHqlParts.get(3);
		

		Map<String,Object> param = new HashMap<String,Object>();
		if (condition.getParameterMap() != null && condition.getParameterMap().size() > 0) {
			param.putAll(condition.getParameterMap());
		}
		
		String countHeadSql = "select count(distinct ti.id) ";
		String queryHeadSql = "select "+userSelectHql+", ti.actorId,ti.end,ti.id, ti.name, ti.preActor,ti.description ";
//		if (userSelectHql.indexOf(" distinct ") < 0)
//		  queryHeadSql = "select distinct "+userSelectHql+", ti.id, ti.name, ti.preActor,ti.description ";
		StringBuffer hqlBuffer = new StringBuffer();		
		hqlBuffer.append(" from org.jbpm.taskmgmt.exe.TaskInstance ti ");	
		hqlBuffer.append(" , "+userFromHql+ " where ");//清单类
		hqlBuffer.append(" ( (ti.actorId='").append(userId).append("')");
		
		if (procTaskAuthList.isEmpty()) {
			hqlBuffer.append(")");
		}else{
			if (procTaskAuthList.size() ==1){//只有一个任务时
				Long brchId = (Long)procTaskAuthList.get(0)[0];
				String procNm = (String)procTaskAuthList.get(0)[1];
				String taskNm = (String)procTaskAuthList.get(0)[2];
				hqlBuffer.append(" or	(ti.brchId="+brchId+" and ti.name='"+taskNm+ "' and ti.procNameKey='"+procNm+"' ) ");	
			}else{//一次查多个任务时
//				Set<Long> brchIdSet = new HashSet<Long>();
//				Set<String> taskNameSet = new HashSet<String>(); 
//				Set<String> procNameSet = new HashSet<String>();
				hqlBuffer.append(" or (");
//				for (int i=0; i<procTaskAuthList.size(); i++) {
//					Long brchId = (Long)procTaskAuthList.get(i)[0];
//					String procNm = (String)procTaskAuthList.get(i)[1];
//					String taskNm = (String)procTaskAuthList.get(i)[2];
					
//					if (i!=0)
//						hqlBuffer.append("  or  ");				
//					hqlBuffer.append(" 	(ti.brchId="+brchId+" and ti.name ='"+taskNm+ "' and ti.procNameKey='"+procNm+"' ) ");	
					
//					brchIdSet.add(brchId);
//					taskNameSet.add(taskNm);
//					procNameSet.add(procNm);
//				}
//				hqlBuffer.append(" ti.brchId in (:task_brchId) and ti.name in (:task_Name) and ti.procNameKey in (:task_procName)");
//				param.put("task_brchId", brchIdSet);
//				param.put("task_Name", taskNameSet);
//				param.put("task_procName", procNameSet);
				hqlBuffer.append(" exists (select 1 ");
				hqlBuffer.append(hqlsb.toString());
				hqlBuffer.append(" and p.procNameKey=ti.procNameKey and t.taskName=ti.name and ta.brchId=ti.brchId");
				hqlBuffer.append(")");
				
				
				hqlBuffer.append(" )");
			}
			hqlBuffer.append(" and ti.actorId is null )");
		}
		
		
		hqlBuffer.append(" and ti.isSuspended != true and ti.isOpen=true and ti.end is null ");
		hqlBuffer.append(" and "+condition.getEntityAlias()+"."+condition.getEntityPkName()+"=ti.entityId");
		hqlBuffer.append(" and "+userWhereHql);		
		String countHql = countHeadSql + hqlBuffer.toString();
		if (countHql.toLowerCase().indexOf("left join") > 1){//统计总数时去掉左连接
			int leftJoinIndex = countHql.toLowerCase().indexOf("left join");
			int firstWhereIndex = countHql.indexOf(" where  ");
			countHql = countHql.substring(0,leftJoinIndex) + countHql.substring(firstWhereIndex);
		}
		if (StringUtils.isBlank(userOrderByHql)){//默认的排序
			hqlBuffer.append(" order by ti.id desc");
		}else{			
			hqlBuffer.append(" order by " +userOrderByHql);
		}
		String queryHql = queryHeadSql + hqlBuffer.toString();
		
		List<Object[]> queryRsList = this.queryByParam(countHql, queryHql,param, page);
		
		List rsList = this.setTaskToEntity(queryRsList);
		
		return rsList;
		
	}
	
	
	/**
	 * 分页查询工作流任务及关联实体
	 *
	 * @param condition
	 * @param actor 
	 * @param taskName 任务名 <li>单个任务：任务名  <li>多个任务（同属一个流程）：任务名|任务名|...|任务名。以'|'分隔
	 * @param page 分页对象，如为null则查全部
	 * @return List< Object[]{Entity1,Entity2,...,Long,Long} > 其中第一个Long为TaskInstance的主键ID, 第二个Long为 TaskInstance.actorId
	 */
	public List queryTaskAndEntityByConditon(BPQueryCondition condition, List<Long> userIds, String taskName, Page page) throws ProcessException {
			
		List<Object[]> procTaskAuthList= null;//授权的任务名组  【机构ID,流程名,任务名】
		StringBuffer hqlsb = new StringBuffer(" from ProcessDef p, HumnTask t, HumnTaskActr ta where p.id=t.procId and t.id=ta.taskId");
		StringBuilder userIdSB = new StringBuilder();
		{//查询用户拥有的任务权限
			List<String[]> procTaskList = condition.getQueryProcTasks();
			if (procTaskList.isEmpty()){//单个流程的任务
				//多个任务名以'|'分隔。
				if( StringUtils.isNotBlank(taskName) ){
					String[] taskNames= taskName.split("\\|");
					String taskNameCond = null;
					if (taskNames.length == 1)
						taskNameCond = " = '"+taskName+"'";
					else{
						StringBuffer sb = new StringBuffer();
						for (int i = 0; i < taskNames.length; i++) {
							sb.append("'"+taskNames[i]+"',");
						}
						taskNameCond = " in (" + sb.deleteCharAt(sb.length()-1) + ")";
					}
					hqlsb.append(" and t.taskName  " + taskNameCond);	
					if (condition.getProcessName() != null) {
						hqlsb.append(" and p.procNameKey = '"+condition.getProcessName()+"' ");
					}
				}
			}else{//多个流程的任务
				for (int i=0; i<procTaskList.size(); i++) {
					String procNm = procTaskList.get(i)[0];
					String taskNm = procTaskList.get(i)[1];
					if (i==0)
						hqlsb.append(" and  (1=0  ");
					
					hqlsb.append(" 	or (t.taskName = '"+taskNm+ "' and p.procNameKey = '"+procNm+"' ) ");	
					
					if (i==procTaskList.size()-1)
						hqlsb.append(" )");
					
				}
			}
			
			
			
			StringBuilder actorCondition = new StringBuilder();

			IUserService userService = SourceTemplate.getBean(IUserService.class,BeanNameConstants.USER_SERVICE);
			
			
			for (Long userId : userIds) {
				
				userIdSB.append("'").append(userId).append("',");
				
				StringBuffer roleSb = new StringBuffer();
				Long userBrchId=userService.getUserById(userId).getBrchId();//所在机构
				List<Role> roleList = userService.queryUserRole(userId);
				for (Role role : roleList) {
					roleSb.append(role.getRoleId()+",");
				}
				
				String userRoleIds=roleSb.append("-1").toString();//拥有的角色集合
				
				if (actorCondition.length() > 0) {
					actorCondition.append(" or ");
				}
				actorCondition.append("(");
				actorCondition.append("ta.actrBrchId=").append(userBrchId);
				actorCondition.append(" and ta.actrRoleId in (").append(userRoleIds).append(")");
				actorCondition.append(")");
			}
			userIdSB.append("'-1'");
			
			
			hqlsb.append(" ");
			
			//or (ta.actrBrchId="+userBrchId+" and ta.actrRoleId is null and ta.actrUserId is null) 暂时去掉
			hqlsb.append(" and (ta.actrUserId in ("+userIdSB.toString()+") or (" + actorCondition.toString()  +")  ) ");
			
			procTaskAuthList = this.getHibernateTemplate().find("select distinct ta.brchId, p.procNameKey, t.taskName " + hqlsb.toString());
			//
		}
		
		/*
		if (procTaskAuthList.isEmpty()){//没有任何任务的权限
			return new ArrayList(0);
		}
		*/
		
		//---------组装查询
		condition.contextInitialized();		
		String userHql = condition.getQueryHql();
		
		List<String> userHqlParts = getUserHqlParts(userHql);
		String userSelectHql = userHqlParts.get(0);
		if (StringUtils.isBlank(userSelectHql)) {
			userSelectHql = condition.getEntityAlias();
		}
		String userFromHql = userHqlParts.get(1);
		String userWhereHql = userHqlParts.get(2);
		String userOrderByHql = userHqlParts.get(3);
		

		Map<String,Object> param = new HashMap<String,Object>();
		if (condition.getParameterMap() != null && condition.getParameterMap().size() > 0) {
			param.putAll(condition.getParameterMap());
		}
		
		String countHeadSql = "select count(distinct ti.id) ";
		String queryHeadSql = "select "+userSelectHql+", ti.actorId,ti.end,ti.id, ti.name, ti.preActor,ti.description ";
//		if (userSelectHql.indexOf(" distinct ") < 0)
//		  queryHeadSql = "select distinct "+userSelectHql+", ti.id, ti.name, ti.preActor,ti.description ";
		StringBuffer hqlBuffer = new StringBuffer();		
		hqlBuffer.append(" from org.jbpm.taskmgmt.exe.TaskInstance ti ");	
		hqlBuffer.append(" , "+userFromHql+ " where ");//清单类
		
		hqlBuffer.append(" ti.isSuspended != true and ti.isOpen=true and ti.end is null ");
		
		StringBuilder actorCondition = new StringBuilder();
		if (condition.getHasActor() == null || condition.getHasActor()) {
			actorCondition.append(" (ti.actorId in (").append(userIdSB.toString()).append("))");
		}
		
		if (condition.getHasActor() == null || !condition.getHasActor()) {
			if (procTaskAuthList != null && procTaskAuthList.size() > 0) {
				if (procTaskAuthList.size() ==1){//只有一个任务时
					Long brchId = (Long)procTaskAuthList.get(0)[0];
					String procNm = (String)procTaskAuthList.get(0)[1];
					String taskNm = (String)procTaskAuthList.get(0)[2];
					actorCondition.append(" or	(ti.brchId="+brchId+" and ti.name='"+taskNm+ "' and ti.procNameKey='"+procNm+"' ) ");	
				}else{//一次查多个任务时
//					Set<Long> brchIdSet = new HashSet<Long>();
//					Set<String> taskNameSet = new HashSet<String>(); 
//					Set<String> procNameSet = new HashSet<String>();
					if (actorCondition.length() > 0) {
						actorCondition.append(" or ");
					}
					actorCondition.append(" (");
//					for (int i=0; i<procTaskAuthList.size(); i++) {
//						Long brchId = (Long)procTaskAuthList.get(i)[0];
//						String procNm = (String)procTaskAuthList.get(i)[1];
//						String taskNm = (String)procTaskAuthList.get(i)[2];
						
//						if (i!=0)
//							hqlBuffer.append("  or  ");				
//						hqlBuffer.append(" 	(ti.brchId="+brchId+" and ti.name ='"+taskNm+ "' and ti.procNameKey='"+procNm+"' ) ");	
						
//						brchIdSet.add(brchId);
//						taskNameSet.add(taskNm);
//						procNameSet.add(procNm);
//					}
//					hqlBuffer.append(" ti.brchId in (:task_brchId) and ti.name in (:task_Name) and ti.procNameKey in (:task_procName)");
//					param.put("task_brchId", brchIdSet);
//					param.put("task_Name", taskNameSet);
//					param.put("task_procName", procNameSet);
					actorCondition.append(" exists (select 1 ");
					actorCondition.append(hqlsb.toString());
					actorCondition.append(" and p.procNameKey=ti.procNameKey and t.taskName=ti.name and ta.brchId=ti.brchId");
					actorCondition.append(")");
					
					
					actorCondition.append(" )");
				}
				actorCondition.append(" and ti.actorId is null ");
				
			} else {
				if (actorCondition.length() == 0) {
					actorCondition.append(" 1<>1");
				}
			}
		}
		
		if (userIds != null && userIds.size() > 0) {
			if (actorCondition.length() > 0) {
				actorCondition.append(" or ");
			}
			actorCondition.append("(").append(" ti.actorId is null and (");
			for (int i = 0 ; i < userIds.size(); i++ ){
				if (i > 0) {
					actorCondition.append(" or ");
				}
				actorCondition.append(" ti.preActor like '%").append(userIds.get(i)).append(",").append("%' ");
			}
			actorCondition.append("))");
		}
		
		hqlBuffer.append(" and (").append(actorCondition.toString()).append(") ");
		
		hqlBuffer.append(" and "+condition.getEntityAlias()+"."+condition.getEntityPkName()+"=ti.entityId");
		hqlBuffer.append(" and "+userWhereHql);		
		String countHql = countHeadSql + hqlBuffer.toString();
		if (countHql.toLowerCase().indexOf("left join") > 1){//统计总数时去掉左连接
			int leftJoinIndex = countHql.toLowerCase().indexOf("left join");
			int firstWhereIndex = countHql.indexOf(" where  ");
			countHql = countHql.substring(0,leftJoinIndex) + countHql.substring(firstWhereIndex);
		}
		if (StringUtils.isBlank(userOrderByHql)){//默认的排序
			hqlBuffer.append(" order by ti.id desc");
		}else{			
			hqlBuffer.append(" order by " +userOrderByHql);
		}
		String queryHql = queryHeadSql + hqlBuffer.toString();
		
		List<Object[]> queryRsList = this.queryByParam(countHql, queryHql,param, page);
		
		List rsList = this.setTaskToEntity(queryRsList);
		
		return rsList;
		
	}
	
	
	/**
	 * 查询运行到指定节点的流程令牌及相关实体
	 * @param condition
	 * @param nodeName 流程节点名
	 * @param page 分页对象，如为null则查全部
	 @return List< Object[]{Entity1,Entity2,...,Long} > 其中Long为Token的主键ID
	 */
	public List queryTokenAndEntityOnNode(BPQueryCondition condition, String nodeName, Page page) throws ProcessException{
		condition.contextInitialized();		
		String userHql = condition.getQueryHql();
		String userLowerHql = userHql.toLowerCase();
		int selectIndex = userLowerHql.indexOf("select");
		int fromIndex = userLowerHql.indexOf("from");
		int whereIndex = userLowerHql.indexOf("where");
		int orderIndex = userLowerHql.indexOf("order by");
		
		String userSelectHql = condition.getEntityAlias();
		if (selectIndex>=0){
			userSelectHql = userHql.substring(selectIndex+"select".length(), fromIndex);			
		}
		int tableEndIndex = whereIndex>0?whereIndex:(orderIndex>0?orderIndex:userHql.length());
		String userFromHql = userHql.substring(fromIndex+"from".length(), tableEndIndex);
		
		String userWhereHql = "";
		if (whereIndex>0){
			userWhereHql = userHql.substring(whereIndex+"where".length(), orderIndex>0?orderIndex:userHql.length());
		}	
		String userOrderByHql = orderIndex>0?userHql.substring(orderIndex+"order by".length()):"";
				
		String countHeadSql = "select count(distinct token.id)  ";
		String queryHeadSql = "select "+userSelectHql+", token.id ";
		if (userSelectHql.indexOf(" distinct ") < 0)
			queryHeadSql = "select distinct  "+userSelectHql+", token.id ";
		StringBuffer hqlBuffer = new StringBuffer();		
		hqlBuffer.append(" from org.jbpm.graph.exe.Token token   ");
		hqlBuffer.append(" join token.node node ");
		hqlBuffer.append(" ,  "+userFromHql +" where ");//清单类
		
		List<String[]> procNodeList = condition.getQueryProcNodes();
		if (procNodeList.isEmpty()){//单个流程节点
			hqlBuffer.append(" node.name='"+nodeName+ "'");	
			hqlBuffer.append(" and token.procNameKey='"+condition.getProcessName()+"' ");
		}else{//多个流程节点
			hqlBuffer.append(" (");
			for (int i=0; i<procNodeList.size(); i++){
				String procNm = procNodeList.get(i)[0];
				String nodeNm = procNodeList.get(i)[1];
				if (i!=0)
					hqlBuffer.append(" 	or ");				
				hqlBuffer.append(" (node.name = '"+nodeNm+ "' and token.procNameKey='"+procNm+"') ");				
			}
			hqlBuffer.append(" )");
		}		
		hqlBuffer.append(" and "+condition.getEntityAlias()+"."+condition.getEntityPkName()+" = token.entityId");
		hqlBuffer.append(" and "+userWhereHql);		
		String countHql = countHeadSql + hqlBuffer.toString();
		if (countHql.toLowerCase().indexOf("left join") > 1){//统计总数时去掉左连接
			int leftJoinIndex = countHql.toLowerCase().indexOf("left join");
			int firstWhereIndex = countHql.indexOf(" where ");
			countHql = countHql.substring(0,leftJoinIndex) + countHql.substring(firstWhereIndex);
		}
		if (orderIndex<1){//默认的排序
			hqlBuffer.append(" order by token.id desc");
		}else{			
			hqlBuffer.append(" order by " +userOrderByHql);
		}
		String queryHql = queryHeadSql + hqlBuffer.toString();
		
		List<Object[]> queryRsList = this.queryByParam(countHql, queryHql, condition.getParameterMap(), page);	
		
		List rsList = this.setTokenToEntity(queryRsList);
		
		return rsList;
	}
	/**
	 * 根据实体信息查找流程令牌 
	 *
	 * @param condition
	 * @param entityId
	 * @return
	 * @throws ProcessException
	 */
	public Token queryTokenByEntityID(BPQueryCondition condition, Long entityId) throws ProcessException{
		String processName = condition.getProcessName();
		String entityPkName = condition.getEntityPkName();
		
		String entityClassName = condition.getEntityClass().getName();		
					
		StringBuffer hqlBuffer = new StringBuffer();		
		hqlBuffer.append("select distinct token from org.jbpm.graph.exe.Token token ");	
		hqlBuffer.append(" join token.processInstance pi  ");
		hqlBuffer.append(" ,  "+entityClassName + " entity ");//清单类				
		hqlBuffer.append(" where ");			
		hqlBuffer.append(" token.procNameKey='"+processName+"%' ");
		hqlBuffer.append(" and pi.end = null and token.end = null and token.isAbleToReactivateParent = true");
		hqlBuffer.append(" and token.entityId="+entityId);
		hqlBuffer.append(" and entity."+entityPkName+" = token.entityId");
		
					
		final String hql = hqlBuffer.toString();
		
		List list = this.getHibernateTemplate().executeFind(new HibernateCallback(){

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);	
				List<Token> list= query.list();
				//避免延迟加载
				for (Iterator<Token> iterator = list.iterator(); iterator.hasNext();) {
					Token token = iterator.next();
					token.getName();					
				}				
				return list;				
			}			
		});		
		if (list.isEmpty()){
			return null;
		}else if (list.size()>1){
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{"one entity in multi processInstances"});
			
		}
	
		return (Token)list.get(0);
	}
	/**
	 * 根据实体信息查找流程实例 
	 *
	 * @param condition
	 * @param entityId
	 * @return
	 * @throws ProcessException
	 */
	public ProcessInstance queryProcessInstanceByEntityID(BPQueryCondition condition, Long entityId) throws ProcessException{
		String processName = condition.getProcessName();
		String entityPkName = condition.getEntityPkName();
		
		String entityClassName = condition.getEntityClass().getName();		
		
		//流程名条件
		StringBuffer pnameCondition = new StringBuffer();
		String process[] = processName.split("\\|");
		pnameCondition.append(" pi.procNameKey='" + process[0] + "'"); 
		if(process.length > 1){
			pnameCondition.insert(0,"(");
			for(int i = 1; i< process.length; i++ ){
				pnameCondition.append(" or pi.procNameKey='" + process[i] + "'"); 
			}
			pnameCondition.append(")");
		}
		
		StringBuffer hqlBuffer = new StringBuffer();		
		hqlBuffer.append("select distinct pi from org.jbpm.graph.exe.ProcessInstance pi  ");
		hqlBuffer.append(" ,  "+entityClassName + " entity ");//清单类				
		hqlBuffer.append(" where ");
		hqlBuffer.append(pnameCondition.toString());
		hqlBuffer.append(" and pi.entityId = "+entityId);
		hqlBuffer.append(" and entity."+entityPkName+" = pi.entityId");
		hqlBuffer.append(" order by pi.id desc");
					
		final String hql = hqlBuffer.toString();
		
		List list = this.getHibernateTemplate().executeFind(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);	
				List<ProcessInstance> list= query.list();
				return list;				
			}			
		});		
		if (list.isEmpty()){
			return null;
		}else if (list.size()>1){
			//throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{"one entity in multi processInstances"});
		}
		return (ProcessInstance)list.get(0);
	}
	/**
	 * 根据实体信息查相应子令牌（用于fork/join结点内的查询）
	 *
	 * @param condition
	 * @param tokenName
	 * @param entityId
	 * @return
	 * @throws ProcessException
	 */
	public Token querySubTokenByEntityID(BPQueryCondition condition, String tokenName, Long entityId) throws ProcessException{
		String processName = condition.getProcessName();
		String entityPkName = condition.getEntityPkName();
		
		String entityClassName = condition.getEntityClass().getName();
					
		StringBuffer hqlBuffer = new StringBuffer();		
		hqlBuffer.append("select distinct token from org.jbpm.graph.exe.Token token  ");	
		hqlBuffer.append(" join token.processInstance pi  ");	
		hqlBuffer.append(" ,  "+entityClassName + " entity");//清单类				
		hqlBuffer.append(" where ");			
		hqlBuffer.append(" pi.procNameKey='"+processName+"' ");
		hqlBuffer.append(" and token.name = '"+tokenName+"'");
		hqlBuffer.append(" and pi.end = null and token.end = null and token.isAbleToReactivateParent = true");
		hqlBuffer.append(" and entity."+entityPkName+" = pi.entityId");
		hqlBuffer.append(" and pi."+entityPkName+" = "+entityId);
					
		final String hql = hqlBuffer.toString();
		
		List list = this.getHibernateTemplate().executeFind(new HibernateCallback(){

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);	
				List<Token> list= query.list();
				//避免延迟加载
				for (Iterator<Token> iterator = list.iterator(); iterator.hasNext();) {
					Token token = iterator.next();
					token.getName();					
				}				
				return list;			
			}			
		});
		
		if (list.isEmpty()){
			return null;
		}else if (list.size()>1){
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{"one entity in multi processInstances"});
			
		}
	
		return (Token)list.get(0);
	}
	/**
	 * 根据实体信息查找任务实例 
	 *
	 * @param condition
	 * @param taskName
	 * @param entityId
	 * @return
	 * @throws ProcessException
	 */
	public TaskInstance queryTaskInstanceByEntityID(BPQueryCondition condition, String taskName, Long entityId) throws ProcessException{
		String processName = condition.getProcessName();
		String entityPkName = condition.getEntityPkName();
		String entityClassName = condition.getEntityClass().getName();
		
		//		多个任务名以'|'分隔。
		String[] taskNames= taskName.split("\\|");
		String taskNameCond = null;
		if (taskNames.length == 1)
			taskNameCond = " = '"+taskName+"'";
		else{
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < taskNames.length; i++) {
				sb.append("'"+taskNames[i]+"',");
			}
			taskNameCond = " in (" + sb.deleteCharAt(sb.length()-1) + ")";
		}
					
		StringBuffer hqlBuffer = new StringBuffer();		
		hqlBuffer.append("select distinct ti from org.jbpm.taskmgmt.exe.TaskInstance ti ");	
		hqlBuffer.append(" join ti.processInstance pi  ");
		hqlBuffer.append(" , "+entityClassName + " entity");//清单类
				
		hqlBuffer.append(" where ");			
		hqlBuffer.append(" ti.procNameKey='"+processName+"' ");
		hqlBuffer.append(" and ti.name  "+taskNameCond);
		hqlBuffer.append(" and ti.isSuspended = false and ti.isOpen = true  ");
		hqlBuffer.append(" and pi.end = null ");
		hqlBuffer.append(" and entity."+entityPkName+" = pem.entityId");
		hqlBuffer.append(" and ti.procNameKey = "+entityId);
		
		final String hql = hqlBuffer.toString();
		
		List list = this.getHibernateTemplate().executeFind(new HibernateCallback(){

			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);	
				List<TaskInstance> list= query.list();
				//避免延迟加载
				for (Iterator<TaskInstance> iterator = list.iterator(); iterator.hasNext();) {
					TaskInstance ti = iterator.next();
					ti.getName();					
				}				
				return list;				
			}			
		});
		
		if (list.isEmpty()){
			return null;
		}else if (list.size()>1){
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{"one entity in multi processInstances"});
			
		}
	
		return (TaskInstance)list.get(0);
	}
	@Override
	public Class getEntityClass() {
		// 无具体类
		return null;
	}
	
	private List setTaskToEntity(List<Object[]> queryResult){
		List list = new ArrayList();
		IUserService userService = SourceTemplate.getBean(IUserService.class, BeanNameConstants.USER_SERVICE);
		for (Object[] objects : queryResult) {
			BaseBpmEntity entity = (BaseBpmEntity)objects[0];//第一个为关联实体		
			String actorId = (String)objects[objects.length-6];
			Date end = (Date)objects[objects.length-5];
			Long taskId = (Long)objects[objects.length-4];//倒数第二个为任务实例ID
			String taskName = (String)objects[objects.length-3];//倒数第二个为任务名
			String holdUserId = (String)objects[objects.length-2];//倒数第一个为领用人
			String description = (String)objects[objects.length-1]; 
			
			Object ext1 = null;
			if (objects.length == 8) {
				ext1 = objects[objects.length-7];
			}
			
			if (entity.getTaskId() != null) {
				try{
					entity = (BaseBpmEntity) entity.clone();
				}catch(CloneNotSupportedException ex){
					//error
				}
			}
			if (StringUtils.isNotBlank(actorId)) {
				Long userId = Long.parseLong(actorId);
				entity.setTaskActorId(userId);
				Buser user = userService.getUserById(userId);
				if (user != null)  {
					entity.setTaskActorName(user.getUserName());
				}
			}
			entity.setTaskEnd(end);
			entity.setTaskName(taskName);
			entity.setTaskId(taskId);	
			entity.setTaskCnName(description);
			if (holdUserId != null){
//				entity.setHoldUserId(Long.valueOf(holdUserId));
			}
			entity.getExtInfo().put("ext1",ext1);
			list.add(entity);
		}
		return list;
	}
	
	private List setTokenToEntity(List<Object[]> queryResult){
		
		for (Object[] objects : queryResult) {
			BaseBpmEntity entity = (BaseBpmEntity)objects[0];//第一个为关联实体			
			Long tokenId = (Long)objects[objects.length-1];//倒数第一个tokenId			
			entity.setTokenId(tokenId);			
		}
		
		if (!queryResult.isEmpty() && queryResult.get(0).length==2){
			List rsList = new ArrayList();
			for (Object[] objects : queryResult) {
				rsList.add(objects[0]);
			}
			return rsList;
		}else{
			return queryResult;
		}
	}
	
	//-----------------------统计任务
	
	/**
	 * 统计任务个数 
	 *
	 * @param condition
	 * @param userId
	 * @param taskName 任务名 <li>单个任务：任务名  <li>多个任务（同属一个流程）：任务名|任务名|...|任务名。以'|'分隔
	 * @return List< Object[String,String,String,String,Long]> pd.name, pd.description流程中文名, ti.name, ti.description任务中文名, count(ti.id)任务个数
	 */
	public List statisticTask(BPQueryCondition condition, Long userId, String taskName){
		List<Object[]> procTaskAuthList= null;//授权的任务名组  【机构ID,流程名,任务名】
		{//查询用户拥有的任务权限
			IUserService userService = SourceTemplate.getBean(IUserService.class,BeanNameConstants.USER_SERVICE);
			
			Long userBrchId=userService.getUserById(userId).getBrchId();//所在机构
			List<Role> roleList = userService.queryUserRole(userId);
			StringBuffer roleSb = new StringBuffer();
			for (Role role : roleList) {
				roleSb.append(role.getRoleId()+",");
			}
			String userRoleIds=roleSb.append("-1").toString();//拥有的角色集合
			
			StringBuffer hqlsb = new StringBuffer("select distinct ta.brchId, p.procNameKey, t.taskName from ProcessDef p, HumnTask t, HumnTaskActr ta where ");
			hqlsb.append(" ");
			
			List<String[]> procTaskList = condition.getQueryProcTasks();
			if (procTaskList.isEmpty()){//单个流程的任务
				//多个任务名以'|'分隔。
				String[] taskNames= taskName.split("\\|");
				String taskNameCond = null;
				if (taskNames.length == 1)
					taskNameCond = " = '"+taskName+"'";
				else{
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < taskNames.length; i++) {
						sb.append("'"+taskNames[i]+"',");
					}
					taskNameCond = " in (" + sb.deleteCharAt(sb.length()-1) + ")";
				}
				hqlsb.append(" t.taskName  " + taskNameCond);	
				hqlsb.append(" and p.procNameKey = '"+condition.getProcessName()+"' ");
			}else{//多个流程的任务
				for (int i=0; i<procTaskList.size(); i++) {
					String procNm = procTaskList.get(i)[0];
					String taskNm = procTaskList.get(i)[1];
					if (i==0)
						hqlsb.append("  (1=0  ");
					
					hqlsb.append(" 	or (t.taskName = '"+taskNm+ "' and p.procNameKey = '"+procNm+"' ) ");	
					
					if (i==procTaskList.size()-1)
						hqlsb.append(" )");
					
				}
			}
			hqlsb.append(" and p.id=t.procId and t.id=ta.taskId ");
			hqlsb.append(" and (ta.actrUserId="+userId+" or (ta.actrBrchId="+userBrchId+" and ta.actrRoleId in ("+userRoleIds+")) or (ta.actrBrchId="+userBrchId+" and ta.actrRoleId is null and ta.actrUserId is null)  ) ");
			
			procTaskAuthList = this.getHibernateTemplate().find(hqlsb.toString());
			//
		}
		
		
		if (procTaskAuthList.isEmpty()){//没有任何任务的权限
			return new ArrayList(0);
		}
		
		StringBuffer hqlBuffer = new StringBuffer();		
		hqlBuffer.append("select pd.name, pd.description, ti.name, ti.description , count(ti.id) from org.jbpm.taskmgmt.exe.TaskInstance ti  ");
		hqlBuffer.append(" join ti.processInstance pi");
		hqlBuffer.append(" join pi.processDefinition pd where ");
		hqlBuffer.append("  ti.isSuspended != true and ti.isOpen=true and ti.end is null and ");
		
		if (procTaskAuthList.size() ==1){//只有一个任务时
			Long brchId = (Long)procTaskAuthList.get(0)[0];
			String procNm = (String)procTaskAuthList.get(0)[1];
			String taskNm = (String)procTaskAuthList.get(0)[2];
			hqlBuffer.append(" 	(ti.brchId="+brchId+" and ti.name='"+taskNm+ "' and ti.procNameKey='"+procNm+"' ) ");	
		}else{//一次查多个任务时
			hqlBuffer.append(" (");
			for (int i=0; i<procTaskAuthList.size(); i++) {
				Long brchId = (Long)procTaskAuthList.get(i)[0];
				String procNm = (String)procTaskAuthList.get(i)[1];
				String taskNm = (String)procTaskAuthList.get(i)[2];
				if (i!=0)
					hqlBuffer.append("  or  ");				
				hqlBuffer.append(" 	(ti.brchId="+brchId+" and ti.name ='"+taskNm+ "' and ti.procNameKey='"+procNm+"' ) ");	
				
			}
			hqlBuffer.append(" )");
		}
		
		hqlBuffer.append(" GROUP BY  pd.name, pd.description, ti.name, ti.description ");
				
		List<Object[]> list = this.getHibernateTemplate().find(hqlBuffer.toString());
		return list;
	}
	
	private static final List<String> getUserHqlParts(String userHql){
		List<String> userHqlParts = new ArrayList<String>(4);
		
		String userLowerHql = userHql.toLowerCase();
		int selectIndex = userLowerHql.indexOf("select ");
		int fromIndex = userLowerHql.indexOf("from ");
		int whereIndex = userLowerHql.indexOf("where ");
		int orderIndex = userLowerHql.indexOf("order by");
		
		String userSelectHql = null;
		if (selectIndex>=0){
			userSelectHql = userHql.substring(selectIndex+"select".length(), fromIndex);			
		}
		userHqlParts.add(userSelectHql);
		
		int tableEndIndex = whereIndex>0?whereIndex:(orderIndex>0?orderIndex:userHql.length());
		String userFromHql = userHql.substring(fromIndex+"from".length(), tableEndIndex);
		userHqlParts.add(userFromHql);
		
		String userWhereHql = "";
		if (whereIndex>0){
			userWhereHql = userHql.substring(whereIndex+"where".length(), orderIndex>0?orderIndex:userHql.length());
		}
		userHqlParts.add(userWhereHql);
		
		String userOrderByHql = orderIndex>0?userHql.substring(orderIndex+"order by".length()):"";
		userHqlParts.add(userOrderByHql);
		
		return userHqlParts;
	}
	
	public List queryDoneTaskAndEntity(BPQueryCondition condition,Long userId,Page page){
		condition.contextInitialized();		
		String userHql = condition.getQueryHql();
		
		List<String> userHqlParts = getUserHqlParts(userHql);
		String userSelectHql = userHqlParts.get(0);
		if (StringUtils.isBlank(userSelectHql)) {
			userSelectHql = condition.getEntityAlias();
		}
		String userFromHql = userHqlParts.get(1);
		String userWhereHql = userHqlParts.get(2);
		String userOrderByHql = userHqlParts.get(3);
		
		String countHeadSql = "select count(distinct ti.id) ";
		String queryHeadSql = "select "+userSelectHql+",trace.delegateUser ,ti.actorId,ti.end, ti.id, ti.name, ti.preActor,ti.description ";
//		if (userSelectHql.indexOf(" distinct ") < 0)
//		  queryHeadSql = "select distinct "+userSelectHql+", ti.id, ti.name, ti.preActor,ti.description ";
		StringBuffer hqlBuffer = new StringBuffer();		
		hqlBuffer.append(" from org.jbpm.taskmgmt.exe.TaskInstance ti ,TaskTraceInfo trace");	
		hqlBuffer.append(" , "+userFromHql+ " where ");//清单类
		hqlBuffer.append(" ti.id=trace.taskId ");
		hqlBuffer.append(" and trace.entityId=ti.entityId ");
		hqlBuffer.append(" and ti.actorId='").append(userId).append("' ");
		
		
		hqlBuffer.append(" and ti.end is not null ");
		hqlBuffer.append(" and "+condition.getEntityAlias()+"."+condition.getEntityPkName()+"=ti.entityId");
		hqlBuffer.append(" and "+userWhereHql);		
		String countHql = countHeadSql + hqlBuffer.toString();
		if (countHql.toLowerCase().indexOf("left join") > 1){//统计总数时去掉左连接
			int leftJoinIndex = countHql.toLowerCase().indexOf("left join");
			int firstWhereIndex = countHql.indexOf(" where  ");
			countHql = countHql.substring(0,leftJoinIndex) + countHql.substring(firstWhereIndex);
		}
		if (StringUtils.isBlank(userOrderByHql)){//默认的排序
			hqlBuffer.append(" order by ti.id desc");
		}else{			
			hqlBuffer.append(" order by " +userOrderByHql);
		}
		String queryHql = queryHeadSql + hqlBuffer.toString();
		
		List<Object[]> queryRsList = this.queryByParam(countHql, queryHql, condition.getParameterMap(), page);
		
		List rsList = this.setTaskToEntity(queryRsList);
		
		return rsList;
	}
	
	public List<Map<String,Object>> getTodoTaskList(Long processInstanceId){
		List<Map<String,Object>> list = null;
		if (processInstanceId != null) {
			String sql = "select * from JBPM_TASKINSTANCE where END_ is null and PROCINST_=?";
			list = getMapListByStanderdSQL(sql, new Object[]{processInstanceId}, null);
		}else{
			list = Collections.emptyList();
		}
		return list;
	}
	
	public List querySimpleTaskAndEntity(BPQueryCondition condition, Long userId, String taskName, Page page){
		//---------组装查询
		condition.contextInitialized();		
		String userHql = condition.getQueryHql();
		
		List<String> userHqlParts = getUserHqlParts(userHql);
		String userSelectHql = userHqlParts.get(0);
		if (StringUtils.isBlank(userSelectHql)) {
			userSelectHql = condition.getEntityAlias();
		}
		String userFromHql = userHqlParts.get(1);
		String userWhereHql = userHqlParts.get(2);
		String userOrderByHql = userHqlParts.get(3);
		

		Map<String,Object> param = new HashMap<String,Object>();
		if (condition.getParameterMap() != null && condition.getParameterMap().size() > 0) {
			param.putAll(condition.getParameterMap());
		}
		
		String countHeadSql = "select count(distinct ti.id) ";
		String queryHeadSql = "select "+userSelectHql+", ti.actorId,ti.end,ti.id, ti.name, ti.preActor,ti.description ";
//				if (userSelectHql.indexOf(" distinct ") < 0)
//				  queryHeadSql = "select distinct "+userSelectHql+", ti.id, ti.name, ti.preActor,ti.description ";
		StringBuilder hqlBuffer = new StringBuilder();		
		hqlBuffer.append(" from org.jbpm.taskmgmt.exe.TaskInstance ti ");	
		hqlBuffer.append(" , "+userFromHql+ " where ");//清单类
		hqlBuffer.append(" ti.name='" + taskName + "'");
		//hqlBuffer.append(" and ti.actorId is null ");
		hqlBuffer.append(" and ti.isSuspended != true and ti.isOpen=true and ti.end is null ");
		hqlBuffer.append(" and "+condition.getEntityAlias()+"."+condition.getEntityPkName()+"=ti.entityId");
		hqlBuffer.append(" and "+userWhereHql);		
		String countHql = countHeadSql + hqlBuffer.toString();
//		if (countHql.toLowerCase().indexOf("left join") > 1){//统计总数时去掉左连接
//			int leftJoinIndex = countHql.toLowerCase().indexOf("left join");
//			int firstWhereIndex = countHql.indexOf(" where  ");
//			countHql = countHql.substring(0,leftJoinIndex) + countHql.substring(firstWhereIndex);
//		}
		if (StringUtils.isBlank(userOrderByHql)){//默认的排序
			hqlBuffer.append(" order by ti.id desc");
		}else{			
			hqlBuffer.append(" order by " +userOrderByHql);
		}
		String queryHql = queryHeadSql + hqlBuffer.toString();
		
		List<Object[]> queryRsList = this.queryByParam(countHql, queryHql,param, page);
		
		List rsList = this.setTaskToEntity(queryRsList);
		
		return rsList;
	}
	
	public boolean isProcessEnd(Long processId){
		boolean result = true;
		if (processId != null) {
			String hql = "from org.jbpm.graph.exe.ProcessInstance pi where pi.id=?";
			List<ProcessInstance> list = find(hql,processId);
			if (list != null && list.size() > 0) {
				for (ProcessInstance pi : list) {
					if (!pi.hasEnded()) {
						result = false;
						break;
					}
				}
			}
		}
		return result;
	}
	
	public ProcessInstance getProcessInstance(Long processId){
		ProcessInstance result = null;
		String hql = "from org.jbpm.graph.exe.ProcessInstance pi where pi.id=?";
		List<ProcessInstance> list = find(hql,processId);
		if (list != null && list.size() > 0) {
			result = list.get(0);
		}
		return result;
	}
	
	public TaskInstance getTaskInstance(Long instanceId,String taskName){
		TaskInstance result = null;
		if (instanceId != null && StringUtils.isNotBlank(taskName)) {
			String hql = "select ti from org.jbpm.taskmgmt.exe.TaskInstance ti join ti.processInstance pi where pi.id=? and ti.name=?";
			List<TaskInstance> list = find(hql, new Object[]{instanceId, taskName});
			if (list != null && list.size() > 0) {
				result = list.get(list.size() - 1);
			}
		}
		return result;
	}
	
	public List<TaskInstance> getTaskInstanceList(Long instanceId,String taskName){
		List<TaskInstance> result = new ArrayList<TaskInstance>();
		if (instanceId != null && StringUtils.isNotBlank(taskName)) {
			String hql = "select ti from org.jbpm.taskmgmt.exe.TaskInstance ti join ti.processInstance pi where pi.id=? and ti.name=?";
			result = find(hql, new Object[]{instanceId, taskName});
		}
		return result;
	}
	
	public TaskInstance getTaskInstanceById(Long taskId){
		TaskInstance result = null;
		if (taskId != null) {
			String hql = "select ti from org.jbpm.taskmgmt.exe.TaskInstance ti  where ti.id=?";
			List<TaskInstance> list = find(hql, new Object[]{taskId});
			if (list != null && list.size() > 0) {
				result = list.get(0);
			}
		}
		return result;
	}
	
	public List<Task> getTaskListByPd(Long pdId){
		List<Task> result = null;
		if (pdId != null) {
			String hql = "select t from org.jbpm.taskmgmt.def.Task t join t.processDefinition pd where pd.id=?";
			result = find(hql, new Object[]{pdId}, null);
		}
		if (result == null) {
			result = Collections.emptyList();
		}
		return result;
	}
}
