package com.upg.ucars.basesystem.security.core.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.upg.cfsettle.util.UtilConstant;
import com.upg.ucars.basesystem.UcarsHelper;
import com.upg.ucars.basesystem.audit.core.AuditTaskRevokeResult;
import com.upg.ucars.basesystem.audit.core.IAuditCallBack;
import com.upg.ucars.basesystem.audit.core.IAuditService;
import com.upg.ucars.basesystem.security.core.branch.BranchHelper;
import com.upg.ucars.basesystem.security.core.branch.IBranchDAO;
import com.upg.ucars.basesystem.security.core.branch.IBranchService;
import com.upg.ucars.basesystem.security.core.member.IMemberDAO;
import com.upg.ucars.basesystem.security.core.role.IRoleService;
import com.upg.ucars.basesystem.security.core.role.RoleConstant;
import com.upg.ucars.basesystem.security.core.sysConfig.ISysConfigService;
import com.upg.ucars.basesystem.security.core.sysparam.ISysParamService;
import com.upg.ucars.basesystem.security.core.userlog.IUserLogDAO;
import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.constant.SessionKeyConst;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.ICommonDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.AdapterRuntimeException;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.LoginException;
import com.upg.ucars.framework.exception.RightsException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.framework.security.ISecurityManager;
import com.upg.ucars.framework.security.ISecurityUserService;
import com.upg.ucars.mapping.basesystem.audit.AuditProcess;
import com.upg.ucars.mapping.basesystem.audit.AuditTask;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.MemberInfo;
import com.upg.ucars.mapping.basesystem.security.ReUserRole;
import com.upg.ucars.mapping.basesystem.security.Role;
import com.upg.ucars.mapping.basesystem.security.SysConfig;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.DESKeyUtil;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.ucars.util.DigestUtil;
import com.upg.ucars.util.LogUtil;
import com.upg.ucars.util.SourceTemplate;
import com.upg.ucars.util.StringUtil;

/**
 * 
 * UserServiceImp
 *
 * @author shentuwy
 *
 */
public class UserServiceImp extends BaseService implements IUserService,ISecurityUserService,IAuditCallBack {
	/** 只能提交为审批的错误 */
	private static final String ERROR_USER_ROLE_AUDIT_MUST_UN_CHECK="USERFUNC_CHECK_001";
	/** 只能撤销审批中的错误 */
	private static final String ERROR_USER_ROLE_REVOKE_MUST_CHECKING="USERFUNC_CHECK_002";
	
	/** 用户 */
	IUserDAO userDAO;
	/** 机构 */
	IBranchDAO branchDAO;
	/** 接入点 */
	IMemberDAO memberDAO;
	/** 用户日志 */
	IUserLogDAO userLogDAO;
	/** 审批  */
	IAuditService auditService;
	/** 参数 */
	ISysParamService sysParamService;
	/** 配置*/
	ISysConfigService sysConfigService=null;
	/** 用户角色 */
	IReUserRoleDAO userRoleDAO;
	/** 用户角色分配审核 */
	private String userRoleCheckProdNo;
	/** 管理员角色分配审核 */
	private String managerRoleCheckProdNo;
	/** 通用DAO */
	private ICommonDAO commonDAO;

	public void setSysParamService(ISysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}

	public void setUserRoleDAO(IReUserRoleDAO userRoleDAO) {
		this.userRoleDAO = userRoleDAO;
	}
	
	public void setUserRoleCheckProdNo(String userRoleCheckProdNo) {
		this.userRoleCheckProdNo = userRoleCheckProdNo;
	}

	public void setUserLogDAO(IUserLogDAO userLogDAO) {
		this.userLogDAO = userLogDAO;
	}

