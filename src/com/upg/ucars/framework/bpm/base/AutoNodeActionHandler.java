package com.upg.ucars.framework.bpm.base;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ExecutionContext;
/**
 * 自动节点的处理基类
 * 
 * @author shentuwy
 */
public abstract class AutoNodeActionHandler implements ActionHandler {

	private static final long serialVersionUID = -2609493174743526798L;
	
	public void execute(ExecutionContext context) throws Exception {
		String lineName = this.executeAction(context);
		if (lineName==null){
			if (context.getNode().getLeavingTransitions().size()>0){
				Transition transition = (Transition)context.getToken().getAvailableTransitions().iterator().next();
				context.leaveNode(transition);
			}else
				context.leaveNode();
		}	
		else
			context.leaveNode(lineName);
		
	}
	/**
	 * 执行动作
	 *
	 * @param context
	 * @return 连线的名称,只有一根连线返回null即可
	 * @throws Exception
	 */
	public abstract String executeAction(ExecutionContext context) throws Exception;

}
