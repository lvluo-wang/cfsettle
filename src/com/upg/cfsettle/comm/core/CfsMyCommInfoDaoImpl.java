package com.upg.cfsettle.comm.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upg.cfsettle.mapping.prj.CfsCommDetail;
import com.upg.cfsettle.mapping.prj.CfsMyCommInfo;
import com.upg.ucars.framework.annotation.Dao;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.base.SysBaseDao;

@Dao
public class CfsMyCommInfoDaoImpl extends SysBaseDao<CfsMyCommInfo, Long> implements ICfsMyCommInfoDao{

	@Override
	public List<CfsCommDetail> findByCommDetail(CfsMyCommInfo searchBean, Page page) {
		StringBuffer sql = new StringBuffer("SELECT o.contract_no AS contractNo,FROM_UNIXTIME(o.invest_time) AS investTime,c.real_name AS realName,"
				+ "p.prj_name AS prjName,o.money/100 AS money,p.total_rate/100 AS totalRate,p.area_rate/100 AS areaRate,p.dept_rate/100 AS deptRate,"
				+ "p.team_rate/100 AS teamRate,p.sysuser_rate/100 AS sysuserRate FROM cfs_prj_order o JOIN cfs_prj p ON o.prj_id = p.id JOIN cfs_cust c "
				+ "ON o.cust_id = c.id where 1=1");
        Map<String,Object> param = new HashMap<String,Object>();
        if(searchBean != null){
        	Long id = searchBean.getId();
        	if(id != null){
        		sql.append(" and o.comm_id=:id");
        		param.put("id",id);
        	}
        }
        List<CfsCommDetail> reslut = (List<CfsCommDetail>) this.findListByMap(sql.toString(),param,page,CfsCommDetail.class);
        return reslut;
	}
	
}