	public void setBranchDAO(IBranchDAO branchDAO) {
		this.branchDAO = branchDAO;
	}

	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public void setMemberDAO(IMemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	public void setCommonDAO(ICommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}
	public void setAuditService(IAuditService auditService) {
		this.auditService = auditService;
	}
	public void setManagerRoleCheckProdNo(String managerRoleCheckProdNo) {
		this.managerRoleCheckProdNo = managerRoleCheckProdNo;
	}

	public void setSysConfigService(ISysConfigService sysConfigService) {
		this.sysConfigService = sysConfigService;
	}
	
	/**
	 * 修改密码
	 * 
	 */
	public void changePassword(String userNo, String oldPwd, String newPwd,String confirmPwd,String miNo) {
		if (!newPwd.equals(confirmPwd)){
			throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.SECURITY_PASSWORD_ERROR_DIFFERENT_PASSWORD);
		}
		
		Buser user = this.userDAO.getUserByUserNoAndMiNo(userNo,miNo);
		if(user==null){
			throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.SECURITY_PASSWORD_ERROR_PREPASSWORD);
		}
		String oldBoxPwd=DigestUtil.getMD5(oldPwd);		
		if (!oldBoxPwd.equals(user.getPassword())){
			throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.SECURITY_PASSWORD_ERROR_PREPASSWORD);
		}
		
		user.setPassword(DigestUtil.getMD5(newPwd));
		user.setPwdChgDt(new Date(System.currentTimeMillis()));
		user.setIsChangePwd(Byte.valueOf("1"));
		this.userDAO.update(user);		
		
	}
	
	/**
	 * 重置密码
	 * 
	 * @param userId
	 * 
	 */
	public void resetPassword(Long userId) {
		Buser user = this.userDAO.get(userId);
		String initPwd = SysConfig.DEFAULT_INIT_PWD;
		if (user.getBrchId()!=null){
			Branch b = SourceTemplate.getBean(IBranchService.class,BeanNameConstants.BRANCH_SERVICE).getBranchByBrchId(user.getBrchId());
			String miNo = b.getMiNo();
			SysConfig config = sysConfigService.getConfigByMiNo(miNo,false);
			if( config != null && StringUtils.isNotBlank(config.getPwdInit()) ){				
				initPwd = DESKeyUtil.DecryptAES(config.getPwdInit(), null);
			}
		}		
		String md5Pwd=DigestUtil.getMD5(initPwd);		
		user.setPassword(md5Pwd);
		user.setPwdChgDt(DateTimeUtil.getNowDateTime());
		//将用户锁定状态修改为离线状态
		if(Buser.STATUS_LOCK.equals(user.getStatus())){
			user.setStatus(Buser.STATUS_OUT_LINE);
		}
		this.userDAO.update(user);
	}
	
	/**
	 * 批量重置密码
	 * 
	 * @param idList
	 * 
	 */
	public void batchResetPassword(List<Long> idList){
		if( idList != null && idList.size() > 0 ){
			for( Long id : idList ){
				resetPassword(id);
			}
		}
	}
	
	/**
	 * 
	 * 增加和修改前的检查
	 *
	 * @param user
	 */
	private void validOperateUser(Buser user){
		if( user != null ){
			//同一机构的管理员不能增加同级的管理员
			if(Buser.TYPE_BRCH_GLOBAL_MANAGER.equals(user.getUserType())||Buser.TYPE_BRCH_LOCAL_MANAGER.equals(user.getUserType())){
				if( user.getBrchId() != null && user.getBrchId().equals(SessionTool.getUserLogonInfo().getBranchId())) {
					ExceptionManager.throwException(ServiceException.class, Buser.ERROR_NOT_ADD_MANAGER_FOR_OWN);
				}
			}
			//同一接入点不能有相同的用户号
			if( user.getUserId() != null ){
				if(userDAO.updateUserRegxUserNo(user)){
					throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.SECURITY_USERNO_EXIST, new String[]{user.getUserNo()});
				}
			}else{
				if (userDAO.isExistUserByuserNoAndMiNo(user.getUserNo(), user.getMiNo())){
					throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.SECURITY_USERNO_EXIST, new String[]{user.getUserNo()});
				}
			}
		}
	}
	
	/**
	 * 增加用户
	 * 
	 * @param user
	 */
	public void saveUser(Buser user) {
		validOperateUser(user);
		this.userDAO.save(user);
	}
	/**
	 * 更新用户
	 * 
	 * @param user
	 */
	public void updateUser(Buser user) {
		validOperateUser(user);
		this.userDAO.update(user);
	}
	
	/**
	 * 设置离线
	 * 
	 * @param userId
	 */
	public void offLine(Long userId) {
		Buser user = this.userDAO.get(userId);
		user.setStatus(Buser.STATUS_OUT_LINE);
		userDAO.update(user);
	}
	public List<Buser> queryUserByLogonCondition(UserLogonInfo logonInfo, QueryComponent queryComponent, Page page,String roleStatus){
		Buser user = new Buser();
		user.setRoleStatus(roleStatus);
		return queryUserByLogonCondition(logonInfo, null, page, user,null,false);
	}
	
	public List<Buser> queryUserByLogonCondition(UserLogonInfo logonInfo, QueryComponent queryComponent, Page page,Buser bu,UserSearchBean searchBean,boolean isAllUser){
		
		List<ConditionBean> buserConditionList = new ArrayList<ConditionBean>();
		
		if( bu != null ){
			String userName = bu.getUserName();
			if( userName != null && userName.trim().length() > 0){
				buserConditionList.add(new ConditionBean("userName", ConditionBean.LIKE, userName.trim()));
			}
			String userNo = bu.getUserNo();
			if( userNo != null && userNo.trim().length() > 0 ){
				buserConditionList.add(new ConditionBean("userNo", ConditionBean.LIKE, userNo.trim()));
			}
			String status = bu.getStatus();
			if( status != null && status.trim().length() > 0 ){
				buserConditionList.add(new ConditionBean("status", status.trim()));
			}
			String roleStatus = bu.getRoleStatus();
			if( roleStatus != null && ! "-1".equals(roleStatus) ){
				buserConditionList.add(new ConditionBean("roleStatus", roleStatus.trim()));
			}
		}
		
		
		
		if ( UserManager.isImplementation() ){
			List<ConditionBean> conditionList = new ArrayList<ConditionBean>(1);
			ConditionBean cb = new ConditionBean("userType", ConditionBean.IN,
					new String[] { Buser.TYPE_SAAS_MAINTENANCE,
							Buser.TYPE_SAAS_MANAGER });
			conditionList.add(cb);
			if( buserConditionList != null && buserConditionList.size() > 0 ){
				conditionList.addAll(buserConditionList);
			}
			String hql = "from Buser buser ";
			QueryCondition qc = new QueryCondition(hql);
			qc.addConditionList(conditionList);
			List<Buser> tmpList=commonDAO.queryByCondition(qc, page, queryComponent);
			return tmpList;
			
		}
		
		List<Object[]> list = null;
		
		if ( UserManager.isSaasManager() ) {
			List<ConditionBean> conditionList = new ArrayList<ConditionBean>(1);
			if( ! isAllUser ){
				ConditionBean cb = new ConditionBean("buser.userType", ConditionBean.IN , new String[]{Buser.TYPE_BRCH_GLOBAL_MANAGER} );
				conditionList.add(cb);
			}
			String hql = "select buser, branch from Buser buser, Branch branch where buser.brchId=branch.brchId ";
			
			if( buserConditionList != null && buserConditionList.size() > 0 ){
				for( ConditionBean ctb : buserConditionList ){
					ctb.setField("buser." + ctb.getField());
				}
				conditionList.addAll(buserConditionList);
			}
			
			QueryCondition qc = new QueryCondition(hql);
			qc.addConditionList(conditionList);
			//order
			if( page != null && page.getSortName()!= null && page.getSortName().trim().length() > 0 ){
				page.setSortName("buser." + page.getSortName());
			}

			list = this.commonDAO.queryByCondition(qc, page, queryComponent);
			
		}else if( UserManager.isBrchLocalManager() || UserManager.isBrchGlobalManager()  ) {					
			
			Branch branch = null;
			if (bu != null && bu.getBrchId() != null) {
				branch = SourceTemplate.getBean(IBranchService.class,BeanNameConstants.BRANCH_SERVICE).getBranchByBrchId(bu.getBrchId());
			}else{
				branch = SourceTemplate.getBean(IBranchService.class,BeanNameConstants.BRANCH_SERVICE).getBranchByBrchId(logonInfo.getBranchId());
			}
			
			String hql = "select buser, branch from Buser buser, Branch branch where buser.brchId=branch.brchId and " +
			"( (buser.brchId =:brchId and buser.userType =:userType1)"+//本机构普通柜员
			"  or ( branch.treeCode like :treeCode and buser.brchId <>:brchId )  " +//下级管理员
			") " ;
			
			
			QueryCondition qc = new QueryCondition(hql);
			qc.addParameter("brchId", branch.getBrchId());
			qc.addParameter("userType1", Buser.TYPE_BRCH_NOMAL_USER);		
			qc.addParameter("treeCode", branch.getTreeCode()+"%");
			
			if (searchBean != null) {
				String roleName = searchBean.getRoleName();
				if (StringUtils.isNotBlank(roleName)) {
					hql += " and exists (select 1 from ReUserRole reUserRole,Role role where reUserRole.roleId=role.roleId and reUserRole.userId=buser.userId and role.roleName like :roleName )";
					qc.setHql(hql);
					qc.addParameter("roleName", "%"+roleName+"%");
				}
			}
			
			if( buserConditionList != null && buserConditionList.size() > 0 ){
				for( ConditionBean ctb : buserConditionList ){
					ctb.setField("buser." + ctb.getField());
				}
				qc.addConditionList(buserConditionList);
			}
			
			
			qc.addOrder(new OrderBean("branch.treeCode"));

			list = this.commonDAO.queryByCondition(qc, page, queryComponent);
			
			
		}
		
		if (list==null||list.isEmpty()){
			return new ArrayList<Buser>(0);
		}
		List<Buser> userList = new ArrayList<Buser>();
		for (Object[] objects : list) {
			Buser user = (Buser)objects[0];
			Branch branch = (Branch)objects[1];
			user.setBrchIdDesc(branch.getBrchName());
//			if( isAllUser ){
				MemberInfo mi =  memberDAO.get(user.getMiNo());
				if( mi != null ){
					user.setMiName(mi.getMiName());
				}
//			}
			userList.add(user);
		}
		return userList;
	}
	
	
	/**
	 * 查询允许分配的角色
	 * 
	 * @param logoner
	 * @param user
	 */
	public List<Role> queryAllowAssignRole(UserLogonInfo logoner, Buser user) {
		
		IRoleService  roleService  = SourceTemplate.getBean(IRoleService.class,BeanNameConstants.ROLE_SERVICE);
		String hql = "from Role ";
		QueryCondition qc = new QueryCondition(hql);
		
		if (UserManager.isImplementation()){ //实施人员
			ArrayList<ConditionBean> conditionList = new ArrayList<ConditionBean>(1);
			ConditionBean cond = new ConditionBean("roleType",user.getUserType());
			conditionList.add(cond);
			qc.addConditionList(conditionList);			
			return roleService.queryByCondition(qc, null);
		}else if (UserManager.isSaasManager()){ //saas管理员			
			ArrayList<ConditionBean> conditionList = new ArrayList<ConditionBean>(1);
			ConditionBean cond = new ConditionBean("roleType",RoleConstant.ROLE_TYPE_CENTRE);
			conditionList.add(cond);
			
			qc.addConditionList(conditionList);			
			return roleService.queryByCondition(qc, null);
		}else if (UserManager.isBrchLocalManager() || UserManager.isBrchGlobalManager() ){ //机构管理员
			
			hql = "select role from Role role, Branch branch where role.brchId=branch.brchId ";
			qc = new QueryCondition(hql);
			
			IBranchService branchService = SourceTemplate.getBean(IBranchService.class,BeanNameConstants.BRANCH_SERVICE);
			Branch branch = branchService.getBranchByBrchId(logoner.getBranchId());	
			
			ArrayList<String> treeCodeList = new ArrayList<String>(3);
			treeCodeList.add(branch.getTreeCode());
			
			String treeCode = branch.getTreeCode();
			while (!StringUtil.isEmpty(BranchHelper.getParentTreeCode(treeCode))){
				treeCode=BranchHelper.getParentTreeCode(treeCode);
				treeCodeList.add(treeCode);
			}
			
			ArrayList<ConditionBean> conditionList = new ArrayList<ConditionBean>(1);
			ConditionBean cond = new ConditionBean("branch.treeCode", ConditionBean.IN, treeCodeList);
			conditionList.add(cond);
			
			List<String> roleTypeList = new ArrayList<String>();
			roleTypeList.add(RoleConstant.ROLE_TYPE_BRANCH);
			roleTypeList.add(RoleConstant.ROLE_TYPE_COMMON);
			roleTypeList.add(RoleConstant.ROLE_TYPE_JUDGMENT);
			
			cond = new ConditionBean("role.roleType",ConditionBean.IN ,roleTypeList);
			conditionList.add(cond);
						
			qc.addConditionList(conditionList);
			
			qc.addOrder(new OrderBean("convert_gbk( 'role.roleName')"));
			
			return roleService.queryByCondition(qc, null);
		}
		
		return new ArrayList<Role>(0);
	}
	
	public List<Role> queryUserRole(Long userId) {
		return this.userDAO.queryUserRole(userId);
		
	}	

	public List<Buser> queryRoleUser(Long roleId, List<ConditionBean> conditionList, Page page) throws DAOException {
		String hql="select user from ReUserRole re,Buser user,Role role " +
		"where user.userId=re.userId and role.roleId=re.roleId and role.roleId="+roleId;
		QueryCondition qc = new QueryCondition(hql);
		qc.addConditionList(conditionList);
		
		List<Buser> list = this.commonDAO.queryByCondition(qc, page);
		return list;
	}
	
	public List<Buser> queryBranchUser(Long brchId, List<ConditionBean> conditionList, boolean isSubBrch,Page page) throws DAOException{
		
		List<Buser> list = null;
		if(isSubBrch)   // 是否包含子机构下用户
		{
			
			Branch branch = SourceTemplate.getBean(IBranchService.class,BeanNameConstants.BRANCH_SERVICE).getBranchByBrchId(brchId);
			
			String hql = "select buser from Buser buser, Branch branch where buser.brchId=branch.brchId  " +
			" and buser.userType =:userType1"+//本机构普通柜员
			" and branch.treeCode like :treeCode and branch.miNo=:miNo " ;
			
			
			QueryCondition qc = new QueryCondition(hql);
			
			qc.addParameter("userType1", Buser.TYPE_BRCH_NOMAL_USER);		
			qc.addParameter("treeCode", branch.getTreeCode()+"%");
			qc.addParameter("miNo", branch.getMiNo());
			
			qc.addConditionList(conditionList);
			qc.addOrder(new OrderBean("buser.userId"));
			list = this.commonDAO.queryByCondition(qc, page);
			
		}else
		{
			String hql="select user from Buser user where " +
			" user.brchId="+brchId;
			QueryCondition qc = new QueryCondition(hql);
			qc.addConditionList(conditionList);
			qc.addOrder(new OrderBean("userId"));
			list = this.commonDAO.queryByCondition(qc, page);
		}
		
		
		
		return list;
	}

	
	private List<ReUserRole> getReUserRoleByUserId(Long userId){
		return this.commonDAO.find("from ReUserRole o where o.userId=?", userId);
	}

	public List<Sysfunc> queryUserFunction(Long userId) {
		return this.userDAO.queryUserFunction(userId);
	}
	
	/**
	 * 查询用户权限 
	 * 使用hibernate二级缓存
	 * @param userId
	 * @return funcId列表
	 * @throws DAOException
	 */
	public List<Long> queryUserFunctionWithCache(Long userId)throws DAOException{
		return this.userDAO.queryUserFunctionWithCache(userId);
	}
	
	public List<Sysfunc> queryUserFunc(Long userId,String userRoleStatus) throws DAOException{
		return this.userDAO.queryUserFunc(userId, userRoleStatus);
	}

	public void checkUserFunction(Long userId) {
		this.userDAO.updateReUserRoleStatusByUserId(userId, Buser.RE_ROLE_STATUS_CHECK);
	}
	

	public void updateUserStatus(Long userId, String userStatus) {
		Buser user = this.userDAO.get(userId);
		user.setStatus(userStatus);
		this.userDAO.update(user);
	}

	public void buildUserRoles(Long userId, List<Long> roleIdList) {
		List<ReUserRole> oldReRoleList = this.getReUserRoleByUserId(userId);
		List<Long> oldRoleIdList = new ArrayList<Long>();
		for (ReUserRole reUserRole : oldReRoleList) {
			oldRoleIdList.add(reUserRole.getRoleId());
		}
		Buser user=userDAO.get(userId);
		for (Long roleId : roleIdList) {
			if (oldRoleIdList.remove(roleId)){
				continue;
			}
			ReUserRole rur = new ReUserRole();
			rur.setUserId(userId);
			rur.setRoleId(roleId);
			rur.setStatus(isCheckUserRole(user)?Buser.RE_ROLE_STATUS_UNCHECK:Buser.RE_ROLE_STATUS_CHECK);
			this.userDAO.addReUserRole(rur);
		}
		List<ReUserRole> rList=new ArrayList<ReUserRole>();
		for (Long roleId : oldRoleIdList) {
			ReUserRole re=userRoleDAO.findByUserIdAndRoleId(userId,roleId);
			if(re!=null){
				rList.add(re);
			}
		}
		userRoleDAO.delAll(rList);
		resetReUserRoleStatus(user);
		modifyUserRoleStatus(userId);
	}
	
	/**
	 * 
	 * 是否需要审核
	 * @param user
	 * @return
	 */
	private boolean isCheckUserRole(Buser user){
		boolean ret = false;
		if( user != null ){
			if( StringUtils.equals(user.getUserType(), Buser.TYPE_BRCH_LOCAL_MANAGER) ){
				ret = sysParamService.isCheckBrchManagerRole(user.getMiNo());
			}else if ( StringUtils.equals(user.getUserType(), Buser.TYPE_BRCH_NOMAL_USER) ){
				ret = sysParamService.isCheckUserRole(user.getMiNo());
			}
		}
		return ret;
	}
	
	private void resetReUserRoleStatus(Buser user){
		if( user != null ){
			List<ReUserRole> list = getReUserRoleByUserId(user.getUserId());
			if( list != null && list.size() > 0 ){
				List<ReUserRole> updateList = new ArrayList<ReUserRole>(list.size());
				String status = isCheckUserRole(user) ? Buser.RE_ROLE_STATUS_UNCHECK:Buser.RE_ROLE_STATUS_CHECK ;
				for( ReUserRole rur : list ){
					if( ! StringUtils.equals(status, rur.getStatus()) ){
						 rur.setStatus(status);
						 updateList.add(rur);
					}
				}
				if( updateList != null && updateList.size() > 0 ){
					userRoleDAO.saveOrUpdateAll(updateList);
				}
			}
		}
	}
	
	/**
	 * 根绝用户角色关系表计算用户表上的角色状态值 
	 * 
	 * @param userId
	 */
	private void modifyUserRoleStatus(Long userId){
		modifyUserRoleStatus(userId,false);
	}
	
	/**
	 * 根绝用户角色关系表计算用户表上的角色状态值 
	 * 
	 * @param userId
	 * @param isClearCache 是否要清理掉一级缓存的内容，一般为false
	 * 
	 * 
	 */
	private void modifyUserRoleStatus( Long userId, boolean isClearCache ){
		if( userId != null ){
			Buser user = getUserById(userId);
			if( user != null ){
				//
				List<ReUserRole> newReUserRoleList = getReUserRoleByUserId(userId);
				if( isClearCache ){
					if( newReUserRoleList != null && newReUserRoleList.size() > 0  ){
						for( ReUserRole ur : newReUserRoleList ){
							commonDAO.evict(ur);
						}
						newReUserRoleList = getReUserRoleByUserId(userId);
					}
				}
				if( newReUserRoleList == null || newReUserRoleList.size() == 0 ){
					user.setRoleStatus(Buser.ROLE_STATUS_UN_ASSIGN);
				}else{
					String roleStatus = Buser.ROLE_STATUS_ASSIGNED; //已审核
					for( ReUserRole rur : newReUserRoleList ){
						String reUserRoleStatus = rur.getStatus();
						if( Buser.RE_ROLE_STATUS_UNCHECK.equals(reUserRoleStatus) ){ 
							roleStatus = Buser.ROLE_STATUS_ASSIGN_AUDITING; //待审核
							break;
						}else if ( Buser.RE_ROLE_STATUS_CHECKING.equals(reUserRoleStatus) ){
							roleStatus = Buser.ROLE_STATUS_AUDITING;
						}
					}
					user.setRoleStatus(roleStatus);
				}
			}
		}
	}

	public void deleteById(Long userId) {
		this.userDAO.deleteReUserRoleByUser(userId);
		this.userDAO.delete(userId);
		
	}

	public Buser getUserById(Long userId) {
		return this.userDAO.get(userId);
	}
	
	public Buser getUserByName(String userName) throws DAOException {
		return this.userDAO.getUserByUserName(userName);
	}
	
	public Buser getUserByNo(String userNo) throws DAOException {
		return this.userDAO.getUserByUserNo(userNo);
	}

	public void noLogon() throws ServiceException, RightsException {
		ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_NO_LOGON);
	}

	public void commitUserRoles(UserLogonInfo logonInfo,Long userId)
			throws ServiceException, DAOException {
		Buser user=userDAO.get(userId);
		List<ReUserRole> res=userDAO.findReUserRoleByUserId(userId);
		if(res!=null&&res.size()>0){
			for(ReUserRole re:res){
				if(!Buser.RE_ROLE_STATUS_UNCHECK.equals(re.getStatus()) ){
					ExceptionManager.throwException(ServiceException.class, ERROR_USER_ROLE_AUDIT_MUST_UN_CHECK);
				}
			}
		}
		auditService.commitAuditTask(logonInfo, getUserRoleCheckProNoByUserType(user), user);
		userDAO.updateReUserRoleStatusByUserId(userId, Buser.RE_ROLE_STATUS_CHECKING);
		modifyUserRoleStatus(userId,true);
	}
	
	public void batchCommitUserRoles(UserLogonInfo logonInfo,List<Long> ids) throws ServiceException,DAOException {
		if( ids != null && ids.size() > 0 ){
			for( Long id : ids ){
				commitUserRoles(logonInfo, id);
			}
		}
	}
	
	/**
	 * 
	 * 根据用户类型获取产品
	 *
	 * @return
	 */
	private String getUserRoleCheckProNoByUserType(Buser user){
		String ret = null;
		if( user != null  ){
			//机构普通用户
			if( StringUtils.equals(Buser.TYPE_BRCH_NOMAL_USER, user.getUserType()) ){
				ret = userRoleCheckProdNo;
			}else if ( StringUtils.equals(Buser.TYPE_BRCH_GLOBAL_MANAGER, user.getUserType()) || StringUtils.equals(Buser.TYPE_BRCH_LOCAL_MANAGER, user.getUserType()) ){
				ret = managerRoleCheckProdNo;
			}
		}
		return ret;
	}
	
	public void revokeUserRoles(UserLogonInfo logonInfo, Long userId)
			throws ServiceException, DAOException {
		Buser user=userDAO.get(userId);
		List<ReUserRole> res=userDAO.findReUserRoleByUserId(userId);
		if(res!=null&&res.size()>0){
			for(ReUserRole re:res){
				if(!Buser.RE_ROLE_STATUS_CHECKING.equals(re.getStatus()) ){
					ExceptionManager.throwException(ServiceException.class, ERROR_USER_ROLE_REVOKE_MUST_CHECKING);
				}
			}
		}
		AuditTaskRevokeResult revokeResult=auditService.revokeAuditTask(logonInfo,getUserRoleCheckProNoByUserType(user),user);
		if(revokeResult.isRevokePass()){
			userDAO.updateReUserRoleStatusByUserId(userId, Buser.RE_ROLE_STATUS_UNCHECK);
		}
		modifyUserRoleStatus(userId, true);
		LogUtil.getCommonLog().debug("用户角色分配撤销审批，撤销结果:"+revokeResult.isRevokePass());
	}
	
	public void batchRevokeUserRoles(UserLogonInfo logonInfo,List<Long> ids) throws ServiceException,DAOException{
		if( ids != null && ids.size() > 0 ){
			for( Long id : ids ){
				revokeUserRoles(logonInfo, id);
			}
		}
	}
	
	public void auditCallBack(AuditTask task, List<AuditProcess> processList)
			throws Exception {
		if(task!=null){
			
			String prodNo=task.getProdNo();
			if(AuditTask.STATUS_AUDIT_PASS.equals(task.getAuditStatus())){
				if(userRoleCheckProdNo.equals(prodNo)||managerRoleCheckProdNo.equals(prodNo)){
					userDAO.updateReUserRoleStatusByUserId(task.getAuditEntityId(), Buser.RE_ROLE_STATUS_CHECK);
				}
			}else{
				if(userRoleCheckProdNo.equals(prodNo)||managerRoleCheckProdNo.equals(prodNo)){
					userDAO.updateReUserRoleStatusByUserId(task.getAuditEntityId(), Buser.RE_ROLE_STATUS_UNCHECK);	
				}
			}
			modifyUserRoleStatus(task.getAuditEntityId());
		}
		
	}

	public AuditTask findUserRoleAuditTask(Long userId)
			throws ServiceException, DAOException {
		Buser user = getUserById(userId);
		return auditService.getLastAuditTask(getUserRoleCheckProNoByUserType(user),userId,Buser.class);
	}
	public UserLogonInfo logon(Buser user, boolean isEncrypt) throws AdapterRuntimeException{
		UserLogonInfo logonInfo= new UserLogonInfo();
		String userNo=user.getUserNo();	
		String miNo=user.getMiNo();
		String pwd=user.getPassword();
		String ip=user.getLastLoginIP();
		Date time=user.getLastLoginTm();
		if(StringUtils.isBlank(userNo) || StringUtils.isBlank(pwd) ){
			//1、用户标识或密码不能为空
			ExceptionManager.throwException(LoginException.class, ErrorCodeConst.LOGON_ERR_NULL);
		}
		//md5加密后的密码
		String md5Pwd=pwd;
		if( ! isEncrypt){
			md5Pwd= SourceTemplate.getBean(ISecurityManager.class,BeanNameConstants.SECURITY_MANAGER_BEAN).getPwdEncryptor().encryption(pwd);
		}
		
		Buser retUser=userDAO.getUserByUserNoAndMiNo(userNo,miNo);
		
		if(retUser==null){
			//2、用户必须是真实存在，用户名和密码不匹配
			ExceptionManager.throwException(LoginException.class, ErrorCodeConst.LOGON_ERR_INVALID);
		}else{
			String status=retUser.getStatus();
			String userType=retUser.getUserType();
			long errNum=retUser.getPwdErrNum()==null?0L:retUser.getPwdErrNum();
			Date pwdChgDt=retUser.getPwdChgDt()==null?DateTimeUtil.getNowDateTime():retUser.getPwdChgDt();
			retUser.setPwdChgDt(pwdChgDt);
			if(Buser.STATUS_CLOSE.equals(status)){
				//3、用户状态不能为关闭状态
				ExceptionManager.throwException(LoginException.class, ErrorCodeConst.LOGON_ERR_CLOSE);
			}
			if(Buser.STATUS_LOCK.equals(status)){
				ExceptionManager.throwException(LoginException.class, ErrorCodeConst.LOGON_ERR_LOCK);
			}
			List<Long> roleList=new ArrayList<Long>();
			//不同的用户类型做不同的处理
			if(Buser.TYPE_IMPLEMENTATION.equals(userType)||Buser.TYPE_SAAS_MANAGER.equals(userType)||Buser.TYPE_SAAS_MAINTENANCE.equals(userType)){
				//实施人员、saas端人员，没有接入信息和机构信息
				//用户名与密码校验
				if(!(md5Pwd.equals(retUser.getPassword()))){
					//用户名和密码不匹配
					ExceptionManager.throwException(LoginException.class, ErrorCodeConst.LOGON_ERR_INVALID);
				}
				roleList=userDAO.getAllredCheckedRoleIdsByUserId(retUser.getUserId());
			}else{
				if(StringUtils.isBlank(miNo)){
					ExceptionManager.throwException(LoginException.class, ErrorCodeConst.LOGON_ERR_MEMBER_NULL);
				}
				//机构总部管理员、机构管理员及普通人员拥有接入信息和机构信息
				Long brchId=retUser.getBrchId();
				if(brchId==null){
					ExceptionManager.throwException(LoginException.class, ErrorCodeConst.LOGON_ERR_BRCH_NULL);
				}
				Branch brch=branchDAO.getBranchByBrchId(brchId);
				if(brch==null){
					ExceptionManager.throwException(LoginException.class, ErrorCodeConst.LOGON_ERR_BRCH_NULL);
				}
				SysConfig config=sysConfigService.getConfigByMiNo(miNo,true);
				//系统配置
				long allowErrNum=config.getErrAllowNum();
				long pwdEffectDays=config.getPwdEffectDays();
				//3、用户名与密码校验
				
				if(!(md5Pwd.equals(retUser.getPassword()))){
					errNum++;
					//如果是密码连续错误次数达到系统配置的约束，则用户锁定
					if(allowErrNum>0){
						if(allowErrNum<=errNum){
							retUser.setStatus(Buser.STATUS_LOCK);
							retUser.setPwdErrNum(0L);
							userDAO.update(retUser);
							
							throw new AdapterRuntimeException(ExceptionManager.getException(LoginException.class, ErrorCodeConst.LOGON_ERR_LOCK));
						}
					}
					retUser.setPwdErrNum(errNum);
					userDAO.update(retUser);
					//用户名和密码不匹配
					throw new AdapterRuntimeException(ExceptionManager.getException(LoginException.class, ErrorCodeConst.LOGON_ERR_INVALID));
				}else{
					retUser.setPwdErrNum(0L);
				}
				//4、IP验证，如果系统配置为固定ip则，每次登录的ip必须一致
				String lastIp=retUser.getLastLoginIP();
				if(lastIp != null && lastIp.length() > 1){
					if(!sysConfigService.isAllowMultiIpLogon(miNo)){
						if(!lastIp.equals(ip)){
							ExceptionManager.throwException(LoginException.class, ErrorCodeConst.LOGON_ERR_IP);
						}
					}else{
						retUser.setLastLoginIP(ip);
					}
				}else
					retUser.setLastLoginIP(ip);
				
				//5、重复登录验证，如果系统配置为不能重复登录，则在用户状态为在线时，用户不能再次登录
				if(Buser.STATUS_ON_LINE.equals(status)){
					if(!sysConfigService.isAllowOnlineLogon(miNo)){
						ExceptionManager.throwException(LoginException.class, ErrorCodeConst.LOGON_ERR_ONLINE);
					}
				}
				
				
				//7、如果密码有效期限小于密码最近一次修改的期限，则拒绝访问，需要修改密码
				if(pwdEffectDays>0){
					int day=DateTimeUtil.getDaysBetween(pwdChgDt, time);
					if(pwdEffectDays<day){
						ExceptionManager.throwException(LoginException.class, ErrorCodeConst.LOGON_ERR_PWD_EFFECT);
					}
				}
				MemberInfo mi=memberDAO.get(miNo);
				if(MemberInfo.NOT_OPEN.equals(mi.getIsOpen())){
					//接入点未开启，不允许登录
					ExceptionManager.throwException(LoginException.class, ErrorCodeConst.LOGON_ERR_MEMBER_NOT_OPEN);
				}
				
				if(UtilConstant.CFS_AREA_MANAGER.equals(retUser.getPosCode())&&retUser.getAreaId()==null){
					UcarsHelper.throwServiceException("未设置管理区域,请联系管理员设置");
				}
				if(UtilConstant.CFS_DEPT_MANAGER.equals(retUser.getPosCode())&&retUser.getDeptId()==null){
					UcarsHelper.throwServiceException("未设置营业部,请联系管理员设置");
				}
				if(UtilConstant.CFS_TEAM_MANAGER.equals(retUser.getPosCode())&&retUser.getTeamId()==null){
					UcarsHelper.throwServiceException("未设置管理部门,请联系管理员设置");
				}
				if(UtilConstant.CFS_CUST_MANAGER.equals(retUser.getPosCode())&&retUser.getTeamId()==null){
					UcarsHelper.throwServiceException("未设置归属部门,请联系管理员设置");
				}
				logonInfo.setBranchId(brchId);
				logonInfo.setBranchName(brch.getBrchName());
				logonInfo.setMiNo(brch.getMiNo());
				logonInfo.setBranchNo(brch.getBrchNo());
				logonInfo.setBranchTreeCode(brch.getTreeCode());
				logonInfo.setBranchParentTreeCode(brch.getParentTreeCode());
				logonInfo.setAreaId(retUser.getAreaId());
				logonInfo.setDeptId(retUser.getDeptId());
				logonInfo.setTeamId(retUser.getTeamId());
				logonInfo.setPosCode(retUser.getPosCode());
				if(Buser.TYPE_BRCH_GLOBAL_MANAGER.equals(userType)){
					roleList=userDAO.getRoleIdsByUserId(retUser.getUserId());
				}else{
					roleList=userDAO.getAllredCheckedRoleIdsByUserId(retUser.getUserId());
				}
				
			}
			//8、获取用户的角色集合
			if(roleList==null||roleList.size()<1){
				ExceptionManager.throwException(LoginException.class, ErrorCodeConst.SECURITY_NO_ROLE_ERROR);
			}
			//9、用户状态更新,在线
			retUser.setStatus(Buser.STATUS_ON_LINE);
			retUser.setLastLoginTm(new Date(System.currentTimeMillis()));
			userDAO.update(retUser);
			
			
			//设置用户登录信息
			logonInfo.setUserType(userType);
			logonInfo.setUserNo(userNo);
			logonInfo.setUserName(retUser.getUserName());
			logonInfo.setLastLogonIP(ip);
			logonInfo.setLastOperTime(time.getTime());
			logonInfo.setSysUserId(retUser.getUserId());
			logonInfo.setRoleList(roleList);
		}
			
		return logonInfo;
	}



	public void setUserStatus(List<Long> ids,String status) throws ServiceException,DAOException{
		if( ids != null && ids.size() > 0 && status != null && status.trim().length() > 0 ){
			List<Buser> userList = userDAO.getUserListByIds(ids);
			for( Buser b : userList ){
				b.setStatus(status);
			}
		}
	}

	public boolean isAuthenticated(HttpServletRequest httpRequest) {
//		System.out.println(httpRequest.getSession().getId());
		UserLogonInfo logonInfo=(UserLogonInfo)httpRequest.getSession().getAttribute(SessionKeyConst.USER_LOGON_INFO);
		if(logonInfo==null){
			return false;
		}else{
			//未绑定角色用户不能访问
			List<Long> roles=logonInfo.getRoleList();
			if(roles==null||roles.size()<1){
				return false;
			}
			return true;
		}
	}

	public void afterSessionRelease(Long userId) {
		offLine(userId);
	}

	public Buser getUserByUserNoAndMiNo(String userNo, String miNo) {
	
		return this.userDAO.getUserByUserNoAndMiNo(userNo, miNo);
	}
	
	/**
	 * 判断用户是否分配了角色
	 * @param userId 用户id
	 * @return true:分配角色 false:未分配角色
	 */
	public boolean hasRoles(Long userId) throws ServiceException,DAOException{
		return this.userRoleDAO.hasRoles(userId);
	}

	public List<Buser> getUserByRole(String roleType) {
		return userRoleDAO.getUserByRole(roleType);
	}
	
	public List<Buser> getUserByRoleName(String roleName){
		return userRoleDAO.getUserByRoleName(roleName);
	}

	public List<Buser> getUserByRoleNameAndBrch(String roleName,Long brchId){
		return userRoleDAO.getUsersByRoleNameAndBrch(roleName,brchId);
	}
	
	public List<Buser> getUserByRoleIdAndBrch(Long roleId,Long brchId){
		return userRoleDAO.getUsersByRoleIdAndBrch(roleId, brchId);
	}
	
	public List<Buser> getUsersByRoleAndBrch(String roleName,String brchNo){
		return userRoleDAO.getUsersByRoleAndBrch(roleName,brchNo);
	}

	@Override
	public List<Buser> getUserByIdList(List<Long> idList) {
		return userDAO.getUserListByIds(idList);
	}

	@Override
	public List<Buser> getUserByDeptId(Long deptId) {
		return userDAO.getUserByDeptId(deptId);
	}

	@Override
	public List<Buser> getUserByAreaId(Long areaId) {
		return userDAO.getUserByAreaId(areaId);
	}

	@Override
	public Buser getUserByTeamIdAndPosCode(Long teamId, String posCode) {
		return userDAO.getUserByTeamIdAndPosCode(teamId, posCode);
	}

	@Override
	public Buser getUserByDeptIdAndPosCode(Long deptId, String posCode) {
		return userDAO.getUserByDeptIdAndPosCode(deptId, posCode);
	}

	@Override
	public Buser getUserByAreaIdAndPosCode(Long areaId, String posCode) {
		return userDAO.getUserByAreaIdAndPosCode(areaId, posCode)
				;
	}

	@Override
	public List<Buser> getCanSetBuser(String posCode) {
		return userDAO.getCanSetBuser(posCode);
	}

	@Override
	public List<Buser> getUserByTeamId(Long teamId) {
		return userDAO.getUserByTeamId(teamId);
	}
}
