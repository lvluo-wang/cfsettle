package com.upg.ucars.framework.bpm.publish.dao;

import java.util.List;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.framework.bpm.ProcessDef;
import com.upg.ucars.model.ConditionBean;

/**
 *
 * @author shentuwy
 */
public class ProcessDefDaoImpl extends BaseDAO<ProcessDef, Long> implements IProcessDefDao  {

	public ProcessDef getProcessDefByName(String name) {
		String hql = "from ProcessDef where procName=?";
		List list = this.find(hql, new String[]{name});
		if (list.isEmpty())
			return null;
		return (ProcessDef)list.get(0);
	}

	public List<ProcessDef> getAllProcessDef() {
		String hql = "from ProcessDef ";
		List<ProcessDef> list = this.find(hql,null);
		return list;
	}

	public List<ProcessDef> queryProcessDef(List<ConditionBean> conditionList,
			Page page) throws DAOException {
		String hql = "from ProcessDef";
		QueryCondition qc = new QueryCondition(hql, "id");
		qc.addConditionList(conditionList);
		List<ProcessDef> list = super.queryByCondition(qc, page);
		return list;
	}

	public List<ProcessDef> queryProcessDef(QueryComponent qcpt, Page page)
			throws DAOException {
		String hql = "from ProcessDef process";	
		QueryCondition qc = new QueryCondition(hql, "id");
		
		List<ProcessDef> list = super.queryByCondition(qc, page , qcpt );
		return list;
	}

	@Override
	public Class<ProcessDef> getEntityClass() {
		
		return ProcessDef.class;
	}

	
}
