package com.upg.ucars.basesystem.product.core.product;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;

public interface IProductInfoDAO extends IBaseDAO<ProductInfo,Long>{
	/**
	 * 获取指定上级产品id的子产品集合
	 * @param parentFuncId
	 * @return
	 */
	public List findProdByParentId(Long parentProdId)throws DAOException;
	
	/**
	 * 判断指定的产品id是否拥有子产品
	 * @param parentFuncId
	 * @return
	 */
	public boolean hasSubProdByParentId(Long parentProdId)throws DAOException;

	/**
	 * 通过名称获取Id
	 * @param funcName
	 * @return
	 */
	public Long getIdByProdNameInParent(String prodName,Long parentProdId) throws DAOException;
	/**
	 * 通过key值获取Id
	 * @param prodNameKey
	 * @return
	 */
	public Long getIdByNameKeyInParent(String prodNameKey,Long parentProdId)throws DAOException;
	/**
	 * 获取所有拥有URL属性的产品
	 * @return
	 */
	public List findAllURLProd()throws DAOException;
	/**
	 * 获取去指定产品id的子节点中最大排序序号
	 * @param targetFunc
	 */
	public Long getMaxSortNo(Long targetFunc)throws DAOException;
	/**
	 * 获取指定的产品的同子集中排序级别级低的集合
	 * @param target
	 * @return
	 */
	public List getButtomProduct(ProductInfo target)throws DAOException;
	/**
	 * 根据产品编号获取产品信息
	 * @param prodNo
	 * @return
	 */
	public ProductInfo getProductInfoByProdNo(String prodNo);
	
	public ProductInfo getProductInfoByProdName(String prodName);
	
	/**
	 * 
	 * 获取接入点下面的所有产品
	 * 
	 * @param miNo
	 * @return
	 * @throws DAOException
	 */
	public List<ProductInfo> getProductListByMino(String miNo) throws DAOException;
	
	/** 
	 * 根据产品列表id获取数据库中的产品数量
	 * @param productIds
	 * @return
	 * @throws DAOException
	 */
	public int getProductAmount(List<Long> productIds) throws DAOException;
}
