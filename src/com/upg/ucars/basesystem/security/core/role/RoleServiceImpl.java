package com.upg.ucars.basesystem.security.core.role;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.basesystem.security.core.branch.BranchHelper;
import com.upg.ucars.basesystem.security.core.branch.IBranchService;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.ICommonDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.ReRoleSysfunc;
import com.upg.ucars.mapping.basesystem.security.Role;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.CommonUtil;
import com.upg.ucars.util.SourceTemplate;
import com.upg.ucars.util.StringUtil;

public class RoleServiceImpl extends BaseService implements IRoleService{
	
	/** 删除已经分配给用户的角色错误  */
	private static final String EDIT_HAS_USER_ROLE_ERROR = "EDIT_HAS_USER_ROLE_ERROR";
	
	/** 角色 */
	private IRoleDAO roleDAO;
	/** 角色权限 */
	private IRoleFuncDAO roleFuncDAO;
	/** 用户服务 */
	private IUserService userService;
	/**机构服务*/
	private IBranchService branchService;
	/** 通用DAO */
	private ICommonDAO commonDAO;
	
	public void saveRole(Role role) throws ServiceException{
		checkExistSameRole(role);
		roleDAO.save(role);
	}
	public void updateRole(Role role) throws ServiceException {
		checkExistSameRole(role);
		if ( isChangeRoleTypeOrBranch(role) ){//修改了类型
			//已经分配给用户
			List<Buser> list = userService.queryRoleUser(role.getRoleId(), null, null);
			if( list != null && list.size() > 0 ){
				ExceptionManager.throwException(ServiceException.class, EDIT_HAS_USER_ROLE_ERROR);
			}
			//已经有权限
			List<Long> reList = this.roleFuncDAO.getReFuncIdsByRoleId(role.getRoleId());
			if ( reList != null && !reList.isEmpty()){
				ExceptionManager.throwException(ServiceException.class, ErrorCodeConst.SECURITY_ROLEUPDATE_SETEDFUNCTION);
				
			}
		}
		roleDAO.merge(role);
	}	
	
	/**
	 * 
	 * 是否改变了角色类型或者机构
	 *
	 * @param role
	 * @return
	 */
	private boolean isChangeRoleTypeOrBranch(Role role){
		boolean ret = false;
		if( role != null ){
			Role dbRole = roleDAO.get(role.getRoleId());
			if( ! StringUtils.equals(role.getRoleType(), dbRole.getRoleType()) ){ //类型修改
				ret = true;
			}else if( !StringUtils.equals(String.valueOf(role.getBrchId()), String.valueOf(dbRole.getBrchId())) ){ //机构修改
				ret = true;
			}
		}
		return ret;
	}
	
