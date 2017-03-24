package com.upg.ucars.framework.bpm.procmap;

import java.util.List;

import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.framework.bpm.ProcessDef;
import com.upg.ucars.mapping.framework.bpm.ProdProcMap;
import com.upg.ucars.model.ConditionBean;
/**
 * 机构产品对应流程管理接口
 *
 * @author shentuwy
 */
public interface IProdProcMapService {
	/**
	 * 增加接入与流程关联
	 * @param entity
	 * @throws ServiceException
	 * @throws DAOException
	 */
	void saveMemberProc(ProdProcMap entity)  throws ServiceException, DAOException;
	/**
	 * 增加接入产品与流程关联
	 * @param entity
	 * @throws ServiceException
	 * @throws DAOException
	 */
	//void saveMemberProdProc(ProdProcMap entity)  throws ServiceException, DAOException;
	/**
	 * 增加机构产品与流程关联
	 * @param entity
	 * @throws ServiceException
	 * @throws DAOException
	 */
	//void saveBrchProdProc(ProdProcMap entity)  throws ServiceException, DAOException;
	/**
	 * 修改关联信息
	 * @param entity
	 * @throws ServiceException
	 * @throws DAOException
	 */
	void update(ProdProcMap entity) throws ServiceException,DAOException;
	ProdProcMap getById(Long id) throws ServiceException,DAOException;	
	void deleteById(Long id) throws ServiceException,DAOException;
	List<ProdProcMap> queryProdProcMap(QueryComponent qcpt, Page page)throws DAOException;
	List<ProdProcMap> queryProdProcMap(List<ConditionBean> condList , Page page)throws DAOException;
	/**
	 * 查询接入产品与流程信息
	 * @param miNo
	 * @param condList
	 * @param page
	 * @return List< Ojbect[ProductInfo,ProdProcMap,ProcessDef]>
	 * @throws DAOException
	 */
	//List<Object[]> queryMemberProdProcessInfo(String miNo, List<ConditionBean> condList , Page page)throws DAOException;
	/**
	 * 查询机构产品与流程信息
	 * @param brchId
	 * @param condList
	 * @param page
	 * @return List< Ojbect[ProductInfo,ProdProcMap,ProcessDef]>
	 * @throws DAOException
	 */
	//List<Object[]> queryBrchProdProcessInfo(Long brchId, List<ConditionBean> condList , Page page)throws DAOException;
	
	/**
	 * 根据机构和产品获取对应流程名
	 * 
	 * @param brchId
	 * @param prodNo
	 * @return
	 * @throws ServiceException 没有对应流程时抛出ServiceException异常
	 */
	String getProcessName(Long brchId, String prodNo) throws ServiceException;
	String getProcessName(Long brchId, String prodNo,boolean ignoreProcess);
	/**
	 * 查询归属接入点的流程 
	 *
	 * @param miNo
	 * @return
	 */
	List<ProcessDef> findProcessByMember(String miNo, Page page) throws DAOException;
	/**
	 *  查询归属接入点的流程及关联产品 
	 *
	 * @param miNo
	 * @param qc
	 * @param page
	 * @return List< Object[ProcessDef, ProductInfo]>
	 * @throws DAOException
	 */
	List<Object[]> findProcAndProductByMember(String miNo, QueryCondition qc,Page page) throws DAOException;
	/**
	 *  查询系统内发布流程及关联产品 
	 *
	 * @param miNo
	 * @param qc
	 * @param page
	 * @return List< Object[ProcessDef, ProductInfo]>
	 * @throws DAOException
	 */
	List<Object[]> findProcAndProduct(QueryCondition qc, Page page) throws DAOException;
	/**
	 * 设置接入的流程权限
	 *
	 * @param miNo
	 * @param procIdList
	 * @throws ServiceException
	 */
	void buildMemberProcess(String miNo, List<Long> procIdList) throws ServiceException;
	
	/**
	 * 设置接入的默认流程
	 *
	 * @param miNo
	 * @param pidList < ProcessDef.id >
	 * @throws ServiceException
	 */
	void buildDefaultProcess(String miNo, List<Long> pidList) throws ServiceException;
	
	/**
	 * 增加接入的流程权限
	 * @param miNo
	 * @param procIdList
	 */
	void addMemeberProcess(String miNo, List<Long> procIdList);
	/**
	 * 删除接入的流程权限
	 *
	 * @param miNo
	 * @param procIdList
	 */
	void removeMemeberProcess(String miNo, List<Long> procIdList);
	
	/**
	 * 查询归属接入点的流程 
	 *
	 * @param miNo
	 * @return
	 */
	List<ProcessDef> findProcessByBrch(Long brchId, Page page) throws DAOException;
	/**
	 * 设置机构的流程权限
	 *
	 * @param brchId
	 * @param procIdList
	 * @throws ServiceException
	 */
	void buildBranchProcess(Long brchId, List<Long> procIdList) throws ServiceException;
}
