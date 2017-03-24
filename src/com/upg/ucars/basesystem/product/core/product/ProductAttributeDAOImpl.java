package com.upg.ucars.basesystem.product.core.product;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.mapping.basesystem.product.ProductAttribute;

/**
 * 
 * ProductAttributeDAOImpl
 * 
 * @author shentuwy
 * 
 */

public class ProductAttributeDAOImpl extends BaseDAO<ProductAttribute, Long> implements IProductAttributeDAO {

	public Class<ProductAttribute> getEntityClass() {
		return ProductAttribute.class;
	}

	@Override
	public ProductAttribute getProductAttributeByCode(String attributeCode) {
		ProductAttribute result = null;
		if (StringUtils.isNotBlank(attributeCode)) {
			String hql = "from ProductAttribute where key=?";
			List<ProductAttribute> list = find(hql, attributeCode);
			if (list != null && list.size() > 0) {
				result = list.get(0);
			}
		}
		return result;
	}

}
