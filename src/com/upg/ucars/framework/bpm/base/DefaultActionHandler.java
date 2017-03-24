package com.upg.ucars.framework.bpm.base;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ProcessException;
import com.upg.ucars.framework.exception.core.BaseAppRuntimeException;
import com.upg.ucars.util.SourceTemplate;

/**
 * 
 * 
 * @author shentuwy
 * @date 2012-7-12
 * 
 */
public class DefaultActionHandler implements ActionHandler {

	private static final long	serialVersionUID	= -104347081869058966L;

	private static final String	ACTION_BEAN_SUFFIX	= "ProcessService";

	private static Logger		log					= LoggerFactory.getLogger(DefaultActionHandler.class);

	public void execute(ExecutionContext context) throws Exception {
		Long entityId = context.getProcessInstance().getEntityId();
		String methodName = context.getAction().getName();

		String suffix = (String) context.getVariable(BpmConstants.VAR_DEFAULT_ACTION_SUFFIX);

		String actionBeanName = getBeanName(entityId);
		if (StringUtils.isNotBlank(suffix)) {
			suffix = suffix.trim();
			actionBeanName += suffix.substring(0, 1).toUpperCase() + suffix.substring(1);
		}
		actionBeanName += ACTION_BEAN_SUFFIX;
		log.info("{processinstance=" + context.getProcessInstance().getId() + ",methodName=" + methodName
				+ ",actionBeanName=" + actionBeanName);
		if (StringUtils.isNotBlank(methodName)) {
			Object action = SourceTemplate.getBean(actionBeanName);

			if (action != null) {
				try {
					MethodUtils.invokeMethod(action, methodName, new Object[] { context });

				} catch (BaseAppRuntimeException e) {
					throw e;
				} catch (Exception e) {
					throw ExceptionManager.getException(ProcessException.class,
							ErrorCodeConst.WF_AUTO_NODE_INVOKE_FAILURE, new String[] { actionBeanName, methodName }, e);
				}
			} else {
				log.warn("processinstance=" + context.getProcessInstance().getId() + ",not find action:"
						+ actionBeanName);
			}
		}
	}

	private static final String getBeanName(Long entityId) {
		String ret = null;
		if (entityId != null) {
			IInstanceBusinessDao dao = SourceTemplate.getBean(IInstanceBusinessDao.class, "instanceBusinessDao");
			String realEntityBeanName = dao.get(entityId).getEntityType();
			if (realEntityBeanName.contains(".")) {
				ret = realEntityBeanName.substring(realEntityBeanName.lastIndexOf(".") + 1);
			} else {
				ret = realEntityBeanName;
			}
			if (ret != null) {
				ret = ret.substring(0, 1).toLowerCase() + ret.substring(1);
			}
		}
		return ret;
	}

}
