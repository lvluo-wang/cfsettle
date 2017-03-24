package com.upg.ucars.framework.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;

/**
 * 
 * XBaseService
 * 
 * @author shentuwy
 * 
 * 
 */

public abstract class XBaseService<T> extends BaseService implements IXBaseService<T> {

	/**
	 * 
	 * 检测 {@link #getBaseDAO()} 返回值是不是空
	 * 
	 */
	private void checkBaseDAONotNull() {
		if (getBaseDAO() == null) { throw new RuntimeException(
				"the returnValue of getBaseDAO'method is null,you need to override it!"); }
	}

	public void save(T entity) {
		if (entity != null) {
			checkBaseDAONotNull();
			beforeSaveOrUpdateCheck(entity);
			IBaseDAO<T, Long> dao = getBaseDAO();
			dao.save(entity);
		}
	}

	/**
	 * 
	 * 保存或更新前业务规则验证
	 * 
	 * @param entity
	 * @throws ServiceException
	 */
	protected void beforeSaveOrUpdateCheck(T entity) throws ServiceException {
	}

	public void update(T entity) {
		if (entity != null) {
			checkBaseDAONotNull();
			beforeSaveOrUpdateCheck(entity);
			IBaseDAO<T, Long> dao = getBaseDAO();
			dao.merge(entity);
		}
	}

	public void deleteById(Long id) {
		if (id != null) {
			checkBaseDAONotNull();
			IBaseDAO<T, Long> dao = getBaseDAO();
			dao.delete(id);
		}
	}

	public void deleteByIdList(List<Long> idList) {
		if (idList != null && idList.size() > 0) {
			checkBaseDAONotNull();
			IBaseDAO<T, Long> dao = getBaseDAO();
			for (Long id : idList) {
				dao.delete(id);
			}
		}
	}

	public void delete(T entity) {
		if (entity != null) {
			checkBaseDAONotNull();
			IBaseDAO<T, Long> dao = getBaseDAO();
			dao.delete(entity);
		}
	}

	public void deleteAll(List<T> entityList) {
		if (entityList != null && entityList.size() > 0) {
			checkBaseDAONotNull();
			IBaseDAO<T, Long> dao = getBaseDAO();
			dao.delAll(entityList);
		}
	}

	public void saveAll(List<T> entityList) {
		saveOrUpdateAll(entityList);
	}

	public void updateAll(List<T> entityList) {
		saveOrUpdateAll(entityList);
	}

	public void saveOrUpdateAll(List<T> entityList) {
		if (entityList != null && entityList.size() > 0) {
			checkBaseDAONotNull();
			IBaseDAO<T, Long> dao = getBaseDAO();
			dao.saveOrUpdateAll(entityList);
		}
	}

	public T getEntityById(Long id) {
		T ret = null;
		if (id != null) {
			checkBaseDAONotNull();
			IBaseDAO<T, Long> dao = getBaseDAO();
			ret = dao.get(id);
		}
		return ret;
	}

	public T getEntityById(String id) {
		T ret = null;
		if (StringUtils.isNotBlank(id) && StringUtils.isNumeric(id)) {
			ret = getEntityById(Long.valueOf(id));
		}
		return ret;
	}

	public List<T> getEntityListByIdList(List<Long> idList, String fieldName) {
		List<T> ret = null;
		if (idList != null && idList.size() > 0) {
			checkBaseDAONotNull();
			IBaseDAO<T, Long> dao = getBaseDAO();
			List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
			conditionList.add(new ConditionBean(StringUtils.isNotBlank(fieldName) ? fieldName : "id", ConditionBean.IN,
					idList));
			ret = dao.queryEntity(conditionList, null);
		}
		return getEmptyListIFNull(ret);
	}

	public List<T> getEntityList(List<ConditionBean> conditionList, Page page) {
		List<T> ret = null;
		checkBaseDAONotNull();
		IBaseDAO<T, Long> dao = getBaseDAO();
		ret = dao.queryEntity(conditionList, page);
		return getEmptyListIFNull(ret);
	}

	public List<T> getEntityList(List<ConditionBean> conditionList, List<OrderBean> orderList, Page page) {
		List<T> ret = null;
		checkBaseDAONotNull();
		IBaseDAO<T, Long> dao = getBaseDAO();
		ret = dao.queryEntity(conditionList, orderList, page);
		return getEmptyListIFNull(ret);
	}

	/**
	 * 
	 * 如果list为空，返回空的List，否则返回本身
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<T> getEmptyListIFNull(List<T> list) {
		return list == null ? SERVICE_EMPTY_LIST : list;
	}

}
