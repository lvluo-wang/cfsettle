package com.upg.ucars.basesystem.security.core.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upg.cfsettle.util.UtilConstant;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.ReUserRole;
import com.upg.ucars.mapping.basesystem.security.Role;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.util.StringUtil;


public class UserDAOImp extends BaseDAO<Buser,Long> implements IUserDAO {

	public Buser getUserByUserNo(String userNo) {
		String hql="from Buser where userNo=?";
		Buser user=null;
		try{
			List list=this.getHibernateTemplate().find(hql, userNo);
			if (list.isEmpty())
				return null;
			user=(Buser)list.get(0);
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return user;
	}
	
	public Buser getUserByUserName(String userName) {
		String hql="from Buser where userName=?";
		Buser user=null;
		try{
			List list=this.getHibernateTemplate().find(hql, userName);
			if (list.isEmpty())
				return null;
			user=(Buser)list.get(0);
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return user;
	}

	public List<Role> getRolesByUserNo(String userNo) {
		String hql="select role from ReUserRole re,Buser user,Role role " +
				"where user.userId=re.userId and role.roleId=re.roleId " +
				"and user.userNo=?";
		List<Role> list=null;
		try{
			list=this.getHibernateTemplate().find(hql,userNo);
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return list;
	}
	public List getAllredCheckedRolesByUserNo(String userNo)
			throws DAOException {
		String hql="select role from ReUserRole re,Buser user,Role role " +
		"where user.userId=re.userId and role.roleId=re.roleId " +
		"and user.userNo=:userNo and re.status=:status";
		List list=null;
		try{
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("userNo", userNo);
			map.put("status", Buser.RE_ROLE_STATUS_CHECK);
			list=queryByParam(hql,map,null);
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return list;
	}

	@Override
	public Class getEntityClass() {
		return Buser.class;
	}

	public void addReUserRole(ReUserRole rur) throws DAOException {
		this.getHibernateTemplate().save(rur);
		
	}

	public void updateReUserRole(ReUserRole rur) throws DAOException {
		this.getHibernateTemplate().update(rur);
		
	}

	public void deleteReUserRole(ReUserRole rur) throws DAOException {
		this.getHibernateTemplate().delete(rur);
		
	}

	public void deleteReUserRoleByUser(Long userId) throws DAOException {
		this.getHibernateTemplate().bulkUpdate("delete ReUserRole  o where o.userId=?", userId);
		
	}

	public List<Role> queryUserRole(Long userId) throws DAOException {
		String hql = "select r from ReUserRole o , Role r where o.roleId = r.roleId and o.userId=?";
		List<Role> list = this.getHibernateTemplate().find(hql, userId);
		return list;
	}
	

	public List<Sysfunc> queryUserFunction(Long userId) throws DAOException {
		String hql = "select distinct f from ReUserRole ur , Role r, ReRoleSysfunc rf, Sysfunc f   " +
				"where ur.userId=? and ur.roleId = r.roleId and r.roleId = rf.roleId and rf.funcId=f.funcId ";
		List<Sysfunc> list = this.getHibernateTemplate().find(hql, userId);
		return list;
	}
	
	/**
	 * 查询用户拥有的权限
	 * @param userId
	 * @return funcId列表
	 * @throws DAOException
	 */
	public List<Long> queryUserFunctionWithCache(Long userId)throws DAOException{
		if(userId == null || userId.intValue() <= 0) return null;
		String hql = "select distinct f.funcId from ReUserRole ur , Role r, ReRoleSysfunc rf, Sysfunc f   " +
					 "where ur.userId=:userId and ur.roleId = r.roleId and r.roleId = rf.roleId and rf.funcId=f.funcId ";
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		query.setLong("userId", userId);
		query.setCacheable(true);
		query.setCacheRegion("queryFuncIdByUserId");
		List<Long> funcIds = query.list();
		return funcIds;
	}
	
	public List<Sysfunc> queryUserFunc(Long userId,String userRoleStatus) throws DAOException {
		String hql = "select distinct f from ReUserRole ur , Role r, ReRoleSysfunc rf, Sysfunc f " +
				     "where ur.roleId = r.roleId and r.roleId = rf.roleId and rf.funcId=f.funcId ";
		if(userId != null)
			hql = hql + " and ur.userId="+userId;
		if(!StringUtil.isEmpty(userRoleStatus))
			hql = hql + " and ur.status='"+userRoleStatus+"'";
		List<Sysfunc> list = this.getHibernateTemplate().find(hql);
		return list;
	}
	

	public void updateReUserRoleStatusByUserId(Long userId, String roleStatus)
			throws DAOException {
		String hql = "update ReUserRole o set o.status=? where o.userId=?";
		this.getHibernateTemplate().bulkUpdate(hql, roleStatus, userId);
		
	}

	public List<ReUserRole> findReUserRoleByUserId(Long userId)
			throws DAOException {
		String hql="from ReUserRole re where re.userId=:userId";
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("userId", userId);
		return queryByParam(hql,map,null);
	}

	public Buser getUserByUserNoAndMiNo(String userNo, String miNo) {
		Buser ret = null;
		if(StringUtils.isNotBlank(userNo) ){
			String hql="from Buser where userNo=:userNo";
			HashMap<String, Object> map=new HashMap<String, Object>();
			map.put("userNo", userNo);
			if( StringUtils.isNotBlank(miNo) ){
				hql += " and miNo=:miNo";
				map.put("miNo", miNo);
			}else{
				hql += " and miNo is null";
			}
			List<Buser> list=queryByParam(hql,map,null);
			if( list != null && list.size() == 1 ){
				ret = (Buser) list.get(0);
			}
		}
		return ret;
	}

	public boolean isExistUserByUserNo(String userNo) throws DAOException {
		String hql="select count(*) from Buser where userNo=:userNo";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userNo", userNo);
		List list=queryByParam(hql,map,null);
		int count=0;
		if(list!=null&&list.size()>0){
			count=Integer.valueOf(list.get(0).toString());
		}
		return count>0;
	}

	public boolean isExistUserByuserNoAndMiNo(String userNo, String miNo)
			throws DAOException {
		String hql="select count(*) from Buser where userNo=:userNo";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userNo", userNo);
		if(miNo!=null){
			hql="select count(*) from Buser where userNo=:userNo and miNo=:miNo";
			map.put("miNo", miNo);
		}
		List list=queryByParam(hql,map,null);
		int count=0;
		if(list!=null&&list.size()>0){
			count=Integer.valueOf(list.get(0).toString());
		}
		return count>0;
	}

	public boolean updateUserRegxUserNo(Buser user) throws DAOException {
		String hql="select count(*) from Buser where userNo=:userNo and userId <>:userId";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userNo", user.getUserNo());
		map.put("userId", user.getUserId());
		if(user.getMiNo()!=null){
			hql="select count(*) from Buser where userNo=:userNo and miNo=:miNo and userId<>:userId";
			map.put("miNo", user.getMiNo());
		}
		List list=queryByParam(hql,map,null);
		int count=0;
		if(list!=null&&list.size()>0){
			count=Integer.valueOf(list.get(0).toString());
		}
		return count>0;
	}

	public List<Role> getAllredCheckedRolesByUserId(Long userId) throws DAOException {
		String hql="select role from ReUserRole re,Role role " +
		"where role.roleId=re.roleId " +
		"and re.userId=:userId and re.status=:status";
		List<Role> list=null;
		try{
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("userId", userId);
			map.put("status", Buser.RE_ROLE_STATUS_CHECK);
			list=queryByParam(hql,map,null);
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return list;
	}
	public List<Long> getAllredCheckedRoleIdsByUserId(Long userId) throws DAOException {
		String hql="select role.roleId from ReUserRole re,Role role " +
		"where role.roleId=re.roleId " +
		"and re.userId=:userId and re.status=:status";
		List<Long> list=null;
		try{
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("userId", userId);
			map.put("status", Buser.RE_ROLE_STATUS_CHECK);
			list=queryByParam(hql,map,null);
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return list;
	}
	public List<Role> getRolesByUserId(Long userId) throws DAOException {
		String hql="select role from ReUserRole re,Role role " +
		"where role.roleId=re.roleId " +
		"and re.userId=?";
		List<Role> list=null;
		try{
			list=this.getHibernateTemplate().find(hql,userId);
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return list;
	}
	public List<Long> getRoleIdsByUserId(Long userId) throws DAOException {
		String hql="select role.roleId from ReUserRole re,Role role " +
		"where role.roleId=re.roleId " +
		"and re.userId=?";
		List<Long> list=null;
		try{
			list=this.getHibernateTemplate().find(hql,userId);
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<Buser> getUserListByIds(List<Long> ids) throws DAOException{
		StringBuilder hql = new StringBuilder("from Buser user where user.userId in (");
		List<Buser> ret = null;
		try{
			for( int i = 0 ; i < ids.size(); i++ ){
				hql.append(ids.get(i));
				if( i < ids.size() - 1 ){
					hql.append(",");
				}
			}
			hql.append(")");
			ret = ( List<Buser> ) getHibernateTemplate().find(hql.toString());
		}catch(Throwable t){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql.toString()}, t);
		}
		return ret == null ? new ArrayList<Buser>(0) : ret;
	}

	@Override
	public List<Buser> getUserByDeptId(Long deptId) {
		String hql = "select u from Buser u where u.deptId=? and u.status !=4" +
				" and u.posCode in ('01','02','03')";
		return find(hql,deptId);
	}

	@Override
	public List<Buser> getUserByAreaId(Long areaId) {
		String hql = "select u from Buser u where u.areaId=? and u.status !=4" +
				" and u.posCode in ('01','02','03','04')";
		return find(hql,areaId);
	}

	@Override
	public List<Long> getUserIdByTeamId(Long teamId) {
		StringBuilder hql = new StringBuilder("select u.userId from Buser u where u.teamId=? and u.posCode in (");
		List<Long> list=null;
		try {
			String posCodeArr[] = new String[]{UtilConstant.CFS_TEAM_MANAGER,UtilConstant.CFS_CUST_MANAGER};
			int length = posCodeArr.length;
			for( int i = 0 ; i < length; i++ ){
				hql.append("'"+posCodeArr[i]+"'");
				if( i < length - 1 ){
					hql.append(",");
				}
			}
			hql.append(")");
			list = getHibernateTemplate().find(hql.toString(),teamId);
		}catch(Throwable t){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql.toString()}, t);
		}

		return list == null ? new ArrayList<Long>(0) : list;
	}

	@Override
	public List<Long> getUserIdByDeptId(Long deptId) {
		StringBuilder hql = new StringBuilder("select u.userId from Buser u where u.deptId=? and u.posCode in (");
		List<Long> list=null;
		try {
			String posCodeArr[] = new String[]{UtilConstant.CFS_TEAM_MANAGER,UtilConstant.CFS_CUST_MANAGER,UtilConstant.CFS_DEPT_MANAGER};
			int length = posCodeArr.length;
			for( int i = 0 ; i < length; i++ ){
				hql.append("'"+posCodeArr[i]+"'");
				if( i < length - 1 ){
					hql.append(",");
				}
			}
			hql.append(")");
			list = getHibernateTemplate().find(hql.toString(),deptId);
		}catch(Throwable t){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql.toString()}, t);
		}

		return list == null ? new ArrayList<Long>(0) : list;
	}

	@Override
	public List<Long> getUserIdByAreaId(Long areaId) {
		StringBuilder hql = new StringBuilder("select u.userId from Buser u where u.areaId=? and u.posCode in (");
		List<Long> list=null;
		try {
			String posCodeArr[] = new String[]{UtilConstant.CFS_TEAM_MANAGER,UtilConstant.CFS_CUST_MANAGER,UtilConstant.CFS_DEPT_MANAGER,UtilConstant.CFS_AREA_MANAGER};
			int length = posCodeArr.length;
			for( int i = 0 ; i < length; i++ ){
				hql.append("'"+posCodeArr[i]+"'");
				if( i < length - 1 ){
					hql.append(",");
				}
			}
			hql.append(")");
			list = getHibernateTemplate().find(hql.toString(),areaId);
		}catch(Throwable t){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql.toString()}, t);
		}

		return list == null ? new ArrayList<Long>(0) : list;
	}

	@Override
	public Buser getUserByTeamIdAndPosCode(Long teamId, String posCode) {
		String hql = "from Buser where teamId=? and posCode=? and status<>4";
		List<Buser> ret = null;
		try{
			ret = getHibernateTemplate().find(hql,new Object[]{teamId,posCode});
		}catch(Throwable t){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql.toString()}, t);
		}
		return ret.size() >0 ? ret.get(0) : null;
	}

	@Override
	public Buser getUserByDeptIdAndPosCode(Long deptId, String posCode) {
		String hql = "from Buser where deptId=? and posCode=? and status<>4";
		List<Buser> ret = null;
		try {
			ret = getHibernateTemplate().find(hql, new Object[]{deptId, posCode});
		} catch (Throwable t) {
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql.toString()}, t);
		}
		return ret.size() > 0 ? ret.get(0) : null;
	}

		@Override
	public Buser getUserByAreaIdAndPosCode(Long areaId, String posCode) {
		String hql = "from Buser where areaId=? and posCode=? and status<>4";
		List<Buser> ret = null;
		try{
			ret = getHibernateTemplate().find(hql,new Object[]{areaId,posCode});
		}catch(Throwable t){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql.toString()}, t);
		}
		return ret.size() >0 ? ret.get(0) : null;
	}

		@Override
		public List<Buser> getCanSetBuser(String posCode) {
			String hql = "";
			if(UtilConstant.CFS_AREA_MANAGER.equals(posCode)){
				hql = "from Buser where posCode=? and status<>4 and areaId is null";
			}else if(UtilConstant.CFS_DEPT_MANAGER.equals(posCode)){
				hql = "from Buser where posCode=? and status<>4 and deptId is null";
			}else if(UtilConstant.CFS_TEAM_MANAGER.equals(posCode)){
				hql = "from Buser where posCode=? and status<>4 and teamId is null";
			}else{
				hql = "from Buser where posCode=? and status<>4 and teamId is null";
			}
			List<Buser> ret = null;
			try {
				ret = getHibernateTemplate().find(hql,posCode);
			}catch(Throwable t){
				ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql.toString()}, t);
			}
			return ret;
		}

		@Override
		public List<Buser> getUserByTeamId(Long teamId) {
			String hql = "select u from Buser u where u.teamId=? and u.status !=4" +
					" and u.posCode ='01'";
			return find(hql,teamId);
		}
}
