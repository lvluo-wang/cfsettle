package com.upg.ucars.basesystem.security.action;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.mapping.organization.CfsOrgArea;
import com.upg.cfsettle.mapping.organization.CfsOrgDept;
import com.upg.cfsettle.mapping.organization.CfsOrgTeam;
import com.upg.cfsettle.organization.core.IOrgAreaService;
import com.upg.cfsettle.organization.core.IOrgDeptService;
import com.upg.cfsettle.organization.core.IOrgTeamService;
import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.basesystem.security.core.branch.IBranchService;
import com.upg.ucars.basesystem.security.core.sysConfig.ISysConfigService;
import com.upg.ucars.basesystem.security.core.sysparam.ISysParamService;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.basesystem.security.core.user.PasswordChg;
import com.upg.ucars.basesystem.security.core.user.UserManager;
import com.upg.ucars.basesystem.security.core.user.UserSearchBean;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.factory.DynamicPropertyTransfer;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.exception.ActionException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.audit.AuditTask;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.ReBrchFunc;
import com.upg.ucars.mapping.basesystem.security.Role;
import com.upg.ucars.mapping.basesystem.security.SysConfig;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.JQueryTreeNode;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.DESKeyUtil;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.DigestUtil;
import com.upg.ucars.util.PropertyTransVo;

/**
 * 
 * 功能说明：用户action
 * @author shentuwy  
 * @author shentuwy
 * @date 2011-8-4 上午8:52:49 
 *
 */
public class UserAction extends BaseAction  {
	
	private static final long serialVersionUID = -93320150946554309L;
	/** 用户服务 */
	private IUserService userService;
	/** 系统配置服务 */
	private ISysConfigService sysConfigService;
	/** 机构服务 */
	private IBranchService branchService;
	/** 用户实体 */
	private Buser user;
	/** 修改密码实体 */
	private PasswordChg passwordChg;
	/** 用户ID */
	private Long userId;
	/** 用户类型列表 */
	private List<Code> userTypeList;
	/** 角色类型列表 */
	private List<Code>  roleTypeList;
	/** json字符串 */
	private String treeJSONInfo;
	/** 机构实体 */
	private Branch branch;
	/** 审核任务实体 */
	private AuditTask auditTask;
	/** 在线用户管理页面的标志  */
	private String olp;
	private String userTypeIsImp="0";
	private String userTypeIsSaas="0";
	private String userTypeIsBrch="0";
	/** 参数服务 */
	private ISysParamService sysParamService;
	/** 用户是否已经分配了角色 */
	private boolean hasRoles = false;
	private String xtheme=ServletActionContext.getServletContext().getInitParameter("default_theme");
	
	private UserSearchBean searchBean;
	
	private List<CfsOrgArea> userAreaList;
	
	private List<CfsOrgDept> userDeptList;
	
	private List<CfsOrgTeam> userTeamList;
	@Autowired
	private IOrgAreaService orgAreaService;
	@Autowired
	private IOrgDeptService orgDeptService;
	@Autowired
	private IOrgTeamService orgTeamService;
	
	private String isChangePwd;
	
	/**
	 * 
	 * 是否在线用户管理页面
	 *
	 * @return
	 */
	private boolean isOnLinePage(){
		return StringUtils.equals("1", olp);
	}
	/**
	 * 
	 * 用户管理主页面
	 *
	 * @return
	 */
	public String mainPage(){	
		String miNo=SessionTool.getUserLogonInfo().getMiNo();
		if( miNo != null ){
			if( sysParamService.isCheckUserRole(miNo) || sysParamService.isCheckBrchManagerRole(miNo) ){
				showAudit="1";
			}
		}
		String utype=SessionTool.getUserLogonInfo().getUserType();
		if(Buser.TYPE_IMPLEMENTATION.equals(utype)){
			userTypeIsImp="1";
		}
		if(Buser.TYPE_SAAS_MAINTENANCE.equals(utype)||Buser.TYPE_SAAS_MANAGER.equals(utype)){
			userTypeIsSaas="1";
		}
		if(Buser.TYPE_BRCH_GLOBAL_MANAGER.equals(utype)||Buser.TYPE_BRCH_LOCAL_MANAGER.equals(utype)){
			userTypeIsBrch="1";
		}
		roleTypeList=DictionaryUtil.getCodesByKey(Buser.CODE_ROLE_STATUS);
		if(isOnLinePage()){
			return "OL_MAIN";	
		}
		return MAIN;
	}
	

