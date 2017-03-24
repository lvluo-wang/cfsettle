/*
 * 源程序名称: IAcctItemDAO.java
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称: 账务配置-记账项
 * 
 */
package com.upg.ucars.basesystem.acctrecord.config.acctitem;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctItem;

/**
 * 
 * 功能说明：记账项数据库操作接口
 * @author shentuwy  
 * @date Jun 27, 2011 08:46:20 AM 
 *
 */
public interface IAcctItemDAO extends IBaseDAO<AcctItem, Long>{

	/**
	 * 根据记账点ID查询记账项
	 * @param acctPointId
	 * @return
	 * @throws DAOException
	 */
	public List<AcctItem> queryAcctItemByAcctPointId(Long acctPointId) throws DAOException;
}
