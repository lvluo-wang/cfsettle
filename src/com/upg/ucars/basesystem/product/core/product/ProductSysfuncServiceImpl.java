package com.upg.ucars.basesystem.product.core.product;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.XBaseService;
import com.upg.ucars.mapping.basesystem.product.ProductSysfunc;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.model.ConditionBean;

/**
 * 
 * ProductSysfuncServiceImpl
 *
 * @author shentuwy
 *
 */
public class ProductSysfuncServiceImpl extends XBaseService<ProductSysfunc> implements IProductSysfuncService {
	
	/** 产品服务dao */
	private IProductSysfuncDAO productSysfuncDAO;
	

	public IBaseDAO<ProductSysfunc, Long> getBaseDAO() {
		return productSysfuncDAO;
	}
	


	public List<Sysfunc> getSysfuncByProduct(Long productId) {
		return productSysfuncDAO.getSysfuncByProduct(productId);
	}
	
	public void assignSysfuncForProduct(Long productId, List<Long> funcIdList) {
		if( productId != null ){
			List<ConditionBean> conditionList = new ArrayList<ConditionBean>(1);
			conditionList.add(new ConditionBean("productId",productId));
			List<ProductSysfunc> existList = getEntityList(conditionList, null);
			List<ProductSysfunc> addList = new ArrayList<ProductSysfunc>();
			if( funcIdList != null && funcIdList.size() > 0 ){
				for( Iterator<Long> it = funcIdList.iterator(); it.hasNext(); ){
					Long funcId = it.next();
					if( existList != null && existList.size() > 0 ){
						for( int i = 0; i < existList.size(); i++ ){
							ProductSysfunc ps = existList.get(i);
							if( ps.getFuncId().equals(funcId) ){
								existList.remove(i);
								break;
							}
						}
					}
					ProductSysfunc ps = new ProductSysfunc();
					ps.setFuncId(funcId);
					ps.setProductId(productId);
					ps.setStatus(ProductSysfunc.STATUS_EFFECITVE);
					addList.add(ps);
				}
			}
			
			//删除
			if( existList != null && existList.size() > 0 ){
				productSysfuncDAO.delAll(existList);
			}
			//增加
			if( addList != null && addList.size() > 0 ){
				productSysfuncDAO.saveOrUpdateAll(addList);
			}
		}
	}
	
	

	public void setProductSysfuncDAO(IProductSysfuncDAO productSysfuncDAO) {
		this.productSysfuncDAO = productSysfuncDAO;
	}
	
	
}
