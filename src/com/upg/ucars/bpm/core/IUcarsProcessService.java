package com.upg.ucars.bpm.core;

import java.util.List;
import java.util.Map;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.taskmgmt.def.Task;

import com.upg.ucars.framework.base.IProcessBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.framework.bpm.HumnTask;
import com.upg.ucars.mapping.framework.bpm.InstanceBusiness;
import com.upg.ucars.mapping.framework.bpm.TaskTraceInfo;
import com.upg.ucars.model.TaskProcessResult;

/**
 * 流程服务
 * 
 * @author shentuwy
 * @date 2012-7-13
 * 
 */
public interface IUcarsProcessService extends IProcessBaseService {

	/** 项目编号 */
	public static final String	PROCESS_PROJECT_NO				= "projectNo";
	/** 客户名称 */
	public static final String	PROCESS_CUSTOMER_NAME			= "customerName";
	/** A角色 */
	public static final String	PROCESS_PROCESSOR_A				= "processorA";
	/** B角色 */
	public static final String	PROCESS_PROCESSOR_B				= "processorB";
	/** 引用实体ID */
	public static final String	PROCESS_ENTITY_ID				= "entityId";
	/** 融资金额 */
	public static final String	PROCESS_FINANCE_AMOUNT			= "financeAmount";
	/** 是否需要前置盖章 */
	public static final String	PROCESS_QIANZHI_GAIZHANG		= "qianzhiGaizhang";
	/** 是否第一笔放款记录 */
	public static final String	PROCESS_FIRST_PRJ_LOAN_RECORD	= "firstPrjLoanRecord";
	/** 放款开始日期 */
	public static final String	PROCESS_START_DATE				= "startDate";
	/** 放款结束日期 */
	public static final String	PROCESS_END_DATE				= "endDate";
	/** 默认的审批请求 */
	public static final String	DEFAULT_TASK_PROCESS_URL		= "/preGuarantee/task_tyspProcess.jhtml";
	
	/** 回调BeanKey */
	public static final String	CALLBACK_BEAN_NAME 				= "callback_bean_name";
	/** 回调Bean方法参数 */
	public static final String 	CALLBACK_METHOD_PARAM 			= "callback_method_param";
	
	/** 任务处理-通过 */
	public static final String 	TASK_PROCESS_RESULT_PASS		= "1";
	/** 任务处理-退回 */
	public static final String 	TASK_PROCESS_RESULT_BACK		= "2";
	/** 任务处理-驳回 */
	public static final String	TASK_PROCESS_RESULT_REJECT		= "0";
	
	/** 任务处理结果流程变量名 */
	public static final String 	TASK_PROCESS_RESULT_KEY			= "isPass";
	

	/**
	 * 启动流程
	 * 
	 * @param prodNo
	 *            产品编号
	 * @param instanceBusiness
	 *            流程实例实体
	 * @param variableMap
	 *            流程变量
	 * @return
	 */
	public InstanceBusiness startProcessInstance(String prodNo,
			InstanceBusiness instanceBusiness, Map<String, Object> variableMap);

	/**
	 * 获取任务信息列表
	 * 
	 * @param userId
	 * @param searchBean
	 * @param page
	 * @return
	 */
	public List<InstanceBusiness> getTaskList(Long userId,
			TaskSearchBean searchBean, Page page);
	
	public List<InstanceBusiness> getSimpleTaskList(Long userId,
			TaskSearchBean searchBean, Page page);

	/**
	 * 获取手工任务信息
	 * 
	 * @param processName
	 * @param taskName
	 * @return
	 */
	public HumnTask getHumnTask(String processName, String taskName);

	/**
	 * 任务处理
	 * 
	 * @param taskId
	 * @param result
	 */
	public void dealTask(Long taskId, TaskProcessResult result);

	/**
	 * 任务处理
	 * 
	 * @param taskId
	 * @param result
	 * @param map
	 */
	public void dealTask(Long taskId, TaskProcessResult result,
			Map<String, Object> map,Map<String, Object> callbackMap);

	/**
	 * 检查任务处理的权限
	 * 
	 * @param taskId
	 * @param userId
	 */
	public void checkDealTaskPerm(Long taskId, Long userId);

	/**
	 * 获取真正的相关的业务实体
	 * 
	 * @param context
	 * @return
	 */
	public Object getEntityById(ExecutionContext context);

	/**
	 * 获取流程相关的业务数据
	 * 
	 * @param context
	 * @return
	 */
	public InstanceBusiness getInstanceBusiness(ExecutionContext context);

	/**
	 * 获取流程相关的业务数据
	 * 
	 * @param businessInstanceId
	 * @return
	 */
	public InstanceBusiness getInstanceBusiness(Long businessInstanceId);

	/**
	 * 保存处理结果,不处理任务
	 * 
	 * @param taskId
	 * @param result
	 */
	public void saveProcessResult(Long taskId, TaskProcessResult result,Map<String,Object> callbackMap);

	/**
	 * 获取任务的处理结果
	 * 
	 * @param taskId
	 * @return
	 */
	public TaskProcessResult getTaskProcessResult(Long taskId);
	
	/**
	 * 获取任务的最新处理结果
	 * 
	 * @param instanceBusinessId
	 * @param taskName
	 * @return
	 */
	public TaskProcessResult getLastTaskProcessResult(Long instanceBusinessId, String taskName);

	/**
	 * 处理任务
	 * 
	 * @param instanceBusinessId
	 *            流程业务实体ID
	 * @param taskName
	 *            任务名称
	 * @param result
	 *            处理意见
	 * @param variableMap
	 * 			  流程变量
	 */
	public void dealTask(Long instanceBusinessId, String taskName,
			TaskProcessResult result,Map<String,Object> variableMap);

