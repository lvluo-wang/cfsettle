package com.upg.ucars.basesystem.security.core.branch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.security.ReBrchFunc;

public class BrchFuncDAOImpl extends BaseDAO<ReBrchFunc,Long> implements IBrchFuncDAO {

	
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return ReBrchFunc.class;
	}
	/**
	 * 通过机构ID删除机构权限信息
	 * @param brchId	机构ID
	 * @throws DAOException
	 */
	public void delBrchFuncByBrchId(Long brchId) throws DAOException{
		String hql = "delete from ReBrchFunc as rbf where rbf.brchId=?";
		try{
			this.getHibernateTemplate().bulkUpdate(hql, brchId);
		} catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] { e.getMessage() }, e);
		}
		
	}
	public List<ReBrchFunc> getBrchFuncByBrchId(Long brchId) {
		String hql = "from ReBrchFunc as rbf where rbf.brchId=?";
		return find(hql, brchId);
	}
	public void updateBrchFuncStatusByBrchId(Long brchId, String struts)
			throws DAOException {
		String hql = "update ReBrchFunc o set o.status=? where o.brchId=?";
		this.getHibernateTemplate().bulkUpdate(hql, struts, brchId);
		
	}
	public ReBrchFunc findByBrchIdAndFuncId(Long brchId, Long funcId)
			throws DAOException {
		String hql="from ReBrchFunc as r where r.brchId=:brchId and r.funcId=:funcId";
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("brchId", brchId);
		map.put("funcId", funcId);
		List<ReBrchFunc> rList=queryByParam(hql,map,null);
		if(rList!=null&&rList.size()>0){
			return rList.get(0);
		}
		return null;
	}
	
	public boolean hasBrchRefFunc(Long funcId) throws DAOException {
		String hql="select count(*) from ReBrchFunc t where t.funcId=:funcId";
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

	/**
	 * 校验这些多个机构中是否存在引用权限
	 * 用于删除机构校验
	 * @param brchIds 机构id列表
	 * @return true: 存在引用 false:不存在
	 * @throws DAOException
	 */
	public boolean hasBrchFuncRef(List<Long> brchIds) throws DAOException {
		String hql="select count(*) from ReBrchFunc t where t.brchId in(:brchIds)";
		try{
			Session session = this.getSession();
			Query query = session.createQuery(hql);
			query.setParameterList("brchIds", brchIds);
			Long count = (Long) query.list().get(0);
			return count>0;
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return false;
	}
	
	/**
	 * 获取机构的权限状态
	 * @param branchId
	 * @return String 0:未分配,1:分配未审核或是分配中,2:已审核,3:审核中
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public String getBrchFuncStatus(Long branchId) throws DAOException{
		String hql = "from ReBrchFunc t where t.brchId =:branchId order by t.id desc";
			Session session = this.getSession();
			Query query = session.createQuery(hql);
			query.setLong("branchId", branchId);
			query.setFirstResult(0);
			query.setMaxResults(1);
			List list = query.list();
//			ReBrchFunc rbf = (ReBrchFunc) query.list().get(0);
			if(list != null && !list.isEmpty()){
				ReBrchFunc rbf = (ReBrchFunc)list.get(0);
				if(rbf == null)
					return ReBrchFunc.STATUS_UN_ASSIGN;
				else
					return rbf.getStatus();
			}
			return ReBrchFunc.STATUS_UN_ASSIGN;
	}
}
