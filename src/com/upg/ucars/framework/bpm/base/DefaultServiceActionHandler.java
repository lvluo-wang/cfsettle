package com.upg.ucars.framework.bpm.base;

import org.apache.commons.beanutils.MethodUtils;
import org.jbpm.graph.exe.ExecutionContext;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ProcessException;
import com.upg.ucars.framework.exception.core.BaseAppRuntimeException;
import com.upg.ucars.util.SourceTemplate;
/**
 * 流程名做为spring BEAN,
 * 自动节点名做为method ,以ExecutionContext context做为参数
 *
 * @author shentuwy
 */
public class DefaultServiceActionHandler extends AutoNodeActionHandler {
	
	private static final long serialVersionUID = 1L;
	
	
	private final static String nameFix = "ProcessService";

	@Override
	public String executeAction(ExecutionContext context) throws Exception {
		// 节点名
		String nodeName = context.getNode().getName();
		// 流程名
		String processName = context.getProcessDefinition().getName();

		Object procService = SourceTemplate.getBean(processName	+ nameFix);
		if (procService == null) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_AUTO_NODE_NO_SERVICE,
					new String[] { processName	+ nameFix });
		}

		try {
			MethodUtils.invokeMethod(procService, nodeName,
					new Object[] { context });

		} catch (BaseAppRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_AUTO_NODE_INVOKE_FAILURE,
					new String[] { processName + nameFix, nodeName }, e);
		}

		return null;
	}

}
