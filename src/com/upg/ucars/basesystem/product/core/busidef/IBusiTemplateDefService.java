package com.upg.ucars.basesystem.product.core.busidef;

import java.util.List;

import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.product.BusiTemplateDef;

public interface IBusiTemplateDefService {

	/**
	 * 新增交易定义信息
	 * @param code
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public Long createBusiTemplateDef(BusiTemplateDef busiDef) throws ServiceException,DAOException;
	/**
	 * 修改交易定义信息
	 * @param code
	 * @throws ServiceException
	 * @throws DAOException
	 */
	
	public void modifyBusiTemplateDef(BusiTemplateDef busiDef) throws ServiceException,DAOException;
	
	
	/**
	 * 查询一个可用的交易信息
	 * @param busiNo
	 * @param miNo
	 * @return
	 * @throws DAOException
	 * @throws DAOException
	 */
	public BusiTemplateDef findUsingBusiDef(String busiNo,String miNo ) throws ServiceException,DAOException;
	
	
	/**
	 * 根据接入点和产品查询交易定义信息
	 * @param prodNo
	 * @param miNo
	 * @param page
	 * @return
	 * @throws DAOException
	 */
	public List<BusiTemplateDef> queryBusiDefsByProdAndMino(String prodNo,String miNo, Page page) throws DAOException;
	
}
