package com.upg.ucars.basesystem.dictionary.core.code;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.XBaseService;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.dictionary.CodeMeta;
import com.upg.ucars.model.ConditionBean;

/**
 * TODO
 * 
 * @author shentuwy
 */
public class CodeServiceImpl extends XBaseService<Code> implements ICodeService {

	/** 代码已经存在 */
	private static final String	ERROR_CODE_HAS_EXIST	= "CODE_HAS_EXIST";

	private ICodeDAO			codeDAO;									// spring
																			// 注入

	private IFieldCodeMapDAO	fieldCodeMapDAO;							// spring
																			// 注入

	public IBaseDAO<Code, Long> getBaseDAO() {
		return codeDAO;
	}

	public void saveCodeMeta(CodeMeta codeMeta) throws DAOException,
			ServiceException {
		if (this.codeDAO.getCodeMetaByKey(codeMeta.getKey()) != null)
			throw ExceptionManager.getException(DAOException.class,
					ErrorCodeConst.BASESYSTEM_CODE_META_REPEAT);

		this.codeDAO.saveCodeMeta(codeMeta);
	}

	public void updateCodeMeta(CodeMeta codeMeta) throws DAOException,
			ServiceException {
		this.codeDAO.updateCodeMeta(codeMeta);

	}

	public void deleteCodeMetaByKey(String key) throws DAOException,
			ServiceException {
		this.codeDAO.deleteCodesByKey(key);
		this.fieldCodeMapDAO.deleteFieldCodeMapsByKey(key);
		this.codeDAO.deleteCodeMetaByKey(key);

	}

	public void saveCode(Code code) throws DAOException, ServiceException {
		List<Code> list = this.codeDAO.getSingleLangCodesByKey(
				code.getCodeKey(), code.getMiNo(), code.getLangType());

		if (!list.isEmpty()) {// 判断是否已存在
			if (code.getMiNo() == null
					|| code.getMiNo().equals(list.get(0).getMiNo()))
				throw ExceptionManager.getException(DAOException.class,
						ErrorCodeConst.BASESYSTEM_CODE_META_REPEAT);
		}

		this.codeDAO.save(code);

	}

	public void updateCode(Code code) throws DAOException, ServiceException {
		this.codeDAO.update(code);

	}

	public void deleteCodeById(Long codeId) throws DAOException,
			ServiceException {
		this.codeDAO.delete(codeId);

	}

	public void deleteCodeByNo(String key, String codeNo) throws DAOException,
			ServiceException {
		List<Code> codeList = this.codeDAO.getCodesByNo(key, codeNo);
		for (Code code : codeList) {
			this.codeDAO.delete(code);
		}

	}

	public void saveCodeInfo(CodeMeta codeMeta, List<Code> codeList)
			throws DAOException, ServiceException {
		this.saveCodeMeta(codeMeta);
		for (Code code : codeList) {
			this.saveCode(code);
		}

	}

	public void updateCodeInfo(CodeMeta codeMeta, List<Code> codeList)
			throws DAOException, ServiceException {
		CodeMeta meta = this.codeDAO.getCodeMetaByKey(codeMeta.getKey());
		meta.setName(codeMeta.getName());

	}

	public void deleteCodeInfoByKey(String key) throws DAOException,
			ServiceException {

		this.codeDAO.deleteCodesByKey(key);
		this.fieldCodeMapDAO.deleteFieldCodeMapsByKey(key);
		this.codeDAO.deleteCodeMetaByKey(key);

	}

	protected void beforeSaveOrUpdateCheck(Code code) throws ServiceException {
		if (code != null) {
			String codeNo = code.getCodeNo();
			if (StringUtils.isBlank(codeNo)) {
				ExceptionManager.throwException(ServiceException.class,
						ERROR_CODE_BLANK, Code.CODE_NO);
			}
			String codeKey = code.getCodeKey();
			if (StringUtils.isBlank(codeKey)) {
				ExceptionManager.throwException(ServiceException.class,
						ERROR_CODE_BLANK, Code.CODE_KEY);
			}
			List<ConditionBean> conditionList = new ArrayList<ConditionBean>(2);
			conditionList.add(new ConditionBean(Code.CODE_NO, codeNo));
			conditionList.add(new ConditionBean(Code.CODE_KEY, codeKey));
			List<Code> list = getEntityList(conditionList, null);
			if (list != null && list.size() > 0) {
				if (list.size() == 1) {
					Code tmp = list.get(0);
					if (code.getId() != null
							&& code.getId().equals(tmp.getId())) {
					} else {
						ExceptionManager.throwException(ServiceException.class,
								ERROR_CODE_HAS_EXIST, new String[] { codeNo,
										codeKey });
					}
				} else {
					ExceptionManager.throwException(ServiceException.class,
							ERROR_CODE_HAS_EXIST, new String[] { codeNo,
									codeKey });
				}
			}
		} else {
			throw new RuntimeException("code is null!");
		}
	}

	public void setCodeDAO(ICodeDAO codeDAO) {
		this.codeDAO = codeDAO;
	}

	public void setFieldCodeMapDAO(IFieldCodeMapDAO fieldCodeMapDAO) {
		this.fieldCodeMapDAO = fieldCodeMapDAO;
	}

}
