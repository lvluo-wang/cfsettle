/*
 * 源程序名称: IAcctRecordBodyDAO.java
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称: 账务配置-分录结构体
 * 
 */
package com.upg.ucars.basesystem.acctrecord.config.acctrecordbody;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctRecordBody;

/**
 * 
 * 功能说明：分录结构体数据库操作接口
 * @author shentuwy  
 * @date Jun 27, 2011 08:46:20 AM 
 *
 */
public interface IAcctRecordBodyDAO extends IBaseDAO<AcctRecordBody, Long>{

	/**
	 * 根据接入点号和组号获取最大序号
	 * @param miNo 接入点
	 * @param groupNo 组号
	 * @return
	 * @throws DAOException
	 */
	public int getMaxSerialNoByGroupNo(String miNo, String groupNo) throws DAOException;
	
	/**
	 * 自动调整序号
	 * @param miNo 接入点
	 * @param groupNo 组号
	 * @param isPlus 是否正加
	 * @param offset 偏移量
	 * @param startSerialNo 开始序号(包括当前值)
	 * @param endSerialNo 结束序号(包括当前值)
	 * @throws DAOException
	 */
	public void autoAdjustSerialNo(String miNo, String groupNo, boolean isPlus, int offset, int startSerialNo, int endSerialNo) throws DAOException;
}
