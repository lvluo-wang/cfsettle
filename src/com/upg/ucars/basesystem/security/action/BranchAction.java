package com.upg.ucars.basesystem.security.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.Action;
import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.basesystem.security.core.branch.BranchHelper;
import com.upg.ucars.basesystem.security.core.branch.IBranchService;
import com.upg.ucars.basesystem.security.core.member.IMemberService;
import com.upg.ucars.basesystem.security.core.subsystem.ISubsystemService;
import com.upg.ucars.basesystem.security.core.sysfunc.ISysfuncService;
import com.upg.ucars.basesystem.security.core.sysparam.ISysParamService;
import com.upg.ucars.basesystem.security.core.user.UserManager;
import com.upg.ucars.constant.CodeKey;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.factory.JQueryTreeNodeManager;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.audit.AuditTask;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.MemberInfo;
import com.upg.ucars.mapping.basesystem.security.ReBrchFunc;
import com.upg.ucars.mapping.basesystem.security.Subsystem;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.model.JQueryTreeNode;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.StringUtil;

/**
 * 功能说明：机构action
 * 
 * @author shentuwy
 * 
 */
public class BranchAction extends BaseAction {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3243652383755661707L;
	/** 机构信息列表 */
	private List<Branch>		records				= new ArrayList<Branch>();
	/** 机构信息 */
	private Branch				brch				= new Branch();
	/** 用户直属机构 */
	private List<Branch>		subBrchs;
	/** 功能权限列表 */
	private List<Sysfunc>		funcs;
	/** 上级权限id */
	private String				parentFuncId;
	/** 机构服务 */
	private IBranchService		branchService;
	/** 权限服务 */
	private ISysfuncService		sysfuncService;
	/** 参数服务 */
	private ISysParamService	sysParamService;
	/** 子系统服务 */
	private ISubsystemService	subsystemService;
	/** 接入点服务 */
	private IMemberService		memberService;

	private boolean				hasSubBranches		= false;

	private String				beIds;											// 设置的Sysfunc.funcId
	private String				unIds;											// 未设置的Sysfunc.funcId
	private String				isSaas;
	private String				unJSONInfo;
	private String				beJSONInfo;
	private List<Code>			brchStatus;
	private String				funcIds;										;
	private Subsystem			subsystem;
	private String				subsysIds;
	private AuditTask			auditTask;
	private List<Code>			miType;
	
	private boolean 			miNoControl 		= true;
	
	private List<Code>			ledgerAttrs;

	public List<Code> getMiType() {
		return miType;
	}

	public void setMiType(List<Code> miType) {
		this.miType = miType;
	}

	public List<Code> getLedgerAttrs() {
		return ledgerAttrs;
	}

	public Subsystem getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(Subsystem subsystem) {
		this.subsystem = subsystem;
	}

	public void setSubsystemService(ISubsystemService subsystemService) {
		this.subsystemService = subsystemService;
	}

	/**
	 * 录入/编辑
	 */
	public String input() {
		ledgerAttrs = DictionaryUtil.getCodesByKey(CodeKey.BRANCH_LEDGER_ATTR);
		if (StringUtils.isNotBlank(getId())) {
			brch = branchService.getBranchByBrchId(Long.valueOf(getId()));
			if (brch != null && brch.getTreeCode() != null) {
				Branch parentBranch = branchService.getSuperBranch(brch
						.getBrchId());
				if (parentBranch != null) {
					brch.setParentBrchName(parentBranch.getBrchName());
					brch.setParentTreeCode(parentBranch.getTreeCode());
				}
			}
			hasSubBranches = branchService.hasSubBranches(brch.getTreeCode());
		} else {
			brch = new Branch();
			UserLogonInfo uli = SessionTool.getUserLogonInfo();
			if (uli.getBranchId() != null && uli.getBranchId() != 0) {
				Branch pBrch = branchService.getBranchByBrchId(uli
						.getBranchId());
				if (pBrch != null) {
					brch.setParentBrchName(pBrch.getBrchName());
					brch.setParentTreeCode(pBrch.getTreeCode());
					brch.setMiNo(uli.getMiNo());
				}
			}
		}
		return Action.INPUT;
	}

