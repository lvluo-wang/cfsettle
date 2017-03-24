package com.upg.ucars.basesystem.security.core.sysConfig;


import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.mapping.basesystem.security.SysConfig;

/**
 * 
 * ISysConfigDAO
 *
 * @author shentuwy
 *
 */
public interface ISysConfigDAO extends IBaseDAO<SysConfig,Long> {
	/**
	 * 获取接入者的系统配置信息
	 * 
	 * @param miNo 接入编号
	 * @return	
	 */
	public SysConfig getConfigByMiNo(String miNo);
}
