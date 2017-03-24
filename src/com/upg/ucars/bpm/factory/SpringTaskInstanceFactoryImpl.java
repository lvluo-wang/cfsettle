package com.upg.ucars.bpm.factory;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.impl.DefaultTaskInstanceFactoryImpl;
import org.springframework.beans.factory.ListableBeanFactory;

import com.upg.ucars.util.SpringContextManager;

@SuppressWarnings("serial")
public class SpringTaskInstanceFactoryImpl extends DefaultTaskInstanceFactoryImpl {

	private void doListener(ExecutionContext executionContext) {
		try {
			ListableBeanFactory beanFactory = (ListableBeanFactory) SpringContextManager.getBeanFactory();
			String[] beanNames = beanFactory.getBeanNamesForType(CreateTaskListener.class);
			if (beanNames != null && beanNames.length > 0) {
				for (String bn : beanNames) {
					CreateTaskListener listener = (CreateTaskListener) beanFactory.getBean(bn);
					if (listener != null) {
						listener.execute(executionContext);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public TaskInstance createTaskInstance(ExecutionContext executionContext) {
		TaskInstance taskInstance = new TaskInstance();
		if (isDoListener()) {
			doListener(executionContext);
		}
		return taskInstance;

	}
	
	private boolean isDoListener(){
		return false;
	}

}
