package com.upg.ucars.basesystem.dictionary.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.basesystem.dictionary.core.code.ICodeMetaService;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.mapping.basesystem.dictionary.CodeMeta;
import com.upg.ucars.model.ConditionBean;

/**
 * 
 * CodeMetaAction
 * 
 * @author shentuwy
 * 
 */
public class CodeMetaAction extends BaseAction {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -4890449500690231867L;

	/** 字典元服务 */
	private ICodeMetaService	codeMetaService;

	/** 字典元 */
	private CodeMeta			cm;

	/**
	 * 
	 * 主页面
	 * 
	 * @return
	 */
	public String main() {
		return SUCCESS;
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
			cm = codeMetaService.getEntityById(Long.valueOf(id));
		}
		return SUCCESS;
	}

	/**
	 * 
	 * 增加或编辑
	 * 
	 * @return
	 */
	public void addOrEdit() {
		if (cm != null) {
			if (cm.getId() == null) { // 增加
				codeMetaService.save(cm);
			} else {
				codeMetaService.update(cm);
			}
		}
	}

	/**
	 * 
	 * 批量删除
	 * 
	 */
	public void batchDelete() {
		codeMetaService.deleteByIdList(getIdList());
	}

	/**
	 * 
	 * 主页面数据列表
	 * 
	 * @return
	 */
	public String list() {
		// List<CodeMeta> list = codeMetaService.getCodeMetaList(cm, getPg());
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
		if (cm != null) {
			String key = cm.getKey();
			if (StringUtils.isNotBlank(key)) {
				conditionList.add(new ConditionBean(CodeMeta.KEY, ConditionBean.LIKE, key));
			}
			String name = cm.getName();
			if (StringUtils.isNotBlank(name)) {
				conditionList.add(new ConditionBean(CodeMeta.NAME, ConditionBean.LIKE, name));
			}

		}
		List<CodeMeta> list = codeMetaService.getEntityList(conditionList, getPg());
		return setDatagridInputStreamData(list, getPg());
	}

	// ======================================
	public void setCodeMetaService(ICodeMetaService codeMetaService) {
		this.codeMetaService = codeMetaService;
	}

	public CodeMeta getCm() {
		return cm;
	}

	public void setCm(CodeMeta cm) {
		this.cm = cm;
	}

}
