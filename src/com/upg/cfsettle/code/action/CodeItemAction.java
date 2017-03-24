package com.upg.cfsettle.code.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.framework.base.BaseAction;
import com.upg.cfsettle.code.core.ICodeItemService;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;

@SuppressWarnings("serial")
public class CodeItemAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -9139979612774399910L;

	/** 多个key的字符串 */
	private String keys;

	/** <code>CODE</code> */
	private FiCodeItem fiCodeItem;

	/** 代码服务 */
	private ICodeItemService codeItemService;

	/**
	 * 
	 * 查询数据字典数据
	 * 
	 * @return 字典的json数据
	 */
	public String findValues() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (keys != null && keys.length() > 0) {
			String key[] = keys.split(COMMA);
			for (String k : key) {
				List<FiCodeItem> codes = null;
				if (StringUtils.isNotEmpty(k)) {
					codes = codeItemService.getCodeItemByKey(k);
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("key", k);
				map.put("map", codes);
				list.add(map);
			}
		}
		return setInputStreamData(list);
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public FiCodeItem getFiCodeItem() {
		return fiCodeItem;
	}

	public void setFiCodeItem(FiCodeItem fiCodeItem) {
		this.fiCodeItem = fiCodeItem;
	}

	public ICodeItemService getCodeItemService() {
		return codeItemService;
	}

	public void setCodeItemService(ICodeItemService codeItemService) {
		this.codeItemService = codeItemService;
	}

	
}
