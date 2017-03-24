package com.upg.ucars.basesystem.security.core.branch;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.basesystem.audit.core.AuditTaskRevokeResult;
import com.upg.ucars.basesystem.audit.core.IAuditCallBack;
import com.upg.ucars.basesystem.audit.core.IAuditService;
import com.upg.ucars.basesystem.security.core.sysparam.ISysParamService;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.audit.AuditProcess;
import com.upg.ucars.mapping.basesystem.audit.AuditTask;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.ReBrchFunc;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.LogUtil;


public class BranchServiceImpl extends BaseService implements IBranchService ,IAuditCallBack{
	private static final String ERROR_BRCH_FUNC_AUDIT_MUST_UN_CHECK="BRCH_FUNC_CHECK_001";//只能提交未审批的
	private static final String ERROR_BRCH_FUNC_REVOKE_MUST_CHECKING="BRCH_FUNC_CHECK_002";//只能撤销审批中的
	private IBranchDAO brchDAO;
	private IBrchFuncDAO brchFuncDAO;
	private IAuditService auditService;
	private String brchFuncCheckProdNo=null;//机构权限分配审批
	private ISysParamService sysParamService;
	public Long addBrch(Branch branch) throws DAOException, ServiceException {
		if(branch == null) {
			throw ExceptionManager.getException(DAOException.class,	ErrorCodeConst.DB_COMMON_ERROR,	new String[]{"添加对象为空"}, null);
		}
		if(brchDAO.hasSameCode(branch.getBrchNo(), null)){
			throw ExceptionManager.getException(ServiceException.class,	ErrorCodeConst.BRCH_MI_NO_REPEAT);
		}
		processBranchInfos(branch);
		Long id = brchDAO.save(branch);
		//初始化默认审批信息
		auditService.copyDefaultProductInfoAuditRoute(id);
		return id;
	}
	
	private void processBranchInfos( Branch branch ){
		try {
			String parentTreecode = branch.getParentTreeCode();
			List<Branch> tempBrchs = null;
			Long level = 1L;
			if( StringUtils.isNotBlank(parentTreecode) ) {
				tempBrchs = brchDAO.getNextSubBranches(parentTreecode, branch.getMiNo());
				level = Long.valueOf(parentTreecode.length()/4 + 1);
			}else{
				tempBrchs = brchDAO.getHQBranches();
			}
			branch.setBrchLevel(level);
			if( isSetTreeCode(branch) ){
				branch.setTreeCode(StringUtils.trimToEmpty(parentTreecode) + BranchHelper.createTreeCode(tempBrchs));
			}
			
		} catch (Exception e) {
			throw ExceptionManager.getException(ServiceException.class,	ErrorCodeConst.GEN_BRCH_TREE_CODE_ERROR,new String[]{e.getMessage()}, e);
		}
	}
	
	/**
	 * 是否需要重新设置树编码 
	 * 
	 * @param branch
	 * @return
	 */
	private boolean isSetTreeCode(Branch branch){
		boolean ret = true;
		if( branch != null && branch.getBrchId() != null){
			if( branch.getBrchLevel() != null && branch.getBrchLevel().longValue() > 1 ){ //不是总部
				Branch dbBranch = brchDAO.get(branch.getBrchId());
				if( dbBranch != null  ){
					int ind = dbBranch.getTreeCode().indexOf(branch.getParentTreeCode());
					if( ind > -1 && dbBranch.getTreeCode().substring(ind + 1).length() == 4  ){
						ret = false;
					}
				}
			}else{
				ret = false;
			}
		}
		return ret;
	}
	
	private boolean canDeleteBranch(Branch branch){
		boolean ret = true;
		if( branch != null ){
			Long subCount = brchDAO.getSubBranchsCount(branch);
			ret = subCount.longValue() < 1 ;
		}
		return ret;
	}

	public void delBrch(Branch branch) throws DAOException {
		// TODO Auto-generated method stub
		brchDAO.delete(branch);
	}

	public void delBrch(Long brchId) throws DAOException {
		// TODO Auto-generated method stub
		brchDAO.delete(brchId);
	}

	public void editBrch(Branch branch) throws DAOException {
		if(brchDAO.hasSameCode(branch.getBrchNo(), branch.getBrchId())){
			throw ExceptionManager.getException(ServiceException.class,	ErrorCodeConst.BRCH_MI_NO_REPEAT);
		}
		processBranchInfos(branch);
		brchDAO.merge(branch);
	}

