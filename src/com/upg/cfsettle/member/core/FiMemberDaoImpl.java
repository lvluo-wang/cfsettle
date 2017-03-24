package com.upg.cfsettle.member.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upg.cfsettle.mapping.member.FiMember;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.SysBaseDao;
import com.upg.ucars.model.ConditionBean;

/**
 * dao持久化实现
 * @author renzhuolun
 * @date 2014年9月28日 上午10:58:18
 * @version <b>1.0.0</b>
 */
@Dao
public class FiMemberDaoImpl extends SysBaseDao<FiMember, Long> implements IFiMemberDao {
	
	/**
	 * 获取所有的members
	 * @author renzhuolun
	 * @date 2014年9月28日 上午11:24:42
	 * @see com.upg.xhh.member.core.IFiMemberDao#getMembers()
	 * @return
	 */
	@Override
	public List<FiMember> getMembers() {
		String hql="FROM FiMember fiMember";
		return this.queryByParam(hql, null, null);
	}


	public List<FiMember> getSharedMemberList(){
		String hql="FROM FiMember fiMember where fiMember.miProjectType = :miProjectType";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("miProjectType",2);
		return this.queryByParam(hql, params, null);
	}
	
	/**
	 * 通过miNo查询miName
	 * @author renzhuolun
	 * @date 2014年9月28日 上午11:24:15
	 * @see com.upg.xhh.member.core.IFiMemberDao#getMiNameByNo(java.lang.String)
	 * @param miNo
	 * @return
	 */
	@Override
	public FiMember getMiNameByNo(String miNo) {
		String hql = "FROM FiMember fiMember";
		QueryCondition condition = new QueryCondition(hql);
		condition.addCondition(new ConditionBean("fiMember.fiMiNo", ConditionBean.EQUAL, miNo));
		List<FiMember> members = this.queryEntity(condition.getConditionList(), null);
		if(members.size() < 1){
			return new FiMember();
		}else{
			return members.get(0);
		}
	}

}
