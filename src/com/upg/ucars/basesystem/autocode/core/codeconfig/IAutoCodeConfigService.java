package com.upg.ucars.basesystem.autocode.core.codeconfig;

import java.util.List;

import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.product.AutoCodeConfig;

public interface IAutoCodeConfigService {

	/**
	 * 新增编码配置信息
	 * @param code
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public Long createAutoGenCode(AutoCodeConfig code) throws ServiceException,DAOException;
	/**
	 * 修改编码配置信息
	 * @param code
	 * @throws ServiceException
	 * @throws DAOException
	 */
	
	public void modifyAutoGenCode(AutoCodeConfig code) throws ServiceException,DAOException;
	
	/**
	 * 根据接入点分页查询编码配置信息
	 * @param miNo
	 * @param page
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public List<AutoCodeConfig> queryCodesByMino(String miNo,Page page) throws ServiceException,DAOException;
	
	/**
	 * 查询一个使用的编码配置信息
	 * @param codeKey
	 * @param miNo
	 * @return
	 * @throws DAOException
	 * @throws DAOException
	 */
	public AutoCodeConfig findUsingCodeConfig(String codeKey,String miNo ) throws ServiceException,DAOException;
	
	/**
	 * 自动生成单个编码
	 * @param codeKey
	 * @param miNo
	 * @param paramValues
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public String autoGenCode(String codeKey,String miNo,String[] paramValues) throws ServiceException,DAOException;
	/**
	 * 批量自动生成指定个数的编码
	 * @param codeKey
	 * @param miNo
	 * @param paramValues
	 * @param length
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public String[] autoGenCodes(String codeKey,String miNo,String[] paramValues,int length) throws ServiceException,DAOException;
	
	
}
