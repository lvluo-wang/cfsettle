package com.upg.cfsettle.foundation.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.model.ConditionBean;
import com.upg.cfsettle.mapping.filcs.FiLcsFundAttach;

/**
 * 理财师service
 * @author renzhuolun
 * @date 2016年6月6日 上午10:57:11
 * @version <b>1.0.0</b>
 */
@Service
public class FiLcsFundAttachServiceImpl extends BaseService implements IFiLcsFundAttachService {
	
	@Autowired
	private IFiLcsFundAttachDao fundAttachDao;

	@Override
	public void addFundAttachList(List<FiLcsFundAttach> fundAttachs) {
		fundAttachDao.saveOrUpdateAll(fundAttachs);
	}

	@Override
	public List<FiLcsFundAttach> getFundAttachsByFundId(Long id) {
		return this.findByCondition(new FiLcsFundAttach(null,id), null);
	}

	@Override
	public List<FiLcsFundAttach> findByCondition(FiLcsFundAttach fundAttach,Page page) {
		String hql = "from FiLcsFundAttach fiLcsFundAttach";
		QueryCondition condition = new QueryCondition(hql);
		if (fundAttach != null) {
			Long fundId = fundAttach.getFundId();
			if (fundId != null) {
				condition.addCondition(new ConditionBean("fiLcsFundAttach.fundId", ConditionBean.EQUAL, fundId));
			}
			Long attachId = fundAttach.getAttachId();
			if (attachId != null) {
				condition.addCondition(new ConditionBean("fiLcsFundAttach.attachId", ConditionBean.EQUAL, attachId));
			}
		}
		return fundAttachDao.queryEntity(condition.getConditionList(), page);
	}

	@Override
	public Long addFundAttach(FiLcsFundAttach fundAttach) {
		return fundAttachDao.save(fundAttach);
	}

	@Override
	public void deleteFundAttach(List<FiLcsFundAttach> delFundAttachs) {
		fundAttachDao.delAll(delFundAttachs);
	}
}