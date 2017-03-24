
package com.upg.ucars.basesystem.product.core.busidef;
import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.product.BusiTemplateDef;

/**
 * 交易模板定义DAO
 * @author alw
 *
 */
public interface IBusiTemplateDefDAO extends IBaseDAO<BusiTemplateDef, Long>{
	
	/**
	 * 根据交易编码查询模板定义
	 * @param busiNo
	 * @return
	 */
	public BusiTemplateDef findByBusiNo(String busiNo) throws DAOException;
	

	/**
	 * 根据交易编码和接入点查询模板定义
	 * @param busiNo
	 * @param miNo
	 * @return
	 * @throws DAOException
	 */
	public BusiTemplateDef findByBusinoAndMino(String busiNo,String miNo) throws DAOException;
	
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
