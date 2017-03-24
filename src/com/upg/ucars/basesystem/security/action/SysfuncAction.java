package com.upg.ucars.basesystem.security.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.basesystem.security.core.role.IRoleService;
import com.upg.ucars.basesystem.security.core.sysfunc.FuncType;
import com.upg.ucars.basesystem.security.core.sysfunc.ISysfuncService;
import com.upg.ucars.constant.CodeKey;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.RightsException;
import com.upg.ucars.framework.security.SecurityResources;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.StringUtil;



/**
 * 
 * 功能说明：系统权限控制器
 * @author shentuwy  
 * @author shentuwy
 * @date 2011-5-24 下午02:22:05 
 *
 */
public class SysfuncAction extends BaseAction {
	
	private static final long serialVersionUID = -2555832610960339734L;
	
	private static final String TO_MAIN="main";
	private static final String TO_EDIT="edit";
	private static final String TO_VIEW="view";
	private static final String QUERY_LIST="list";
	private Sysfunc sysfunc=null;
	private Sysfunc parentFunc=null;
	private List<Code> funcType = new ArrayList<Code>();
	private List<Code> useType;
	private Long funcId;
	private String id;
	private ISysfuncService sysfuncService;
	private IRoleService roleService;
	private Long sourceId;
	private Long targetId;
	private String point;
	private boolean hasSubSysfunc = false;
	/**
	 * 定义权限主页面
	 * @return
	 * @throws Exception
	 */
	public String mainPage(){
		
		return TO_MAIN;
	}
	/**
	 * 添加权限
	 * @return
	 * @throws Exception
	 */
	public String add(){
		parentFunc=sysfuncService.getSysfunc(funcId);
		if(parentFunc.isFunc())
			ExceptionManager.throwException(RightsException.class,ErrorCodeConst.SECURITY_SYSFUNC_ADD_016);
		List<Code> codes =DictionaryUtil.getCodesByKey(Sysfunc.CODE_FUNC_TYPE);
		if(parentFunc.isMenu()){
			for(Code code : codes){
				if(Sysfunc.FUNC_TYPE_FUNC.equals(code.getCodeNo())){
					funcType.add(code);
					break;
				}
			}
		}
		if(parentFunc.isMenuGroup()){
			for(Code code : codes){
				if(Sysfunc.FUNC_TYPE_MENU.equals(code.getCodeNo()) || Sysfunc.FUNC_TYPE_MENU_GROUP.equals(code.getCodeNo())){
					funcType.add(code);
				}
			}
		}
		useType=DictionaryUtil.getCodesByKey(Sysfunc.CODE_USE_TYPE);
		return ADD;
	}
	/**
	 * 编辑权限
	 * @return
	 * @throws Exception
	 */
	public String edit(){
		sysfunc = sysfuncService.getSysfunc(funcId);
		hasSubSysfunc = sysfuncService.hasSubFunc(funcId);
		funcType = DictionaryUtil.getCodesByKey(Sysfunc.CODE_FUNC_TYPE);
		Code find_code = null;
		if(!hasSubSysfunc){//不含子节点，并且是菜单或菜单组类型，则可以有菜单，菜单组类型下拉列表
			if(sysfunc.isMenu() || sysfunc.isMenuGroup()){
				for(Code code : funcType){
					if(Sysfunc.FUNC_TYPE_FUNC.equals(code.getCodeNo())){
						find_code = code;
						break;
					}
				}
				funcType.remove(find_code);//不包含功能类型
			}else{
				for(Code code : funcType){
					if(Sysfunc.FUNC_TYPE_FUNC.equals(code.getCodeNo())){
						find_code = code;
						break;
					}
				}
				funcType.clear();
				funcType.add(find_code);
			}
		}else{
			for(Code code : funcType){
				if(sysfunc.getFuncType().equals(code.getCodeNo())){
					find_code = code;
					break;
				}
			}
			funcType.clear();
			funcType.add(find_code);
		}
		useType=DictionaryUtil.getCodesByKey(Sysfunc.CODE_USE_TYPE);
		return TO_EDIT;
	}
	/**
	 * 查看权限
	 * @return
	 */
	public String view(){
		funcType=DictionaryUtil.getCodesByKey(CodeKey.SYSFUNC_FUNC_TYPE);
		useType=DictionaryUtil.getCodesByKey(Sysfunc.CODE_USE_TYPE);
		sysfunc=sysfuncService.getSysfunc(funcId);
		return TO_VIEW;
	}
	/**
	 * 删除权限
	 * @throws Exception
	 */
	public void delete(){
		sysfuncService.removeSysfunc(funcId);
	}
	/**
	 * 保存
	 * @throws Exception
	 */
	public void save(){
		Long parentFuncId = parentFunc.getFuncId();
		Sysfunc l_parentFunc = sysfuncService.getSysfunc(parentFuncId);
		String l_functype = sysfunc.getFuncType();
		//如果父节点是菜单组,子节点又为功能权限类型，则抛出异常
		if(l_parentFunc.isMenuGroup() && Sysfunc.FUNC_TYPE_FUNC.equals(l_functype)){
			ExceptionManager.throwException(RightsException.class,ErrorCodeConst.SECURITY_SYSFUNC_ADD_017);
		}
		//如果父节点是菜单,子节点仅能为功能权限类型
		if(l_parentFunc.isMenu() && !Sysfunc.FUNC_TYPE_FUNC.equals(l_functype)){
			ExceptionManager.throwException(RightsException.class,ErrorCodeConst.SECURITY_SYSFUNC_ADD_018);
		}
		//如果是菜单或是功能权限类型，则url不能为空
		if(Sysfunc.FUNC_TYPE_FUNC.equals(l_functype) || Sysfunc.FUNC_TYPE_MENU.equals(l_functype)){
			if(StringUtil.isEmpty(sysfunc.getUrl())){
				ExceptionManager.throwException(RightsException.class,ErrorCodeConst.SECURITY_SYSFUNC_ADD_019);
			}
		}else
			sysfunc.setUrl(null);
		sysfunc.setParentFuncId(parentFuncId);
		sysfuncService.createSysfunc(sysfunc);
		if(!StringUtil.isEmpty(sysfunc.getUrl())){
			SecurityResources.addSecurityResource(sysfunc.getUrl());
		}
	}
	/**
	 * 更新
	 * @throws Exception
	 */
	public void update(){
		Sysfunc tmp = sysfuncService.getSysfunc(sysfunc.getFuncId());
		hasSubSysfunc = sysfuncService.hasSubFunc(sysfunc.getFuncId());
		//如果包含子节点，权限类型不能修改,否则抛出异常
		if(hasSubSysfunc && !tmp.getFuncType().equals(sysfunc.getFuncType())){
			ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_EDIT_TYPE_NOT_ALLOW);
		}
		//如果是功能或菜单类型则url不允许为空,否则抛出异常
		if(sysfunc.isFunc() || sysfunc.isMenu()){
			if(StringUtil.isEmpty(sysfunc.getUrl()))
				ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_SYSFUNC_EDIT_URL_NOT_EMPTY);
		}else
			sysfunc.setUrl(null);
		String oldUrl=tmp.getUrl();
		sysfuncService.modifySysfunc(sysfunc);
		if(oldUrl != null){
			if(!oldUrl.equals(sysfunc.getUrl())){
				List<Long> roleIds=roleService.findRoleIdsByFuncId(tmp.getFuncId());
				Set<Long> roleSet=new HashSet<Long>();
				roleSet.addAll(roleIds);
				SecurityResources.updateResourcesSecurity(sysfunc.getUrl(), roleSet);
			}
		}
	}
	
	public String queryList(){
		return QUERY_LIST;
	}
	/**
	 * 移动权限关系
	 */
	public void move(){
		sysfuncService.moveFunc(sourceId, targetId, point);
	}
	/**
	 * 查询菜单
	 * @return
	 * @throws Exception
	 */
	public String queryMenu()throws Exception{
		UserLogonInfo logonInfo=SessionTool.getUserLogonInfo();
		List<Map<String,Object>> menus=new ArrayList<Map<String,Object>>();
		List<Sysfunc> nodes=null;
		if(id!=null){
			nodes=sysfuncService.getSubMenu(logonInfo,Long.valueOf(id));
		}else{
			if(funcId!=null){
				nodes=sysfuncService.getSubMenu(logonInfo,Long.valueOf(funcId));
			}else{
				nodes=sysfuncService.getSubMenu(logonInfo,Sysfunc.ROOT_FUNC_ID);
			}
			
		}
		for(Sysfunc node:nodes){
			Map<String,Object> item=new HashMap<String,Object>();
			item.put("id", node.getFuncId());
			String funcName=node.getFuncName();
			String nameKey=node.getFuncNameKey();
			if(nameKey!=null&&nameKey.length()>0){
				String keyValue=getText(nameKey);
				if(!nameKey.equals(keyValue)){
					funcName=keyValue;
				}
			}
			item.put("text", funcName);
			Map<String,Object> attrMap=new HashMap<String,Object>();
			attrMap.put("url", node.getUrl());
			item.put("attributes", attrMap);
			if(funcId!=null){
//				if(sysfuncService.hasSubMenu(logonInfo, node.getFuncId())){
//					item.put("state", "closed");
//				}
				//更换为，如果url为空或者长度小于3，则认为是菜单组
				if(StringUtil.isEmpty(node.getUrl())||node.getUrl().length()<2){
					item.put("state", "closed");
				}
			}
			
			menus.add(item);
		}
		return setInputStreamData(menus);
	}
	/**
	 * 查询Z菜单
	 * @return
	 * @throws Exception
	 */
	public String queryZMenu()throws Exception{
		UserLogonInfo logonInfo=SessionTool.getUserLogonInfo();
		List<Map<String,Object>> menus=new ArrayList<Map<String,Object>>();
		List<Sysfunc> nodes=null;
		if(id!=null){
			nodes=sysfuncService.getSubMenu(logonInfo,Long.valueOf(id));
		}else{
			if(funcId!=null){
				nodes=sysfuncService.getSubMenu(logonInfo,Long.valueOf(funcId));
			}else{
				nodes=sysfuncService.getSubMenu(logonInfo,Sysfunc.ROOT_FUNC_ID);
			}
			
		}
		for(Sysfunc node:nodes){
			Map<String,Object> item=new HashMap<String,Object>();
			item.put("id", node.getFuncId());
			String funcName=node.getFuncName();
			String nameKey=node.getFuncNameKey();
			if(nameKey!=null&&nameKey.length()>0){
				String keyValue=getText(nameKey);
				if(!nameKey.equals(keyValue)){
					funcName=keyValue;
				}
			}
			item.put("name", funcName);
			item.put("icon", "");
			item.put("targeturl", node.getUrl());
			if(funcId!=null){
				if(StringUtil.isEmpty(node.getUrl())||node.getUrl().length()<2){
					item.put("isParent", "true");
				}
			}
			
			menus.add(item);
		}
		return setInputStreamData(menus);
	}
	public String getUserMenu()throws Exception{
		UserLogonInfo logonInfo=SessionTool.getUserLogonInfo();
		List<Sysfunc> nodes=sysfuncService.getMenu(logonInfo);
		return setInputStreamData(Sysfunc.buildCloseTree(nodes));
	}
	/**
	 * 查询权限
	 * @return
	 * @throws Exception
	 */
	public String queryFunc()throws Exception{
		List<Map<String,Object>> funcs=new ArrayList<Map<String,Object>>();
		List<Sysfunc> nodes=null;
		if(id==null||id.length()<1){
			nodes=sysfuncService.getSubFunc(null);
		}else{
			nodes=sysfuncService.getSubFunc(Long.valueOf(id));
		}
		
		for(Sysfunc node:nodes){
			Map<String,Object> item=new HashMap<String,Object>();
			item.put("id", node.getFuncId());
			String funcName=node.getFuncName();
			String nameKey=node.getFuncNameKey();
			if(nameKey!=null&&nameKey.length()>0){
				if(!nameKey.equals(getText(nameKey))){
					funcName=getText(nameKey);
				}
			}
			item.put("text", funcName);
			String funcType=node.getFuncType();
			if(FuncType.FUNC.equals(funcType)){
				item.put("iconCls","icon-func");
			}else{
				item.put("iconCls","icon-menu");
			}
			Map<String,Object> attrMap=new HashMap<String,Object>();
			attrMap.put("url", node.getUrl());
			attrMap.put("funcId", node.getFuncId());
			item.put("attributes", attrMap);
			if(sysfuncService.hasSubFunc(node.getFuncId()) || StringUtils.equals(FuncType.GROUP, node.getFuncType()) ){
				item.put("state", "closed");
			}
			funcs.add(item);
		}
		return setInputStreamData(funcs);
	}
	/**
	 * 获取用户的可访问的url和需要权限控制的url
	 * @return
	 * @throws Exception
	 */
	public String getACL()throws Exception{
		Set<String> allPrivilege=SecurityResources.getAllPrivilege();
		List userFuncs=new ArrayList();
		UserLogonInfo logonInfo=SessionTool.getUserLogonInfo();
		if(logonInfo!=null){
			List<Sysfunc> tmp=sysfuncService.getUserFunc(logonInfo);
			if(tmp!=null){
				for(Sysfunc func:tmp){
					userFuncs.add(func.getUrl());
				}
			}
		}
		Map<String,Object> item=new HashMap<String,Object>();
		item.put("allAuth", allPrivilege);
		item.put("userAuth", userFuncs);
		return setInputStreamData(item);
	}
	public Sysfunc getSysfunc() {
		return sysfunc;
	}
	public void setSysfunc(Sysfunc sysfunc) {
		this.sysfunc = sysfunc;
	}
	public Long getFuncId() {
		return funcId;
	}
	public void setFuncId(Long funcId) {
		this.funcId = funcId;
	}
	public ISysfuncService getSysfuncService() {
		return sysfuncService;
	}
	public void setSysfuncService(ISysfuncService sysfuncService) {
		this.sysfuncService = sysfuncService;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Sysfunc getParentFunc() {
		return parentFunc;
	}
	public void setParentFunc(Sysfunc parentFunc) {
		this.parentFunc = parentFunc;
	}
	public List<Code> getFuncType() {
		return funcType;
	}
	public void setFuncType(List<Code> funcType) {
		this.funcType = funcType;
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
	public List<Code> getUseType() {
		return useType;
	}
	public void setUseType(List<Code> useType) {
		this.useType = useType;
	}
	public IRoleService getRoleService() {
		return roleService;
	}
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
	public boolean isHasSubSysfunc() {
		return hasSubSysfunc;
	}
	public void setHasSubSysfunc(boolean hasSubSysfunc) {
		this.hasSubSysfunc = hasSubSysfunc;
	}
	
	
}