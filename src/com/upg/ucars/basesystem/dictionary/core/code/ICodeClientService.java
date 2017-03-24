package com.upg.ucars.basesystem.dictionary.core.code;

import java.util.List;

import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.dictionary.DictArea;

/**
 * 为其他模块提供字典信息的查询功能
 * <br>所取信息均以默认的国际化语言
 *
 * @author shentuwy
 */
public interface ICodeClientService {
	/**
	 * 获取指定字典种类所对应的项列表
	 * @param codeKey 字典种类键名
	 * @return
	 */
	public List<Code> getCodesByKey(String codeKey);
	
	
	/**
	 * 获取指定字典种类所对应的项列表
	 * @param codeKey 字典种类键名
	 * @param values 需要查询的字典项值
	 * @return
	 */
	public List<Code> getCodesByKey(String codeKey,String[] values);
	
	
	/**
	 * 获取字典项名称
	 * @param codeKey
	 * @param codeNo
	 * @return
	 */
	public String getCodeNameByKey(String codeKey, String codeNo);
	
	/**
	 * 获取指定表和字段所对应的字典项列表
	 * @param tableName
	 * @param fieldName
	 * @return
	 */
	public List<Code> getCodesByField(String tableName, String fieldName);
	/**
	 * 获取字典项名称
	 * @param tableName
	 * @param fieldName
	 * @param codeNo
	 * @return
	 */
	public String getCodeNameByField(String tableName, String fieldName, String codeNo);
	
	
	/**
	 * 获取省份
	 * @param pid
	 * @return
	 */
	public List<DictArea> getProvinceList();
	/**
	 * 根据上级区域ID获取区域
	 * @param pid
	 * @return
	 */
	public List<DictArea> getAreaListByPid(Long pid);
	/**
	 * 根据上级区域ID获取区域
	 * @param pid
	 * @return
	 */
	public List<DictArea> getAreaListByCode(String code);
	

}