	/**
	 * 
	 * 查询机构下用户列表,用户选择用户时使用
	 *
	 * @return
	 */
	public String selectUser(){
		
		return "selectUser";
		
	}
	/**
	 * 
	 * 查询机构下用户列表,用户选择用户时使用
	 *
	 * @return
	 */
	public String queryBrchUsers(){
		
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>(2);
		conditionList.add(new ConditionBean("buser.status", ConditionBean.NOT_EQUAL, Buser.STATUS_CLOSE));
		if(this.getUser()!=null && this.getUser().getUserName() != null)
		{
			conditionList.add(new ConditionBean("buser.userName", ConditionBean.LIKE, this.user.getUserName()));
		}
		
		Long brchId = this.getBranch().getBrchId();
		
		List<Buser> userList = this.userService.queryBranchUser(brchId, conditionList, true, this.getPg());
		List<PropertyTransVo> transVoList = new ArrayList<PropertyTransVo>();
		transVoList.add(new PropertyTransVo("brchId", Branch.class, "brchId", "brchName", "brchName"));
		return setDatagridInputStreamData(DynamicPropertyTransfer.transform(userList, transVoList), getPg());
	}
	
	/**
	 * 
	 * 获取用户列表
	 *
	 * @return
	 */
	public String listUsers(){
		UserLogonInfo logonInfo = SessionTool.getUserLogonInfo();
		if(user==null){
			user=new Buser();
			user.setRoleStatus("-1");
		}
		boolean isAllUser = false;
		if(isOnLinePage()){
			user.setStatus(Buser.STATUS_ON_LINE);
			isAllUser = true;
		}
		List<Buser> userList = this.userService.queryUserByLogonCondition(logonInfo, null, this.getPg(),user,searchBean,isAllUser) ;
		return setDatagridInputStreamData(userList, getPg());
	}
	/**
	 * 
	 * 改变用户状态
	 *
	 */
	public void changeStatus(){
		if(user!=null){
			userService.updateUserStatus(user.getUserId(), user.getStatus());
		}
	}
	
	public void batchChangeStatus(){
		List<Long> userIds = this.getIdList();
		for(Long userId : userIds){
			userService.updateUserStatus(userId, user.getStatus());
		}
	}
	
	public void changeTheme(){
		if(!StringUtils.isBlank(xtheme)){
			SessionTool.getUserLogonInfo().setTheme(xtheme);
		}
	}
	/**
	 * 
	 * 增加用户页面
	 *
	 * @return
	 */
	public String toAdd() {
		prepare();
		return ADD;
	}
	/**
	 * 
	 * 增加用户
	 *
	 */
	public void addUser(){
		user.setPwdChgDt(DateTimeUtil.getNowDateTime());
		user.setPwdErrNum(0L);
		user.setStatus(Buser.STATUS_OUT_LINE);
		Long brchId=user.getBrchId();
		SysConfig config = null;
		if(brchId!=null){
			Branch brch=branchService.getBranchByBrchId(brchId);
			String miNo=brch.getMiNo();
			user.setMiNo(miNo);
			config = sysConfigService.getConfigByMiNo(miNo,true);
		}
		String md5Pwd = "";
		if(config == null){
			md5Pwd = DigestUtil.getMD5(SysConfig.DEFAULT_INIT_PWD);
		}else{
			if("".equals(config.getPwdInit()) || "null".equals(config.getPwdInit()) || config.getPwdInit() == null)
				md5Pwd=DigestUtil.getMD5(SysConfig.DEFAULT_INIT_PWD);
			else
				md5Pwd=DigestUtil.getMD5(DESKeyUtil.DecryptAES(config.getPwdInit(), null));
		}
//		md5Pwd=DigestUtil.getMD5(DESKeyUtil.DecryptAES(config.getPwdInit()==null?SysConfig.DEFAULT_INIT_PWD:config.getPwdInit(), null));
		user.setPassword(md5Pwd);
		user.setRoleStatus(Buser.ROLE_STATUS_UN_ASSIGN);
		this.userService.saveUser(user);
	}
	
