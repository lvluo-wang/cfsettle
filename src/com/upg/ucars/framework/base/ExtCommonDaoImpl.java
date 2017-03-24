package com.upg.ucars.framework.base;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;

@Dao
public class ExtCommonDaoImpl extends AbstractHibernateDAO implements IExtCommonDao {

	@Override
	public Long save(Object entity) {
		Long result = null;
		Serializable pk = getHibernateTemplate().save(entity);
		if (pk != null && pk instanceof Long) {
			result = (Long) pk;
		}
		return result;
	}

	@Override
	public void save(Collection<Object> entities) {
		if (entities != null && !entities.isEmpty()) {
			for (Object entity : entities) {
				save(entity);
			}
		}
	}

	@Override
	public void delete(Object entity) {
		getHibernateTemplate().delete(entity);
	}

	@Override
	public void delete(Class<?> clazz, Long id) {
		Object obj = get(clazz, id);
		delete(obj);
	}

	@Override
	public void delete(Class<?> clazz, List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				delete(clazz, id);
			}
		}
	}

	@Override
	public void delete(Collection<?> entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	@Override
	public void update(Object entity) {
		getHibernateTemplate().update(entity);
	}

	@Override
	public void update(List<Object> entities) {
		if (entities != null && !entities.isEmpty()) {
			for (Object entity : entities) {
				update(entity);
			}
		}
	}

	@Override
	public void saveOrUpdate(Object entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdate(Collection<?> entities) {
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

	@Override
	public <T> T get(Class<T> clazz, Long id) {
		T result = null;
		if (id != null) {
			result = getHibernateTemplate().get(clazz, id);
		}
		return result;
	}

	@Override
	public <T> List<T> findEntities(final String hql, final Object... values) {
		List<T> result = getHibernateTemplate().find(hql, values);
		return getEmptyListIfNull(result);
	}

	@SuppressWarnings("unchecked")
	private static final <T> List<T> getEmptyListIfNull(List<T> list) {
		return list == null ? Collections.EMPTY_LIST : list;
	}

	@Override
	public <T> List<T> findEntities(String hql, Map<String, Object> params, Page page) {
		List<T> result = queryByParam(hql, params, page);
		return getEmptyListIfNull(result);
	}

	@Override
	public <T> List<T> findEntities(String hql, Map<String, Object> params) {

		String[] keyArray = new String[0];
		Object[] valueArray = new Object[0];
		if (params != null && !params.isEmpty()) {
			List<String> keyList = new ArrayList<String>(params.size());
			List<Object> valueList = new ArrayList<Object>(params.size());
			for (Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator(); it.hasNext();) {
				Map.Entry<String, Object> entry = it.next();
				keyList.add(entry.getKey());
				valueList.add(entry.getValue());
			}
			keyArray = keyList.toArray(new String[params.size()]);
			valueArray = valueList.toArray();
		}
		List<T> result = getHibernateTemplate().findByNamedParam(hql, keyArray, valueArray);
		return getEmptyListIfNull(result);
	}

	@Override
	public List<Map<String, Object>> findMapListByStanderdSQL(String sql, Map<String, Object> valueMap, Page page) {
		return getMapListByStanderdSQL(sql, valueMap, page);
	}

	@Override
	public int executeSql(final String sql, final Map<String, Object> params) {
		return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Integer>() {
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sql);
				if (params != null && !params.isEmpty()) {
					for (Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator(); it.hasNext();) {
						Map.Entry<String, Object> entry = it.next();
						applyNamedParameterToQuery(query, entry.getKey(), entry.getValue());
					}
				}
				return query.executeUpdate();
			}
		});

	}

	protected void applyNamedParameterToQuery(Query queryObject, String paramName, Object value)
			throws HibernateException {

		if (value instanceof Collection) {
			queryObject.setParameterList(paramName, (Collection) value);
		} else if (value instanceof Object[]) {
			queryObject.setParameterList(paramName, (Object[]) value);
		} else {
			queryObject.setParameter(paramName, value);
		}
	}

	@Override
	public <T> T merge(T entity) {
		return getHibernateTemplate().merge(entity);
	}

	@Override
	public <T> List<T> queryEntity(String hql, List<ConditionBean> conditionList, List<OrderBean> orderList, Page page) {
		QueryCondition qc = new QueryCondition(hql);
		qc.addConditionList(conditionList);
		qc.addOrderList(orderList);
		List<T> list = super.queryByCondition(qc, page);
		return list;
	}
}
