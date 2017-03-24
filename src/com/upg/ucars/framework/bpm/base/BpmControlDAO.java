package com.upg.ucars.framework.bpm.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.EntityMode;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.graph.def.DelegationException;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.svc.Services;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.util.EqualsUtil;
import org.springmodules.workflow.jbpm31.JbpmCallback;
import org.springmodules.workflow.jbpm31.support.JbpmDaoSupport;

import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.bpm.ext.IBpmPlantfromClientService;
import com.upg.ucars.framework.bpm.tasktrace.service.ITaskTraceInfoService;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ProcessException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.framework.exception.core.BaseAppRuntimeException;
import com.upg.ucars.framework.exception.core.UserConcemedException;
import com.upg.ucars.mapping.framework.bpm.TaskTraceInfo;
import com.upg.ucars.model.DealOpinionInfo;
import com.upg.ucars.model.TaskProcessResult.ProcessBehavior;
import com.upg.ucars.util.BeanUtils;
import com.upg.ucars.util.SourceTemplate;


/**
 * Jbpm基本DAO, 用于对JBPM提供的方法的包装
 * 
 * <li>涉及流程运转的方法必须捕获Exception再转换成ProcessException往外抛.
 * 
 * @author shentuwy
 * @date 2012-7-11
 *
 */
