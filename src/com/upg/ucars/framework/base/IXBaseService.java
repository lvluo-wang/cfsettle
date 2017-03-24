package com.upg.ucars.framework.base;

import java.util.List;

import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;

/**
 * 
 * 提供服务类的基本功能
 * 
 * @author shentuwy
 * 
 */
public interface IXBaseService<T> extends IBaseService {

	/** 不能为空的错误代码 */
	public static final String	ERROR_CODE_BLANK	= "BLANK";

	/**
	 * 
	 * 此实体服务对应的实体DAO，需要子类实现，否则使用默认提供的方法会抛异常。
	 * 
	 * @return 实体对应的DAO
	 */
	public IBaseDAO<T, Long> getBaseDAO();

	/**
	 * 
	 * 保存单条记录
	 * 
	 * @param entity
	 *            实体信息
	 */
	public void save(T entity);

	/**
	 * 
	 * 更新单条记录
	 * 
	 * @param entity
	 *            实体信息
	 */
	public void update(T entity);

	/**
	 * 
	 * 删除记录
	 * 
	 * @param id
	 *            实体ID
	 */
	public void deleteById(Long id);

	/**
	 * 
	 * 删除多条记录
	 * 
	 * @param idList
	 *            实体ID列表
	 */
	public void deleteByIdList(List<Long> idList);

	/**
	 * 
	 * 删除记录
	 * 
	 * @param entity
	 *            实体信息
	 */
	public void delete(T entity);

	/**
	 * 
	 * 删除多条记录
	 * 
	 * @param entityList
	 *            实体列表
	 */
	public void deleteAll(List<T> entityList);

	/**
	 * 
	 * 批量保存
	 * 
	 * @param entityList
	 *            实体列表
	 */
	public void saveAll(List<T> entityList);

	/**
	 * 
	 * 批量更新
	 * 
	 * @param entityList
	 *            实体列表
	 */
	public void updateAll(List<T> entityList);

	/**
	 * 
	 * 批量保存或更新
	 * 
	 * @param entityList
	 *            实体列表
	 */
	public void saveOrUpdateAll(List<T> entityList);

	/**
	 * 
	 * 获取实体
	 * 
	 * @param id
	 *            实体ID
	 * @return 返回实体信息
	 */
	public T getEntityById(Long id);

	/**
	 * 
	 * 获取实体
	 * 
	 * @param id
	 *            实体ID
	 * @return 返回实体信息
	 */
	public T getEntityById(String id);

	/**
	 * 
	 * 获取列表
	 * 
	 * @param idList
	 *            　实体ＩＤ列表
	 * @param fieldName
	 *            　实体ＩＤ字段名
	 * @return　实体列表
	 */
	public List<T> getEntityListByIdList(List<Long> idList, String fieldName);

	/**
	 * 
	 * 获取列表
	 * 
	 * @param conditionList
	 *            　查询条件列表
	 * @param page
	 *            　分页信息
	 * @return　实体列表
	 */
	public List<T> getEntityList(List<ConditionBean> conditionList, Page page);

	/**
	 * 
	 * 获取列表
	 * 
	 * @param conditionList
	 *            　条件列表
	 * @param orderList
	 *            　排序列表
	 * @param page
	 *            　分页信息
	 * @return　实体列表
	 */
	public List<T> getEntityList(List<ConditionBean> conditionList, List<OrderBean> orderList, Page page);
}