	/**
	 * 列表信息
	 */
	public String list() {
		UserLogonInfo logonInfo = SessionTool.getUserLogonInfo();
		String miNo = logonInfo.getMiNo();
		if (miNo != null) {
			if (sysParamService.isCheckBrchFunc(miNo)) {
				showAudit = "1";
			}
		}
		if (UserManager.isSaasMaintenance() || UserManager.isSaasManager()) {
			isSaas = "1";
		} else {
			isSaas = "0";
		}
		return "list";
	}

	/**
	 * 机构选择
	 */
	public String selectBrch() {
		return "selectBrch";
	}

	public String toCHooseBrch() {
		return "chooseBrch";
	}

	public String query() {
		UserLogonInfo uli = SessionTool.getUserLogonInfo();
		getPg().setCurrentPage(getPage());
		getPg().setPageSize(getRows());
		if (Buser.TYPE_SAAS_MANAGER.equals(uli.getUserType())) {
			// SaaS管理员，可维护所有总部机构
			records = branchService.getHQBranches(brch, getPg());

		}
		if (Buser.TYPE_BRCH_GLOBAL_MANAGER.equals(uli.getUserType())
				|| Buser.TYPE_BRCH_LOCAL_MANAGER.equals(uli.getUserType())) {
			if (uli.getBranchId() != null) {
				Branch usrBrch = branchService.getBranchByBrchId(uli
						.getBranchId());
				// records
				// =branchService.getSubBrchByPage(usrBrch.getTreeCode(),
				// getPg());
				Branch queryBrch = new Branch();
				queryBrch.setTreeCode(usrBrch.getTreeCode());
				if (brch != null) {
					if (brch.getTreeCode() != null
							&& brch.getTreeCode().trim().length() > 0) {
						queryBrch.setTreeCode(brch.getTreeCode().trim());
					}
					if (brch.getBrchName() != null
							&& brch.getBrchName().trim().length() > 0) {
						queryBrch.setBrchName(brch.getBrchName().trim());
					}
					if (brch.getBrchNo() != null
							&& brch.getBrchNo().trim().length() > 0) {
						queryBrch.setBrchNo(brch.getBrchNo().trim());
					}
				}
				records = branchService.findSubBrchs(queryBrch, getPg());
			}
		}
		for (Branch brch : records) {
			String parentTreeCode = BranchHelper.getParentTreeCode(brch
					.getTreeCode());
			if (!"".equals(parentTreeCode)) {
				Branch parentBranch = branchService
						.getBrchByTreeCode(parentTreeCode);
				if (parentBranch != null) {
					// 上级机构名称
					brch.setParentBrchName(parentBranch.getBrchName());
				}
			}
			// 设置接入名称
			MemberInfo memberInfo = memberService
					.findMemberInfo(brch.getMiNo());
			if (memberInfo != null)
				brch.setMiName(memberInfo.getMiName());
			// 下面代码统一改为这行
			brch.setFuncStatus(branchService.getBrchFuncStatus(brch.getBrchId()));
			// 这个方法可以修正，因效率不高
			// List<ReBrchFunc> re =
			// branchService.getFunctionMap(brch.getBrchId());
			// //设为未分配
			// //brch.setFuncStatus(Branch.FUNC_STATUS_UN_ASSIGN);
			// brch.setFuncStatus(ReBrchFunc.STRUTS_UN_ASSIGN);
			// if(re!=null&&re.size()>0){
			// 设为分配中
			// brch.setFuncStatus(Branch.FUNC_STATUS_ASSIGNED);
			// brch.setFuncStatus(ReBrchFunc.STRUTS_ASSIGNNING);
			// ReBrchFunc rbf = (ReBrchFunc)re.get(0);
			// brch.setFuncStatus(rbf.getStatus());
			// for(ReBrchFunc bfunc : re){
			// if(ReBrchFunc.STRUTS_UNCHECK.equals(bfunc.getStatus())){
			// brch.setFuncStatus(Branch.FUNC_STATUS_ASSIGN_AUDITING);
			// }
			// if(ReBrchFunc.STRUTS_CHECKING.equals(bfunc.getStatus())){
			// brch.setFuncStatus(Branch.FUNC_STATUS_AUDITING);
			// }
			// }
			// }
		}
		return setDatagridInputStreamData(records, getPg());
	}