	/**
	 * 
	 * 增加或者编辑
	 *
	 */
	public void saveOrUpUser(){
		if( user != null && user.getUserId() != null ){ //编辑
			updateUser();
		}else{ //增加
			addUser();
		}
	}
	
	

	
	/**
	 * 
	 * 机构查询
	 *
	 * @return
	 */
	public String queryChooseBrch(){
		UserLogonInfo logonInfo = SessionTool.getUserLogonInfo();
		List<Branch> brchList = null;
		if ( UserManager.isSaasManager() || UserManager.isSaasMaintenance() || UserManager.isImplementation()){			
//			brchList = branchService.getHQBranches(getPg());
			brchList = branchService.getHQBranches(branch, getPg());
		}
		if ( UserManager.isBrchGlobalManager() || UserManager.isBrchLocalManager() ) {	
			Branch brch = branchService.getBranchByBrchId(logonInfo.getBranchId());
//			brchList = branchService.getSubBranches(brch.getTreeCode());
			brchList = branchService.getSubBranches(brch.getTreeCode(),branch.getBrchId());
		}
		//设置权限状态
		for(Branch brch: brchList){
			brch.setFuncStatus(branchService.getBrchFuncStatus(brch.getBrchId()));
		}
		return setDatagridInputStreamData(brchList, getPg());
	}
	
	/**
	 * 
	 * 获取用户类型列表
	 *
	 */
	private void prepare(){
		userAreaList = orgAreaService.findByCondition(new CfsOrgArea(Byte.valueOf("1")), null);
		String userId = getId();
		if( StringUtils.isNotBlank(userId) ){
			user = userService.getUserById(Long.valueOf(userId));
			//审核中的用户不能被编辑
			if( StringUtils.equals(Buser.ROLE_STATUS_AUDITING, user.getRoleStatus()) ){
				ExceptionManager.throwException(ActionException.class, ErrorCodeConst.COMMON_ERROR_CODE,new String[]{getText("EDIT_AUDITING_ERROR")});
			}
			if (user.getBrchId()!=null){
				this.branch = branchService.getBranchByBrchId(user.getBrchId());
			}
			hasRoles = userService.hasRoles(Long.valueOf(userId));
		}
		userTypeList=new ArrayList<Code>();
		List<Code> codeList  = DictionaryUtil.getCodesByField("buser", "user_type");
		if( codeList != null && codeList.size() > 0 ){
			List<String> userTypeCodeNo = new ArrayList<String>();
			if(hasRoles){
				userTypeCodeNo.add(user.getUserType());
			}else{
				if (UserManager.isImplementation()){
					userTypeCodeNo.add(Buser.TYPE_SAAS_MAINTENANCE);
					userTypeCodeNo.add(Buser.TYPE_SAAS_MANAGER);			
				}else if (UserManager.isSaasManager()) {
					userTypeCodeNo.add(Buser.TYPE_BRCH_GLOBAL_MANAGER);			
				}else if (UserManager.isBrchGlobalManager()) {		
					userTypeCodeNo.add(Buser.TYPE_BRCH_LOCAL_MANAGER);	
					userTypeCodeNo.add(Buser.TYPE_BRCH_NOMAL_USER);	
				}else if (UserManager.isBrchLocalManager()) {		
					userTypeCodeNo.add(Buser.TYPE_BRCH_LOCAL_MANAGER);	
					userTypeCodeNo.add(Buser.TYPE_BRCH_NOMAL_USER);			
				}
			}
			if( userTypeCodeNo != null && userTypeCodeNo.size() > 0 ){
				for( Code code : codeList  ){
					if( userTypeCodeNo.contains(code.getCodeNo()) ){
						userTypeList.add(code);
					}
				}
			}
		}
	}
	
