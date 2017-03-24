

package com.upg.ucars.basesystem.autotask.core;

import com.upg.ucars.basesystem.autotask.core.autotask.IAutoTaskService;
import com.upg.ucars.basesystem.autotask.core.autotasklog.IAutoTaskLogService;
import com.upg.ucars.util.SourceTemplate;
/**
 * 服务工厂
 *
 * @author shentuwy
 */
public class AutoTaskServiceFactory {
	/**
	 * 
	 * @return
	 */
	public static IAutoTaskCurrentService getAutoTaskCurrentService(){
		IAutoTaskCurrentService service = (IAutoTaskCurrentService)SourceTemplate.getBean("autoTaskCurrentService");
		return service;
	}
	
	public static IAutoTaskService getAutoTaskService(){
		IAutoTaskService service = (IAutoTaskService)SourceTemplate.getBean("autoTaskService");
		return service;
	}
	
	public static IAutoTaskLogService getAutoTaskLogService(){
		return (IAutoTaskLogService)SourceTemplate.getBean("autoTaskLogService");
	}
}
