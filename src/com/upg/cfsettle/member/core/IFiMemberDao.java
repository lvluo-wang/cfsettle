package com.upg.cfsettle.member.core;

import java.util.List;

import com.upg.cfsettle.mapping.member.FiMember;
import com.upg.ucars.framework.base.IBaseDAO;

public interface IFiMemberDao extends IBaseDAO<FiMember, Long> {
	
	/**
	 * 获取所有的members
	 * @author renzhuolun
	 * @date 2014年9月28日 上午11:07:34
	 * @return
	 */
	List<FiMember> getMembers();


	/**
	 * 获取共享标投放站点列表
	 * @return
	 */
	public List<FiMember> getSharedMemberList();
	
	/**
	 * 通过miNo查询miName
	 * @author renzhuolun
	 * @date 2014年9月28日 上午11:21:19
	 * @param miNo
	 * @return
	 */
	FiMember getMiNameByNo(String miNo);

}
