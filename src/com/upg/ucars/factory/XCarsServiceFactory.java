package com.upg.ucars.factory;

import com.upg.ucars.basesystem.acctrecord.runtime.IAcctRecordService;
import com.upg.ucars.basesystem.audit.core.IAuditCallBack;
import com.upg.ucars.basesystem.audit.core.IAuditService;
import com.upg.ucars.basesystem.autocode.core.codeconfig.IAutoCodeConfigService;
import com.upg.ucars.basesystem.busidate.core.IBusiDateService;
import com.upg.ucars.basesystem.dictionary.core.code.ICodeClientService;
import com.upg.ucars.basesystem.dictionary.core.code.ICodeService;
import com.upg.ucars.basesystem.dictionary.core.code.IRegionService;
import com.upg.ucars.basesystem.product.core.busidef.IBusiTemplateDefService;
import com.upg.ucars.basesystem.product.core.product.IProductService;
import com.upg.ucars.basesystem.security.core.branch.IBranchService;
import com.upg.ucars.basesystem.security.core.role.IRoleService;
import com.upg.ucars.basesystem.security.core.sysConfig.ISysConfigService;
import com.upg.ucars.basesystem.security.core.sysparam.ISysParamService;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.framework.bpm.assign.service.IHumanTaskAssignService;
import com.upg.ucars.framework.bpm.assist.service.IBpmActivityManager;
import com.upg.ucars.framework.bpm.assist.service.IBpmDataClearService;
import com.upg.ucars.framework.bpm.procmap.IProdProcMapService;
import com.upg.ucars.framework.bpm.publish.service.IHumnTaskService;
import com.upg.ucars.framework.bpm.publish.service.IProcessDefService;
import com.upg.ucars.framework.bpm.tasktrace.service.ITaskTraceInfoService;
import com.upg.ucars.framework.security.ISecurityManager;
import com.upg.ucars.tools.imp.excel.IExcelImportCallBack;
import com.upg.ucars.util.SourceTemplate;

public class XCarsServiceFactory {

	/**
	 * 获取字典域服务实例
	 *
	 * @return
	 */
	public static ICodeService getCodeService(){
		return SourceTemplate.getBean(ICodeService.class,BeanNameConstants.CODE_SERVICE);
	}
	
	/**
	 * 获取字典信息查询服务实例
	 *
	 * @return
	 */
	public static ICodeClientService getCodeClientService(){
		return SourceTemplate.getBean(ICodeClientService.class,BeanNameConstants.CODE_CLIENT_SERVICE);
	}
	
	
	/**
	 * 地区服务
	 *
	 * @return
	 */
	public static IRegionService getRegionService(){
		return SourceTemplate.getBean(IRegionService.class,BeanNameConstants.REGION_SERVICE);
	}
	
	/**
	 * 
	 * 机构服务
	 *
	 * @return
	 */
	public static IBranchService getBranchService(){
		return SourceTemplate.getBean(IBranchService.class,BeanNameConstants.BRANCH_SERVICE);
	}
	
	
	/**
	 * 
	 * 任务分配服务
	 *
	 * @return
	 */
	public static IHumanTaskAssignService getHumanTaskAssignService(){
		return SourceTemplate.getBean(IHumanTaskAssignService.class,BeanNameConstants.HUMN_TASK_ASSIGN_SERVICE);
	} 
	
	/**
	 * 
	 * 角色服务
	 *
	 * @return
	 */
	public static IRoleService  getRoleService(){
		return SourceTemplate.getBean(IRoleService.class,BeanNameConstants.ROLE_SERVICE);
	}
	
	/**
	 * 
	 * 用户服务
	 *
	 * @return
	 */
	public static IUserService  getUserService(){
		return SourceTemplate.getBean(IUserService.class,BeanNameConstants.USER_SERVICE);
	}
	
	/**
	 * 
	 * 系统配置服务
	 *
	 * @return
	 */
	public static ISysConfigService  getSysConfigService(){
		return SourceTemplate.getBean(ISysConfigService.class,BeanNameConstants.SYS_CONFIG_SERVICE);
	}
	
	
	/**
	 * 
	 * 系统配置
	 *
	 * @return
	 */
	public static ISysParamService getSysParamService(){
		return SourceTemplate.getBean(ISysParamService.class,BeanNameConstants.SYS_PARAM_SERVICE);
	}
	
