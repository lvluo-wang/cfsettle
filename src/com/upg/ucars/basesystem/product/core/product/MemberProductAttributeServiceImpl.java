package com.upg.ucars.basesystem.product.core.product;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.XBaseService;
import com.upg.ucars.mapping.basesystem.product.MemberProductAttribute;
import com.upg.ucars.mapping.basesystem.product.ProductAttribute;
import com.upg.ucars.model.ConditionBean;

/**
 * 
 * MemberProductAttributeServiceImpl
 * 
 * @author shentuwy
 * 
 */
public class MemberProductAttributeServiceImpl extends XBaseService<MemberProductAttribute> implements
		IMemberProductAttributeService {

	private IMemberProductAttributeDAO	memberProductAttributeDAO;

	private IProductAttributeService	productAttributeService;

	public void setMemberProductAttributeDAO(IMemberProductAttributeDAO memberProductAttributeDAO) {
		this.memberProductAttributeDAO = memberProductAttributeDAO;
	}

	public void setProductAttributeService(IProductAttributeService productAttributeService) {
		this.productAttributeService = productAttributeService;
	}

	public IBaseDAO<MemberProductAttribute, Long> getBaseDAO() {
		return memberProductAttributeDAO;
	}

	public void assignProductAttributes(Long prodId, List<Long> attrIds, String miNo) {
		if (prodId != null && StringUtils.isNotBlank(miNo)) {
			List<ConditionBean> conditionList = new ArrayList<ConditionBean>(2);
			conditionList.add(new ConditionBean("productId", prodId));
			conditionList.add(new ConditionBean("miNo", miNo));
			List<MemberProductAttribute> existList = getEntityList(conditionList, null);
			List<MemberProductAttribute> addList = new ArrayList<MemberProductAttribute>();
			if (attrIds != null && attrIds.size() > 0) {
				for (Long id : attrIds) {
					ProductAttribute pa = productAttributeService.getEntityById(id);
					if (pa != null) {
						MemberProductAttribute noChangeMpa = null;
						if (existList != null && existList.size() > 0) {
							for (MemberProductAttribute mpa : existList) {
								if (id.equals(mpa.getAttributeId())) {
									noChangeMpa = mpa;
									break;
								}
							}
						}
						if (noChangeMpa == null) {
							MemberProductAttribute mpa = new MemberProductAttribute();
							mpa.setProductId(prodId);
							mpa.setMiNo(miNo);
							mpa.setAttributeId(pa.getId());
							mpa.setAttributeKey(pa.getKey());
							mpa.setAttributeValue(pa.getValue());
							addList.add(mpa);
						} else {
							existList.remove(noChangeMpa);
						}
					}
				}
			}
			if (existList != null && existList.size() > 0) {
				deleteAll(existList);
			}
			if (addList != null && addList.size() > 0) {
				saveAll(addList);
			}
		}
	}

}
