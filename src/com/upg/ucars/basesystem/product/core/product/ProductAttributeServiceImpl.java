package com.upg.ucars.basesystem.product.core.product;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.XBaseService;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.product.ProductAttribute;
import com.upg.ucars.model.ConditionBean;

/**
 * 
 * ProductAttributeServiceImpl
 * 
 * @author shentuwy
 * 
 */

public class ProductAttributeServiceImpl extends XBaseService<ProductAttribute> implements IProductAttributeService {

	private IProductAttributeDAO	productAttributeDAO;

	public void setProductAttributeDAO(IProductAttributeDAO productAttributeDAO) {
		this.productAttributeDAO = productAttributeDAO;
	}

	public IBaseDAO<ProductAttribute, Long> getBaseDAO() {
		return productAttributeDAO;
	}

	protected void beforeSaveOrUpdateCheck(ProductAttribute pa) throws ServiceException {
		if (pa != null) {
			String key = pa.getKey();
			if (StringUtils.isNotBlank(key)) {
				List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
				conditionList.add(new ConditionBean(ProductAttribute.KEY, key));
				List<ProductAttribute> list = getEntityList(conditionList, null);
				if (list != null && list.size() > 0) {
					if (list.size() == 1 && pa.getId() != null && pa.getId().equals(list.get(0).getId())) {
					} else {
						ExceptionManager.throwException(ServiceException.class, PRODUCT_ATTRIBUTE_KEY_EXIST_ERROR);
					}
				}
			}
		}
	}

	@Override
	public ProductAttribute getProductAttributeByCode(String attributeCode) {
		return productAttributeDAO.getProductAttributeByCode(attributeCode);
	}
	
	
}
