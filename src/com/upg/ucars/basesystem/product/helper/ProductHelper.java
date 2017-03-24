package com.upg.ucars.basesystem.product.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.upg.ucars.mapping.basesystem.product.ProductInfo;

public class ProductHelper {
	/**
	 * 给权限排序
	 *
	 * @param funcList
	 * @return
	 */
	public static List<ProductInfo> sortProductList(List<ProductInfo> prodList){
		ArrayList<ProductInfo> rsList = new ArrayList<ProductInfo>();
		
		HashMap<Long, ProductInfo> map1 = new HashMap<Long, ProductInfo>();
		for (ProductInfo prodInfo : prodList) {
			map1.put(prodInfo.getId(), prodInfo);//全部加入map
			
			if (prodInfo.getParentProdId() == null)//根菜单加入结果集
				rsList.add(prodInfo);
		}		
		
		for (ProductInfo prodInfo : prodList) {
			appendParentAndMe(prodInfo, map1, rsList);
		}		
		
		return rsList;
	}
	
	private static void appendParentAndMe(ProductInfo prodInfo, HashMap<Long, ProductInfo> map1, ArrayList<ProductInfo> rsList){
		
		ProductInfo parent = map1.get(prodInfo.getParentProdId());
		if (parent == null){
			return;
		}else{
			appendParentAndMe(parent, map1, rsList);
		}
		
		if (!rsList.contains(prodInfo))
			rsList.add(prodInfo);
		
	}

}
