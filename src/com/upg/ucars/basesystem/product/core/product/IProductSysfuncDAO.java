package com.upg.ucars.basesystem.product.core.product;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.product.ProductSysfunc;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;

/**
 * 
 * IProductSysfuncDAO
 *
 * @author shentuwy
 *
 */
public interface IProductSysfuncDAO extends IBaseDAO<ProductSysfunc, Long> {

	/**
	 * 
	 * 获取权限列表
	 *
	 * @param productId
	 * @return
	 */
	public List<Sysfunc> getSysfuncByProduct(Long productId);
	
	/**
	 * 是否有产品引用权限
	 * @param funcId 
	 * @return true:有 false:无
	 * @throws DAOException
	 */
	public boolean hasProductRefFunc(Long funcId) throws DAOException;
	
}
