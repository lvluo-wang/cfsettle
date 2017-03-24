package com.upg.ucars.framework.bpm.base;

import org.apache.commons.beanutils.MethodUtils;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ProcessException;
import com.upg.ucars.framework.exception.core.BaseAppRuntimeException;
import com.upg.ucars.util.LogUtil;
import com.upg.ucars.util.SourceTemplate;
/**
 * 按名称进行匹配的动作处理 
 * <br>action的name属性，  约定为：中文描述(SpringBean名.方法名) * 
 * <br>例如: 修改状态(serviceName.methodName)
 * <br>对应方法参数为ExecutionContext context
 * 
 *
 * @author shentuwy
 */
public class PatternNameActionHandler implements ActionHandler {

	private static final long serialVersionUID = 1L;

	public void execute(ExecutionContext context) throws Exception {
		String name = context.getAction().getName();
		
		String serviceName = null;
		String methodName = null;
		//解析名称
		try {
			String ptName = name.substring(name.indexOf("(")+1, name.indexOf(")"));
			String[] pts = ptName.split("\\.");
			serviceName = pts[0];
			methodName = pts[1];	
			
			if (serviceName==null || methodName==null){
				throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_ACTION_NAME_INVALID, new String[]{name});
			}
			
		} catch (Exception e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_ACTION_NAME_INVALID, new String[]{name});
		}
		
		//服务调用		
		try {
			LogUtil.getWorkFlowLog().info("before execute action :"+name);
			Object ps = SourceTemplate.getBean(serviceName);
			MethodUtils.invokeMethod(ps, methodName,
					new Object[] { context });
			LogUtil.getWorkFlowLog().info("end execute action :"+name);

		} catch (BaseAppRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw ExceptionManager.getException(ProcessException.class, ErrorCodeConst.WF_ACTION_INVOKE_FAILURE,
					new String[] { serviceName, methodName }, e);
		}

	}

}
