package com.upg.ucars.basesystem.dictionary.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.basesystem.dictionary.core.code.ICodeMetaService;
import com.upg.ucars.basesystem.dictionary.core.code.IFieldCodeMapService;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.mapping.basesystem.dictionary.CodeMeta;
import com.upg.ucars.mapping.basesystem.dictionary.FieldCodeMap;
import com.upg.ucars.model.ConditionBean;

/**
 * 
 * FieldCodeMapAction
 * 
 * @author shentuwy
 * 
 */
public class FieldCodeMapAction extends BaseAction {

	/**
	 * 
	 */
	private static final long		serialVersionUID	= -4208600386800017701L;

	/** 字段代码映射服务 */
	private IFieldCodeMapService	fieldCodeMapService;

	/** 字典元服务 */
	private ICodeMetaService		codeMetaService;

	/** 字段代码映射实体 */
	private FieldCodeMap			fcm;

	/** 字典元 */
	private CodeMeta				cm;

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
	 * 查询
	 * 
	 * @return
	 */
	public String list() {
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
		if (fcm != null) {
			String tableName = fcm.getTableName();
			if (StringUtils.isNotBlank(tableName)) {
				conditionList.add(new ConditionBean(FieldCodeMap.TABLE_NAME, ConditionBean.LIKE, tableName));
			}
			String fieldName = fcm.getFieldName();
			if (StringUtils.isNotBlank(fieldName)) {
				conditionList.add(new ConditionBean(FieldCodeMap.FIELD_NAME, ConditionBean.LIKE, fieldName));
			}
			String codeKey = fcm.getCodeKey();
			if (StringUtils.isNotBlank(codeKey)) {
				conditionList.add(new ConditionBean(FieldCodeMap.CODE_KEY, ConditionBean.LIKE, codeKey));
			}
		}
		List<FieldCodeMap> list = fieldCodeMapService.getEntityList(conditionList, getPg());
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
			fcm = fieldCodeMapService.getEntityById(Long.valueOf(id));
			String codeKey = fcm.getCodeKey();
			if (StringUtils.isNotBlank(codeKey)) {
				cm = codeMetaService.getCodeMetaByKey(codeKey);
			}
		}
		return SUCCESS;
	}

	/**
	 * 
	 * 增加或编辑
	 * 
	 */
	public void addOrEdit() {
		if (fcm != null) {
			if (fcm.getId() != null) {
				fieldCodeMapService.update(fcm);
			} else {
				fieldCodeMapService.save(fcm);
			}
		}
	}

	/**
	 * 
	 * 批量删除
	 * 
	 */
	public void batchDelete() {
		fieldCodeMapService.deleteByIdList(getIdList());
	}

	// ===========================================

	public void setFieldCodeMapService(IFieldCodeMapService fieldCodeMapService) {
		this.fieldCodeMapService = fieldCodeMapService;
	}

	public void setCodeMetaService(ICodeMetaService codeMetaService) {
		this.codeMetaService = codeMetaService;
	}

	public FieldCodeMap getFcm() {
		return fcm;
	}

	public void setFcm(FieldCodeMap fcm) {
		this.fcm = fcm;
	}

	public CodeMeta getCm() {
		return cm;
	}

	public void setCm(CodeMeta cm) {
		this.cm = cm;
	}

}
