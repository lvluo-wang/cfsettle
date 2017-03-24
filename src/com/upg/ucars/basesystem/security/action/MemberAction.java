package com.upg.ucars.basesystem.security.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.basesystem.security.core.member.IMemberService;
import com.upg.ucars.basesystem.security.core.sysConfig.ISysConfigService;
import com.upg.ucars.basesystem.security.core.sysparam.ISysParamService;
import com.upg.ucars.constant.CodeKey;
import com.upg.ucars.factory.JQueryTreeNodeManager;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.security.MemberInfo;
import com.upg.ucars.mapping.basesystem.security.SysConfig;
import com.upg.ucars.mapping.basesystem.security.SysParam;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.DESKeyUtil;

/**
 * 接入
 * 
 * @author shentuwy
 * 
 */
public class MemberAction extends BaseAction {
	
	private static final long serialVersionUID = 5059288256132435667L;
	
	private static final String EDIT_CONFIG = "edit_config";
	/** 接入点 */
	private IMemberService memberService;
	/** 系统配置服务 */
	private ISysConfigService sysConfigService;
	/** 系统参数服务 */
	private ISysParamService sysParamService;
	/** 接入点实体 */
	private MemberInfo member;
	/** 接入点类型 */
	private List<Code> miType;
	/** 是与否 */
	private List<Code> yesNo;
	/** 接入者编号 */
	private String miNo;
	/** 结构ID */
	private Long brchId;
	/** 用户ID */
	private Long userId;
	/** 用户编号 */
	private String userNo;
	/** 系统配置 */
	private SysConfig config;

	
	/**
	 * 
	 * 分配参数
	 *
	 */
	public void assignSps(){
		if( StringUtils.isNotBlank(miNo) ){
			sysParamService.assignSysParam(getIdList(), miNo);
		}
	}
	

	/**
	 * 接入管理的主页面
	 * 
	 * @return
	 */
	public String mainPage() {
		return MAIN;
	}

	/**
	 * 添加接入
	 * 
	 * @return
	 */
	public String add() {
		miType = DictionaryUtil.getCodesByKey(CodeKey.MEMBER_MI_TYPE);
		return ADD;
	}

	/**
	 * 保存
	 */
	public void save() {
		memberService.createMemberInfo(member);
	}

	/**
	 * 更新
	 */
	public void update() {
		memberService.modifyMemberInfo(member);
	}
	
	/**
	 * 
	 * 增加或编辑
	 *
	 */
	public void saveOrEditConfig(){
		if( config != null ){
			if( config.getScId() != null ){
				sysConfigService.editSysConfig(config);
			}else{
				sysConfigService.addSysConfig(config);
			}
		}
	}

	/**
	 * 编辑接入信息
	 * 
	 * @return
	 */
	public String edit() {
		miType = DictionaryUtil.getCodesByKey(CodeKey.MEMBER_MI_TYPE);
		member = memberService.findMemberInfo(member.getMiNo());
		return EDIT;
	}

	/**
	 * 查看接入信息
	 * 
	 * @return
	 */
	public String view() {
		miType = DictionaryUtil.getCodesByKey(CodeKey.MEMBER_MI_TYPE);
		member = memberService.findMemberInfo(member.getMiNo());
		return VIEW;
	}
	
	/**
	 * 删除 
	 */
	public void delete() {
		close();
	}
	
	/**
	 * 用于弹出式选择接入的查询
	 * 
	 * @return
	 */
	public String queryMember() {
		QueryComponent query = buildQueryWithHttpRequest(memberService
				.getQueryComponent());
		Page pg = getPg();
		List<MemberInfo> list = memberService.queryMemberInfo(getPg(), query);
		return setDatagridInputStreamData(list, pg);
	}

	/**
	 * 数据组件异步查询
	 * 
	 * @return
	 */
	public String data() {
		Page pg = getPg();
		List<MemberInfo> list = memberService.getMemberList(member, pg);
		return setDatagridInputStreamData(list, pg);
	}
	
	/**
	 * 
	 * 开启
	 *
	 */
	public void open() {
		memberService.openMember(member.getMiNo());
	}
	
	/**
	 * 
	 * 关闭
	 *
	 */
	public void close() {
		memberService.closeMember(member.getMiNo());
	}

	
	public String config() {
		yesNo = DictionaryUtil.getCodesByKey(CodeKey.YES_NO);
		config = sysConfigService.getConfigByMiNo(member.getMiNo(),true);
		if (config != null && config.getPwdInit() != null ){
			String ped = DESKeyUtil.DecryptAES(config.getPwdInit(), null);
			config.setPwdInit(ped);
		}
		if (config.getScId() == null) {
			config.setMiNo(member.getMiNo());
		}
		return EDIT_CONFIG;
	}

	public String queryByUserNo() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (userNo != null) {
			List<MemberInfo> memberList = memberService
					.findMemberByUserNo(userNo);
			map.put("list", memberList);
		}
		return setInputStreamData(map);
	}

	/**
	 * 
	 * 参数分配页面
	 *
	 * @return
	 */
	public String sysParam(){
		return SUCCESS;
	}
	
	/**
	 * 
	 * 要分配的参数的接入点
	 *
	 * @return
	 */
	public String sysParamMember(){
		List<MemberInfo> list = memberService.getMemberList(new ArrayList<ConditionBean>(),null);
		return setInputStreamData(JQueryTreeNodeManager.convertListToJTN(list));
	}
	
	/**
	 * 
	 * 要分配的参数
	 *
	 * @return
	 */
	public String sysParamList(){
		List<SysParam> list = sysParamService.findParamByPage(new SysParam(), null);
		List<Map<String,Object>> ret = null;
		if( list != null && list.size() > 0 ){
			ret = new ArrayList<Map<String,Object>>(list.size());
			for( SysParam sp : list ){
				ret.add(sysParamService.convertSysParamToMap(sp));
			}
		}
		return setDatagridInputStreamData(ret,getPg());
		//return setInputStreamData(ret);
	}
	
	/**
	 * 
	 * 已经分配给接入点的参数
	 *
	 * @return
	 */
	public String getSelectedSysParamByMember(){
		List<SysParam> list = null;
		if( StringUtils.isNotBlank(miNo) ){
			list = sysParamService.getSysParams(miNo);
		}
		return setInputStreamData(list == null ? Collections.EMPTY_LIST : list );
	}
	
	public MemberInfo getMember() {
		return member;
	}

	public void setMember(MemberInfo member) {
		this.member = member;
	}

	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}

	public String getMiNo() {
		return miNo;
	}

	public void setMiNo(String miNo) {
		this.miNo = miNo;
	}

	public Long getBrchId() {
		return brchId;
	}

	public void setBrchId(Long brchId) {
		this.brchId = brchId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Code> getMiType() {
		return miType;
	}

	public void setMiType(List<Code> miType) {
		this.miType = miType;
	}

	public SysConfig getConfig() {
		return config;
	}

	public void setConfig(SysConfig config) {
		this.config = config;
	}

	public List<Code> getYesNo() {
		return yesNo;
	}

	public void setYesNo(List<Code> yesNo) {
		this.yesNo = yesNo;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public void setSysConfigService(ISysConfigService sysConfigService) {
		this.sysConfigService = sysConfigService;
	}

	public void setSysParamService(ISysParamService sysParamService) {
		this.sysParamService = sysParamService;
	}

}
