/*
 * 源程序名称: AcctPointDAOImpl.java
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称: 账务配置-记账点
 * 
 */
package com.upg.ucars.basesystem.acctrecord.config.acctpoint;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctPoint;

/**
 * 
 * 功能说明：记账点数据库操作DAO实现
 * @author shentuwy  
 * @date Jun 27, 2011 08:46:20 AM 
 *
 */
public class AcctPointDAOImpl extends BaseDAO<AcctPoint, Long> implements IAcctPointDAO{

	@Override
	public Class getEntityClass() {
		return AcctPoint.class;
	}

}
