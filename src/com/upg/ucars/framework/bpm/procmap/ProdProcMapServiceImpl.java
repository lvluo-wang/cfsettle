package com.upg.ucars.framework.bpm.procmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.upg.ucars.constant.CommonConst;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.ICommonDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.framework.bpm.ProcessDef;
import com.upg.ucars.mapping.framework.bpm.ProdProcMap;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.OrderBean;

public class ProdProcMapServiceImpl extends BaseService implements
		IProdProcMapService {
	private IProdProcMapDAO prodProcMapDAO;
	private ICommonDAO commonDAO;//spring set

	public IProdProcMapDAO getProdProcMapDAO() {
		return prodProcMapDAO;
	}

	public void setProdProcMapDAO(IProdProcMapDAO prodProcMapDAO) {
		this.prodProcMapDAO = prodProcMapDAO;
	}

	public ICommonDAO getCommonDAO() {
		return commonDAO;
	}

	public void setCommonDAO(ICommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	public void saveMemberProc(ProdProcMap entity) throws ServiceException,
			DAOException {
		entity.setMapType(ProdProcMap.MAP_TYPE_MEMBER_PROC);
		this.prodProcMapDAO.save(entity);

	}

	public void update(ProdProcMap entity) throws ServiceException,
			DAOException {
		this.prodProcMapDAO.update(entity);

	}

	public ProdProcMap getById(Long id) throws ServiceException, DAOException {
		return this.prodProcMapDAO.get(id);
	}

	public void deleteById(Long id) throws ServiceException, DAOException {
		this.prodProcMapDAO.delete(id);
	}

	public List<ProdProcMap> queryProdProcMap(QueryComponent qcpt, Page page)
			throws ServiceException {
		List<ProdProcMap> list = this.prodProcMapDAO.queryEntity(qcpt, page);
		return list;
	}

	public List<ProdProcMap> queryProdProcMap(List<ConditionBean> condList,
			Page page) throws DAOException {
		List<ProdProcMap> list = this.prodProcMapDAO.queryEntity(condList, page);
		return list;
	}

	public String getProcessName(Long brchId, String prodNo) throws ServiceException {
		return getProcessName(brchId, prodNo, false);
	}
	
	public String getProcessName(Long brchId, String prodNo,boolean ignoreProcess) {
		//机构流程
		String hql = "select def.procName from ProdProcMap map, ProcessDef def where map.procId=def.id and map.brchId=? and def.desiProdNo=? and map.mapType=?";
		List<String> list = this.commonDAO.find(hql, new Object[]{brchId, prodNo, ProdProcMap.MAP_TYPE_BRCH_PROC});
		if (!list.isEmpty()){
			if (list.size()>1){//机构分配了多个流程，则抛出异常提示。
				String procNames="";
				for (String sn : list) {
					procNames += sn+"|";
				}
				throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.PROCMAP_GETPROC_001, new String[]{prodNo, procNames});
			}
			
			return list.get(0);
		}else{
			//接入点流程
			hql = "select def.procName, map.isDefault from ProdProcMap map, ProcessDef def, Branch b where map.procId=def.id and b.brchId=? and b.miNo=map.miNo and def.desiProdNo=? and map.mapType=?";
			List<Object[]> pnList = this.commonDAO.find(hql, new Object[]{brchId, prodNo, ProdProcMap.MAP_TYPE_MEMBER_PROC});
			if (!pnList.isEmpty()){
				if (pnList.size()>1){
					for (Object[] objs : pnList) {
						if (CommonConst.LOGIC_YES.equals(objs[1]))
							return objs[0].toString();
					}
					//
					throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.PROCMAP_GETPROC_002, new String[]{prodNo});
					
				}
				return pnList.get(0)[0].toString();
				
			}
			
		}		
		//
		if(ignoreProcess){
			return null;
		}else{
			throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.PROCMAP_GETPROC_003, new String[]{prodNo});
		}
		
	}

	public List<ProcessDef> findProcessByMember(String miNo, Page page) {
		String hql = "select p from ProcessDef p, ProdProcMap m where p.id=m.procId and m.miNo=:miNo and m.mapType=:mapType order by p.desiProdNo";
		HashMap parameterMap = new HashMap(1);
		parameterMap.put("miNo", miNo);
		parameterMap.put("mapType", ProdProcMap.MAP_TYPE_MEMBER_PROC);
		
		List<ProcessDef> list = this.commonDAO.queryByParam(hql, parameterMap, page);
		
		return list;
	}
	public List<Object[]> findProcAndProduct(QueryCondition qc, Page page)
		throws DAOException {
		if (qc == null){
			qc = new QueryCondition();
		}
		qc.addOrder(new OrderBean("proc.desiProdNo"));	
		
		String hql = "select proc, prod from ProcessDef proc ,ProductInfo prod " +
				" where  proc.desiProdNo=prod.prodNo ";
		
		qc.setHql(hql);
		
		List<Object[]>  ls = this.commonDAO.queryByCondition(qc, page);
		
		return ls;
	}

	public List<Object[]> findProcAndProductByMember(String miNo,
			QueryCondition qc, Page page) throws DAOException {
		if (qc == null)
			qc = new QueryCondition();
		
		String hql = "select proc, prod from ProcessDef proc ,ProductInfo prod,  ProdProcMap m " +
				" where proc.id=m.procId and proc.desiProdNo=prod.prodNo ";
		
		qc.setHql(hql);
		qc.addCondition(new ConditionBean("m.miNo", miNo));
		qc.addCondition(new ConditionBean("m.mapType", ProdProcMap.MAP_TYPE_MEMBER_PROC));
		List<Object[]>  ls = this.commonDAO.queryByCondition(qc, page);
		
		return ls;
	}

	public void buildMemberProcess(String miNo, List<Long> procIdList)
			throws ServiceException {
		
		List<ProcessDef> procList = this.findProcessByMember(miNo, null);
		
		ArrayList<Long> beList = new ArrayList<Long>(procIdList);
		ArrayList<Long> unList = new ArrayList<Long>(); 
		for (ProcessDef proc : procList) {
			if (procIdList.contains(proc.getId()))
				beList.remove(proc.getId());
			else
				unList.add(proc.getId());
		}
		if (!unList.isEmpty()){
			this.prodProcMapDAO.deleteMemberProcess(miNo, unList);
			
			List<Long> brchIdList = this.getCommonDAO().find("select id from Branch where miNo = ? ", miNo);;
			for (Long brchId : brchIdList) {//删除所有机构的流程分配
				this.getProdProcMapDAO().deleteBranchProcess(brchId, unList);
			}
			
		}
		
		
		if (!beList.isEmpty()){
			for (Long pid : beList) {
				ProdProcMap map = new ProdProcMap();
				map.setMiNo(miNo);
				map.setProcId(pid);
				this.saveMemberProc(map);
			}
			//
		}
	}

	public void addMemeberProcess(String miNo, List<Long> procIdList) {
		if (procIdList==null || procIdList.isEmpty())
			return;
		
		List<ProcessDef> procList = this.findProcessByMember(miNo, null);
		
		ArrayList<Long> beList = new ArrayList<Long>(procIdList);
		for (ProcessDef proc : procList) {
			if (procIdList.contains(proc.getId()))
				beList.remove(proc.getId());
		}
		
		if (!beList.isEmpty()){
			for (Long pid : beList) {
				ProdProcMap map = new ProdProcMap();
				map.setMiNo(miNo);
				map.setProcId(pid);
				this.saveMemberProc(map);
			}
		}
		
	}

	public void removeMemeberProcess(String miNo, List<Long> procIdList) {
		if (!procIdList.isEmpty()){
			this.prodProcMapDAO.deleteMemberProcess(miNo, procIdList);
		}
		
	}

	public List<ProcessDef> findProcessByBrch(Long brchId, Page page)
			throws DAOException {
		String hql = "select p from ProcessDef p, ProdProcMap m where p.id=m.procId and m.brchId=:brchId and m.mapType=:mapType";
		HashMap parameterMap = new HashMap(1);
		parameterMap.put("brchId", brchId);
		parameterMap.put("mapType", ProdProcMap.MAP_TYPE_BRCH_PROC);
		
		List<ProcessDef> list = this.commonDAO.queryByParam(hql, parameterMap, page);
		
		return list;
	}

	public void buildBranchProcess(Long brchId, List<Long> procIdList)
			throws ServiceException {
		List<ProcessDef> procList = this.findProcessByBrch(brchId, null);
		
		ArrayList<Long> beList = new ArrayList<Long>(procIdList);
		ArrayList<Long> unList = new ArrayList<Long>(); 
		for (ProcessDef proc : procList) {
			if (procIdList.contains(proc.getId()))
				beList.remove(proc.getId());
			else
				unList.add(proc.getId());
		}
		if (!unList.isEmpty()){
			this.prodProcMapDAO.deleteBranchProcess(brchId, unList);
		}
		if (!beList.isEmpty()){
			for (Long pid : beList) {
				ProdProcMap map = new ProdProcMap();
				map.setBrchId(brchId);
				map.setProcId(pid);
				map.setMapType(ProdProcMap.MAP_TYPE_BRCH_PROC);
				this.prodProcMapDAO.save(map);
			}
			//
		}
		
	}

	public void buildDefaultProcess(String miNo, List<Long> pidList)
			throws ServiceException {
		String hql = "from ProdProcMap where  miNo=? and mapType=?";
		List<ProdProcMap> list = this.commonDAO.find(hql, new Object[]{miNo, ProdProcMap.MAP_TYPE_MEMBER_PROC});
		
		for (ProdProcMap o : list) {
			if (pidList.contains(o.getProcId()))
				o.setIsDefault(CommonConst.LOGIC_YES);
			else
				o.setIsDefault(CommonConst.LOGIC_NO);
		}
		
		this.getProdProcMapDAO().saveOrUpdateAll(list);
		
	}

	


}
