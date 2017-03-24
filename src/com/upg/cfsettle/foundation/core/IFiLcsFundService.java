package com.upg.cfsettle.foundation.core;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.cfsettle.mapping.filcs.FiLcsFund;
import com.upg.cfsettle.mapping.filcs.FiLcsFundAttach;

/**
 * 理财师service
 * @author renzhuolun
 * @date 2016年6月6日 上午10:55:30
 * @version <b>1.0.0</b>
 */
public interface IFiLcsFundService extends IBaseService {
	
	/**
	 * 查询基金信息
	 * @author renzhuolun
	 * @date 2016年6月6日 上午11:04:52
	 * @param searchBean
	 * @param pg
	 * @return
	 */
	List<FiLcsFund> findByCondition(FiLcsFund searchBean, Page page);
	
	/**
	 * 新增基金信息
	 * @author renzhuolun
	 * @date 2016年6月6日 上午11:13:31
	 * @param fiLcsFund
	 * @param fiLcsFundAttach 
	 */
	void addFiLcsFund(FiLcsFund fiLcsFund, FiLcsFundAttach fiLcsFundAttach);
	
	/**
	 * 基金信息
	 * @author renzhuolun
	 * @date 2016年6月7日 下午5:38:25
	 * @param id
	 * @return
	 */
	FiLcsFund getFiLcsFundById(Long id);
	
	/**
	 * 修改基金信息
	 * @author renzhuolun
	 * @date 2016年6月8日 下午4:21:58
	 * @param fiLcsFund
	 */
	void editFiLcsFund(FiLcsFund fiLcsFund);
	
	/**
	 * 项目审核
	 * @author renzhuolun
	 * @date 2016年6月13日 下午4:22:28
	 * @param id
	 * @param status
	 * @param fiLcsFund 
	 */
	void doAuditFiLcsFund(Long id,Byte status, FiLcsFund fiLcsFund);
	
	/**
	 * 查询审核信息
	 * @author renzhuolun
	 * @date 2016年6月13日 下午4:50:07
	 * @param searchBean
	 * @param pg
	 * @return
	 */
	List<FiLcsFund> findAuditByCondition(FiLcsFund searchBean, Page pg);
	
	/**
	 * 查询管理信息
	 * @author renzhuolun
	 * @date 2016年6月13日 下午4:50:46
	 * @param searchBean
	 * @param pg
	 * @return
	 */
	List<FiLcsFund> findOperByCondition(FiLcsFund searchBean, Page pg);
	
	/**
	 * 基金信息置顶
	 * @author renzhuolun
	 * @date 2016年6月13日 下午10:18:46
	 * @param id
	 */
	void doUpperLcsFundById(Long id);
	
	/**
	 * 修改运作信息
	 * @author renzhuolun
	 * @date 2016年6月13日 下午10:40:49
	 * @param fiLcsFund
	 */
	void doEditOperInfo(FiLcsFund fiLcsFund);
	
	/**
	 * 设为活动产品
	 * @author renzhuolun
	 * @date 2016年6月14日 上午11:30:00
	 * @param id
	 */
	void doActiveLcsFundById(Long id);
	
	/**
	 * 修改预售开始时间
	 * @author renzhuolun
	 * @date 2016年6月14日 上午11:49:55
	 * @param fiLcsFund
	 */
	void doEditStartTime(FiLcsFund fiLcsFund);
	
	/**
	 * 修改预售结束时间
	 * @author renzhuolun
	 * @date 2016年6月14日 上午11:50:10
	 * @param fiLcsFund
	 */
	void doEditEndTime(FiLcsFund fiLcsFund);
	
	/**
	 * 撤销基金项目
	 * @author renzhuolun
	 * @date 2016年6月14日 下午5:11:08
	 * @param id
	 */
	void undoFund(Long id);
	
	/**
	 * 修改基金状态页面
	 * @author renzhuolun
	 * @date 2016年7月12日 下午3:00:58
	 * @param fiLcsFund
	 */
	void doEditFundStatus(FiLcsFund fiLcsFund);
}
