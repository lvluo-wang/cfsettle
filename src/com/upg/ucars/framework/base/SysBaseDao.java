package com.upg.ucars.framework.base;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.orm.hibernate3.HibernateTemplate;

public abstract class SysBaseDao<T, PK extends Serializable> extends BaseDAO<T, PK> {

	private HibernateTemplate	sysHibernateTemplate;

	@Resource(name = "sysHibernateTemplate")
	public void setSysHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.sysHibernateTemplate = hibernateTemplate;
	}

	protected void initDao() throws Exception {
		super.initDao();
		if (sysHibernateTemplate != null) {
			super.setHibernateTemplate(sysHibernateTemplate);
		}
	}

}
