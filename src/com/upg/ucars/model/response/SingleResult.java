/*
 * 源程序名称: SignleResult.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：　外部服务接口
 * 
 */

package com.upg.ucars.model.response;

public class SingleResult<E> extends BaseResult{
	
	/**
	 * 数据对象
	 */
	private E data;

	public E getData() {
		return data;
	}

	public void setData(E data) {
		this.data = data;
	}
	
}
