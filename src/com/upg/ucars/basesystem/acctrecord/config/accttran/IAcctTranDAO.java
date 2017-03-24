/*
 * 源程序名称: IAcctTranDAO.java
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称: 账务配置-记账交易
 * 
 */
package com.upg.ucars.basesystem.acctrecord.config.accttran;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctTran;

/**
 * 
 * 功能说明：记账交易数据库操作接口
 * @author shentuwy  
 * @date Jun 27, 2011 08:46:20 AM 
 *
 */
public interface IAcctTranDAO extends IBaseDAO<AcctTran, Long>{

	/**
	 * 根据记账点ID查询记账交易
	 * @param acctPointId
	 * @return
	 * @throws DAOException
	 */
	public List<AcctTran> queryAcctTranByAcctPointId(Long acctPointId) throws DAOException;
}
