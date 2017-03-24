package com.upg.ucars.basesystem.product.core.product;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.mapping.basesystem.product.ReBrchProd;

public class ReBrchProdDAOImp extends BaseDAO<ReBrchProd, Long> implements IReBrchProdDAO {

	@Override
	public Class getEntityClass() {
		return ReBrchProd.class;
	}

	
}
