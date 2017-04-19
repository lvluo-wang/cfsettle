package com.upg.ucars.framework.base;


import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.StaleObjectStateException;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;
import com.upg.ucars.util.StringUtil;
/**
 * DAO基本实现类--实现基本操作方法
 *
 * @author shentuwy
 */
public abstract class BaseDAO<T,PK extends Serializable> extends AbstractHibernateDAO implements IBaseDAO<T,PK>{
	
	protected List<T> EMPTY_LIST = new ArrayList<T>(0);

	/**
	 * 获取数据表的映射类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Class<T> getEntityClass() {
		return (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public void delete(PK id) throws DAOException{
		try {
			this.getHibernateTemplate().delete(get(id));
		} catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] { e.getMessage() }, e);
		}
				
	}

	public PK save(T entity) throws DAOException {
		
		try {
			return (PK)this.getHibernateTemplate().save(entity);
		} catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] { e.getMessage() }, e);
		}
		
	}

	public void update(T entity) throws DAOException {
		try {
			this.getHibernateTemplate().update(entity);
		} catch (StaleObjectStateException e) {
			throw ExceptionManager.getException(ServiceException.class,	ErrorCodeConst.DB_VERSION_ERROR,  e);
		} catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] { e.getMessage() }, e);
		}
		
		
	}
	
	public T merge(T entity) throws DAOException {
		try {
			return this.getHibernateTemplate().merge(entity);
		} catch (StaleObjectStateException e) {
			throw ExceptionManager.getException(ServiceException.class,	ErrorCodeConst.DB_VERSION_ERROR,  e);
		} catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] { e.getMessage() }, e);
		}
		
		
	}

	public void saveOrUpdate(T entity) throws DAOException {
		try {
			this.getHibernateTemplate().saveOrUpdate(entity);
		} catch (StaleObjectStateException e) {
			throw ExceptionManager.getException(ServiceException.class,	ErrorCodeConst.DB_VERSION_ERROR,  e);
		}  catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] { e.getMessage() }, e);
		}
		
	}
	
	public void saveOrUpdateAll(List<T> entityList) throws DAOException{
		
		try {
			this.getHibernateTemplate().saveOrUpdateAll(entityList);
		} catch (StaleObjectStateException e) {
			throw ExceptionManager.getException(ServiceException.class,	ErrorCodeConst.DB_VERSION_ERROR,  e);
		}  catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] { e.getMessage() }, e);
		}
	}

	public T get(PK id) throws DAOException {
		try {
			return (T)this.getHibernateTemplate().get(getEntityClass(), id);
		} catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] { e.getMessage() }, e);
		}
		
	}

	public T load(PK id) throws DAOException {
		try {
			return (T)this.getHibernateTemplate().load(getEntityClass(), id);
		} catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] { e.getMessage() }, e);
		}
		
	}
	
	
	
	public void delete(T entity) throws DAOException {
		try {
			this.getHibernateTemplate().delete(entity);
		} catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] { e.getMessage() }, e);
		}		
	}
	
	public void evict(final Object entity){
		this.getHibernateTemplate().evict(entity);
	}
	
	public List<T> queryEntity(List<ConditionBean> conditionList, Page page) throws DAOException{
		String className = this.getEntityClass().getSimpleName();
		String hql = "from "+className + " "+StringUtil.firstLower(className);
		QueryCondition qc = new QueryCondition(hql);
		qc.addConditionList(conditionList);
		List<T> list = super.queryByCondition(qc, page);
		return list;
	}
	
	
	public List<T> queryEntity(List<ConditionBean> conditionList,
			List<OrderBean> orderList, Page page) throws DAOException {
		String className = this.getEntityClass().getSimpleName();
		String hql = "from "+className + " "+StringUtil.firstLower(className);
		QueryCondition qc = new QueryCondition(hql);
		qc.addConditionList(conditionList);
		qc.addOrderList(orderList);
		List<T> list = super.queryByCondition(qc, page);
		
		return list;
	}

	public List<T> queryEntity(QueryComponent qcpt, Page page) throws DAOException{
		String className = this.getEntityClass().getSimpleName();
		String hql = "from "+className + " "+StringUtil.firstLower(className);
		QueryCondition qc = new QueryCondition(hql);
		
		List<T> list = super.queryByCondition(qc, page , qcpt );
		return list;
	}
	public void delAll(List<T> entityList) throws DAOException {
		try {
			this.getHibernateTemplate().deleteAll(entityList);
		}	catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] { e.getMessage() }, e);
		}	
	}	
	
	/**
	 * 根据主键及版本号读取实体对象
	 * 采用此方法时，对应实体映射中必须指定版本字段的映射，并且映射名称必须为"version"
	 * @param keyno   实体主键值
	 * @param version 实体版本号
	 * @return 实体对象
	 * @throws DAOException
	 * @author alw
	 */
	public T readByVersion(PK keyno,Number version) throws DAOException{
		
		String versionPropertyName = "ver";
		try {
        	String clsName = getEntityClass().getName();
        	
        	Object[] paramObjs=new Object[]{keyno,version};
        	
           
        	org.hibernate.metadata.ClassMetadata cmt = getHibernateTemplate().getSessionFactory().getClassMetadata(clsName);
        	String idPropertyName = cmt.getIdentifierPropertyName();
        
        	StringBuffer sb = new StringBuffer();
        	sb.append("select a from ").append(clsName).append(" a ");
        	sb.append("where a.").append(idPropertyName).append("=? ");
        	sb.append(" and a.").append(versionPropertyName).append("=?");
        	
        	
        	List ls = getHibernateTemplate().find(sb.toString(), paramObjs);
            if(ls!=null&&ls.size()==1) 
            	return (T)ls.get(0);
            else{
            	//这里抛出的异常与Hibernate进行的版本检查时抛出的异常保持一致
            	//Exception e = new org.hibernate.StaleObjectStateException(clsName,keyno);
            	//throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] { "根据pk【"+keyno+"】及version【"+version+"】未查到对象!" });
            	throw ExceptionManager.getException(ServiceException.class,	ErrorCodeConst.DB_VERSION_ERROR);
            }
           
        } catch (Exception re) {
        	throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[] { re.getMessage() }, re);
        }
	}
	
	public List<T> queryEntityByCustomerHQL(String hql,List<ConditionBean> conditionList, List<OrderBean> orderList, Page page) throws DAOException{
		QueryCondition qc = new QueryCondition(hql);
		qc.addConditionList(conditionList);
		qc.addOrderList(orderList);
		List<T> list = super.queryByCondition(qc, page);
		return list;
	}
	
}