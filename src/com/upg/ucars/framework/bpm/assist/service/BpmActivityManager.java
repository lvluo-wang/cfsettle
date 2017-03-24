package com.upg.ucars.framework.bpm.assist.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.taskmgmt.exe.PooledActor;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.util.Clock;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springmodules.workflow.jbpm31.JbpmCallback;

import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.bpm.assist.dao.IBpmActivityDao;
import com.upg.ucars.framework.bpm.assist.model.BpmSearchBean;
import com.upg.ucars.framework.bpm.assist.model.CurrentNodeInfo;
import com.upg.ucars.framework.bpm.assist.model.NodeLineDTO;
import com.upg.ucars.framework.bpm.assist.model.ProcDefDTO;
import com.upg.ucars.framework.bpm.assist.model.ProcessInstanceDTO;
import com.upg.ucars.framework.bpm.assist.model.TaskInstanceDTO;
import com.upg.ucars.framework.bpm.assist.model.VariableInstanceDTO;
import com.upg.ucars.framework.bpm.base.BpmControlDAO;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.util.SourceTemplate;


/** 
 * 流程活动管理者
 *
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */

public class BpmActivityManager implements IBpmActivityManager {
	private IBpmActivityDao bpmActivityDao;	
	private BpmControlDAO bpmControlDAO;
	
	public IBpmActivityDao getBpmActivityDao() {
		return bpmActivityDao;
	}
	public void setBpmActivityDao(IBpmActivityDao bpmActivityDao) {
		this.bpmActivityDao = bpmActivityDao;
	}
		
	public BpmControlDAO getBpmControlDAO() {
		return bpmControlDAO;
	}
	public void setBpmControlDAO(BpmControlDAO bpmControlDAO) {
		this.bpmControlDAO = bpmControlDAO;
	}
	public List<ProcessInstanceDTO> queryProcessInstances(BpmSearchBean sb, Page page) {
		String procName = sb.getProcName();
		if (sb.getBrchId()!=null){//各接入行所对应流程名
			procName = sb.getProcName()+"_"+sb.getBrchId().substring(0,4);
		}		
		
		if (StringUtils.isEmpty(sb.getValue())){
			List<ProcessInstanceDTO> list = this.getBpmActivityDao().queryProcessInstances(procName, sb.getEntityId(), page);
			return list;
		}else if (BpmSearchBean.Type_Entity_Id.equals(sb.getType())){//按清单ID查询
			Long entityId = sb.getEntityId();
			List<ProcessInstanceDTO> list = this.getBpmActivityDao().queryProcessInstances(procName, entityId, page);
			return list;
		} else {
			String  entityNo = sb.getEntityNo();
			HibernateTemplate ht = SourceTemplate.getHibernateTemplate();			
			
			ProcDefDTO procDef = ProcDefManager.getProcDefByName(sb.getProcName());		
			
			List idList = ht.find("select "+procDef.getIdName()+" from "+procDef.getClzName()+" where "+procDef.getNoName()+"=? ", entityNo);
			if (idList.isEmpty())
				return new ArrayList(0);
			
			return this.getBpmActivityDao().queryProcessInstances(procName, idList, page);
			
		}		
		
		
		
	}
	
	public List<TaskInstanceDTO> getTaskInstances(Long processInstanceId) {
		
		List<TaskInstanceDTO> list = this.getBpmActivityDao().getTaskInstances(processInstanceId);
		return list;
		
	}
	
	public CurrentNodeInfo getCurrentNodeInfo(Long processInstanceId){
		final Long piId = processInstanceId;
		
		CurrentNodeInfo dto = (CurrentNodeInfo)this.bpmControlDAO.getTemplate().execute(					
				new JbpmCallback() {
					public Object doInJbpm(JbpmContext context)
							throws JbpmException {	
						Node node = context.loadProcessInstance(piId).getRootToken().getNode();		
						CurrentNodeInfo nodeDto = new CurrentNodeInfo(node.getId(), node.getName(), node.getDescription(), getNodeType(context.getSession(), node.getId()));			
						
						if (!node.hasNoLeavingTransitions()){
							Iterator<Transition> it = node.getLeavingTransitions().iterator();
							while(it.hasNext()){
								Transition transition = it.next();
								NodeLineDTO line = new NodeLineDTO(transition.getId(), transition.getName(), transition.getDescription(), NodeLineDTO.Type_Line);			
								
								Node lineToNode = transition.getTo();
								NodeLineDTO toNode = new NodeLineDTO(lineToNode.getId(), lineToNode.getName(), lineToNode.getDescription(), getNodeType(context.getSession(), lineToNode.getId()));
								nodeDto.addLineAndToNode(line, toNode);
							}							
						}
						
						return nodeDto;							 
					}
					
					
			});
		
		return dto;
		
	}
	/**
	 * 查询节点类型
	 * @param session
	 * @param nodeId
	 * @return NodeLineDTO.Type_*
	 */
	private String getNodeType(Session session, Long nodeId){
		String sql = "select class_ from jbpm_node where id_="+nodeId; 				
    	SQLQuery sqlQuery = session.createSQLQuery(sql);
    	String nodeType = sqlQuery.list().get(0).toString();
    	return nodeType;
	}
	
