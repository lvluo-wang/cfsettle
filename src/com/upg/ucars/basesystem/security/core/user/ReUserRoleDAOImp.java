package com.upg.ucars.basesystem.security.core.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.ReUserRole;
import com.upg.ucars.model.security.UserLogonInfo;

public class ReUserRoleDAOImp extends BaseDAO<ReUserRole, Long> implements
		IReUserRoleDAO {

	@Override
	public Class<ReUserRole> getEntityClass() {
		return ReUserRole.class;
	}

	public ReUserRole findByUserIdAndRoleId(Long userId, Long roleId)
			throws DAOException {
		String hql = "from ReUserRole as r where r.userId=:userId and r.roleId=:roleId";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("roleId", roleId);
		List<ReUserRole> rList = queryByParam(hql, map, null);
		if (rList != null && rList.size() > 0) {
			return rList.get(0);
		}
		return null;
	}

	public boolean hasRoles(Long userId) throws DAOException {
		String hql = "select count(*) from ReUserRole t where t.userId=:userId";
		try {
			Session session = this.getSession();
			Query query = session.createQuery(hql);
			query.setParameter("userId", userId);
			Long count = (Long) query.list().get(0);
			return count > 0;
		} catch (Exception e) {
			ExceptionManager.throwException(DAOException.class,
					ErrorCodeConst.DB_OPERATION_ERROR, new String[] { hql }, e);
		}
		return false;
	}

	public List<Buser> getUserByRole(String roleType) {
		UserLogonInfo uli = SessionTool.getUserLogonInfo();
		String hql = "select u from com.upg.ucars.mapping.basesystem.security.Role r, ReUserRole ur,Buser u "
				+ "where r.roleId= ur.roleId and ur.userId=u.userId and ur.status=? and r.roleType=? ";
		if (uli != null && StringUtils.isNotBlank(uli.getMiNo())) {
			hql += " and u.miNo='" + uli.getMiNo() + "'";
		}
		hql += " order by u.userNo asc";

		return find(hql, new String[] { Buser.RE_ROLE_STATUS_CHECK, roleType });
	}
	
	public List<Buser> getUserByRoleName(String roleName){
		String hql = "select u from Role r, ReUserRole ur,Buser u "
				+ "where r.roleId= ur.roleId and ur.userId=u.userId and ur.status=? and r.roleName=?";

		return find(hql, new String[] { Buser.RE_ROLE_STATUS_CHECK, roleName });
	}

	public List<Buser> getUsersByRoleAndBrch(String roleName,String brchNo){
		
		String hql = "select u from com.upg.ucars.mapping.basesystem.security.Role r, ReUserRole ur,Buser u "
				+ "where r.roleId= ur.roleId and ur.userId=u.userId and ur.status=? and r.roleName=? and u.brchId=(" +
				"select b.brchId from Branch b where b.brchNo=?)";
		return find(hql,new Object[]{Buser.RE_ROLE_STATUS_CHECK,roleName,brchNo});
	}
	
	public List<Buser> getUsersByRoleNameAndBrch(String roleName,Long brchId){
		String hql = "select u from com.upg.ucars.mapping.basesystem.security.Role r, ReUserRole ur,Buser u "
				+ "where r.roleId= ur.roleId and ur.userId=u.userId and ur.status=? and r.roleName=? and u.brchId=?";
		return find(hql,new Object[]{Buser.RE_ROLE_STATUS_CHECK,roleName,brchId});
	}
	
	public List<Buser> getUsersByRoleIdAndBrch(Long roleId,Long brchId){
		String hql = "select u from com.upg.ucars.mapping.basesystem.security.Role r, ReUserRole ur,Buser u "
				+ "where r.roleId= ur.roleId and ur.userId=u.userId and ur.status=? and r.roleId=? and u.brchId=?";
		return find(hql,new Object[]{Buser.RE_ROLE_STATUS_CHECK,roleId,brchId});
	}


	//
}
