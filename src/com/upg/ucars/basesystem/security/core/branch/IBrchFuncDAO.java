package com.upg.ucars.basesystem.security.core.branch;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.security.ReBrchFunc;

public interface IBrchFuncDAO extends IBaseDAO<ReBrchFunc,Long> {
	/**
	 * 通过机构ID删除机构权限信息
	 * @param brchId	机构ID
	 * @throws DAOException
	 */
	public void delBrchFuncByBrchId(Long brchId) throws DAOException;

	/**
	 * 查询机构权限
	 * @param brchId 机构id
	 * @return
	 */
	public List<ReBrchFunc> getBrchFuncByBrchId(Long brchId);

	/**
	 * 更改机构权限状态
	 * @param brchId
	 * @param struts
	 * @throws DAOException
	 */
	public void updateBrchFuncStatusByBrchId(Long brchId, String struts) throws DAOException;

	public ReBrchFunc findByBrchIdAndFuncId(Long brchId, Long unFuncId)throws DAOException;
	
	/**
	 * 是否有机构引用权限
	 * @param funcId
	 * @return true:有 false:无
	 * @throws DAOException
	 */
	public boolean hasBrchRefFunc(Long funcId) throws DAOException;
	
	/**
	 * 校验这些多个机构中是否存在引用权限
	 * 用于删除机构校验
	 * @param brchIds 机构id列表
	 * @return true: 存在引用 false:不存在
	 * @throws DAOException
	 */
	public boolean hasBrchFuncRef(List<Long> brchIds) throws DAOException;
	
	/**
	 * 获取机构的权限状态
	 * @param branchId
	 * @return String 0:未分配,1:分配未审核或是分配中,2:已审核,3:审核中
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public String getBrchFuncStatus(Long branchId) throws DAOException;

}
