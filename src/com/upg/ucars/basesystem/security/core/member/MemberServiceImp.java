package com.upg.ucars.basesystem.security.core.member;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.basesystem.security.core.sysConfig.ISysConfigService;
import com.upg.ucars.constant.CodeKey;
import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.queryComponent.OperateType;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.base.queryComponent.QueryConfigLoader;
import com.upg.ucars.framework.base.queryComponent.QueryObject;
import com.upg.ucars.framework.base.queryComponent.ValueType;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.RightsException;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.MemberInfo;
import com.upg.ucars.mapping.basesystem.security.SysConfig;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.DateTimeUtil;


/**
 * 
 * MemberServiceImp
 *
 * @author shentuwy
 *
 */

public class MemberServiceImp implements IMemberService {
	/** 接入点 */
	private IMemberDAO memberDAO;
	/** 系统配置 */
	private ISysConfigService sysConfigService;


	public void createMemberInfo(MemberInfo mInfo) {
		String miName=mInfo.getMiName();
		String miNo=mInfo.getMiNo();
		if(memberDAO.existMiName(miName)){
			ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_MEMBER_SAME_MINAME);
		}
		if(memberDAO.existMiNo(miNo)){
			ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_MEMBER_SAME_MINO);
		}
		mInfo.setIsOpen(MemberInfo.NOT_OPEN);
		mInfo.setMiDt(DateTimeUtil.getNowDateTime());
		memberDAO.save(mInfo);
		//保存接入点参数默认设置
		sysConfigService.saveDefaultSysConfig(miNo);
	}
	
	public void modifyMemberInfo(MemberInfo mInfo) {
		String miNo=mInfo.getMiNo();
		String miName=mInfo.getMiName();
		if(memberDAO.existOtherMiName(miNo,miName)){
			ExceptionManager.throwException(RightsException.class, ErrorCodeConst.SECURITY_MEMBER_SAME_MINAME);
		}
		memberDAO.update(mInfo);
	}

	public List<MemberInfo> queryMemberInfo(Page pg, QueryComponent qc){
		return memberDAO.queryEntity(qc, pg);
	}

	public void openMember(String miNo) {
		MemberInfo info = findMemberInfo(miNo);
		info.setIsOpen(MemberInfo.OPEN);
		modifyMemberInfo(info);
	}

	public void closeMember(String miNo) {
		MemberInfo info = findMemberInfo(miNo);
		info.setIsOpen(MemberInfo.NOT_OPEN);
		modifyMemberInfo(info);
	}

	public MemberInfo findMemberInfo(String miNo) {
		return memberDAO.get(miNo);
	}
	
	public Branch findMemberBranch(String miNo)  {
		return memberDAO.findBranchByMiNo(miNo);
	}

	public Buser findMemberUser(Long brchId) {
		return memberDAO.findUserByBrchId(brchId);
	}

	public void setMemberDAO(IMemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	public void setSysConfigService(ISysConfigService sysConfigService) {
		this.sysConfigService = sysConfigService;
	}

	public void removeMemberInfo(String miNo) {
		memberDAO.delete(miNo);
	}

	public QueryComponent getQueryComponent() {
		QueryComponent component=new QueryComponent();
		QueryObject q1=new QueryObject();
		q1.setQueryName("memberInfo.miNo");
		q1.addOperate(OperateType.EQ_OP);
		//设置默认操作符
		q1.setQueryOperate(OperateType.LIKE);
		q1.setValueType(ValueType.STRING);
		QueryObject q2=new QueryObject();
		q2.setQueryName("memberInfo.miName");
		q2.addOperate(OperateType.EQ_OP);
		q2.addOperate(OperateType.LIKE_OP);
		//设置默认操作符
		q2.setQueryOperate(OperateType.LIKE);
		q2.setValueType(ValueType.STRING);
		QueryObject q3=new QueryObject();
		q3.setQueryName("memberInfo.miType");
		q3.addOperate(OperateType.EQ_OP);
		//设置默认操作符
		q3.setQueryOperate(OperateType.EQ);
		q3.setValueType(ValueType.LIST);
		Map<String,String> map=new HashMap<String,String>();
		List<Code> list=DictionaryUtil.getCodesByKey(CodeKey.MEMBER_MI_TYPE);
		for(Code code:list){
			map.put(code.getCodeNo(), code.getCodeName());
		}
		String valueList=QueryConfigLoader.getValueListByMap(map);
		q3.setValueList(valueList);
		component.addQuery(q1);
		component.addQuery(q2);
		component.addQuery(q3);
		return component;
	}

	public SysConfig getConfig(String miNo) {
		return memberDAO.getSysconfigByMiNo(miNo);
	}

	public void saveConfig(SysConfig config) {
		sysConfigService.addSysConfig(config);
	}
	
	public List<MemberInfo> findMemberByUserNo(String userNo) {
		return memberDAO.findMemberByUserNo(userNo);
	}
	
	public List<MemberInfo> getMemberList(List<ConditionBean> conditionList,Page pg){
		List<MemberInfo> ret = memberDAO.queryEntity(conditionList, pg);
		return ret == null ? Collections.EMPTY_LIST : ret;
	}

	public List<MemberInfo> getMemberList(MemberInfo member, Page pg) {
		List<MemberInfo> ret = null;
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
		if( member != null ){
			String miName = member.getMiName();
			if( StringUtils.isNotBlank(miName) ){
				conditionList.add(new ConditionBean(MemberInfo.MI_NAME, ConditionBean.LIKE, miName.trim()));
			}
			String miNo = member.getMiNo();
			if( StringUtils.isNotBlank(miNo) ){
				conditionList.add(new ConditionBean(MemberInfo.MI_NO,ConditionBean.LIKE,miNo.trim()));
			}
		}
		ret = getMemberList(conditionList, pg);
		return ret;
	}
	
}
