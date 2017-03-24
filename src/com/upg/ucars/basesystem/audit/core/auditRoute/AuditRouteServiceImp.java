package com.upg.ucars.basesystem.audit.core.auditRoute;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.basesystem.audit.core.auditNode.IAuditNodeDAO;
import com.upg.ucars.basesystem.audit.core.auditStation.IAuditStationDAO;
import com.upg.ucars.basesystem.audit.core.auditStation.IAuditStationRoleDAO;
import com.upg.ucars.basesystem.audit.core.auditStation.IAuditStationUserDAO;
import com.upg.ucars.basesystem.audit.core.auditTask.IAuditTaskDAO;
import com.upg.ucars.basesystem.audit.core.productAudit.IProductAuditDAO;
import com.upg.ucars.basesystem.product.core.product.IMemberProductInfoDAO;
import com.upg.ucars.basesystem.product.core.product.IProductInfoDAO;
import com.upg.ucars.basesystem.security.core.branch.IBranchDAO;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.audit.AuditNode;
import com.upg.ucars.mapping.basesystem.audit.AuditRoute;
import com.upg.ucars.mapping.basesystem.audit.AuditStation;
import com.upg.ucars.mapping.basesystem.audit.AuditTask;
import com.upg.ucars.mapping.basesystem.audit.ReAuditRouteProduct;
import com.upg.ucars.mapping.basesystem.audit.ReAuditStationRole;
import com.upg.ucars.mapping.basesystem.audit.ReAuditStationUser;
import com.upg.ucars.mapping.basesystem.product.MemberProductInfo;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.BuserComparator;
import com.upg.ucars.mapping.basesystem.security.Role;
import com.upg.ucars.model.security.UserLogonInfo;

