package com.upg.ucars.basesystem.busidate.core;

import java.util.Date;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.mapping.basesystem.busidate.BusiDate;


/**
 * 
 * IBusiDateService
 *
 * @author shentuwy
 *
 */
public interface IBusiDateService extends IBaseService{
	
	
	/**
	 * 
	 * 根据机构编号获取营业日对象
	 *
	 * @param <T>
	 * @param miNo
	 * @return
	 */
	public BusiDate getBusiDateByMino(String miNo);
	
	/**
	 * 
	 *  根据机构编号获取营业日对象
	 *
	 * @param <T>
	 * @param clz
	 * @param miNo
	 * @return
	 */
	public <T extends BusiDate> T getBusiDateByMino(Class<T> clz,String miNo);
	
	/**
	 * 
	 * 调整到上一个营业日
	 *
	 * @param miNo
	 */
	public void adjustCurDateToPreDateByMino(String miNo);
	
	/**
	 * 
	 * 调整到下一个营业日
	 *
	 * @param miNo
	 */
	public void adjustCurDateToNextDateByMino(String miNo);
	
	/**
	 * 
	 * 调整当前营业日
	 *
	 * @param miNo
	 * @param curDate
	 */
	public void adjustCurDateByMino(String miNo,Date curDate);
	
	/**
	 * 
	 * 更改营业日状态
	 *
	 * @param miNo
	 * @param sysStatus
	 */
	public void changeSysStatusByMino(String miNo,String sysStatus);
	
}
