package com.upg.ucars.basesystem.security.core.role;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.security.ReRoleSysfunc;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;

public class RoleFuncDAOImpl extends BaseDAO<ReRoleSysfunc, Long> implements IRoleFuncDAO {

	public void addRoleFunc(ReRoleSysfunc rrs) throws DAOException {
		getHibernateTemplate().save(rrs);

	}

	public void deleteRole(ReRoleSysfunc rrs) throws DAOException {
		getHibernateTemplate().delete(rrs);

	}
	
	public void updateRole(ReRoleSysfunc rrs) throws DAOException {
		getHibernateTemplate().update(rrs);
	}


	public List<Long> getReFuncIdsByRoleId(Long roleId) throws DAOException {
		String hql = "select rrs.funcId from ReRoleSysfunc as rrs where rrs.roleId=?";
		List<Long> list = getHibernateTemplate().find(hql, roleId);
		return list;
	}
	
	public List getUnCheckSysfuncs(Long roleId) throws DAOException{
		String HQL="select sysfunc from ReRoleSysfunc as rolefunc,Sysfunc as sysfunc where rolefunc.funcId=sysfunc.funcId and rolefunc.roleId=? and rolefunc.status=? order by rolefunc.funcId";
		
		List  list=null;
		try{
			list=this.getHibernateTemplate().find(HQL, roleId, RoleConstant.ROLE_STATUS_UNCHECK);
		}catch(Exception e){
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] {HQL}, e);
		}
		return list;
	}
	
	public List getCheckedSysfuncs(Long roleId) throws DAOException{
		String HQL="select sysfunc from ReRoleSysfunc as rolefunc,Sysfunc as sysfunc where rolefunc.funcId=sysfunc.funcId and rolefunc.roleId=? and rolefunc.status=? order by rolefunc.funcId";
		
		List  list=null;
		try{
			list=this.getHibernateTemplate().find(HQL, roleId, RoleConstant.ROLE_STATUS_CHECK);
		}catch(Exception e){
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] {HQL}, e);
		}
		return list;
	}
	
	public void checkRoleSyfuncs(Long roleId) throws DAOException{
		String hql = "update ReRoleSysfunc set status=? where roleId="+roleId;
		try{			
			this.getHibernateTemplate().bulkUpdate(hql, RoleConstant.ROLE_STATUS_CHECK);
		}catch(Exception e){
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] {hql}, e);
		}
	}
	

	public List<ReRoleSysfunc> getReRoleSysfuncByRoleId(Long roleId) throws ServiceException {
		String sql = "from ReRoleSysfunc as rrs where rrs.roleId=?";
		try {			
			return this.getHibernateTemplate().find(sql,roleId);
		} catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] {sql}, e);
			
		}
		
	}
	
	
	public void deleteByRoleId(Long roleId) throws DAOException {
		String hql = "delete ReRoleSysfunc where roleId=?";
		try {			
			this.getHibernateTemplate().bulkUpdate(hql, roleId);
		} catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] {hql}, e);
			
		}
	}

	@Override
	public Class getEntityClass() {		
		return ReRoleSysfunc.class;
	}

	public List<Sysfunc> getAllSysfuncs() throws DAOException {
		List<Sysfunc> list = this.getHibernateTemplate().find("from Sysfunc ");
		return list;
	}

	public List<Sysfunc> findSysfuncByRoleId(Long roleId) throws DAOException {
		String hql="select sysfunc from ReRoleSysfunc as rolefunc,Sysfunc as sysfunc where rolefunc.funcId=sysfunc.funcId and rolefunc.roleId=? order by sysfunc.funcId";
		
		try {			
			return this.getHibernateTemplate().find(hql,roleId);
		} catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] {hql}, e);
			
		}
	}

	public List<Long> findSysfuncIdsByRoleId(Long roleId) throws DAOException {
		String hql="select rolefunc.funcId from ReRoleSysfunc as rolefunc where rolefunc.roleId=?";
		
		try {			
			return this.getHibernateTemplate().find(hql,roleId);
		} catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] {hql}, e);
			
		}
	}
	public List<Long> getRoleIdsByFuncId(Long funcId) throws DAOException {
		String hql="select rolefunc.roleId from ReRoleSysfunc as rolefunc where rolefunc.funcId=?";
		
		try {			
			return this.getHibernateTemplate().find(hql,funcId);
		} catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] {hql}, e);
			
		}
	}
	public ReRoleSysfunc findByRoleIdAndFuncId(Long roleId, Long funcId)
			throws DAOException {
		String hql="from ReRoleSysfunc as r where r.roleId=:roleId and r.funcId=:funcId";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("roleId", roleId);
		map.put("funcId", funcId);
		List<ReRoleSysfunc> rList=queryByParam(hql,map,null);
		if(rList!=null&&rList.size()>0){
			return rList.get(0);
		}
		return null;
	}

	public boolean hasRoleRefFunc(Long funcId) throws DAOException {
		String hql="select count(*) from ReRoleSysfunc t where t.funcId=:funcId";
		try{
			int count=0;
			Map<String, Object> parameterMap = new HashMap<String, Object>(1);
			parameterMap.put("funcId", funcId);
			List list = super.queryByParam(hql, parameterMap, null);
			count=Integer.parseInt(""+list.get(0));
			return count>0;
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return false;
	}

	
}