	/**
	 * 
	 * 编辑页面
	 *
	 * @return
	 */
	public String toEdit() {
		prepare();
		userDeptList = orgDeptService.find(null,null);
		userTeamList = orgTeamService.find(null, null);
		return EDIT;
	}
	/**
	 * 
	 * 更新用户
	 *
	 * @return
	 */
	public void updateUser(){
		Buser retuser=userService.getUserById(user.getUserId());
		if(retuser.getUserType()!=null && !retuser.getUserType().equals(user.getUserType())){
			hasRoles = userService.hasRoles(user.getUserId());
			if(hasRoles)
				ExceptionManager.throwException(ServiceException.class,ErrorCodeConst.SECURITY_USER_EDIT_USERTYPE);
		}
		String preUserType = retuser.getUserType();
		retuser.setUserName(user.getUserName());
		retuser.setUserType(user.getUserType());
		retuser.setUserNo(user.getUserNo());
		retuser.setEmail(user.getEmail());
		retuser.setAreaId(user.getAreaId());
		retuser.setDeptId(user.getDeptId());
		retuser.setTeamId(user.getTeamId());
		Long brchId=user.getBrchId();
		if(brchId!=null){
			Branch brch=branchService.getBranchByBrchId(brchId);
			user.setMiNo(brch.getMiNo());
			retuser.setMiNo(brch.getMiNo());
			retuser.setBrchId(brchId);
		}
		if(Buser.TYPE_IMPLEMENTATION.equals(user.getUserType())||Buser.TYPE_SAAS_MAINTENANCE.equals(user.getUserType())||Buser.TYPE_SAAS_MANAGER.equals(user.getUserType())){
			retuser.setBrchId(null);
		}
		if( retuser.getBrchId() == null ){
			retuser.setMiNo(null);
		}
		this.userService.updateUser(retuser);
		
		if (preUserType!=null && !preUserType.equals(retuser.getUserType())){
			this.userService.buildUserRoles(retuser.getUserId(), new ArrayList<Long>(0));
		}
	}
	
	/**
	 * 
	 * 删除用户
	 *
	 */
	public void deleteUser(){
		List<Long> list = this.getIdList();
		for (Long id : list) {
			userService.deleteById(id);
		}
	}
	/**
	 * 
	 * 重置密码
	 *
	 * @return
	 */
	public void resetPassword(){
		userService.batchResetPassword(getIdList());
	}
	
