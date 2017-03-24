/*******************************************************************************
 * Copyright (c) 2003-2008 leadmind Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on 2008-11-11
 *******************************************************************************/


package com.upg.ucars.framework.bpm.assign.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Branch;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.mapping.basesystem.security.Role;
import com.upg.ucars.mapping.framework.bpm.HumnTask;


public class HumanTaskAssignDaoImpl extends BaseDAO<HumnTask, Long> implements IHumanTaskAssignDao{
	/**
	 * 取出所有根据流程名和任务名配置的人员TODO 
	 *
	 * @param processName
	 * @param taskName
	 * @return
	 * @throws DAOException
	 */
	public String[] getTaskActorsByUserId(String processName, String taskName) throws DAOException{
		String sql="select user.userNo from Buser as user,HumnTaskActr as htActr,ProcessDef as p,HumnTask as h where" +
							" htActr.actrPerson=user.sysUserId and htActr.taskId=h.id and h.procId=p.id" +
									" and h.taskName =? and p.processName=?";
		Object[] paras=new Object[2];
		paras[0]=taskName;
		paras[1]=processName;
		
		List list=this.getHibernateTemplate().find(sql,paras);		
		String[] users=listToString(list);
		return users;
	}
	/**
	 * 取出所有根据流程名和任务名配置的机构（brch）下的所有人员 
	 *
	 * @param processName
	 * @param taskName
	 * @param brch
	 * @return
	 * @throws DAOException
	 */
	public String[] getTaskActorsByBrch(String processName, String taskName, Branch brch) throws DAOException{
		String sql="select user.userNo from Buser user,HumnTaskActr htActr,ProcessDef p,HumnTask h,Branch brch where" +
							" htActr.actrBranch=brch.brchId and user.brchId=brch.brchId and htActr.taskId=h.id and h.procId=p.id " +
										" and h.taskName =? and p.processName=? and brch.brchId=?";
		Object[] paras=new Object[3];
		paras[0]=taskName;
		paras[1]=processName;
		//paras[2]=brch.getBrchId();
		
		List list=this.getHibernateTemplate().find(sql,paras);	
		String[] users=listToString(list);
		return users;
	}
	
	

	/**
	 *	2008-11-20
	 * @author penghui (mailto:penghui@leadmind.com.cn)
	 * 取出所有根据流程名和任务名配置的权限的所有用户，而且该用户在传入的机构（branch）下
	 */
	public String[] getTaskActorsByMenu(String processName, String taskName, Branch branch) throws DAOException{
		
		String sql="select user.userNo from Buser user,ProcessDef p,HumnTask h,Branch brch,Branchrole brchRole," +
						"ReUserRole reUserRole,ReRoleSyfc reRoleSysfunc where h.procId=p.id and user.brchId=brch.brchId and h.taskName =?" +
								" and p.processName=? and brch.brchId=? and h.funcId=reRoleSysfunc.id.funcId and ((brch.brchId=brchRole.id.brchId and " +
									  "brchRole.id.roleId = reRoleSysfunc.id.roleId ) or (user.sysUserId=reUserRole.id.sysUserId and reUserRole.id.roleId=reRoleSysfunc.id.roleId ))";
		
		Object[] paras=new Object[3];
		paras[0]=taskName;
		paras[1]=processName;
		//paras[2]=branch.getBrchId();
		
		List list=this.getHibernateTemplate().find(sql,paras);	
		String[] users=listToString(list);
		return users;
	}
	/**
	 *	2008-11-20
	 * @author penghui (mailto:penghui@leadmind.com.cn)
	 * 取出所有根据流程名和任务名配置的角色（role）下的所有人员
	 */
	public String[] getTaskActorsByRole(String processName, String taskName, Role role) throws DAOException{
		String sql="select user.userNo from Buser user,HumnTaskActr htActr,ProcessDef p,HumnTask h,ReUserRole reUserRole,Role r where" +
							" htActr.actrRole=r.roleId and htActr.taskId=h.id and h.procId=p.id and reUserRole.id.sysUserId=user.sysUserId" +
									" and reUserRole.id.roleId =? and h.taskName =? and p.processName=?";
		Object[] paras=new Object[3];
		//paras[0]=role.getRoleId();
		paras[1]=taskName;
		paras[2]=processName;
		
		List list=this.getHibernateTemplate().find(sql,paras);
		String[] users=listToString(list);
		return users;
	}
	
