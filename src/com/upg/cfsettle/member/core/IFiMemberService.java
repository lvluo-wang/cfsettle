package com.upg.cfsettle.member.core;

import java.util.List;

import com.upg.cfsettle.mapping.member.FiMember;
import com.upg.ucars.framework.base.IBaseService;

/**
 * service类
 * @author renzhuolun
 * @date 2014年9月28日 上午10:57:42
 * @version <b>1.0.0</b>
 */
public interface IFiMemberService extends IBaseService {
	
	/**
	 * 获取所有的members
	 * @author renzhuolun
	 * @date 2014年9月28日 上午11:05:08
	 * @return
	 */
	List<FiMember> getMembers();
	
	/**
	 * 获取所有有效的接入点
	 * 
	 * @return
	 */
	List<FiMember> findAllEffectiveMember();
	
	/**
	 * 获取所有的miName
	 * @author renzhuolun
	 * @date 2014年9月28日 上午11:19:14
	 * @param miNo
	 * @return
	 */
	FiMember getMiNameByNo(String miNo);
}
