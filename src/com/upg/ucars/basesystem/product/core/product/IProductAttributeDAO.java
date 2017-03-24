package com.upg.ucars.basesystem.product.core.product;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.mapping.basesystem.product.ProductAttribute;

/**
 * 
 * IProductAttributeDAO
 * 
 * @author shentuwy
 * 
 */
public interface IProductAttributeDAO extends IBaseDAO<ProductAttribute, Long> {
	
	public ProductAttribute getProductAttributeByCode(String attributeCode);

}
