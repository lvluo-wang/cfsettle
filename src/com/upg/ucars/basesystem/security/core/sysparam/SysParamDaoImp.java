package com.upg.ucars.basesystem.security.core.sysparam;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.mapping.basesystem.security.SysParam;
import com.upg.ucars.model.ConditionBean;

/**
 * 
 * SysParamDaoImp
 * 
 * @author shentuwy
 * 
 */
public class SysParamDaoImp extends BaseDAO<SysParam, Long> implements ISysParamDao {

	public Class<SysParam> getEntityClass() {
		return SysParam.class;
	}

	public SysParam findSysParam(String miNo, String paramKey) {
		List<SysParam> list = findSysParamList(miNo, paramKey);
		return list != null && list.size() == 1 ? list.get(0) : null;
	}

	/**
	 * 
	 * 获取系统参数列表
	 * 
	 * @param miNo
	 *            接入点编号
	 * @param paramKey
	 *            参数编号
	 * @return 参数列表
	 */
	private List<SysParam> findSysParamList(String miNo, String paramKey) {
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>(2);
		if (StringUtils.isNotBlank(miNo)) {
			conditionList.add(new ConditionBean(SysParam.MI_NO, miNo));
		} else {
			conditionList.add(new ConditionBean(SysParam.MI_NO, ConditionBean.IS_NULL, null));
		}
		if (StringUtils.isNotBlank(paramKey)) {
			conditionList.add(new ConditionBean(SysParam.PARAM_KEY, paramKey));
		}
		return queryEntity(conditionList, null);
	}

	public List<SysParam> getSysParams(String miNo) {
		return findSysParamList(miNo, null);
	}

	public List<SysParam> getSysParamByIds(List<Long> idList) {
		List<SysParam> ret = null;
		if (idList != null) {
			List<ConditionBean> conditionList = new ArrayList<ConditionBean>(1);
			conditionList.add(new ConditionBean(SysParam.PARAM_ID, ConditionBean.IN, idList));
			ret = queryEntity(conditionList, null);
		}
		return ret == null ? EMPTY_LIST : ret;
	}

}
