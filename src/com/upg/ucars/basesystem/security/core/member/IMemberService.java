package com.upg.ucars.basesystem.security.core.member;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.MemberInfo;
import com.upg.ucars.mapping.basesystem.security.SysConfig;
import com.upg.ucars.model.ConditionBean;

/**
 * 接入服务
 * 
 * @author shentuwy
 * @date 2011-1-27 下午04:17:13
 * @version V1.0
 */
public interface IMemberService extends IBaseService {
	/**
	 * 创建一条接入信息
	 * 
	 * @param mInfo 接入信息
	 */
	public void createMemberInfo(MemberInfo mInfo);
	/**
	 * 修改接入信息
	 * 
	 * @param mInfo 接入信息
	 */
	public void modifyMemberInfo(MemberInfo mInfo);
	/**
	 * 分页查询
	 * 
	 * @param pg 分页
	 * @param qc 查询组件
	 * @return
	 */
	public List<MemberInfo> queryMemberInfo(Page pg,QueryComponent qc);
	/**
	 * 开启接入
	 * 
	 * @param miNo 接入编号
	 */
	public void openMember(String miNo);
	/**
	 * 关闭接入
	 * 
	 * @param miNo
	 * 
	 */
	public void closeMember(String miNo);
	/**
	 * 获取接入信息
	 * 
	 * @param miNo
	 * @return
	 */
	public MemberInfo findMemberInfo(String miNo);
	/**
	 * 获取接入总部信息
	 * 
	 * @param miNo
	 * @return
	 */
	public Branch findMemberBranch(String miNo);
	/**
	 * 获取总部管理员信息
	 * 
	 * @param brchId
	 * @return
	 */
	public Buser findMemberUser(Long brchId);
	/**
	 * 移除接入信息
	 * 
	 * @param miNo
	 */
	public void removeMemberInfo(String miNo);
	/**
	 * 使用编码方式实现自定义组件查询
	 * 
	 * @return
	 */
	public QueryComponent getQueryComponent();
	/**
	 * 获取接入者的系统配置
	 * 
	 * @return
	 */
	public SysConfig getConfig(String miNo);
	/**
	 * 为接入者添加系统配置
	 * 
	 * @param config
	 */
	public void saveConfig(SysConfig config);
	
	/**
	 * 根据用户编号获取接入列表
	 * 
	 * @param userNo 用户编号
	 * @return
	 */
	public List<MemberInfo> findMemberByUserNo(String userNo);
	
	/**
	 * 
	 * 获取接入点列表
	 *
	 * @param conditionList
	 * @param pg
	 * @return
	 */
	public List<MemberInfo> getMemberList(List<ConditionBean> conditionList,Page pg);
	/**
	 * 
	 * 获取接入点列表
	 *
	 * @param member
	 * @param pg
	 * @return
	 */
	public List<MemberInfo> getMemberList(MemberInfo member,Page pg);
}
