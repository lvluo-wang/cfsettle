package com.upg.cfsettle.foundation.core;

import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.SysBaseDao;
import com.upg.ucars.util.DateTimeUtil;
import com.upg.cfsettle.mapping.filcs.FiLcsFund;

/**
 * 理财师dao
 * @author renzhuolun
 * @date 2016年6月6日 上午10:56:23
 * @version <b>1.0.0</b>
 */
@Dao
public class FiLcsFundDaoImpl extends SysBaseDao<FiLcsFund, Long>implements IFiLcsFundDao {

	@Override
	public Long addFiLcsFund(FiLcsFund fiLcsFund) {
		fiLcsFund.setCtime(DateTimeUtil.getNowDateTime());
		fiLcsFund.setMtime(DateTimeUtil.getNowDateTime());
		fiLcsFund.setFundStatus(Byte.valueOf("1"));
		fiLcsFund.setTop(Byte.valueOf("0"));
		fiLcsFund.setActivity(Byte.valueOf("0"));
//		fiLcsFund.setEndTime(DateTimeUtil.getSpecifiedDayAfter(fiLcsFund.getEndTime()));
		return this.save(fiLcsFund);
	}
}