	/*
	 * 将取得的用户集合转换成数组
	 */
	public String[] listToString(List list){
		String[] users=new String[list.size()];
		for(int i=0;i<list.size();i++){
			users[i]=(String)list.get(i);
		}
		return users;
	}
	/**
	 *	2008-11-20
	 * @author penghui (mailto:penghui@leadmind.com.cn)
	 * 取出所有根据流程名和任务名配置的所有用户ID，机构ID，角色ID，部门ID
	 */
	public List[] getRefIds(String processName, String taskName) throws DAOException {
		List brchIds=new ArrayList();
		List deptIds=new ArrayList();
		List roleIds=new ArrayList();
		List userIds=new ArrayList();
		String sql="select htActr.actrPerson,htActr.actrBranch,htActr.actrDept,htActr.actrRole from HumnTaskActr htActr,HumnTask h,ProcessDef p " +
						"where htActr.taskId=h.id and h.procId=p.id and h.taskName=? and p.processName=?";
		
		List list=this.getHibernateTemplate().find(sql,new Object[]{taskName,processName});
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			
			Object userId=obj[0];
			Object brchId=obj[1];
			Object deptId=obj[2];
			Object roleId=obj[3];
			
			
			if(brchId!=null&&!"".equalsIgnoreCase(brchId.toString()))
				brchIds.add(brchId);
			if(deptId!=null)
				deptIds.add(deptId);
			if(roleId!=null)
				roleIds.add(roleId);
			if(userId!=null)
				userIds.add(userId);
		}
		
		List lists[]=new List[4];
		lists[0]=userIds;
		lists[1]=brchIds;
		lists[2]=deptIds;
		lists[3]=roleIds;
		
		if(lists[0].size()<1)
			lists[0].add(new Long(-1));
		
		if(lists[1].size()<1)
			lists[1].add(" ");
		
		if(lists[2].size()<1)
			lists[2].add(new Long(-1));
		