	/**
	 * 上级机构查询 机构总部管理员，机构分支管理员创建，修改机构功能中，选择上级机构时使用
	 */
	public String superiorQuery() {
		UserLogonInfo uli = SessionTool.getUserLogonInfo();
		getPg().setCurrentPage(getPage());
		getPg().setPageSize(getRows());
		// if(Buser.TYPE_SAAS_MANAGER.equals(uli.getUserType())) {
		// //SaaS管理员，可维护所有总部机构
		// records = branchService.getHQBranches(brch,getPg());
		// }
		if (Buser.TYPE_BRCH_GLOBAL_MANAGER.equals(uli.getUserType())
				|| Buser.TYPE_BRCH_LOCAL_MANAGER.equals(uli.getUserType())) {
			if (uli.getBranchId() != null) {
				Branch usrBrch = branchService.getBranchByBrchId(uli
						.getBranchId());
				Branch queryBrch = new Branch();
				queryBrch.setTreeCode(usrBrch.getTreeCode());
				if (brch != null) {
					if (brch.getTreeCode() != null
							&& brch.getTreeCode().trim().length() > 0) {
						queryBrch.setTreeCode(brch.getTreeCode().trim());
					}
					if (brch.getBrchName() != null
							&& brch.getBrchName().trim().length() > 0) {
						queryBrch.setBrchName(brch.getBrchName().trim());
					}
					if (brch.getBrchNo() != null
							&& brch.getBrchNo().trim().length() > 0) {
						queryBrch.setBrchNo(brch.getBrchNo().trim());
					}
					if (this.getId() != null && !"null".equals(this.getId())
							&& !"".equals(this.getId())) {
						queryBrch.setBrchId(Long.valueOf(this.getId()));
					}
				}
				records = branchService.findSubBrchs(queryBrch, getPg(), true);
			}
		}
		for (Branch brch : records) {
			String parentTreeCode = BranchHelper.getParentTreeCode(brch
					.getTreeCode());
			if (!"".equals(parentTreeCode)) {
				Branch parentBranch = branchService
						.getBrchByTreeCode(parentTreeCode);
				if (parentBranch != null) {
					// 上级机构名称
					brch.setParentBrchName(parentBranch.getBrchName());
				}
			}
			// 设置接入名称
			MemberInfo memberInfo = memberService
					.findMemberInfo(brch.getMiNo());
			if (memberInfo != null)
				brch.setMiName(memberInfo.getMiName());
			brch.setFuncStatus(branchService.getBrchFuncStatus(brch.getBrchId()));
		}
		return setDatagridInputStreamData(records, getPg());
	}

	/**
	 * 保存机构信息
	 */
	public void save() {
		if (brch.getBrchId() != null && brch.getBrchId() != 0) {
			branchService.editBrch(brch);
		} else {
			branchService.addBrch(brch);
		}
	}

	/**
	 * 删除
	 */
	public void del() {
		if (getIds() == null || "".equals(getIds()))
			return;
		List<Branch> delCols = new ArrayList<Branch>();
		for (String id : getIds().split(",")) {
			Branch b = branchService.getBranchByBrchId(Long.valueOf(id));
			delCols.add(b);
		}
		branchService.delBrches(delCols);
	}

