package com.upg.ucars.basesystem.product.core.product;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.product.MemberProductInfo;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;


public interface IMemberProductInfoDAO extends IBaseDAO<MemberProductInfo, Long> {
	/**
	 * 获取接入的产品
	 * @param miNo
	 * @return
	 */
	List<MemberProductInfo> findProductsByMiNo(String miNo);
	/**
	 * 获取接入子产品
	 * @param prodId 父级产品id
	 * @param miNo 接入编号
	 * @return
	 */
	List<ProductNode> findSebMemberProduct(Long prodId, String miNo);
	
	/**
	 * 增加接入点产品
	 *
	 * @param miNo 接入编号
	 * @param prodIdList 产品ID集合
	 */
	void saveMemberProducts(String miNo, List<Long> prodIdList);
	
	/**
	 * 删除接入点产品
	 *
	 * @param miNo 接入编号
	 * @param prodIdList 产品ID集合
	 */
	void deleteMemberProducts(String miNo, List<Long> prodIdList);
	/**
	 * 分页获取接入产品
	 * @param miNo 接入编号
	 * @param product 产品信息
	 * @param pg 分页
	 * @return
	 */
	List<MemberProductInfo> findProductsByMiNo(String miNo,ProductInfo product, Page pg);
	/**
	 * 获取成员产品对象
	 * @param miNo
	 * @param prodNo
	 * @return
	 */
	MemberProductInfo getMemberProductByProdNo(String miNo, String prodNo) ;
	
	/**
	 * 获取归属接入点的指定上级产品id的子产品集合
	 * @param miNo
	 * @param parentProdId
	 * @return Object[]{ProductInfo, MemberProductInfo}
	 * @throws DAOException
	 */
	List<Object[]> findMemberProdByParentId(String miNo, Long parentProdId) throws  DAOException;
	
	
	
}
