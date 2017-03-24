package com.upg.ucars.basesystem.dictionary.core.code;

import java.util.List;

import com.upg.ucars.framework.base.IXBaseService;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.dictionary.CodeMeta;

/**
 * 字典实体服务定义 <br>
 * 对实体CodeMeta, Code, FieldCodeMap提供结合服务
 * 
 * @author shentuwy
 */
public interface ICodeService extends IXBaseService<Code> {

	/**
	 * 保存字典主信息
	 * 
	 * @param codeMeta
	 * @throws DAOException
	 * @throws ServiceException
	 *             如果已存在key值对应的主信息，则抛出异常
	 */
	public void saveCodeMeta(CodeMeta codeMeta) throws DAOException,
			ServiceException;

	public void updateCodeMeta(CodeMeta codeMeta) throws DAOException,
			ServiceException;

	/**
	 * 删除主信息及依赖数据信息
	 * 
	 * @param key
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public void deleteCodeMetaByKey(String key) throws DAOException,
			ServiceException;

	/********************************************************************************
	 * ------------------------------------------------------------------------
	 * -----
	 ********************************************************************************/
	/**
	 * 保存字典明细信息
	 * 
	 * @param code
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public void saveCode(Code code) throws DAOException, ServiceException;

	public void updateCode(Code code) throws DAOException, ServiceException;

	/**
	 * 删除一条记录
	 * 
	 * @param codeId
	 *            Code类Id值
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public void deleteCodeById(Long codeId) throws DAOException,
			ServiceException;

	/**
	 * 删除对应的编号记录 <br>
	 * --由于支持国际化，所以会有多条记录
	 * 
	 * @param key
	 * @param codeNo
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public void deleteCodeByNo(String key, String codeNo) throws DAOException,
			ServiceException;

	/********************************************************************************
	 * ------------------------------------------------------------------------
	 * -----
	 ********************************************************************************/

	/**
	 * 保存一条字典信息 <br>
	 * 如果存在主信息，则只增加明细信息；如果存在部分明细信息，则增加其余明细信息
	 * 
	 * @param codeMeta
	 * @param codeList
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public void saveCodeInfo(CodeMeta codeMeta, List<Code> codeList)
			throws DAOException, ServiceException;

	public void updateCodeInfo(CodeMeta codeMeta, List<Code> codeList)
			throws DAOException, ServiceException;

	/**
	 * 删除主信息及所有明细信息
	 * 
	 * @param key
	 *            CodeMeta.key
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public void deleteCodeInfoByKey(String key) throws DAOException,
			ServiceException;

	/********************************************************************************
	 * ------------------------------------------------------------------------
	 * -----
	 ********************************************************************************/

}
