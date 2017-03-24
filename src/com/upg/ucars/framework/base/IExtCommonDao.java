package com.upg.ucars.framework.base;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;

/**
 * 通用的Dao
 * 
 */
public interface IExtCommonDao {

	/**
	 * 保存一个实体类
	 * 
	 * @param entity
	 * @return
	 */
	public Long save(Object entity);

	/**
	 * 批量保存实体
	 * 
	 * @param entities
	 */
	public void save(Collection<Object> entities);

	/**
	 * 删除一个实体
	 * 
	 * @param entity
	 */
	public void delete(Object entity);

	/**
	 * 根据主键删除实体
	 * 
	 * @param clazz
	 * @param id
	 */
	public void delete(Class<?> clazz, Long id);

	/**
	 * 根据主键列表删除批量实体
	 * 
	 * @param clazz
	 * @param ids
	 */
	public void delete(Class<?> clazz, List<Long> ids);

	/**
	 * 删除多个实体
	 * 
	 * @param entities
	 */
	public void delete(Collection<?> entities);

	/**
	 * 更改一个实体
	 * 
	 * @param entity
	 */
	public void update(Object entity);

	/**
	 * 批量更新实体
	 * 
	 * @param entities
	 */
	public void update(List<Object> entities);

	/**
	 * 保存或更新
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(Object entity);

	/**
	 * 保存或更新多个实体
	 * 
	 * @param entities
	 */
	public void saveOrUpdate(Collection<?> entities);

	/**
	 * 根据ID获取一个实体
	 * 
	 * @param clazz
	 * @param id
	 * @return
	 */
	public <T> T get(Class<T> clazz, Long id);

	/**
	 * 查询实体列表
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public <T> List<T> findEntities(final String hql, final Object... values);

	/**
	 * 查询实体列表
	 * 
	 * @param hql
	 * @param params
	 * @param page
	 * @return
	 */
	public <T> List<T> findEntities(final String hql, Map<String, Object> params);

	/**
	 * 查询实体列表
	 * 
	 * @param hql
	 * @param params
	 * @param page
	 * @return
	 */
	public <T> List<T> findEntities(final String hql, Map<String, Object> params, Page page);

	/**
	 * 原生sql查询
	 * 
	 * @param sql
	 * @param valueMap
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> findMapListByStanderdSQL(final String sql, final Map<String, Object> valueMap,
			final Page page);

	/**
	 * 执行sql语句
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int executeSql(final String sql, final Map<String, Object> params);

	/**
	 * 合并实体属性
	 * 
	 * @param entity
	 * @return
	 */
	public <T> T merge(T entity);

	/**
	 * 查询实体列表
	 * 
	 * @param hql
	 * @param conditionList
	 * @param orderList
	 * @param page
	 * @return
	 */
	public <T> List<T> queryEntity(String hql, List<ConditionBean> conditionList, List<OrderBean> orderList, Page page);

}
