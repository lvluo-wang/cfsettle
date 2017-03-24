package com.upg.ucars.basesystem.product.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.basesystem.dictionary.core.code.ICodeMetaService;
import com.upg.ucars.basesystem.dictionary.core.code.ICodeService;
import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.basesystem.product.core.product.IProductAttributeService;
import com.upg.ucars.constant.SessionKeyConst;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.dictionary.CodeMeta;
import com.upg.ucars.mapping.basesystem.product.ProductAttribute;
import com.upg.ucars.model.ConditionBean;

/**
 * 
 * ProductAttributeAction
 * 
 * @author shentuwy
 * 
 */
public class ProductAttributeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long					serialVersionUID	= 3161966327620210012L;

	/** 产品属性 */
	private ProductAttribute					pa;
	/** 字典元 */
	private CodeMeta							cm;
	/** 字典列表 */
	private List<Code>							codeList;
	/** 字典元服务 */
	private transient ICodeMetaService			codeMetaService;
	/** 字典服务 */
	private transient ICodeService				codeService;
	/** 产品属性服务 */
	private transient IProductAttributeService	productAttributeService;

	/**
	 * 
	 * 产品属性主页面
	 * 
	 * @return
	 */
	public String main() {
		return SUCCESS;
	}

	/**
	 * 
	 * 产品属性列表
	 * 
	 * @return
	 */
	public String list() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Page page = getPg();
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>(1);
		if (pa != null) {
			String name = pa.getName();
			if (StringUtils.isNotBlank(name)) {
				conditionList.add(new ConditionBean("name", ConditionBean.LIKE, pa.getName()));
			}
		}
		List<ProductAttribute> attrList = productAttributeService.getEntityList(conditionList, page);
		if (attrList != null && attrList.size() > 0) {
			for (ProductAttribute attr : attrList) {
				Map<String, Object> paMap = new HashMap<String, Object>();
				paMap.put("id", attr.getId());
				paMap.put("name", attr.getName());
				paMap.put("value", attr.getValue());
				String codeMetaKey = attr.getCodeMetaKey();
				paMap.put("codeMetaKey", codeMetaKey);
				String valueDisp = attr.getValue();
				String codeMetaDisp = codeMetaKey;
				if (StringUtils.isNotBlank(codeMetaKey)) {
					valueDisp = DictionaryUtil.getCodeNameByKey(codeMetaKey, attr.getValue());
					cm = codeMetaService.getCodeMetaByKey(codeMetaKey);
					if( cm != null ){
						codeMetaDisp = cm.getName();
					}
				}
				paMap.put("valueDisp", valueDisp);
				paMap.put("codeMetaDisp", codeMetaDisp);
				paMap.put("key", attr.getKey());
				list.add(paMap);
			}
		}
		return setDatagridInputStreamData(list, page);
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
			pa = productAttributeService.getEntityById(Long.valueOf(id));
			String codeMetaKey = pa.getCodeMetaKey();
			if (StringUtils.isNotBlank(codeMetaKey)) {
				cm = codeMetaService.getCodeMetaByKey(codeMetaKey);
				List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
				conditionList.add(new ConditionBean(Code.CODE_KEY, codeMetaKey));
				Locale locale = SessionTool.getAttribute(SessionKeyConst.SESSION_LOCAL) == null ? Locale.CHINA
						: (Locale) SessionTool.getAttribute(SessionKeyConst.SESSION_LOCAL);
				conditionList.add(new ConditionBean(Code.LANG_TYPE, locale.toString()));
				codeList = codeService.getEntityList(conditionList, null);
			}
		}
		return SUCCESS;
	}

	/**
	 * 
	 * 增加或编辑产品属性
	 * 
	 */
	public void addOrEditAttr() {
		if (pa != null) {
			if (pa.getId() == null) {
				productAttributeService.save(pa);
			} else {
				productAttributeService.update(pa);
			}
		}
	}

	/**
	 * 
	 * 删除
	 * 
	 */
	public void deleteAttrs() {
		productAttributeService.deleteByIdList(getIdList());
	}

	public ProductAttribute getPa() {
		return pa;
	}

	public void setPa(ProductAttribute pa) {
		this.pa = pa;
	}

	public void setProductAttributeService(IProductAttributeService productAttributeService) {
		this.productAttributeService = productAttributeService;
	}

	public CodeMeta getCm() {
		return cm;
	}

	public void setCm(CodeMeta cm) {
		this.cm = cm;
	}

	public List<Code> getCodeList() {
		return codeList;
	}

	public void setCodeMetaService(ICodeMetaService codeMetaService) {
		this.codeMetaService = codeMetaService;
	}

	public void setCodeService(ICodeService codeService) {
		this.codeService = codeService;
	}

}