	/**
	 * 获取流程变量
	 * 
	 * @param processId
	 *            流程ID
	 * @param name
	 *            变更名
	 * @return
	 */
	public Object getVariable(Long processId, String name);
	
	/**
	 * 获取流程变量
	 * 
	 * @param instanceBusinessId
	 * @param name
	 * @return
	 */
	public Object getVariableByInstanceBusiness(Long instanceBusinessId, String name);

	/**
	 * 查询已办任务
	 * 
	 * @param userId
	 * @param searchBean
	 * @param page
	 * @return
	 */
	public List<InstanceBusiness> getDoneTaskList(Long userId,
			TaskSearchBean searchBean, Page page);

	/**
	 * 获取待办任务
	 * 
	 * @param processInstanceId
	 *            流程实例ID
	 * @return
	 */
	public List<Map<String, Object>> getTodoTaskList(Long processInstanceId);

	/**
	 * 是否上会产品
	 * 
	 * @param brchId
	 * @param product
	 * @return
	 */
	public boolean isAssessMeetingProduct(Long brchId, String prodNo);

	/**
	 * 仅流程所用
	 * 
	 * @param brchId
	 * @param prodNo
	 * @return
	 */
	public boolean isAssessMeetingProductUsedByProcess(Long brchId,
			String prodNo);

	/**
	 * 是否上会流程
	 * 
	 * @param processName
	 * @return
	 */
	public boolean isAssessMeetingProcess(String processName);
	
	/**
	 * 获取审批数据
	 * 
	 * @param taskId
	 * @return
	 */
	public TaskTraceInfo getTaskTraceInfoByTaskId(Long taskId);

	/**
	 * 结束流程
	 * 
	 * @param instanceBusinessId
	 * @return
	 */
	public void endProcess(Long instanceBusinessId);

	/**
	 * 流程是否可以被取消
	 * 
	 * @return
	 */
	public boolean canCancelProcess(Long instanceBusinessId);
	
	
	public List<InstanceBusiness> getInstanceBusinessList(InstanceBusinessSearchBean searchBean,Page page);
	
	public Map<String,String> getLeaveTransitionMapByTask(Long taskId);
	
	/**
	 * 实体是否有相应的活动流程
	 * 
	 * @param prodNo
	 * @param entityId
	 * @param clz
	 * @return
	 */
	public boolean hasActiveProcess(String prodNo,Long entityId, Class<?> clz);
	
	/**
	 * 流程是否在指定的节点上
	 * 
	 * @return
	 */
	public boolean isEntityInProcessNode(String prodNo,Long entityId,Class<?> clz,String taskName);
	
	/**
	 * 获取任务的处理结果
	 * 
	 * @param instanceBusinessId	
	 * @param taskName
	 * @return
	 */
	public TaskProcessResult getTaskProcessResult(Long instanceBusinessId,String taskName);
	
	
	/**
	 * 处理过的条件
	 * <br/>
	 * 需要增加-个参数：
	 * 	<pre>
	 * "actorId"：用户ID
	 * </pre>
	 * 
	 * @return
	 */
	public String getProcessedConditionHql(String entityIdIdentifier,Class<?> entityClass);
	
	/**
	 * 更改流程提交人
	 * 
	 * @param instanceBusinessId
	 * @param userId
	 */
	public void changeProcessStarter(Long instanceBusinessId,Long userId);
	
	/**
	 * 更改流程机构
	 * 
	 * @param instanceBusinessId
	 * @param brchId
	 */
	public void changeProcessBranch(Long instanceBusinessId,Long brchId);
	
	/**
	 * 获取流程发起机构
	 * 
	 * 
	 * @param instanceBusinessId
	 * @return
	 */
	public Branch getProcessBranch(Long instanceBusinessId);
	
	/**
	 * 获取未生成任务的所有任务节点
	 * 
	 * @param instanceBusinessId
	 * @return
	 */
	public List<Task> getUndoTaskNode(Long instanceBusinessId);
	
	/**
	 * 根据任务获取InstanceBusiness实例 
	 * 
	 * @param taskId
	 * @return
	 */
	public InstanceBusiness getInstanceBusinessByTaskId(Long taskId);
	
	/**
	 * 根据流程ID获取InstanceBusiness
	 * 
	 * @param instanceId
	 * @return
	 */
	public InstanceBusiness getInstanceBusinessByInstanceId(Long instanceId);
	
	/**
	 * 获取流程状态
	 * 
	 * @param instanceId
	 * @return
	 */
	public String getInstanceStatus(Long instanceId);
	
	/**
	 * 根据流程实例ID获取对应的对象
	 * 
	 * @param instanceBusinessId
	 * @return
	 */
	public Object getEntityObject(Long instanceBusinessId);
	
	/**
	 * 获取产品对应的流程名
	 * 
	 * @param brchId
	 * @param prodNo
	 * @return
	 */
	public String getProcessName(Long brchId,String prodNo);
	
	/**
	 * 流程是否在指定的节点上
	 * 
	 * @param instanceBusinessId
	 * @param nodeName
	 * @return
	 */
	public boolean isProcessInNode(Long instanceBusinessId,String nodeName);
	
	/**
	 * 流程是否有指定的未处理的任务
	 * 
	 * @param instanceBusinessId
	 * @param taskName
	 * @return
	 */
	public boolean hasTodoTaskInProcess(Long instanceBusinessId,String taskName);
}
