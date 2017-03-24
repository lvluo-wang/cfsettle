package com.upg.finance.xhh.core;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.ExtCommonDaoImpl;

@Dao
public class XhhFinanceDaoImpl extends ExtCommonDaoImpl implements IXhhFinanceDao{
	
	@Override
	@Resource(name="sysHibernateTemplate")
	public void setSuperHibernateTemplate(HibernateTemplate hibernateTemplate){
		super.setHibernateTemplate(hibernateTemplate);
	}

}
