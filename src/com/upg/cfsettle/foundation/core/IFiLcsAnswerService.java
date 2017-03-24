package com.upg.cfsettle.foundation.core;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.cfsettle.mapping.filcs.FiLcsAnswer;

/**
 * 理财师答案service
 * @author renzhuolun
 * @date 2016年6月16日 上午10:56:01
 * @version <b>1.0.0</b>
 */
public interface IFiLcsAnswerService extends IBaseService {
	
	/**
	 * 条件查询
	 * @author renzhuolun
	 * @date 2016年6月16日 上午11:14:23
	 * @param searchBean
	 * @param page
	 * @return
	 */
	List<FiLcsAnswer> findByCondition(FiLcsAnswer searchBean, Page page);
	
	/**
	 * 新增
	 * @author renzhuolun
	 * @date 2016年6月16日 下午4:53:14
	 * @param lcsAnswer
	 */
	void addLcsAnswer(FiLcsAnswer lcsAnswer);
	
	/**
	 * 主键查询
	 * @author renzhuolun
	 * @date 2016年6月16日 下午4:53:26
	 * @param id
	 * @return
	 */
	FiLcsAnswer getFiLcsAnswerById(Long id);
	
	/**
	 * 修改
	 * @author renzhuolun
	 * @date 2016年6月16日 下午4:53:38
	 * @param lcsAnswer
	 */
	void editLcsAnswer(FiLcsAnswer lcsAnswer);
}
