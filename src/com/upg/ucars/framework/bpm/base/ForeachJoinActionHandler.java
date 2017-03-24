package com.upg.ucars.framework.bpm.base;

import java.util.Iterator;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

/**
 * 
 * 
 * @author shentuwy
 * @date 2012-7-13
 *
 */
public class ForeachJoinActionHandler implements ActionHandler {
	
	
	public void execute(ExecutionContext context) throws Exception {
		Token token = context.getToken();		
		Node node = context.getNode();
		Token parentToken = token.getParent();
		
		token.end();//结束子令牌
		Iterator<Token> iter = parentToken.getChildren().values().iterator();
		boolean allEnd = true;
		while ( iter.hasNext() ) {
            Token concurrentToken = iter.next();
            if (!node.equals(concurrentToken.getNode())) {
            	allEnd = false;
            	break;
            }
        }
		
		if (allEnd){//所有子令牌的结束,你令牌进行流转
			ExecutionContext parentContext = new ExecutionContext(parentToken);
			context.getNode().leave(parentContext);			
		}		
		
	}

}
