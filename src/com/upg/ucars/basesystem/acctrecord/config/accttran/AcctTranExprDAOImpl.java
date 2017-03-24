/*
 * 源程序名称: AcctTranExprDAOImpl.java
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称: 账务配置-记账交易表达式
 * 
 */
package com.upg.ucars.basesystem.acctrecord.config.accttran;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctTranExpr;

/**
 * 
 * 功能说明：记账交易表达式数据库操作DAO实现
 * @author shentuwy  
 * @date Jun 27, 2011 08:46:20 AM 
 *
 */
public class AcctTranExprDAOImpl extends BaseDAO<AcctTranExpr, Long> implements IAcctTranExprDAO{

	@Override
	public Class getEntityClass() {
		return AcctTranExpr.class;
	}

}
