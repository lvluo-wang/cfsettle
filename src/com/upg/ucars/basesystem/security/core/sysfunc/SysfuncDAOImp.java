package com.upg.ucars.basesystem.security.core.sysfunc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.basesystem.security.core.role.RoleConstant;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.mapping.basesystem.security.Role;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.util.CommonUtil;

public class SysfuncDAOImp extends BaseDAO<Sysfunc,Long> implements ISysfuncDAO{

	public List<Sysfunc>  getSubMenuByRoles(List<Role> roles, Long funcId) {
		StringBuffer hql=new StringBuffer("select func from Sysfunc func,ReRoleSysfunc re ");
		hql.append("where func.funcId=re.funcId ");
		hql.append("and func.parentFuncId=").append(funcId).append(" and func.funcType='1' ");
		hql.append("and re.roleId in(:roleIds) " );
		hql.append("and re.status='1'  order by func.sortNo");
		List<Sysfunc>  list=null;
		try{
			if(roles!=null&&roles.size()>0){
				Long[] roleIds=new Long[roles.size()];
				for(int i=0;i<roles.size();i++){
					roleIds[i]=roles.get(i).getRoleId();
				}
				Map<String, Object> parameterMap = new HashMap<String, Object>(1);
				parameterMap.put("roleIds", roleIds);
				
				list=super.queryByParam(hql.toString(), parameterMap, null);
				list=CommonUtil.removeDuplicate(list);
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql.toString()}, e);
		}
		
		
		return list;
	}
	public List<Sysfunc>  getSubMenuByRoleIds(List<Long> roles, Long funcId) {
		StringBuilder hql=new StringBuilder("select func from Sysfunc func,ReRoleSysfunc re ");
		hql.append("where func.funcId=re.funcId ");
		hql.append("and func.parentFuncId=").append(funcId).append(" and func.funcType in ('1','0') ");
		hql.append("and re.roleId in(:roleIds) " );
		hql.append("and re.status='1'  order by func.sortNo");
		List<Sysfunc>  list=null;
		try{
			if(roles!=null&&roles.size()>0){
				Long[] roleIds=new Long[roles.size()];
				for(int i=0;i<roles.size();i++){
					roleIds[i]=roles.get(i);
				}
				Map<String, Object> parameterMap = new HashMap<String, Object>(1);
				parameterMap.put("roleIds", roleIds);
				
				list=super.queryByParam(hql.toString(), parameterMap, null);
				list=CommonUtil.removeDuplicate(list);
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql.toString()}, e);
		}
		
		
		return list;
	}
	public boolean hasSubMenuByRoles(List<Role> roles, Long funcId) {
		StringBuffer hql=new StringBuffer("select count(*)  from Sysfunc func,ReRoleSysfunc re ");
		hql.append("where func.funcId=re.funcId ");
		hql.append("and func.parentFuncId='").append(funcId).append("' and func.funcType='1' ");
		hql.append("and re.roleId in(:roleIds) ");
		hql.append("and re.status='1'");
		List list=null;
		int count=0;
		try{
			if(roles!=null&&roles.size()>0){
				Long[] roleIds=new Long[roles.size()];
				for(int i=0;i<roles.size();i++){
					roleIds[i]=roles.get(i).getRoleId();
				}
				Map<String, Object> parameterMap = new HashMap<String, Object>(1);
				parameterMap.put("roleIds", roleIds);
				
				list=super.queryByParam(hql.toString(), parameterMap, null);
				count=Integer.parseInt(""+list.get(0));
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql.toString()}, e);
		}
		return count>0;
	}
	public boolean hasSubMenuByRoleIds(List<Long> roles, Long funcId) {
		StringBuffer hql=new StringBuffer("select count(*)  from Sysfunc func,ReRoleSysfunc re ");
		hql.append("where func.funcId=re.funcId ");
		hql.append("and func.parentFuncId='").append(funcId).append("' and func.funcType='1' ");
		hql.append("and re.roleId in(:roleIds) ");
		hql.append("and re.status='1'");
		int count=0;
		try{
			if(roles!=null&&roles.size()>0){
				Long[] roleIds=new Long[roles.size()];
				for(int i=0;i<roles.size();i++){
					roleIds[i]=roles.get(i);
				}
				Map<String, Object> parameterMap = new HashMap<String, Object>(1);
				parameterMap.put("roleIds", roleIds);
				
				List<?> list=super.queryByParam(hql.toString(), parameterMap, null);
				count=Integer.parseInt(""+list.get(0));
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql.toString()}, e);
		}
		return count>0;
	}
	@Override
	public Class getEntityClass() {
		return Sysfunc.class;
	}

	public List<Sysfunc>  findFuncByParentId(Long parentFuncId) {
		String hql="from Sysfunc func where func.parentFuncId=? order by func.sortNo";
		List<Sysfunc>  list=null;
		try{
			if(parentFuncId==null){
				hql="from Sysfunc func where func.funcId=?";
				list=find(hql, Sysfunc.ROOT_FUNC_ID);
			}else{
				list=find(hql, parentFuncId);
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return list;
	}

	public boolean hasSubFuncByPraentId(Long parentFuncId) {
		String hql="select count(*) from Sysfunc func where func.parentFuncId=?";
		try{
			int count=0;
			List<?> list=find(hql, parentFuncId);
			count=Integer.parseInt(""+list.get(0));
			return count>0;
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return false;
	}

	public boolean hasSameFuncId(Long funcId) {
		String hql="select count(*) from Sysfunc func where func.funcId=?";
		try{
			int count=0;
			List list=find(hql, funcId);
			count=Integer.parseInt(""+list.get(0));
			return count>0;
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return false;
	}

	public boolean hasSameFuncName(String funcName,Long funcId) {
		String hql="select count(*) from Sysfunc func where func.funcName=:funcName and func.parentFuncId=:funcId";
		try{
			int count=0;
			Map<String, Object> parameterMap = new HashMap<String, Object>(1);
			parameterMap.put("funcName", funcName);
			parameterMap.put("funcId", funcId);
			List list=super.queryByParam(hql, parameterMap, null);
			count=Integer.parseInt(""+list.get(0));
			return count>0;
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return false;
	}

	public boolean hasSameFuncNameKey(String funcNameKey,Long funcId) {
		String hql="select count(*) from Sysfunc func where func.funcNameKey=:funcNameKey and func.parentFuncId=:funcId";
		try{
			int count=0;
			Map<String, Object> parameterMap = new HashMap<String, Object>(1);
			parameterMap.put("funcNameKey", funcNameKey);
			parameterMap.put("funcId", funcId);
			List list=super.queryByParam(hql, parameterMap, null);
			count=Integer.parseInt(""+list.get(0));
			return count>0;
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return false;
	}

	public List<Sysfunc> findAllURLFunc() {
		String hql="from Sysfunc func where func.url is not null";
		List<Sysfunc>  list=null;
		try{
			list=find(hql);
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return list;
	}

	public List<Long> fundRolesByfuncId(Long funcId) {
		String hql="select re.roleId from ReRoleSysfunc re where re.funcId=?";
		List<Long> list=new ArrayList<Long>();
		try{
			List<Long> listTmp=find(hql,funcId);
			if(listTmp!=null){
				list.addAll(listTmp);
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return list;
	}

	public Long getMaxSortNo(Long targetFunc) {
		String hql="select max(func.sortNo) from Sysfunc func where func.parentFuncId=?";
			List list=null;
		try{
			list=find(hql,targetFunc);
			if(list!=null&&list.size()>0){
				Long maxNo=(Long)list.get(0);
				if(maxNo!=null){
					return maxNo;
				}
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return 0L;
	}

	public List<Sysfunc> getButtomFunc(Sysfunc target) {
		String hql="from Sysfunc func where func.parentFuncId=:funcId and func.sortNo>:sortNo";
		try{
			if(target!=null){
				Map<String, Object> parameterMap = new HashMap<String, Object>(1);
				parameterMap.put("funcId", target.getParentFuncId());
				parameterMap.put("sortNo", target.getSortNo());
				return super.queryByParam(hql, parameterMap, null);
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return null;
	}

	public List<Sysfunc> getSyfuncByRoleList(List<Role> roles) {
		StringBuffer hql=new StringBuffer("select func from Sysfunc func,ReRoleSysfunc re ");
		hql.append("where func.funcId=re.funcId ");
		hql.append("and func.url is not null ");
		hql.append("and re.roleId in(:roleIds) ");
		hql.append("and re.status='1'");
		List<Sysfunc> list=null;
		try{
			if(roles!=null&&roles.size()>0){
				Long[] roleIds=new Long[roles.size()];
				for(int i=0;i<roles.size();i++){
					roleIds[i]=roles.get(i).getRoleId();
				}
				Map<String, Object> parameterMap = new HashMap<String, Object>(1);
				parameterMap.put("roleIds", roleIds);
				
				list=super.queryByParam(hql.toString(), parameterMap, null);
				list=CommonUtil.removeDuplicate(list);
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql.toString()}, e);
		}
		
		
		return list;
	}
	public List<Sysfunc> getSyfuncByRoleIdList(List<Long> roles) {
		StringBuffer hql=new StringBuffer("select func from Sysfunc func,ReRoleSysfunc re ");
		hql.append("where func.funcId=re.funcId ");
		hql.append("and func.url is not null ");
		hql.append("and re.roleId in(:roleIds) ");
		hql.append("and re.status='1'");
		List<Sysfunc> list=null;
		try{
			if(roles!=null&&roles.size()>0){
				Long[] roleIds=new Long[roles.size()];
				for(int i=0;i<roles.size();i++){
					roleIds[i]=roles.get(i);
				}
				Map<String, Object> parameterMap = new HashMap<String, Object>(1);
				parameterMap.put("roleIds", roleIds);
				
				list=super.queryByParam(hql.toString(), parameterMap, null);
				list=CommonUtil.removeDuplicate(list);
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql.toString()}, e);
		}
		
		
		return list;
	}
	public List<Sysfunc> getMenuByRoleList(List<Role> roles) {
		StringBuffer hql=new StringBuffer("select func from Sysfunc func,ReRoleSysfunc re ");
		hql.append("where func.funcId=re.funcId ");
		hql.append("and re.roleId in(:roleIds) ");
		hql.append("and func.funcType='1' ");
		hql.append("and re.status='1'");
		List<Sysfunc> list=null;
		try{
			if(roles!=null&&roles.size()>0){
				Long[] roleIds=new Long[roles.size()];
				for(int i=0;i<roles.size();i++){
					roleIds[i]=roles.get(i).getRoleId();
				}
				Map<String, Object> parameterMap = new HashMap<String, Object>(1);
				parameterMap.put("roleIds", roleIds);
				
				list=super.queryByParam(hql.toString(), parameterMap, null);
				list=CommonUtil.removeDuplicate(list);
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql.toString()}, e);
		}
		
		
		return list;
	}
	public List<Sysfunc> getMenuByRoleIdList(List<Long> roles) {
		StringBuffer hql=new StringBuffer("select func from Sysfunc func,ReRoleSysfunc re ");
		hql.append("where func.funcId=re.funcId ");
		hql.append("and re.roleId in(:roleIds) ");
		hql.append("and func.funcType='1' ");
		hql.append("and re.status='1'");
		List<Sysfunc> list=null;
		try{
			if(roles!=null&&roles.size()>0){
				Long[] roleIds=new Long[roles.size()];
				for(int i=0;i<roles.size();i++){
					roleIds[i]=roles.get(i);
				}
				Map<String, Object> parameterMap = new HashMap<String, Object>(1);
				parameterMap.put("roleIds", roleIds);
				
				list=super.queryByParam(hql.toString(), parameterMap, null);
				list=CommonUtil.removeDuplicate(list);
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql.toString()}, e);
		}
		
		
		return list;
	}
	public List<Map<String,Object>> findCheckedRoleWithFunc() throws DAOException {
		String sql="SELECT F.URL,R.ROLE_ID FROM SYSFUNC F,RE_ROLE_FUNC R WHERE F.FUNC_ID=R.FUNC_ID AND R.STATUS=:STATUS AND F.URL IS NOT NULL";
		Map<String,Object> tmp=new HashMap<String,Object>();
		tmp.put("STATUS", RoleConstant.ROLE_STATUS_CHECK);
		return getMapListByStanderdSQL(sql,tmp,null);
	}
	
	public List<Sysfunc> getSysfuncList(Map<String, Object> param) throws DAOException {
		String sql = "from Sysfunc";
		List<Object> list = null;
		if( param != null ){
			list = new ArrayList<Object>(param.size());
			Object funcType = param.get(Sysfunc.FUNC_TYPE);
			if( funcType != null ){
				sql += " where funcType=?";
				list.add(funcType);
			}
			return getHibernateTemplate().find(sql, list.toArray());
		}
		return getHibernateTemplate().find(sql);
	}
	
	public List<Sysfunc> querySysfuncs(List<String> usetypes,List<String> functypes) throws DAOException {
		String hql = " from Sysfunc t where 1=1 ";
		String tmp = "";
		String sp = "";
		if(usetypes != null && !usetypes.isEmpty()){
			for(String usetype : usetypes){
				tmp = tmp + sp + "'"+usetype+"'";
				sp = ",";
			}
			hql = hql + " and t.useType in ("+tmp+") ";
		}
		if(functypes != null && !functypes.isEmpty()){
			tmp = "";
			sp = "";
			for(String functype : functypes){
				tmp = tmp + sp + "'"+functype+"'";
				sp = ",";
			}
			hql = hql + " and t.funcType in ("+tmp+") ";
		}
		return this.find(hql);
	}
	
	public boolean hasSameFuncName(String funcName, Long funcId,Long excludefuncId) throws DAOException {
		String hql="select count(*) from Sysfunc func where func.funcName=:funcName and func.parentFuncId=:funcId and func.funcId!=:excludefuncId";
		try{
			int count=0;
			Map<String, Object> parameterMap = new HashMap<String, Object>(1);
			parameterMap.put("funcName", funcName);
			parameterMap.put("funcId", funcId);
			parameterMap.put("excludefuncId", excludefuncId);
			List list=super.queryByParam(hql, parameterMap, null);
			count=Integer.parseInt(""+list.get(0));
			return count>0;
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return false;
	}
	public boolean hasSameFuncNameKey(String funcNameKey, Long funcId,Long excludefuncId) throws DAOException {
		String hql="select count(*) from Sysfunc func where func.funcNameKey=:funcNameKey and func.parentFuncId=:funcId and func.funcId!=:excludefuncId";
		try{
			int count=0;
			Map<String, Object> parameterMap = new HashMap<String, Object>(1);
			parameterMap.put("funcNameKey", funcNameKey);
			parameterMap.put("funcId", funcId);
			parameterMap.put("excludefuncId", excludefuncId);
			List list=super.queryByParam(hql, parameterMap, null);
			count=Integer.parseInt(""+list.get(0));
			return count>0;
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return false;
	}

	public boolean hasResourcePermission(List<Long> roleIds, String resourceId) {
		boolean ret = false;
		if (roleIds != null && StringUtils.isNotBlank(resourceId)) {
			String hql = "select count(sf.funcId) from Sysfunc sf,ReRoleSysfunc rsf"
					+ " where rsf.funcId=sf.funcId "
					+ " and sf.url=:resourceId and rsf.roleId in (:roleIds)";
			List<?> list = getHibernateTemplate().findByNamedParam(hql,
					new String[] { "resourceId", "roleIds" },
					new Object[] { resourceId, roleIds });
			ret = ((Number) list.get(0)).intValue() > 0;
		}
		return ret;
	}

}
