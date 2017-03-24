package com.upg.ucars.framework.bpm.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.taskmgmt.exe.TaskInstance;

import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.IProcessBaseService;
import com.upg.ucars.framework.bpm.ext.IBpmPlantfromClientService;
import com.upg.ucars.framework.exception.ProcessException;
import com.upg.ucars.mapping.basesystem.product.MemberProductInfo;
import com.upg.ucars.util.SourceTemplate;

/**
 * 流程服务基础类
 * @author shentuwy
 */
public class ProcessBaseService extends BaseService implements IProcessBaseService {	
	private BpmControlDAO bpmControlDAO;
	private BpmQueryDAO bpmQueryDAO;
	
	/**
	 * 创建流程实例 
	 *
	 * @param processDefinitionName
	 * @param entity 流程关联的业务实体
	 * @param variableMap 变量集
	 * @return
	 * @throws ProcessException
	 */
	protected Long startProcessInstance(String processDefinitionName, Object entity, Map<String, Object> variableMap)throws ProcessException{
		Long pid = (Long)this.getBpmControlDAO().startProcessInstance(processDefinitionName, entity, variableMap);
		return pid;
	}	
	/**
	 * 根据机构和产品获取对应工作流
	 *
	 * @param brchNo 机构号
	 * @param prodNo 产品号
	 * @return
	 */
	protected String getProcessDefName(Long brchId, String prodNo){		
		IBpmPlantfromClientService service = SourceTemplate.getBean(IBpmPlantfromClientService.class,BeanNameConstants.BPM_PLANTFORM_CLIENT_SERVICE);
		String procName = service.getProcessName(brchId, prodNo);
		return procName;
	}
	
	protected String getProcessDefNameIgnoreNoProcess(Long brchId, String prodNo){		
		IBpmPlantfromClientService service = SourceTemplate.getBean(IBpmPlantfromClientService.class,BeanNameConstants.BPM_PLANTFORM_CLIENT_SERVICE);
		String procName = service.getProcessNameIgnoreNoProcess(brchId, prodNo);
		return procName;
	}
	
	/**
	 * 根据机构与产品信息创建流程实例 
	 *
	 * @param brchNo 机构编号
	 * @param prodNo 产品编号
	 * @param entity 流程关联的业务实体
	 * @param variableMap 变量集
	 * @return
	 * @throws ProcessException
	 */
	protected Long startProcessInstance(Long brchId, String prodNo,  Object entity, Map<String, Object> variableMap)throws ProcessException{
		variableMap = processStartProcessVariables(brchId, prodNo, variableMap);
		if("W0050".equals(prodNo)){
			prodNo = "W0060";
		}
		String processDefinitionName = this.getProcessDefName(brchId, prodNo);
		return startProcessInstance(processDefinitionName,entity,variableMap);
	}	
	
	private Map<String,Object> processStartProcessVariables(Long brchId,String prodNo, Map<String,Object> variableMap){
		Map<String,Object> processVariableMap = variableMap;
		if (processVariableMap == null) {
			processVariableMap = new HashMap<String,Object>(3);
		}
		processVariableMap.put(BpmConstants.VAR_BRCH_ID, brchId);
		processVariableMap.put(BpmConstants.VAR_PROD_NO, prodNo);
		String mpHql = "select mp from Branch b, MemberProductInfo mp ,ProductInfo p where b.miNo=mp.miNo and mp.prodId = p.id and b.brchId=? and p.prodNo=?";
		List<MemberProductInfo> mpList = this.getBpmQueryDAO().find(mpHql, new Object[]{brchId, prodNo});
		if (mpList.isEmpty()){
			//TODO 异常
		}else{
			processVariableMap.put(BpmConstants.VAR_MEMBER_PRODUCT, mpList.get(0));//上下文中放置接入产品对象 
		}
		return processVariableMap;
	}
	
	
	/**
	 * 批量创建流程实例 
	 *
	 * @param brchNo 机构编号
	 * @param prodNo 产品编号
	 * @param entityList 流程关联的业务实体集合
	 * @param variableMap 变量集
	 * @return
	 * @throws ProcessException
	 */
	protected List<Long> startBatchProcessInstance(Long brchId, String prodNo,  List<Object> entityList, Map<String, Object> variableMap)throws ProcessException{
		variableMap = processStartProcessVariables(brchId, prodNo, variableMap);
		String processName = this.getProcessDefName(brchId, prodNo);
		ArrayList<Long> pidList = new ArrayList<Long>(entityList.size());
		for (Object entity : entityList) {
			Long pid = startProcessInstance(processName, entity, variableMap);
			pidList.add(pid);
		}
		return pidList;
	}	
	
