package com.upg.ucars.basesystem.webUtil.tag;

import java.util.List;

import javax.servlet.jsp.tagext.BodyTagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.upg.ucars.basesystem.security.core.sysfunc.ISysfuncService;
import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.util.SourceTemplate;

/**
 * 
 * PermissionTag.java
 * 
 * @author shentuwy
 * @date 2012-7-27
 * 
 */
public class PermissionTag extends BodyTagSupport {

	private static final long	serialVersionUID	= -5533628339349697914L;

	private static Logger		log					= LoggerFactory
															.getLogger(PermissionTag.class);

	/** 资源标示 */
	private String				resourceId;

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public int doStartTag() {
		boolean isPermission = hasPermission();
		int ret = SKIP_BODY;
		if (isPermission) {
			ret = EVAL_BODY_INCLUDE;
		}
		return ret;
	}

	@Override
	public int doEndTag() {
		return EVAL_PAGE;
	}

	private boolean hasPermission() {
		boolean ret = false;
		List<Long> roleIdList = SessionTool.getUserLogonInfo().getRoleList();
		ret = SourceTemplate.getBean(ISysfuncService.class,
				BeanNameConstants.SYSFUNC_SERVICE).hasResourcePermission(
				roleIdList, resourceId);
		if (log.isDebugEnabled()) {
			log.debug("permission(roleIdList=" + String.valueOf(roleIdList)
					+ ",resourceId=" + String.valueOf(resourceId) + ",perm="
					+ ret);
		}

		return ret;
	}
}
