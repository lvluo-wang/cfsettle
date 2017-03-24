package com.upg.ucars.basesystem.audit.core;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.basesystem.audit.core.auditNode.IAuditNodeDAO;
import com.upg.ucars.basesystem.audit.core.auditProcess.IAuditProcessDAO;
import com.upg.ucars.basesystem.audit.core.auditRoute.IAuditRouteDAO;
import com.upg.ucars.basesystem.audit.core.auditRoute.IAuditRouteService;
import com.upg.ucars.basesystem.audit.core.auditStation.IAuditStationDAO;
import com.upg.ucars.basesystem.audit.core.auditStation.IAuditStationUserDAO;
import com.upg.ucars.basesystem.audit.core.auditTask.IAuditTaskDAO;
import com.upg.ucars.basesystem.audit.core.productAudit.IProductAuditDAO;
import com.upg.ucars.basesystem.product.core.product.IMemberProductInfoDAO;
import com.upg.ucars.basesystem.product.core.product.IProductInfoDAO;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.ICommonDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.audit.AuditNode;
import com.upg.ucars.mapping.basesystem.audit.AuditProcess;
import com.upg.ucars.mapping.basesystem.audit.AuditRoute;
import com.upg.ucars.mapping.basesystem.audit.AuditStation;
import com.upg.ucars.mapping.basesystem.audit.AuditTask;
import com.upg.ucars.mapping.basesystem.audit.ReAuditRouteProduct;
import com.upg.ucars.mapping.basesystem.product.MemberProductInfo;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.SourceTemplate;

public class AuditServiceImp extends BaseService implements IAuditService {
	/** 审批产品不存在,产品编号为[{0}] */
	private static final String ERROR_PROD_NOT_EXIST_BY_NO="AUDIT_001";
	/** 审批产品未绑定审批路线，产品编号[(0)] */
	private static final String ERROR_PROD_NOT_BIND_ROUTE="AUDIT_002";
	/** 审批实体找不到数据映射，实体名 */
	private static final String ERROR_AUDIT_ENTITY_NOT_MAP="AUDIT_003";
	/** 审批产品找不到审批岗位，产品编号[{}] */
	private static final String ERROR_AUDIT_STATION_NOT_EXIST="AUDIT_004";
	/** 审批任务创建失败 */
	private static final String ERROR_AUDIT_TASK_CREATE="AUDIT_005";
	/** 相同产品下同一审批实体只能存在一个审批中任务 */
	private static final String ERROR_AUDIT_TASKING_EXIST="AUDIT_006";
	/** 当前用户审批岗位不满足 */
	private static final String ERROR_AUDIT_STATION_REGX="AUDIT_007";
	/** 该审批已被受理，不能再次受理 */
	private static final String ERROR_AUDIT_RE_ACCEPT="AUDIT_008";
	/** 该审批已结束或被撤销，不能受理 */
	private static final String ERROR_AUDIT_ACCEPT_REVOKE_OR_OVER="AUDIT_009";
	/** 只能撤销本人受理的审批 */
	private static final String ERROR_REVODE_ACCEPT_EXEC_PERSON_DEF="AUDIT_010";
	/** 只能撤销受理中的审批 */
	private static final String ERROR_REVODE_ACCEPT_NOT_ACCEPTING="AUDIT_011";
	/** 审批信息不完整，请录入完整的审批信息后再提交 */
	private static final String ERROR_AUDIT_RESULT_NOT_COMPLATE="AUDIT_012";
	/** 不能提交未受理或已受理的审批 */
	private static final String ERROR_AUDIT_COMMIT_MUST_ACCEPING="AUDIT_013";
	/** 审批回调服务处理异常 */
	private static final String ERROR_AUDIT_CALLBACK="AUDIT_014";
	/** 审批模式为互斥时，不能审批自己的业务 */
	private static final String ERROR_AUDIT_MODE_REGX_FAIL="AUDIT_015";
	/** 撤销非审批中的错误 */
	private static final String ERROR_REVOKE_NOT_AUDITING_AUDITTASK = "ERROR_REVOKE_NOT_AUDITING_AUDITTASK";
	/** 撤销受理中的错误 */
	private static final String ERROR_REVOKE_ACCEPTING_AUDITPROCESS = "ERROR_REVOKE_ACCEPTING_AUDITPROCESS";
	
