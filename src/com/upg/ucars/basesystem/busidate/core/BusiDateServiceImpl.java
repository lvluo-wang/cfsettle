package com.upg.ucars.basesystem.busidate.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.XBaseService;
import com.upg.ucars.mapping.basesystem.busidate.BusiDate;
import com.upg.ucars.model.ConditionBean;


/**
 * 
 * BusiDateServiceImpl
 *
 * @author shentuwy
 *
 */
public class BusiDateServiceImpl extends XBaseService<BusiDate> implements IBusiDateService {
	
	private IBusiDateDAO busiDateDAO;

	
	public IBaseDAO<BusiDate, Long> getBaseDAO() {
		return busiDateDAO;
	}
	
	public BusiDate getBusiDateByMino(String miNo) {
		return getBusiDateByMino(BusiDate.class, miNo);
	}
	
	public <T extends BusiDate> T getBusiDateByMino(Class<T> cls, String miNo) {
		return getBusiDateByMino(cls,miNo,true);
	}
	
	/**
	 * 
	 * 根据机构编号获取营业日对象
	 *
	 * @param <T>
	 * @param cls
	 * @param miNo
	 * @param isGetDefIfNull
	 * @return
	 */
	private <T extends BusiDate> T getBusiDateByMino(Class<T> cls,String miNo,boolean isGetDefIfNull){
		T ret = null;
		if( StringUtils.isNotBlank(miNo) ){
			List<ConditionBean> conditionList = new ArrayList<ConditionBean>();
			conditionList.add(new ConditionBean("miNo",miNo));
			List<T> list = busiDateDAO.getCommonBusiDateList(cls, conditionList, null);
			if( list != null && list.size() > 0 ){
				ret = list.get(0);
			}
		}
		if( ret == null && isGetDefIfNull ){
			ret = busiDateDAO.getCommonBusiDateByNullMino(cls);
		}
		return ret;
	}
	
	public void adjustCurDateToPreDateByMino(String miNo){
		if( StringUtils.isNotBlank(miNo) ){
			BusiDate bd = getBusiDateByMino(BusiDate.class, miNo, false);
			if( bd != null ){
				bd.setNextDate(bd.getCurDate());
				bd.setCurDate(bd.getPreDate());
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(bd.getPreDate().getTime());
				cal.add(Calendar.DAY_OF_MONTH, -1);
				bd.setPreDate(cal.getTime());
				update(bd);
			}
		}
	}
	
	public void adjustCurDateToNextDateByMino(String miNo){
		if( StringUtils.isNotBlank(miNo) ){
			BusiDate bd = getBusiDateByMino(BusiDate.class, miNo, false);
			if( bd != null ){
				bd.setPreDate(bd.getCurDate());
				bd.setCurDate(bd.getNextDate());
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(bd.getNextDate().getTime());
				cal.add(Calendar.DAY_OF_MONTH, 1);
				bd.setNextDate(cal.getTime());
				update(bd);
			}
		}
	}
	
	public void adjustCurDateByMino(String miNo,Date curDate){
		if( StringUtils.isNotBlank(miNo) && curDate != null ){
			BusiDate bd = getBusiDateByMino(BusiDate.class, miNo, false);
			if( bd != null ){
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(curDate.getTime());
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND,0);
				cal.set(Calendar.MILLISECOND, 0);
				bd.setCurDate(cal.getTime());
				cal.add(Calendar.DAY_OF_MONTH,-1 );
				bd.setPreDate(cal.getTime());
				cal.add(Calendar.DAY_OF_MONTH, 2);
				bd.setNextDate(cal.getTime());
				update(bd);
			}
		}
	}
	
	public void changeSysStatusByMino(String miNo,String sysStatus){
		if( StringUtils.isNotBlank(miNo) && StringUtils.isNotBlank(sysStatus) ){
			BusiDate bd = getBusiDateByMino(BusiDate.class, miNo, false);
			if( bd != null ){
				bd.setSysStatus(sysStatus);
				update(bd);
			}
		}
	}
	
	//----------------------------------------------------
	public void setBusiDateDAO(IBusiDateDAO busiDateDAO) {
		this.busiDateDAO = busiDateDAO;
	}

}
