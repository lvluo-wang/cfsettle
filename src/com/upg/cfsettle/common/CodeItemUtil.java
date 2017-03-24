package com.upg.cfsettle.common;

import java.util.List;

import com.upg.ucars.util.SourceTemplate;
import com.upg.cfsettle.code.core.ICodeItemService;
import com.upg.cfsettle.mapping.ficode.FiCodeItem;

/**
 * 字典工具 
 *
 */
public class CodeItemUtil {
	
	/**
	 * 获取字典项名称
	 * @param codeKey
	 * @param codeNo
	 * @return
	 */
	public static String getCodeNameByKey(String codeKey, String codeNo){
		return SourceTemplate.getBean(ICodeItemService.class,"codeItemService").getCodeItemNameByKey(codeKey, codeNo);
	}
	
	public static List<FiCodeItem> getCodeItemsByKey(String codeKey){
		return SourceTemplate.getBean(ICodeItemService.class,"codeItemService").getCodeItemByKey(codeKey);
	}
}