	/** 审批进度 */
	private IAuditProcessDAO auditProcessDAO;
	/** 产品路线 */
	private IProductAuditDAO productAuditDAO;
	/** 产品 */
	private IProductInfoDAO productInfoDAO;
	/** 公用DAO */
	private ICommonDAO commonDAO;
	/** 审批任务 */
	private IAuditTaskDAO auditTaskDAO;
	/** 审批路线 */
	private IAuditRouteDAO auditRouteDAO;
	/** 审批节点 */
	private IAuditNodeDAO auditNodeDAO;
	/** 审批岗位 */
	private IAuditStationDAO auditStationDAO;
	/** 岗位用户 */
	private IAuditStationUserDAO stationUserDAO;
	
	/** 接入点产品关系 */
	private IMemberProductInfoDAO memberProductInfoDAO;
	
	/** 审批路线服务 */
	private IAuditRouteService auditRouteService;
	
	
	
	public void setAuditRouteService(IAuditRouteService auditRouteService) {
		this.auditRouteService = auditRouteService;
	}
	public void setAuditProcessDAO(IAuditProcessDAO auditProcessDAO) {
		this.auditProcessDAO = auditProcessDAO;
	}
	public void setProductAuditDAO(IProductAuditDAO productAuditDAO) {
		this.productAuditDAO = productAuditDAO;
	}

	public void setProductInfoDAO(IProductInfoDAO productInfoDAO) {
		this.productInfoDAO = productInfoDAO;
	}