	/**
	 * 进入机构权限分配页面
	 */
	public String brchPopedom() {
		if (UserManager.isSaasMaintenance() || UserManager.isSaasManager()) {
			isSaas = "1";
		} else {
			isSaas = "0";
		}
		// 如果是审核中的权限,不允许设置权限
		String status = branchService.getBrchFuncStatus(brch.getBrchId());
		if (ReBrchFunc.STATUS_CHECKING.equals(status))
			throw ExceptionManager.getException(ServiceException.class,
					ErrorCodeConst.BRANCH_SET_RIGHT_CHECKING);
		return "brchPopedom";
	}

	public String assignSubsys() {
		return "assignSubsys";
	}

	/**
	 * 保存机构权限
	 */
	public void saveBrchFunc() {
		// 如果是审核中的权限,不允许设置权限
		String status = branchService.getBrchFuncStatus(brch.getBrchId());
		if (ReBrchFunc.STATUS_CHECKING.equals(status))
			throw ExceptionManager.getException(ServiceException.class,
					ErrorCodeConst.BRANCH_SET_RIGHT_CHECKING);
		List<Long> beFuncIdList = new ArrayList<Long>();
		brch = branchService.getBranchByBrchId(brch.getBrchId());
		String[] ids = this.getBeIds().split(",");
		for (String id : ids) {
			if (!StringUtil.isEmpty(id))
				beFuncIdList.add(Long.valueOf(id));
		}
		// 删除原有权限
		branchService.delBrchFuncByBrchId(brch.getBrchId());

		List<ReBrchFunc> brchFuncs = new ArrayList<ReBrchFunc>();
		for (Long funcId : beFuncIdList) {
			ReBrchFunc rbf = new ReBrchFunc();
			rbf.setBrchId(brch.getBrchId());
			rbf.setFuncId(funcId);
			// 默认为已审核
			// rbf.setStatus(ReBrchFunc.STRUTS_CHECK);
			rbf.setStatus(ReBrchFunc.STATUS_CHECKED);
			if (!"1".equals(brch.getBrchLevel() + "")) {
				// if(SysParam.isBrchFuncMustCheck(brch.getMiNo())){
				if (sysParamService.isCheckBrchFunc(brch.getMiNo())) {
					// rbf.setStatus(ReBrchFunc.STRUTS_UNCHECK);
					// 设置为分配中
					rbf.setStatus(ReBrchFunc.STATUS_ASSIGNNING);
				}
			}
			brchFuncs.add(rbf);
		}
		branchService.addBrchFuncs(brchFuncs);
	}

