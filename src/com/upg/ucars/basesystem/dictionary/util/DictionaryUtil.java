package com.upg.ucars.basesystem.dictionary.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.basesystem.dictionary.core.code.ICodeClientService;
import com.upg.ucars.basesystem.dictionary.core.code.IDictTradeService;
import com.upg.ucars.constant.BeanNameConstants;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.dictionary.DictArea;
import com.upg.ucars.mapping.basesystem.dictionary.DictTrade;
import com.upg.ucars.util.SourceTemplate;

/**
 * 字典工具 
 *
 * @author shentuwy
 */
public class DictionaryUtil {
	
	/**
	 * 获取指定字典种类所对应的项列表
	 * @param codeKey 字典种类键名
	 * @return
	 */
	public static List<Code> getCodesByKey(String codeKey){
		return SourceTemplate.getBean(ICodeClientService.class,BeanNameConstants.CODE_CLIENT_SERVICE).getCodesByKey(codeKey);
	}
	
	/**
	 * 获取指定字典种类及部分项编号所对应的项列表
	 *
	 * @param codeKey 键名
	 * @param codeNos 项编号
	 * @return
	 */
	public static List<Code> getCodesByKey(String codeKey,String[] codeNos){
		return SourceTemplate.getBean(ICodeClientService.class,BeanNameConstants.CODE_CLIENT_SERVICE).getCodesByKey(codeKey, codeNos);
	}
	
	/**
	 * 获取字典项名称
	 * @param codeKey
	 * @param codeNo
	 * @return
	 */
	public static String getCodeNameByKey(String codeKey, String codeNo){
		return SourceTemplate.getBean(ICodeClientService.class,BeanNameConstants.CODE_CLIENT_SERVICE).getCodeNameByKey(codeKey, codeNo);
	}
	
	/**
	 * 获取指定表和字段所对应的字典项列表
	 * @param tableName
	 * @param fieldName
	 * @return
	 */
	public static List<Code> getCodesByField(String tableName, String fieldName){
		tableName = tableName.toUpperCase();
		fieldName = fieldName.toUpperCase();
		return SourceTemplate.getBean(ICodeClientService.class,BeanNameConstants.CODE_CLIENT_SERVICE).getCodesByField(tableName, fieldName);
	}
	/**
	 * 获取字典项名称
	 * @param tableName
	 * @param fieldName
	 * @param codeNo
	 * @return
	 */
	public static String getCodeNameByField(String tableName, String fieldName, String codeNo){
		tableName = tableName.toUpperCase();
		fieldName = fieldName.toUpperCase();
		return SourceTemplate.getBean(ICodeClientService.class,BeanNameConstants.CODE_CLIENT_SERVICE).getCodeNameByField(tableName, fieldName, codeNo);
	}
	
	
	/**
	 * 将字典中的值翻译成名称
	 * @param codeKey 字典键
	 * @param keyNo 字典编码，可用逗号隔开
	 * @return
	 */
	public static String getCodeNamesByKey(String codeKey,String[] codeNos){
		StringBuffer names = new StringBuffer();
		String namesStr = null;
		List<Code> keyNoList = null;
		if(codeNos == null){
			return "";
		}
		keyNoList = DictionaryUtil.getCodesByKey(codeKey,codeNos);
		for(Code code : keyNoList){
			names.append(code.getCodeName() + ",");
		}
		if(names.toString().endsWith(",")){
			namesStr = names.toString().substring(0,names.length()-1);
		}
		return namesStr;
	}

	
	/**
	 * 获取指定字典种类所对应的项列表
	 * @param codeKey 字典种类键名
	 * @return
	 */
	public static List<DictArea> getProvinces(){
		return SourceTemplate.getBean(ICodeClientService.class,BeanNameConstants.CODE_CLIENT_SERVICE).getProvinceList();
	}
	
	/**
	 * 根据上级区域ID获取区域
	 * @param pid
	 * @return
	 */
	public static List<DictArea> getAreaListByPid(Long pid){
		return SourceTemplate.getBean(ICodeClientService.class,BeanNameConstants.CODE_CLIENT_SERVICE).getAreaListByPid(pid);
	}
	/**
	 * 根据上级区域ID获取区域
	 * @param pid
	 * @return
	 */
	public static List<DictArea> getAreaListByCode(String code){
		return SourceTemplate.getBean(ICodeClientService.class,BeanNameConstants.CODE_CLIENT_SERVICE).getAreaListByCode(code);
	}
	
	/**
	 * 根据区域代码获取名称
	 * 
	 * @param code
	 * @return
	 */
	public static String getNameCnByCode(String code){
		String result = "";
		DictArea dictArea = getDictAreaByCode(code);
		if (dictArea != null) {
			result = dictArea.getNameCn();
		}
		return result;
	}
	
	private static final DictArea getDictAreaByCode(String code) {
		DictArea result = null;
		if (code != null && code.trim().length() > 0) {
			List<DictArea> areaList = SourceTemplate.getBean(ICodeClientService.class,BeanNameConstants.CODE_CLIENT_SERVICE).getAreaListByCode(code);
			if (areaList != null && areaList.size() >= 1) {
				result = areaList.get(0);
			}
		}
		return result;
	}
	
	/**
	 * 根据区域代码获取ID
	 * 
	 * @param code
	 * @return
	 */
	public static final Long getDictAreaIdByCode(String code){
		Long result = null;
		DictArea dictArea = getDictAreaByCode(code);
		if (dictArea != null) {
			result = dictArea.getId();
		}
		return result;
	}
	
	private static final DictTrade getDictTradeByCode(String code){
		DictTrade result = null;
		if (StringUtils.isNotBlank(code)) {
			result = SourceTemplate.getBean(IDictTradeService.class,BeanNameConstants.DICT_TRADE_SERVICE).findTradeByCode(code);
		}
		return result;
	}
	
	/**
	 * 根据行业代码获取行业ID
	 * 
	 * @param code
	 * @return
	 */
	public static final Long getDictTradeIdByCode(String code){
		Long result = null;
		DictTrade dictTrade = getDictTradeByCode(code);
		if (dictTrade != null) {
			result = dictTrade.getId();
		}
		return result;
	}
}
