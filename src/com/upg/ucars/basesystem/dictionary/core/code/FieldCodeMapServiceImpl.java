package com.upg.ucars.basesystem.dictionary.core.code;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.XBaseService;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.dictionary.FieldCodeMap;
import com.upg.ucars.model.ConditionBean;

/**
 * 
 * FieldCodeMapService
 * 
 * @author shentuwy
 * 
 */
public class FieldCodeMapServiceImpl extends XBaseService<FieldCodeMap> implements IFieldCodeMapService {

	/** 表字段代码映射已存在 */
	private static final String	ERROR_FIELD_CODE_MAP_HAS_EXIST	= "FIELD_CODE_MAP_HAS_EXIST";

	/** 字段字典映射 */
	private IFieldCodeMapDAO	fieldCodeMapDAO;

	public IBaseDAO<FieldCodeMap, Long> getBaseDAO() {
		return fieldCodeMapDAO;
	}

	protected void beforeSaveOrUpdateCheck(FieldCodeMap fcm) throws ServiceException {
		if (fcm != null) {
			String tableName = fcm.getTableName();
			if (StringUtils.isBlank(tableName)) {
				ExceptionManager.throwException(ServiceException.class, ERROR_CODE_BLANK, FieldCodeMap.TABLE_NAME);
			}
			String fieldName = fcm.getFieldName();
			if (StringUtils.isBlank(fieldName)) {
				ExceptionManager.throwException(ServiceException.class, ERROR_CODE_BLANK, FieldCodeMap.FIELD_NAME);
			}
			List<ConditionBean> conditionList = new ArrayList<ConditionBean>(2);
			conditionList.add(new ConditionBean(FieldCodeMap.TABLE_NAME, tableName));
			conditionList.add(new ConditionBean(FieldCodeMap.FIELD_NAME, fieldName));
			List<FieldCodeMap> list = getEntityList(conditionList, null);
			if (list != null && list.size() > 0) {
				if (list.size() == 1) {
					FieldCodeMap tmp = list.get(0);
					if (fcm.getId() != null && fcm.getId().equals(tmp.getId())) {
					} else {
						ExceptionManager.throwException(ServiceException.class, ERROR_FIELD_CODE_MAP_HAS_EXIST,
								new String[] { tableName, fieldName });
					}
				} else {
					ExceptionManager.throwException(ServiceException.class, ERROR_FIELD_CODE_MAP_HAS_EXIST,
							new String[] { tableName, fieldName });
				}
			}
		} else {
			throw new RuntimeException("fieldCodeMap is null!");
		}
	}

	// ======================================

	public void setFieldCodeMapDAO(IFieldCodeMapDAO fieldCodeMapDAO) {
		this.fieldCodeMapDAO = fieldCodeMapDAO;
	}

}