		if(lists[3].size()<1)
			lists[3].add(new Long(-1));
		
		
		return lists;
	}
	/**
	 * 取出所有根据流程名和任务名配置的所有人员 
	 *
	 * @param processName
	 * @param taskName
	 * @return
	 * @throws DAOException
	 */
	public String[] getTaskActors(String processName, String taskName) throws DAOException {
		Object obj=null;
		String[] users=getActorsByFilter(processName, taskName,obj);
		return users;
	}
	/**
	 *	2008-11-20
	 * @author penghui (mailto:penghui@leadmind.com.cn)
	 * 取出所有根据流程名和任务名配置的所有人员，所有人员属于filter
	 */
	private String[] getActorsByFilter(String processName, String taskName, Object filter) throws DAOException {
		final Object obj=filter;
		final List ids[]=getRefIds(processName, taskName);

		List users=(List) this.getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query q=setParameterByObject(session,obj,ids);	
				List list=q.list();
				return list;
			}	
		});
		String[] str=listToString(users);
		return str;
	}
	/**
	 * 分类设置SQL，参数
	 * @param session
	 * @param filter
	 * @param ids
	 * @return
	 */
	private Query setParameterByObject(Session session,Object filter,List ids[]){
		StringBuffer baseSb=new StringBuffer();
		
		baseSb.append("select u0.USER_NO from BUSER u0 where u0.SYS_USER_ID in (:userId) union ");
		baseSb.append("select u1.USER_NO from BUSER u1,BRANCH brch where u1.BRCH_ID=brch.BRCH_ID and u1.BRCH_ID in (:brchId) union ");
	
		baseSb.append("select u2.USER_NO from BUSER u2,DEPT_USER deptUser,DEPARTMENT dept where " +
						"u2.SYS_USER_ID=deptUser.SYS_USER_ID and deptUser.DEPT_ID=dept.DEPT_ID and deptUser.DEPT_ID in (:deptId) union ");
	
		baseSb.append("select u3.USER_NO from BUSER u3,RE_USER_ROLE reUserRole,ROLE r where " +
							"u3.SYS_USER_ID=reUserRole.SYS_USER_ID and reUserRole.ROLE_ID=r.ROLE_ID and reUserRole.ROLE_ID in (:roleId)");
		
		
		Query q=null;
		if(filter==null){
			StringBuffer sb=new StringBuffer();
			sb.append("select u.USER_NO from BUSER u where u.USER_NO in(");
			sb.append(baseSb.toString());
			sb.append(")");
			String sql=sb.toString();		
			q=session.createSQLQuery(sql);
			q.setParameterList("userId", ids[0]);
			q.setParameterList("brchId", ids[1]);
			q.setParameterList("deptId", ids[2]);
			q.setParameterList("roleId", ids[3]);
		}
		else{		
//			if(filter instanceof Buser){				
//				sb.append("and u.BRCH_ID=:id");			
//				q=session.createQuery(sb.toString());
//				Buser user=(Buser)filter;
//				q.setParameter("id",user.getSysUserId());
//			}
							
			if(filter instanceof Branch){
				StringBuffer sb=new StringBuffer();
				sb.append("select u.USER_NO from BUSER u where u.USER_NO in(");
				sb.append(baseSb.toString());
				sb.append(")");
				sb.append("and u.BRCH_ID=:id");	
				String sql=sb.toString();		
				q=session.createSQLQuery(sql);
				q=session.createSQLQuery(sb.toString());
				Branch brch=(Branch)filter;
				//q.setParameter("id",brch.getBrchId());
			}
			
			
	
			if(filter instanceof Role){
				StringBuffer sb=new StringBuffer();
				sb.append("select u.USER_NO from BUSER u,RE_USER_ROLE userRole where u.USER_NO in(");
				sb.append(baseSb.toString());
				sb.append(")");
				sb.append(" and u.SYS_USER_ID=userRole.SYS_USER_ID and userRole.ROLE_ID = (:id)");
				String sql=sb.toString();
				q=session.createSQLQuery(sql);
				Role role=(Role)filter;
				//q.setParameter("id",role.getBrchId());
			}
		
			q.setParameterList("userId", ids[0]);
			q.setParameterList("brchId", ids[1]);
			q.setParameterList("deptId", ids[2]);
			q.setParameterList("roleId", ids[3]);
		}
		return q;
	}
	/**
	 * 取出所有根据流程名和任务名配置的所有人员，这些人员属于filter机构
	 * @param processName
	 * @param taskName
	 * @param filter
	 * @return
	 * @throws DAOException
	 */
	public String[] getActorsFilteByBrch(String processName, String taskName, Branch filter) throws DAOException {
		String[] users=getActorsByFilter(processName, taskName, filter);
		return users;
	}
	
	/**
	 *	取出所有根据流程名和任务名配置的所有人员，这些人员属于filter角色
	 */
	public String[] getActorsFilteByRole(String processName, String taskName, Role filter) throws DAOException {
		String[] users=getActorsByFilter(processName, taskName, filter);
		return users;
	}
	
	public List[] getRefIdsByBrchId(String processName, String taskName, Long brchId) throws DAOException {
		List deptIds=new ArrayList();
		List roleIds=new ArrayList();
		List userIds=new ArrayList();
		String sql="select htActr.actrPerson,htActr.actrDept,htActr.actrRole from HumnTaskActr htActr,HumnTask h,ProcessDef p " +
						"where htActr.taskId=h.id and h.procId=p.id and h.taskName=? and p.processName=? and htActr.actrBranch=?";
		
		List list=this.getHibernateTemplate().find(sql,new Object[]{taskName,processName,brchId});
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[]) list.get(i);
			
			Object userId=obj[0];
			Object deptId=obj[1];
			Object roleId=obj[2];
			
			
			if(deptId!=null)
				deptIds.add(deptId);
			if(roleId!=null)
				roleIds.add(roleId);
			if(userId!=null)
				userIds.add(userId);
		}
		
		List lists[]=new List[3];
		lists[0]=userIds;
		lists[1]=deptIds;
		lists[2]=roleIds;	
		
		if(lists[0].size()<1)
			lists[0].add(new Long(-1));
		
		if(lists[1].size()<1)
			lists[1].add(new Long(-1));
		
		if(lists[2].size()<1)
			lists[2].add(new Long(-1));
				
		return lists;
	}
	public String[] getTaskActorsByBrchId(String processName, String taskName, Long brchId) throws DAOException {
		final String pName=processName;
		final String tName=taskName;
		final Long bId=brchId;
		List userList=null;
		
		final StringBuffer baseSb=new StringBuffer();
		
		baseSb.append("select u.USER_NO from BUSER u where u.USER_NO in(");
		baseSb.append("select u1.USER_NO from BUSER u1 where u1.SYS_USER_ID in (:userId) union ");
	
		baseSb.append("select u2.USER_NO from BUSER u2,DEPT_USER deptUser,DEPARTMENT dept where " +
						"u2.SYS_USER_ID=deptUser.SYS_USER_ID and deptUser.DEPT_ID=dept.DEPT_ID and deptUser.DEPT_ID in (:deptId) union ");
	
		baseSb.append("select u3.USER_NO from BUSER u3,RE_USER_ROLE reUserRole,ROLE r where u3.BRCH_ID = (:brchId) and " +
							"u3.SYS_USER_ID=reUserRole.SYS_USER_ID and reUserRole.ROLE_ID=r.ROLE_ID and reUserRole.ROLE_ID in (:roleId)");
		baseSb.append(")");
		
		try{
			userList=(List) this.getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					List[] list=getRefIdsByBrchId(pName, tName, bId);
					
					List userNos=session.createSQLQuery(baseSb.toString())
										.setParameterList("userId", list[0])
										.setParameterList("deptId", list[1])
										.setParameterList("roleId", list[2])
										.setParameter("brchId", bId)
										.list();

					return userNos;
				}
				
			});
		}
		catch(Exception e){
			throw ExceptionManager.getException(DAOException.class, "DAO_0002");
		}
		return listToString(userList);
	}
	
	
	public List getTaskActors(String processName, String taskName, Long brchId)
			throws DAOException {
		
		final String pName=processName;
		final String tName=taskName;
		final Long bId=brchId;
		List userList=null;
		
		final StringBuffer baseSb=new StringBuffer();
		
		baseSb.append("select distinct u.USER_NAME from BUSER u where u.USER_NAME in(");
		baseSb.append("select u1.USER_NAME from BUSER u1 where u1.SYS_USER_ID in (:userId) union ");
	
		baseSb.append("select u2.USER_NAME from BUSER u2,DEPT_USER deptUser,DEPARTMENT dept where " +
						"u2.SYS_USER_ID=deptUser.SYS_USER_ID and deptUser.DEPT_ID=dept.DEPT_ID and deptUser.DEPT_ID in (:deptId) union ");
	
		baseSb.append("select u3.USER_NAME from BUSER u3,RE_USER_ROLE reUserRole,ROLE r where u3.BRCH_ID = (:brchId) and " +
							"u3.SYS_USER_ID=reUserRole.SYS_USER_ID and reUserRole.ROLE_ID=r.ROLE_ID and reUserRole.ROLE_ID in (:roleId)");
		baseSb.append(")");
		
		try{
			userList=(List) this.getHibernateTemplate().execute(new HibernateCallback(){
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					List[] list=getRefIdsByBrchId(pName, tName, bId);
					
					List userNos=session.createSQLQuery(baseSb.toString())
										.setParameterList("userId", list[0])
										.setParameterList("deptId", list[1])
										.setParameterList("roleId", list[2])
										.setParameter("brchId", bId)
										.list();

					return userNos;
				}
				
			});
		}
		catch(Exception e){
			throw ExceptionManager.getException(DAOException.class, "DAO_0002");
		}
		
		return userList;
		
	}
	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return null;
	}
}