public class BpmControlDAO extends JbpmDaoSupport{	
	/**
	 * 创建流程实例
	 * @param processDefinitionName
	 * @return
	 * @throws DAOException 
	 */
	public Long createProcessInstance(final String processDefinitionName)throws ProcessException{
		
		try {			
			Long id = (Long)this.getTemplate().execute(
					
					new JbpmCallback() {

						public Object doInJbpm(JbpmContext context)
								throws JbpmException {
							ProcessDefinition pd = context.getGraphSession().findLatestProcessDefinition(processDefinitionName);
							ProcessInstance pi = pd.createProcessInstance();					
							return Long.valueOf(pi.getId()+"");								
						}

					});
				
				return id;
			
		} catch (Exception e) {//捕获jBPM中的异常，转化成DAOException.		
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_CREATE_PROCESS_ERROR, new String[]{processDefinitionName}, e);
		}
		
	}
	
	
	/**
	 * 根据产品编号和机构创建流程实例
	 * @param productNo 产品编号
	 * @return 流程实例ID
	 * @throws DAOException
	 * @throws ServiceException 
	 */
	public Long createProcessInstanceByProdAndBrch(String prodNo, Long brchId) throws ProcessException{
		
		//创建产品对应的流程，并在流程中添加产品属性
		String processName = this.getProcessNameByProdAndBrch(prodNo, brchId);
		Long pid = this.createProcessInstance(processName);
		this.createVariable(pid, BpmConstants.VAR_PROD_NO, prodNo);//流程上下文中增加产品编号
		this.createVariable(pid, BpmConstants.VAR_BRCH_ID, brchId);//流程上下文中增加机构编号		
		
		return pid;//创建流程实例
	}

	/**
	 * 根据产品编号查找流程
	 * @param productNo 产品编号
	 * @return
	 * @throws DAOException
	 */
	private String getProcessNameByProduct(String productNo) throws ProcessException{		
		String hql = "select procName from ProdProcMap o where o.prodNo = ? ";
		List list = SourceTemplate.getHibernateTemplate().find(hql, productNo);
		if (list.isEmpty() || list.size() != 1){
			//用户不关心异常无须国际化
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{"产品未明确定义业务流程，产品编号："+productNo});
			
		}
		return list.get(0).toString();
	}
	
	/**
	 * 查找流程
	 * @param prodNo 产品编号
	 * @param brchNo 机构编号
	 * @return
	 * @throws DAOException
	 */
	private String getProcessNameByProdAndBrch(String prodNo, Long brchId)throws ProcessException{		
		String hql = "select procName from ProdProcMap o where o.prodNo = ? and o.brchId = ?";
		List list = SourceTemplate.getHibernateTemplate().find(hql, new Object[]{prodNo, brchId});
		if (list.isEmpty()){
			hql="from Product where prodNo=?";
			List prodList = SourceTemplate.getHibernateTemplate().find(hql,new Object[]{prodNo});
			if (prodList!=null&&prodList.size()>0) {
				
			} else {
				//用户不关心异常无须国际化
				throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{"根据产品号【"+prodNo+"】没有找到对应的产品信息"});
				
			}
		} 
		return list.get(0).toString();
	}
	/**
	 * 创建流程变量
	 * @param processInstanceId
	 * @param key
	 * @param obj
	 */
	public void createVariable(final Long processInstanceId, final String key, final Object obj)throws ProcessException{
		
		try {
			this.getTemplate().execute(
					
					new JbpmCallback() {

						public Object doInJbpm(JbpmContext context)
								throws JbpmException {
							ProcessInstance pi = context.getProcessInstance(processInstanceId.longValue());
							pi.getContextInstance().createVariable(key, obj);
							return null;						
						}

					});		
		} catch (Exception e) {			
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
			
		}
		
		
		
	}	
	
	/**
	 * 创建令牌变量
	 * @param processInstanceId
	 * @param key
	 * @param obj
	 */
	public void createTokenVariable(final Long tokenId, final String key, final Object obj)throws ProcessException{
		
		try {
			this.getTemplate().execute(
					
					new JbpmCallback() {

						public Object doInJbpm(JbpmContext context)
								throws JbpmException {
							Token token = context.loadToken(tokenId.longValue());
							token.getProcessInstance().getContextInstance().createVariable(key, obj, token);
							return null;						
						}

					});		
		} catch (Exception e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}		
		
	}
	
	
	/**
	 * 删除令牌变量
	 * @param processInstanceId
	 * @param key
	 * @param obj
	 */
	public void deleteTokenVariable(final Long tokenId, final String key)throws ProcessException{
		
		try {
			this.getTemplate().execute(
					
					new JbpmCallback() {

						public Object doInJbpm(JbpmContext context)
								throws JbpmException {
							Token token = context.loadToken(tokenId.longValue());
							token.getProcessInstance().getContextInstance().deleteVariable(key, token);
							return null;						
						}

					});		
		} catch (Exception e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}		
		
	}	
	
	/**
	 * 删除变量
	 * @param processInstanceId
	 * @param key
	 * @param obj
	 */
	public void deleteProcessVariable(final Long processInstanceId, final String key)throws ProcessException{
		
		try {
			this.getTemplate().execute(
					
					new JbpmCallback() {

						public Object doInJbpm(JbpmContext context)
								throws JbpmException {
							ProcessInstance pi = context.getProcessInstance(processInstanceId.longValue());
							pi.getContextInstance().deleteVariable(key);
							return null;						
						}

					});		
		} catch (Exception e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}		
		
	}	
	/**
	 * 获取任务上下文
	 * @param taskId
	 * @param key
	 * @return
	 */
	public Object getTaskVariable(final Long taskId, final String key)throws ProcessException{
		
		return this.getTemplate().execute(
				
				new JbpmCallback() {

					public Object doInJbpm(JbpmContext context)
							throws JbpmException {
						TaskInstance ti = context.loadTaskInstance(taskId.longValue());
						return ti.getVariable(key);
					}

				});		
		
	}
	
	/**
	 * 获取流程上下文
	 * @param taskId
	 * @param key
	 * @return
	 */
	public Object getTokenVariable(final Long tokenId, final String key)throws ProcessException{
		
		return this.getTemplate().execute(
				
				new JbpmCallback() {

					public Object doInJbpm(JbpmContext context)
							throws JbpmException {
						Token token = context.getToken(tokenId.longValue());
						return token.getProcessInstance().getContextInstance().getVariable(key, token);
					}

				});		
		
	}
	
	/**
	 * 获取流程上下文
	 * @param taskId
	 * @param key
	 * @return
	 */
	public Object getProcessVariable(final Long processId, final String key)throws ProcessException{
		
		return this.getTemplate().execute(
				
				new JbpmCallback() {

					public Object doInJbpm(JbpmContext context)
							throws JbpmException {
						ProcessInstance pi = context.getProcessInstance(processId.longValue());
						return pi.getContextInstance().getVariable(key);
					}

				});		
		
	}
	/**
	 * 根据任务ID找流程
	 * @param taskId
	 * @return
	 * @throws DAOException
	 */
	public Long getProcessIdByTaskId(final Long taskId)throws DAOException{
		Long id = (Long) this.getTemplate().execute(
				
				new JbpmCallback() {

					public Object doInJbpm(JbpmContext context)
							throws JbpmException {
						TaskInstance ti = context.loadTaskInstance(taskId.longValue());
						long pid = ti.getProcessInstance().getId();
						
						return Long.valueOf(pid+"");
					}

				});		
		
		return id;
	}
	/**
	 * 根据任务ID找令牌ID
	 * @param taskId
	 * @return
	 * @throws DAOException
	 */
	public Long getRootTokenIdByProcessId(final Long processId)throws ProcessException{
		Long id = (Long) this.getTemplate().execute(
				
				new JbpmCallback() {

					public Object doInJbpm(JbpmContext context)
							throws JbpmException {
						long tokenId = context.loadProcessInstance(processId.longValue()).getRootToken().getId();									
						return new Long(tokenId);
					}

				});		
		
		return id;
	}
	/**
	 * 根据任务ID找令牌ID
	 * @param taskId
	 * @return
	 * @throws DAOException
	 */
	public Long getTokenIdByTaskId(final Long taskId)throws ProcessException{
		Long id = (Long) this.getTemplate().execute(
				
				new JbpmCallback() {

					public Object doInJbpm(JbpmContext context)
							throws JbpmException {
						TaskInstance ti = context.loadTaskInstance(taskId.longValue());						
						return new Long(ti.getToken().getId());
					}

				});		
		
		return id;
	}
	/**通过令牌获取令牌所在的流程
	 * 
	 * @param tokenId
	 * @return
	 * @throws DAOException
	 */
	public Long getProcessIdByTokenId(final Long tokenId)throws ProcessException{
		Long id = (Long) this.getTemplate().execute(
				
				new JbpmCallback() {

					public Object doInJbpm(JbpmContext context)
							throws JbpmException {
						long pid = context.loadToken(tokenId.longValue()).getProcessInstance().getId();						
						return new Long(pid);
					}

				});		
		
		return id;
	}
	
	public ProcessInstance findProcessInstance(Long id)throws ProcessException{
		return this.getTemplate().findProcessInstance(id);
	}
	
	public void saveProcessInstance(final Long ProcessInstanceId)throws DAOException{		
		try{
			this.getTemplate().execute(
					
					new JbpmCallback() {
	
						public Object doInJbpm(JbpmContext context)
								throws JbpmException {
							ProcessInstance pi = context.getProcessInstance(ProcessInstanceId.longValue());
							context.save(pi);
							return null;
						}
	
					});
		} catch (Exception e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}

	}
	
	
	public void signal(final Long processInstanceId)throws ProcessException{
		try {
			this.getTemplate().execute(
					
					new JbpmCallback() {

						public Object doInJbpm(JbpmContext context)
								throws JbpmException {
							context.loadProcessInstance(processInstanceId.longValue()).signal();
							return null;
						}

					});
		}catch (Exception e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}
	}
	/**
	 * 流程流转
	 * @param ProcessInstanceId
	 * @param transitionName 流转方向
	 */
	public void signal(final Long ProcessInstanceId, final String transitionName)throws ProcessException{
		
		try {
			this.getTemplate().execute(
					
					new JbpmCallback() {

						public Object doInJbpm(JbpmContext context)
								throws JbpmException {
							context.loadProcessInstance(ProcessInstanceId.longValue()).signal(transitionName);
							return null;
						}

					});
		}catch (DelegationException e) {//JBPM会将ACTION中的异常转化成DelegationException		
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e.getCause());	
		}catch (ServiceException e) {//捕获jBPM中的异常，转化成DAOException.
			throw e;
		}catch (Exception e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}
		
	}
	/**
	 * 令牌流转  
	 * @param tokenId
	 * @throws DAOException
	 */
	public void signalToken(final Long tokenId)throws ProcessException{
		try {
			this.getTemplate().execute(
					
					new JbpmCallback() {

						public Object doInJbpm(JbpmContext context)
								throws JbpmException {
							context.loadToken(tokenId.longValue()).signal();
							return null;
						}

					});
		}catch (DelegationException e) {//JBPM会将ACTION中的异常转化成DelegationException
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}catch (ServiceException e) {
			throw e;
		}catch (Exception e) {//捕获jBPM中的异常，转化成DAOException.
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}
		
	}
	/**
	 * 令牌流转
	 * @param tokenId
	 * @param transitionName
	 * @throws DAOException
	 */
	public void signalToken(final Long tokenId, final String transitionName)throws ProcessException{
		
		try {
			this.getTemplate().execute(
					
					new JbpmCallback() {

						public Object doInJbpm(JbpmContext context)
								throws JbpmException {
							context.loadToken(tokenId.longValue()).signal(transitionName);
							return null;
						}

					});
		}catch (DelegationException e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}catch (ServiceException e) {
			throw e;
		}catch (Exception e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}
		
	}

	
	public void dealTask(final Long taskId)throws ProcessException{
		try {
			this.getTemplate().execute(
					
					new JbpmCallback() {

						public Object doInJbpm(JbpmContext context)
								throws JbpmException {							
							TaskInstance ti = context.loadTaskInstance(taskId.longValue());
							ti.setVariable(BpmConstants.VAR_LAST_TASK_NAME, ti.getName());//上下文中存放最后处理的任务名
							ti.end();
							return null;
						}

					});
		}catch (DelegationException e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}catch (ServiceException e) {
			throw e;
		}catch (Exception e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}
		
		
	}
	
	
	public void dealTask(final Long taskId, final String transitionName)throws ProcessException{
		try {
			this.getTemplate().execute(
					
					new JbpmCallback() {

						public Object doInJbpm(JbpmContext context)
								throws JbpmException {
							TaskInstance ti = context.loadTaskInstance(taskId.longValue());
							ti.setVariable(BpmConstants.VAR_LAST_TASK_NAME, ti.getName());//上下文中存放最后处理的任务名
							ti.end(transitionName);							
							return null;
						}

			});
		}catch (DelegationException e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}
		
	}
	
	/**
	 * 
	 * @param taskId 审批路线的任务ID
	 * @param isPass 审批是否通过
	 * @param isBack 审批不通过是否返回给上一审批者
	 * @throws DAOException
	 */
	public void dealTaskOfAudit(final Long taskId, boolean isPass, boolean isBack)throws ProcessException{
		if (isPass){
			//默认为通过
		}else{
			Long tokenId = this.getTokenIdByTaskId(taskId);
			this.createTokenVariable(tokenId, AuditRouteDecisionHandler.Val_Audit_Is_Pass, Boolean.FALSE);
			 if (isBack){
				 this.createTokenVariable(tokenId, AuditRouteDecisionHandler.Val_Audit_Is_Back, Boolean.TRUE);				 
			 }else{
				 //默认为不返回上一审批者
			 }			 
		}
		//结束当前审批任务
		this.dealTask(taskId,AuditRouteDecisionHandler.Line_Name_To_Audit_Decision);		
	}
	
	/**
	 * 结果流程实例
	 * @param processInstanceId
	 * @throws DAOException
	 */
	public void endProcessInstance(final Long processInstanceId)throws ProcessException{
		try {
			this.getTemplate().execute(					
					new JbpmCallback() {
						public Object doInJbpm(JbpmContext context)
								throws JbpmException {					
							ProcessInstance pi = context.loadProcessInstance(processInstanceId.longValue());
							pi.end();
							pi.getTaskMgmtInstance().endAll();
							context.save(pi);
							
							return null;
						}
			});
		}catch (DelegationException e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}
	}
	
	public void endProcessInstanceByTaskId(final Long taskId)throws ProcessException{ 
		try {
			this.getTemplate().execute(					
					new JbpmCallback() {
						public Object doInJbpm(JbpmContext context)
								throws JbpmException {					
							TaskInstance ti = context.getTaskInstance(taskId.longValue());
							ProcessInstance pi = ti.getProcessInstance(); 
							pi.end();
							pi.getTaskMgmtInstance().endAll();
							context.save(pi);
							return null;
						}
			});
		}catch (DelegationException e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}catch (ServiceException e) {
			throw e;
		} catch (Exception e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}
	}
	
	/**
	 * 结束令牌
	 * @param token
	 * @throws DAOException
	 */	
	public void endToken(final Long tokenId)throws ProcessException{
		try {
			this.getTemplate().execute(					
					new JbpmCallback() {
						public Object doInJbpm(JbpmContext context)
								throws JbpmException {							
							List list = context.getTaskMgmtSession().findTaskInstancesByToken(tokenId);
							for (int i = 0; i < list.size(); i++) {
								TaskInstance ti = (TaskInstance)list.get(i);
								ti.setSignalling(false);
								ti.end();
							}
							context.loadToken(tokenId).end();
							return null;
						}

			});
		}catch (DelegationException e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}catch (ServiceException e) {			
			throw e;
		} catch (Exception e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}
	}
	
	/**
	 * 删除流程实例
	 * @param processInstanceId
	 * @throws DAOException
	 */
	public void deleteProcessInstance(final Long processInstanceId)throws ProcessException{
		try {
			this.getTemplate().execute(					
					new JbpmCallback() {
						public Object doInJbpm(JbpmContext context)
								throws JbpmException {		
							context.getGraphSession().deleteProcessInstance(processInstanceId.longValue());							
							return null;
						}
			});
		}catch (DelegationException e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		} catch (ServiceException e) {
			throw e;
		}catch (Exception e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}
	}
	
	/**
	 * 获取任务的名称
	 * @param taskInstanceId 任务实例ID
	 * @throws DAOException
	 */
	public String getTaskNameById(final Long taskInstanceId)throws ProcessException{
	
		try {
			return this.getTemplate().execute(					
					new JbpmCallback() {
						public Object doInJbpm(JbpmContext context)
								throws JbpmException {		
							return context.getTaskInstance(taskInstanceId.longValue()).getName();							 
						}
			}).toString();
		} catch (Exception e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}
	}
	
	/**
	 *  获取令牌所在的节点名称
	 * @param tokenId 令牌ID
	 * @return
	 * @throws DAOException
	 */
	public String getNodeNameByTokenId(final Long tokenId)throws ProcessException{
	
		try {
			return this.getTemplate().execute(					
					new JbpmCallback() {
						public Object doInJbpm(JbpmContext context)
								throws JbpmException {	
							String nodeName = context.loadToken(tokenId.longValue()).getNode().getName();							
							return nodeName;							 
						}
			}).toString();
		} catch (Exception e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}
	}
	
	//
	
	/**
	 * 创建流程实例
	 * @param processDefinitionName
	 * @return
	 * @throws DAOException 
	 */
	public Long startProcessInstance(final String processDefinitionName, final Object entity, final Map<String, Object> variableMap)throws ProcessException{
		
		try {			
			Long id = (Long)this.getTemplate().execute(
					
					new JbpmCallback() {

						public Object doInJbpm(JbpmContext context)
								throws JbpmException {
							ProcessDefinition pd = context.getGraphSession().findLatestProcessDefinition(processDefinitionName);
							
							Long entityId = getIdValue(context.getSessionFactory(), entity);	
							String hql = "SELECT id FROM org.jbpm.graph.exe.ProcessInstance t WHERE t.end is null  AND procNameKey=:procNameKey AND entityId=:entityId";
							
							List<Long> piIdList = context.getSession().createQuery(hql)
									.setParameter("procNameKey", pd.getProcNameKey())
									.setParameter("entityId", entityId)
									.list();
							if (!piIdList.isEmpty()){//是否存在未结束流程
								throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_EXIST_UNEND_PROCESS, new String[]{processDefinitionName, entityId.toString()});
							}							
							
							ProcessInstance pi = pd.createProcessInstance();	
							if (variableMap != null){
								pi.getContextInstance().addVariables(variableMap);//增加流程变量
								pi.setBrchId((Long)variableMap.get(BpmConstants.VAR_BRCH_ID));			
							}
							pi.setBrchId((Long)variableMap.get(BpmConstants.VAR_BRCH_ID));
//							pi.getContextInstance().createVariable(BpmConstants.VAR_ENTITY, entity);//增加业务实体							
							pi.setEntityId(entityId);											
									
							pi.signal();							
							context.save(pi);
							
							return pi.getId();								
						}

					});
				
				return id;
			
		}catch (BaseAppRuntimeException e) {	
			throw e;
		}catch (DelegationException e) {//JBPM会将ACTION中的异常转化成DelegationException
			if (e.getCause() instanceof BaseAppRuntimeException) {
				throw (BaseAppRuntimeException)e.getCause();
			}else{
				ExceptionManager.throwException(ProcessException.class, ErrorCodeConst.WF_CREATE_PROCESS_ERROR, new String[]{processDefinitionName}, e);
			}
		}catch (Exception e) {
			ExceptionManager.throwException(ProcessException.class, ErrorCodeConst.WF_CREATE_PROCESS_ERROR, new String[]{processDefinitionName}, e);
		}
		return null;
		
	}
	/**
	 * 获取主键值
	 * @param sessionFactory
	 * @param entity
	 * @return
	 */
	private Long getIdValue(SessionFactory sessionFactory, Object entity){
		ClassMetadata classMetadata = sessionFactory.getClassMetadata(entity.getClass());
		if(classMetadata==null)
			classMetadata = sessionFactory.getClassMetadata(entity.getClass().getSuperclass());
	    Serializable s = classMetadata.getIdentifier(entity, EntityMode.POJO);
	    return Long.parseLong(s.toString());      
	}

	/**
	 * 令牌流转  
	 * @param tokenId
	 * @throws ProcessException
	 */
	public void signalToken(Long tokenId, Map<String, Object> variableMap)throws ProcessException{
		this.signalToken(tokenId, null, variableMap);
	}
	/**
	 * 令牌流转
	 * @param tokenId
	 * @param transitionName
	 * @throws ProcessException
	 */
	public void signalToken(final Long tokenId, final String transitionName, final Map<String, Object> variableMap)throws ProcessException{	
		
		try {
			this.getTemplate().execute(
					
					new JbpmCallback() {

						public Object doInJbpm(JbpmContext context)
								throws JbpmException {
							Token token = context.loadToken(tokenId.longValue());
							if (variableMap!=null)
								token.getProcessInstance().getContextInstance().addVariables(variableMap, token);
							if (transitionName==null){
								Transition transition = (Transition)token.getAvailableTransitions().iterator().next();
								token.signal(transition);
							}else
								token.signal(transitionName);
							context.save(token.getProcessInstance());
							return null;
						}

					});
		}catch (DelegationException e) {//JBPM会将ACTION中的异常转化成DelegationException
			ExceptionManager.throwException(ProcessException.class, ErrorCodeConst.WF_TOKEN_PROCESS_ERROR, new String[]{tokenId.toString()}, e);
		}catch (BaseAppRuntimeException e) {	
			throw e;
		} catch (Exception e) {	
			ExceptionManager.throwException(ProcessException.class, ErrorCodeConst.WF_TOKEN_PROCESS_ERROR, new String[]{tokenId.toString()}, e);
		}
		
	}

	/**
	 * 处理任务
	 * @param taskId
	 * @param variableMap
	 * @throws ProcessException
	 */
	public void dealTask(final Long taskId, final Map<String, Object> variableMap)throws ProcessException{
		this.dealTask(taskId, null, variableMap);
		
	}
	public void onlyEndTask(final Long taskId,final Map<String,Object> variableMap) throws ProcessException{
		try {
			this.getTemplate().execute(
					
					new JbpmCallback() {

						public Object doInJbpm(JbpmContext context)
								throws JbpmException {
							TaskInstance ti = context.loadTaskInstance(taskId.longValue());
							ti.setSignalling(false);
							ti.setVariable(BpmConstants.VAR_LAST_TASK_NAME, ti.getName());//上下文中存放最后处理的任务名
							if (variableMap!=null){
								//设置任务处理信息
								DealOpinionInfo opinion = (DealOpinionInfo)variableMap.get(BpmConstants.VAR_TASK_OPINION);
								if (opinion != null){
									String taskName = StringUtils.isNotBlank(ti.getDescription()) ? ti.getDescription() : ti.getName();
									opinion.setTaskName(taskName);
									opinion.setProcessName(ti.getProcessInstance().getProcessDefinition().getName());
									SourceTemplate.getBean(IBpmPlantfromClientService.class,BeanNameConstants.BPM_PLANTFORM_CLIENT_SERVICE).traceTaskDeal(taskId, ti.getEntityId(), opinion);
									variableMap.remove(BpmConstants.VAR_TASK_OPINION);//上下文中不记录处理信息									
								}
								ti.addVariables(variableMap);
							}
							ti.end();
							context.save(ti.getProcessInstance());												
							return null;
						}

			});
		}catch (DelegationException e) {//JBPM会将ACTION中的异常转化成DelegationException			
			ExceptionManager.throwException(ProcessException.class, ErrorCodeConst.WF_TASK_PROCESS_ERROR, new String[]{taskId.toString()}, e);
		}catch (BaseAppRuntimeException e) {
			throw e;
		} catch (Exception e) {	
			e.printStackTrace();
			ExceptionManager.throwException(ProcessException.class, ErrorCodeConst.WF_TASK_PROCESS_ERROR, new String[]{taskId.toString()}, e);
		}
	}
	/**
	 * 处理任务后按指定连线流转
	 * @param taskId
	 * @param transitionName
	 * @param variableMap
	 * @throws ProcessException
	 */
	public void dealTask(final Long taskId, final String transitionName, final Map<String, Object> variableMap)throws ProcessException{
		try {
			this.getTemplate().execute(
					
					new JbpmCallback() {

						public Object doInJbpm(JbpmContext context)
								throws JbpmException {

							String actualTransitionName = transitionName;
							
							
							TaskInstance ti = context.loadTaskInstance(taskId.longValue());
							ti.setVariable(BpmConstants.VAR_LAST_TASK_NAME, ti.getName());//上下文中存放最后处理的任务名
							String agree = null;
							if (variableMap!=null){
								//设置任务处理信息
								DealOpinionInfo opinion = (DealOpinionInfo)variableMap.get(BpmConstants.VAR_TASK_OPINION);
								if (opinion != null){
									agree = opinion.getAgree();
									if (!ProcessBehavior.UNDO.getName().equals(agree)) {
										String taskName = StringUtils.isNotBlank(ti.getDescription()) ? ti.getDescription() : ti.getName();
										opinion.setTaskName(taskName);
										opinion.setProcessName(ti.getProcessInstance().getProcessDefinition().getName());
										SourceTemplate.getBean(IBpmPlantfromClientService.class,BeanNameConstants.BPM_PLANTFORM_CLIENT_SERVICE).traceTaskDeal(taskId, ti.getEntityId(), opinion);
									}
									variableMap.remove(BpmConstants.VAR_TASK_OPINION);//上下文中不记录处理信息									
								}
								ti.addVariables(variableMap);
							}
							
							
							if (ProcessBehavior.BACK.getName().equals(agree)){ //退回
								boolean backTrue = false;
								Token nowToken = ti.getToken();
								TaskInstance lastFinishedTask = findPreNearestTaskInstance(ti);
								
								if (lastFinishedTask != null) {
									ti.setSignalling(false);
									nowToken.setNode(lastFinishedTask.getTask().getTaskNode());
									TaskInstance newTaskInstance = new TaskInstance();
									try{
										BeanUtils.copyNoNullProperties(newTaskInstance, lastFinishedTask);
									}catch(Exception ex){
										ex.printStackTrace();
										throw new RuntimeException(ex);
									}
									newTaskInstance.setId(0);
									newTaskInstance.setEnd(null);
									newTaskInstance.setSignalling(true);
									newTaskInstance.setCreate(new Date());
									ti.getTaskMgmtInstance().addTaskInstance(newTaskInstance);
									
									
									ITaskTraceInfoService taskTraceInfoService = SourceTemplate.getBean(ITaskTraceInfoService.class,BeanNameConstants.TASK_TRACE_INFO_SERVICE);
									TaskTraceInfo tti = taskTraceInfoService.getTaskTraceInfoByTaskId(lastFinishedTask.getId());
									
									TaskTraceInfo newTti = new TaskTraceInfo();
									try{
										BeanUtils.copyNoNullProperties(newTti, tti);
									}catch(Exception ex){
										ex.printStackTrace();
										throw new RuntimeException(ex);
									}
									newTti.setId(null);
									newTti.setEntityId(null);
									newTti.setTaskId(newTaskInstance.getId());
									taskTraceInfoService.saveTaskTraceInfo(newTti);
									
									backTrue = true;
								}
								if (!backTrue) {
									ExceptionManager.throwException(UserConcemedException.class, ErrorCodeConst.COMMON_ERROR_CODE, "流程不能退回！");
								} else {
									actualTransitionName = null;
								}
							} else if (ProcessBehavior.UNDO.getName().equals(agree)) { //撤回
								boolean undo = false;
								if (ti.getEnd() != null && ti.getToken().getEnd() == null) {
									List<TaskInstance> deleteTasks = findLatestTasks(ti);
									if (deleteTasks != null && !deleteTasks.isEmpty()) {
										
										TaskInstance newTask = new TaskInstance();
										try{
											BeanUtils.copyNoNullProperties(newTask, ti);
										}catch(Exception ex){
											ex.printStackTrace();
											throw new RuntimeException(ex);
										}
										newTask.setId(0);
										newTask.setEnd(null);
										newTask.setSignalling(true);
										newTask.setCreate(new Date());
										Services.assignId(newTask);
										
										ti.getTaskMgmtInstance().addTaskInstance(newTask);
										
										TaskInstance oldTask = ti;
										
										ITaskTraceInfoService taskTraceInfoService = SourceTemplate.getBean(ITaskTraceInfoService.class,BeanNameConstants.TASK_TRACE_INFO_SERVICE);
										
										for (int i = 0; i < deleteTasks.size(); i++){
											TaskInstance task = deleteTasks.get(i);
											task.setSignalling(false);
											if (i == 0) {
												ti = task;
											} else {
												task.end();
												TaskTraceInfo tti = taskTraceInfoService.getTaskTraceInfoByTaskId(task.getId());
												if (tti != null && tti.getEntityId() > 0) {
													tti.setEntityId(-tti.getEntityId());
												}
											}
										}
										
										ti.getToken().setNode(oldTask.getTask().getTaskNode());
										
										
										TaskTraceInfo tti = taskTraceInfoService.getTaskTraceInfoByTaskId(oldTask.getId());
										
										TaskTraceInfo newTti = new TaskTraceInfo();
										try{
											BeanUtils.copyNoNullProperties(newTti, tti);
										}catch(Exception ex){
											ex.printStackTrace();
											throw new RuntimeException(ex);
										}
										
										
										if (tti.getEntityId() > 0) {
											tti.setEntityId(-tti.getEntityId());
										}
										
										newTti.setId(null);
										newTti.setEntityId(null);
										newTti.setTaskId(newTask.getId());
										taskTraceInfoService.saveTaskTraceInfo(newTti);
										
										actualTransitionName = null;
										undo = true;
									}
								}
								if(!undo) {
									ExceptionManager.throwException(UserConcemedException.class, ErrorCodeConst.COMMON_ERROR_CODE, "流程不能撤回！");
								}
							}
							
							
							
							
							if (actualTransitionName==null){
								Transition transition = null;
								Set<Transition> transitionSet = ti.getToken().getAvailableTransitions();
								if (transitionSet != null) {
									if (transitionSet.size() == 1) {
										transition = transitionSet.iterator().next();
									}else{
										for (Transition t : transitionSet) {
											if (StringUtils.isBlank(t.getName())) {
												transition = t;
												break;
											}
										}
									}
								}
								ti.end(transition);
							} else {
								ti.end(actualTransitionName);
							}
							
							
							context.save(ti.getProcessInstance());
							return null;
						}

			});
		}catch (DelegationException e) {//JBPM会将ACTION中的异常转化成DelegationException			
			ExceptionManager.throwException(ProcessException.class, ErrorCodeConst.WF_TASK_PROCESS_ERROR, new String[]{taskId.toString()}, e);
		}catch (BaseAppRuntimeException e) {
			throw e;
		} catch (Exception e) {	
			e.printStackTrace();
			ExceptionManager.throwException(ProcessException.class, ErrorCodeConst.WF_TASK_PROCESS_ERROR, new String[]{taskId.toString()}, e);
		}
	}
	
	private TaskInstance findPreNearestTaskInstance(TaskInstance ti){
		TaskInstance result = null;
		if (ti != null) {
			Node node = ti.getTask().getTaskNode();
			List<Node> candidates = findPreNearestTaskNodeCandidates(node,false);
			if (candidates != null && !candidates.isEmpty()) {
				Collection<TaskInstance> taskList =  ti.getTaskMgmtInstance().getTaskInstances();
				List<TaskInstance> doneTaskList = new ArrayList<TaskInstance>();
				if (taskList != null && !taskList.isEmpty()) {
					for (TaskInstance task : taskList ) {
						if (task.getEnd() != null) {
							doneTaskList.add(task);
						}
					}
				}
				if (!doneTaskList.isEmpty()) {
					Collections.sort(doneTaskList, new Comparator<TaskInstance>() {
						@Override
						public int compare(TaskInstance t1, TaskInstance t2) {
							Date d1 = t1.getEnd();
							Date d2 = t2.getEnd();
							return d2.compareTo(d1);
						}
					});
					
					for (TaskInstance task : doneTaskList) {
						for (Node n : candidates) {
							if (EqualsUtil.equals(task.getTask().getTaskNode(), n)) {
								result = task;
								break;
							}
						}
					}
					
				}
				
			}
		}
		return result;
	}
	
	private static final String TASK_NODE_STRING_PRE = "TaskNode";
	private static final String DECISION_STRING_PRE = "Decision";
	
	private List<Node> findPreNearestTaskNodeCandidates(Node node,boolean includeSelf){
		List<Node> result = new ArrayList<Node>();
		if (node != null) {
			String nodeStr = node.toString();
			if (nodeStr.startsWith(TASK_NODE_STRING_PRE) || nodeStr.startsWith(DECISION_STRING_PRE)) {
				if (includeSelf && nodeStr.startsWith(TASK_NODE_STRING_PRE) ) {
					result.add(node);
				} else {
					Set<Transition> tranSet = node.getArrivingTransitions();
					if (tranSet != null && !tranSet.isEmpty()) {
						for (Transition tran : tranSet) {
							Node preNode = tran.getFrom();
							result.addAll(findPreNearestTaskNodeCandidates(preNode,true));
						}
					}
				}
				
			}
		}
		return result;
	}
	
	private List<TaskInstance> findLatestTasks(TaskInstance ti){
		List<TaskInstance> result = new ArrayList<TaskInstance>();
		if (ti != null) {
			Node node = ti.getTask().getTaskNode();
			List<Node> candidates = findLatestTaskNodeCandidates(node,false);
			if (candidates != null && !candidates.isEmpty()) {
				Collection<TaskInstance> taskList = ti.getTaskMgmtInstance().getTaskInstances();
				List<TaskInstance> todoTaskList = new ArrayList<TaskInstance>();
				if (taskList != null) {
					for (TaskInstance task : taskList) {
						if (task.getEnd() == null) {
							todoTaskList.add(task);
						}
					}
				}
				if (!todoTaskList.isEmpty()) {
					boolean findNot = false;
					for (TaskInstance task : todoTaskList) {
						boolean hasMatch = false;
						for (Node candidate : candidates) {
							if (EqualsUtil.equals(task.getTask().getTaskNode(), candidate)){
								hasMatch = true;
								break;
							}
						}
						if (!hasMatch) {
							findNot = true;
							break;
						}
					}
					if (!findNot) {
						result.addAll(todoTaskList);
					}
				}
			}
		}
		return result;
	}
	
	private List<Node> findLatestTaskNodeCandidates(Node node,boolean includeSelf){
		List<Node> result = new ArrayList<Node>();
		if (node != null) {
			String nodeStr = node.toString();
			if (nodeStr.startsWith(TASK_NODE_STRING_PRE) || nodeStr.startsWith(DECISION_STRING_PRE)) {
				if (includeSelf && nodeStr.startsWith(TASK_NODE_STRING_PRE) ) {
					result.add(node);
				} else {
					List<Transition> tranSet = node.getLeavingTransitionsList();
					if (tranSet != null && !tranSet.isEmpty()) {
						for (Transition tran : tranSet) {
							Node nextNode = tran.getTo();
							result.addAll(findLatestTaskNodeCandidates(nextNode,true));
						}
					}
				}
				
			}
		}
		return result;
	}
	
	public TaskInstance getTaskInstance(final Long instanceId,final String taskName)throws ProcessException{
		TaskInstance taskInstance = null;
		try {
			taskInstance = (TaskInstance) this.getTemplate().execute(
					
					new JbpmCallback() {

						public Object doInJbpm(JbpmContext context)
								throws JbpmException {
							
							ProcessInstance processInstance = context.getProcessInstance(instanceId);
							
							Collection<TaskInstance> tasks = processInstance.getTaskMgmtInstance().getTaskInstances();
							
							TaskInstance ti = null;
							
							if (tasks != null && tasks.size() > 0) {
								for (TaskInstance taskInstance : tasks) {
									if (StringUtils.equals(taskName, taskInstance.getName()) 
											&& taskInstance.getEnd() == null) {
										ti = taskInstance;
										break;
									}
								}
							}
							return ti;
						}

			});
		}catch (DelegationException e) {//JBPM会将ACTION中的异常转化成DelegationException			
			ExceptionManager.throwException(ProcessException.class, ErrorCodeConst.WF_TASK_PROCESS_ERROR, new String[]{"instanceId="+ String.valueOf(instanceId) + "taskName=" + String.valueOf(taskName)
				}, e);
		}catch (BaseAppRuntimeException e) {
			throw e;
		} catch (Exception e) {	
			e.printStackTrace();
			ExceptionManager.throwException(ProcessException.class, ErrorCodeConst.WF_TASK_PROCESS_ERROR, new String[]{"instanceId="+ String.valueOf(instanceId) + "taskName=" + String.valueOf(taskName)}, e);
		}
		return taskInstance;
	}
	
	
	/**
	 * 重新打开流程 
	 *
	 * @param prodNo
	 * @param nodeName
	 */
	public void reOpenProcessInstance(final Long processInstanceId, final String nodeName){
		try {
			this.getTemplate().execute(
					
					new JbpmCallback() {

						public Object doInJbpm(JbpmContext context)
								throws JbpmException {
							ProcessInstance pi = context.loadProcessInstance(processInstanceId);
							Node node = pi.getProcessDefinition().getNode(nodeName);
							if (node == null){
								//TODO 不存在此节点
							}
							pi.getRootToken().setNode(node);
							pi.setEnd(null);
							//pi.suspend();
							
							if (node instanceof TaskNode){//如果返回到任务节点，则生成节点下任务。
								TaskNode taskNode = (TaskNode)node;
								Iterator taskIt = taskNode.getTasks().iterator();
								while(taskIt.hasNext()){
									Task task = (Task)taskIt.next();									
									pi.getTaskMgmtInstance().createTaskInstance(task, pi.getRootToken());
								}
									
							}		
							context.save(pi);
							context.getSession().flush();
							//更新jbpm_token
							String sql = "update jbpm_token set end_ = null, isabletoreactivateparent_ = 1 where id_ = "+pi.getRootToken().getId();
							context.getSession().createSQLQuery(sql).executeUpdate();
							//更新JBPM_PROCESSINSTANCE
							sql = "update JBPM_PROCESSINSTANCE set end_=null where id_ = "+pi.getId();
							context.getSession().createSQLQuery(sql).executeUpdate();						
							//
							//
							
							return null;
						}

			});
		}catch (DelegationException e) {//JBPM会将ACTION中的异常转化成DelegationException			
			//ExceptionManager.throwException(ProcessException.class, ErrorCodeConst.WF_TASK_PROCESS_ERROR, new String[]{taskId.toString()}, e);
		}catch (BaseAppRuntimeException e) {
			throw e;
		} catch (Exception e) {	
			//ExceptionManager.throwException(ProcessException.class, ErrorCodeConst.WF_TASK_PROCESS_ERROR, new String[]{taskId.toString()}, e);
		}
					
	}
	
	public Map<String,String> getLeaveTransitionMapByTask(final Long taskId){
		final Map<String,String> ret = new HashMap<String,String>();
		try {
			this.getTemplate().execute(
					
					new JbpmCallback() {

						public Object doInJbpm(JbpmContext context)
								throws JbpmException {
							
							if (taskId != null) {
								TaskInstance taskInstance = context.getTaskInstance(taskId);
								if (taskInstance != null) {
									Node node = taskInstance.getToken().getNode();
									List<Transition> transitions = node.getLeavingTransitions();
									if (transitions != null && transitions.size() > 0) {
										for (Transition t : transitions) {
											ret.put(t.getName()==null ?  "" : t.getName(), t.getTo().getName());
										}
									}
									if (findPreNearestTaskInstance(taskInstance) != null) {
										ret.put(ProcessBehavior.BACK.getName(), ProcessBehavior.BACK.getName());
									}
								}
							}
							
							return null;
						}

			});
		}catch (DelegationException e) {//JBPM会将ACTION中的异常转化成DelegationException			
			ExceptionManager.throwException(ProcessException.class, ErrorCodeConst.WF_TASK_PROCESS_ERROR, new String[]{taskId.toString()}, e);
		}catch (BaseAppRuntimeException e) {
			throw e;
		} catch (Exception e) {	
			ExceptionManager.throwException(ProcessException.class, ErrorCodeConst.WF_TASK_PROCESS_ERROR, new String[]{taskId.toString()}, e);
		}
		return ret;
	}
	

}
