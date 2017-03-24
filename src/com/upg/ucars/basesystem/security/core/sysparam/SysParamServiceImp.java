package com.upg.ucars.basesystem.security.core.sysparam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.ActionContext;
import com.upg.ucars.basesystem.audit.core.AuditTaskRevokeResult;
import com.upg.ucars.basesystem.audit.core.IAuditCallBack;
import com.upg.ucars.basesystem.audit.core.IAuditService;
import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.basesystem.security.core.sysConfig.ISysConfigService;
import com.upg.ucars.basesystem.security.core.user.IUserService;
import com.upg.ucars.basesystem.security.core.user.UserManager;
import com.upg.ucars.constant.CommonConst;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.constant.ParameterCodeConst;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.audit.AuditProcess;
import com.upg.ucars.mapping.basesystem.audit.AuditTask;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.SysParam;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.LogUtil;

/**
 * 系统参数服务类
 * 
 * @author shentuwy
 * 
 */
public class SysParamServiceImp extends BaseService implements ISysParamService, IAuditCallBack {

	/** 只能提交未审批 */
	private static final String	ERROR_AUDIT_MUST_UN_CHECK		= "SYS_PARAM_AUDIT_001";
	/** 只能撤销审批中 */
	private static final String	ERROR_REVOKE_MUST_CHECKING		= "SYS_PARAM_AUDIT_002";
	/** 修改审核中的参数错误 */
	private static final String	ERROR_OPERATE_CHECKING_SYSPARAM	= "ERROR_OPERATE_CHECKING_SYSPARAM";

	/** 审批服务 */
	private IAuditService		auditService;
	/** 系统参数审批 */
	private String				sysParamCheckProdNo;
	/** 参数数据操作 */
	private ISysParamDao		sysParamDAO;
	/** 接入点配置服务 */
	private ISysConfigService	sysConfigService;
	/** 用户操作服务 */
	private IUserService		userService;

	public void setSysParamDAO(ISysParamDao sysParamDAO) {
		this.sysParamDAO = sysParamDAO;
	}

	public void setAuditService(IAuditService auditService) {
		this.auditService = auditService;
	}

	public void setSysParamCheckProdNo(String sysParamCheckProdNo) {
		this.sysParamCheckProdNo = sysParamCheckProdNo;
	}

