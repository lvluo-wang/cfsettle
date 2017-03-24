package com.upg.ucars.basesystem.dictionary.core.code;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.dictionary.CodeMeta;
import com.upg.ucars.model.ConditionBean;

/**
 * 提供对Code Cocd_Meta数据访问功能
 *
 * @author shentuwy
 */
public interface ICodeDAO extends IBaseDAO<Code, Long> {
	public void saveCodeMeta(CodeMeta meta)throws DAOException;
	public void updateCodeMeta(CodeMeta meta)throws DAOException;
	/**
	 * 获取字典主信息
	 *
	 * @param key
	 * @return
	 * @throws DAOException
	 */
	public CodeMeta getCodeMetaByKey(String key) throws DAOException;
		
	public List<Code> getCodesByKey(String key) throws DAOException;
	/**
	 * 获取当前语言的字典列表
	 * 
	 * @param key
	 * @return
	 */
	public List<Code> getCurrentLangCodesByKey(String key);
	public List<Code> getCurrentLangCodesByKey(String key,String[] values)throws DAOException;
	
	public List<Code> getSingleLangCodesByKey(String key, String miNo, String langType)throws DAOException;
	
	/**
	 * 根据条件筛选key对应的编号集合
	 * @param key
	 * @param condList 针对Code表的条件,不需要别名
	 * @return
	 * @throws DAOException
	 */
	public List<Code> getCodesByKeyWithCodition(String key, List<ConditionBean> condList) throws DAOException;
	public List<Code> getCurrentLangCodesByKeyWithCodition(String key, List<ConditionBean> condList)throws DAOException;
	public List<Code> getSingleLangCodesByKeyWithCodition(String key, String miNo, String langType, List<ConditionBean> condList)throws DAOException;
	
	/**
	 * 获取项信息
	 *
	 * @param key
	 * @param codeNo
	 * @return
	 * @throws DAOException
	 */
	public List<Code> getCodesByNo(String key, String codeNo) throws DAOException;	
	public Code getCurrentLangCodeByNo(String key, String codeNo) throws DAOException;	
	public Code getSingleLangCodeByNo(String key, String codeNo, String miNo, String langType) throws DAOException;
	
	public void deleteCodeMetaByKey(String key)throws DAOException;	
	public void deleteCodesByKey(String key)throws DAOException;
	public void deleteSingleLangCodesByKey(String key, String miNo, String langType)throws DAOException;
	
	

	
    
}