	/**
	 * 
	 * 重置密码页面
	 *
	 * @return
	 */
	public String toChangePassword(){
		if(passwordChg==null){
			passwordChg=new PasswordChg();
		}
		UserLogonInfo logoner = SessionTool.getUserLogonInfo();
		if(logoner!=null){
			passwordChg.setSysUserID(logoner.getSysUserId());
			passwordChg.setUserNo(logoner.getUserNo());
		}
		isChangePwd = this.getIsChangePwd();
		return "changePassword";
	}
	/**
	 * 
	 * 修改密码
	 *
	 */
	public void changePassword(){
			this.userService.changePassword(passwordChg.getUserNo(), passwordChg.getOldPassword(), passwordChg.getNewPassword(), 
					passwordChg.getConfirmPassword(),
					SessionTool.getUserLogonInfo() != null ? SessionTool.getUserLogonInfo().getMiNo() : passwordChg.getMiNo());
	}
	/**
	 * 
	 * 设置用户离线
	 *
	 * @return
	 */
	public void setUserOffline(){
		List<Long> idList = getIdList();
		userService.setUserStatus(idList, Buser.STATUS_OUT_LINE);
		for (Long userId : idList) {
			SessionTool.removeUserOldSession(userId);
		}
	}
	/**
	 * 
	 * 用户角色页面
	 *
	 * @return
	 */
	public String userRoleMain(){
		this.initUserRoleInfo();
		return "userRoleMain";
	}
	
	
	/**
	 * 初始化已分配及未分配的角色树信息。
	 * 
	 */
	private void initUserRoleInfo(){		
		UserLogonInfo logoner = SessionTool.getUserLogonInfo();
		
		Buser user = userService.getUserById(userId);
		
		List<Role> allList = userService.queryAllowAssignRole(logoner, user);
		List<Role> beList = userService.queryUserRole(userId);
		
		ArrayList<JQueryTreeNode> nodeList = new ArrayList<JQueryTreeNode>();		
		
		for (Role role : allList) {		
			JQueryTreeNode node = new JQueryTreeNode();
			node.setId(role.getRoleId().toString());
			node.setText(role.getRoleName());
			node.setIconCls("icon-ok");
			
			if (beList.contains(role)){//已分配权限
				node.setChecked(true);
				
			}else{//未分配				
				node.setChecked(false);
					
			}	
			nodeList.add(node);
		}
		this.setTreeJSONInfo(getJsonData(nodeList));
	}
	
	
	/**
	 * 初始化已分配权限树信息。
	 */
	public String userFuncTree() {
		user=userService.getUserById(userId);
//		List<Sysfunc> beFuncList = this.userService.queryUserFunction(userId);
		List<Sysfunc> beFuncList = this.userService.queryUserFunc(userId, Buser.RE_ROLE_STATUS_CHECK);
		if (user.getBrchId() != null){
			ArrayList<Sysfunc> be2List = new ArrayList<Sysfunc>();
//			List<Sysfunc> brchFuncList = this.branchService.getFunctionByPage(user.getBrchId(), null);
			List<Sysfunc> brchFuncList = this.branchService.queryFunc(user.getBrchId(), ReBrchFunc.STATUS_CHECKED);
			for (Sysfunc func : brchFuncList) {
				if (beFuncList.contains(func)){
					be2List.add(func);
				}
			}
			beFuncList = be2List;
		}
		this.setTreeJSONInfo(getJsonData(Sysfunc.buildOpenTree(beFuncList)));
		
		return "userFuncTree";
	}
	public String userFuncAudit(){
		user=userService.getUserById(getAuditEntityId());
		List<Sysfunc> beFuncList = this.userService.queryUserFunction(getAuditEntityId());
		this.setTreeJSONInfo(getJsonData(Sysfunc.buildOpenTree(beFuncList)));
		return "userFuncTreeAudit";
	}
	
