package com.upg.ucars.framework.bpm.publish.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.lang.StringUtils;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.taskmgmt.def.Task;
import org.jdom.Document;
import org.jdom.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.upg.ucars.basesystem.product.core.product.IProductService;
import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.ICommonDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.bpm.publish.dao.IProcessDefDao;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;
import com.upg.ucars.mapping.framework.bpm.HumnTask;
import com.upg.ucars.mapping.framework.bpm.ProcessDef;
import com.upg.ucars.model.BooleanResult;
import com.upg.ucars.util.LogUtil;
import com.upg.ucars.util.SourceTemplate;
import com.upg.ucars.util.XmlUtil;

/**
 * 流程实体服务
 * 
 * @author shentuwy
 */
public class ProcessDefServiceImpl extends BaseService implements
		IProcessDefService {

	private static Logger		log	= LoggerFactory
											.getLogger(ProcessDefServiceImpl.class);

	private IProcessDefDao		processDefDao;
	private ICommonDAO			commonDAO;
	private JbpmConfiguration	jbpmConf;
	private IProductService		productService;

	public void setJbpmConf(JbpmConfiguration jbpmConf) {
		this.jbpmConf = jbpmConf;
	}

	public void setProcessDefDao(IProcessDefDao processDefDao) {
		this.processDefDao = processDefDao;
	}

	public void setCommonDAO(ICommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public void setProductService(IProductService productService) {
		this.productService = productService;
	}

	public boolean publishService(String fileType, File file,
			List<String> prodNoList) {
		boolean b = true;
		try {
			if (fileType.equals("text/xml")
					|| fileType.equals("application/x-xml")) {
				Object[] prodNoAndTaskInfos = parseProdNoAndTask(file);
				List<String> allProdNoList = new ArrayList<String>();
				if (prodNoList != null && prodNoList.size() > 0) {
					allProdNoList.addAll(prodNoList);
				}
				List<String> processProdNoAttr = (List<String>) prodNoAndTaskInfos[0];
				for (String prodNo : processProdNoAttr) {
					if (!allProdNoList.contains(prodNo)) {
						allProdNoList.add(prodNo);
					}
				}
				Map<String, String> taskTypeMap = (Map<String, String>) prodNoAndTaskInfos[1];
				publicProcess(file, allProdNoList, taskTypeMap);
			} else if (fileType.equals("application/x-zip-compressed")
					|| fileType.equals("application/octet-stream")) {
				parseZipFile(file);
			} else {
				b = false;
			}
		} catch (Exception e) {
			throw ExceptionManager.getException(ServiceException.class,
					ErrorCodeConst.WF_COMMON_ERROR,
					new String[] { e.getMessage() }, e);
		}
		return b;

	}

	private void publicProcess(InputStream in, List<String> prodNoList,
			Map<String, String> taskTypeMap) throws Exception {
		for (String prodNo : prodNoList) {
			if (StringUtils.isNotBlank(prodNo)) {
				ProcessDefinition pd = ProcessDefinition
						.parseXmlInputStream(in);
				publishService(pd, prodNo, taskTypeMap);
			}
		}
	}

	private void publicProcess(File file, List<String> prodNoList,
			Map<String, String> taskTypeMap) throws Exception {
		for (String prodNo : prodNoList) {
			if (StringUtils.isNotBlank(prodNo)) {
				InputStream in = null;
				try {
					in = new FileInputStream(file);
					ProcessDefinition pd = ProcessDefinition
							.parseXmlInputStream(in);
					publishService(pd, prodNo, taskTypeMap);
				} finally {
					if (in != null) {
						try {
							in.close();
						} catch (Exception e) {
							log.error(e.getMessage(), e);
						}
					}
				}
			}
		}
	}

	private void resetProcessInfos(ProcessDefinition pd, String prodNo) {
		ProductInfo product = productService.getProductByProdNo(prodNo);
		String oldProcessName = pd.getName();
		String newProcessName = "";
		String[] nameArray = oldProcessName.split("_");
		for (int i = 0; i < nameArray.length; i++) {
			if (i == 0) {
				newProcessName += nameArray[i];
				newProcessName += prodNo;
			} else {
				newProcessName += "_";
				newProcessName += nameArray[i];
			}
		}
		pd.setName(newProcessName);
		pd.setDescription(product.getProdName());
	}

	private static final Object[] parseProdNoAndTask(File file)
			throws Exception {
		return parseProdNoAndTask(new FileInputStream(file));
	}

	private static final Object[] parseProdNoAndTask(InputStream in)
			throws Exception {
		Object[] ret = new Object[2];
		List<String> prodNoList = new ArrayList<String>();
		Map<String, String> taskTypeMap = new HashMap<String, String>();
		ret[0] = prodNoList;
		ret[1] = taskTypeMap;
		if (in != null) {
			try {
				Document doc = XmlUtil.parse(in);
				Element root = doc.getRootElement();

				// 解析产品代码
				String prodNos = root.getAttributeValue("prod-no");
				if (StringUtils.isNotBlank(prodNos)) {
					String[] prodNoArray = prodNos.split(",");
					for (String prodNo : prodNoArray) {
						if (StringUtils.isNotBlank(prodNo)) {
							prodNoList.add(prodNo);
						}
					}
				}

				// 解析人工任务
				List elements = root.getChildren("task-node");
				Iterator it = elements.iterator();
				while (it.hasNext()) {
					Element e = (Element) it.next();
					List taskList = e.getChildren("task");
					for (int i = 0; i < taskList.size(); i++) {
						Element taskEle = (Element) taskList.get(i);
						String taskName = taskEle.getAttributeValue("name");
						String taskType = taskEle
								.getAttributeValue("task-type");
						taskTypeMap.put(taskName, taskType);
					}

				}
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (Exception e) {
						log.error("close process file fail " + e.getMessage(),
								e);
					}
				}
			}
		}
		return ret;
	}

	private List<ProcessDefinition> parseZipFile(File zipFile) throws Exception {
		ZipFile zf = new ZipFile(zipFile);
		Enumeration<? extends ZipEntry> en = zf.entries();
		ArrayList<ProcessDefinition> list = new ArrayList<ProcessDefinition>();
		while (en.hasMoreElements()) {
			ZipEntry entry = en.nextElement();
			String entryName = entry.getName();
			if (entryName.endsWith("processdefinition.xml")) {
				Object[] prodNoAndTaskInfos = parseProdNoAndTask(zf
						.getInputStream(entry));
				publicProcess(zf.getInputStream(entry),
						(List<String>) prodNoAndTaskInfos[0],
						(Map<String, String>) prodNoAndTaskInfos[1]);
			}

		}
		return list;
	}

	// 发布
	public void publishService(ProcessDefinition pDef, String prodNo,
			Map<String, String> taskTypeMap) {
		JbpmContext jbpmCxt = null;
		List<HumnTask> taskEntities = new ArrayList<HumnTask>();
		try {
			jbpmCxt = jbpmConf.createJbpmContext();
			// 更改流程的名称和描述
			resetProcessInfos(pDef, prodNo);
			String procNameKey = pDef.getName().split("_")[0];
			pDef.setProcNameKey(procNameKey);
			jbpmCxt.deployProcessDefinition(pDef);
			ProcessDef p = this.getProcessDefByName(pDef.getName());
			if (p == null)
				p = new ProcessDef();
			p.setProcCnName(pDef.getDescription());
			p.setProcName(pDef.getName());
			p.setProcNameKey(procNameKey);
			p.setDesiProdNo(prodNo);
			p.setCreateTime(new Date(System.currentTimeMillis()));
			p.setJbpmPdId(pDef.getId());
			processDefDao.saveOrUpdate(p);

			HashMap taskNameMap = new HashMap();
			if (p != null) {
				List<HumnTask> tasks = SourceTemplate.getBean(
						IHumnTaskService.class,
						BeanNameConstants.HUMN_TASK_SERVICE)
						.getHumnTasksByProcId(p.getId());
				for (HumnTask task : tasks) {
					taskNameMap.put(task.getTaskName(), task);
				}
			}
			List list = pDef.getNodes();
			Iterator it = list.iterator();

			while (it.hasNext()) {
				Object obj = it.next();
				if (obj instanceof TaskNode) {
					TaskNode taskNode = (TaskNode) obj;
					Set task = taskNode.getTasks();
					if (task != null) {
						Iterator iter = task.iterator();
						while (iter.hasNext()) {
							Task t = (Task) iter.next();
							HumnTask ht = (HumnTask) taskNameMap.get(t
									.getName());
							if (ht == null)
								ht = new HumnTask();
							ht.setProcId(p.getId());
							ht.setTaskName(t.getName());
							ht.setTaskCnName(t.getDescription());
							String taskType = (String) taskTypeMap.get(t
									.getName());
							ht.setTaskType(StringUtils.isBlank(taskType) ? HumnTask.TASK_TYPE_COMMON
									: taskType);
							taskEntities.add(ht);
						}
					}
				}
			}
			SourceTemplate.getBean(IHumnTaskService.class,
					BeanNameConstants.HUMN_TASK_SERVICE).saveHunmTasks(
					taskEntities);
		} catch (Exception e) {
			throw ExceptionManager.getException(ServiceException.class,
					ErrorCodeConst.WF_COMMON_ERROR,
					new String[] { e.getMessage() }, e);
		} finally {
			if (jbpmCxt != null) {
				try {
					jbpmCxt.close();
				} catch (Exception e2) {
					LogUtil.getExceptionLog().error(e2);
				}

			}
		}
	}

	public boolean publishService(ProcessDefinition processDefinition)
			throws ServiceException {
		JbpmContext jbpmContext = ((JbpmConfiguration) SourceTemplate
				.getBean("jbpmConfiguration")).createJbpmContext();

		try {
			jbpmContext.deployProcessDefinition(processDefinition);

			// 根据流程名找到流程，如果流程存在则更新相关信息，不存在则新增。
			ProcessDef p = this
					.getProcessDefByName(processDefinition.getName());
			if (p == null) {
				ProcessDef process = new ProcessDef();
				process.setProcName(processDefinition.getName());
				process.setProcCnName(processDefinition.getDescription());
				this.save(process);
				p = process;
			} else {
				p.setProcCnName(processDefinition.getDescription());
				p.setProcName(processDefinition.getName());
				update(p);
			}

			List tasks = SourceTemplate.getBean(IHumnTaskService.class,
					BeanNameConstants.HUMN_TASK_SERVICE).getHumnTasksByProcId(
					p.getId());
			HashMap taskNameMap = new HashMap();

			for (int m = 0; m < tasks.size(); m++) {
				HumnTask task = (HumnTask) tasks.get(m);
				taskNameMap.put(task.getTaskName(), task);
			}

			List humnTasks = new ArrayList();
			List list = processDefinition.getNodes();
			for (int i = 0; i < list.size(); i++) {
				Object obj = list.get(i);
				if (obj instanceof TaskNode) {
					TaskNode taskNode = (TaskNode) obj;
					Set task = taskNode.getTasks();
					if (task != null) {
						for (Iterator iter = task.iterator(); iter.hasNext();) {
							Task t = (Task) iter.next();
							HumnTask humnTask = new HumnTask();

							humnTask.setProcId(p.getId());
							humnTask.setTaskName(t.getName());
							humnTask.setTaskCnName(t.getDescription());
							humnTask.setTaskType(HumnTask.TASK_TYPE_COMMON);
							if (taskNameMap.containsKey(humnTask.getTaskName())) {
								HumnTask ht = (HumnTask) taskNameMap
										.get(humnTask.getTaskName());
								ht.setProcId(humnTask.getProcId());
								ht.setTaskCnName(humnTask.getTaskCnName());
								ht.setTaskType(humnTask.getTaskType());
								humnTask = ht;
							} else {
								taskNameMap.put(humnTask.getTaskName(),
										humnTask);
							}
							humnTasks.add(humnTask);
						}
					}
				}
			}
			// 批量增加或更新humnTasks，如果任务存在，则更新为最新的
			SourceTemplate.getBean(IHumnTaskService.class,
					BeanNameConstants.HUMN_TASK_SERVICE).saveHunmTasks(
					humnTasks);
		} catch (Exception e) {
			// TODO
			// throw new ServiceException(e);
		} finally {
			if (jbpmContext != null) {
				jbpmContext.close();
			}
		}
		return true;
	}

	public void save(ProcessDef process) throws ServiceException {
		process.setCreateTime(new Date(System.currentTimeMillis()));
		this.processDefDao.save(process);

	}

	public ProcessDef getProcessDefById(Long id) {
		ProcessDef p = (ProcessDef) this.processDefDao.get(id);
		return p;
	}

	public BooleanResult checkDeleteProcess(String name) throws DAOException {
		JbpmContext jbpmContext = null;
		try {
			jbpmContext = ((JbpmConfiguration) SourceTemplate
					.getBean("jbpmConfiguration")).createJbpmContext();
			List<ProcessDefinition> definitions = jbpmContext.getGraphSession()
					.findAllProcessDefinitionVersions(name);
			for (ProcessDefinition definition : definitions) {
				List instances = jbpmContext.getGraphSession()
						.findProcessInstances(definition.getId());
				for (int k = 0; k < instances.size(); k++) {
					ProcessInstance instance = (ProcessInstance) instances
							.get(k);
					if (instance.getEnd() == null) {
						return new BooleanResult(false,
								"存在正在运行的流程");
					}
				}
			}

		} finally {
			if (jbpmContext != null)
				jbpmContext.close();
		}

		return new BooleanResult(true);
	}

	public void deleteProcessDef(String name) throws ServiceException {
		BooleanResult br = this.checkDeleteProcess(name);
		if (!br.isSuccess()) {
			throw ExceptionManager.getException(ServiceException.class,
					ErrorCodeConst.WF_COMMON_ERROR, br.getInfo());
		}

		// 删除jbpm信息
		this.delJbpmProcess(name);

		ProcessDef process = this.getProcessDefByName(name);
		// 删除所属任务
		SourceTemplate.getBean(IHumnTaskService.class,
				BeanNameConstants.HUMN_TASK_SERVICE).delHumnTasksByProcId(
				process.getId());
		// 删除流程实例
		this.processDefDao.delete(process);

	}

	// 删除jbpm中的对应ProcessDefinition中的数据
	private void delJbpmProcess(String name) {

		JbpmContext jbpmContext = null;

		try {
			jbpmContext = ((JbpmConfiguration) SourceTemplate
					.getBean("jbpmConfiguration")).createJbpmContext();
			List<ProcessDefinition> definitions = jbpmContext.getGraphSession()
					.findAllProcessDefinitionVersions(name);
			for (ProcessDefinition definition : definitions) {
				jbpmContext.getGraphSession().deleteProcessDefinition(
						definition);
			}

		} finally {
			if (jbpmContext != null)
				jbpmContext.close();
		}

	}

	public void update(ProcessDef process) throws ServiceException {
		this.processDefDao.update(process);
	}

	public List<ProcessDef> getAllProcessDef() {
		return this.processDefDao.getAllProcessDef();
	}

	public ProcessDef getProcessDefByName(String name) throws ServiceException {
		ProcessDef p = processDefDao.getProcessDefByName(name);
		return p;

	}

	public List<ProcessDef> queryProcessDef(QueryComponent qcpt, Page page){
		List<ProcessDef> list = this.processDefDao.queryProcessDef(qcpt, page);
		return list;
	}

	public ProcessDef getDefaultProcessByProdId(Long prodId)
			throws DAOException, ServiceException {
		String hql = "select proc from ProcessDef proc , ProductInfo prod where proc.desiProdNo=prod.prodNo and prod.id=?";
		List<ProcessDef> list = this.commonDAO.find(hql,
				new Object[] { prodId });
		if (list.isEmpty())
			return null;

		ProcessDef process = list.get(0);
		for (ProcessDef p : list) {// 同一类的工作类名称前缀一致，以名称最短的为默认的流程
			if (process.getProcName().length() > p.getProcName().length()) {
				process = p;
			}
		}
		return process;
	}

}
