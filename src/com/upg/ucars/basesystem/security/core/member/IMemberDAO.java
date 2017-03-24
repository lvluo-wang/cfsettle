package com.upg.ucars.basesystem.security.core.member;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.security.Branch;
import com.upg.ucars.mapping.basesystem.security.Buser;
import com.upg.ucars.mapping.basesystem.security.MemberInfo;
import com.upg.ucars.mapping.basesystem.security.SysConfig;

public interface IMemberDAO extends IBaseDAO<MemberInfo,String> {
	/**
	 * 获取接入的机构总部信息
	 * @param miId
	 * @return
	 * @throws DAOException
	 */
	public Branch findBranchByMiNo(String miNo)throws DAOException;
	/**
	 * 获取接入的总部管理员信息
	 * @param brchId
	 * @return
	 */
	public Buser findUserByBrchId(Long brchId)throws DAOException;
	/**
	 * 判断接入名称是否重复
	 * @param miName
	 * @return
	 */
	public boolean existMiName(String miName)throws DAOException;
	/**
	 * 判断接入编号是否重复
	 * @param miNo
	 * @return
	 */
	public boolean existMiNo(String miNo)throws DAOException;
	/**
	 * 除了指定的接入者之外是否有重名
	 * @param miName
	 * @return
	 */
	public boolean existOtherMiName(String miNo,String miName)throws DAOException;
	/**
	 * 根据接入编号获取系统配置
	 * @param miNo
	 * @return
	 */
	public SysConfig getSysconfigByMiNo(String miNo)throws DAOException;
	/**
	 * 根据用户编号获取接入列表
	 * @param userNo 用户编号
	 * @return
	 * @throws DAOException
	 */
	public List<MemberInfo> findMemberByUserNo(String userNo)throws DAOException;
}
