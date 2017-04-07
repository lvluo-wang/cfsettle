package com.upg.cfsettle.cust.core;

import java.util.List;

import com.upg.cfsettle.mapping.prj.CfsCust;
import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;


public interface ICfsCustService extends IBaseService {
	
	/**
	 *查询
	 * @author renzhuolun
	 * @date 2017年4月2日 下午4:26:15
	 * @param searchBean
	 * @param page
	 * @return
	 */
	List<CfsCust> findByCondition(CfsCust searchBean, Page page);
	
	/**
	 * 主键查询
	 * @author renzhuolun
	 * @date 2017年4月2日 下午4:26:31
	 * @param id
	 * @return
	 */
	CfsCust queryCfsCustById(Long id);
	
	/**
	 * 修改
	 * @author renzhuolun
	 * @date 2017年4月2日 下午4:26:50
	 * @param cust
	 */
	void updateCfsCust(CfsCust cust);
	
	/**
	 * 新增
	 * @author renzhuolun
	 * @date 2017年4月2日 下午4:27:03
	 * @param cust
	 */
	void addCfsCust(CfsCust cust);
	
	/**
	 * 删除
	 * @author renzhuolun
	 * @date 2017年4月2日 下午4:27:14
	 * @param pkId
	 */
	void deleteById(Long pkId);


	/**
	 * 员工名下的所有客户
	 * @param buserId
	 * @return
	 */
	List<CfsCust> findAllCustByBuserId(Long buserId);

}
