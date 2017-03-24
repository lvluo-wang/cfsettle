package com.upg.ucars.framework.bpm.publish.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.jbpm.graph.def.ProcessDefinition;

import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.mapping.framework.bpm.ProcessDef;
import com.upg.ucars.model.BooleanResult;

public interface IProcessDefService {

	/**
	 * 保存流程定义
	 * 
	 * @param process
	 */
	public void save(ProcessDef process);

	/**
	 * 根据流程ID获取流程定义
	 * 
	 * @param id
	 * @return
	 */
	public ProcessDef getProcessDefById(Long id);

	/**
	 * 更新流程定义
	 * 
	 * @param process
	 */
	public void update(ProcessDef process);

	/**
	 * 通过流程名获取流程
	 * 
	 * @param name
	 * @return
	 */
	public ProcessDef getProcessDefByName(String name);

	/**
	 * 根据流程文件发布流程
	 * 
	 * @param fileType
	 *            文件类别 -- 支持xml文件及zip文件
	 * @param file
	 *            文件entity
	 * @return
	 */
	public boolean publishService(String fileType, File file, List<String> prodNoList);

	/**
	 * 根据流程定义文件及产品号、任务类别发布流程
	 * 
	 * @param pDef
	 *            流程定义文件
	 * @param prodNo
	 *            产品号
	 * @param taskType
	 *            任务类别
	 */
	public void publishService(ProcessDefinition pDef, String prodNo,
			Map<String, String> taskType);

	/**
	 * 根据流程定义文件发布流程
	 * 
	 * @param processDefinition
	 *            流程定义文件
	 * @return
	 */
	public boolean publishService(ProcessDefinition processDefinition);

	/**
	 * 取出所有流程
	 * 
	 * @return
	 */
	public List<ProcessDef> getAllProcessDef();

	/**
	 * 检查流程是否允许删除
	 * 
	 * @param name
	 *            流程名
	 * @return true 可以删除，否则不可删除。
	 */
	public BooleanResult checkDeleteProcess(String name);

	/**
	 * 删除流程及其相关数据信息
	 * 
	 * @param name
	 * @return
	 */
	public void deleteProcessDef(String name);

	public List<ProcessDef> queryProcessDef(QueryComponent qcpt, Page page);

	/**
	 * 获取产品默认定义的流程
	 * 
	 * @param prodNo
	 *            产品编号
	 * @return
	 */
	public ProcessDef getDefaultProcessByProdId(Long prodId);

}
