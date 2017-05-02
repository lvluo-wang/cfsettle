package com.upg.ucars.framework.base;

import java.util.List;

import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;

/**
 * DAO的通用接口
 * 
 * @author shentuwy
 */
public interface IBaseDAO<T,PK extends java.io.Serializable> {
	
	public void delete(PK id);
	
	/**
	 * 保存
	 * @param entity
	 * @throws DAOException
	 */
	public PK save(T entity) throws DAOException;
	/**
	 * 修改
	 * @param entity
	 * @return
	 * @throws DAOException
	 */
	public void update(T entity) throws DAOException;
	
	/**
	 * 合并在同一session中的两个相同oid对象,保存或修改，解决
	 *  different object with the same identifier value was already associated wit
	 * @param entity
	 * @throws DAOException
	 */
	public T merge(T entity) throws DAOException;
	
	/**
	 * 保存或修改
	 * @param entity
	 * @return
	 * @throws DAOException
	 */
	public void saveOrUpdate(T entity) throws DAOException;
	/**
	 * 批量保存或修改
	 * @param entityList
	 * @return
	 * @throws DAOException
	 */
	public void saveOrUpdateAll(List<T> entityList) throws DAOException;
	/**
	 * 批量删除
	 * @param entityList	对象集合
	 * @throws DAOException
	 */
	public void delAll(List<T> entityList) throws DAOException;
	
	/**
	 * 获取实体对象
	 * @param entityClass
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public T get(PK id) throws DAOException;
	
	/**
	 * 获取实体对象
	 * @param entityClass
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public T load(PK id) throws DAOException;
	/**
	 * 根据实体删除
	 * @param entity
	 * @throws DAOException
	 */
	public void delete(T entity) throws DAOException;
	/**
	 * 移出缓存中对象
	 * 
	 * @param entity
	 */
	public void evict(final Object entity);
	
	/**
	 * 条件查询
	 *
	 * @param conditionList
	 * @param page
	 * @return
	 * @throws DAOException
	 */
	public List<T> queryEntity(List<ConditionBean> conditionList, Page page) throws DAOException;
	/**
	 * 带排序条件查询
	 *
	 * @param conditionList
	 * @param page
	 * @return
	 * @throws DAOException
	 */
	public List<T> queryEntity(List<ConditionBean> conditionList, List<OrderBean> orderList, Page page) throws DAOException;
	/**
	 * 页面组件查询
	 *
	 * @param qcpt 页面查询组件
	 * @param page 分页对象
	 * @return
	 * @throws DAOException
	 */
	public List<T> queryEntity(QueryComponent qcpt, Page page) throws DAOException;
	
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
	//public List queryByParam(String hql, Map<String, Object> parameterMap, Page page) throws DAOException;
	
	/**
	 * @see AbstractHibernateDAO#queryByCondition(QueryCondition qc, Page page)
	 * @param qc
	 * @param page
	 * @return
	 * @throws DAOException
	 */
	//public List queryByCondition(QueryCondition qc, Page page) throws DAOException;
	/**
	 * @see AbstractHibernateDAO#queryByCondition(QueryCondition qc, Page page,QueryComponent queryComponent)
	 * @param qc
	 * @param page
	 * @param queryComponent
	 * @return
	 * @throws DAOException
	 */
	//public List queryByCondition(QueryCondition qc, Page page,QueryComponent queryComponent) throws DAOException;
	/**
	 * 带多个参数的HQL查询
	 *
	 * @param hql
	 * @param values 参数
	 * @return
	 * @throws DAOException
	 */
	//public List find(String hql, Object[] values) throws DAOException;
	/**
	 * 带一个参数的HQL查询
	 *
	 * @param hql
	 * @param value
	 * @return
	 * @throws DAOException
	 */
	//public List find(String hql, Object value) throws DAOException;
	/**
	 * 按hql查询
	 * @param hql
	 * @return	结果集
	 * @throws DAOException
	 */
	//public List find(String hql) throws DAOException;
		
	
	/**
	 * 根据主键及版本号读取实体对象
	 * @param keyno   实体主键值
	 * @param version 实体版本号
	 * @return 实体对象
	 * @throws DAOException
	 * @author alw
	 */
	public T readByVersion(PK keyno,Number version)throws DAOException;
	
	/**
	 * 自定义hql
	 * @author renzhuolun
	 * @date 2017年4月19日 下午8:57:52
	 * @param hql
	 * @param conditionList
	 * @param orderList
	 * @param page
	 * @return
	 */
	public List<T> queryEntityByCustomerHQL(String hql,List<ConditionBean> conditionList, List<OrderBean> orderList, Page page);
}