	public List<Branch> getBelongMems(String miNo) throws DAOException {
		// TODO Auto-generated method stub
		return brchDAO.getBelongMems(miNo);
	}

	public Branch getBranchByBrchId(Long brchId) throws DAOException {
		return brchDAO.getBranchByBrchId(brchId); 
	}

	public List<ReBrchFunc> getFunctionIds(Long brchId) throws DAOException {
		// TODO Auto-generated method stub
		return brchDAO.getFunctionIds(brchId);
	}

	public List<Sysfunc> getFunctions(Long brchId) throws DAOException {
		// TODO Auto-generated method stub
		return brchDAO.getFunctions(brchId);
	}
	public List<Sysfunc> getAllredCheckedFunctions(Long branchId)
			throws ServiceException, DAOException {
		// TODO Auto-generated method stub
		return brchDAO.getAllredCheckedFunctions(branchId);
	}
	
	
	/**
	 * 获得子机构列表
	 * @param treeCode	    机构树型编码
	 * @param excludeBrchId	不包含机构
	 * @return List<Branch>
	 * @throws DAOException
	 */
	public List<Branch> getSubBranches(String treeCode,Long excludeBrchId) throws DAOException{
		return brchDAO.getSubBranches(treeCode,excludeBrchId);
	}
	
	/**
	 * 根据treecode校验机构是否还有子机构
	 * @param treeCode 
	 * @return true:有 false:无
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public boolean hasSubBranches(String treeCode) throws DAOException,ServiceException{
		Branch branch = new Branch();
		branch.setTreeCode(treeCode);
		Long count  = brchDAO.getSubBranchsCount(branch);
		return count > 0;
	}
	
	
	public void setBrchDAO(IBranchDAO brchDAO) {
		this.brchDAO = brchDAO;
	}

	public void setAuditService(IAuditService auditService) {
		this.auditService = auditService;
	}

	public String getBrchFuncCheckProdNo() {
		return brchFuncCheckProdNo;
	}

	public void setBrchFuncCheckProdNo(String brchFuncCheckProdNo) {
		this.brchFuncCheckProdNo = brchFuncCheckProdNo;
	}

	public void setSysParamService(ISysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}

	public void addBrchs(List<Branch> brchs) throws DAOException {
		List tempBrchs = null;
		Long level = 1L;
		String parentTreecode, tmpTreeCode;
		List<String> treeCodeCols = new ArrayList();
		try {
			for(Branch brch : brchs) {
				parentTreecode = brch.getParentTreeCode();
				if( parentTreecode != null && !"".equals(parentTreecode) ) {
					tempBrchs = brchDAO.getSubBranches(parentTreecode);
					level = Long.valueOf(parentTreecode.length()/4 + 1);
				} else {
					tempBrchs = brchDAO.getHQBranches();
				}
				brch.setBrchLevel(level);
				tmpTreeCode = StringUtils.trimToEmpty(parentTreecode) + BranchHelper.createTreeCode(parentTreecode, tempBrchs, treeCodeCols);
				brch.setTreeCode(tmpTreeCode);
				treeCodeCols.add(tmpTreeCode);
			}
		} catch (Exception e) {
			throw ExceptionManager.getException(ServiceException.class,	ErrorCodeConst.GEN_BRCH_TREE_CODE_ERROR,new String[]{e.getMessage()}, e);
		}
		brchDAO.saveOrUpdateAll(brchs);
		
	}

	public void delBrches(List<Branch> brches) throws DAOException {
		if( brches != null && brches.size() > 0 ){
			boolean canDel = true;
			List<Long> brchIds = new ArrayList<Long>();
			for( int i = 0; i < brches.size(); i++ ){
				Branch b = brches.get(i);
				brchIds.add(b.getBrchId());
				canDel = canDeleteBranch(b);
				if( ! canDel ){
					break;
				}
			}
			//不包含子机构并且不在引用权限则可删除
			if( !canDel)	
				throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.BRANCH_CHANGE_TREECODE_ERROR);
			boolean hasFunc = brchFuncDAO.hasBrchFuncRef(brchIds);
			if(hasFunc)
				throw ExceptionManager.getException(ServiceException.class, ErrorCodeConst.BRANCH_DEL_ERROR_HAS_RIGHT);
			brchDAO.delAll(brches);
		}
		
	}

	public List<Branch> getBelongMemByPage(String miNo, Page page) throws DAOException {
		// TODO Auto-generated method stub
		return brchDAO.getBelongMemByPage(miNo, page);
	}

	public List<Sysfunc> getFunctionByPage(Long brchId, Page page) throws DAOException {
		// TODO Auto-generated method stub
		return brchDAO.getFunctionByPage(brchId, page);
	}

	public List<ReBrchFunc> getFunctionIdByPage(Long brchId, Page page)
			throws DAOException {
		// TODO Auto-generated method stub
		return brchDAO.getFunctionIdByPage(brchId, page);
	}
	
	/**
	 * 按机构id，机构权限状态查询权限
	 * */
	public List<Sysfunc> queryFunc(Long brchId, String brchFuncStatus) throws DAOException,ServiceException{
		return this.brchDAO.queryFunc(brchId, brchFuncStatus);
	}

	
	public List<Branch> findBranch(Branch brch) throws DAOException {
		// TODO Auto-generated method stub
		return brchDAO.findBranch(brch);
	}

	
	public List<Branch> findBranchByPage(Branch brch, Page page) throws DAOException {
		// TODO Auto-generated method stub
		return brchDAO.findBranchByPage(brch, page);
	}

	
	public void addBrchFunc(ReBrchFunc rbf) throws DAOException {
		brchFuncDAO.save(rbf);
		
	}

	
	public void addBrchFuncs(List<ReBrchFunc> brchFuncs) throws DAOException {
		brchFuncDAO.saveOrUpdateAll(brchFuncs);
		
	}

	
	public void delBrchFuncByBrchId(Long brchId) throws DAOException {
		brchFuncDAO.delBrchFuncByBrchId(brchId);
		
	}

	
	public void delBrchFuncs(List<ReBrchFunc> brchFuncs) throws DAOException {
		brchFuncDAO.delAll(brchFuncs);
		
	}

	
	public void editBrchFunc(ReBrchFunc rbf) throws DAOException {
		brchFuncDAO.update(rbf);
		
	}

	
	public void editBrchFuncs(List<ReBrchFunc> brchFuncs) throws DAOException {
		brchFuncDAO.saveOrUpdateAll(brchFuncs);
		
	}

