package com.upg.cfsettle.foundation.core;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.cfsettle.mapping.filcs.FiLcsFundManagerExt;

/**
 * 理财师service
 * @author renzhuolun
 * @date 2016年6月6日 上午10:55:30
 * @version <b>1.0.0</b>
 */
public interface IFiLcsFundManagerExtService extends IBaseService {
	
	/**
	 * 添加管理者扩展信息
	 * @author renzhuolun
	 * @date 2016年6月6日 下午5:29:18
	 * @param perExt
	 */
	void addFundManagerExtList(List<FiLcsFundManagerExt> perExt);
	
	/**
	 * 条件查询理财经理理财信息
	 * @author renzhuolun
	 * @date 2016年6月13日 上午10:30:56
	 * @param fiLcsFundManagerExt
	 * @param page
	 * @return
	 */
	List<FiLcsFundManagerExt> findByCondition(FiLcsFundManagerExt fiLcsFundManagerExt,Page page);
	
	/**
	 * 批量删除理财经理扩展信息
	 * @author renzhuolun
	 * @date 2016年6月15日 下午3:32:15
	 * @param oldExt
	 */
	void deleteLcsFundManagerExtist(List<FiLcsFundManagerExt> oldExt);
}