	/**
	 * 
	 * 判断同一个机构下面是否有相同的角色存在
	 *
	 * @param role
	 */
	private void checkExistSameRole(Role role){
		if( role != null ){
			String roleName = role.getRoleName();
			String roleType = role.getRoleType();
			Long brchId = role.getBrchId();
			Long roleId = role.getRoleId();
			List<Role> existList = null;
			List<Object> paramList = new ArrayList<Object>();
			String hql = "from Role where roleName=? and roleType=? ";
			paramList.add(roleName);
			paramList.add(roleType);
			if( brchId != null ){
				hql += " and brchId= ? ";
				paramList.add(brchId);
			}
			if( roleId != null ){
				hql += " and roleId <> ?";
				paramList.add(roleId);
			}
			existList = commonDAO.find(hql, paramList.toArray());
			if( existList != null && existList.size() > 0 ){
				throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.SECURITY_ROLENAME_EXIST, new String[]{role.getRoleName()});
			}
		}
	}
	
	public void setRoleDAO(IRoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}	
	
	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}
	public void setRoleFuncDAO(IRoleFuncDAO roleFuncDAO) {
		this.roleFuncDAO = roleFuncDAO;
	}
	public void setCommonDAO(ICommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	public Role getRoleById(Long roleId) throws ServiceException {
		return roleDAO.get(roleId);
		
	}
	public List<Role> getRolesByBrchId(Long brchId) throws ServiceException {
		String hql = "from Role r where r.brchId=?";
		return this.commonDAO.find(hql, brchId);
		
	}
	public List<Role> queryRoles(List<ConditionBean> conditionList, Page page)
			throws ServiceException {
		return this.roleDAO.queryEntity(conditionList, page);
		
	}
	public List<Role> queryRoles(QueryComponent qcpt, Page page)
			throws ServiceException {
		return this.roleDAO.queryEntity(qcpt, page);
		
	}
	
	public void checkRoleSyfuncs(Long roleId) throws ServiceException {
		roleFuncDAO.checkRoleSyfuncs(roleId);
		
	}
	public List<Role> queryRolesByType(String[] roleTypes,
			List<ConditionBean> conditionList, Page page)
			throws ServiceException {
		String hql = "from Role role ";
		QueryCondition qc = new QueryCondition(hql, "role.roleId");
		qc.addConditionList(conditionList);
		if (roleTypes!=null && roleTypes.length>0){
			ConditionBean cb = new ConditionBean("role.roleType",ConditionBean.IN, roleTypes);
			qc.addCondition(cb);
		}
		
		qc.addOrder(new OrderBean("convert_gbk( 'role.roleName')"));
		
		List<Role> list = roleDAO.queryByCondition(qc, page);
		
		return list;
	}
	public void deleteById(Long id) throws ServiceException {
		if( id != null ){
			Role role = roleDAO.get(id);
			List<Buser> list = userService.queryRoleUser(id, null, null);
			if( list != null && list.size() > 0 ){
				ExceptionManager.throwException(ServiceException.class, EDIT_HAS_USER_ROLE_ERROR);
			}
			roleDAO.delete(role);
			roleFuncDAO.deleteByRoleId(id);
		}
	}
	
	public void deleteByIdList(List<Long> idList){
		if( idList != null && idList.size() > 0 ){
			for( Long id : idList ){
				deleteById(id);
			}
		}
	}
	
	public void buildRoleSysfuncs(Long roleId, List<Long> sysfuncIdList)
			throws ServiceException {
		//原先关联的权限主键
		 List<Long> oldFuncIdList = roleFuncDAO.getReFuncIdsByRoleId(roleId);
		 
		
		for (Long funcId : sysfuncIdList) {
			if (oldFuncIdList.remove(funcId)){				
				continue;
			}
			
			ReRoleSysfunc rrs = new ReRoleSysfunc();
			rrs.setStatus(RoleConstant.ROLE_STATUS_CHECK);
			rrs.setRoleId(roleId);
			rrs.setFuncId(funcId);
			roleFuncDAO.addRoleFunc(rrs);//保存增加的关联。
		}
		
		List<ReRoleSysfunc> rList=new ArrayList<ReRoleSysfunc>();
		for (Long unFuncId : oldFuncIdList ){//删除关联
			ReRoleSysfunc re=roleFuncDAO.findByRoleIdAndFuncId(roleId,unFuncId);
			if(re!=null){
				rList.add(re);
			}
		}
		roleFuncDAO.delAll(rList);
		
	}
	public List<Sysfunc> findAllowAssignFunction(String roleType,UserLogonInfo logoner)
			throws ServiceException {
		ArrayList<ConditionBean> conditionList = new ArrayList<ConditionBean>(1);
		conditionList.add(new ConditionBean("id", ConditionBean.IN, logoner.getRoleList()));
		List<Role> roleList = this.roleDAO.queryEntity(conditionList, null);
		 
		List<String> roleTypeList = new ArrayList<String>(roleList.size());
		for (Role o : roleList) {
			roleTypeList.add(o.getRoleType());
		}
		
		List<Sysfunc> funcList=new ArrayList<Sysfunc>(0);
		if (roleTypeList.contains(RoleConstant.ROLE_TYPE_APP)){
			funcList=roleFuncDAO.getAllSysfuncs();
		}else{ 
			if (roleTypeList.contains(RoleConstant.ROLE_TYPE_SAAS_MNG)){
				funcList=roleFuncDAO.getAllSysfuncs();
			}else{
				if (roleTypeList.contains(RoleConstant.ROLE_TYPE_CENTRE)){
					funcList = SourceTemplate.getBean(IBranchService.class,BeanNameConstants.BRANCH_SERVICE).getAllredCheckedFunctions(logoner.getBranchId());
				}else{
					if (roleTypeList.contains(RoleConstant.ROLE_TYPE_BRANCH)){
						funcList = SourceTemplate.getBean(IBranchService.class,BeanNameConstants.BRANCH_SERVICE).getAllredCheckedFunctions(logoner.getBranchId());
					}
				}
			}
		}
		
		return filterFuncList(funcList,roleType);

	}
	
	
	/**
	 * 根据待分配的角色类型，过滤权限列表
	 * @param funcList 权限列表
	 * @param roleType 角色类型
	 */
	private List<Sysfunc>  filterFuncList(List<Sysfunc> funcList,String roleType){
		List<Sysfunc> result=new ArrayList<Sysfunc>();
		String type="0";
		if(RoleConstant.ROLE_TYPE_APP.equals(roleType)){
			//实施
			type=Sysfunc.USE_TYPE_APP;
		}
		if(RoleConstant.ROLE_TYPE_SAAS_MNG.equals(roleType)||RoleConstant.ROLE_TYPE_SAAS_OPER.equals(roleType)){
			//saas
			type=Sysfunc.USE_TYPE_SAAS;
		}
		if(RoleConstant.ROLE_TYPE_CENTRE.equals(roleType)||RoleConstant.ROLE_TYPE_BRANCH.equals(roleType)){
			//机构
			type=Sysfunc.USE_TYPE_BRCH;
		}
		if(RoleConstant.ROLE_TYPE_COMMON.equals(roleType) || RoleConstant.ROLE_TYPE_JUDGMENT.equals(roleType)){
			type=Sysfunc.USE_TYPE_BUSI;
		}
		List<Sysfunc> childs=new ArrayList<Sysfunc>();
		for(int i=0;i<funcList.size();i++){
			if(funcList.get(i).getUrl()==null||funcList.get(i).getUrl().length()<1){
				result.add(funcList.get(i));
			}else{
				if(type.equals(funcList.get(i).getUseType())){
					result.add(funcList.get(i));
					childs.add(funcList.get(i));
				}
			}
			
		}
		List<Sysfunc> sList=new ArrayList<Sysfunc>();
		for(int i=0;i<childs.size();i++){
			hand(childs.get(i),result,sList);
		}
		List<Sysfunc> endList=new ArrayList<Sysfunc>();
		endList.addAll(childs);
		endList.addAll(sList);
		
		return CommonUtil.removeDuplicate(endList);
	}
	
	private void hand(Sysfunc func,List<Sysfunc> result,List<Sysfunc> sList){
		Long tmp=func.getParentFuncId();
		if(tmp!=null){
			Sysfunc tmpFunc=new Sysfunc();
			tmpFunc.setFuncId(tmp);
			if(result.contains(tmpFunc)){
				Sysfunc parentFunc=result.get(result.indexOf(tmpFunc));
				if(!sList.contains(parentFunc)){
					sList.add(parentFunc);
				}
				hand(parentFunc,result,sList);
			}
		}
		
	}
	public List<Sysfunc> findSysfuncByRoleId(Long roleId) throws ServiceException {
		return roleFuncDAO.findSysfuncByRoleId(roleId);
	}
	public List<Long> findRoleIdsByFuncId(Long funcId) throws DAOException {
		return roleFuncDAO.getRoleIdsByFuncId(funcId);
	}
	
	public List<Role> queryByCondition(QueryCondition qc, Page page)
			throws DAOException {
		
		return this.roleDAO.queryByCondition(qc, page);
	}
	public List<Long> findSysfuncIdsByRoleId(Long roleId) throws DAOException {
		return roleFuncDAO.findSysfuncIdsByRoleId(roleId);
	}
	public void addFunc(Long roleId, List<Long> funcIdList) throws DAOException {
		List<ReRoleSysfunc> list=new ArrayList<ReRoleSysfunc>();
		for (Long funcId : funcIdList) {
			ReRoleSysfunc rrs=roleFuncDAO.findByRoleIdAndFuncId(roleId, funcId);
			if(rrs==null){
				rrs = new ReRoleSysfunc();
				rrs.setStatus(RoleConstant.ROLE_STATUS_CHECK);
				rrs.setRoleId(roleId);
				rrs.setFuncId(funcId);
				list.add(rrs);
			}
			
		}
		if(list.size()>0){
			roleFuncDAO.saveOrUpdateAll(list);
		}
		
	}
	public void removeFunc(Long roleId, List<Long> funcIdList)
			throws DAOException {
		List<ReRoleSysfunc> rList=new ArrayList<ReRoleSysfunc>();
		for (Long unFuncId : funcIdList ){//删除关联
			ReRoleSysfunc re=roleFuncDAO.findByRoleIdAndFuncId(roleId,unFuncId);
			if(re!=null){
				rList.add(re);
			}
		}
		roleFuncDAO.delAll(rList);
	}
	public List<Role> findAvailableRolesByBranch(Long brchId,
			boolean containParent,Page page) throws ServiceException, DAOException {
		String hql = "select role from Role role, Branch branch where role.brchId=branch.brchId ";
		QueryCondition qc = new QueryCondition(hql);
		ArrayList<ConditionBean> conditionList = new ArrayList<ConditionBean>(1);
		if(containParent){
			Branch branch = branchService.getBranchByBrchId(brchId);	
			ArrayList<String> treeCodeList = new ArrayList<String>(3);
			treeCodeList.add(branch.getTreeCode());
			String treeCode = branch.getTreeCode();
			while (!StringUtil.isEmpty(BranchHelper.getParentTreeCode(treeCode))){
				treeCode=BranchHelper.getParentTreeCode(treeCode);
				treeCodeList.add(treeCode);
			}
			ConditionBean cond = new ConditionBean("branch.treeCode", ConditionBean.IN, treeCodeList);
			conditionList.add(cond);
		}else{
			ConditionBean cond = new ConditionBean("branch.brchId",  brchId);
			conditionList.add(cond);
		}
		
		
		qc.addConditionList(conditionList);
		return queryByCondition(qc, page);
	}

	
	
}