	public void setBrchFuncDAO(IBrchFuncDAO brchFuncDAO) {
		this.brchFuncDAO = brchFuncDAO;
	}

	public List<Branch> getHQBranches() throws DAOException {
		return brchDAO.getHQBranches();
	}
	public List<Branch> findSubBrchs(Branch brch, Page page)
			throws DAOException {
		// TODO Auto-generated method stub
		return brchDAO.findSubBrchs(brch, page);
	}
	
	/**
	 * 获得子机构
	 * @param hasLocalBrch true:包括本机构，false:不包括本机构
	 * @param brch 组合条件信息
	 * @param page 分页信息
	 * @return List<Branch>
	 */
	public List<Branch> findSubBrchs(Branch brch, Page page,boolean hasLocalBrch)throws DAOException{
		return brchDAO.findSubBrchs(brch, page,hasLocalBrch);
	}

	
	public List<Branch> getHQBranches(Page page) throws DAOException {
		// TODO Auto-generated method stub
		return brchDAO.getHQBranches(page);
	}

	public List getHQBranches(Branch branch, Page page) throws DAOException {
		// TODO Auto-generated method stub
		return brchDAO.getHQBranches(branch,page);
	}

	public List getBelongMemByPage(Branch branch, String miNo, Page page) {
		// TODO Auto-generated method stub
		return brchDAO.getBelongMemByPage(branch,miNo, page);
	}
	public Branch getSuperBranch(String treeCode) throws DAOException {
		String parentTreeCode = BranchHelper.getParentTreeCode(treeCode);
		if( StringUtils.isBlank(parentTreeCode) ){
			return null;
		}
		return this.getBrchByTreeCode(parentTreeCode);
	}

	public Branch getSuperBranch(Long brchId) throws DAOException {
		Branch brch = brchDAO.getBranchByBrchId(brchId);
		if(brch != null) {
			return getSuperBranch(brch.getTreeCode());
		}
		return null;
	}

	public List<ReBrchFunc> getFunctionMap(Long brchId) throws DAOException {

		return brchFuncDAO.getBrchFuncByBrchId(brchId);
	}

