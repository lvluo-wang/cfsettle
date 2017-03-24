/*
 * 源程序名称: SubsystemDAOImpl.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 技术平台：XCARS
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.basesystem.security.core.subsystem;


import java.util.HashMap;
import java.util.List;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.mapping.basesystem.security.Subsystem;

public class SubsystemDAOImpl extends BaseDAO<Subsystem, Long> implements
		ISubsystemDAO {
	@Override
	public Class getEntityClass() {
		return Subsystem.class;
	}

	public List<Subsystem> findAllSubsystem() {
		String hql="from Subsystem";
		return find(hql);
	}

	public List<Subsystem> getSubsystemByStatus(String status) {
		String hql="from Subsystem s where s.subsysStatus=:status";
		HashMap map=new HashMap();
		map.put("status", status);
		return queryByParam(hql,map,null);
	}

}
