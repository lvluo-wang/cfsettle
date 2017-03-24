package com.upg.ucars.framework.bpm.procmap;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.framework.bpm.ProdProcMap;

public interface IProdProcMapDAO extends IBaseDAO<ProdProcMap, Long> {
	/**
	 * 批量删除接入点流程
	 *
	 * @param miNo
	 * @param procIdList
	 * @throws ServiceException
	 */
	void deleteMemberProcess(String miNo, List<Long> procIdList) throws ServiceException;
	/**
	 * 批量删除机构流程
	 *
	 * @param miNo
	 * @param procIdList
	 * @throws ServiceException
	 */
	void deleteBranchProcess(Long brchId, List<Long> procIdList) throws ServiceException;

}
