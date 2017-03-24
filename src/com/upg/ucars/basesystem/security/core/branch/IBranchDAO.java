package com.upg.ucars.basesystem.security.core.branch;


import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.ReBrchFunc;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;

/**
 * 机构数据访问
 * 
 * @date 2011-2-12 上午11:28:10
 *
 */
public interface IBranchDAO extends IBaseDAO<Branch, Long>{
	/**
	 * 根据机构id获取机构信息
	 * @param brchId 用户号
	 * @return
	 */
	public Branch getBranchByBrchId(Long brchId) throws DAOException;
	
	/**
	 * 获得子机构列表
	 * @param treeCode	    树型编码
	 * @param excludeBrchId	不包含机构
	 * @return List<Branch>
	 * @throws DAOException
	 */
	public List<Branch> getSubBranches(String treeCode,Long excludeBrchId) throws DAOException;
	
	/**
	 * 获得总部机构
	 * @return	List<Branch>
	 * @throws DAOException
	 */
	public List<Branch> getHQBranches()  throws DAOException; 
	/**
	 * 获得总部机构
	 * @param	page	分页信息
	 * @return	List<Branch>
	 * @throws DAOException
	 */
	public List<Branch> getHQBranches(Page page)  throws DAOException;
	
	/**
	 * 按接入ID获取接入成员机构列表
	 * @param miNo	接入者编码
	 * @return	List<Branch>
	 * @throws DAOException
	 */
	public List<Branch> getBelongMems(String miNo) throws DAOException; 
	/**
	 * 按接入ID获取接入成员机构列表
	 * @param miNo	接入者编码
	 * @param page	分页信息
	 * @return	List<Branch>
	 * @throws DAOException
	 */
	public List<Branch> getBelongMemByPage(String miNo, Page page) throws DAOException; 
	/**
	 * 根据机构ID获得权限列表
	 * @param brchId	机构ID
	 * @return	List<ReBrchFunc>
	 * @throws DAOException
	 */
	public List<ReBrchFunc> getFunctionIds(Long brchId)  throws DAOException;
	/**
	 * 根据机构ID获得权限列表
	 * @param brchId	机构ID
	 * @param page	分页信息
	 * @return	List<ReBrchFunc>
	 * @throws DAOException
	 */
	public List<ReBrchFunc> getFunctionIdByPage(Long brchId, Page page)  throws DAOException; 
	/**
	 * 根据机构ID获得权限列表
	 * @param brchId	机构ID
	 * @return	List<Sysfunc>
	 * @throws DAOException
	 */
	public List<Sysfunc> getFunctions(Long brchId)  throws DAOException;
	/**
	 * 根据机构ID获得权限列表
	 * @param brchId	机构ID
	 * @param page	分页信息
	 * @return	List<Sysfunc>
	 * @throws DAOException
	 */
	public List<Sysfunc> getFunctionByPage(Long brchId, Page page)  throws DAOException;
	/**
	 * 组合条件查询
	 * @param brch	组合条件信息
	 * @return	List<Branch>
	 * @throws DAOException
	 */
	public List<Branch> findBranch(Branch brch) throws DAOException;
	/**
	 * 组合条件查询
	 * @param brch	组合条件信息
	 * @param page 分页信息
	 * @return	List<Branch>
	 * @throws DAOException
	 */
	public List<Branch> findBranchByPage(Branch brch, Page page) throws DAOException;
	/**
	 * 获得子机构（不包括机构本身）
	 * @param brch 组合条件信息
	 * @return List<Branch>
	 */
	public List<Branch> findSubBrchs(Branch brch, Page page) throws DAOException;
	
	/**
	 * 获得子机构
	 * @param hasLocalBrch true:包括本机构，false:不包括本机构
	 * @param brch 组合条件信息
	 * @param page 分页信息
	 * @return List<Branch>
	 */
	public List<Branch> findSubBrchs(Branch brch, Page page,boolean hasLocalBrch)throws DAOException;
	
	/**
	 * 根据接入编号获取总部机构
	 * @param miNo 接入编号
	 * @return
	 * @throws DAOException
	 */
	public Branch getHQBranch(String miNo)throws DAOException;
	/**
	 * 条件查询总部
	 * 返回列表中不包含指定的Branch.branchId
	 * @param branch
	 * @param page
	 * @return
	 */
	public List getHQBranches(Branch branch, Page page);
	/**
	 * 条件查询接入机构
	 * @param branch
	 * @param miNo
	 * @param page
	 * @return
	 */
	public List getBelongMemByPage(Branch branch, String miNo, Page page);
	/**
	 * 获取已审核的机构权限
	 * @param branchId
	 * @return
	 * @throws DAOException
	 */
	public List<Sysfunc> getAllredCheckedFunctions(Long branchId)throws DAOException;
	/**
	 * 获取子机构的数量
	 * 
	 * @param branch
	 * @return
	 * @throws DAOException
	 */
	public Long getSubBranchsCount(Branch branch ) throws DAOException;
	
	/**
	 * 
	 * 根据树形编码获取机构列表
	 *
	 * @param treeCodeList
	 * @param miNo
	 * @return
	 * @throws DAOException
	 */
	public List<Branch> getBranchListByTreeCodeList(final List<String> treeCodeList,final String miNo) throws DAOException;
	
	/**
	 * 判断机构码是否重复
	 * 用于新增，修改机构时校验
	 * @param String brchNo 机构编码
	 * @param Long excludeBrchId 排除校验机构范围，用于修改时校验
	 * @return boolean true:重复，false：不重复
	 * @throws DAOException
	 */
	public boolean hasSameCode(String brchNo,Long excludeBrchId) throws DAOException;
	
	/**
	 * 按机构id，机构权限状态查询权限
	 * */
	public List<Sysfunc> queryFunc(Long brchId, String brchFuncStatus) throws DAOException;
	/**
	 * 获取子机构列表
	 *
	 * @param treeCode
	 * @param miNo
	 * @param page
	 * @return
	 */
	List<Branch> getSubBranchList(String treeCode,String miNo, Page page);
	/**
	 * 获得子机构列表
	 * @param treeCode	树型编码
	 * @return List<Branch>
	 * @throws DAOException
	 */
	List<Branch> getSubBranches(String treeCode); 
	

	/**
	 * 取得下一级的机构信息
	 * @param treeCode
	 * @param miNo
	 * @return
	 */
	public List<Branch> getNextSubBranches(String treeCode,String miNo) ;
}
