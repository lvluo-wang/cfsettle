package com.upg.ucars.basesystem.busidate.core;

import java.util.Collections;
import java.util.List;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.mapping.basesystem.busidate.BusiDate;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.StringUtil;


/**
 * 
 * BusiDateDAOImpl
 *
 * @author shentuwy
 *
 */

@SuppressWarnings("unchecked")
public class BusiDateDAOImpl extends BaseDAO<BusiDate, Long> implements IBusiDateDAO {

	public Class<BusiDate> getEntityClass() {
		return BusiDate.class;
	}
	
	
	public <T extends BusiDate> List<T> getCommonBusiDateList(Class<T> cls,List<ConditionBean> conditionList,Page page){
		List<T> ret = null;
		String className = cls.getSimpleName();
		String hql = "from "+className + " "+StringUtil.firstLower(className);
		QueryCondition qc = new QueryCondition(hql);
		qc.addConditionList(conditionList);
		ret = super.queryByCondition(qc, page);
		return ret == null ? Collections.EMPTY_LIST : ret;
	}


	public <T extends BusiDate> T getCommonBusiDateByNullMino(Class<T> cls) {
		T ret = null;
		String className = cls.getSimpleName();
		String hql = "from "+className + " where miNo is null ";
		List<T> list = getHibernateTemplate().find(hql);
		if( list != null && list.size() > 0 ){
			ret = list.get(0);
		}
		return ret;
	}
	
}