	/**
	 * 
	 * 安全管理
	 *
	 * @return
	 */
	public static ISecurityManager getSecurityManager(){
		return SourceTemplate.getBean(ISecurityManager.class,BeanNameConstants.SECURITY_MANAGER_BEAN);
	}
	

	/**
	 * 
	 * 自动代码配置
	 *
	 * @return
	 */
	public static IAutoCodeConfigService getAutoCodeConfigService()
	{
		return SourceTemplate.getBean(IAutoCodeConfigService.class,BeanNameConstants.AUTO_CODE_CONFIG_SERVICE);
	
	}
	
	/**
	 * 
	 * 业务模板定义
	 *
	 * @return
	 */
	public static IBusiTemplateDefService getBusiTemplateDefService()
	{
		return SourceTemplate.getBean(IBusiTemplateDefService.class,BeanNameConstants.BUSI_TEMPLATE_DEF_SERVICE);
	
	}
	
	/**
	 * 
	 * 产品服务
	 *
	 * @return
	 */
	public static IProductService getProductService(){
		return SourceTemplate.getBean(IProductService.class,BeanNameConstants.PRODUCT_SERVICE);
	}
	
	
   //------------- 以下是工作流相关服务---
	/**
	 * 流程清理服务
	 * @return
	 */
	public static IBpmDataClearService getBpmDataClearService(){
		return SourceTemplate.getBean(IBpmDataClearService.class,BeanNameConstants.BPM_DATA_CLEAR_SERVICE);
	}
	/**
	 * 获取流程活动管理
	 * @return
	 */
	public static IBpmActivityManager getBpmActivityManager(){
		return SourceTemplate.getBean(IBpmActivityManager.class,BeanNameConstants.BPM_ACTIVITY_MANAGER);
	} 

	
	/**
	 * 流程定义服务
	 * @return
	 */
	public static IProcessDefService getProcessDefService(){
		return  SourceTemplate.getBean(IProcessDefService.class,BeanNameConstants.PROCESS_SERVICE);
	}
	/**
	 * 人工任务服务
	 * @return
	 */
	public static IHumnTaskService getHumnTaskService(){
		return SourceTemplate.getBean(IHumnTaskService.class,BeanNameConstants.HUMN_TASK_SERVICE);
	} 
	
	
	/**
	 * 获取关联服务 
	 *
	 * @return
	 */
	public static IProdProcMapService getProdProcMapService(){
		return SourceTemplate.getBean(IProdProcMapService.class,BeanNameConstants.PROD_PROC_MAP_SERVICE);
	}
	
	
	/**
	 * 任务信息追踪服务
	 * @return
	 */
	public static ITaskTraceInfoService getTaskTraceInfoService(){
		return SourceTemplate.getBean(ITaskTraceInfoService.class,BeanNameConstants.TASK_TRACE_INFO_SERVICE);
	}
	
	/**
	 * 审批服务
	 */
	public static IAuditService getAuditService(){
		return SourceTemplate.getBean(IAuditService.class,BeanNameConstants.AUDIT_SERVICE);
	}
	
	/**
	 * 审批回调服务
	 * @param serviceBeanName 实现了审批回调服务接口的bean
	 * @return
	 */
	public static IAuditCallBack getAuditCallBackService(String serviceBeanName){
		return (IAuditCallBack)SourceTemplate.getBean(serviceBeanName);
	}
	/**
	 * Excel导入回调服务
	 * @param serviceBeanName
	 * @return
	 */
	public static IExcelImportCallBack getExcelCallBackService(String serviceBeanName){
		return (IExcelImportCallBack)SourceTemplate.getBean(serviceBeanName);
	}
	
	/**
	 * 
	 * 账号记录服务
	 *
	 * @return
	 */
	public static IAcctRecordService getAcctRecordService(){
		return SourceTemplate.getBean(IAcctRecordService.class,BeanNameConstants.ACCT_RECORD_SERVICE);
	}
	
	/**
	 * 
	 * 营业日服务
	 *
	 * @return
	 */
	public static IBusiDateService getBusiDateService(){
		return SourceTemplate.getBean(IBusiDateService.class,BeanNameConstants.BUSI_DATE_SERVICE);
	}
	
}
