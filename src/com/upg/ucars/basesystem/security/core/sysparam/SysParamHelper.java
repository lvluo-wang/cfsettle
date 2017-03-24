package com.upg.ucars.basesystem.security.core.sysparam;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.util.SourceTemplate;

/**
 * 
 * SysParamHelper
 * 
 * @author shentuwy
 * 
 */

public final class SysParamHelper {

	/**
	 * 
	 * 取参数值，如果取不到则取参数的默认值
	 * 
	 * @param miNo
	 *            接入点编号
	 * @param paramKey
	 *            参数编号
	 * @return 参数值
	 */

	public static String getCheckedSysParamValue(String miNo, String paramKey) {
		String ret = null;
		if (StringUtils.isNotBlank(paramKey)) {
			ISysParamService service = SourceTemplate.getBean(ISysParamService.class,
					BeanNameConstants.SYS_PARAM_SERVICE);
			if (service != null) {
				ret = service.getSysParamValueByKey(miNo, paramKey);
			}
		}
		return ret;
	}

}
