/*
 * 源程序名称: ListResult.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：　外部服务接口
 * 
 */

package com.upg.ucars.framework.base;

import java.util.List;

import com.upg.ucars.model.response.BaseResult;

public class ListResult<E> extends BaseResult{
	
	/**
	 * 结果数据列表
	 */
	private List<E> listData;
	
	/**
	 * 分页信息
	 */	
	private Page page;

	public List<E> getListData() {
		return listData;
	}

	public void setListData(List<E> listData) {
		this.listData = listData;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
