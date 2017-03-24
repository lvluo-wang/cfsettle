package com.upg.ucars.basesystem.dictionary.core.code;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.XBaseService;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.dictionary.CodeMeta;
import com.upg.ucars.model.ConditionBean;

/**
 * 
 * CodeMetaServiceImpl
 * 
 * @author shentuwy
 * 
 */
@SuppressWarnings("unchecked")
public class CodeMetaServiceImpl extends XBaseService<CodeMeta> implements ICodeMetaService {

	/** 定义代码错误 */
	private static final String	ERROR_CODE_META_HAS_EXIST	= "CODE_META_HAS_EXIST";

	/** 字典元信息 */
	private ICodeMetaDAO		codeMetaDAO;

	public IBaseDAO<CodeMeta, Long> getBaseDAO() {
		return codeMetaDAO;
	}

	public List<CodeMeta> getCodeMetaList(CodeMeta cm, Page pg) {
		List<CodeMeta> ret = null;
		List<ConditionBean> list = new ArrayList<ConditionBean>();
		if (cm != null) {
			String key = cm.getKey();
			if (StringUtils.isNotBlank(key)) {
				list.add(new ConditionBean(CodeMeta.KEY, ConditionBean.LIKE, key));
			}
			String name = cm.getName();
			if (StringUtils.isNotBlank(name)) {
				list.add(new ConditionBean(CodeMeta.NAME, ConditionBean.LIKE, name));
			}
		}
		ret = codeMetaDAO.queryEntity(list, pg);
		return ret == null ? SERVICE_EMPTY_LIST : ret;
	}

	public CodeMeta getCodeMetaByKey(String key) {
		CodeMeta ret = null;
		if (StringUtils.isNotBlank(key)) {
			List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
			conditionList.add(new ConditionBean(CodeMeta.KEY, key));
			List<CodeMeta> list = codeMetaDAO.queryEntity(conditionList, null);
			if (list != null && list.size() > 0) {
				ret = list.get(0);
			}
		}
		return ret;
	}

	protected void beforeSaveOrUpdateCheck(CodeMeta cm) throws ServiceException {
		if (cm != null) {
			String key = cm.getKey();
			if (StringUtils.isBlank(key)) {
				ExceptionManager.throwException(ServiceException.class, ERROR_CODE_BLANK, CodeMeta.KEY);
			} else {
				List<ConditionBean> conditionList = new ArrayList<ConditionBean>(1);
				conditionList.add(new ConditionBean(CodeMeta.KEY, key));
				List<CodeMeta> list = getEntityList(conditionList, null);
				if (list != null && list.size() > 0) {
					if (list.size() == 1) {
						CodeMeta tmp = list.get(0);
						if (cm.getId() != null && cm.getId().equals(tmp.getId())) {
						} else {
							ExceptionManager.throwException(ServiceException.class, ERROR_CODE_META_HAS_EXIST, key);
						}
					} else {
						// 数据库已经存在多个相同的key
						ExceptionManager.throwException(ServiceException.class, ERROR_CODE_META_HAS_EXIST, key);
					}
				}
			}
		} else {
			throw new RuntimeException("codemeta is null!");
		}
	}

	// =============================================
	public void setCodeMetaDAO(ICodeMetaDAO codeMetaDAO) {
		this.codeMetaDAO = codeMetaDAO;
	}

}
