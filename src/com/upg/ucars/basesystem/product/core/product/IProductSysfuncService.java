package com.upg.ucars.basesystem.product.core.product;

import java.util.List;

import com.upg.ucars.framework.base.IXBaseService;
import com.upg.ucars.mapping.basesystem.product.ProductSysfunc;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;

/**
 * 
 * IProductSysfuncService
 *
 * @author shentuwy
 *
 */
public interface IProductSysfuncService extends IXBaseService<ProductSysfunc> {
	
	/**
	 * 
	 * 获取权限列表
	 *
	 * @param productId
	 * @return
	 */
	public List<Sysfunc> getSysfuncByProduct(Long productId);
	
	/**
	 * 
	 * 产品分配权限
	 *
	 * @param productId
	 * @param funcIdList
	 */
	public void assignSysfuncForProduct(Long productId,List<Long> funcIdList);
	

}
