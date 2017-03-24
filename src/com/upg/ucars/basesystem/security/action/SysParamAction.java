package com.upg.ucars.basesystem.security.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.basesystem.dictionary.core.code.ICodeMetaService;
import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.basesystem.security.core.sysConfig.ISysConfigService;
import com.upg.ucars.basesystem.security.core.sysparam.ISysParamService;
import com.upg.ucars.basesystem.security.core.user.UserManager;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.mapping.basesystem.audit.AuditTask;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.dictionary.CodeMeta;
import com.upg.ucars.mapping.basesystem.security.SysParam;

/**
 * 
 * 功能说明：系统参数action
 * 
 * 
 * @author shentuwy
 * @date 2011-8-4 上午8:52:21
 * 
 */
public class SysParamAction extends BaseAction {

	private static final long	serialVersionUID	= 5306754753725697028L;

	/** 系统参数 */
	private SysParam			sp;
	/** 审批任务 */
	private AuditTask			auditTask;
	/** 参数状态列表 */
	private List<Code>			statusTypeList;
	/** 参数服务 */
	private ISysParamService	sysParamService;
	/** 字典列表 */
	private List<Code>			codeList;
	/** 字典元 */
	private CodeMeta			codeMeta;
	/** 字典元服务 */
	private ICodeMetaService	codeMetaService;
	/** 系统配置服务 */
	private ISysConfigService	sysConfigService;

	/**
	 * 新增和编辑
	 * 
	 * @return 返回增加或编辑页面
	 * 
	 */
	public String input() {
		String id = getId();
		if (StringUtils.isNotBlank(id)) {
			sp = sysParamService.getParamById(Long.valueOf(id));
			if (sp != null) {
				String codeKey = sp.getCodeKey();
				if (StringUtils.isNotBlank(codeKey)) {
					codeMeta = codeMetaService.getCodeMetaByKey(codeKey);
					codeList = DictionaryUtil.getCodesByKey(codeKey);
				}
			}
		}
		return ADD;
	}

	/**
	 * 保存
	 * 
	 */
	public void save() {
		if (sp != null) {
			if (sp.getParamId() != null && sp.getParamId() != 0) {
				sysParamService.editParam(sp);
			} else {
				sp.setBuildUserId(SessionTool.getUserLogonInfo().getSysUserId());
				sp.setMiNo(SessionTool.getUserLogonInfo().getMiNo());
				sysParamService.addParam(sp);
			}
		}
	}

	/**
	 * 删除
	 * 
	 */
	public void del() {
		sysParamService.deleteParams(getIdList());
	}

	/**
	 * 列表页面
	 * 
	 * @return 主页面
	 */
	public String list() {
		String miNo = SessionTool.getUserLogonInfo().getMiNo();
		if (miNo != null) {
			if (UserManager.isBrchGlobalManager() || UserManager.isBrchLocalManager() || UserManager.isBrchNormalUser()) {
				showAudit = "1";
			}
		}
		statusTypeList = DictionaryUtil.getCodesByKey(SysParam.CODE_SYS_PARAM_STATUS);
		return MAIN;
	}

	/**
	 * 查询
	 * 
	 * @return 返回参数列表的json数据
	 */
	public String query() {
		if (sp == null) {
			sp = new SysParam();
		}
		sp.setMiNo(SessionTool.getUserLogonInfo().getMiNo());
		Page pg = getPg();
		List<SysParam> recs = sysParamService.findParamByPage(sp, pg);
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		if (recs != null && recs.size() > 0) {
			for (SysParam param : recs) {
				dataList.add(sysParamService.convertSysParamToMap(param));
			}
		}
		return setDatagridInputStreamData(dataList, pg);
	}

	/**
	 * 
	 * 提交审批
	 * 
	 */
	public void commitAudit() {
		sysParamService.commitAudit(Long.valueOf(getId()));
	}

	/**
	 * 
	 * 批量提交审批
	 * 
	 */
	public void batchCommitAudit() {
		sysParamService.commitAudit(getIdList());
	}

	/**
	 * 
	 * 撤销审批
	 * 
	 */
	public void revokeAudit() {
		sysParamService.revokeAudit(Long.valueOf(getId()));
	}

	/**
	 * 
	 * 批量撤销审批
	 * 
	 */
	public void batchRevokeAudit() {
		sysParamService.revokeAudit(getIdList());
	}

	/**
	 * 
	 * 查看审批进度
	 * 
	 * @return 审批进度页面
	 */
	public String viewAuditProcess() {
		auditTask = sysParamService.findAuditTask(Long.valueOf(getId()));
		return VIEW_AUDIT_PROCESS;
	}

	/**
	 * 
	 * 详情
	 * 
	 * @return 详情页面
	 */
	public String auditView() {
		sp = sysParamService.getParamById(getAuditEntityId());
		if (sp != null) {
			String codeKey = sp.getCodeKey();
			if (StringUtils.isNotBlank(codeKey)) {
				codeList = DictionaryUtil.getCodesByKey(codeKey);
			}
		}
		return "auditView";
	}

	public SysParam getSp() {
		return sp;
	}

	public void setSp(SysParam sp) {
		this.sp = sp;
	}

	public void setSysParamService(ISysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}

	public AuditTask getAuditTask() {
		return auditTask;
	}

	public void setAuditTask(AuditTask auditTask) {
		this.auditTask = auditTask;
	}

	public List<Code> getStatusTypeList() {
		return statusTypeList;
	}

	public void setStatusTypeList(List<Code> statusTypeList) {
		this.statusTypeList = statusTypeList;
	}

	public List<Code> getCodeList() {
		return codeList;
	}

	public void setCodeList(List<Code> codeList) {
		this.codeList = codeList;
	}

	public CodeMeta getCodeMeta() {
		return codeMeta;
	}

	public void setCodeMeta(CodeMeta codeMeta) {
		this.codeMeta = codeMeta;
	}

	public void setCodeMetaService(ICodeMetaService codeMetaService) {
		this.codeMetaService = codeMetaService;
	}

	public void setSysConfigService(ISysConfigService sysConfigService) {
		this.sysConfigService = sysConfigService;
	}

}
