package com.upg.ucars.basesystem.security.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.basesystem.security.core.role.IRoleService;
import com.upg.ucars.basesystem.security.core.role.RoleConstant;
import com.upg.ucars.basesystem.security.core.sysfunc.ISysfuncService;
import com.upg.ucars.basesystem.security.core.user.UserManager;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.security.SecurityResources;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.security.Role;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.StringUtil;


public class RoleAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6580671753965911050L;
	/** 角色实体 */
	private Role role;
	/** 角色ID */
	private Long roleId;
	/** 角色服务 */
	private IRoleService roleService;
	/** 权限服务 */
	private ISysfuncService sysfuncService;
	/** 角色类型列表 */
	private List<Code> roleTypeList;
	/** 用户登录信息 */
	private UserLogonInfo userLogonInfo;
	/** 已设置的权限ID */
	private String beIds;
	/** 未设置的权限ID */
	private String unIds;
	/** 权限ID */
	private String funcIds;
	private boolean showBrch;
	
	/**
	 * 
	 * 角色管理主页面
	 *
	 * @return
	 */
	public String mainPage(){
		setShowBrch(!(UserManager.isImplementation() || UserManager.isSaasManager() || UserManager.isSaasMaintenance()));
		return MAIN;
	}
	
	/**
	 * 
	 * 角色列表
	 *
	 * @return
	 */
	public String listRoles(){
		userLogonInfo = SessionTool.getUserLogonInfo();
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
		ConditionBean cb = new ConditionBean("role.brchId",userLogonInfo.getBranchId());
		conditionList.add(cb);	
		
		if(role!=null&&role.getRoleName()!=null&&role.getRoleName().length()>0){
			ConditionBean cb2 = new ConditionBean("role.roleName",ConditionBean.LIKE,role.getRoleName().trim());
			conditionList.add(cb2);	
		}
		
		List<String> roleQueryTypeList = getRoleType();
		List<Role> roleList = roleService.queryRolesByType(roleQueryTypeList.toArray(new String[0]), conditionList,getPg());
		return setDatagridInputStreamData(roleList, getPg());
	}
	
	/**
	 * 
	 * 获取角色类型
	 *
	 * @return
	 */
	private List<String> getRoleType(){
		List<String> ret = new ArrayList<String>();
		if( UserManager.isImplementation() ){
			ret.add(RoleConstant.ROLE_TYPE_SAAS_MNG);
			ret.add(RoleConstant.ROLE_TYPE_SAAS_OPER);
		}else if ( UserManager.isSaasManager() || UserManager.isSaasMaintenance() ){
			ret.add(RoleConstant.ROLE_TYPE_CENTRE);
		}else if ( UserManager.isBrchGlobalManager() || UserManager.isBrchLocalManager() ){
			ret.add(RoleConstant.ROLE_TYPE_BRANCH);
			ret.add(RoleConstant.ROLE_TYPE_COMMON);
			ret.add(RoleConstant.ROLE_TYPE_JUDGMENT);
		}
		return ret;
	}
	
	/**
	 * 
	 * 增加页面
	 *
	 * @return
	 */
	public String toAdd() {
		List<Code> typeList  = DictionaryUtil.getCodesByField("role", "role_type");
		roleTypeList  =new ArrayList<Code>();
		List<String> rtl = getRoleType();
		for( Code code : typeList ){
			if( rtl.contains(code.getCodeNo()) ){
				roleTypeList.add(code);
			}
		}
		return ADD;
	}
	
	/**
	 * 
	 * 增加
	 *
	 */
	public void addRole() {
		UserLogonInfo logoner = SessionTool.getUserLogonInfo();
		role.setBrchId(logoner.getBranchId());
		roleService.saveRole(role);
	}
	
	/**
	 * 
	 *	删除角色
	 *
	 * @return
	 */
	public void deleteRole() {
		roleService.deleteByIdList(getIdList());
	}
	
	/**
	 * 
	 * 编辑页面
	 *
	 * @return
	 */
	public String toEdit() {
		role = roleService.getRoleById(this.getPKId());
		List<Code> typeList  = DictionaryUtil.getCodesByField("role", "role_type");
		roleTypeList  =new ArrayList<Code>();
		List<String> rtl = getRoleType();
		for( Code code : typeList ){
			if( rtl.contains(code.getCodeNo()) ){
				roleTypeList.add(code);
			}
		}
		return EDIT;
	}
	
	/**
	 * 
	 * 编辑
	 *
	 */
	public void updateRole() {
		role.setBrchId(SessionTool.getUserLogonInfo().getBranchId());
		roleService.updateRole(role);
	}
	
	/**
	 * 
	 * 分配权限页面
	 *
	 * @return
	 */
	public String roleFuncMain() {
		return "roleFuncMain";
	}
	
	
	/**
	 * 
	 * 获取权限数据
	 *
	 * @return
	 */
	public String roleFuncTree(){
		Map<String,Object> map=new HashMap<String,Object>();
		UserLogonInfo logoner = SessionTool.getUserLogonInfo();
		Role role=roleService.getRoleById(getRoleId());
		List<Sysfunc> allFuncList = roleService.findAllowAssignFunction(role.getRoleType(),logoner);
		List<Sysfunc> beFuncList = roleService.findSysfuncByRoleId(this.getRoleId());
		map=sysfuncService.getTwoTree(allFuncList, beFuncList);
		return setInputStreamData(map);
	}
	/**
	 * 添加权限
	 * @return
	 */
	public String addFunc(){
		if(funcIds!=null){
			List<Long> funcIdList = new ArrayList<Long>();
			
			String[] ids = funcIds.split(",");
			for (String id : ids) {
				if (!StringUtil.isEmpty(id))
					funcIdList.add(Long.valueOf(id));
			}
			roleService.addFunc(roleId,funcIdList);
			updateNewFuncPrivilege(roleId);
		}
		return roleFuncTree();
	}
	/**
	 * 角色与权限变化后，需要维护服务器当前配置
	 * @param roleId
	 */
	private void updateNewFuncPrivilege(Long roleId){
		//新绑定角色与权限的时候，需要更新缓存中的绑定关系
		List<Sysfunc> userFuncs=roleService.findSysfuncByRoleId(roleId);
		Set<String> privileges=new HashSet<String>();
		for(Sysfunc func:userFuncs){
			if(!StringUtil.isEmpty(func.getUrl())){
				privileges.add(func.getUrl());
			}
		}
		//更新权限资源
		SecurityResources.updateResourcesSecurity(privileges, roleId);
	}
	/**
	 * 删除权限
	 * @return
	 */
	public String delFunc(){
		if(funcIds!=null){
			List<Long> funcIdList = new ArrayList<Long>();
			
			String[] ids = funcIds.split(",");
			for (String id : ids) {
				if (!StringUtil.isEmpty(id))
					funcIdList.add(Long.valueOf(id));
			}
			roleService.removeFunc(roleId,funcIdList);
			updateNewFuncPrivilege(roleId);
		}
		return roleFuncTree();
	}
	
	/**
	 * 
	 * 保存角色权限关系
	 *
	 * @return
	 */
	public String saveRoleFunc(){
		
		List<Long> beFuncIdList = new ArrayList<Long>();
		
		String[] ids = this.getBeIds().split(",");
		for (String id : ids) {
			if (!StringUtil.isEmpty(id))
				beFuncIdList.add(Long.valueOf(id));
		}
		
		roleService.buildRoleSysfuncs(roleId, beFuncIdList);
		updateNewFuncPrivilege(roleId);
		return null;		
	}

	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
	public List<Code> getRoleTypeList() {
		return roleTypeList;
	}
	public String getBeIds() {
		return beIds;
	}
	public void setBeIds(String beIds) {
		this.beIds = beIds;
	}
	public String getUnIds() {
		return unIds;
	}
	public void setUnIds(String unIds) {
		this.unIds = unIds;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public UserLogonInfo getUserLogonInfo() {
		return userLogonInfo;
	}
	public void setUserLogonInfo(UserLogonInfo userLogonInfo) {
		this.userLogonInfo = userLogonInfo;
	}
	public void setSysfuncService(ISysfuncService sysfuncService) {
		this.sysfuncService = sysfuncService;
	}
	public String getFuncIds() {
		return funcIds;
	}
	public void setFuncIds(String funcIds) {
		this.funcIds = funcIds;
	}
	public void setRoleTypeList(List<Code> roleTypeList) {
		this.roleTypeList = roleTypeList;
	}
	public void setShowBrch(boolean showBrch){
		this.showBrch=showBrch;
	}
	public boolean getShowBrch(){
		return showBrch;
	}
}
