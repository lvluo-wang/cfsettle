package com.upg.ucars.basesystem.security.core.sysparam;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.mapping.basesystem.security.SysParam;

/**
 * 
 * ISysParamDao
 * 
 * @author shentuwy
 * 
 */
public interface ISysParamDao extends IBaseDAO<SysParam, Long> {

	/**
	 * 获取系统参数
	 * 
	 * @param miNo
	 *            接入者编号
	 * @param paramKey
	 *            参数键值
	 * @return 参数信息
	 */
	public SysParam findSysParam(String miNo, String paramKey);

	/**
	 * 获取系统参数
	 * 
	 * @param miNo
	 *            接入者编号
	 * @return 接入点下所有参数
	 */
	public List<SysParam> getSysParams(String miNo);

	/**
	 * 
	 * 获取参数列表
	 * 
	 * @param idList
	 *            参数ID列表
	 * @return 参数列表
	 */
	public List<SysParam> getSysParamByIds(List<Long> idList);

}
