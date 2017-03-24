package com.upg.ucars.basesystem.audit.action;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.basesystem.audit.core.AuditConfig;
import com.upg.ucars.basesystem.audit.core.AuditHistSearchBean;
import com.upg.ucars.basesystem.audit.core.IAuditService;
import com.upg.ucars.basesystem.audit.core.auditRoute.IAuditRouteService;
import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.basesystem.product.core.product.IProductService;
import com.upg.ucars.basesystem.security.core.branch.IBranchService;
import com.upg.ucars.basesystem.security.core.role.IRoleService;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.mapping.basesystem.audit.AuditNode;
import com.upg.ucars.mapping.basesystem.audit.AuditProcess;
import com.upg.ucars.mapping.basesystem.audit.AuditRoute;
import com.upg.ucars.mapping.basesystem.audit.AuditStation;
import com.upg.ucars.mapping.basesystem.audit.AuditTask;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.Role;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.StringUtil;
/**
 * 审批
 * 
 * @author shentuwy
 * @date 2011-4-1 上午08:55:43
 *
 */
public class AuditAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6642486294186641618L;
	
	private static final String AUDIT_ROUTE_MAIN="auditRouteMain";
	private static final String AUDIT_ROUTE_ADD="auditRouteAdd";
	private static final String AUDIT_ROUTE_EDIT="auditRouteEdit";
	private static final String AUDIT_NODE_ADD="auditNodeAdd";
	private static final String AUDIT_NODE_EDIT="auditNodeEdit";
	private static final String AUDIT_STATION_ADD="auditStationAdd";
	private static final String AUDIT_STATION_EDIT="auditStationEdit";
	private static final String AUDIT_ROUTE_SET="auditRouteSet";
	private static final String AUDIT_ROUTE_VIEW="auditRouteView";
	private static final String AUDIT_NODE_VIEW="auditNodeView";
	private static final String AUDIT_STATION_VIEW="auditStationView";
	private static final String AUDIT_PRODUCT_MAIN="auditProductMain";
	private static final String PRODUCT_AUDIT_ROUTE_SELECT="productAuditRouteSelect";
	private static final String PRODUCT_AUDIT_ROUTE_SET="productAuditRouteSet";
	private static final String USER_MUTIL_SELECT="userMutilSelect";
	private static final String AUDIT_STATION_USER_MAIN="auditStationUserMain";
	private static final String AUDIT_MAIN="auditMain";
	private static final String AUDIT="audit";
	private static final String AUDIT_TASK_HIST="auditTaskHist";
	private static final String TO_ASSIGN_STATION="toAssignStation";
	private static final String TO_PRE_ASSIGN_STATION="toPreAssignStation";
	
	private IAuditRouteService auditRouteService;
	private IBranchService branchService;
	private IUserService userService;
	private IRoleService roleService;
	/** 审批服务 */
	private IAuditService auditService;
	/** 产品服务 */
	private IProductService productService;
	private AuditRoute auditRoute;
	private AuditNode auditNode;
	private AuditStation auditStation;
	private AuditProcess auditProcess;
	private AuditTask auditTask;
	private ProductInfo product;
	private AuditHistSearchBean auditHistSearch;
	private List<Code> auditMode;
	private List<Code> nodeExecMode;
	private List<Code> isPrivilegeCtrl;
	private Long sourceId;
	private Long targetId;
	private String point;
	private String sourceType;
	private String targetType;
	private Long nodeId;
	private String nodeType;
	private Branch bindBrch;
	private String prodIds;
	private String prodNo;
	private String assignedUser;
	private String assignUserId;
	private String assignRoleId;
	private String prodFlag;
	private List<Code> auditProcessExecResult;
	private List<Code> auditStatus;
	private String globalManager="0";
	/** 是否可以编辑审批路线 */
	private String isEditAuditTag;
	
	/** 提示信息 */
	private String tipMsg;
	
	/**
	 * 审批路线主页面
	 * @return
	 */
	public String auditRouteMain(){
		return AUDIT_ROUTE_MAIN;
	}
	/**
	 * 获取当前用户所在的接入行所有的审批路线
	 * @return
	 */
	public String auditRouteList(){
		String miNo=SessionTool.getUserLogonInfo().getMiNo();
		if( auditRoute == null ){
			auditRoute = new AuditRoute();
		}
		auditRoute.setMiNo(miNo);
		List<AuditRoute> auditRouteList=auditRouteService.findAuditRoute(auditRoute,getPg());
		return setDatagridInputStreamData(auditRouteList, getPg());
	}
	
	/**
	 * 准备添加一条审批路线
	 * @return
	 */
	public String addAuditRoute(){
		auditMode=DictionaryUtil.getCodesByField("AUDIT_ROUTE", "AUDIT_MODE");
		nodeExecMode=DictionaryUtil.getCodesByField("AUDIT_ROUTE", "AN_EXEC_MODE");
		return AUDIT_ROUTE_ADD;
	}
	/**
	 * 保存新增的审批路线
	 */
	public void saveAuditRoute(){
		if(auditRoute!=null){
			UserLogonInfo logonInfo=SessionTool.getUserLogonInfo();
			String miNo=logonInfo.getMiNo();
			if(StringUtils.isNotBlank(miNo)){
				auditRoute.setMiNo(miNo);
			}
			auditRouteService.createAuditRoute(auditRoute);
		}
		
	}
	/**
	 * 准备编辑一条审批路线
	 * @return
	 */
	public String editAuditRoute(){
		auditMode=DictionaryUtil.getCodesByField("AUDIT_ROUTE", "AUDIT_MODE");
		nodeExecMode=DictionaryUtil.getCodesByField("AUDIT_ROUTE", "AN_EXEC_MODE");
		auditRoute=auditRouteService.findAuditRoute(auditRoute.getId());
		return AUDIT_ROUTE_EDIT;
	}
	/**
	 * 修改一条审批路线
	 */
	public void modifyAuditRoute(){
		if(auditRoute!=null){
			auditRouteService.modifyAuditRoute(auditRoute);
		}
	}
	/**
	 * 设置审批路线模板（添加节点、岗位）
	 * @return
	 */
	public String setAuditRouteTemplate(){
		if( auditRoute != null && auditRoute.getId() != null ){
			isEditAuditTag = auditRouteService.isEditAudit(auditRoute.getId()) ? "1" : "0";
		}
		return AUDIT_ROUTE_SET;
	}
	/**
	 * 查询审批路线模板的树性结构（路线-节点-岗位）
	 * @return
	 */
	public String queryAuditRouteTmpTree(){
		List<Map<String,Object>> array=new ArrayList<Map<String,Object>>();
		if(auditRoute!=null){
			auditRoute=auditRouteService.findAuditRoute(auditRoute.getId());
			String nodeMode=auditRoute.getAuditNodeExecMode();
			Map<String,Object> routeMap=new HashMap<String,Object>();
			routeMap.put("id", auditRoute.getId());
			routeMap.put("text", auditRoute.getAuditRouteName());
			routeMap.put("iconCls", "icon-route");
			Map<String,String> routeAttributes=new HashMap<String,String>();
			routeAttributes.put("type", "route");
			routeAttributes.put("miNo", auditRoute.getMiNo());
			routeMap.put("attributes", routeAttributes);
			List<AuditNode> nodes=auditRouteService.findAuditNode(auditRoute);
			if(nodes!=null){
				List<Map<String,Object>> routeChild=new ArrayList<Map<String,Object>>();
				for(AuditNode node:nodes){
					Map<String,Object> nodeMap=new HashMap<String,Object>();
					nodeMap.put("id", node.getId());
					nodeMap.put("text", node.getAuditNodeName());
					String ctrlMode=node.getIsPrivilegeCtrl();
					if("1".equals(nodeMode)){
						nodeMap.put("iconCls", "icon-sync-node");
					}else{
						nodeMap.put("iconCls", "icon-async-node");
					}
					Map<String,String> nodeAttributes=new HashMap<String,String>();
					nodeAttributes.put("type", "node");
					nodeMap.put("attributes", nodeAttributes);
					List<AuditStation> stations=auditRouteService.findAuditStation(node,null);
					if(stations!=null){
						List<Map<String,Object>> nodeChild=new ArrayList<Map<String,Object>>();
						for(AuditStation station:stations){
							Map<String,Object> stationMap=new HashMap<String,Object>();
							stationMap.put("id", station.getId());
							stationMap.put("text", station.getAuditStationName());
							if("1".equals(ctrlMode)){
								stationMap.put("iconCls", "icon-ctrl-station");
							}else{
								stationMap.put("iconCls", "icon-normal-station");
							}
							Map<String,String> stationAttributes=new HashMap<String,String>();
							stationAttributes.put("type", "station");
							stationMap.put("attributes", stationAttributes);
							nodeChild.add(stationMap);
						}
						nodeMap.put("children", nodeChild);
					}
					routeChild.add(nodeMap);
				}
				routeMap.put("children", routeChild);
			}
			array.add(routeMap);
		}
		return setInputStreamData(array);
	}
	/**
	 * 移动审批树形结构（节点间、岗位间）
	 */
	public void move(){
		auditRouteService.moveNodeOrStation(sourceId, sourceType, targetId, targetType, point);
	}
	public String addTemplate(){
		if(nodeId!=null&&nodeType!=null){
			
			if("route".equals(nodeType)){
				auditNode=new AuditNode();
				auditNode.setAuditRouteId(nodeId);
				return addAuditNode();
			}
			if("node".equals(nodeType)){
				auditNode=auditRouteService.findAuditNode(nodeId);
				if(Buser.TYPE_BRCH_GLOBAL_MANAGER.equals(SessionTool.getUserLogonInfo().getUserType())){
					globalManager="1";
				}else{
					globalManager="0";
				}
				
				auditStation=new AuditStation();
				auditStation.setAuditNodeId(auditNode.getId());
				auditStation.setAuditRouteId(auditNode.getAuditRouteId());
				return addAuditStation();
			}
		}
		return null;
	}
	public String add(){
		if(nodeId!=null&&nodeType!=null){
			
			if("route".equals(nodeType)){
				auditNode=new AuditNode();
				auditNode.setAuditRouteId(nodeId);
				return addAuditNode();
			}
			if("node".equals(nodeType)){
				if(Buser.TYPE_BRCH_GLOBAL_MANAGER.equals(SessionTool.getUserLogonInfo().getUserType())){
					globalManager="1";
				}else{
					globalManager="0";
				}
				auditNode=auditRouteService.findAuditNode(nodeId);
				Long brchId=SessionTool.getUserLogonInfo().getBranchId();
				auditStation=new AuditStation();
				auditStation.setAuditNodeId(auditNode.getId());
				auditStation.setAuditRouteId(auditNode.getAuditRouteId());
				if(brchId!=null){
					auditStation.setCreateBrchId(brchId);
				}
				return addAuditStation();
			}
		}
		return null;
	}
	public String edit(){
		if(nodeId!=null&&nodeType!=null){
			if("route".equals(nodeType)){
				auditRoute=auditRouteService.findAuditRoute(nodeId);
				auditMode=DictionaryUtil.getCodesByField("AUDIT_ROUTE", "AUDIT_MODE");
				nodeExecMode=DictionaryUtil.getCodesByField("AUDIT_ROUTE", "AN_EXEC_MODE");
				return AUDIT_ROUTE_EDIT;
			}
			if("node".equals(nodeType)){
				auditNode=auditRouteService.findAuditNode(nodeId);
				return editAuditNode();
			}
			if("station".equals(nodeType)){
				auditStation=auditRouteService.findAuditStation(nodeId);
				if(Buser.TYPE_BRCH_GLOBAL_MANAGER.equals(SessionTool.getUserLogonInfo().getUserType())){
					globalManager="1";
				}else{
					globalManager="0";
				}
				Long brchId=auditStation.getBindBrchId();
				if(brchId!=null){
					bindBrch=branchService.getBranchByBrchId(brchId);
				}
				auditNode=auditRouteService.findAuditNode(auditStation.getAuditNodeId());
				return editAuditStation();
			}
		}
		return null;
	}
	public void delete(){
		if(nodeId!=null&&nodeType!=null){
			if("route".equals(nodeType)){
				auditRoute=new AuditRoute();
				auditRoute.setId(nodeId);
				removeAuditRoute();
			}
			if("node".equals(nodeType)){
				auditNode=new AuditNode();
				auditNode.setId(nodeId);
				removeAuditNode();
			}
			if("station".equals(nodeType)){
				auditStation=new AuditStation();
				auditStation.setId(nodeId);
				removeAuditStation();
			}
		}
	}
	public String view(){
		if(nodeId!=null&&nodeType!=null){
			if("route".equals(nodeType)){
				auditMode=DictionaryUtil.getCodesByField("AUDIT_ROUTE", "AUDIT_MODE");
				nodeExecMode=DictionaryUtil.getCodesByField("AUDIT_ROUTE", "AN_EXEC_MODE");
				auditRoute=auditRouteService.findAuditRoute(nodeId);
				return AUDIT_ROUTE_VIEW;
			}
			if("node".equals(nodeType)){
				auditNode=auditRouteService.findAuditNode(nodeId);
				return viewAuditNode();
			}
			if("station".equals(nodeType)){
				auditStation=auditRouteService.findAuditStation(nodeId);
				Long brchId=auditStation.getBindBrchId();
				if(brchId!=null){
					bindBrch=branchService.getBranchByBrchId(brchId);
				}
				return viewAuditStation();
			}
		}
		return null;
	}
	/**
	 * 准备添加一个审批节点
	 * @return
	 */
	public String  addAuditNode(){
		isPrivilegeCtrl=DictionaryUtil.getCodesByField("AUDIT_NODE", "IS_PRIVILEGE_CTRL");
		return AUDIT_NODE_ADD;
	}
	/**
	 * 保存新增的审批节点
	 */
	public void saveAuditNode(){
		auditRouteService.addAuditNode(auditNode);
	}
	/**
	 * 准备编辑一个审批节点
	 * @return
	 */
	public String editAuditNode(){
		isPrivilegeCtrl=DictionaryUtil.getCodesByField("AUDIT_NODE", "IS_PRIVILEGE_CTRL");
		return AUDIT_NODE_EDIT;
	}
	/**
	 * 修改一个审批节点
	 */
	public void modifyAuditNode(){
		auditRouteService.modifyAuditNode(auditNode);
	}
	/**
	 * 准备添加一个审批岗位
	 * @return
	 */
	public String addAuditStation(){
		return AUDIT_STATION_ADD;
	}
	/**
	 * 保存新增的审批岗位
	 */
	public void saveAuditStation(){
		auditRouteService.addAuditStation(auditStation);
	}
	/**
	 * 准备修改一个审批岗位
	 * @return
	 */
	public String editAuditStation(){
		
		return AUDIT_STATION_EDIT;
	}
	/**
	 * 修改一个 审批岗位
	 */
	public void modifyAuditStation(){
		auditRouteService.modifyAuditStation(auditStation);
	}
	public void removeAuditRoute(){
		auditRouteService.removeAuditRoute(auditRoute);
	}
	public void removeAuditNode(){
		auditRouteService.removeAuditNode(auditNode);
	}
	public void removeAuditStation(){
		auditRouteService.removeAuditStation(auditStation);
	}
	public String viewAuditRoute(){
		auditRoute=auditRouteService.findAuditRoute(auditRoute.getId());
		return AUDIT_ROUTE_VIEW;
	}
	public String viewAuditNode(){
		isPrivilegeCtrl=DictionaryUtil.getCodesByField("AUDIT_NODE", "IS_PRIVILEGE_CTRL");
		return AUDIT_NODE_VIEW;
	}
	public String viewAuditStation(){
		return AUDIT_STATION_VIEW;
	}
	/**
	 * 产品审批路线设置主页面
	 * @return
	 */
	public String setProduct(){
		
		return AUDIT_PRODUCT_MAIN;
	}
	/**
	 * 产品审批设置主页面数据表格数据
	 * @return
	 */
	public String productAuditList(){
		List<Map<String,Object>> productAuditRouteList=auditRouteService.findProductAuditRoute(SessionTool.getUserLogonInfo(),product,getPg());
		return setDatagridInputStreamData(productAuditRouteList, getPg());
	}

	
	/**
	 * 
	 * 为产品分配审批路线
	 *
	 */
	public void assignAuditRouteForProduct(){
		if(prodIds!=null&&auditRoute!=null){
			String[] prodId=prodIds.split(COLON);
			Long brchId=SessionTool.getUserLogonInfo().getBranchId();
			auditRouteService.assignAuditRouteForProduct(prodId,auditRoute,brchId);
		}
	}
	
	/**
	 * 
	 * 取消产品的审批路线
	 *
	 */
	public void cancelAuditRouteForProduct(){
		if( StringUtils.isNotBlank(getIds()) ){
			auditRouteService.cancelAuditRouteForProduct(getIdList());
		}
	}
	
	/**
	 * 
	 * 调整产品审批岗位
	 *
	 * @return
	 */
	public String setProductAuditStation(){
		if( auditRoute != null && auditRoute.getId() != null ){
			if(auditService.hasMulProductBindAuditRoute(auditRoute.getId(), SessionTool.getUserLogonInfo().getBranchId())){
				setTipMsg(getText("mulproduct_bind_auditroute"));
			}
		}
		return PRODUCT_AUDIT_ROUTE_SET;
	}
	/**
	 * 机构在绑定产品和审批路线后，路线和节点与审批模板一致，但审批岗位是一份全新的拷贝
	 * @return
	 */
	public String queryAuditRouteTree(){
		UserLogonInfo logonInfo=SessionTool.getUserLogonInfo();
		List<Map<String,Object>> array=new ArrayList<Map<String,Object>>();
		Long brchId=logonInfo.getBranchId();
		if(auditRoute!=null){
			auditRoute=auditRouteService.findAuditRoute(auditRoute.getId());
			String nodeMode=auditRoute.getAuditNodeExecMode();
			Map<String,Object> routeMap=new HashMap<String,Object>();
			routeMap.put("id", auditRoute.getId());
			routeMap.put("text", auditRoute.getAuditRouteName());
			routeMap.put("iconCls", "icon-route");
			Map<String,String> routeAttributes=new HashMap<String,String>();
			routeAttributes.put("type", "route");
			routeMap.put("attributes", routeAttributes);
			List<AuditNode> nodes=auditRouteService.findAuditNode(auditRoute);
			if(nodes!=null){
				List<Map<String,Object>> routeChild=new ArrayList<Map<String,Object>>();
				for(AuditNode node:nodes){
					Map<String,Object> nodeMap=new HashMap<String,Object>();
					nodeMap.put("id", node.getId());
					nodeMap.put("text", node.getAuditNodeName());
					String ctrlMode=node.getIsPrivilegeCtrl();
					if("1".equals(nodeMode)){
						nodeMap.put("iconCls", "icon-sync-node");
					}else{
						nodeMap.put("iconCls", "icon-async-node");
					}
					Map<String,String> nodeAttributes=new HashMap<String,String> ();
					nodeAttributes.put("type", "node");
					nodeMap.put("attributes", nodeAttributes);
					List<AuditStation> stations=auditRouteService.findAuditStation(node,brchId);
					if(stations!=null){
						List<Map<String,Object>> nodeChild=new ArrayList<Map<String,Object>>();
						for(AuditStation station:stations){
							Map<String,Object> stationMap=new HashMap<String,Object>();
							stationMap.put("id", station.getId());
							stationMap.put("text", station.getAuditStationName());
							if("1".equals(ctrlMode)){
								stationMap.put("iconCls", "icon-ctrl-station");
							}else{
								stationMap.put("iconCls", "icon-normal-station");
							}
							Map<String,String> stationAttributes=new HashMap<String,String>();
							stationAttributes.put("type", "station");
							stationMap.put("attributes", stationAttributes);
							nodeChild.add(stationMap);
						}
						nodeMap.put("children", nodeChild);
					}
					routeChild.add(nodeMap);
				}
				routeMap.put("children", routeChild);
			}
			
			array.add(routeMap);
		}
		return setInputStreamData(array);
	}
	/**
	 * 
	 * 岗位人员分配
	 *
	 * @return
	 */
	public String auditStationUserMain(){
		return AUDIT_STATION_USER_MAIN;
	}
	
	/**
	 * 
	 * 获取岗位列表
	 *
	 * @return
	 */
	public String auditStationUserList(){
		Long brchId=SessionTool.getUserLogonInfo().getBranchId();
		if( auditStation == null ){
			auditStation = new AuditStation();
		}
		auditStation.setBindBrchId(brchId);
		List<Map<String,Object>> resultList=auditRouteService.findBindedAuditStation(auditStation,getPg());
		return setDatagridInputStreamData(resultList, getPg());
	}
	public String assignUserSelect(){
		assignedUser="";
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		List<Buser> list=new ArrayList<Buser>();
		if(auditStation!=null){
			list=auditRouteService.findBuserByAuditStationId(auditStation.getId());
			auditStation=auditRouteService.findAuditStation(auditStation.getId());
		}
		jsonMap.put(TOTAL,list.size());
		jsonMap.put(ROWS,list);
		jsonMap.put(PAGE, getPg());
		assignedUser= getJsonData(jsonMap);
		return USER_MUTIL_SELECT;
	}
	public String assignedUserList(){
		List<Buser> list=new ArrayList<Buser>();
		if(auditStation!=null){
			list=auditRouteService.findBuserByAuditStationId(auditStation.getId());
			
		}
		return setDatagridInputStreamData(list,getPg());
	}
	public String assingUserList(){
		List<Buser> users=new ArrayList<Buser>(0);
		List<Buser> list=new ArrayList<Buser>(0);
		if(auditStation!=null){
			auditStation=auditRouteService.findAuditStation(auditStation.getId());
			list=auditRouteService.findBuserByAuditStationId(auditStation.getId());
			//if(auditStation.getBindBrchId().equals(SessionTool.getUserLogonInfo().getBranchId())){
				users=userService.queryBranchUser(auditStation.getBindBrchId(), null, false,getPg());
				if(list!=null&&list.size()>0){
					for(int i=0;i<list.size();i++){
						Buser u=list.get(i);
						users.remove(u);
					}
				}
			//}
		}
		return setDatagridInputStreamData(users, getPg());
	}
	public String preAssingUserList(){
		List<Buser> users=new ArrayList<Buser>(0);
		List<Buser> list=new ArrayList<Buser>(0);
		if(auditStation!=null){
			auditStation=auditRouteService.findAuditStation(auditStation.getId());
			list=auditRouteService.findBuserByAuditStationId(auditStation.getId());
			//if(auditStation.getBindBrchId().equals(SessionTool.getUserLogonInfo().getBranchId())){
			if(auditStation.getBindBrchId()!=null){
				users=userService.queryBranchUser(auditStation.getBindBrchId(), null, false,getPg());
			}
				if(users.size()>0&&list!=null&&list.size()>0){
					for(int i=0;i<list.size();i++){
						Buser u=list.get(i);
						users.remove(u);
					}
				}
			//}
		}
		return setDatagridInputStreamData(users, getPg());
	}
	public void assignUserSave(){
		String[] userIds = null;
		if( StringUtils.isNotBlank(assignUserId) ){
			userIds=assignUserId.split(COLON);
		}else{
			userIds = new String[0];
		}
		auditRouteService.assignAuditStationWithUsers(userIds,auditStation);
	}
	public void assignUserAdd(){
		String[] uids=new String[0];
		if( StringUtils.isNotBlank(assignUserId)&&auditStation!=null ){
			if(assignUserId.indexOf(",")>0){
				uids=assignUserId.split(",");
			}else  if(assignUserId.indexOf(":")>0){
				uids=assignUserId.split(":");
			}else{
				uids=new String[1];
				uids[0]=assignUserId;
			}
		}else{
			return;
		}
		auditRouteService.addAssignAuditStationWithUsers(uids,auditStation);
	}
	public void assignUserCancel(){
		String[] uids=new String[0];
		if( StringUtils.isNotBlank(assignUserId)&&auditStation!=null ){
			if(assignUserId.indexOf(",")>0){
				uids=assignUserId.split(",");
			}else  if(assignUserId.indexOf(":")>0){
				uids=assignUserId.split(":");
			}else{
				uids=new String[1];
				uids[0]=assignUserId;
			}
		}else{
			return;
		}
		auditRouteService.cancelAssignAuditStationWithUsers(uids,auditStation);
	}
	public String toAssignStation(){
		auditRoute=auditRouteService.findAuditRoute(auditRoute.getId());
		if(nodeId!=null&&nodeType!=null){
			if("station".equals(nodeType)){
				auditStation=auditRouteService.findAuditStation(nodeId);
				Long brchId=auditStation.getBindBrchId();
				if(brchId!=null){
					bindBrch=branchService.getBranchByBrchId(brchId);
				}
			}
		}
		return TO_ASSIGN_STATION;
	}
	public String toPreAssignStation(){
		auditRoute=auditRouteService.findAuditRoute(auditRoute.getId());
		if(nodeId!=null&&nodeType!=null){
			if("station".equals(nodeType)){
				auditStation=auditRouteService.findAuditStation(nodeId);
				Long brchId=auditStation.getBindBrchId();
				if(brchId!=null){
					bindBrch=branchService.getBranchByBrchId(brchId);
				}
			}
		}
		return TO_PRE_ASSIGN_STATION;
	}
	public String assignedRoleList(){
		List<Role> list=new ArrayList<Role>();
		if(auditStation!=null){
			list=auditRouteService.findRoleByAuditStationId(auditStation.getId());
			
		}
		return setDatagridInputStreamData(list,getPg());
	}
	public String assingRoleList(){
		List<Role> roles=new ArrayList<Role>(0);
		List<Role> list=new ArrayList<Role>(0);
		if(auditStation!=null){
			auditStation=auditRouteService.findAuditStation(auditStation.getId());
			list=auditRouteService.findRoleByAuditStationId(auditStation.getId());
			roles=roleService.findAvailableRolesByBranch(auditStation.getBindBrchId(), true, null);
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Role u=list.get(i);
					roles.remove(u);
				}
			}
		}
		return setDatagridInputStreamData(roles, getPg());
	}
	public String preAssingRoleList(){
		List<Role> roles=new ArrayList<Role>(0);
		List<Role> list=new ArrayList<Role>(0);
		if(auditStation!=null){
			auditStation=auditRouteService.findAuditStation(auditStation.getId());
			list=auditRouteService.findRoleByAuditStationId(auditStation.getId());
			if(auditStation.getBindBrchId()!=null){
				roles=roleService.findAvailableRolesByBranch(auditStation.getBindBrchId(), true, null);
			}else{
				String miNo=SessionTool.getUserLogonInfo().getMiNo();
				Branch hqBrch=branchService.getHQBranchByMino(miNo);
				roles=roleService.findAvailableRolesByBranch(hqBrch.getBrchId(), false, null);
			}
			
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Role u=list.get(i);
					roles.remove(u);
				}
			}
		}
		return setDatagridInputStreamData(roles, getPg());
	}
	public void assignRoleAdd(){
		String[] rids=new String[0];
		if( StringUtils.isNotBlank(assignRoleId)&&auditStation!=null ){
			if(assignRoleId.indexOf(",")>0){
				rids=assignRoleId.split(",");
			}else  if(assignRoleId.indexOf(":")>0){
				rids=assignRoleId.split(":");
			}else{
				rids=new String[1];
				rids[0]=assignRoleId;
			}
		}else{
			return;
		}
		auditRouteService.addAssignAuditStationWithRoles(rids,auditStation);
	}
	public void assignRoleCancel(){
		String[] rids=new String[0];
		if( StringUtils.isNotBlank(assignRoleId)&&auditStation!=null ){
			if(assignRoleId.indexOf(",")>0){
				rids=assignRoleId.split(",");
			}else  if(assignRoleId.indexOf(":")>0){
				rids=assignRoleId.split(":");
			}else{
				rids=new String[1];
				rids[0]=assignRoleId;
			}
		}else{
			return;
		}
		auditRouteService.cancelAssignAuditStationWithRoles(rids,auditStation);
	}
	public String queryRouteStations(){
		List<AuditStation> list=new ArrayList<AuditStation>(0);
		if(auditRoute!=null){
			list=auditRouteService.findAuditStationByAuditRouteId(auditRoute.getId(),SessionTool.getUserLogonInfo().getBranchId());
		}
		return setDatagridInputStreamData(list, getPg());
	}
	public String queryRouteStationsTemplate(){
		List<AuditStation> list=new ArrayList<AuditStation>(0);
		if(auditRoute!=null){
			list=auditRouteService.findAuditStationTemplateByAuditRouteId(auditRoute.getId());
		}
		return setDatagridInputStreamData(list, getPg());
	}
	public String viewAssignedUser(){
		List<Buser> list=new ArrayList<Buser>(0);
		if(auditStation!=null){
			list=auditRouteService.findBuserByAuditStationIdContainRole(auditStation.getId());
		}
		return setDatagridInputStreamData(list, getPg());
	}
	/*---------产品审批-------*/
	/**
	 * 产品审批主页面
	 */
	public String auditMain(){
		return AUDIT_MAIN;
	}
	/**
	 * 产品审批主页面审批列表
	 * @return
	 */
	public String auditList(){
		List<Map<String,Object>> result=auditService.queryCanOperateAuditList(SessionTool.getUserLogonInfo(),getPg(),prodNo);
		return setDatagridInputStreamData(result, getPg());
	}
	/**
	 * 审批受理
	 */
	public void acceptProcess(){
		auditService.acceptAuditProcess(SessionTool.getUserLogonInfo(), getIdList());
	}
	/**
	 * 撤销审批受理
	 */
	public void revokeProcess(){
		auditService.revokeAcceptAuditProcess(SessionTool.getUserLogonInfo(), getIdList());
	}
	/**
	 * 审批
	 * @return
	 */
	public String audit(){
		auditTask=auditService.getAuditTask(auditTask.getId());
		auditProcess=auditService.getauditProcess(auditProcess.getId());
		if( auditProcess != null && StringUtils.equals(AuditProcess.STATUS_UN_ACCEPT, auditProcess.getAuditProcessStatus()) ){
			auditService.acceptAuditProcess(SessionTool.getUserLogonInfo(), auditProcess);
			auditProcess=auditService.getauditProcess(auditProcess.getId());
		}
		product=productService.getProduct(auditTask.getProdId());
		if(StringUtil.isEmpty(product.getProdUrl())){
			product.setProdUrl(AuditConfig.getAuditUrlByProdNo(product.getProdNo()));
		}
		auditProcessExecResult=DictionaryUtil.getCodesByKey(AuditProcess.CODE_ACCEPT_RESULT);
		return AUDIT;
	}
	public String queryProcess(){
		List<Map<String,Object>> list= new ArrayList<Map<String,Object>>();
		if(auditTask!=null&&auditTask.getId()!=null){
			list=auditService.queryAllAuditProcess(auditTask.getId());
		}

		return setDatagridInputStreamData(list, getPg());
	}

	public void commitAuditResult(){
		auditService.commitAuditProcess(auditProcess);
	}
	public String saveAuditResult(){
		auditService.saveAuditResult(auditProcess);
		auditProcess=auditService.getauditProcess(auditProcess.getId());
		return setInputStreamData(auditProcess);
	}
	public void saveAndCommitAuditResult(){
		auditService.saveAndCommitAuditResult(auditProcess);
	}
	public String queryAuditHist(){
		if(product!=null&&product.getProdNo()!=null){
			product=productService.getProductByProdNo(product.getProdNo());
			if(product!=null){
				auditHistSearch.setProdId(product.getId());
			}
		}
		UserLogonInfo logonInfo=SessionTool.getUserLogonInfo();
		auditStatus=DictionaryUtil.getCodesByKey(AuditTask.CODE_AUDIT_STATUS);
		if(Buser.TYPE_BRCH_NOMAL_USER.equals(logonInfo.getUserType())){
			if(auditHistSearch==null){
				auditHistSearch=new AuditHistSearchBean();
			}
			auditHistSearch.setBrchId(logonInfo.getBranchId());
			auditHistSearch.setAuditActor(logonInfo.getSysUserId());
		}
		return AUDIT_TASK_HIST;
	}
	public String auditHistList(){
		UserLogonInfo logonInfo=SessionTool.getUserLogonInfo();
		if(auditHistSearch==null){
			auditHistSearch=new AuditHistSearchBean();
		}
		if(Buser.TYPE_BRCH_NOMAL_USER.equals(logonInfo.getUserType())){
			if(auditHistSearch.getAuditActor()==null){
				auditHistSearch.setAuditActor(logonInfo.getSysUserId());
			}
		}else{
			if(auditHistSearch.getBrchId()==null||auditHistSearch.getBrchId().equals(0L)){
				auditHistSearch.setBrchId(logonInfo.getBranchId());
			}
		}
		if(product!=null&&product.getProdNo()!=null){
			product=productService.getProductByProdNo(product.getProdNo());
			if(product!=null){
				auditHistSearch.setProdId(product.getId());
			}
		}
		if( auditHistSearch.getEndDate() != null ){
			Calendar cal = Calendar.getInstance();
			cal.setTime(auditHistSearch.getEndDate());
			cal.add(Calendar.DAY_OF_MONTH, 1);
			auditHistSearch.setEndDate(cal.getTime());
		}
		String[] status=getHttpRequest().getParameterValues("auditHistSearch.status[]");
		if(status!=null&&status.length>0&&status[0].length()>0){
			String tmp="";
			for(String st:status){
				if(tmp.length()>0){
					tmp+=",";
				}
				tmp+="'"+st+"'";
			}
			auditHistSearch.setStatus(tmp);
		}
		List<Map<String,Object>> auditHist=auditService.queryAuditHist(auditHistSearch,getPg());
//		Map<String,Object> jsonMap = new HashMap<String,Object>();
//		jsonMap.put(TOTAL,getPg().getTotalRows());
//		jsonMap.put(ROWS,auditHist);

		return setDatagridInputStreamData(auditHist, getPg());
	}
	public String viewAuidtProcess(){
		return VIEW_AUDIT_PROCESS;
	}
	/*-------get/set--------*/
	public IAuditRouteService getAuditRouteService() {
		return auditRouteService;
	}
	public void setAuditRouteService(IAuditRouteService auditRouteService) {
		this.auditRouteService = auditRouteService;
	}
	public AuditRoute getAuditRoute() {
		return auditRoute;
	}
	public void setAuditRoute(AuditRoute auditRoute) {
		this.auditRoute = auditRoute;
	}
	public List<Code> getAuditMode() {
		return auditMode;
	}
	public void setAuditMode(List<Code> auditMode) {
		this.auditMode = auditMode;
	}
	public List<Code> getNodeExecMode() {
		return nodeExecMode;
	}
	public void setNodeExecMode(List<Code> nodeExecMode) {
		this.nodeExecMode = nodeExecMode;
	}
	public Long getSourceId() {
		return sourceId;
	}
	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}
	public Long getTargetId() {
		return targetId;
	}
	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public AuditNode getAuditNode() {
		return auditNode;
	}
	public void setAuditNode(AuditNode auditNode) {
		this.auditNode = auditNode;
	}
	public AuditStation getAuditStation() {
		return auditStation;
	}
	public void setAuditStation(AuditStation auditStation) {
		this.auditStation = auditStation;
	}
	public List<Code> getIsPrivilegeCtrl() {
		return isPrivilegeCtrl;
	}
	public void setIsPrivilegeCtrl(List<Code> isPrivilegeCtrl) {
		this.isPrivilegeCtrl = isPrivilegeCtrl;
	}
	public IBranchService getBranchService() {
		return branchService;
	}
	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}
	public Branch getBindBrch() {
		return bindBrch;
	}
	public void setBindBrch(Branch bindBrch) {
		this.bindBrch = bindBrch;
	}
	public String getProdIds() {
		return prodIds;
	}
	public void setProdIds(String prodIds) {
		this.prodIds = prodIds;
	}
	public String getAssignedUser() {
		return assignedUser;
	}
	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}
	public IUserService getUserService() {
		return userService;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public String getAssignUserId() {
		return assignUserId;
	}
	public void setAssignUserId(String assignUserId) {
		this.assignUserId = assignUserId;
	}
	public IAuditService getAuditService() {
		return auditService;
	}
	public void setAuditService(IAuditService auditService) {
		this.auditService = auditService;
	}
	public AuditProcess getAuditProcess() {
		return auditProcess;
	}
	public void setAuditProcess(AuditProcess auditProcess) {
		this.auditProcess = auditProcess;
	}
	public void setProductService(IProductService productService) {
		this.productService = productService;
	}
	public AuditTask getAuditTask() {
		return auditTask;
	}
	public void setAuditTask(AuditTask auditTask) {
		this.auditTask = auditTask;
	}
	public ProductInfo getProduct() {
		return product;
	}
	public void setProduct(ProductInfo product) {
		this.product = product;
	}
	public List<Code> getAuditProcessExecResult() {
		return auditProcessExecResult;
	}
	public void setAuditProcessExecResult(List<Code> auditProcessExecResult) {
		this.auditProcessExecResult = auditProcessExecResult;
	}
	public AuditHistSearchBean getAuditHistSearch() {
		return auditHistSearch;
	}
	public void setAuditHistSearch(AuditHistSearchBean auditHistSearch) {
		this.auditHistSearch = auditHistSearch;
	}
	public List<Code> getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(List<Code> auditStatus) {
		this.auditStatus = auditStatus;
	}
	public String getProdFlag() {
		return prodFlag;
	}
	public void setProdFlag(String prodFlag) {
		this.prodFlag = prodFlag;
	}
	public String getProdNo() {
		return prodNo;
	}
	public void setProdNo(String prodNo) {
		this.prodNo = prodNo;
	}
	public String getIsEditAuditTag() {
		return isEditAuditTag;
	}
	public void setIsEditAuditTag(String isEditAuditTag) {
		this.isEditAuditTag = isEditAuditTag;
	}
	public String getTipMsg() {
		return tipMsg;
	}
	public void setTipMsg(String tipMsg) {
		this.tipMsg = tipMsg;
	}
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
	public String getAssignRoleId() {
		return assignRoleId;
	}
	public void setAssignRoleId(String assignRoleId) {
		this.assignRoleId = assignRoleId;
	}
	public String getGlobalManager() {
		return globalManager;
	}
	public void setGlobalManager(String globalManager) {
		this.globalManager = globalManager;
	}

}
