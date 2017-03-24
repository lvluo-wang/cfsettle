package com.upg.ucars.basesystem.security.core.sysConfig;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.mapping.basesystem.security.SysConfig;
import com.upg.ucars.model.ConditionBean;


/**
 * 
 * SysConfigDAOImpl
 *
 * @author shentuwy
 *
 */
public class SysConfigDAOImpl extends BaseDAO<SysConfig,Long> implements ISysConfigDAO {

	public SysConfig getConfigByMiNo(String miNo) {
		SysConfig ret = null; 
		if( StringUtils.isNotBlank(miNo) ){
			List<ConditionBean> conditionList = new ArrayList<ConditionBean>(1);
			conditionList.add(new ConditionBean(SysConfig.MI_NO, miNo));
			List<SysConfig> list = queryEntity(conditionList, null);
			if( list != null && list.size() == 1 ){
				ret = list.get(0);
			}
		}
		return ret;
	}

	public Class<SysConfig> getEntityClass() {
		return SysConfig.class;
	}

}
