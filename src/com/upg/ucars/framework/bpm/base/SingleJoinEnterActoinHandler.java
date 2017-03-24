/**
 * 
 */
package com.upg.ucars.framework.bpm.base;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.node.Join;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * 单个分支进入Join结点就结束整个分支，并取消未完成的任务。 
 * <br>应用于Join结点note-enter事件
 * 
 *
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */
public class SingleJoinEnterActoinHandler implements ActionHandler {
	 
	private static final long serialVersionUID = 1L;

	public void execute(ExecutionContext executionContext) throws Exception {
		Join join = (Join) executionContext.getNode();
		
		// reactivate the parent when the first token arrives in the join
		join.setDiscriminator(true);		
		//join.setNOutOfM(1);
		//取得join子令牌，取得没有结束的子令牌。   
		Map map = executionContext.getToken().getParent().getActiveChildren();   
	    Iterator it = map.values().iterator();   
	    while(it.hasNext()) {   
		    Token token = (Token) it.next();	    
		    Collection unfinishedTasks =executionContext.getTaskMgmtInstance().getUnfinishedTasks(token);   
		    if (unfinishedTasks.size() > 0){
		    	
		    	Iterator tit =  unfinishedTasks.iterator();
				while (tit.hasNext()) {   				   
				    TaskInstance unfinishedTaskInstance = (TaskInstance)tit.next();	
				    //取消流转，不触发令牌流转
				    unfinishedTaskInstance.setSignalling(false);
				    //取消任务
				    unfinishedTaskInstance.cancel();
				}  
				//
		    }
	    	
	    }
	    

	}

}