	public String changeSubsysMode() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Subsystem> allSubsysList = subsystemService.findOpenedSubsystem();
		List<JQueryTreeNode> nodes = new ArrayList<JQueryTreeNode>();
		for (Subsystem subsys : allSubsysList) {
			JQueryTreeNode node = new JQueryTreeNode();
			node.setId(subsys.getSubsysId() + "");
			node.setText(subsys.getSubsysName());
			node.setIconCls("icon-system");
			nodes.add(node);
		}
		map.put("subsys", nodes);
		return setInputStreamData(map);
	}

	public String toChangeSubsysMode() {
		return "changeSubsysMode";
	}

	public String querySubsysFunc() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Long> sysIdList = new ArrayList<Long>();
		if (subsysIds != null) {
			String[] ids = subsysIds.split(",");
			for (String id : ids) {
				if (!StringUtil.isEmpty(id))
					sysIdList.add(Long.valueOf(id));
			}
			List<Sysfunc> funclist = subsystemService
					.findExistingFunc(sysIdList);
			// 已分配权限
			List<Sysfunc> beFuncList = branchService
					.getAllredCheckedFunctions(brch.getBrchId());
			map = sysfuncService.getTwoTree(funclist, beFuncList);
		}
		return setInputStreamData(map);
	}

	public String brchFuncTree() {
		Map<String, Object> map = new HashMap<String, Object>();
		// 可分配权限
		List<Sysfunc> allFuncList = null;
		UserLogonInfo logoner = SessionTool.getUserLogonInfo();
		Long uBrchId = logoner.getBranchId();
		if (uBrchId != null) {
			allFuncList = branchService.getFunctions(uBrchId);
		} else {
			allFuncList = sysfuncService.querySysfunc(new QueryComponent(),
					null);
			if (allFuncList != null && allFuncList.size() > 0) {
				// TODO 临时使用
				List<Sysfunc> tmp = new ArrayList<Sysfunc>();
				Map<Long, Sysfunc> m = new HashMap<Long, Sysfunc>();
				for (Iterator<Sysfunc> it = allFuncList.iterator(); it
						.hasNext();) {
					Sysfunc sf = it.next();
					String useType = sf.getUseType();
					if (StringUtils.equals("1", useType)
							|| StringUtils.equals("2", useType)) {
						m.put(sf.getFuncId(), sf);
					} else {
						tmp.add(sf);
					}
				}
				if (tmp != null && tmp.size() > 0) {
					List<Sysfunc> needAddList = new ArrayList<Sysfunc>();
					for (Iterator<Sysfunc> it = tmp.iterator(); it.hasNext();) {
						Sysfunc sf = it.next();
						processAddSysfunc(sf, m, needAddList);
					}
					tmp.addAll(0, needAddList);
				}
				allFuncList = tmp;
			}
		}
		// 已分配权限
		List<Sysfunc> beFuncList = branchService.getFunctions(brch.getBrchId());
		map = sysfuncService.getTwoTree(allFuncList, beFuncList);
		return setInputStreamData(map);
	}

	/**
	 * 
	 * 增加父节点
	 * 
	 * @param func
	 * @param map
	 * @param list
	 */
	private void processAddSysfunc(Sysfunc func, Map<Long, Sysfunc> map,
			List<Sysfunc> list) {
		if (func != null && map != null && map.size() > 0 && list != null) {
			Sysfunc sf = map.get(func.getParentFuncId());
			if (sf != null && !list.contains(sf)) {
				list.add(sf);
				processAddSysfunc(sf, map, list);
			}
		}
	}

	/**
	 * 添加权限
	 * 
	 * @return
	 */
	public String addFunc() {
		if (funcIds != null) {
			List<Long> funcIdList = new ArrayList<Long>();

			String[] ids = funcIds.split(",");
			for (String id : ids) {
				if (!StringUtil.isEmpty(id))
					funcIdList.add(Long.valueOf(id));
			}
			branchService.addFunc(brch.getBrchId(), funcIdList);
		}
		return brchFuncTree();
	}

	/**
	 * 删除权限
	 * 
	 * @return
	 */
	public String delFunc() {
		if (funcIds != null) {
			List<Long> funcIdList = new ArrayList<Long>();

			String[] ids = funcIds.split(",");
			for (String id : ids) {
				if (!StringUtils.isEmpty(id)) {
					funcIdList.add(Long.valueOf(id));
				}
			}
			branchService.removeFunc(brch.getBrchId(), funcIdList);
		}
		return brchFuncTree();
	}

	/**
	 * 添加权限
	 * 
	 * @return
	 */
	public String addFuncBySys() {
		if (funcIds != null) {
			List<Long> funcIdList = new ArrayList<Long>();

			String[] ids = funcIds.split(",");
			for (String id : ids) {
				if (!StringUtil.isEmpty(id))
					funcIdList.add(Long.valueOf(id));
			}
			branchService.addFunc(brch.getBrchId(), funcIdList);
		}
		return querySubsysFunc();
	}

	/**
	 * 删除权限
	 * 
	 * @return
	 */
	public String delFuncBySys() {
		if (funcIds != null) {
			List<Long> funcIdList = new ArrayList<Long>();

			String[] ids = funcIds.split(",");
			for (String id : ids) {
				if (!StringUtil.isEmpty(id))
					funcIdList.add(Long.valueOf(id));
			}
			branchService.removeFunc(brch.getBrchId(), funcIdList);
		}
		return querySubsysFunc();
	}

	public String brchFuncAudit() {
		brch = branchService.getBranchByBrchId(getAuditEntityId());
		// 已分配权限
		List<Sysfunc> beFuncList = branchService.getFunctions(brch.getBrchId());
		this.setBeJSONInfo(getJsonData(Sysfunc.buildOpenTree(beFuncList)));
		return "brchFuncAudit";
	}

	/**
	 * 
	 * 获取机构树数据
	 * 
	 * @return
	 */
	public String getBranchTree() {
		List<Map<String, Object>> branchList = null;
		String branchId = getId();
		if (StringUtils.isNotBlank(branchId)) {
			branchList = getChildBranch(Long.valueOf(branchId));
		} else {
			branchList = getBranchTreeRoot(false);
		}
		return setInputStreamData(branchList);
	}

	/**
	 * 
	 * 获取机构数第一和第二级
	 * 
	 * @return
	 */
	private List<Map<String, Object>> getBranchTreeRoot(boolean isControl) {
		List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
		UserLogonInfo uli = SessionTool.getUserLogonInfo();
		Long branchId = uli.getBranchId();
		if ( isControl && branchId != null) {
			Branch branch = branchService.getBranchByBrchId(branchId);
			List<Branch> root = new ArrayList<Branch>(1);
			root.add(branch);
			ret.addAll(JQueryTreeNodeManager.convertListToJTNMap(root));
			ret.get(0).put(JQueryTreeNode.CHILDREN,
					getChildBranch(branch.getBrchId()));
		} else {
//			Map<String, Object> root = JQueryTreeNodeManager
//					.getBranchRootJQueryTreeMap();
			List<Branch> rootList = new ArrayList<Branch>();
			if (!miNoControl || StringUtils.isBlank(uli.getMiNo())) {
				rootList.addAll(branchService.getHQBranches());
			}else{
				rootList.add(branchService.getHQBranchByMino(uli.getMiNo()));
			}
			List<Map<String, Object>> childs = JQueryTreeNodeManager
					.convertListToJTNMap(rootList);
			addChildAttr(childs);
//			root.put(JQueryTreeNode.CHILDREN, childs);
			ret.addAll(childs);
		}
		if (ret != null && ret.size() > 0) {
//			ret.get(0).put(JQueryTreeNode.STATE, JQueryTreeNode.STATE_OPEN);
		}
		return ret;
	}

	/**
	 * 
	 * 获取下一机构节点列表
	 * 
	 * @param branchId
	 * @return
	 */
	private List<Map<String, Object>> getChildBranch(Long branchId) {
		List<Map<String, Object>> ret = null;
		if (branchId != null) {
			Branch branch = branchService.getBranchByBrchId(branchId);
			if (branch != null) {
				Branch queryBranch = new Branch();
				queryBranch.setBrchLevel(branch.getBrchLevel() + 1);
				queryBranch.setTreeCode(branch.getTreeCode());
				ret = JQueryTreeNodeManager.convertListToJTNMap(branchService
						.findSubBrchs(queryBranch, null, false));
				addChildAttr(ret);
			}
		}
		return ret;
	}

	/**
	 * 
	 * 增加子节点属性
	 * 
	 */
	private void addChildAttr(List<Map<String, Object>> list) {
		if (list != null && list.size() > 0) {
			for (Map<String, Object> m : list) {
				if (branchService.hasSubBranches(String
						.valueOf(((Map<String, Object>) m
								.get(JQueryTreeNode.ATTRIBUTES))
								.get("treeCode")))) {
					m.put(JQueryTreeNode.CHILDREN, Collections.EMPTY_LIST);
					m.put(JQueryTreeNode.STATE, JQueryTreeNode.STATE_CLOSED);
				}
			}
		}
	}

	public String getParentBranchs() {
		List<Branch> superBranchList = branchService.getParentBranchList(brch
				.getBrchId());
		return setInputStreamData(superBranchList);
	}

	public void commitBrchFuncAudit() {
		UserLogonInfo logonInfo = SessionTool.getUserLogonInfo();
		branchService.commitBrchFuncAudit(logonInfo, brch);
	}

	public void batchCommitBrchFuncAudit() {
		UserLogonInfo logonInfo = SessionTool.getUserLogonInfo();
		branchService.batchCommitBrchFuncAudit(logonInfo, getIdList());
	}

	public void revokeBrchFuncAudit() {
		UserLogonInfo logonInfo = SessionTool.getUserLogonInfo();
		branchService.revokeBrchFuncAudit(logonInfo, brch);
	}

	public void batchRevokeBrchFuncAudit() {
		UserLogonInfo logonInfo = SessionTool.getUserLogonInfo();
		branchService.batchRevokeBrchFuncAudit(logonInfo, getIdList());
	}

	public String viewBrchFuncAuditProcess() {
		setAuditTask(branchService.findBrchFuncAuditTask(
				SessionTool.getUserLogonInfo(), brch.getBrchId()));
		return VIEW_AUDIT_PROCESS;
	}

	public String toCopyBrchFunc(){
		brchFuncAudit();
		return "toCopyBrchFunc";
	}
	
	public void doCopyBrchFunc(){
		List<Long> destBranchIds = getIdList();
		branchService.copyBrchFunc(brch.getBrchId(), destBranchIds);
	}
	
	public List<Branch> getRecords() {
		return records;
	}

	public void setRecords(List<Branch> records) {
		this.records = records;
	}

	public Branch getBrch() {
		return brch;
	}

	public void setBrch(Branch brch) {
		this.brch = brch;
	}

	public void setBranchService(IBranchService branchService) {
		this.branchService = branchService;
	}

	public List<Branch> getSubBrchs() {
		return subBrchs;
	}

	public void setSubBrchs(List<Branch> subBrchs) {
		this.subBrchs = subBrchs;
	}

	public List<Sysfunc> getFuncs() {
		return funcs;
	}

	public void setFuncs(List<Sysfunc> funcs) {
		this.funcs = funcs;
	}

	public void setSysfuncService(ISysfuncService sysfuncService) {
		this.sysfuncService = sysfuncService;
	}

	public String getParentFuncId() {
		return parentFuncId;
	}

	public void setParentFuncId(String parentFuncId) {
		this.parentFuncId = parentFuncId;
	}

	public String getBeIds() {
		return beIds;
	}

	public void setBeIds(String beIds) {
		this.beIds = beIds;
	}

	public String getUnIds() {
		return unIds;
	}

	public void setUnIds(String unIds) {
		this.unIds = unIds;
	}

	public String getUnJSONInfo() {
		return unJSONInfo;
	}

	public void setUnJSONInfo(String unJSONInfo) {
		this.unJSONInfo = unJSONInfo;
	}

	public String getBeJSONInfo() {
		return beJSONInfo;
	}

	public void setBeJSONInfo(String beJSONInfo) {
		this.beJSONInfo = beJSONInfo;
	}

	public List<Code> getBrchStatus() {
		return brchStatus;
	}

	public void setBrchStatus(List<Code> brchStatus) {
		this.brchStatus = brchStatus;
	}

	public String getFuncIds() {
		return funcIds;
	}

	public void setFuncIds(String funcIds) {
		this.funcIds = funcIds;
	}

	public String getSubsysIds() {
		return subsysIds;
	}

	public void setSubsysIds(String subsysIds) {
		this.subsysIds = subsysIds;
	}

	public String getIsSaas() {
		return isSaas;
	}

	public void setIsSaas(String isSaas) {
		this.isSaas = isSaas;
	}

	public AuditTask getAuditTask() {
		return auditTask;
	}

	public void setAuditTask(AuditTask auditTask) {
		this.auditTask = auditTask;
	}

	public void setSysParamService(ISysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}

	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}

	public boolean isHasSubBranches() {
		return hasSubBranches;
	}

	public void setHasSubBranches(boolean hasSubBranches) {
		this.hasSubBranches = hasSubBranches;
	}

	public boolean isMiNoControl() {
		return miNoControl;
	}

	public void setMiNoControl(boolean miNoControl) {
		this.miNoControl = miNoControl;
	}
	

}
