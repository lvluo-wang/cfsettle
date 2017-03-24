package com.upg.ucars.framework.bpm.assist.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ProcessInstance;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.bpm.assist.model.NodeLineDTO;
import com.upg.ucars.framework.bpm.assist.model.ProcessInstanceDTO;
import com.upg.ucars.framework.bpm.assist.model.TaskInstanceDTO;
import com.upg.ucars.mapping.basesystem.security.Buser;


public class BbmActivityDaoImpl extends BaseDAO<ProcessInstance, Long> implements IBpmActivityDao {	

	public List<ProcessInstanceDTO> queryProcessInstances(String procName,	Long entityId, Page page) {

		StringBuffer hqlBuffer = new StringBuffer();		
		hqlBuffer.append(" FROM org.jbpm.graph.exe.ProcessInstance pi join pi.processDefinition pd ");		
		hqlBuffer.append(" WHERE ");			
		hqlBuffer.append(" pd.procNameKey = '"+procName+"' ");		
		//hqlBuffer.append(" and pi.end = null and token.end = null and token.isAbleToReactivateParent = true");
		
		if (entityId != null)
			hqlBuffer.append(" and pi.entityId = "+entityId);
					
		String countHql = "SELECT count(pi.id) "+hqlBuffer.toString();
		String hql = "SELECT new com.upg.ucars.framework.bpm.assist.model.ProcessInstanceDTO(pi.id, pi.start, pi.end, pi.isSuspended) "
				+ hqlBuffer +" order by pi.id desc ";
		
		List<ProcessInstanceDTO> list = this.queryByParam(countHql, hql, null, page);		
		return list;

	}
	
	public List<ProcessInstanceDTO> queryProcessInstances(String procName,	List entityIds, Page page) {
		
		StringBuffer idStrBuf = new StringBuffer();
		for (int i = 0; i < entityIds.size(); i++) {
			idStrBuf.append(entityIds.get(i)+", ");
		}
		idStrBuf.append("-1");

		StringBuffer hqlBuffer = new StringBuffer();		
		hqlBuffer.append(" FROM org.jbpm.graph.exe.ProcessInstance pi join pi.processDefinition pd ");
		//hqlBuffer.append(" , ProcEntityMap pem ");//变量类			
		hqlBuffer.append(" WHERE ");			
		hqlBuffer.append(" pd.procNameKey = '"+procName+"' ");		
		//hqlBuffer.append(" and pi.end = null and token.end = null and token.isAbleToReactivateParent = true");
		//hqlBuffer.append(" and pem.procId = pi.id  ");
		hqlBuffer.append(" and pi.entityId  in ("+idStrBuf+") ");
					
		String countHql = "SELECT count(pi.id) "+hqlBuffer.toString();
		String hql = "SELECT new com.upg.ucars.framework.bpm.assist.model.ProcessInstanceDTO(pi.id, pi.start, pi.end, pi.isSuspended) "
				+ hqlBuffer +" order by pi.id desc ";
		
		List<ProcessInstanceDTO> list = this.queryByParam(countHql, hql, null, page);		
		return list;

	}


	
	public List<TaskInstanceDTO> getTaskInstances(Long processInstanceId) {
		
		StringBuffer hqlBuffer = new StringBuffer();	
		hqlBuffer.append("SELECT new com.upg.ucars.framework.bpm.assist.model.TaskInstanceDTO(ti.id, ti.name, ti.description, ti.create, ti.end, ti.isSuspended, ti.actorId) ");
		hqlBuffer.append(" FROM org.jbpm.taskmgmt.exe.TaskInstance ti ");
		hqlBuffer.append(" WHERE ti.processInstance.id ="+processInstanceId);
		hqlBuffer.append(" ORDER BY ti.id desc ");	
		
		List<TaskInstanceDTO> list = this.find(hqlBuffer.toString(), null);
		
		return list;
		
	}

	public List<NodeLineDTO> getTransitionInfoByToken(Long tokenId){
		final Long token = tokenId;
		
		List<NodeLineDTO> list = this.getHibernateTemplate().executeFind(new HibernateCallback(){

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {				
				String hql = "select log from org.jbpm.graph.log.TransitionLog log where log.token.id="+token; 				
				Query query = session.createQuery(hql);
				List<org.jbpm.graph.log.TransitionLog> list = query.list();
				int size = list.size();
				
				List<NodeLineDTO> dtoList = new ArrayList<NodeLineDTO>(size*2+1);
				
			    for (int i=0; i<size; i++) {
			    	org.jbpm.graph.log.TransitionLog log = list.get(i);	
			    	Node sourceNode = log.getSourceNode();			    	
			    	NodeLineDTO nodeDto = new NodeLineDTO(sourceNode.getId(), sourceNode.getName(), sourceNode.getDescription(), getNodeType(session, sourceNode.getId()));			    	
			    	dtoList.add(nodeDto);
			    	
			    	NodeLineDTO tranDto = new NodeLineDTO(log.getTransition().getId(), log.getTransition().getName(), log.getTransition().getDescription(), NodeLineDTO.Type_Line);
			    	dtoList.add(tranDto);
			    	
			    	if (i==size-1){
			    		Node desNode = log.getDestinationNode();
			    		NodeLineDTO nodeDto2 = new NodeLineDTO(desNode.getId(), desNode.getName(), desNode.getDescription(), getNodeType(session, desNode.getId()));
				    	dtoList.add(nodeDto2);
			    	}
				}				
				
				return dtoList;				
			}			
			
			private String getNodeType(Session session, Long nodeId){
				String sql = "select class_ from jbpm_node where id_="+nodeId; 				
		    	SQLQuery sqlQuery = session.createSQLQuery(sql);
		    	String nodeType = sqlQuery.list().get(0).toString();
		    	return nodeType;
			}
			
		});
		return list;
		
	}
	
	public List<Buser> getActorsByTask(Long taskInstanceId) {		
		StringBuffer sqlBuffer = new StringBuffer();		
		sqlBuffer.append("select user  from org.jbpm.taskmgmt.exe.PooledActor pooledActor  ");
		sqlBuffer.append(" join pooledActor.taskInstances ti ");
		sqlBuffer.append(" , Buser user ");
		sqlBuffer.append(" where ti.id=? ");
		sqlBuffer.append(" and user.userNo = pooledActor.actorId ");
		
		List<Buser> list = this.find(sqlBuffer.toString(), new Object[]{taskInstanceId});
		
		return list;
		
	}

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
