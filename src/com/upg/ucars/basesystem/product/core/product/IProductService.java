package com.upg.ucars.basesystem.product.core.product;

import java.util.List;
import java.util.Map;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.product.MemberProductInfo;
import com.upg.ucars.mapping.basesystem.product.ProductAttribute;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;
import com.upg.ucars.mapping.basesystem.product.ReBrchProd;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;
/**
 * 产品信息服务
 * 
 * @date 2011-2-14 下午05:02:53
 *
 */
public interface IProductService extends IBaseService{

	/**
	 * 创建产品信息
	 * @param func 权限
	 */
	public void createProduct(ProductInfo prod)throws ServiceException,DAOException;
	/**
	 * 修改产品信息
	 * @param func
	 */
	public void modifyProduct(ProductInfo prod)throws ServiceException,DAOException;
	/**
	 * 查询产品信息集合
	 * @param qc 查询组件
	 * @param page 页面
	 * @return
	 */
	public List<ProductInfo> queryProduct(QueryComponent qc,Page page)throws ServiceException,DAOException;
	/**
	 * 按条件查询 
	 *
	 * @param conditionList
	 * @param orderList
	 * @param page
	 * @return
	 * @throws DAOException
	 */
	public List<ProductInfo> queryProductByConditin(List<ConditionBean> conditionList, List<OrderBean> orderList, Page page)throws DAOException;
	/**
	 * 获取子产品
	 * @param prodId
	 * @return
	 */
	public List<ProductInfo> findSubProduct(Long prodId)throws ServiceException,DAOException;
	/**
	 * 异步的获取子产品
	 * @param parentFuncId
	 * @return
	 */
	public List<Map<String,Object>> getAsyncSubProduct(Long parentProdId)throws ServiceException,DAOException;
	/**
	 * 判断指定产品是否拥有子产品
	 * @param parentProdId
	 * @return
	 */
	public boolean hasSubProduct(Long parentProdId)throws ServiceException,DAOException;
	/**
	 * 根据产品id获取产品对象
	 * @param prodId
	 * @return
	 * @throws ServiceException
	 */
	public ProductInfo getProduct(Long prodId)throws ServiceException,DAOException;

	/**
	 * 移除一个产品信息
	 * @param prodId
	 * @throws ServiceException
	 */
	public void removeProduct(Long prodId)throws ServiceException,DAOException;

	/**
	 * 将source产品移到target产品的point位置
	 * @param sourceProd 将要移动的权限
	 * @param targetProd 目标权限
	 * @param point 位置 'append','top' or 'bottom‘
	 */
	public void moveProduct(Long sourceProd,Long targetProd,String point)throws ServiceException,DAOException;
	/**
	 * 获取子产品
	 * @param prodId 产品id
	 * @return
	 */
	public List<ProductInfo> getSubProduct(Long prodId)throws ServiceException,DAOException;
	/**
	 * 获取接入点产品
	 *
	 * @param miNo
	 * @return Object[]{ProductInfo, MemberProductInfo}
	 * @throws DAOException
	 */
	public List<Object[]> findMemberProduct(String miNo, Page page)throws DAOException;
	/**
	 * 获取成员行子产品 
	 *
	 * @param miNo
	 * @param prodId
	 * @return  Object[]{ProductInfo, MemberProductInfo}
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public List<Object[]> getMemberSubProduct(String miNo, Long parentProdId)throws ServiceException,DAOException;
	/**
	 * 修改接入点产品信息
	 * @param func
	 */
	public void modifyMemberProduct(MemberProductInfo memberProd)throws ServiceException,DAOException;
	/**
	 * 根据产品编号获取产品对象
	 * @param prodNo
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public ProductInfo getProductByProdNo(String prodNo)throws ServiceException,DAOException;
	
	public ProductInfo getProductByProdName(String prodName)throws ServiceException,DAOException;
	/**
	 * 根据产品编号获取父级产品对象
	 * @param prodNo
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public ProductInfo getParentProductByProdNo(String prodNo)throws ServiceException,DAOException;
	
	public MemberProductInfo getMemberProductByProdNo(String miNo, String prodNo) throws DAOException;
	
	/**
	 * 获取接入子产品
	 * @param prodId 父级产品id
	 * @param miNo 接入编号
	 * @return
	 */
	public List<ProductNode> findSubMemberProduct(Long prodId, String miNo);
	/**
	 * 设定接入者产品
	 * @param miNo
	 * @param productIdList
	 */
	public void buildMemberProduct(String miNo, List<Long> productIdList);
	/**
	 * 为机构设置准入产品
	 *
	 * @param brchId
	 * @param productIdList
	 */
	public void buildBranchProduct(Long brchId, List<Long> productIdList);
	/**
	 * 复核机构的准入产品 
	 *
	 * @param brchId
	 * @param isAgree
	 */
	public void checkBranchProduct(Long brchId, boolean isAgree);
	/**
	 * 查询机构产品关系
	 * @param brchId
	 * @return
	 */
	public List<ReBrchProd> findReBrchProdByBrch(Long brchId);
	/**
	 * 查询需要进行复核的机构
	 * @param miNo
	 * @return
	 */
	public List<Branch> findBranchOfCheckProd(QueryCondition qc, String miNo, Page page);
	/**
	 * 查询机构的已授权产品
	 *
	 * @param qc 注入查询条件 ( 机构别名branch.*, 产品别名product.*)
	 * @param brchId
	 * @param page
	 * @return
	 */
	public List<ProductInfo> findAuthProdOfBrch(QueryCondition qc, Long brchId, Page page);

	/**
	 * 
	 * 获取产品对应的所有属性
	 *
	 * @param prodId
	 * @param miNo
	 * @return
	 */
	public List<ProductAttribute> getAttributesByProdId(Long prodId,String miNo);
	
	/**
	 * 
	 * 查询产品列表
	 *
	 * @param miNo
	 * @param attrKey
	 * @param attrValue
	 * @return
	 */
	public List<ProductInfo> getProductListByAttr(String miNo,String attrKey,String attrValue);
	
	public List<ProductInfo> getSubProductListByAttrAndParent(String miNo,String attrKey,String attrValue,Long parentId);
	
	/**
	 * 校验产品数量
	 * @param productIds 产品id列表
	 * @return true： 表示数据库中的数量与productIds.size相等 
	 *         false：表示数据库中的数量与productIds.size不相等，表明数据库中有些产品可能被删除了
	 */
	public boolean checkProductAmount(List<Long> productIds);
	
	/**
	 * 获取产品属性值
	 * 
	 * @param miNo
	 * @param prodNo
	 * @param attr
	 * @return
	 */
	public String getAttributeValue(String miNo,String prodNo,String attr);
	
	/**
	 * 获取所有的子产品代码列表包括参数
	 * 
	 * @param prodNo
	 * @return
	 */
	public List<String> getAllSubProdNos(String prodNo);
}