	public void commitBrchFuncAudit(UserLogonInfo logonInfo, Branch brch) throws ServiceException,
			DAOException {
		String status = brchFuncDAO.getBrchFuncStatus(brch.getBrchId());
		if(!ReBrchFunc.STATUS_ASSIGNNING.equals(status))
			throw ExceptionManager.getException(ServiceException.class,ERROR_BRCH_FUNC_AUDIT_MUST_UN_CHECK);
		Branch branch=brchDAO.get(brch.getBrchId());
//		throw ExceptionManager.getException(ServiceException.class,ErrorCodeConst.BRANCH_AUDIT_ERROR_UN_ASSIGNNING);
//		List<ReBrchFunc> res=getFunctionMap(brch.getBrchId());
//		if(res!=null&&res.size()>0){
//			for(ReBrchFunc re:res){
//				if(!ReBrchFunc.STRUTS_UNCHECK.equals(re.getStatus())){
//					ExceptionManager.throwException(ServiceException.class, ERROR_BRCH_FUNC_AUDIT_MUST_UN_CHECK);
//				}
//			}
//		}
		Long taskId=auditService.commitAuditTask(logonInfo, brchFuncCheckProdNo, branch);
		brchFuncDAO.updateBrchFuncStatusByBrchId(brch.getBrchId(),ReBrchFunc.STATUS_CHECKING);
		LogUtil.getCommonLog().debug("机构权限分配提交审批，任务id:"+taskId);
	}
	
	public void batchCommitBrchFuncAudit(UserLogonInfo logonInfo,List<Long> ids) throws ServiceException,DAOException{
		if(ids != null && ids.size() > 0 ){
			for( Long id : ids ){
				Branch branch = new Branch();
				branch.setBrchId(id);
				commitBrchFuncAudit(logonInfo, branch);
			}
		}
	}

	public void revokeBrchFuncAudit(UserLogonInfo logonInfo, Branch brch) throws ServiceException,
			DAOException {
		String status = brchFuncDAO.getBrchFuncStatus(brch.getBrchId());
		//只有审核中的权限才可以撤销
		if(!ReBrchFunc.STATUS_CHECKING.equals(status))
			throw ExceptionManager.getException(ServiceException.class,ERROR_BRCH_FUNC_REVOKE_MUST_CHECKING);
		Branch branch=brchDAO.get(brch.getBrchId());
//		List<ReBrchFunc> res=getFunctionMap(brch.getBrchId());
//		if(res!=null&&res.size()>0){
//			for(ReBrchFunc re:res){
//				if(!ReBrchFunc.STRUTS_CHECKING.equals(re.getStatus())){
//					ExceptionManager.throwException(ServiceException.class, ERROR_BRCH_FUNC_REVOKE_MUST_CHECKING);
//				}
//			}
//		}
		AuditTaskRevokeResult revokeResult=auditService.revokeAuditTask(logonInfo,brchFuncCheckProdNo,branch);
		if(revokeResult.isRevokePass()){
//			brchFuncDAO.updateBrchFuncStatusByBrchId(brch.getBrchId(),ReBrchFunc.STRUTS_UNCHECK);
			brchFuncDAO.updateBrchFuncStatusByBrchId(brch.getBrchId(),ReBrchFunc.STATUS_ASSIGNNING);
		}
		LogUtil.getCommonLog().debug("机构权限分配撤销审批，撤销结果"+revokeResult.isRevokePass());
		
	}
	
	public void batchRevokeBrchFuncAudit(UserLogonInfo logonInfo,List<Long> ids)throws ServiceException,DAOException{
		if(ids != null && ids.size() > 0 ){
			for( Long id : ids ){
				Branch branch = new Branch();
				branch.setBrchId(id);
				revokeBrchFuncAudit(logonInfo, branch);
			}
		}
	}
	
	
	public void auditCallBack(AuditTask task, List<AuditProcess> processList)
			throws Exception {
		if(task!=null){
			String prodNo=task.getProdNo();
			if(AuditTask.STATUS_AUDIT_PASS.equals(task.getAuditStatus())){
				if(brchFuncCheckProdNo.equals(prodNo)){
//					brchFuncDAO.updateBrchFuncStatusByBrchId(task.getAuditEntityId(),ReBrchFunc.STRUTS_CHECK);
					brchFuncDAO.updateBrchFuncStatusByBrchId(task.getAuditEntityId(),ReBrchFunc.STATUS_CHECKED);
				}
			}else{
				if(brchFuncCheckProdNo.equals(prodNo)){
//					brchFuncDAO.updateBrchFuncStatusByBrchId(task.getAuditEntityId(),ReBrchFunc.STRUTS_UNCHECK);
					brchFuncDAO.updateBrchFuncStatusByBrchId(task.getAuditEntityId(),ReBrchFunc.STATUS_ASSIGNNING);
				}
			}
			
		}

	}