	public List<NodeLineDTO> getTransitionInfo(Long processInstanceId) {
		Long tokenId = this.getBpmControlDAO().getRootTokenIdByProcessId(processInstanceId);
		List<NodeLineDTO>  list = this.getBpmActivityDao().getTransitionInfoByToken(tokenId);
		return list;
		
	}
	
	public List<VariableInstanceDTO> getVariableInstances(Long processInstanceId) {
		final List<VariableInstanceDTO> viList = new ArrayList<VariableInstanceDTO>();
		final Long piId=processInstanceId;
		
		this.getBpmControlDAO().getTemplate().execute(
				
				new JbpmCallback() {

					public Object doInJbpm(JbpmContext context)
							throws JbpmException {
						Map map = context.loadProcessInstance(piId).getContextInstance().getVariables();
						Iterator keyIt = map.keySet().iterator();
						while (keyIt.hasNext()){
							String key = keyIt.next().toString();
							Object val = map.get(key);	
							VariableInstanceDTO dto = new VariableInstanceDTO(key);
							if (dto.setValue(val))
								viList.add(dto);
							
						}
						return null;			
					}

				});
		
		return viList;
		
	}
	
	public List<Buser> getActorsByTask(Long taskInstanceId) {
		List<Buser> list = this.getBpmActivityDao().getActorsByTask(taskInstanceId);
		return list;		
	}
	
	
	
	public void addTaskActor(Long taskInstanceId, String userNo) {
		final Long tiId = taskInstanceId;
		final String actor = userNo;		
		this.getBpmControlDAO().getTemplate().execute(
				
				new JbpmCallback() {

					public Object doInJbpm(JbpmContext context)
							throws JbpmException {
						TaskInstance ti = context.loadTaskInstance(tiId);
						Set set = ti.getPooledActors();
						PooledActor pa = new PooledActor(actor);
						set.add(pa);
						ti.setPooledActors(set);
						
						return null;			
					}

		});
		
	}
	/**
	 * (non-Javadoc)
	 * @see com.leadmind.basesystem.bpm.assist.service.IBpmActivityManager#delTaskActor(java.lang.Long, java.lang.String)
	 */
	public void delTaskActor(Long taskInstanceId, String userNo) {
		
		final Long tiId = taskInstanceId;
		final String actor = userNo;
		
		this.getBpmControlDAO().getTemplate().execute(				
				new JbpmCallback() {
					public Object doInJbpm(JbpmContext context)
							throws JbpmException {
						TaskInstance ti = context.loadTaskInstance(tiId);						
						Set set = ti.getPooledActors();
						Iterator iter = set.iterator();
						PooledActor pooledActor = null;
						while (iter.hasNext()) {
							PooledActor pa = (PooledActor) iter.next();
							if (pa.getActorId().equals(actor)) {	
								pooledActor = pa;
								break;
							}
				      	}
						set.remove(pooledActor);	
						ti.setPooledActors(set);
						
						return null;			
					}

		});
		
	}
	
	public boolean suspendProcessInstance(Long processInstanceId) {
		
		final Long piId = processInstanceId;
		this.getBpmControlDAO().getTemplate().execute(
				
				new JbpmCallback() {

					public Object doInJbpm(JbpmContext context)
							throws JbpmException {
						ProcessInstance pi = context.loadProcessInstance(piId);
						if (!pi.isSuspended()){
							pi.suspend();
							pi.setEnd(Clock.getCurrentTime());
							//终止未结束的任务
							List<TaskInstance> list = context.getTaskMgmtSession().findTaskInstancesByProcessInstance(pi);
							for(TaskInstance ti : list){
								if (ti.isOpen())
									ti.suspend();
							}
						}						
						
						
						return null;			
					}

		});
		return true;
		
	}

