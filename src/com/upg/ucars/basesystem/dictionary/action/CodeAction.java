package com.upg.ucars.basesystem.dictionary.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.basesystem.dictionary.core.code.ICodeMetaService;
import com.upg.ucars.basesystem.dictionary.core.code.ICodeService;
import com.upg.ucars.constant.CodeKey;
import com.upg.ucars.constant.SessionKeyConst;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.dictionary.CodeMeta;
import com.upg.ucars.model.ConditionBean;

/**
 * 
 * CodeAction
 * 
 * @author shentuwy
 * 
 */

public class CodeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7592721282223782106L;

	/** 字典服务 */
	private ICodeService		codeService;

	/** 字典元服务 */
	private ICodeMetaService	codeMetaService;

	/** 字典实体 */
	private Code				code;

	/** 字典元实体 */
	private CodeMeta			cm;

	/** 代码列表 */
	private List<Code>			langType;

	/**
	 * 主页面
	 * 
	 * @return
	 */
	public String main() {
		getLangTypeList();
		return SUCCESS;
	}
	
	/**
	 * 
	 * 获取语言列表
	 *
	 */
	private void getLangTypeList() {
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>(1);
		conditionList.add(new ConditionBean(Code.CODE_KEY, CodeKey.LANGUAGE_TYPE));
		conditionList.add(new ConditionBean(Code.LANG_TYPE, SessionTool.getAttribute(SessionKeyConst.SESSION_LOCAL)
				.toString()));
		langType = codeService.getEntityList(conditionList, null);
	}

	/**
	 * 
	 * 查询
	 * 
	 * @return
	 */
	public String list() {
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
		if (code != null) {
			String codeKey = code.getCodeKey();
			if (StringUtils.isNotBlank(codeKey)) {
				conditionList.add(new ConditionBean(Code.CODE_KEY, codeKey));
			}
			String codeNo = code.getCodeNo();
			if (StringUtils.isNotBlank(codeNo)) {
				conditionList.add(new ConditionBean(Code.CODE_NO, ConditionBean.LIKE, codeNo));
			}
			String codeName = code.getCodeName();
			if (StringUtils.isNotBlank(codeName)) {
				conditionList.add(new ConditionBean(Code.CODE_NAME, ConditionBean.LIKE, codeName));
			}
			String langType = code.getLangType();
			if (StringUtils.isNotBlank(langType)) {
				conditionList.add(new ConditionBean(Code.LANG_TYPE, langType));
			}
		}
		List<Code> list = codeService.getEntityList(conditionList, getPg());
		return setDatagridInputStreamData(list, getPg());
	}

	/**
	 * 
	 * 增加或编辑页面
	 * 
	 * @return
	 */
	public String addOrEditPage() {
		String id = getId();
		if (StringUtils.isNotBlank(id)) {
			code = codeService.getEntityById(id);
			String codeKey = code.getCodeKey();
			if (StringUtils.isNotBlank(codeKey)) {
				cm = codeMetaService.getCodeMetaByKey(codeKey);
			}
		}
		getLangTypeList();
		return SUCCESS;
	}

	/**
	 * 
	 * 增加或更新
	 * 
	 */
	public void addOrEdit() {
		if (code != null) {
			if (code.getId() != null) {
				codeService.update(code);
			} else {
				codeService.save(code);
			}
		}
	}

	/**
	 * 
	 * 批量删除
	 * 
	 */
	public void batchDelete() {
		codeService.deleteByIdList(getIdList());
	}

	// =========================
	public void setCodeService(ICodeService codeService) {
		this.codeService = codeService;
	}

	public void setCodeMetaService(ICodeMetaService codeMetaService) {
		this.codeMetaService = codeMetaService;
	}

	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
	}

	public CodeMeta getCm() {
		return cm;
	}

	public void setCm(CodeMeta cm) {
		this.cm = cm;
	}

	public List<Code> getLangType() {
		return langType;
	}

	public void setLangType(List<Code> langType) {
		this.langType = langType;
	}

}
