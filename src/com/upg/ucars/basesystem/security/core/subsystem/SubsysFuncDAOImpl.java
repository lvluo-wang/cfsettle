/*
 * 源程序名称: SubsysFuncDAOImpl.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 技术平台：XCARS
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.ucars.basesystem.security.core.subsystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.mapping.basesystem.security.ReSubsysFunc;
import com.upg.ucars.mapping.basesystem.security.Sysfunc;
import com.upg.ucars.util.CommonUtil;

public class SubsysFuncDAOImpl extends BaseDAO<ReSubsysFunc, Long>
		implements ISubsysFuncDAO {

	@Override
	public Class getEntityClass() {
		return ReSubsysFunc.class;
	}

	public List<Sysfunc> getSysfuncBySubsysId(Long subsysId)
			throws DAOException {
		String hql="select f  from ReSubsysFunc s,Sysfunc f where s.funcId=f.funcId and s.subsysId=:subsysId";
		HashMap map=new HashMap();
		map.put("subsysId", subsysId);
		return queryByParam(hql,map,null);
	}

	public ReSubsysFunc findReSubsysFunc(Long subsysId, Long funcId)
			throws DAOException {
		String hql="from ReSubsysFunc s where s.subsysId=:subsysId and s.funcId=:funcId";
		HashMap map=new HashMap();
		map.put("subsysId", subsysId);
		map.put("funcId", funcId);
		List list=queryByParam(hql,map,null);
		ReSubsysFunc re=null;
		if(list!=null&&list.size()>0){
			re=(ReSubsysFunc)list.get(0);
		}
		return re;
	}

	public List<Sysfunc> getSysfuncBySubsysId(List<Long> sysIdList)
			throws DAOException {
		String hql="select f  from ReSubsysFunc s,Sysfunc f where s.funcId=f.funcId and s.subsysId in (:subsysId)";

		List<Sysfunc> list=new ArrayList<Sysfunc>();
		try{
			if(sysIdList!=null&&sysIdList.size()>0){
				Long[] sysIds=new Long[sysIdList.size()];
				for(int i=0;i<sysIdList.size();i++){
					sysIds[i]=sysIdList.get(i);
				}
				Map<String, Object> parameterMap = new HashMap<String, Object>(1);
				parameterMap.put("subsysId", sysIds);
				
				list=super.queryByParam(hql.toString(), parameterMap, null);
				list=CommonUtil.removeDuplicate(list);
			}
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql.toString()}, e);
		}
		
		return list;

	}

	public List<Sysfunc> getSubMenuList(Long subsysId, Long parentFuncId)
			throws DAOException {
		String hql="select f  from ReSubsysFunc s,Sysfunc f where s.funcId=f.funcId and s.subsysId=:subsysId and f.parentFuncId=:parentFuncId and f.funcType in ('1','0') " +
				" order by f.sortNo";
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("subsysId", subsysId);
		map.put("parentFuncId", parentFuncId);
		return queryByParam(hql,map,null);
	}

	public List<Sysfunc> getMenuSysfuncBySubsysId(Long subsysId)
			throws DAOException {
		String hql="select f  from ReSubsysFunc s,Sysfunc f where s.funcId=f.funcId and s.subsysId=:subsysId and f.funcType in (:funcType)";
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("subsysId", subsysId);
		List<String> funcTypeList = new ArrayList<String>(2);
		funcTypeList.add(Sysfunc.FUNC_TYPE_MENU);
		funcTypeList.add(Sysfunc.FUNC_TYPE_MENU_GROUP);
		map.put("funcType", funcTypeList);
		return queryByParam(hql,map,null);
	}

	public boolean hasSubSysRefFunc(Long funcId) throws DAOException {
		String hql="select count(*) from ReSubsysFunc t where t.funcId=:funcId";
		try{
			int count=0;
			Map<String, Object> parameterMap = new HashMap<String, Object>(1);
			parameterMap.put("funcId", funcId);
			List list = super.queryByParam(hql, parameterMap, null);
			count=Integer.parseInt(""+list.get(0));
			return count>0;
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return false;
	}

	public boolean hasFunc(Long subsysId) throws DAOException {
		String hql="select count(*) from ReSubsysFunc t where t.subsysId=:subsysId";
		try{
			int count=0;
			Map<String, Object> parameterMap = new HashMap<String, Object>(1);
			parameterMap.put("subsysId", subsysId);
			List list = super.queryByParam(hql, parameterMap, null);
			count=Integer.parseInt(""+list.get(0));
			return count>0;
		}catch(Exception e){
			ExceptionManager.throwException(DAOException.class, ErrorCodeConst.DB_OPERATION_ERROR, new String[]{hql}, e);
		}
		return false;
	}
}
