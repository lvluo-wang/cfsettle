package com.upg.ucars.basesystem.security.core.branch;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.ReBrchFunc;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.StringUtil;

public class BranchDAOImpl extends BaseDAO<Branch, Long> implements IBranchDAO {

	public Branch getBranchByBrchId(Long brchId) throws DAOException {
		Branch brch = null;
		String hql = "from Branch brch where brch.brchId=?";
		List list = find(hql, brchId);
		if (list != null && list.size() > 0) {
			brch = (Branch) list.get(0);
		}
		return brch;
	}

	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return Branch.class;
	}

	public List<Branch> getBelongMems(String miNo) throws DAOException {
		String hql = " from Branch as brch where brch.miNo=?";
		return find(hql, miNo);
	}

	public List<ReBrchFunc> getFunctionIds(Long brchId) throws DAOException {
		String hql = " from ReBrchFunc as bf where bf.brchId=?";
		return find(hql, brchId);
	}

	public List<Sysfunc> getFunctions(Long brchId) throws DAOException {
		String hql = "select func from Sysfunc as func, ReBrchFunc as bf where bf.funcId=func.funcId and bf.brchId=?";
		return find(hql, brchId);
	}
	public List<Sysfunc> getAllredCheckedFunctions(Long branchId)
			throws DAOException {
		String hql = "select func from Sysfunc as func, ReBrchFunc as bf where bf.funcId=func.funcId and bf.brchId=:brchId and bf.status=:status";
		HashMap map=new HashMap();
		map.put("brchId", branchId);
//		map.put("status", ReBrchFunc.STRUTS_CHECK);
		map.put("status", ReBrchFunc.STATUS_CHECKED);
		return queryByParam(hql,map,null);
	}
	
	public List<Branch> getSubBranches(String treeCode,Long excludeBrchId) throws DAOException {
		String hql = " from Branch as brch where 1=1 ";
		List params = new ArrayList();
		if(!StringUtil.isEmpty(treeCode)){
			hql = hql + " and brch.treeCode like ? ";
			params.add(treeCode + "%");
		}
		if(excludeBrchId != null){
			hql = hql + " and brch.brchId !=? ";
			params.add(excludeBrchId);
		}
		return find(hql,params.toArray());
	}
	
	
	/**;
	 * 获得总部机构
	 * @return	List<Branch>
	 * @throws DAOException
	 */
	public List<Branch> getHQBranches()  throws DAOException {
		String hql = "from Branch as brch where brch.brchLevel=1";
		return find(hql);
	}
	public Branch getHQBranch(String miNo)throws DAOException{
		String hql="from Branch as brch where brch.brchLevel=1 and brch.miNo=?";
		Branch brch=null;
		List list=find(hql,miNo);
		if(list!=null&&list.size()>0){
			brch=(Branch)list.get(0);
		}
		return brch;
	}
	public List<Branch> getHQBranches(Page page)  throws DAOException {
		String hql = " from Branch as brch where brch.brchLevel=1";
		QueryCondition qc = new QueryCondition(hql, "brchId");
		return this.queryByCondition(qc, page);
	}

	public List<Branch> getBelongMemByPage(String miNo, Page page) throws DAOException {
		String hql = " from Branch as brch";
		QueryCondition qc = new QueryCondition(hql, "brchId");
		qc.addCondition(new ConditionBean("miNo", miNo));
		return this.queryByCondition(qc, page);
	}

	public List<Sysfunc> getFunctionByPage(Long brchId, Page page) throws DAOException {
		String hql = "select func from Sysfunc as func, ReBrchFunc as bf where bf.funcId=func.funcId";
		QueryCondition qc = new QueryCondition(hql);
		qc.addCondition(new ConditionBean("bf.id.brchId", brchId));
		return this.queryByCondition(qc, page);
	}
	
	/**
	 * 按机构id，机构权限状态查询权限
	 * */
	public List<Sysfunc> queryFunc(Long brchId, String brchFuncStatus) throws DAOException {
		String hql = "select func from Sysfunc as func, ReBrchFunc as bf where bf.funcId=func.funcId";
		if(brchId != null){
			hql = hql + " and bf.brchId="+brchId;
		}
		if(!StringUtil.isEmpty(brchFuncStatus)){
			hql = hql + " and bf.status='"+brchFuncStatus+"'";
		}
		return this.find(hql);
	}

	public List<ReBrchFunc> getFunctionIdByPage(Long brchId, Page page)
			throws DAOException {
		String hql = " from ReBrchFunc as bf";
		QueryCondition qc = new QueryCondition(hql);
		qc.addCondition(new ConditionBean("bf.id.brchId", brchId));
		return this.queryByCondition(qc, page);
	}
	
	public List<Branch> findBranch(Branch brch) throws DAOException {
		try {
			//if(brch == null) return null;
			
			List queryCols = new ArrayList();
			
			String hql = "from Branch where 1=1";
			if(brch.getBrchId() != null) {
				queryCols.add(brch.getBrchId());
				hql += " and brchId=?";
			}
			if(brch.getBrchName() != null && !"".equals(brch.getBrchName())) {
				queryCols.add("%" + brch.getBrchName().trim() + "%");
				hql += " and brchName like ?";
			}
			if(brch.getMiNo() != null) {
				queryCols.add(brch.getMiNo());
				hql += " and miNo=?";
			}
			if(brch.getTreeCode() != null && !"".equals(brch.getTreeCode())) {
				queryCols.add(brch.getTreeCode());
				hql += " and treeCode=?";
			}
			if(brch.getBrchStatus() != null && !"".equals(brch.getBrchStatus())) {
				queryCols.add(brch.getBrchStatus());
				hql += " and brchStatus=?";
			}
			return find(hql, queryCols.toArray());
		} catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] { e.getMessage() }, e);
		}
	}

	public List<Branch> findBranchByPage(Branch brch, Page page) throws DAOException {
		
		//if(brch == null) return null;
		
		String hql = "from Branch";
		QueryCondition qc = new QueryCondition(hql,"brchId");
		if(brch != null) {
			if(brch.getBrchNo() != null && !"".equals(brch.getBrchNo())) {
				qc.addCondition(new ConditionBean("brchNo", brch.getBrchNo()));
			}
			if(brch.getBrchName() != null && !"".equals(brch.getBrchName())) {
				qc.addCondition(new ConditionBean("brchName", ConditionBean.LIKE, brch.getBrchName().trim()));
			}
			if(brch.getTreeCode() != null && !"".equals(brch.getTreeCode())) {
				qc.addCondition(new ConditionBean("treeCode",  brch.getTreeCode()));
			}
			if(brch.getMiNo() != null && !"".equals(brch.getMiNo())) {
				qc.addCondition(new ConditionBean("miNo", brch.getMiNo()));
			}
			if(brch.getBrchStatus() != null && !"".equals(brch.getBrchStatus())) {
				qc.addCondition(new ConditionBean("brchStatus", brch.getBrchStatus()));
			}
		}
		return this.queryByCondition(qc, page);
		
	}

	
	public List<Branch> findSubBrchs(Branch brch, Page page)
			throws DAOException {
		Map<String,Object> queryCols = new HashMap<String,Object>();
		
		StringBuilder hql = new StringBuilder("from Branch where 1=1");
		
		if( brch != null ){
			if(brch.getBrchId() != null) {
				queryCols.put("brchId", brch.getBrchId());
				hql .append(" and brchId=:brchId " );
			}
			if(brch.getBrchName() != null && !"".equals(brch.getBrchName())) {
				queryCols.put("brchName", "%" + brch.getBrchName().trim() + "%");
				hql .append(" and brchName like :brchName " );
			}
			if(brch.getMiNo() != null) {
				queryCols.put("miNo", brch.getMiNo());
				hql .append(" and miNo=:miNo " );
			}
			if( brch.getBrchNo() != null && brch.getBrchNo().trim().length() > 0 ){
				queryCols.put("brchNo", "%" +brch.getBrchNo().trim() + "%");
				hql.append(" and brchNo like :brchNo ");
			}
			if(brch.getTreeCode() != null && !"".equals(brch.getTreeCode())) {
				queryCols.put("treeCode1", brch.getTreeCode());
				queryCols.put("treeCode2", brch.getTreeCode() + "%");
				hql.append(" and (treeCode!=:treeCode1 and treeCode like :treeCode2 )" );
			}
			if(brch.getBrchStatus() != null && !"".equals(brch.getBrchStatus())) {
				queryCols.put("brchStatus", brch.getBrchStatus());
				hql.append( " and brchStatus=:brchStatus " );
			}
		}
		return this.queryByParam(hql.toString(), queryCols, page);
	}
	
	/**
	 * 获得子机构
	 * @param hasLocalBrch true:包括本机构，false:不包括本机构
	 * @param brch 组合条件信息
	 * @param page 分页信息
	 * @return List<Branch>
	 */
	public List<Branch> findSubBrchs(Branch brch, Page page,boolean hasLocalBrch)
			throws DAOException {
		Map<String, Object> queryCols = new HashMap<String, Object>();
		StringBuilder hql = new StringBuilder("from Branch where 1=1");
		if (brch != null) {
			if (brch.getBrchId() != null) {
				queryCols.put("brchId", brch.getBrchId());
				hql.append(" and brchId !=:brchId ");
			}
			if (brch.getBrchName() != null && !"".equals(brch.getBrchName())) {
				queryCols.put("brchName", "%" + brch.getBrchName() + "%");
				hql.append(" and brchName like :brchName ");
			}
			if (brch.getMiNo() != null) {
				queryCols.put("miNo", brch.getMiNo());
				hql.append(" and miNo = :miNo ");
			}
			if (brch.getBrchNo() != null
					&& brch.getBrchNo().trim().length() > 0) {
				queryCols.put("brchNo", "%" + brch.getBrchNo().trim() + "%");
				hql.append(" and brchNo like :brchNo ");
			}
			if (brch.getTreeCode() != null && !"".equals(brch.getTreeCode())) {
				if(hasLocalBrch){//包含本级机构
					queryCols.put("treeCode", brch.getTreeCode() + "%");
					hql.append(" and treeCode like :treeCode ");
				}else{//不包含本级机构，只包含子层机构
					queryCols.put("treeCode1", brch.getTreeCode());
					queryCols.put("treeCode2", brch.getTreeCode() + "%");
					hql.append(" and (treeCode!=:treeCode1 and treeCode like :treeCode2 )");
				}
			}
			if (brch.getBrchStatus() != null && !"".equals(brch.getBrchStatus())) {
				queryCols.put("brchStatus", brch.getBrchStatus());
				hql.append(" and brchStatus=:brchStatus ");
			}
			if( brch.getBrchLevel() != null ){
				queryCols.put("brchLevel", brch.getBrchLevel());
				hql.append(" and brchLevel=:brchLevel");
			}
		}
		return this.queryByParam(hql.toString(), queryCols, page);
	}

	public List getHQBranches(Branch branch, Page page) {
		String hql = " from Branch as brch where brch.brchLevel=1";
		QueryCondition qc = new QueryCondition(hql,"brchId");
		if(branch != null){
			if(branch.getBrchNo() != null && !"".equals(branch.getBrchNo())) {
				qc.addCondition(new ConditionBean("brchNo",ConditionBean.LIKE, branch.getBrchNo().trim()));
			}
			if(branch.getBrchName() != null && !"".equals(branch.getBrchName())) {
				qc.addCondition(new ConditionBean("brchName", ConditionBean.LIKE, branch.getBrchName().trim()));
			}
			//不包含指定的分支id(在机构修改的时候，上级不能指向自己)
			if(branch.getBrchId() != null) {
				qc.addCondition(new ConditionBean("brchId", ConditionBean.NOT_EQUAL, branch.getBrchId()));
			}
		}
		return this.queryByCondition(qc, page);
	}

	public List getBelongMemByPage(Branch branch, String miNo, Page page) {
		String hql = " from Branch ";
		QueryCondition qc = new QueryCondition(hql,"brchId");
		if(branch!=null){
			if(branch.getBrchNo() != null && !"".equals(branch.getBrchNo())) {
				qc.addCondition(new ConditionBean("brchNo", branch.getBrchNo().trim()));
			}
			if(branch.getBrchName() != null && !"".equals(branch.getBrchName())) {
				qc.addCondition(new ConditionBean("brchName", ConditionBean.LIKE, branch.getBrchName().trim()));
			}
		}
		qc.addCondition(new ConditionBean("miNo", miNo));
		return this.queryByCondition(qc, page);
	}

	public Long getSubBranchsCount(Branch branch ) {
		Long ret = null;
		if( branch != null ){
			String treeCode = branch.getTreeCode();
			if( treeCode != null && treeCode.length() > 0 ){
				String hql = "select count(brchId) from Branch where treeCode !=? and treeCode like ? ";
				List list = find(hql,new Object[]{branch.getTreeCode(),branch.getTreeCode() + "%"});
				ret = (Long) list.get(0);
			}
		}
		return ret == null ? Long.valueOf(0) : ret;
	}
	
	@SuppressWarnings("unchecked")
	public List<Branch> getBranchListByTreeCodeList(final List<String> treeCodeList,final String miNo) throws DAOException {
		List<Branch> ret = null;
		if( treeCodeList != null  && treeCodeList.size() > 0 ){
			ret = (List<Branch>) getHibernateTemplate().executeFind(new HibernateCallback<List<Branch>>() {
				public List<Branch> doInHibernate(Session session)
						throws HibernateException, SQLException {
					String hql = "from Branch where treeCode in ( :treeCodeList )";
					if( miNo != null && miNo.trim().length() > 0 ){
						hql += " and  miNo='"+ miNo.trim() + "'";
					}
					Query query = session.createQuery(hql);
					query.setParameterList("treeCodeList", treeCodeList);
					return query.list();
				}
			});
		}
		return ret == null ? new ArrayList<Branch>(0) : ret ;
	}

	public boolean hasSameCode(String brchNo, Long excludeBrchId)throws DAOException {
		String hql="select count(*) from Branch t where t.brchNo=:brchNo ";
		if(excludeBrchId != null)
			hql = hql + " and t.brchId != :excludeBrchId";
		try{
			int count=0;
			Map<String, Object> parameterMap = new HashMap<String, Object>(1);
			parameterMap.put("brchNo", brchNo);
			if(excludeBrchId != null)
				parameterMap.put("excludeBrchId", excludeBrchId);
			List list=super.queryByParam(hql, parameterMap, null);
			count=Integer.parseInt(""+list.get(0));
			return count>0;
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return false;
	}
	
	public List<Branch> getSubBranchList(String treeCode,String miNo, Page page){
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
		conditionList.add(new ConditionBean(Branch.TREE_CODE,ConditionBean.LIKE,treeCode.trim()+"%"));
		if( StringUtils.isNotBlank(miNo) ){
			conditionList.add(new ConditionBean(Branch.MI_NO,miNo));
		}
		return queryEntity(conditionList, page);
	}
	
	public List<Branch> getSubBranches(String treeCode){
		return getSubBranchList(treeCode,null,null);
	}
	
	
	/**
	 * 取得下一级的机构信息
	 * @param treeCode
	 * @param miNo
	 * @return
	 */
	
	
	public List<Branch> getNextSubBranches(String treeCode,String miNo) {
		String hql = " from Branch as brch where brch.treeCode like ? and brch.miNo=?";
		Object[] values = new String[]{treeCode+"____",miNo};
		return this.find(hql, values);
	}

}