package com.upg.cfsettle.foundation.core;

import java.util.List;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.cfsettle.mapping.filcs.FiLcsQuestion;

/**
 * 理财师问题service
 * @author renzhuolun
 * @date 2016年6月16日 上午10:56:27
 * @version <b>1.0.0</b>
 */
public interface IFiLcsQuestionService extends IBaseService {
	
	/**
	 * 条件查询
	 * @author renzhuolun
	 * @date 2016年6月16日 上午11:16:28
	 * @param searchBean
	 * @param page
	 * @return
	 */
	List<FiLcsQuestion> findByCondition(FiLcsQuestion searchBean, Page page);
	
	/**
	 * 添加问题
	 * @author renzhuolun
	 * @date 2016年6月16日 下午12:30:57
	 * @param lcsQuestion
	 */
	void addLcsQuestion(FiLcsQuestion lcsQuestion);
	
	/**
	 * 修改问题
	 * @author renzhuolun
	 * @date 2016年6月16日 下午12:31:12
	 * @param lcsQuestion
	 */
	void editLcsQuestion(FiLcsQuestion lcsQuestion);
	
	/**
	 * 通过主键查询
	 * @author renzhuolun
	 * @date 2016年6月16日 下午1:21:44
	 * @param id
	 * @return
	 */
	FiLcsQuestion getFiLcsQuestionById(Long id);
	
}