	public void setCommonDAO(ICommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public void setAuditTaskDAO(IAuditTaskDAO auditTaskDAO) {
		this.auditTaskDAO = auditTaskDAO;
	}

	public void setAuditNodeDAO(IAuditNodeDAO auditNodeDAO) {
		this.auditNodeDAO = auditNodeDAO;
	}

	public void setAuditStationDAO(IAuditStationDAO auditStationDAO) {
		this.auditStationDAO = auditStationDAO;
	}

	public void setStationUserDAO(IAuditStationUserDAO stationUserDAO) {
		this.stationUserDAO = stationUserDAO;
	}

	public void setAuditRouteDAO(IAuditRouteDAO auditRouteDAO) {
		this.auditRouteDAO = auditRouteDAO;
	}
	public void setMemberProductInfoDAO(IMemberProductInfoDAO memberProductInfoDAO) {
		this.memberProductInfoDAO = memberProductInfoDAO;
	}
	
	
	public Long commitAuditTask(UserLogonInfo logonInfo, String prodNo,
			Object entity,BigDecimal auditPrivilege, String callBackServiceBean,String auditRemark) throws ServiceException {
		if(prodNo==null||prodNo.length()<1){
			//产品不存在，产品编号为
			ExceptionManager.throwException(ServiceException.class, ERROR_PROD_NOT_EXIST_BY_NO, prodNo);
		}
		ProductInfo prod=productInfoDAO.getProductInfoByProdNo(prodNo);
		//1、校验产品
		if(prod==null){
			//产品不存在，产品编号为
			ExceptionManager.throwException(ServiceException.class, ERROR_PROD_NOT_EXIST_BY_NO, prodNo);
		}
		
		Long prodId=prod.getId();
		Long brchId=logonInfo.getBranchId();
		Long userId=logonInfo.getSysUserId();
		AuditRoute route= null;
		
		//处理历史遗留　
		for(int i = 0; i < 2 ; i++){
			route= productAuditDAO.findAuditRoute(prodId, brchId);
			if( route != null ){
				break;
			}
			if( route == null && i == 0 ){
				copyDefaultProductInfoAuditRoute(brchId);
			}
		}
		//2、校验审批路线
		if(route==null){
			//产品未绑定审批路线，产品编号为
			ExceptionManager.throwException(ServiceException.class, ERROR_PROD_NOT_BIND_ROUTE, prodNo);
		}
		
		Long auditRouteId=route.getId();
		String entityName=entity.getClass().getName();
		Serializable pk=commonDAO.getPK(entity);
		//3、校验审批实体
		if(pk==null){
			//审批实体找不到数据映射
			ExceptionManager.throwException(ServiceException.class, ERROR_AUDIT_ENTITY_NOT_MAP, entityName);
		}
		Long entityId=Long.parseLong(pk.toString());
		
		
		//查询第一个审批岗位
		AuditStation firstStation=findFirstStation(auditRouteId,brchId);
		//4、校验审批岗位
		if(null==firstStation){
			//产品编号为prodNo的产品找不到可用的审批岗位
			ExceptionManager.throwException(ServiceException.class, ERROR_AUDIT_STATION_NOT_EXIST, prodNo);
		}
		//5、校验审批任务
		List<AuditTask> tasks=auditTaskDAO.findAuditTask(brchId, prodId,entityId);
		if(null!=tasks&&tasks.size()>0){
			for(AuditTask t:tasks){
				String st=t.getAuditStatus();
				if(AuditTask.STATUS_AUDITING.equals(st)){
					//同一产品下的同一审批实体只能有一个审批中任务
					ExceptionManager.throwException(ServiceException.class,ERROR_AUDIT_TASKING_EXIST, new String[]{prodNo,entityId+""});
				}
			}
		}
		Long auditTaskId=null;
		//6、创建审批任务
		try{
			AuditTask tk=new AuditTask();
			tk.setAuditEntityId(entityId);
			tk.setAuditEntityName(entityName);
			tk.setAuditEntityService(callBackServiceBean==null?"":callBackServiceBean);
			tk.setAuditPrivilege(auditPrivilege);
			tk.setAuditRouteId(auditRouteId);
			tk.setAuditTaskAuthor(userId);
			tk.setBrchId(brchId);
			tk.setProdId(prodId);
			tk.setAuditTaskCreateTime(DateTimeUtil.getNowDateTime());
			tk.setAuditStatus(AuditTask.STATUS_AUDITING);
			tk.setAuditRemark(auditRemark==null?"":auditRemark);
			auditTaskId=auditTaskDAO.save(tk);
			//7、创建第一个岗位的审批进度
			AuditProcess process=new AuditProcess();
			process.setAuditProcessCommitPerson(userId);
			process.setAuditProcessExecStation(firstStation.getId());
			process.setAuditTaskId(auditTaskId);
			process.setAuditProcessStatus(AuditProcess.STATUS_UN_ACCEPT);
			process.setSortNo(1L);
			auditProcessDAO.save(process);
		}catch(Exception e){
			ExceptionManager.throwException(ServiceException.class, ERROR_AUDIT_TASK_CREATE,e);
		}
		return auditTaskId;
	}


	public AuditTaskRevokeResult revokeAuditTask(Long auditTaskId)throws ServiceException {
		AuditTaskRevokeResult result=new AuditTaskRevokeResult();
		result.setRevokePass(AuditTaskRevokeResult.FAIL);
		AuditTask tk=auditTaskDAO.get(auditTaskId);
		if(tk!=null&&!AuditTask.STATUS_REVOKED.equals(tk.getAuditStatus())){
			List<AuditProcess> processes=auditProcessDAO.findAllProcess(tk.getId());
			if(processes!=null&&processes.size()>0){
				if(processes.size()==1){
					AuditProcess p=processes.get(0);
					if(AuditProcess.STATUS_UN_ACCEPT.equals(p.getAuditProcessStatus())){
						//只产生一个审批进度，且没有人受理，则可撤销
						tk.setAuditStatus(AuditTask.STATUS_REVOKED);
						auditTaskDAO.update(tk);
						result.setRevokePass(AuditTaskRevokeResult.PASS);
					}
				}
				result.setAuditProcessList(processes);
			}
			result.setAuditTask(tk);
		}
		return result;
	}
	public AuditTaskRevokeResult revokeAuditTask(UserLogonInfo logonInfo,
			String prodNo, Object entity) throws ServiceException {
		
		ProductInfo prod=productInfoDAO.getProductInfoByProdNo(prodNo);
		//1、校验产品
		if(prod==null){
			//产品不存在，产品编号为
			ExceptionManager.throwException(ServiceException.class, ERROR_PROD_NOT_EXIST_BY_NO, prodNo);
		}
		Serializable pk=commonDAO.getPK(entity);
		//2、校验审批实体
		String entityName=entity.getClass().getName();
		if(pk==null){
			//审批实体找不到数据映射
			ExceptionManager.throwException(ServiceException.class, ERROR_AUDIT_ENTITY_NOT_MAP, entityName);
		}
		Long entityId=Long.parseLong(pk.toString());
		Long prodId=prod.getId();
		Long brchId=logonInfo.getBranchId();
		AuditTaskRevokeResult result=new AuditTaskRevokeResult();
		result.setRevokePass(AuditTaskRevokeResult.FAIL);
		//查找最后一次审批任务
		AuditTask tk=auditTaskDAO.findLastAuditTask(brchId, prodId,entityId);
		if( tk != null && StringUtils.equals(AuditTask.STATUS_AUDITING, tk.getAuditStatus()) ){
			List<AuditProcess> processes=auditProcessDAO.findAllProcess(tk.getId());
			if(processes!=null&&processes.size()>0){
				if(processes.size()==1){
					AuditProcess p=processes.get(0);
					if(AuditProcess.STATUS_UN_ACCEPT.equals(p.getAuditProcessStatus())){
						//只产生一个审批进度，且没有人受理，则可撤销
						tk.setAuditStatus(AuditTask.STATUS_REVOKED);
						auditTaskDAO.update(tk);
						result.setRevokePass(AuditTaskRevokeResult.PASS);
					}
				}
				result.setAuditProcessList(processes);
			}
			if( StringUtils.equals(AuditTask.STATUS_AUDITING, tk.getAuditStatus()) ){
				ExceptionManager.throwException(ServiceException.class, ERROR_REVOKE_ACCEPTING_AUDITPROCESS);
			}
			result.setAuditTask(tk);
		}else{
			//非审批中的任务不能撤销
			ExceptionManager.throwException(ServiceException.class, ERROR_REVOKE_NOT_AUDITING_AUDITTASK);
		}
		return result;
	}

	public void acceptAuditProcess(UserLogonInfo logonInfo, AuditProcess process)
			throws ServiceException {
		Long processId=process.getId();
		Long userId=logonInfo.getSysUserId();
		AuditProcess p=auditProcessDAO.get(processId);
		if(p!=null){
			/*
			Long stationId=p.getAuditProcessExecStation();
			if(!stationUserDAO.existStationUserMap(userId,stationId)){
				//校验该用户是否拥有必须的审批岗位
				ExceptionManager.throwException(ServiceException.class, ERROR_AUDIT_STATION_REGX);
			}
			*/
			Long tkId=p.getAuditTaskId();
			AuditTask tk=auditTaskDAO.get(tkId);
			AuditRoute route=auditRouteDAO.get(tk.getAuditRouteId());
			String auditMode=route.getAuditMode();
			if(AuditRoute.AUDIT_MODE_MUTUALLY.equals(auditMode)){
				if(userId.equals(tk.getAuditTaskAuthor())){
					//审批模式为互斥时，不能审批自己的业务
					ExceptionManager.throwException(ServiceException.class, ERROR_AUDIT_MODE_REGX_FAIL);
				}
			}
			if(AuditTask.STATUS_AUDITING.equals(tk.getAuditStatus())){
				if(AuditProcess.STATUS_UN_ACCEPT.equals(p.getAuditProcessStatus())){
					p.setAuditProcessExecPerson(userId);
					p.setAuditProcessStatus(AuditProcess.STATUS_ACCEPING);
					auditProcessDAO.update(p);
				}else{
					//只能受理未被受理的审批进度
					ExceptionManager.throwException(ServiceException.class, ERROR_AUDIT_RE_ACCEPT);
				}
			}else{
				//只能处理审批中的审批任务，以撤销或结束的不能受理
				ExceptionManager.throwException(ServiceException.class, ERROR_AUDIT_ACCEPT_REVOKE_OR_OVER);
			}
		}

	}
	
	public void acceptAuditProcess(UserLogonInfo logonInfo,List<Long> idList){
		if( logonInfo != null && idList != null && idList.size() > 0 ){
			for( Long auditProcessId : idList ){
				AuditProcess ap = new AuditProcess();
				ap.setId(auditProcessId);
				acceptAuditProcess(logonInfo, ap);
			}
		}
	}
	
	public void revokeAcceptAuditProcess(UserLogonInfo logonInfo,Long processId)
			throws ServiceException {
		Long userId=logonInfo.getSysUserId();
		AuditProcess p=auditProcessDAO.get(processId);
		if(p!=null){
			if(AuditProcess.STATUS_ACCEPING.equals(p.getAuditProcessStatus())){
				if(userId.equals(p.getAuditProcessExecPerson())){
					p.setAuditProcessStatus(AuditProcess.STATUS_UN_ACCEPT);
					p.setAuditProcessExecPerson(null);
					auditProcessDAO.update(p);
				}else{
					ExceptionManager.throwException(ServiceException.class, ERROR_REVODE_ACCEPT_EXEC_PERSON_DEF);
				}
			}else{
				ExceptionManager.throwException(ServiceException.class, ERROR_REVODE_ACCEPT_NOT_ACCEPTING);
			}
			
		}

	}

	public void revokeAcceptAuditProcess(UserLogonInfo logonInfo,List<Long> idList){
		if( logonInfo != null && idList != null && idList.size() > 0 ){
			for( Long auditProcessId : idList ){
				revokeAcceptAuditProcess(logonInfo, auditProcessId);
			}
		}
	}

	public List<Map<String,Object>> queryCanOperateAuditList(UserLogonInfo userLogonInfo,Page page,String prodNo)
			throws ServiceException, DAOException {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		if(userLogonInfo!=null){
			list=auditProcessDAO.getCanOperateAuditProcess(userLogonInfo.getSysUserId(),prodNo,userLogonInfo.getBranchId(),page);
			if( list != null && list.size() > 0 ){
				//目前只会有一个接入点
				MemberProductInfo mpi = memberProductInfoDAO.getMemberProductByProdNo(String.valueOf(list.get(0).get("miNo")), prodNo);
				if( mpi != null ){
					String prodAlias = mpi.getProdAlias();
					if(StringUtils.isNotBlank(prodAlias)){
						for(Map<String,Object> map :list){
							map.put("prodAlias", prodAlias);
						}
					}
				}
			}
		}
		return list;
	}
	
	public Map<String,Integer> getCanOperateAPCount(Long userId,List<String> prodNos,Long brchId){
		return auditProcessDAO.getCanOperateAPCount(userId, prodNos, brchId);
	}

	public AuditTask getAuditTask(Long id) throws ServiceException,
			DAOException {
		return auditTaskDAO.get(id);
	}

	public List<Map<String,Object>> queryAllAuditProcess(Long id) throws ServiceException,
			DAOException {
		
		return auditProcessDAO.getAllProcess(id);
	}

	public AuditProcess getauditProcess(Long id) throws ServiceException,
			DAOException {
		return auditProcessDAO.get(id);
	}

	public void saveAndCommitAuditResult(AuditProcess auditProcess)
			throws ServiceException, DAOException {
		AuditProcess ap=auditProcessDAO.get(auditProcess.getId());
		ap.setAuditProcessExecRemark(auditProcess.getAuditProcessExecRemark());
		ap.setAuditProcessExecResult(auditProcess.getAuditProcessExecResult());
		auditProcessDAO.update(ap);
		commitAuditProcess(ap);
		
	}

	public void saveAuditResult(AuditProcess auditProcess)
			throws ServiceException, DAOException {
		auditProcessDAO.update(auditProcess);
		
	}
	public List<AuditProcess> queryAlreadyDoneAuditProcess(Long auditTaskId)throws ServiceException {

		return null;
	}
	
	public void commitAuditProcess(AuditProcess process)throws ServiceException {
		process=auditProcessDAO.get(process.getId());
		String remark=process.getAuditProcessExecRemark();
		String result=process.getAuditProcessExecResult();
		String status=process.getAuditProcessStatus();
		//1,审批提交校验
		if(remark==null||remark.length()<1||result==null||result.length()<1){
			//审批意见不完整，不能提交
			ExceptionManager.throwException(ServiceException.class, ERROR_AUDIT_RESULT_NOT_COMPLATE);
		}
		if(AuditProcess.STATUS_ACCEPED.equals(status)||AuditProcess.STATUS_UN_ACCEPT.equals(status)){
			//不能提交未受理或已受理完成的审批
			ExceptionManager.throwException(ServiceException.class, ERROR_AUDIT_COMMIT_MUST_ACCEPING);
		}
		//2，根据审批意见分别处理
		AuditTask task=auditTaskDAO.get(process.getAuditTaskId());
		ProductInfo prod=productInfoDAO.get(task.getProdId());
		//同意
		if(AuditProcess.RESULT_AGREE.equals(result)){
			AuditStation currentStation=auditStationDAO.get(process.getAuditProcessExecStation());
			AuditStation nextStation=findNextAuditStation(task,currentStation);
			if(nextStation==null){
				//不存在下一岗，结束审批任务,审批通过
				task.setAuditStatus(AuditTask.STATUS_AUDIT_PASS);
				task.setAuditTaskDoneTime(DateTimeUtil.getNowDateTime());
				task.setProdNo(prod.getProdNo());
				auditCallBack(task);
				auditTaskDAO.update(task);
			}else{
				//为下一岗准备审批进度
				AuditProcess nextProcess=new AuditProcess();
				nextProcess.setAuditProcessCommitPerson(process.getAuditProcessExecPerson());
				nextProcess.setAuditProcessCommitStation(process.getAuditProcessExecStation());
				nextProcess.setAuditProcessExecStation(nextStation.getId());
				nextProcess.setAuditProcessStatus(AuditProcess.STATUS_UN_ACCEPT);
				nextProcess.setAuditTaskId(process.getAuditTaskId());
				nextProcess.setSortNo(process.getSortNo()+1);
				auditProcessDAO.save(nextProcess);
			}
		}
		//拒绝
		if(AuditProcess.RESULT_REFUSED.equals(result)){
			//结束审批任务,审批不通过
			task.setAuditStatus(AuditTask.STATUS_AUDIT_FAIL);
			task.setAuditTaskDoneTime(DateTimeUtil.getNowDateTime());
			task.setProdNo(prod.getProdNo());
			auditCallBack(task);
			auditTaskDAO.update(task);
		}
		//驳回
		if(AuditProcess.RESULT_REJECT.equals(result)){
			if(process.getAuditProcessCommitStation()==null){
				//提交岗位为空，则认为不存在上一岗，结束审批任务，不通过
				task.setAuditStatus(AuditTask.STATUS_AUDIT_FAIL);
				task.setAuditTaskDoneTime(DateTimeUtil.getNowDateTime());
				task.setProdNo(prod.getProdNo());
				auditCallBack(task);
				auditTaskDAO.update(task);
			}else{
				//为上一岗创建新的审批进度，提交人作为下一个进度的执行人
				AuditProcess nextProcess=new AuditProcess();
				nextProcess.setAuditProcessCommitPerson(process.getAuditProcessExecPerson());
				nextProcess.setAuditProcessCommitStation(process.getAuditProcessExecStation());
				nextProcess.setAuditProcessExecStation(process.getAuditProcessCommitStation());
				nextProcess.setAuditProcessExecPerson(process.getAuditProcessCommitPerson());
				nextProcess.setAuditProcessStatus(AuditProcess.STATUS_UN_ACCEPT);
				nextProcess.setAuditTaskId(process.getAuditTaskId());
				nextProcess.setSortNo(process.getSortNo()+1);
				auditProcessDAO.save(nextProcess);
			}
		}
		//更新当前进度为已受理状态
		process.setAuditProcessDoneTime(DateTimeUtil.getNowDateTime());
		process.setAuditProcessStatus(AuditProcess.STATUS_ACCEPED);
		auditProcessDAO.update(process);
	}
	/**
	 * 获取审批任务的下一执行岗位
	 * @param auditTask
	 * @param auditStation
	 * @return
	 */
	private AuditStation findNextAuditStation(AuditTask auditTask,AuditStation auditStation) {
		AuditRoute route=auditRouteDAO.get(auditTask.getAuditRouteId());
		List<AuditNode> nodes=auditNodeDAO.findAuditNodeByAuditRouteId(route.getId());
		String nodeExecMode=route.getAuditNodeExecMode();
		BigDecimal auditPrivilege=auditTask.getAuditPrivilege()==null?BigDecimal.ZERO:auditTask.getAuditPrivilege();
		//所有节点是否全部需要执行
		boolean isFullExec=AuditRoute.NODE_EXEC_MODE_FULL_EXEC.equals(nodeExecMode);
		//找寻当前岗位的标志
		boolean flag=false;
		for(int i=0;i<nodes.size();i++){
			AuditNode node=nodes.get(i);
			//节点下的岗位是否需要权限控制
			boolean isCtrl=AuditNode.IS_PRIVILEGE_CTRL.equals(node.getIsPrivilegeCtrl());
			List<AuditStation>  stations=auditStationDAO.findAuditStationByAuditNodeId(node.getId(), auditTask.getBrchId());
			for(int j=0;j<stations.size();j++){
				AuditStation station=stations.get(j);
				BigDecimal p=station.getAuditStationPrivilege();
				//当前岗位的审批权限
				BigDecimal currentPrivilege=p==null?BigDecimal.ZERO:p;
				if(station.getId().equals(auditStation.getId())){
					flag=true;
					if(!isFullExec){
						//条件执行
						if(isCtrl){
							//需要权限控制
							if(auditPrivilege.doubleValue()<=currentPrivilege.doubleValue()){
								//满足审批权限要求,不需要下一岗
								return null;
							}
						}
					}else{
						//全部执行
						if(isCtrl){
							//需要权限控制
							if(auditPrivilege.doubleValue()<=currentPrivilege.doubleValue()){
								//满足审批权限要求,跳出当前节点
								break;
							}
						}
					}
				}else{
					//当前岗位之后的第一个岗位
					if(flag){
						return station;
					}
				}
				
			}
		}
		return null;
	}
	/**
	 * 第一个审批岗位
	 * @param auditRouteId
	 * @param brchId
	 * @return
	 */
	private AuditStation findFirstStation(Long auditRouteId,Long brchId){
		List<AuditStation> allStation=auditStationDAO.findAllStationOrderByNodeAndSort(auditRouteId, brchId);
		AuditStation firstStation=null;
		if(allStation!=null&&allStation.size()>0){
			firstStation=allStation.get(0);
		}
		return firstStation;
	}
	/**
	 * 审批结束后回调当初注入的审批任务
	 * @param auditTask
	 * @throws ServiceException
	 */
	private void auditCallBack(AuditTask auditTask)throws ServiceException{
		if(auditTask!=null){
			try {
				List<AuditProcess> processes=auditProcessDAO.findAllProcess(auditTask.getId());
				IAuditCallBack callBack=SourceTemplate.getBean(IAuditCallBack.class,auditTask.getAuditEntityService());		
				callBack.auditCallBack(auditTask, processes);
			} catch (Exception e) {
				ExceptionManager.throwException(ServiceException.class, ERROR_AUDIT_CALLBACK,e);
			}
		}
	}

	public AuditTask getLastAuditTask(String prodNo,Long entityId,Class<?> cls)
			throws ServiceException, DAOException {
		ProductInfo prod=productInfoDAO.getProductInfoByProdNo(prodNo);
		if(prod==null){
			//产品不存在，产品编号为
			ExceptionManager.throwException(ServiceException.class, ERROR_PROD_NOT_EXIST_BY_NO, prodNo);
		}
		return auditTaskDAO.getLastAuditTask(prod.getId(),entityId, cls);
	}

	public List<Map<String,Object>> queryAuditHist(AuditHistSearchBean auditHistSearch,
			Page page) throws ServiceException, DAOException {
		return auditTaskDAO.findAuditHist(auditHistSearch,page);
	}

	public void copyDefaultProductInfoAuditRoute( Long branchId) {
		if( branchId != null ){
			List<ReAuditRouteProduct> defaultList = productAuditDAO.getAuditRouteProdByBrchNull();
			if( defaultList != null && defaultList.size() > 0 ){
				List<ReAuditRouteProduct> addList = new ArrayList<ReAuditRouteProduct>(defaultList.size());
				for( ReAuditRouteProduct ap : defaultList ){
					if( productAuditDAO.getReAuditRouteProductCount(ap.getAuditRouteId(), ap.getProdId(), branchId) == 0 ){
						ReAuditRouteProduct addArp = new ReAuditRouteProduct();
						addArp.setAuditRouteId(ap.getAuditRouteId());
						addArp.setProdId(ap.getProdId());
						addArp.setBrchId(branchId);
						addList.add(addArp);
					}
				}
				if( addList != null && addList.size() > 0 ){
					productAuditDAO.saveOrUpdateAll(addList);
					//拷贝岗位
					List<Long> idList = new ArrayList<Long>(addList.size());
					for( ReAuditRouteProduct arp : addList ){
						idList.add(arp.getAuditRouteId());
					}
					auditRouteService.copyDefaultAuditStationByAuditRouteId(idList, branchId,true);
				}
			}
		}
	}
	
	
	public Long commitAuditTask(UserLogonInfo logonInfo, String prodNo,
			Object entity, String auditRemark) throws ServiceException {
		
		return commitAuditTask(logonInfo,prodNo,entity,BigDecimal.ZERO,AuditConfig.getCallBackByProdNo(prodNo),auditRemark);
	}
	public Long commitAuditTask(UserLogonInfo logonInfo, String prodNo,
			Object entity, BigDecimal auditPrivilege, String auditRemark)
			throws ServiceException {
		return commitAuditTask(logonInfo,prodNo,entity,auditPrivilege,AuditConfig.getCallBackByProdNo(prodNo),auditRemark);
	}
	public Long commitAuditTask(UserLogonInfo logonInfo, String prodNo,
			Object entity)
			throws ServiceException {
		return commitAuditTask(logonInfo,prodNo,entity,BigDecimal.ZERO,AuditConfig.getCallBackByProdNo(prodNo),"");
	}
	
	public boolean hasMulProductBindAuditRoute(Long auditRouteId, Long branchId){
		return productAuditDAO.getReAuditRouteProductCount(auditRouteId, null, branchId) > 1;
	}

}