	public void setSysConfigService(ISysConfigService sysConfigService) {
		this.sysConfigService = sysConfigService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	/**
	 * 判断是否已经存在冲突的参数 <br/>
	 * 即：paramKey和miNo一致
	 * 
	 * @param sp
	 * @return 如果存在，则返回存在的参数，否则返回null
	 */
	private SysParam checkExistSameSysParam(SysParam sp) {
		SysParam ret = null;
		if (sp != null) {
			String paramKey = sp.getParamKey();
			String miNo = sp.getMiNo();
			if (paramKey != null) {
				SysParam dbSp = sysParamDAO.findSysParam(miNo, paramKey);
				if (dbSp != null && (sp.getParamId() == null || (!sp.getParamId().equals(dbSp.getParamId())))) {
					ExceptionManager
							.throwException(ServiceException.class, ErrorCodeConst.SYS_PARAM_KEY_MI_EXIST_ERROR);
				}
				ret = dbSp;
			}
		}
		return ret;
	}

	/**
	 * 
	 * 检查编辑条件
	 * 
	 */
	private void validOperateCondition(SysParam sp) {
		if (sp != null && sp.getParamId() != null) {
			// 审批中的参数不能被操作
			SysParam dbSp = sysParamDAO.get(sp.getParamId());
			if (dbSp != null) {
				if (StringUtils.equals(SysParam.STATUS_CHECKING, dbSp.getStatus())) {
					ExceptionManager.throwException(ServiceException.class, ERROR_OPERATE_CHECKING_SYSPARAM);
				}
			}
		}
	}

	/**
	 * 
	 * 参数是否需要审核
	 * 
	 * @param miNo
	 *            接入点编号
	 * @return true：审核，false：不需审核
	 */
	private boolean isNeedCheck(String miNo) {
		boolean ret = false;
		if (UserManager.isBrchLocalManager() || UserManager.isBrchNormalUser() || UserManager.isBrchGlobalManager()) {
			ret = sysConfigService.isSysparamCheck(miNo);
		}
		return ret;
	}

	/**
	 * 
	 * 设置参数实际值
	 * 
	 * @param sp
	 *            参数信息
	 */
	private void setParamValue(SysParam sp) {
		if (sp != null) {
			if (StringUtils.equals(SysParam.STATUS_CHECKED, sp.getStatus())) {
				sp.setParamValue(sp.getTempValue());
			}
		}
	}

	public Long addParam(SysParam sp) {
		checkExistSameSysParam(sp);
		sp.setStatus(isNeedCheck(sp.getMiNo()) ? SysParam.STATUS_UNCHECK : SysParam.STATUS_CHECKED);
		setParamValue(sp);
		return sysParamDAO.save(sp);
	}

	public void addParams(List<SysParam> sps) {
		if (sps != null && sps.size() > 0) {
			sysParamDAO.saveOrUpdateAll(sps);
		}
	}

	public void delParam(SysParam sp) {
		sysParamDAO.delete(sp);
	}

	/**
	 * 
	 * 删除参数
	 * 
	 * @param sps
	 *            参数信息列表
	 */
	private void delParams(List<SysParam> sps) {
		if (sps != null && sps.size() > 0) {
			for (SysParam sp : sps) {
				validOperateCondition(sp);
			}
			sysParamDAO.delAll(sps);
		}
	}

	public void deleteParams(List<Long> idList) {
		if (idList != null && idList.size() > 0) {
			List<SysParam> list = getSysParamByIds(idList);
			if (list != null && list.size() > 0) {
				delParams(list);
			}
		}
	}

	public void editParam(SysParam sp) {
		checkExistSameSysParam(sp);
		validOperateCondition(sp);
		sp.setStatus(isNeedCheck(sp.getMiNo()) ? SysParam.STATUS_UNCHECK : SysParam.STATUS_CHECKED);
		setParamValue(sp);
		sp.setModifyUserId(SessionTool.getUserLogonInfo().getSysUserId());
		if (StringUtils.equals(SysParam.STATUS_CHECKED, sp.getStatus())) {
			sp.setReviewUserId(sp.getModifyUserId());
		} else if (StringUtils.equals(SysParam.STATUS_UNCHECK, sp.getStatus())) {
			sp.setReviewUserId(null);
		}
		sysParamDAO.merge(sp);
	}

	public void editParams(List<SysParam> sps) {
		if (sps != null && sps.size() > 0) {
			sysParamDAO.saveOrUpdateAll(sps);
		}
	}

	public SysParam getParamById(Long id) {
		return sysParamDAO.get(id);
	}

	public SysParam findSysParam(String miNo, String paramKey) {
		return sysParamDAO.findSysParam(miNo, paramKey);
	}

	public List<SysParam> getSysParams(String miNo) {
		return sysParamDAO.getSysParams(miNo);
	}

	public void delParamById(Long id) {
		sysParamDAO.delete(id);
	}

	public List<SysParam> findParamByPage(SysParam sp, Page page) {
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
		if (sp != null) {
			String miNo = sp.getMiNo();
			if (StringUtils.isNotBlank(miNo)) {
				conditionList.add(new ConditionBean(SysParam.MI_NO, miNo));
			} /*else {
				conditionList.add(new ConditionBean(SysParam.MI_NO, ConditionBean.IS_NULL, null));
			}*/
			String paramKey = sp.getParamKey();
			if (StringUtils.isNotBlank(paramKey)) {
				conditionList
						.add(new ConditionBean(SysParam.PARAM_KEY, ConditionBean.LIKE, StringUtils.trim(paramKey)));
			}
			String paramName = sp.getParamName();
			if (StringUtils.isNotBlank(paramName)) {
				conditionList.add(new ConditionBean(SysParam.PARAM_NAME, ConditionBean.LIKE, StringUtils
						.trim(paramName)));
			}
			String status = sp.getStatus();
			if (StringUtils.isNotBlank(status)) {
				conditionList.add(new ConditionBean(SysParam.STATUS, status));
			}
		}
		return sysParamDAO.queryEntity(conditionList, page);
	}

	public void commitAudit(Long id) {
		SysParam sp = getParamById(id);
		if (!SysParam.STATUS_UNCHECK.equals(sp.getStatus())) {
			ExceptionManager.throwException(ServiceException.class, ERROR_AUDIT_MUST_UN_CHECK);
		}
		Long taskId = auditService.commitAuditTask(SessionTool.getUserLogonInfo(), sysParamCheckProdNo, sp);
		sp.setStatus(SysParam.STATUS_CHECKING);
		sysParamDAO.update(sp);
		LogUtil.getCommonLog().debug("系统参数添加或修改后提交审批，任务id:" + taskId);
	}

	public void commitAudit(List<Long> ids) {
		if (ids != null && ids.size() > 0) {
			for (Long i : ids) {
				commitAudit(i);
			}
		}
	}

	public AuditTask findAuditTask(Long id) {
		return auditService.getLastAuditTask(this.sysParamCheckProdNo, id, SysParam.class);
	}

	public void revokeAudit(Long id) {
		SysParam sp = getParamById(id);
		if (!SysParam.STATUS_CHECKING.equals(sp.getStatus())) {
			ExceptionManager.throwException(ServiceException.class, ERROR_REVOKE_MUST_CHECKING);
		}
		AuditTaskRevokeResult revokeResult = auditService.revokeAuditTask(SessionTool.getUserLogonInfo(),
				sysParamCheckProdNo, sp);
		if (revokeResult.isRevokePass()) {
			sp.setStatus(SysParam.STATUS_UNCHECK);
			sysParamDAO.update(sp);
		}
		LogUtil.getCommonLog().debug("系统参数撤销审批，撤销结果" + revokeResult.isRevokePass());
	}

	public void revokeAudit(List<Long> ids) {
		if (ids != null && ids.size() > 0) {
			for (Long i : ids) {
				revokeAudit(i);
			}
		}
	}

	public void auditCallBack(AuditTask task, List<AuditProcess> processList) {
		if (task != null) {
			String prodNo = task.getProdNo();
			if (this.sysParamCheckProdNo.equals(prodNo)) {
				SysParam sp = sysParamDAO.get(task.getAuditEntityId());
				if (sp != null) {
					sp.setStatus(AuditTask.STATUS_AUDIT_PASS.equals(task.getAuditStatus()) ? SysParam.STATUS_CHECKED
							: SysParam.STATUS_UNCHECK);
					sp.setReviewUserId(processList.get(processList.size() - 1).getAuditProcessExecPerson());
					setParamValue(sp);
					// temp solution
					Object paramValueObj = ActionContext.getContext().getParameters().get("sp.paramValue");
					if (paramValueObj != null) {
						if (paramValueObj.getClass().isArray()) {
							sp.setParamValue(String.valueOf(((Object[]) paramValueObj)[0]));
						} else if (paramValueObj instanceof String) {
							sp.setParamValue((String) paramValueObj);
						}
					}
					sysParamDAO.update(sp);
				}
			}
		}

	}

	public String getSysParamValueByKey(String miNo, String paramKey) {
		SysParam sp = null;
		if (StringUtils.isNotBlank(paramKey)) {
			// 先找接入点的参数
			if (StringUtils.isNotBlank(miNo)) {
				sp = sysParamDAO.findSysParam(miNo, paramKey);
			}
			if (sp == null) {
				// 再找默认的参数
				sp = sysParamDAO.findSysParam(null, paramKey);
			}
		}
		return sp == null ? null : sp.getParamValue();
	}

	/**
	 * 判断参数是否需要审核 <br/>
	 * 
	 * 先找接入者是否有此参数，若无，则找默认参数
	 * 
	 * @param miNo
	 *            接入者编号
	 * @param paramKey
	 *            参数编号
	 * @return true：审核，false：不需审核
	 */
	private boolean isCheckSysParam(String miNo, String paramKey) {
		boolean ret = false;
		if (StringUtils.isNotBlank(paramKey)) {
			ret = StringUtils.equals(CommonConst.EFFECTIVE, getSysParamValueByKey(miNo, paramKey));
		}
		return ret;
	}

	public boolean isCheckBrchFunc(String miNo) {
		return isCheckSysParam(miNo, ParameterCodeConst.BRCH_FUNC_CHECK);
	}

	public boolean isCheckUserRole(String miNo) {
		return isCheckSysParam(miNo, ParameterCodeConst.BUSI_USER_FUNC_CHECK);
	}

	public boolean isCheckBrchManagerRole(String miNo) {
		return isCheckSysParam(miNo, ParameterCodeConst.BRCH_MANAGER_FUNC_CHECK);
	}

	@SuppressWarnings("unchecked")
	public List<SysParam> getSysParamByIds(List<Long> idList) {
		List<SysParam> ret = null;
		if (idList != null && idList.size() > 0) {
			ret = sysParamDAO.getSysParamByIds(idList);
		}
		return ret == null ? SERVICE_EMPTY_LIST : ret;
	}

	public void assignSysParam(List<Long> idList, String miNo) {
		if (StringUtils.isNotBlank(miNo)) {
			List<SysParam> hasAssigned = getSysParams(miNo);
			List<SysParam> selectedList = getSysParamByIds(idList);
			List<SysParam> delList = new ArrayList<SysParam>();
			// 查询要删除的
			if (hasAssigned != null && hasAssigned.size() > 0) {
				for (Iterator<SysParam> aIt = hasAssigned.iterator(); aIt.hasNext();) {
					SysParam s1 = aIt.next();
					boolean isDel = true;
					if (selectedList != null && selectedList.size() > 0) {
						for (int i = 0; i < selectedList.size(); i++) {
							SysParam s2 = selectedList.get(i);
							if (StringUtils.equals(s1.getParamKey(), s2.getParamKey())) {
								isDel = false;
								selectedList.remove(i);
								break;
							}
						}
					}
					if (isDel) {
						delList.add(s1);
					}
				}
			}
			// 增加的
			if (delList != null && delList.size() > 0) {
				delParams(delList);
			}
			if (selectedList != null && selectedList.size() > 0) {
				List<SysParam> addList = new ArrayList<SysParam>(selectedList.size());
				for (Iterator<SysParam> it = selectedList.iterator(); it.hasNext();) {
					SysParam s1 = it.next();
					SysParam s2 = new SysParam();
					s2.setParamKey(s1.getParamKey());
					s2.setParamName(s1.getParamName());
					s2.setTempValue(s1.getTempValue());
					s2.setParamValue(s1.getParamValue());
					s2.setStatus(s1.getStatus());
					s2.setMiNo(miNo);
					s2.setCodeKey(s1.getCodeKey());
					s2.setRemark(s1.getRemark());
					s2.setBuildUserId(SessionTool.getUserLogonInfo().getSysUserId());
					addList.add(s2);
				}
				addParams(addList);
			}
		}
	}

	public Map<String, Object> convertSysParamToMap(SysParam param) {
		Map<String, Object> ret = new HashMap<String, Object>();
		if (param != null) {
			ret.put("paramId", param.getParamId());
			ret.put("paramName", param.getParamName());
			ret.put("paramValue", param.getParamValue());
			ret.put("paramKey", param.getParamKey());
			ret.put("tempValue", param.getTempValue());
			ret.put("status", param.getStatus());
			ret.put("miNo", param.getMiNo());
			ret.put("buildUserId", param.getBuildUserId());
			ret.put("modifyUserId", param.getModifyUserId());
			ret.put("reviewUserId", param.getReviewUserId());
			ret.put("codeKey", param.getCodeKey());
			ret.put("remark", param.getRemark());
			ret.put("version", param.getVersion());
			if (param.getBuildUserId() != null) {
				param.setBuildUserName(getUserName(param.getBuildUserId()));
				ret.put("buildUserName", param.getBuildUserName());
			}
			if (param.getReviewUserId() != null) {
				param.setReviewUserName(getUserName(param.getReviewUserId()));
				ret.put("reviewUserName", param.getReviewUserName());
			}
			if (param.getModifyUserId() != null) {
				ret.put("modifyUserIdDisp", getUserName(param.getModifyUserId()));
			}
			if (StringUtils.isNotBlank(param.getCodeKey()) && StringUtils.isNotBlank(param.getTempValue())) {
				ret.put("tempValueDisp", DictionaryUtil.getCodeNameByKey(param.getCodeKey(), param.getTempValue()));
			} else {
				ret.put("tempValueDisp", param.getTempValue());
			}

			if (StringUtils.isNotBlank(param.getCodeKey()) && StringUtils.isNotBlank(param.getParamValue())) {
				ret.put("paramValueDisp", DictionaryUtil.getCodeNameByKey(param.getCodeKey(), param.getParamValue()));
			} else {
				ret.put("paramValueDisp", param.getParamValue());
			}

			if (StringUtils.isNotBlank(param.getStatus())) {
				ret.put("statusDisp",
						DictionaryUtil.getCodeNameByKey(SysParam.CODE_SYS_PARAM_STATUS, param.getStatus()));
			} else {
				ret.put("statusDisp", param.getStatus());
			}
		}
		return ret;
	}

	/**
	 * 
	 * 获取用户的名字
	 * 
	 * @param userId
	 *            用户ID
	 * 
	 * @return 用户名称
	 */
	private String getUserName(Long userId) {
		String ret = null;
		if (userId != null) {
			Buser u = userService.getUserById(userId);
			if (u != null) {
				ret = u.getUserName();
			}
		}
		return ret == null ? StringUtils.EMPTY : ret;
	}

}
