package com.upg.ucars.framework.base;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.hibernate.UpperAliasToEntityMapResultTransformer;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.model.OrderBean;
import com.upg.ucars.util.StringUtil;

/**
 * DAO基本实现类-实现复杂查询功能
 *
 * @author shentuwy
 */
public abstract class AbstractHibernateDAO extends HibernateDaoSupport {
	private boolean cacheQueries = false;
	private String queryCacheRegion;
	
	@Resource(name="hibernateTemplate")
	public void setSuperHibernateTemplate(HibernateTemplate hibernateTemplate){
		super.setHibernateTemplate(hibernateTemplate);
	}
	

	public void setCacheQueries(boolean cacheQueries) {
		this.cacheQueries = cacheQueries;
	}

	public void setQueryCacheRegion(String queryCacheRegion) {
		this.queryCacheRegion = queryCacheRegion;
	}

	public boolean isCacheQueries() {
		return cacheQueries;
	}

	public String getQueryCacheRegion() {
		return queryCacheRegion;
	}
	
	protected void prepareQuery(Query query) {
		query.setCacheable(isCacheQueries());
		if (queryCacheRegion != null) {
			query.setCacheRegion(queryCacheRegion);
		}
	}
	protected void prepareCriteria(Criteria criteria) {
		
		criteria.setCacheable(isCacheQueries());
		if (queryCacheRegion != null) {
			criteria.setCacheRegion(queryCacheRegion);
		}

		SessionFactoryUtils.applyTransactionTimeout(criteria, getSessionFactory());
	}
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
	public <T> List<T> queryByParam(String hql, Map<String, Object> parameterMap, Page page) throws DAOException{
		if (page==null){//不分页，查询出所有的信息。
			return this.queryByParam(null, hql, parameterMap, null);
		}
		
		// 全部lower用于计算index.
		String lowHql = hql.toLowerCase();
		int pos1 = lowHql.indexOf("select ");
		int pos2 = lowHql.indexOf("from ");
		if (pos1>pos2)//'select'是子查询中的。
			pos1=-1;			

		String behindFrom = hql.substring(pos2);
		// 算总数的hql
		String countHql = "select count(*) " + behindFrom;
		//兼容查询语句中多个字段查询如:select tb.c1,tb.c2 from table tb 
//		if (pos1 >= 0) {			
//			String rs = hql.substring(pos1 + "select".length() + 1, pos2);
//			countHql = "select count(" + rs + ") " + behindFrom;
//		}
		
		
		String lowerCountHql = countHql.toLowerCase();
		int pos3 = lowerCountHql.indexOf("group by");
		int pos4 = lowerCountHql.indexOf("order by");
		int endPos=-1;
		{//存在GROUP 及 ORDER时，取最小的INDEX删除
			if (pos3>=0)
				endPos=pos3;
			if (endPos<0 || (pos4>=0 && pos4<endPos))
				endPos=pos4;
			
		}
		if (endPos>=0){
			countHql=countHql.substring(0, endPos);
		}

		return this.queryByParam(countHql, hql, parameterMap, page);

	}
	/**
	 * 按参数查询，需要给出查询总数的hql
	 * @param countHql 计算总数的hql, 如：select count(p.id) from Product p where p.prodNo=:prodNo
	 * @param hql 查询hql, 如 from Product p where p.prodNo=:prodNo
	 * @param parameterMap
	 * @param page
	 * @throws DAOException
	 * @return
	 */
	public List queryByParam(final String countHql, final String hql,
			final Map<String, Object> parameterMap, final Page page) throws DAOException {
		
//		LogFactory.getLog(getClass()).info("count hql:"+countHql);
//		LogFactory.getLog(getClass()).info("query hql:"+hql);
		
		processParameterTrim(parameterMap);

		return (List) this.getHibernateTemplate().executeFind(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Map<String, Object> parameterMap2 = parameterMap;
						if (parameterMap == null || parameterMap.isEmpty())
							parameterMap2 = new HashMap<String, Object>(0);
						if (page == null||page.isQueryAll()) {   // 不需要分页
							Query query = session.createQuery(hql);
							query.setCacheable(isCacheQueries());
							if (queryCacheRegion != null) {
								query.setCacheRegion(queryCacheRegion);
							}
							Set<String> paramSet = parameterMap2.keySet();
							for (String prarmKey : paramSet) {
								Object paramValue = parameterMap2.get(prarmKey);
								if (paramValue instanceof Collection)
									query.setParameterList(prarmKey, (Collection<?>)paramValue);
								else if (paramValue instanceof Object[])
									query.setParameterList(prarmKey, (Object[])paramValue);
								else 
									query.setParameter(prarmKey, parameterMap2.get(prarmKey));								
							}
							try {
								return query.list();
							} catch (Exception e) {
								throw ExceptionManager.getException(DAOException.class, ErrorCodeConst.DB_COMMON_ERROR, new String[]{"hql:"+hql}, e);
							}							
						}
						// 分页控制						
						Query query = session.createQuery(countHql);
						query.setCacheable(isCacheQueries());
						if (queryCacheRegion != null) {
							query.setCacheRegion(queryCacheRegion);
						}
						Set<String> paramSet = parameterMap2.keySet();
						for (String prarmKey : paramSet) {
							Object paramValue = parameterMap2.get(prarmKey);
							if (paramValue instanceof Collection)
								query.setParameterList(prarmKey, (Collection<?>)paramValue);
							else if (paramValue instanceof Object[])
								query.setParameterList(prarmKey, (Object[])paramValue);
							else 
								query.setParameter(prarmKey, parameterMap2.get(prarmKey));
						}

						Long totalCount = null;
						
						try {
							totalCount = (Long) query.list().get(0);
						} catch (Exception e) {
							throw ExceptionManager.getException(DAOException.class, ErrorCodeConst.DB_COMMON_ERROR, new String[]{"count total hql:"+countHql}, e);
						}
						
						page.afterNew(totalCount.intValue());
						if (page.getCurrentPage()> page.getTotalPages()){
							page.setCurrentPage(page.getTotalPages());
						}
						
						//无记录
						if (page.getTotalRows() < 1) {// 为空
							return new ArrayList(0);
						}
						
						//查询
						query = session.createQuery(hql);
						query.setCacheable(isCacheQueries());
						if (queryCacheRegion != null) {
							query.setCacheRegion(queryCacheRegion);
						}

						query.setFirstResult((page.getCurrentPage() - 1) * page.getPageSize())
							.setMaxResults(page.getPageSize());						
						for (String prarmKey : paramSet) {
							Object paramValue = parameterMap2.get(prarmKey);
							if (paramValue instanceof Collection)
								query.setParameterList(prarmKey, (Collection<?>)paramValue);
							else if (paramValue instanceof Object[])
								query.setParameterList(prarmKey, (Object[])paramValue);
							else 
								query.setParameter(prarmKey, parameterMap2.get(prarmKey));
						}
						try {
							return query.list();
						} catch (Exception e) {
							throw ExceptionManager.getException(DAOException.class, ErrorCodeConst.DB_COMMON_ERROR, new String[]{"hql:"+hql}, e);
						}
					}

		});

	}
	/**
	 * 按条件查询
	 *
	 * @param qc
	 * @param page
	 * @throws DAOException
	 * @return
	 */
	public <T> List<T> queryByCondition(QueryCondition qc, Page page) throws DAOException{	
		if(page!=null){
			if (!StringUtil.isEmpty(page.getSortName())){
				qc.addOrder(new OrderBean(page.getSortName(), Page.SORT_ASC.equals(page.getSortOrder())));
			}
				
		}

		qc.contextInitialized();
		List list = this.queryByParam(qc.getCountHql(), qc.getQueryHql(), 
				qc.getParameterMap(), page);		
		return list;
	}
	/**
	 * 基于查询组件，page,qc的查询 
	 *
	 * @param qc
	 * @param page
	 * @param queryComponent
	 * @return
	 * @throws DAOException
	 */
	public List queryByCondition(QueryCondition qc, Page page,QueryComponent queryComponent) throws DAOException {		
		qc.read(queryComponent);
		if(page!=null){
			if (!StringUtil.isEmpty(page.getSortName())){
				qc.addOrder(new OrderBean(page.getSortName(), Page.SORT_ASC.equals(page.getSortOrder())));
			}
		}

		qc.contextInitialized();
		List list = this.queryByParam(qc.getCountHql(), qc.getQueryHql(), 
				qc.getParameterMap(), page);		
		return list;
	}
	
	
	public <T> List<T> find(String hql, Object[] values) throws DAOException {
		try {
			return this.getHibernateTemplate().find(hql, values);
		} catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class, ErrorCodeConst.DB_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}
		
	}
	
	public <T> List<T> find(String hql, Object value) throws DAOException {
		try {
			return this.getHibernateTemplate().find(hql, value);
		} catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class, ErrorCodeConst.DB_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}
		
	}
	public <T> List<T> find(String hql) throws DAOException {
		try {
			return this.getHibernateTemplate().find(hql);
		} catch (Exception e) {
			throw ExceptionManager.getException(DAOException.class, ErrorCodeConst.DB_COMMON_ERROR, new String[]{e.getMessage()}, e);
		}
		
	}
	
	
	
	
	
	
	/**
	 * 针对HibernateTemplate的find方法提供分页查询的支持
	 * @param hql
	 * @param values
	 * @param page
	 * @return
	 * @author alw
	 */
	
	public List find(final String hql, final Object[] values,final Page page) {
		
		  return getHibernateTemplate().executeWithNativeSession(
				  new HibernateCallback<List>() {
						   public List doInHibernate( Session session)throws HibernateException, SQLException {
							   
							   Query query = session.createQuery(hql);
							   prepareQuery(query);
							   if (values != null&&values.length>0) {
									for (int i = 0; i < values.length; i++) {
										query.setParameter(i, values[i]);
									}
								}
								
							   if(page!=null&&!page.isQueryAll())   // 需要分页
					            { 
								   
								    int fromCount = StringUtil.getHasCount(hql.toLowerCase(), " from ");
								    
								    // 增加 distinct关键词时支持分页功能
								    String selecthql = hql.substring(0, hql.toLowerCase().indexOf(" from "));
								    int distinctCount = StringUtil.getHasCount(selecthql.toLowerCase(), " distinct ");
								    
								    if(fromCount>1||distinctCount>0)  //数量>1 认为是复合查询 或者存在distinct 关键词时 通过ScrollableResults 取得总行数
								    {
								        ScrollableResults scrollableResults = query.scroll();
							            scrollableResults.last();
							            int rownum = scrollableResults.getRowNumber();
							            page.afterNew(rownum+1);
								    }else  //简单查询 通过count 取得总行数
								    {
								    	    String counthql = "";
									        if (hql.toLowerCase().indexOf(" order ") > 0)
									            counthql = hql.substring(0, hql.toLowerCase().indexOf(" order "));
									        else counthql = hql;
									        counthql = counthql.substring(counthql.toLowerCase().indexOf(" from "));

									        counthql = "select count(*) " + counthql;
									       
									        Query countQuery = session.createQuery(counthql);
									        prepareQuery(countQuery);
									        
									        if (values != null&&values.length>0) {
												for (int i = 0; i < values.length; i++) {
													countQuery.setParameter(i, values[i]);
												}
											}
									        //Hibernate3.2中最新版本中返回的类型改为java.lang.Long,3.1及以前版本是用的Integer,所以这里为了兼容两个版本
									        //采用公共的父类 Number
									        int rownum = ( (Number) countQuery.iterate().next()).intValue();
										    page.afterNew(rownum);
								    }

						            query.setMaxResults(page.getPageSize());
						            query.setFirstResult(page.getStartRow());
					            }
							   
							    List list = query.list();
							    return list;
						   }
				  });
		
	}
	
	
	/**
	 * 针对HibernateTemplate的findByCriteria方法提供分页查询的支持
	 * @param criteria
	 * @param page
	 * @param isRootEntity   是否只返回 根实体的List
	 * @return
	 * @author alw
	 */
	
	public List findByCriteria(final DetachedCriteria criteria,final Page page,final boolean isRootEntity) {
		
		  return getHibernateTemplate().executeWithNativeSession(
				  new HibernateCallback<List>() {
						   public List doInHibernate(Session session)throws HibernateException, SQLException {
							   
							    Criteria executableCriteria = criteria.getExecutableCriteria(session);
							    
								prepareCriteria(executableCriteria);
								
								
								if(page!=null&&!page.isQueryAll())   // 需要分页
					            { 
									int totalCount = ((Integer) executableCriteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
									executableCriteria.setProjection(null); 
									page.afterNew(totalCount);
									if(totalCount>0)
									{
										executableCriteria.setFirstResult(page.getStartRow());
										executableCriteria.setMaxResults(page.getPageSize());
									}
					            }
								
								if(isRootEntity)
								{
									executableCriteria.setResultTransformer(Criteria.ROOT_ENTITY).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
								}
					            List ls = executableCriteria.list();
					            
					            
							    return ls;
						   }
				  });
		
	}
	
	/**
     * 通过标准的SQL查询数据
     * @param sql String
     * @return List
     * @author alw
     */
	protected List<HsDynaBean> getBeanListByStanderdSQL(final String sql,final Object[] values,final Page page){
		
		  return getHibernateTemplate().executeWithNativeSession(
				  new HibernateCallback<List<HsDynaBean>>() {
						   public List doInHibernate(Session session)throws HibernateException, SQLException {
							   
							   //这里设置结果集的转换器为自己扩展的转换器，将字段别名转为大写，进行查询，生成的HsDynaBean中的属性名称统一采用大写  如：HsDynaBean.get("NAME");
							   Query query = session.createSQLQuery(sql).setResultTransformer(UpperAliasToEntityMapResultTransformer.INSTANCE); //返回一个map,KEY:为DB中名称一致（大小写一致）;
							   //prepareQuery(query);  //createSQLQuery返回的对象不受hibernate 管理，因此对其设置缓存失效
							   if (values != null&&values.length>0) {
									for (int i = 0; i < values.length; i++) {
										query.setParameter(i, values[i]);
									}
								}
								
							   if(page!=null&&!page.isQueryAll())   // 需要分页,取得总行数设置分页查询
					          { 
								   
								     String counthql = "select count(*) from("+sql+") count_view";
								     
								     Query countQuery = session.createSQLQuery(counthql);
								        //prepareQuery(countQuery);//createSQLQuery返回的对象不受hibernate 管理，因此对其设置缓存失效
								        
								        if (values != null&&values.length>0) {
											for (int i = 0; i < values.length; i++) {
												countQuery.setParameter(i, values[i]);
											}
										}
							        //Hibernate3.2中最新版本中返回的类型改为java.lang.Long,3.1及以前版本是用的Integer,所以这里为了兼容两个版本
							        //采用公共的父类 Number
								     
							        int rownum = ( (Number) countQuery.list().get(0)).intValue();
								    page.afterNew(rownum);
								    
								    
						            query.setMaxResults(page.getPageSize());
						            query.setFirstResult(page.getStartRow());
					          }
							   
							    List<Map> list = query.list();
							    
							    List<HsDynaBean> returnLs = new ArrayList();
							    for(Map map:list)
							    {
							    	HsDynaBean db = new HsDynaBean(map);
							    	returnLs.add(db);
							    }
							    return returnLs;
							   
							   
						   }
   
				  });
    }

	/***
	 * findListByMap(sql分页查询)
	 * @param sql
	 * @param values
	 * @param page
	 * @param clazz
	 * @return List<?>

	 * @throws

	 * @since
	 */
	protected List<?> findListByMap(final String sql,final Map<String, Object> values,final Page page,final Class<?> clazz){
		Session session=super.getSession();
		Query query=session.createSQLQuery(sql);
		String sqlCount="select Count(1) from ("+sql+") count_view";
		Query queryCount=session.createSQLQuery(sqlCount);
		for (Map.Entry<String, Object> enty : values.entrySet()) {

			query.setParameter(enty.getKey(),enty.getValue());
			queryCount.setParameter(enty.getKey(),enty.getValue());
		}

		if(page!=null&&!page.isQueryAll()){
			int rownum = ( (Number) queryCount.list().get(0)).intValue();
			page.afterNew(rownum);
			query.setFirstResult((page.getCurrentPage()-1)*page.getPageSize());
			query.setMaxResults(page.getPageSize());
		}
		List<?> list=query.setResultTransformer(Transformers.aliasToBean(clazz)).list();
		session.flush();
		session.clear();
//		session.close();
		return list;
	}
	
	/**
     * 通过标准的SQL查询数据
     * <br>如：select a.f1,a.f2 from Aa a where a.f1=? and a.f2=?
     * @param sql String
     * @return List
     * @author alw
     */
	protected List<Map<String,Object>> getMapListByStanderdSQL(final String sql,final Object[] values,final Page page){
		
		  return getHibernateTemplate().executeWithNativeSession(
				  new HibernateCallback<List<Map<String,Object>>>() {
						   public List doInHibernate(Session session)throws HibernateException, SQLException {
							   
							   //这里设置结果集的转换器为自己扩展的转换器，将字段别名转为大写，生成的Map中的Key统一采用大写  如：Map.get("NAME");
							   Query query = session.createSQLQuery(sql.toUpperCase()).setResultTransformer(UpperAliasToEntityMapResultTransformer.INSTANCE); //返回一个map,KEY:为DB中名称一致（大小写一致）;
							   //prepareQuery(query);  //createSQLQuery返回的对象不受hibernate 管理，因此对其设置缓存失效
							   if (values != null&&values.length>0) {
									for (int i = 0; i < values.length; i++) {
										query.setParameter(i, values[i]);
									}
								}
								
							   if(page!=null&&!page.isQueryAll())   // 需要分页,取得总行数设置分页查询
					          { 
								   
								     String counthql = "select count(*) from("+sql+") count_view";
								     
								     Query countQuery = session.createSQLQuery(counthql);
								        //prepareQuery(countQuery);//createSQLQuery返回的对象不受hibernate 管理，因此对其设置缓存失效
								        
								        if (values != null&&values.length>0) {
											for (int i = 0; i < values.length; i++) {
												countQuery.setParameter(i, values[i]);
											}
										}
							        //Hibernate3.2中最新版本中返回的类型改为java.lang.Long,3.1及以前版本是用的Integer,所以这里为了兼容两个版本
							        //采用公共的父类 Number
								     
							        int rownum = ( (Number) countQuery.list().get(0)).intValue();
								    page.afterNew(rownum);
								    
								    
						            query.setMaxResults(page.getPageSize());
						            query.setFirstResult(page.getStartRow());
					          }
							   
							    List<Map<String,Object>> list = query.list();
							    return list;
							   
						   }
   
				  });
    }
	/**
	 * 增加标准的sql查询，支持分页和map方式参数注入
	 * @param sql
	 * @param valueMap
	 * @param page
	 * @author cuckoo
	 * @return
	 */
	protected List<Map<String,Object>> getMapListByStanderdSQL(final String sql,final Map<String,Object> valueMap,final Page page){
		
		  return getHibernateTemplate().executeWithNativeSession(
				  new HibernateCallback<List<Map<String,Object>>>() {
						   public List doInHibernate(Session session)throws HibernateException, SQLException {
							   
							   //这里设置结果集的转换器为自己扩展的转换器，将字段别名转为大写，生成的Map中的Key统一采用大写  如：Map.get("NAME");
							   Query query = session.createSQLQuery(sql.toUpperCase()).setResultTransformer(UpperAliasToEntityMapResultTransformer.INSTANCE); //返回一个map,KEY:为DB中名称一致（大小写一致）;
								if(valueMap!=null){
									Set<String> keys=valueMap.keySet();
									for(String key:keys){
										//由于sql本身转换为大写，故参数在注入前也转换为大写
										Object value = valueMap.get(key);
										if (value instanceof Collection) {
											query.setParameterList(key.toUpperCase(), (Collection<?>) value);
										}else{
											query.setParameter(key.toUpperCase(), value);
										}
									}
								}
							   if(page!=null&&!page.isQueryAll()){ 
								// 需要分页,取得总行数设置分页查询
								     String counthql = "select count(*) from("+sql+") count_view";
								     
								     Query countQuery = session.createSQLQuery(counthql.toUpperCase());
							        if(valueMap!=null){
										Set<String> keys=valueMap.keySet();
										for(String key:keys){
											Object v = valueMap.get(key);
											if (v instanceof Collection) {
												countQuery.setParameterList(key.toUpperCase(),(Collection<?>) v);
											}else{
												countQuery.setParameter(key.toUpperCase(), v);
											}
										}
									}
							        int rownum = ( (Number) countQuery.list().get(0)).intValue();
								    page.afterNew(rownum);
								    
								    
						            query.setMaxResults(page.getPageSize());
						            query.setFirstResult(page.getStartRow());
					          }
							   
							    List<Map<String,Object>> list = query.list();
							    return list;
							   
						   }
 
				  });
  }

 
	
    /**
	 * 动态更新保存数据对象  只更新属性值有变化的字段  设置<class dynamic-update="true"用于激活hibernate的这种行为。
	 * @param session
	 * @param object  
	 * @return object 更新后的对象
	 * @author alw
	 
    protected Object dynamicUpdate(final BaseEntity object)	         
    {
    		  
            
    	return getHibernateTemplate().executeWithNativeSession(
	  			  new HibernateCallback<Object>() {
	  					   public Object doInHibernate(Session session)throws HibernateException, SQLException {
	  						   
	  						    BaseEntity newobj;
								try {
									newobj = (BaseEntity)object.clone();
								} catch (CloneNotSupportedException e) {
									// TODO Auto-generated catch block
									throw ExceptionManager.getException(DAOException.class, ErrorCodeConst.DB_COMMON_ERROR, new String[]{e.getMessage()}, e);
								}
							   // 调出原来的持久对象 使得在内存中存在要更新对象的快照，Hibernate 会区分哪些字段进行了变化
							   session.refresh(object,LockMode.NONE);
								
							   //-- 进行版本属性的验证和版本号校验
							   String clsName = object.getClass().getName();
				    	       org.hibernate.metadata.ClassMetadata cmt = session.getSessionFactory().getClassMetadata(clsName);
							   int  versionProperty = cmt.getVersionProperty();
							  
							   if(versionProperty>=0)// 存在version属性，则进行版本号的校验
							   {
								  Number ver1 = (Number)cmt.getVersion(newobj, EntityMode.POJO);
								  Serializable id = cmt.getIdentifier(newobj, EntityMode.POJO);
								  
								  Number ver2 = (Number)cmt.getVersion(object, EntityMode.POJO);
								  if(ver1==null||ver1.intValue()!=ver2.intValue()) //版本号不匹配，抛出状态不一致异常
										throw new org.hibernate.StaleObjectStateException(clsName,id);
								 
							   }
							    
					           //执行对象属性的更新
								
					        	BeanCopier copier = BeanCopier.create(newobj.getClass(), object.getClass(), false);
					        	copier.copy(newobj, object, null);
					        	
					        	session.update(object);
					        	session.flush();  
				                return object;
	  					   }
	  			  });
	  			  	  
       
    }
    
    */
    /**
     * 批量更新或删除
     * @param queryString   更新(删除)语句 HQL 格式   delete from abc as o where o.keyno=?
     * @param value         参数值
     * @author alw
     */
   protected int batchUpdateOrDel(String queryString, Object value){
	   if(value!=null)
		   return batchUpdate(queryString, new Object[] {value});
	   else
		   return batchUpdate(queryString, null);  
	}

    protected int batchUpdate(final String queryString, final Object[] values){
		
		 Integer rownum = (Integer)getHibernateTemplate().executeWithNativeSession(
	  			  new HibernateCallback<Object>() {
	  					   public Object doInHibernate(Session session)
	  					     throws HibernateException, SQLException {
	  						    Query query = session.createQuery(queryString);
		  						if (values != null) {
		  							for (int i = 0; i < values.length; i++) {
		  								query.setParameter(i, values[i]);
		  							}
		  						}
		  						return new Integer(query.executeUpdate());
	  					   }
	  			  });
	  	   return rownum.intValue();
	  	   
				
			
   }

    public static final void processParameterTrim(Map<String,Object> param){
    	if( param != null && param.size() > 0 ){
    		for( Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();it.hasNext(); ){
    			Map.Entry<String, Object> entry = it.next();
    			Object value = entry.getValue();
    			if( value != null && value instanceof String ){
    				entry.setValue(((String)value).trim());
    			}
    		}
    	}
    }
	 
}
