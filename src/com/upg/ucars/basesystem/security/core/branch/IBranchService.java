package com.upg.ucars.basesystem.security.core.branch;



import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.audit.AuditTask;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.ReBrchFunc;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.model.security.UserLogonInfo;

/**
 * 机构服务类
 * 
 * @author shentuwy
 *
 */
public interface IBranchService extends IBaseService {
	/*
	 * 总部机构Level
	 */
	public static Long BRCH_LEVE_HQ = 1L;
	/**
	 * 新增机构信息
	 * @param branch	机构对象
	 * @return	机构ID
	 * @throws ServiceException
	 */
	public Long addBrch(Branch branch) throws DAOException, ServiceException;
	/**
	 * 批量添加
	 * @param brchs	机构信息集合
	 * @throws DAOException
	 */
	public void addBrchs(List<Branch> brchs) throws DAOException;
	/**
	 * 编辑机构信息
	 * @param branch	机构对象
	 * @return	编辑成功标记
	 * @throws ServiceException
	 */
	public void editBrch(Branch branch) throws DAOException;
	/**
	 * 删除机构信息
	 * @param branch	机构对象
	 * @return	删除成功标记
	 * @throws ServiceException
	 */
	public void delBrch(Branch branch) throws DAOException;
	/**
	 * 批量删除
	 * @param brches	机构信息集合
	 * @throws DAOException
	 */
	public void delBrches(List<Branch> brches) throws DAOException;
	/**
	 * 通过机构ID删除机构信息
	 * @param brchId	机构ID
	 * @return	删除成功标记
	 * @throws ServiceException
	 */
	public void delBrch(Long brchId) throws DAOException;
	/**
	 * 根据机构id获取机构信息
	 * @param brchId 机构ID
	 * @return
	 */
	public Branch getBranchByBrchId(Long brchId) throws DAOException;
	
	/**
	 * 获得子机构列表
	 * @param treeCode	    机构树型编码
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
	 * 分页获得总部机构
	 * @param	page	分页信息
	 * @return	List<Branch>
	 * @throws DAOException
	 */
	public List<Branch> getHQBranches(Page page)  throws DAOException;
	/**
	 * 根据接入编号获取总部机构
	 * @param miNo
	 * @return
	 * @throws DAOException
	 */
	public Branch getHQBranchByMino(String miNo) throws DAOException;
	/**
	 * 按接入ID获取接入成员机构列表
	 * @param miNo	接入机构编码
	 * @return	List<Branch>
	 * @throws DAOException
	 */
	public List<Branch> getBelongMems(String miNo) throws DAOException; 
	/**
	 * 按接入ID分页获取接入成员机构列表
	 * @param miNo	接入机构编码
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
	 * 根据机构ID获得机构权限对应记录列表
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
	 * 添加机构权限
	 * @param rbf	机构权限信息
	 * @throws DAOException
	 */
	public void addBrchFunc(ReBrchFunc rbf) throws DAOException;
	/**
	 * 批量添加机构权限
	 * @param brchFuncs	机构权限列表
	 * @throws DAOException
	 */
	public void addBrchFuncs(List<ReBrchFunc> brchFuncs) throws DAOException; 
	/**
	 * 编辑机构权限
	 * @param rbf 机构权限信息
	 * @throws DAOException
	 */
	public void editBrchFunc(ReBrchFunc rbf) throws DAOException; 
	/**
	 * 编辑机构权限
	 * @param brchFuncs 机构权限列表
	 * @throws DAOException
	 */
	public void editBrchFuncs(List<ReBrchFunc> brchFuncs) throws DAOException; 
	/**
	 * 删除机构权限
	 * @param brchFuncs 机构权限列表
	 * @throws DAOException
	 */
	public void delBrchFuncs(List<ReBrchFunc> brchFuncs) throws DAOException;
	/**
	 * 通过机构ID删除机构权限信息
	 * @param brchId	机构ID
	 * @throws DAOException
	 */
	public void delBrchFuncByBrchId(Long brchId) throws DAOException;
	/**
	 * 根据树型编码获取上级机构
	 * @param treeCode 当前机构树型编码
	 * @return Branch
	 * @throws DAOException
	 */
	public Branch getSuperBranch(String treeCode) throws DAOException ;
	/**
	 * 根据当前机构ID获取上级机构
	 * @param brchId 当前机构ID
	 * @return Branch 上级机构
	 * @throws DAOException
	 */
	public Branch getSuperBranch(Long brchId) throws DAOException;
	/**
	 * 获取总部机构
	 * @param branch 机构查询条件（brchNo,brchName）
	 * @param pg 分页
	 * @return
	 * @throws DAOException
	 */
	@SuppressWarnings("rawtypes")
	public List getHQBranches(Branch branch, Page pg)throws DAOException;
	/**
	 * 获取接入下的机构
	 * @param branch 机构查询条件（brchNo,brchName）
	 * @param miNo
	 * @param pg
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List getBelongMemByPage(Branch branch, String miNo, Page pg);
	/**
	 * 获取机构及权限映射关系
	 * @param brchId 机构id
	 * @return
	 * @throws DAOException
	 */
	public List<ReBrchFunc> getFunctionMap(Long brchId)throws DAOException;
	/**
	 * 提交机构权限分配审批
	 * @param logonInfo 
	 * @param brch 机构
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public void commitBrchFuncAudit(UserLogonInfo logonInfo, Branch brch)throws ServiceException,DAOException;
	
	/**
	 * 批量机构权限分配审批
	 * 
	 * @param logonInfo
	 * @param ids
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public void batchCommitBrchFuncAudit(UserLogonInfo logonInfo,List<Long> ids) throws ServiceException,DAOException;
	/**
	 * 撤销机构权限分配审批
	 * @param logonInfo 
	 * @param brch
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public void revokeBrchFuncAudit(UserLogonInfo logonInfo, Branch brch)throws ServiceException,DAOException;
	
	/**
	 * 批量撤销机构权限分配审批
	 * 
	 * @param logonInfo
	 * @param ids
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public void batchRevokeBrchFuncAudit(UserLogonInfo logonInfo,List<Long> ids)throws ServiceException,DAOException;
	
	/**
	 * 获取机构已审核的权限
	 * @param branchId
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public List<Sysfunc> getAllredCheckedFunctions(Long branchId)throws ServiceException,DAOException;
	/**
	 * 获取机构权限审批任务
	 * @param logonInfo
	 * @param brchId
	 * @return
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public AuditTask findBrchFuncAuditTask(UserLogonInfo logonInfo,Long brchId)throws ServiceException,DAOException;
	/**
	 * 添加权限
	 * @param brchId
	 * @param funcIdList
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public void addFunc(Long brchId, List<Long> funcIdList)throws ServiceException,DAOException;
	/**
	 * 删除权限
	 * @param brchId
	 * @param funcIdList
	 * @throws ServiceException
	 * @throws DAOException
	 */
	public void removeFunc(Long brchId, List<Long> funcIdList)throws ServiceException,DAOException;
	
