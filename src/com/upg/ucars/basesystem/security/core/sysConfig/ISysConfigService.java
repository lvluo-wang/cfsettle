package com.upg.ucars.basesystem.security.core.sysConfig;


import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.mapping.basesystem.security.SysConfig;


/**
 * 
 * ISysConfigService
 *
 * @author shentuwy
 *
 */
public interface ISysConfigService extends IBaseService{
	/**
	 * 获取接入者的系统配置信息
	 * 
	 * @param miId 接入id
	 * @param isDefaultSc
	 * @return
	 */
	public SysConfig getConfigByMiNo(String miNo,boolean isDefaultSc);
	/**
	 * 新增系统配置信息
	 * 
	 * @param sc	系统配置信息
	 * @return	信息记录ID
	 */
	public Long addSysConfig(SysConfig sc);
	/**
	 * 编辑系统配置信息
	 * 
	 * @param sc	系统配置信息
	 */
	public void editSysConfig(SysConfig sc);
	
	/**
	 * 
	 * 是否允许多IP登录
	 *
	 * @param miNo
	 * @return
	 */
	public boolean isAllowMultiIpLogon(String miNo);
	
	/**
	 * 
	 * 是否允许在线登录
	 *
	 * @param miNo
	 * @return
	 */
	public boolean isAllowOnlineLogon(String miNo);
	
	/**
	 * 
	 * 参数是否需要审核
	 *
	 * @param miNo
	 * @return
	 */
	public boolean isSysparamCheck(String miNo);
	
	/**
	 * 
	 * 保存默认的系统配置
	 * 
	 *@param miNo
	 */
	public void saveDefaultSysConfig(String miNo);
	
}
