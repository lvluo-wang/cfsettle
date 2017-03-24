package com.upg.ucars.bpm.factory;

import org.jbpm.graph.exe.ExecutionContext;

/**
 * 创建流程任务的监听器<br/>
 * 注意实现此监听器是全局的
 * 
 * 
 */
public interface CreateTaskListener {

	void execute(ExecutionContext executionContext);

}