	/**
	 * 
	 * 获取所有的父机构信息列表
	 *
	 * @param branchId
	 * @return
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public List<Branch> getParentBranchList(Long branchId) throws DAOException,ServiceException;
	
	/**
	 * 获取机构的权限状态
	 * @param branchId
	 * @return String 0:未分配,1:分配未审核或是分配中,2:已审核,3:审核中
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public String getBrchFuncStatus(Long branchId) throws DAOException,ServiceException;

	/**
	 * 根据treecode校验机构是否还有子机构
	 * @param treeCode 
	 * @return true:有 false:无
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public boolean hasSubBranches(String treeCode) throws DAOException,ServiceException;
	
	/**
	 * 按机构id，机构权限状态查询权限
	 * */
	public List<Sysfunc> queryFunc(Long brchId, String brchFuncStatus) throws DAOException,ServiceException;
	
	/**
	 * 获取机构
	 *
	 * @param miNo
	 * @param brchNo
	 * @return
	 */
	Branch getBranch(String miNo, String brchNo);
	/**
	 * 根据树型码查询机构
	 * 
	 * @deprecated replaced by {@link #getBrchByTreeCodeAndMiNo(String, String)}
	 * 
	 * @param treeCode
	 * @return
	 */
	@Deprecated
	Branch getBrchByTreeCode(String treeCode); 
	/**
	 * 获取机构
	 *
	 * @param treeCode
	 * @param miNO
	 * @return
	 */
	Branch getBrchByTreeCodeAndMiNo(String treeCode,String miNo);
	/**
	 * 获得子机构列表
	 * 
	 * @deprecated replaced by {@link #getSubBranchList(String, String, Page)}
	 * 
	 * @param treeCode	机构树型编码
	 * @param	page	分页信息
	 * @return List<Branch>
	 * @throws DAOException
	 */
	@Deprecated
	List<Branch> getSubBrchByPage(String treeCode, Page page); 
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
	 * 
	 * @deprecated replaced by {@link #getSubBranchList(String, String)}
	 * 
	 * @param treeCode	机构树型编码
	 * @return List<Branch>
	 */
	@Deprecated
	List<Branch> getSubBranches(String treeCode);
	
	/**
	 * 获取子机构列表 
	 *
	 * @param treeCode
	 * @param miNo
	 * @return
	 */
	List<Branch> getSubBranchList(String treeCode,String miNo);
	
	/**
	 * 拷贝机构权限
	 * 
	 * @param brchId
	 * @param destBranchIds
	 */
	public void copyBrchFunc(Long brchId,List<Long> destBranchIds);
	
	/**
	 * 获取机构的所有子机构id列表 包括机构本身
	 * 
	 * @param brchId
	 * @return
	 */
	public List<Long> getSubBranchIds(Long brchId);
	
}