	/**
	 * 
	 * 用户分配角色
	 *
	 */
	public void saveUserRole(){
		this.userService.buildUserRoles(userId, getIdList());
	}
	/**
	 * 
	 * 提交用户角色审批
	 *
	 */
	public void commitUserRoleAudit(){
		UserLogonInfo logonInfo=SessionTool.getUserLogonInfo();
		this.userService.commitUserRoles(logonInfo,userId);
		
	}
	/**
	 * 
	 * 批量提交用户角色审批
	 *
	 */
	public void batchCommitUserRoleAudit(){
		UserLogonInfo logonInfo=SessionTool.getUserLogonInfo();
		userService.batchCommitUserRoles(logonInfo, getIdList());
	}
	/**
	 * 
	 * 撤销用户角色审批
	 *
	 */
	public void reworkUserRoleAudit(){
		UserLogonInfo logonInfo=SessionTool.getUserLogonInfo();
		this.userService.revokeUserRoles(logonInfo,userId);
		
	}
	/**
	 * 
	 * 批量撤销用户角色审批
	 *
	 */
	public void batchReworkUserRoleAudit(){
		UserLogonInfo logonInfo = SessionTool.getUserLogonInfo();
		userService.batchRevokeUserRoles(logonInfo, getIdList());
	}
	/**
	 * 
	 * 查看用户角色审批进度
	 *
	 * @return
	 */
	public String viewUserRoleAuditProcess(){
		auditTask=userService.findUserRoleAuditTask(userId);
		return VIEW_AUDIT_PROCESS;
	}
	/**
	 * 获取用户状态
	 * @return
	 */
	public String getCurUser(){
		user = userService.getUserById(SessionTool.getUserLogonInfo().getSysUserId());
		Map<String,String> json = new HashMap<String,String>();
		if(user.getLastLoginTm() !=null||user.getLastLoginIP()!=null){
			json.put("user","changed");
		}else{
			json.put("user","unchange");
		}
		return setInputStreamData(json);
	}
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public Buser getUser() {
		return user;
	}
	public void setUser(Buser user) {
		this.user = user;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getTreeJSONInfo() {
		return treeJSONInfo;
	}
	public void setTreeJSONInfo(String treeJSONInfo) {
		this.treeJSONInfo = treeJSONInfo;
	}
	public PasswordChg getPasswordChg() {
		return passwordChg;
	}
	public void setPasswordChg(PasswordChg passwordChg) {
		this.passwordChg = passwordChg;
	}
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	public void setSysConfigService(ISysConfigService sysConfigService) {
		this.sysConfigService = sysConfigService;
	}
	public List<Code>  getUserTypeList() {
		return userTypeList;
	}
	public void setUserTypeList(List<Code>  userTypeList) {
		this.userTypeList = userTypeList;
	}
	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}
	public List<Code>  getRoleTypeList() {
		return roleTypeList;
	}
	public void setRoleTypeList(List<Code>  roleTypeList) {
		this.roleTypeList = roleTypeList;
	}
	public AuditTask getAuditTask() {
		return auditTask;
	}
	public void setAuditTask(AuditTask auditTask) {
		this.auditTask = auditTask;
	}
	public String getOlp() {
		return olp;
	}
	public void setOlp(String olp) {
		this.olp = olp;
	}
	public void setSysParamService(ISysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}
	public String getXtheme() {
		return xtheme;
	}
	public void setXtheme(String xtheme) {
		this.xtheme = xtheme;
	}
	public String getUserTypeIsImp() {
		return userTypeIsImp;
	}
	public void setUserTypeIsImp(String userTypeIsImp) {
		this.userTypeIsImp = userTypeIsImp;
	}
	public String getUserTypeIsSaas() {
		return userTypeIsSaas;
	}
	public void setUserTypeIsSaas(String userTypeIsSaas) {
		this.userTypeIsSaas = userTypeIsSaas;
	}
	public String getUserTypeIsBrch() {
		return userTypeIsBrch;
	}
	public void setUserTypeIsBrch(String userTypeIsBrch) {
		this.userTypeIsBrch = userTypeIsBrch;
	}
	public UserSearchBean getSearchBean() {
		return searchBean;
	}
	public void setSearchBean(UserSearchBean searchBean) {
		this.searchBean = searchBean;
	}
	public List<CfsOrgArea> getUserAreaList() {
		return userAreaList;
	}
	public void setUserAreaList(List<CfsOrgArea> userAreaList) {
		this.userAreaList = userAreaList;
	}
	public List<CfsOrgDept> getUserDeptList() {
		return userDeptList;
	}
	public void setUserDeptList(List<CfsOrgDept> userDeptList) {
		this.userDeptList = userDeptList;
	}
	public List<CfsOrgTeam> getUserTeamList() {
		return userTeamList;
	}
	public void setUserTeamList(List<CfsOrgTeam> userTeamList) {
		this.userTeamList = userTeamList;
	}
	public String getIsChangePwd() {
		return isChangePwd;
	}
	public void setIsChangePwd(String isChangePwd) {
		this.isChangePwd = isChangePwd;
	}
}