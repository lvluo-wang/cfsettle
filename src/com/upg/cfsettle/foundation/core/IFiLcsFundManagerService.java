package com.upg.cfsettle.foundation.core;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.cfsettle.mapping.filcs.FiLcsFundAttach;
import com.upg.cfsettle.mapping.filcs.FiLcsFundManager;
import com.upg.cfsettle.mapping.filcs.FiLcsFundManagerExt;

/**
 * 理财师service
 * @author renzhuolun
 * @date 2016年6月6日 上午10:55:30
 * @version <b>1.0.0</b>
 */
public interface IFiLcsFundManagerService extends IBaseService {
	
	/**
	 * 新增理财经理
	 * @author renzhuolun
	 * @date 2016年6月6日 下午5:21:38
	 * @param fundManager
	 * @return
	 */
	void addLcsFundManagerOfPer(FiLcsFundManager fundManager);
	
	/**新增理财经理和经验
	 * @author renzhuolun
	 * @date 2016年6月8日 下午5:14:26
	 * @param fundManager
	 * @param fundManagerExt
	 */
	void addFiFundManager(FiLcsFundManager fundManager,FiLcsFundManagerExt fundManagerExt);
	
	/**
	 * 新增理财团队
	 * @author renzhuolun
	 * @date 2016年6月6日 下午5:21:38
	 * @param fundManager
	 * @return
	 */
	void addLcsFundManagerOfTeam(FiLcsFundManager fundManager);
	
	/**
	 * 条件查询理财经理
	 * @author renzhuolun
	 * @date 2016年6月12日 下午5:45:19
	 * @param fundManager
	 * @param page
	 * @return
	 */
	List<FiLcsFundManager> findByCondition(FiLcsFundManager fundManager, Page page);
	
	/**
	 * 主键查询理财经理
	 * @author renzhuolun
	 * @date 2016年6月13日 上午10:05:57
	 * @param id
	 * @return
	 */
	FiLcsFundManager getFundManagerById(Long id);
	
	/**
	 * 获取经理的理财信息
	 * @author renzhuolun
	 * @date 2016年6月13日 上午10:28:11
	 * @param id
	 * @return
	 */
	List<FiLcsFundManagerExt> getFundManagerExtById(Long id);
	
	/**
	 * 通过头像id获取附件信息
	 * @author renzhuolun
	 * @date 2016年6月13日 上午11:11:42
	 * @param headAttach
	 * @return
	 */
	FiLcsFundAttach getFundAttachByHeadAttachId(Long headAttach);
	
	/**
	 * 修改经理信息
	 * @author renzhuolun
	 * @date 2016年6月15日 下午2:44:51
	 * @param fundManager
	 */
	void doEditFundManager(FiLcsFundManager fundManager);
}
