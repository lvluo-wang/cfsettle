package com.upg.ucars.basesystem.security.core.member;


import java.util.HashMap;
import java.util.List;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.MemberInfo;
import com.upg.ucars.mapping.basesystem.security.SysConfig;

public class MemberDAOImp extends BaseDAO<MemberInfo,String> implements IMemberDAO {

	@Override
	public Class<MemberInfo> getEntityClass() {
		return MemberInfo.class;
	}

	public Branch findBranchByMiNo(String miNo) throws DAOException {
		String hql="from Branch brch where brch.miNo=? and brch.parentBrchId is null";
		Branch brch=null;
		try{
			List list=find(hql, miNo);
			if(list!=null&&list.size()>0){
				brch=(Branch)list.get(0);
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return brch;
	}

	public Buser findUserByBrchId(Long brchId) throws DAOException {
		Buser user=null;
		String hql="from Buser user where user.brchId=? and user.userType=?";
		try{
			List list=find(hql,new Object[]{brchId,Buser.TYPE_BRCH_GLOBAL_MANAGER});
			if(list!=null&&list.size()>0){
				user=(Buser)list.get(0);
			}
		}catch(Exception e){
			
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return user;
	}

	public boolean existMiName(String miName) {
		String hql="select count(*) from MemberInfo mi where mi.miName=?";
		try{
			int count=0;
			List list=find(hql,miName);
			count=Integer.parseInt(""+list.get(0));
			return count>0;
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return false;
	}

	public boolean existMiNo(String miNo) {
		String hql="select count(*) from MemberInfo mi where mi.miNo=?";
		try{
			int count=0;
			List list=find(hql,miNo);
			count=Integer.parseInt(""+list.get(0));
			return count>0;
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return false;
	}

	public boolean existOtherMiName(String miNo, String miName) {
		String hql="select count(*) from MemberInfo mi where mi.miName=? and mi.miNo <> ?";
		try{
			int count=0;
			List list=find(hql,new String[]{miName,miNo});
			count=Integer.parseInt(""+list.get(0));
			return count>0;
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return false;
	}

	public SysConfig getSysconfigByMiNo(String miNo) {
		String hql="from SysConfig config where config.miNo=?";
		SysConfig config=null;
		try{
			List list=find(hql,miNo);
			if(list!=null&&list.size()>0){
				config=(SysConfig)list.get(0);
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return config;
	}

	public List<MemberInfo> findMemberByUserNo(String userNo)
			throws DAOException {
		String hql="select new MemberInfo(m.miNo,m.miName) from MemberInfo m,Buser u where u.miNo=m.miNo and u.userNo=:userNo";
		HashMap map=new HashMap();
		map.put("userNo", userNo);
		return queryByParam(hql,map,null);
	}


}
