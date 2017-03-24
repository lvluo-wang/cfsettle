package com.upg.ucars.framework.bpm.procmap;

import java.util.List;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.framework.bpm.ProdProcMap;

public class ProdProcMapDAOImpl extends BaseDAO<ProdProcMap, Long> implements
		IProdProcMapDAO {

	@Override
	public Class getEntityClass() {
		
		return ProdProcMap.class;
	}

	public void deleteMemberProcess(String miNo, List<Long> procIdList)
			throws ServiceException {
		StringBuffer idsb = new StringBuffer("-1");
		for (Long pid : procIdList) {
			idsb.append(","+pid);
		}
		
		String hql = "delete ProdProcMap where brchId is null and miNo=? and procId in ("+idsb+")";
		
		this.getHibernateTemplate().bulkUpdate(hql, miNo);
		
	}

	public void deleteBranchProcess(Long brchId, List<Long> procIdList)
			throws ServiceException {
		StringBuffer idsb = new StringBuffer("-1");
		for (Long pid : procIdList) {
			idsb.append(","+pid);
		}
		
		String hql = "delete ProdProcMap where brchId=? and procId in ("+idsb+")";
		
		this.getHibernateTemplate().bulkUpdate(hql, brchId);
		
	}

	

}
