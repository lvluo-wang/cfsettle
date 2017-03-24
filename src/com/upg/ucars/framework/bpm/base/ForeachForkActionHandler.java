package com.upg.ucars.framework.bpm.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.EntityMode;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;


/**
 * 
 * 
 * @author shentuwy
 * @date 2012-7-13
 *
 */
public abstract class ForeachForkActionHandler implements ActionHandler {
	
	
	public void execute(ExecutionContext context) throws Exception {
		Token token = context.getToken();
		List childEntityList = getChildTokenEntitys(context);
		for (Object childEntity : childEntityList){
			Long childEntityId = getIdValue(context.getJbpmContext().getSessionFactory(), childEntity);
			Token childToken = new Token(token, childEntityId.toString());
			childToken.setProcNameKey(context.getProcessInstance().getProcNameKey());
			childToken.setEntityId(childEntityId);			
			
			ExecutionContext subExecutionContext = new ExecutionContext(childToken);
			context.getNode().leave(subExecutionContext);
		}
		
	}
	
	/**
	 * 获取主键值
	 * @param sessionFactory
	 * @param entity
	 * @return
	 */
	private Long getIdValue(SessionFactory sessionFactory, Object entity){
		ClassMetadata classMetadata = sessionFactory.getClassMetadata(entity.getClass());
	    Serializable s = classMetadata.getIdentifier(entity, EntityMode.POJO);
	    return Long.parseLong(s.toString());      
	}
	/**
	 * 获取产生子令牌的实体集合
	 * @param context
	 * @return
	 */
	public abstract List<?> getChildTokenEntitys(ExecutionContext context);

}
