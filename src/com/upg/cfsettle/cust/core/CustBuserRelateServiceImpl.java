package com.upg.cfsettle.cust.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.upg.cfsettle.mapping.prj.CfsCustBuserRelate;
import com.upg.ucars.framework.annotation.Service;

@Service
public class CustBuserRelateServiceImpl implements ICustBuserRelateService{
	
	@Autowired
	private ICfsCustBuserRelateDao custBuserRelateDao;

	@Override
	public CfsCustBuserRelate getRelateByCustId(Long custId) {
		List<CfsCustBuserRelate> list = custBuserRelateDao.findByCustId(custId);
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}
}