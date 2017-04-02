package com.upg.cfsettle.comm.core;

import java.util.List;

import com.upg.cfsettle.mapping.prj.CfsMyCommInfo;
import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;


public interface ICfsMyCommInfoService extends IBaseService {
	
	/**
	 *查询
	 * @author renzhuolun
	 * @date 2017年4月2日 下午4:26:15
	 * @param searchBean
	 * @param page
	 * @return
	 */
	List<CfsMyCommInfo> findByCondition(CfsMyCommInfo searchBean, Page page);
	
	/**
	 * 主键查询
	 * @author renzhuolun
	 * @date 2017年4月2日 下午4:26:31
	 * @param id
	 * @return
	 */
	CfsMyCommInfo queryCfsMyCommInfoById(Long id);
	
	/**
	 * 修改
	 * @author renzhuolun
	 * @date 2017年4月2日 下午4:26:50
	 * @param myCommInfo
	 */
	void updateCfsMyCommInfo(CfsMyCommInfo myCommInfo);
	
	/**
	 * 新增
	 * @author renzhuolun
	 * @date 2017年4月2日 下午4:27:03
	 * @param myCommInfo
	 */
	void addCfsMyCommInfo(CfsMyCommInfo myCommInfo);
	
	/**
	 * 删除
	 * @author renzhuolun
	 * @date 2017年4月2日 下午4:27:14
	 * @param pkId
	 */
	void deleteById(Long pkId);

}
