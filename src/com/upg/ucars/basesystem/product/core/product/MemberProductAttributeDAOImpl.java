package com.upg.ucars.basesystem.product.core.product;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.mapping.basesystem.product.MemberProductAttribute;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;

/**
 * 
 * BranchProductAttributeDAOImpl
 * 
 * @author shentuwy
 * 
 */
public class MemberProductAttributeDAOImpl extends BaseDAO<MemberProductAttribute, Long> implements
		IMemberProductAttributeDAO {

	public Class<MemberProductAttribute> getEntityClass() {
		return MemberProductAttribute.class;
	}

	public List<MemberProductAttribute> getMemberProductAttributeList(Long prodId, String miNo) {
		List<MemberProductAttribute> ret = null;
		if (prodId != null && StringUtils.isNotBlank(miNo)) {
			String hql = null;
			Map<String, Object> parameterMap = new HashMap<String, Object>();

			hql = "from MemberProductAttribute bpa where bpa.productId=:productId and bpa.miNo=:miNo";
			parameterMap.put("productId", prodId);
			parameterMap.put("miNo", miNo);

			ret = queryByParam(hql, parameterMap, null);
		}
		return ret == null ? EMPTY_LIST : ret;
	}

	public List<ProductInfo> getProductListByAttr(String miNo, String attrKey, String attrValue) {
		List<ProductInfo> ret = null;
		StringBuilder sb = new StringBuilder();
		sb.append("select pi from ProductInfo pi,MemberProductAttribute mpa where pi.id=mpa.productId and mpa.miNo=:miNo and mpa.attributeKey=:attrKey and mpa.attributeValue in (:attrValue) order by pi.sortNo");
		String[] attrs = attrValue.split(",");
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("miNo", miNo);
		parameterMap.put("attrKey", attrKey);
		parameterMap.put("attrValue", attrs);
		ret = queryByParam(null, sb.toString(), parameterMap, null);
		return ret == null ? Collections.EMPTY_LIST : ret;
	}
	
	public List<ProductInfo> getSubProductListByAttrAndParent(String miNo,String attrKey,String attrValue,Long parentId){
		List<ProductInfo> ret = null;
		StringBuilder sb = new StringBuilder();
		sb.append("select pi from ProductInfo pi,MemberProductInfo mp");
		sb.append(" where mp.prodId=pi.id and pi.parentProdId=:parentProdId and mp.miNo=:miNo ");
		sb.append(" and ( exists ( from MemberProductAttribute mpa where mpa.productId=pi.id and mpa.miNo=mp.miNo and mpa.attributeKey=:attributeKey and mpa.attributeValue in (:attributeValue)) ");
		sb.append(" or not exists (select mpa from MemberProductAttribute mpa where mpa.productId=pi.id and mpa.miNo=mp.miNo and mpa.attributeKey=:attributeKey)");
		sb.append(" ) order by pi.sortNo");
		String[] attrs = attrValue.split(",");
		
		Map<String,Object> parameterMap = new HashMap<String,Object>();
		parameterMap.put("miNo", miNo);
		parameterMap.put("attributeValue", attrs);
		parameterMap.put("attributeKey", attrKey);
		parameterMap.put("parentProdId", parentId);
		
		ret = queryByParam(null, sb.toString(), parameterMap, null);
		return ret == null ? Collections.EMPTY_LIST : ret;
	}
	
	public String getProductAttributeValue(String miNo,Long prodId,String attr){
		String attributeValue = null;
		String hql = "from MemberProductAttribute where productId=? and miNo=? and attributeKey=?";
		List<MemberProductAttribute> list = find(hql, new Object[]{prodId,miNo,attr});
		if (list != null && list.size() == 1) {
			attributeValue = list.get(0).getAttributeValue();
		}
		return attributeValue;
	}

}
