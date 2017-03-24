package com.upg.cfsettle.member.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.cfsettle.mapping.member.FiMember;
import com.upg.ucars.framework.annotation.Service;
import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.model.ConditionBean;

/**
 * service实现类
 * @author renzhuolun
 * @date 2014年9月28日 上午10:58:43
 * @version <b>1.0.0</b>
 */
@Service
public class FiMemberServiceImpl extends BaseService implements IFiMemberService {
	@Autowired
	private IFiMemberDao memberDao;
	
	/**
	 * 获取所有的members
	 * @author renzhuolun
	 * @date 2014年9月28日 上午11:07:22
	 * @see com.upg.xhh.member.core.IFiMemberService#getMembers()
	 * @return
	 */
	public List<FiMember> getMembers() {
		return memberDao.getMembers();
	}

	public void setMemberDao(IFiMemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@Override
	public FiMember getMiNameByNo(String miNo) {
		return memberDao.getMiNameByNo(miNo);
	}

	@Override
	public List<FiMember> findAllEffectiveMember() {
		List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
		conditionList.add(new ConditionBean("status","1"));
		return memberDao.queryEntity(conditionList, null);
	}
}