	public AuditTask findBrchFuncAuditTask(UserLogonInfo logonInfo, Long brchId)
			throws ServiceException, DAOException {
		
		return auditService.getLastAuditTask(brchFuncCheckProdNo,brchId, Branch.class);
	}

	public void addFunc(Long brchId, List<Long> funcIdList)
			throws ServiceException, DAOException {
		Branch brch=brchDAO.get(brchId);
		boolean flag = isCheckBrchFunc(brch);
		List<ReBrchFunc> list=new ArrayList<ReBrchFunc>();
		for(Long funcId:funcIdList){
			ReBrchFunc re=brchFuncDAO.findByBrchIdAndFuncId(brchId, funcId);
			if(re==null){
				re=new ReBrchFunc();
				if(flag){
					//re.setStatus(ReBrchFunc.STRUTS_UNCHECK);
					re.setStatus(ReBrchFunc.STATUS_ASSIGNNING);
				}else{
//					re.setStatus(ReBrchFunc.STRUTS_CHECK);
					re.setStatus(ReBrchFunc.STATUS_CHECKED);
				}
				re.setBrchId(brchId);
				re.setFuncId(funcId);
				list.add(re);
			}
			
		}
		if(list.size()>0){
			setStatusByBranchId(brch);
			brchFuncDAO.saveOrUpdateAll(list);
		}
		
	}
	/**
	 * 
	 * 机构权限分配是否需要审核
	 *
	 * @param branch
	 * @return
	 */
	private boolean isCheckBrchFunc(Branch branch){
		boolean ret = false;
		if( branch != null){
			if( branch.getBrchLevel() != null && branch.getBrchLevel().longValue() > 1 ){
				ret = sysParamService.isCheckBrchFunc(branch.getMiNo());
			}
		}
		return ret;
	}
	
	/**
	 * 
	 * 重新设置
	 *
	 * @param branchId
	 */
	private void setStatusByBranchId(Branch branch){
		boolean isCheck = isCheckBrchFunc(branch);
//		brchFuncDAO.updateBrchFuncStatusByBrchId(branch.getBrchId(), isCheck ? ReBrchFunc.STRUTS_UNCHECK : ReBrchFunc.STRUTS_CHECK );
		brchFuncDAO.updateBrchFuncStatusByBrchId(branch.getBrchId(), isCheck ? ReBrchFunc.STATUS_ASSIGNNING : ReBrchFunc.STATUS_CHECKED );
	}

	public void removeFunc(Long brchId, List<Long> funcIdList)
			throws ServiceException, DAOException {
		List<ReBrchFunc> rList=new ArrayList<ReBrchFunc>();
		for (Long unFuncId : funcIdList ){//删除关联
			ReBrchFunc re=brchFuncDAO.findByBrchIdAndFuncId(brchId,unFuncId);
			if(re!=null){
				rList.add(re);
			}
		}
		if( rList != null && rList.size() > 0 ){
			brchFuncDAO.delAll(rList);
			Branch brch=brchDAO.get(brchId);
			setStatusByBranchId(brch);
		}
	}

	public List<Branch> getParentBranchList(Long branchId) throws DAOException,ServiceException {
		List<Branch> ret = null;
		if( branchId != null ){
			Branch branch = brchDAO.getBranchByBrchId(branchId);
			if(branch != null){
				List<String> treeCodeList = BranchHelper.getAllPreTreeCode(branch.getTreeCode()); 
				ret =  brchDAO.getBranchListByTreeCodeList(treeCodeList,branch.getMiNo());
				if( ret != null && ret.size() > 0 ){
					Collections.sort(ret,new Comparator<Branch>() {
						public int compare(Branch o1, Branch o2) {
							return o1.getBrchLevel().compareTo(o2.getBrchLevel());
						}
						
					});
				}
			}
		}
		return ret == null ? new ArrayList<Branch>(0) : ret ;
	}