	/**
	 * 获取任务上下文
	 * @param taskId
	 * @param key
	 * @return
	 */
	protected Object getTaskVariable(Long taskId, String key)throws ProcessException{
		return this.getBpmControlDAO().getTaskVariable(taskId, key);		
	}
	
	/**
	 * 获取令牌上下文
	 * @param taskId
	 * @param key
	 * @return
	 */
	protected Object getTokenVariable(Long tokenId, String key)throws ProcessException{		
		return this.getBpmControlDAO().getTokenVariable(tokenId, key);	
		
	}
	/**
	 * 获取流程上下文
	 * @param taskId
	 * @param key
	 * @return
	 */
	protected Object getProcessVariable(Long processId, String key)throws ProcessException{		
		return this.getBpmControlDAO().getProcessVariable(processId, key);
	}
	/**
	 * 令牌流转  
	 * @param tokenId
	 * @throws ProcessException
	 */
	protected void signalToken(Long tokenId, Map<String, Object> variableMap)throws ProcessException{
		this.signalToken(tokenId, null, variableMap);
	}
	/**
	 * 令牌按指定连线流转
	 * @param tokenId
	 * @param transitionName 连线名
	 * @throws ProcessException
	 */
	protected void signalToken(final Long tokenId, final String transitionName, final Map<String, Object> variableMap)throws ProcessException{	
		this.getBpmControlDAO().signalToken(tokenId, transitionName, variableMap);
	}

	/**
	 * 处理任务
	 * @param taskId
	 * @param variableMap
	 * @throws ProcessException
	 */
	protected void dealTask(final Long taskId, final Map<String, Object> variableMap)throws ProcessException{
		this.dealTask(taskId, null, variableMap);
		
	}
	/**
	 * 处理任务后按指定连线流转
	 * @param taskId
	 * @param transitionName
	 * @param variableMap
	 * @throws ProcessException
	 */
	protected void dealTask(final Long taskId, final String transitionName, final Map<String, Object> variableMap)throws ProcessException{
		
		this.getBpmControlDAO().dealTask(taskId, transitionName, variableMap);
		
	}
	/**
	 * 结束流程 
	 *
	 * @param processInstanceId
	 * @throws ProcessException
	 */
	public void endProcessInstance(Long processInstanceId) throws ProcessException{
		this.getBpmControlDAO().endProcessInstance(processInstanceId);
	}
	
	
	
	/**
	 * 只结束任务不继续执行
	 * 
	 * @param taskId
	 * @param variableMap
	 * @throws ProcessException
	 */
	public void onlyEndTask(final Long taskId,final Map<String,Object> variableMap) throws ProcessException{
		this.getBpmControlDAO().onlyEndTask(taskId, variableMap);
	}
	/**
	 * 根据任务id结束流程
	 * 
	 * @param taskId
	 * @throws ProcessException
	 */
	public void endProcessInstanceByTaskId(final Long taskId)throws ProcessException{
		this.getBpmControlDAO().endProcessInstanceByTaskId(taskId);
	}
	
	public TaskInstance getTaskInstance(final Long instanceId,final String taskName) throws ProcessException{
		return this.getBpmControlDAO().getTaskInstance(instanceId, taskName);
	}
	
	/**
	 * 重新打开流程 
	 *
	 * @param prodNo
	 * @param nodeName
	 */
	public void reOpenProcessInstance(Long processInstanceId, String nodeName){
		this.getBpmControlDAO().reOpenProcessInstance(processInstanceId, nodeName);
	}
	
	public BpmControlDAO getBpmControlDAO() {
		return bpmControlDAO;
	}
	public void setBpmControlDAO(BpmControlDAO bpmControlDAO) {
		this.bpmControlDAO = bpmControlDAO;
	}
	public BpmQueryDAO getBpmQueryDAO() {
		return bpmQueryDAO;
	}
	public void setBpmQueryDAO(BpmQueryDAO bpmQueryDAO) {
		this.bpmQueryDAO = bpmQueryDAO;
	}
	
	
}
