package com.upg.cfsettle.foundation.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.model.ConditionBean;
import com.upg.cfsettle.mapping.filcs.FiLcsFundManagerExt;

/**
 * 理财师service
 * @author renzhuolun
 * @date 2016年6月6日 上午10:57:11
 * @version <b>1.0.0</b>
 */
@Service
public class FiLcsFundManagerExtServiceImpl extends BaseService implements IFiLcsFundManagerExtService {
	
	@Autowired
	private IFiLcsFundManagerExtDao fundManagerExtDao;

	@Override
	public void addFundManagerExtList(List<FiLcsFundManagerExt> perExt) {
		fundManagerExtDao.saveOrUpdateAll(perExt);
	}

	@Override
	public List<FiLcsFundManagerExt> findByCondition(FiLcsFundManagerExt fiLcsFundManagerExt,Page page) {
		String hql = "from FiLcsFundManagerExt fiLcsFundManagerExt";
		QueryCondition condition = new QueryCondition(hql);
		if (fiLcsFundManagerExt != null) {
			Long fundManagerId = fiLcsFundManagerExt.getFundManagerId();
			if (fundManagerId != null) {
				condition.addCondition(new ConditionBean("fiLcsFundManagerExt.fundManagerId", ConditionBean.EQUAL, fundManagerId));
			}
		}
		return fundManagerExtDao.queryEntity(condition.getConditionList(), page);
	}

	@Override
	public void deleteLcsFundManagerExtist(List<FiLcsFundManagerExt> oldExt) {
		fundManagerExtDao.delAll(oldExt);
	}
}