	public boolean resumeProcessInstance(Long processInstanceId) {
		final Long piId = processInstanceId;
		try {
			this.getBpmControlDAO().getTemplate().execute(
					
					new JbpmCallback() {

						public Object doInJbpm(JbpmContext context)
								throws JbpmException {
							ProcessInstance pi = context.loadProcessInstance(piId);
							if (pi.isSuspended()){
								pi.resume();
								pi.setEnd(null);
								//打开未结束 且被终止的任务
								List<TaskInstance> list = context.getTaskMgmtSession().findTaskInstancesByProcessInstance(pi);
								for(TaskInstance ti : list){
									if (ti.isSuspended())
										ti.resume();
								}
							}
													
							return null;			
						}

			});
		} catch (Exception e) {
			throw ExceptionManager.getException(ServiceException.class, "DAO_0002");
		}
				
		return true;
		
	}
	
	
	public boolean suspendTaskInstance(Long taskInstanceId) {
		try {
			final Long tiId = taskInstanceId;		
			this.getBpmControlDAO().getTemplate().execute(				
					new JbpmCallback() {
						public Object doInJbpm(JbpmContext context)
								throws JbpmException {
							TaskInstance ti = context.loadTaskInstance(tiId);
							if(ti.isOpen())
								ti.suspend();					
							return null;			
						}

			});
		} catch (Exception e) {
			throw ExceptionManager.getException(ServiceException.class, "DAO_0002");
		}
		
		return true;
	}
	
	public boolean resumeTaskInstance(Long taskInstanceId) {
		try {
			final Long tiId = taskInstanceId;		
			this.getBpmControlDAO().getTemplate().execute(				
					new JbpmCallback() {
						public Object doInJbpm(JbpmContext context)
								throws JbpmException {
							TaskInstance ti = context.loadTaskInstance(tiId);	
							if (ti.isSuspended()){
								ti.resume();
							}						
							return null;			
						}

			});	
			
			return true;
		} catch (Exception e) {
			throw ExceptionManager.getException(ServiceException.class, "DAO_0002");
		}
		
	}

	public boolean addOrUpdateVariable(Long processInstanceId, String name,
			Object value) {
		
		Object oldValue = this.getBpmControlDAO().getProcessVariable(processInstanceId, name);
		if (oldValue==null || value.getClass().isAssignableFrom(oldValue.getClass())){
			this.getBpmControlDAO().createVariable(processInstanceId, name, value);
		}else{
			throw ExceptionManager.getException(ServiceException.class, "DAO_0002");
			//throw new ServiceException("修改失败！与原上下文类型不符");
		}
		
		return true;
		
	}
	
	public boolean delVariable(Long processInstanceId, String name) {
		try {
			this.getBpmControlDAO().deleteProcessVariable(processInstanceId, name);
			
		} catch (Exception e) {
			throw ExceptionManager.getException(ServiceException.class, "DAO_0002");
		}
		
		return true;
		
	}
	
	public boolean signal(Long processInstanceId, String lineName) {
		try {
			if (StringUtils.isEmpty(lineName)){
				this.getBpmControlDAO().signal(processInstanceId);
			}else{
				this.getBpmControlDAO().signal(processInstanceId, lineName);
			}
			this.getBpmControlDAO().saveProcessInstance(processInstanceId);
		} catch (Exception e) {
			throw ExceptionManager.getException(ServiceException.class, "DAO_0002", e);
		}
		
		return true;
	}
	
	public void cancelHolder(Long taskId) throws ServiceException {
		HibernateTemplate ht = this.getBpmControlDAO().getTemplate().getHibernateTemplate();
		ht.bulkUpdate("update org.jbpm.taskmgmt.exe.TaskInstance set actorId = null where id=?", taskId);
		//TaskInstance ti = ht.get(TaskInstance.class, taskId);
		//ti.setActorId(null);
		//ht.update(ti);		
	}
	
	public void holdTask(Long taskId, Long userId) throws ServiceException {
		HibernateTemplate ht = this.getBpmControlDAO().getTemplate().getHibernateTemplate();
		TaskInstance ti = ht.get(TaskInstance.class, taskId);
		ti.setActorId(userId.toString());
		ht.update(ti);
		
	}
		
}

 