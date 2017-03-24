package com.upg.ucars.framework.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.EntityMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;

import com.upg.ucars.framework.exception.DAOException;

public class CommonDAOImp extends AbstractHibernateDAO implements ICommonDAO {

	
	
	
	public List findByOneProperty(Class cls,String property,List values) throws DAOException{
		//StringBuffer sb = new StringBuffer("select a from Branch as a ");
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(cls); 
		
		if(values!=null && values.size()==1)
		{
			detachedCriteria.add(Restrictions.eq(property, values.get(0)));
			//sb.append(" where a.").append(property).append("=?");
		}
		
		if(values!=null && values.size()> 1)
		{
			//sb.append(" where a.").append(property).append("in(:property)");
			detachedCriteria.add(Restrictions.in(property, values));
		}
		
		
		return this.getHibernateTemplate().findByCriteria(detachedCriteria);
		
	}
	
	public Serializable getPK(Object entity){
		Serializable pk=null;
		ClassMetadata meta=getSessionFactory().getClassMetadata(entity.getClass());
		if(meta==null)
			meta = getSessionFactory().getClassMetadata(entity.getClass().getSuperclass());
		if(meta!=null){
			pk=meta.getIdentifier(entity, EntityMode.POJO);
		}
		
		
		return pk;
	}

	public void evict(Object entity) throws DAOException {
		this.getHibernateTemplate().evict(entity);
		
	}

	public void bulkUpdate(String hql, Object... values) {
		this.getHibernateTemplate().bulkUpdate(hql, values);
		
	}

	@Override
	public List<Map<String, Object>> getObjectMapListByStanderdSQL(String sql, Map<String, Object> valueMap, Page page) {
		return getMapListByStanderdSQL(sql, valueMap, page);
	}

}
