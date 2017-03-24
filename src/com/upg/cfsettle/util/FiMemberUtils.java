package com.upg.cfsettle.util;

import java.util.ArrayList;
import java.util.List;

import com.upg.cfsettle.mapping.ficode.FiCodeItem;
import com.upg.cfsettle.mapping.member.FiMember;
import com.upg.cfsettle.member.core.IFiMemberService;
import com.upg.ucars.basesystem.UcarsHelper;
import com.upg.ucars.util.DateTimeUtil;

public class FiMemberUtils {
	/**
	 * 返回所有有效的接入点字典
	 * 
	 * @return
	 */
	public static List<FiCodeItem> getMemberList(){
		IFiMemberService  memberService = UcarsHelper.getBean(IFiMemberService.class,"fiMemberService");
		List<FiMember> members = memberService.findAllEffectiveMember();
		List<FiCodeItem> codeItems = new ArrayList<FiCodeItem>();
		String codeKey = DateTimeUtil.getCurTime();
		for(FiMember member :members){
			FiCodeItem fiCodeItem = new FiCodeItem();
			fiCodeItem.setCodeKey(codeKey);
			fiCodeItem.setCodeNo(member.getFiMiNo());
			fiCodeItem.setCodeName(member.getMiName());
			fiCodeItem.setLangType("zh_CN");
			codeItems.add(fiCodeItem);
		}
		return codeItems;
	}
	
	/**
	 * 根据No直接获致到Name
	 * @param portalCode
	 * @return
	 */
	public static String getMiNameByNo(String miNo){
		IFiMemberService memberService = UcarsHelper.getBean(IFiMemberService.class,"fiMemberService");
		FiMember fiMember = memberService.getMiNameByNo(miNo);
		if(fiMember != null){
			return fiMember.getMiName();
		}else{
			return "接入点不存在";
		}
	}
}
