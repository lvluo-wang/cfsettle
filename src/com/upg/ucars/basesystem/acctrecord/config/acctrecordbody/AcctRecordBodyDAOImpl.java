/*
 * 源程序名称: AcctRecordBodyDAOImpl.java
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称: 账务配置-分录结构体
 * 
 */
package com.upg.ucars.basesystem.acctrecord.config.acctrecordbody;

import java.util.List;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctRecordBody;

/**
 * 
 * 功能说明：分录结构体数据库操作DAO实现
 * @author shentuwy  
 * @date Jun 27, 2011 08:46:20 AM 
 *
 */
public class AcctRecordBodyDAOImpl extends BaseDAO<AcctRecordBody, Long> implements IAcctRecordBodyDAO{

	@Override
	public Class getEntityClass() {
		return AcctRecordBody.class;
	}

	public int getMaxSerialNoByGroupNo(String miNo, String groupNo)
			throws DAOException {
		String hql = "select max(serialNo) from AcctRecordBody body where body.miNo=? and body.groupNo=?";
		List list = this.find(hql, new Object[]{miNo, groupNo});
		if(list.size() > 0 && list.get(0) != null){
			return Integer.parseInt(list.get(0).toString());
		}
		return 0;
	}

	public void autoAdjustSerialNo(String miNo, String groupNo, boolean isPlus,
			int offset, int startSerialNo, int endSerialNo) throws DAOException {
		String opr = isPlus ? "+" : "-";
		String hql = "update AcctRecordBody body set body.serialNo=body.serialNo"+opr+"? where body.miNo=? and body.groupNo=? ";
		hql += "and body.serialNo>=? and body.serialNo<=? ";
		this.batchUpdate(hql, new Object[]{offset,miNo,groupNo,startSerialNo,endSerialNo});
	}
}
