package com.upg.ucars.basesystem.product.core.product;

import java.util.List;

import com.upg.ucars.framework.base.IXBaseService;
import com.upg.ucars.mapping.basesystem.product.MemberProductAttribute;


/**
 * 
 * IMemberProductAttributeService
 *
 * @author shentuwy
 *
 */

public interface IMemberProductAttributeService extends IXBaseService<MemberProductAttribute> {
	
	/**
	 * 
	 * 分配产品属性
	 *
	 * @param prodId
	 * @param attrIds
	 * @param miNo
	 */
	public void assignProductAttributes(Long prodId,List<Long> attrIds,String miNo);

}
