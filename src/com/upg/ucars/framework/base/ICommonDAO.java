package com.upg.ucars.framework.base;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.DAOException;

public interface ICommonDAO {

	/**
	 * 按参数查询 
	 * <br>
	 * <p>注意：<li>'group by' or 'order by'只能一个空格。<li>不支持子查询中带'group by' or 'order by'. 
	 * @param hql  查询hql, 如: from Product p where p.prodNo=:prodNo
	 * @param parameterMap 参数的键值对,如：<'bankNo', '100200300'>
	 * @param page
	 * @throws DAOException
	 * @return
	 */
	public <T> List<T> queryByParam(String hql, Map<String, Object> parameterMap, Page page) throws DAOException;
	
	/**
	 * @see AbstractHibernateDAO#queryByCondition(QueryCondition qc, Page page)
	 * @param qc
	 * @param page
	 * @return
	 * @throws DAOException
	 */
	public <T> List<T> queryByCondition(QueryCondition qc, Page page) throws DAOException;
	/**
	 * @see AbstractHibernateDAO#queryByCondition(QueryCondition qc, Page page,QueryComponent queryComponent)
	 * @param qc
	 * @param page
	 * @param queryComponent
	 * @return
	 * @throws DAOException
	 */
	public List queryByCondition(QueryCondition qc, Page page,QueryComponent queryComponent) throws DAOException;
	/**
	 * 带多个参数的HQL查询
	 *
	 * @param hql
	 * @param values 参数
	 * @return
	 * @throws DAOException
	 */
	public <T> List<T> find(String hql, Object[] values) throws DAOException;
	/**
	 * 带一个参数的HQL查询
	 *
	 * @param hql
	 * @param value
	 * @return
	 * @throws DAOException
	 */
	public <T> List<T> find(String hql, Object value) throws DAOException;
	/**
	 * 从当前的hibernate session缓存中清除此对象
	 *
	 * @param entity
	 * @throws DAOException
	 */
	public void evict(Object entity) throws DAOException;
	
	
	/**
	 * 依据某个对象指定的属性进行查询
	 * @param cls
	 * @param property
	 * @param values
	 * @return
	 * @throws DAOException
	 */
	public List findByOneProperty(Class cls,String property,List values) throws DAOException;
	/**
	 * 获取实体主键
	 * @param entity 实体
	 * @return
	 */
	public Serializable getPK(Object entity);
	
	public void bulkUpdate(String hql, Object... values);
	
	/**
	 * sql语句查询
	 * 
	 * @param sql
	 * @param valueMap
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getObjectMapListByStanderdSQL(final String sql,final Map<String,Object> valueMap,final Page page);
}