public class AuditRouteServiceImp extends BaseService implements
		IAuditRouteService {
	
	/** 删除绑定人员的岗位错误 */
	private static final String DELETE_BIND_USER_STATION_ERROR = "DELETE_BIND_USER_STATION_ERROR";
	/** 删除已有岗位的节点错误 */
	private static final String DELETE_HAS_STATION_NODE_ERROR = "DELETE_HAS_STATION_NODE_ERROR";
	
	private IAuditRouteDAO auditRouteDAO;
	private IAuditNodeDAO auditNodeDAO;
	private IAuditStationDAO auditStationDAO;
	private IMemberProductInfoDAO memberProductInfoDAO;
	private IProductInfoDAO productInfoDAO;
	private IProductAuditDAO productAuditDAO;
	private IAuditTaskDAO auditTaskDAO;
	private IBranchDAO branchDAO;
	private IAuditStationUserDAO auditStationUserDAO;
	private IAuditStationRoleDAO auditStationRoleDAO;
	public List<AuditRoute> findAuditRoute(AuditRoute auditRoute, Page page)
			throws DAOException {
		return auditRouteDAO.findAuditRouteByMiNo(auditRoute,page);
	}

	public void createAuditRoute(AuditRoute auditRoute) throws DAOException {
		auditRouteDAO.save(auditRoute);
	}

	public void modifyAuditRoute(AuditRoute auditRoute) throws DAOException {
		auditRouteDAO.update(auditRoute);
	}

	public void removeAuditRoute(AuditRoute auditRoute) throws DAOException {
		//只能删除没有与产品绑定的审批路线
		//...
		List<ReAuditRouteProduct> list=productAuditDAO.findAuditProductByAuditRouteId(auditRoute.getId());
		if(list!=null&&list.size()>0){
			ExceptionManager.throwException(ServiceException.class, ReAuditRouteProduct.HAS_AUDITING_PRODUCT_ERROR);
		}
		auditRouteDAO.delete(findAuditRoute(auditRoute.getId()));

	}

	public void addAuditNode(AuditNode auditNode) throws DAOException {
		Long no=auditNodeDAO.getMaxSortNo(auditNode.getAuditRouteId());
		auditNode.setSortNo(no);
		auditNodeDAO.save(auditNode);
	}

	public void modifyAuditNode(AuditNode auditNode) throws DAOException {
		auditNodeDAO.update(auditNode);

	}

	public void removeAuditNode(AuditNode auditNode) throws DAOException {
		List<AuditStation> stationList = auditStationDAO.getAuditStationListByNodeId(auditNode.getId());
		if( stationList != null && stationList.size() > 0 ){
			ExceptionManager.throwException(ServiceException.class, DELETE_HAS_STATION_NODE_ERROR);
		}
		auditNodeDAO.delete(findAuditNode(auditNode.getId()));
	}

	public void addAuditStation(AuditStation auditStation)
			throws DAOException {
		Long no=auditStationDAO.getMaxSortNo(auditStation.getAuditNodeId(),auditStation.getCreateBrchId());
		auditStation.setSortNo(no+1);
//		if( auditStation.getCreateBrchId() == null ){
//			auditStation.setCreateBrchId(SessionTool.getUserLogonInfo().getBranchId());
//		}
		auditStationDAO.save(auditStation);

	}

	public void modifyAuditStation(AuditStation auditStation)
			throws DAOException {
		auditStationDAO.update(auditStation);

	}

	public void removeAuditStation(AuditStation auditStation)
			throws DAOException {
		List<Buser> bindBusers = findBuserByAuditStationId(auditStation.getId());
		if( bindBusers != null && bindBusers.size() > 0 ){
			ExceptionManager.throwException(ServiceException.class, DELETE_BIND_USER_STATION_ERROR);
		}
		auditStationDAO.delete(findAuditStation(auditStation.getId()));

	}

	public void moveNodeOrStation(Long source, String sourceType, Long target,
			String targetType, String point) throws ServiceException,DAOException {
		if(AuditRoute.TYPE.equals(sourceType)){
			ExceptionManager.throwException(ServiceException.class,AuditRoute.AUDIT_ROUTE_SET_ERR);
		}
		if(AuditNode.TYPE.equals(sourceType)){
			if(AuditStation.TYPE.equals(targetType)){
				ExceptionManager.throwException(ServiceException.class, AuditNode.AUDIT_ROUTE_SET_ERR);
			}
			AuditNode sourceNode=auditNodeDAO.get(source);
			if(AuditNode.TYPE.equals(targetType)){
				if("append".equals(point)){
					ExceptionManager.throwException(ServiceException.class, AuditNode.AUDIT_ROUTE_SET_ERR);
				}
				AuditNode targetNode=auditNodeDAO.get(target);
				List<AuditNode> list=auditNodeDAO.getButtomAuditNode(targetNode);
				if(list!=null&&list.size()>0){
					List list2=new ArrayList();
					for(AuditNode node:list){
						if(node.getId().equals(sourceNode.getId())){
							continue;
						}
						Long sortNo=node.getSortNo()+1;
						node.setSortNo(sortNo);
						list2.add(node);
					}
					sourceNode.setAuditRouteId(targetNode.getAuditRouteId());
					Long no=targetNode.getSortNo();
					if("top".equals(point)){
						sourceNode.setSortNo(no);
						targetNode.setSortNo(no+1);
						list2.add(targetNode);
					}
					if("bottom".equals(point)){
						sourceNode.setSortNo(no+1);
					}
					list2.add(sourceNode);
					auditNodeDAO.saveOrUpdateAll(list2);
					return;
				}else{
					sourceNode.setAuditRouteId(targetNode.getAuditRouteId());
					Long no=targetNode.getSortNo();
					list=new ArrayList();
					if("top".equals(point)){
						sourceNode.setSortNo(no);
						targetNode.setSortNo(no+1);
						list.add(targetNode);
					}
					if("bottom".equals(point)){
						sourceNode.setSortNo(no+1);
					}
					list.add(sourceNode);
					auditNodeDAO.saveOrUpdateAll(list);
					return;
				}
			}
			if(AuditRoute.TYPE.equals(targetType)){
				if(!"append".equals(point)){
					ExceptionManager.throwException(ServiceException.class, AuditNode.AUDIT_ROUTE_SET_ERR);
				}
				//
				AuditRoute targetRoute=auditRouteDAO.get(target);
				if(sourceNode.getAuditRouteId().equals(targetRoute.getId())){
					return;
				}
				Long no=auditNodeDAO.getMaxSortNo(targetRoute.getId())+1;
				sourceNode.setSortNo(no);
				sourceNode.setAuditRouteId(target);
				auditNodeDAO.update(sourceNode);
			}
		}
		if(AuditStation.TYPE.equals(sourceType)){
			if(AuditRoute.TYPE.equals(targetType)){
				ExceptionManager.throwException(ServiceException.class, AuditStation.AUDIT_ROUTE_SET_ERR);
			}
			AuditStation sourceStation=auditStationDAO.get(source);
			if(AuditStation.TYPE.equals(targetType)){
				if("append".equals(point)){
					ExceptionManager.throwException(ServiceException.class, AuditStation.AUDIT_ROUTE_SET_ERR);
				}
				
				AuditStation targetStation=auditStationDAO.get(target);
				List<AuditStation> list=auditStationDAO.getButtomAuditStation(targetStation);
				if(list!=null&&list.size()>0){
					List list2=new ArrayList();
					for(AuditStation node:list){
						if(node.getId().equals(sourceStation.getId())){
							continue;
						}
						Long sortNo=node.getSortNo()+1;
						node.setSortNo(sortNo);
						list2.add(node);
					}
					sourceStation.setAuditNodeId(targetStation.getAuditNodeId());
					Long no=targetStation.getSortNo();
					if("top".equals(point)){
						sourceStation.setSortNo(no);
						targetStation.setSortNo(no+1);
						list2.add(targetStation);
					}
					if("bottom".equals(point)){
						sourceStation.setSortNo(no+1);
					}
					list2.add(sourceStation);
					auditNodeDAO.saveOrUpdateAll(list2);
					return;
				}else{
					sourceStation.setAuditNodeId(targetStation.getAuditNodeId());
					Long no=targetStation.getSortNo();
					list=new ArrayList();
					if("top".equals(point)){
						sourceStation.setSortNo(no);
						targetStation.setSortNo(no+1);
						list.add(targetStation);
					}
					if("bottom".equals(point)){
						sourceStation.setSortNo(no+1);
					}
					list.add(sourceStation);
					auditStationDAO.saveOrUpdateAll(list);
					return;
				}
			}
			if(AuditNode.TYPE.equals(targetType)){
				if(!"append".equals(point)){
					ExceptionManager.throwException(ServiceException.class, AuditStation.AUDIT_ROUTE_SET_ERR);
				}
				AuditNode targetNode=auditNodeDAO.get(target);
				if(sourceStation.getAuditNodeId().equals(targetNode.getId())){
					return;
				}
				Long no=auditStationDAO.getMaxSortNo(targetNode.getId(), sourceStation.getCreateBrchId())+1;
				sourceStation.setSortNo(no);
				sourceStation.setAuditNodeId(targetNode.getId());
				auditStationDAO.update(sourceStation);
			}
		}
	}
	public AuditRoute findAuditRoute(Long id)throws DAOException {
		return auditRouteDAO.get(id);
	}
	public List<AuditNode> findAuditNodeByAuditRouteId(Long auditRouteId)throws DAOException {
		return auditNodeDAO.findAuditNodeByAuditRouteId(auditRouteId);
	}

	public List<AuditStation> findAuditStationByAuditNodeId(Long auditNodeId,Long brchId)throws DAOException {
		return auditStationDAO.findAuditStationByAuditNodeId(auditNodeId,brchId);
	}
	public List<AuditNode> findAuditNode(AuditRoute auditRoute)throws DAOException {
		if(auditRoute!=null&&auditRoute.getId()!=null){
			return findAuditNodeByAuditRouteId(auditRoute.getId());
		}
		return null;
	}

	public List<AuditStation> findAuditStation(AuditNode node, Long brchId) throws DAOException{
		if(node!=null&&node.getId()!=null){
			return findAuditStationByAuditNodeId(node.getId(),brchId);
		}
		return null;
	}
	public AuditNode findAuditNode(Long id) throws DAOException{
		return auditNodeDAO.get(id);
	}

	public AuditStation findAuditStation(Long id) throws DAOException{
		return auditStationDAO.get(id);
	}
	public List<Map<String,Object>> findProductAuditRoute(UserLogonInfo logonInfo,ProductInfo product, Page pg) {
		String miNo=logonInfo.getMiNo();
		Long brchId=logonInfo.getBranchId();
		List<Map<String,Object>> result= null;
		if(miNo!=null&&brchId!=null){
			//1,获取接入的产品信息
			List<MemberProductInfo> list=memberProductInfoDAO.findProductsByMiNo(miNo,product,pg);
			if(list!=null&&list.size()>0){
				result=new ArrayList<Map<String,Object>>(list.size());
				for(MemberProductInfo mp:list){
					String prodAlias=mp.getProdAlias();
					Long prodId=mp.getProdId();
					ProductInfo pi=productInfoDAO.get(prodId);
					String prodNo=pi.getProdNo();
					Object[] rapAr =productAuditDAO.findReArProdAndAr(prodId,brchId);
					AuditRoute auditRoute = null;
					ReAuditRouteProduct rap = null;
					if( rapAr != null ){
						rap = (ReAuditRouteProduct) rapAr[0];
						auditRoute = (AuditRoute) rapAr[1];
					}
					String auditRouteName="";
					Long auditRouteId=null;
					if(auditRoute!=null){
						auditRouteName=auditRoute.getAuditRouteName();
						auditRouteId=auditRoute.getId();
					}
					Map<String,Object> map=new HashMap<String,Object>();
					map.put("prodId", prodId);
					map.put("prodNo", prodNo);
					map.put("prodAlias", StringUtils.isBlank(prodAlias) ? pi.getProdName() : prodAlias );
					map.put("auditRouteId", auditRouteId);
					map.put("auditRouteName", auditRouteName);
					map.put("brchId", brchId);
					map.put("miNo", miNo);
					map.put("rapId", rap == null ? Long.valueOf(0): rap.getId());
					result.add(map);
				}
			}
		}
		return result == null ? SERVICE_EMPTY_LIST : result;
	}
	public void assignAuditRouteForProduct(String[] prodId,
			AuditRoute auditRoute, Long  brchId )throws ServiceException,DAOException{
		if(prodId != null && prodId.length>0){
			List<ReAuditRouteProduct> list=new ArrayList<ReAuditRouteProduct>();
			Long auditRouteId=auditRoute.getId();
			List<ReAuditRouteProduct> rps=productAuditDAO.findBindedProduct(brchId,auditRouteId);
			for(String id:prodId){
				Long pId=Long.valueOf(id);
				//查询产品原来绑定的审批路线
				ReAuditRouteProduct rap=productAuditDAO.findReAuditRouteProduct(pId,brchId);
				Long oldAuditRouteId=null;
				if(rap==null){
					rap=new ReAuditRouteProduct();
					rap.setProdId(pId);
					rap.setBrchId(brchId);
				}else{
					canChangeAudit(brchId, pId);
					oldAuditRouteId=rap.getAuditRouteId();
					if(auditRouteId.equals(oldAuditRouteId)){
						//如果新的审批路线与旧的路线相同，则跳过
						continue;
					}
				}
				//绑定关系已存在或已变更时,维护新的绑定关系
				rap.setAuditRouteId(auditRouteId);
				list.add(rap);
			}
			//保存产品与新路线的绑定关系
			productAuditDAO.saveOrUpdateAll(list);
			if(rps==null||rps.size()<1){
				List<Long> arIdList = new ArrayList<Long>(1);
				arIdList.add(auditRouteId);
				//copyDefaultAuditStationByAuditRouteId(arIdList, brchId,false);
				//增加用户及角色绑定的配置拷贝
				copyDefaultAuditStationAndBindConfigByAuditRouteId(arIdList,brchId,false);
			}
			
		}
	}
	
	
	/**
	 * 
	 * 检查产品是否有审批中的审批任务，如果有则不能变更
	 *
	 * @param branchId
	 * @param productId
	 * @return
	 */
	private void canChangeAudit(Long branchId,Long productId){
		if( branchId != null && productId != null ){
			List<AuditTask> auditingTasks=auditTaskDAO.findAuditingTask(branchId,productId);
			if(auditingTasks!=null&&auditingTasks.size()>0){
				ProductInfo prod=productInfoDAO.get(productId);
				ExceptionManager.throwException(ServiceException.class, ReAuditRouteProduct.HAS_AUDITING_TASK_ERROR, prod.getProdNo());
			}
		}
	}
	
	public void cancelAuditRouteForProduct(List<Long> rapIdList){
		if( rapIdList != null && rapIdList.size() > 0 ){
			for( Long id : rapIdList ){
				if( id.longValue() > 0 ){
					ReAuditRouteProduct rap = productAuditDAO.get(id);
					if( rap != null ){
						canChangeAudit(rap.getBrchId(), rap.getProdId());
						productAuditDAO.delete(rap);
						//cleanAuditStation(rap.getBrchId(), rap.getAuditRouteId());
						//清理岗位并清理绑定的用户角色配置
						cleanAuditStationAndBindConfig(rap.getBrchId(), rap.getAuditRouteId());
					}
				}
			}
			
		}
	}
	
	/**
	 * 
	 * 清理岗位
	 *
	 * @param branchId
	 * @param auditRouteId
	 */
	private void cleanAuditStation(Long branchId,Long auditRouteId){
		if( branchId != null && auditRouteId != null ){
			List<ReAuditRouteProduct> list = productAuditDAO.findBindedProduct(branchId, auditRouteId);
			if( list == null || list.size() == 0 ){
				List<AuditStation> stationList = auditStationDAO.findAuditStationByAuditRouteId(auditRouteId, branchId);
				if( stationList != null && stationList.size() > 0 ){
					auditStationDAO.delAll(stationList);
				}
			}
		}
	}
	/**
	 * 
	 * 清理岗位，清理岗位绑定的用户和角色配置
	 *
	 * @param branchId
	 * @param auditRouteId
	 */
	private void cleanAuditStationAndBindConfig(Long branchId,Long auditRouteId){
		if( branchId != null && auditRouteId != null ){
			List<ReAuditRouteProduct> list = productAuditDAO.findBindedProduct(branchId, auditRouteId);
			if( list == null || list.size() == 0 ){
				List<AuditStation> stationList = auditStationDAO.findAuditStationByAuditRouteId(auditRouteId, branchId);
				if( stationList != null && stationList.size() > 0 ){
					//清理岗位绑定的人员和角色配置，2012年2月增加
					for(AuditStation as:stationList){
						Long asId=as.getId();
						List<ReAuditStationUser> asUsers=auditStationUserDAO.findReAuditStationUserBy(asId);
						List<ReAuditStationRole> asRoles=auditStationRoleDAO.findReAuditStationRoleBy(asId);
						auditStationUserDAO.delAll(asUsers);
						auditStationRoleDAO.delAll(asRoles);
					}
					auditStationDAO.delAll(stationList);
				}
			}
		}
	}
	public void copyDefaultAuditStationByAuditRouteId(List<Long> arIdList,Long branchId,boolean isSysDef){
		if( arIdList != null && arIdList.size() > 0  && branchId != null){
			List<AuditStation> stationList = new ArrayList<AuditStation>();
			for( Long auditRouteId : arIdList ){
				List<AuditStation> exitsList = auditStationDAO.findAuditStationByAuditRouteId(auditRouteId, branchId);
				if(exitsList == null || exitsList.size() < 1){
					List<AuditStation> list = auditStationDAO.getAuditStationsByAridAndBrchNull(auditRouteId, isSysDef);
					if( list != null  ){
						for( AuditStation station : list ){
							AuditStation as = new AuditStation();
							as.setAuditRouteId(station.getAuditRouteId());
							as.setAuditNodeId(station.getAuditNodeId());
							as.setAuditStationName(station.getAuditStationName());
							as.setAuditStationPrivilege(station.getAuditStationPrivilege());
							as.setSortNo(station.getSortNo());
							as.setCreateBrchId(branchId);
							if( station.getBindBrchId() == null ){
								as.setBindBrchId(branchId);
							}else{
								as.setBindBrchId(station.getBindBrchId());
							}
							as.setAuditStationRemark(station.getAuditStationRemark());
							stationList.add(as);
						}
					}
				}
			}
			if( stationList != null && stationList.size() > 0 ){
				auditStationDAO.saveOrUpdateAll(stationList);
			}
		}
	}
	public void copyDefaultAuditStationAndBindConfigByAuditRouteId(List<Long> arIdList,Long branchId,boolean isSysDef){
		if( arIdList != null && arIdList.size() > 0  && branchId != null){
			List<ReAuditStationRole> asRoleList=new ArrayList<ReAuditStationRole>();
			List<ReAuditStationUser> asUserList=new ArrayList<ReAuditStationUser>();
			for( Long auditRouteId : arIdList ){
				List<AuditStation> exitsList = auditStationDAO.findAuditStationByAuditRouteId(auditRouteId, branchId);
				if(exitsList == null || exitsList.size() < 1){
					List<AuditStation> list = auditStationDAO.getAuditStationsByAridAndBrchNull(auditRouteId, isSysDef);
					if( list != null  ){
						for( AuditStation station : list ){
							AuditStation as = new AuditStation();
							as.setAuditRouteId(station.getAuditRouteId());
							as.setAuditNodeId(station.getAuditNodeId());
							as.setAuditStationName(station.getAuditStationName());
							as.setAuditStationPrivilege(station.getAuditStationPrivilege());
							as.setSortNo(station.getSortNo());
							as.setCreateBrchId(branchId);
							if( station.getBindBrchId() == null ){
								as.setBindBrchId(branchId);
							}else{
								as.setBindBrchId(station.getBindBrchId());
							}
							as.setAuditStationRemark(station.getAuditStationRemark());
							List<ReAuditStationUser> asUsers=auditStationUserDAO.findReAuditStationUserBy(station.getId());
							List<ReAuditStationRole> asRoles=auditStationRoleDAO.findReAuditStationRoleBy(station.getId());
							//拷贝一个岗位
							Long asId=auditStationDAO.save(as);
							//拷贝角色绑定配置
							for(ReAuditStationRole asr:asRoles){
								ReAuditStationRole asRole=new ReAuditStationRole();
								asRole.setAuditStationId(asId);
								asRole.setRoleId(asr.getRoleId());
								asRoleList.add(asRole);
							}
							//拷贝用户绑定配置
							for(ReAuditStationUser asu:asUsers){
								ReAuditStationUser asUser=new ReAuditStationUser();
								asUser.setAuditStationId(asId);
								asUser.setUserId(asu.getUserId());
								asUserList.add(asUser);
							}
						}
						
					}
				}
			}
			if( asRoleList.size() > 0 ){
				auditStationRoleDAO.saveOrUpdateAll(asRoleList);
			}
			if( asUserList.size() > 0 ){
				auditStationUserDAO.saveOrUpdateAll(asUserList);
			}
		}
	}
	
	public List<Map<String,Object>> findBindedAuditStation(AuditStation as, Page pg) {
		List<AuditStation> stations=auditStationDAO.findBindedAuditStation(as,pg);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		if(stations!=null){
			for(AuditStation auditStation:stations){
				AuditRoute route=auditRouteDAO.get(auditStation.getAuditRouteId());
				Long createBrchId=auditStation.getCreateBrchId();
				Long auditStationId=auditStation.getId();
				Branch brch=branchDAO.get(createBrchId);
				String createBrchName=brch.getBrchName();
				
				Long userNumber=auditStationUserDAO.countUserNumberByAuditStationId(auditStationId);
				Long userNumber2=auditStationRoleDAO.countUserNumberByAuditStationId(auditStationId);
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("auditStationId", auditStationId);
				map.put("auditStationName", auditStation.getAuditStationName());
				map.put("auditStationRemark", auditStation.getAuditStationRemark());
				map.put("auditStationPrivilege", auditStation.getAuditStationPrivilege());
				map.put("brchName", createBrchName);
				map.put("userNumber", userNumber+userNumber2);
				map.put("auditRouteName", route.getAuditRouteName());
				map.put("auditRouteRemark", route.getAuditRouteRemark());
				list.add(map);
			}
		}
		return list;
	}
	public List<Buser> findBuserByAuditStationId(Long auditStationId) {
		
		return auditStationUserDAO.findBuserByAuditStationId(auditStationId);
	}
	public void assignAuditStationWithUsers(String[] userIds,
			AuditStation auditStation) throws ServiceException, DAOException {
		//1，删除旧的配置
		List<ReAuditStationUser> reList=auditStationUserDAO.findReAuditStationUserBy(auditStation.getId());
		if(reList!=null&&reList.size()>0)
			auditStationUserDAO.delAll(reList);
		//2，应用新的配置
		List<ReAuditStationUser> list=new ArrayList<ReAuditStationUser>();
		if(userIds!=null&&userIds.length>0){
			for(String id:userIds){
				Long userId=Long.valueOf(id);
				ReAuditStationUser re=new ReAuditStationUser();
				re.setAuditStationId(auditStation.getId());
				re.setUserId(userId);
				list.add(re);
			}
			auditStationUserDAO.saveOrUpdateAll(list);
		}
		
		
	}
	public void addAssignAuditStationWithUsers(String[] userIds,
			AuditStation auditStation) {
				List<ReAuditStationUser> reList=auditStationUserDAO.findReAuditStationUserBy(auditStation.getId());

				if(userIds!=null&&userIds.length>0){
					for(String id:userIds){
						Long userId=Long.valueOf(id);
						ReAuditStationUser re=new ReAuditStationUser();
						re.setAuditStationId(auditStation.getId());
						re.setUserId(userId);
						if(!reList.contains(re)){
							reList.add(re);
						}
						
					}
					auditStationUserDAO.saveOrUpdateAll(reList);
				}
		
	}
	public void cancelAssignAuditStationWithUsers(String[] userIds,
			AuditStation auditStation) throws ServiceException, DAOException{
		auditStationUserDAO.delete(userIds,auditStation);
	}
	public List<Role> findRoleByAuditStationId(Long stationId) {
		return auditStationRoleDAO.findRoleByAuditStationId(stationId);
	}

	public void addAssignAuditStationWithRoles(String[] rids,
			AuditStation auditStation) {
			List<ReAuditStationRole> reList=auditStationRoleDAO.findReAuditStationRoleBy(auditStation.getId());

			if(rids!=null&&rids.length>0){
				for(String id:rids){
					Long roleId=Long.valueOf(id);
					ReAuditStationRole re=new ReAuditStationRole();
					re.setAuditStationId(auditStation.getId());
					re.setRoleId(roleId);
					if(!reList.contains(re)){
						reList.add(re);
					}
					
				}
				auditStationRoleDAO.saveOrUpdateAll(reList);
			}
		
	}

	public void cancelAssignAuditStationWithRoles(String[] rids,
			AuditStation auditStation) {
		auditStationRoleDAO.delete(rids,auditStation);
		
	}
	public List<AuditStation> findAuditStationByAuditRouteId(Long auditRouteId,
			Long branchId) {
		
		return auditStationDAO.findAllStationOrderByNodeAndSort(auditRouteId, branchId);
	}
	public List<Buser> findBuserByAuditStationIdContainRole(Long auditStationid) {
		List<Buser> uList1=auditStationUserDAO.findBuserByAuditStationId(auditStationid);
		List<Buser> uList2=auditStationRoleDAO.findBuserByAuditStationId(auditStationid);
		List<Buser> retList=new ArrayList<Buser>();
		for(Buser u:uList1){
			if(!retList.contains(u)){
				retList.add(u);
			}
		}
		for(Buser u:uList2){
			if(!retList.contains(u)){
				retList.add(u);
			}
		}
		BuserComparator comparator=new BuserComparator();
		Collections.sort(retList,comparator);
		return retList;
	}
	public boolean isEditAudit(Long auditRouteId) {
		boolean ret = true;
		if( auditRouteId != null ){
			List<ReAuditRouteProduct> list = productAuditDAO.findAuditProductByAuditRouteId(auditRouteId);
			if( list != null && list.size() > 0 ){
				ret = false;
			}
		}
		return ret;
	}
	
	
	/*------------get/set---------------*/

	public void setAuditRouteDAO(IAuditRouteDAO auditRouteDAO) {
		this.auditRouteDAO = auditRouteDAO;
	}

	public void setAuditNodeDAO(IAuditNodeDAO auditNodeDAO) {
		this.auditNodeDAO = auditNodeDAO;
	}

	public void setAuditStationDAO(IAuditStationDAO auditStationDAO) {
		this.auditStationDAO = auditStationDAO;
	}

	public void setMemberProductInfoDAO(IMemberProductInfoDAO memberProductInfoDAO) {
		this.memberProductInfoDAO = memberProductInfoDAO;
	}

	public void setProductInfoDAO(IProductInfoDAO productInfoDAO) {
		this.productInfoDAO = productInfoDAO;
	}
	
	public void setProductAuditDAO(IProductAuditDAO productAuditDAO) {
		this.productAuditDAO = productAuditDAO;
	}

	public void setAuditTaskDAO(IAuditTaskDAO auditTaskDAO) {
		this.auditTaskDAO = auditTaskDAO;
	}

	public void setBranchDAO(IBranchDAO branchDAO) {
		this.branchDAO = branchDAO;
	}

	public void setAuditStationUserDAO(IAuditStationUserDAO auditStationUserDAO) {
		this.auditStationUserDAO = auditStationUserDAO;
	}

	public void setAuditStationRoleDAO(IAuditStationRoleDAO auditStationRoleDAO) {
		this.auditStationRoleDAO = auditStationRoleDAO;
	}

	public List<AuditStation> findAuditStationTemplateByAuditRouteId(Long id) {
		return auditStationDAO.getAuditStationsByAridAndBrchNull(id, false);
	}

	

	

	

	


}
