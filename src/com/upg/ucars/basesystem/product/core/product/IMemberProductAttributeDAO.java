package com.upg.ucars.basesystem.product.core.product;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.mapping.basesystem.product.MemberProductAttribute;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;

/**
 * 
 * IBranchProductAttributeDAO
 * 
 * @author shentuwy
 * 
 */
public interface IMemberProductAttributeDAO extends IBaseDAO<MemberProductAttribute, Long> {

	/**
	 * 
	 * 获取机构产品属性列表
	 * 
	 * @param prodId
	 * @param miNo
	 * @param branchId
	 * @return
	 */
	public List<MemberProductAttribute> getMemberProductAttributeList(Long prodId, String miNo);

	/**
	 * 
	 * TODO description
	 * 
	 * @param miNo
	 * @param attrKey
	 * @param attrValue
	 * @return
	 */
	public List<ProductInfo> getProductListByAttr(String miNo, String attrKey, String attrValue);
	public List<ProductInfo> getSubProductListByAttrAndParent(String miNo,String attrKey,String attrValue,Long parentId);
	
	/**
	 * 获取产品属性值
	 * 
	 * @param miNo
	 * @param prodId
	 * @param attr
	 * @return
	 */
	public String getProductAttributeValue(String miNo,Long prodId,String attr);

}