	/**
	 * 获取机构的权限状态
	 * @param branchId
	 * @return String 0:未分配,1:分配未审核或是分配中,2:已审核,3:审核中
	 * @throws DAOException
	 * @throws ServiceException
	 */
	public String getBrchFuncStatus(Long branchId) throws DAOException,ServiceException{
		return brchFuncDAO.getBrchFuncStatus(branchId);
	}
	
	
	public Branch getBranch(String miNo, String brchNo){
		Branch ret = null;
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>(2);
		conditionList.add(new ConditionBean(Branch.BRCH_NO,brchNo));
		conditionList.add(new ConditionBean(Branch.MI_NO,miNo));
		List<Branch> list = brchDAO.queryEntity(conditionList, null);
		if( list != null && list.size() == 1 ){
			ret = list.get(0);
		}
		return ret;
	}
	
	public Branch getBrchByTreeCode(String treeCode){
		return getBrchByTreeCodeAndMiNo(treeCode, null);
	}
	
	public Branch getBrchByTreeCodeAndMiNo(String treeCode,String miNo){
		Branch ret = null;
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>(2);
		conditionList.add(new ConditionBean(Branch.TREE_CODE,treeCode));
		if( StringUtils.isNotBlank(miNo)){
			conditionList.add(new ConditionBean(Branch.MI_NO,miNo));
		}
		List<Branch> list = brchDAO.queryEntity(conditionList, null);
		if( list != null && list.size() == 1 ){
			ret = list.get(0);
		}
		return ret;
	}
	
	public List<Branch> getSubBrchByPage(String treeCode, Page page){
		return getSubBranchList(treeCode, null, page);
	}
	
	public List<Branch> getSubBranchList(String treeCode,String miNo, Page page){
		return brchDAO.getSubBranchList(treeCode, miNo, page);
	}
	
	public List<Branch> getSubBranches(String treeCode){
		return getSubBranchList(treeCode, null, null);
	}
	
	public List<Branch> getSubBranchList(String treeCode,String miNo){
		return getSubBranchList(treeCode, miNo,null);
	}

	public Branch getHQBranchByMino(String miNo) throws DAOException {
		return brchDAO.getHQBranch(miNo);
	}
	
	public void copyBrchFunc(Long brchId,List<Long> destBranchIds){
		if (brchId != null && destBranchIds != null && destBranchIds.size() > 0) {
			List<ReBrchFunc> srcRbfList = getFunctionIds(brchId);
			Set<Long> destBranchs = new HashSet<Long>();
			for (Long destId : destBranchIds) {
				Branch branch = getBranchByBrchId(destId);
				List<Branch> branchList = getSubBranchList(branch.getTreeCode(), branch.getMiNo());
				for (Branch b : branchList) {
					destBranchs.add(b.getBrchId());
				}
			}
			
			destBranchs.remove(brchId);
			
			
			for (Long destBrchId : destBranchs) {
				
				List<ReBrchFunc> destRbfList = getFunctionIds(destBrchId);
				
				List<ReBrchFunc> addRbfList = new ArrayList<ReBrchFunc>();
				
				for (ReBrchFunc srcRbf : srcRbfList) {
					boolean isFind = false;
					for (int i = 0; i <destRbfList.size(); i++) {
						ReBrchFunc destRbf = destRbfList.get(i);
						if (srcRbf.getFuncId().equals(destRbf.getFuncId())) {
							destRbfList.remove(i);
							isFind = true;
							break;
						}
					}
					if (!isFind) {
						ReBrchFunc rbf = new ReBrchFunc();
						rbf.setBrchId(destBrchId);
						rbf.setFuncId(srcRbf.getFuncId());
						rbf.setStatus(srcRbf.getStatus());
						addRbfList.add(rbf);
					}
				}
				
				if (destRbfList != null && destRbfList.size() > 0) {
					delBrchFuncs(destRbfList);
				}
				if (addRbfList != null && addRbfList.size() > 0) {
					addBrchFuncs(addRbfList);
				}
			}
			
		}
	}

	@Override
	public List<Long> getSubBranchIds(Long brchId) {
		List<Long> result = new ArrayList<Long>();
		if (brchId != null) {
			Branch branch = getBranchByBrchId(brchId);
			if (branch != null) {
				result.add(brchId);
				String treeCode = branch.getTreeCode();
				String miNo = branch.getMiNo();
				List<Branch> subBranchList = getSubBranchList(treeCode, miNo);
				if (subBranchList != null) {
					for (Iterator<Branch> it = subBranchList.iterator();it.hasNext();) {
						Branch child = it.next();
						result.add(child.getBrchId());
					}
				}
			}
		}
		return result;
	}
}
