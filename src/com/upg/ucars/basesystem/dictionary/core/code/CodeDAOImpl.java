package com.upg.ucars.basesystem.dictionary.core.code;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.base.QueryCondition;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.dictionary.CodeMeta;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.security.UserLogonInfo;
import com.upg.ucars.util.StringUtil;

public class CodeDAOImpl extends BaseDAO<Code, Long> implements ICodeDAO {
	
	@Override
	public Class<Code> getEntityClass() {		
		return Code.class;
	}

	public CodeMeta getCodeMetaByKey(String key) throws DAOException {
		List<CodeMeta> list= this.find("FROM CodeMeta WHERE key=? ORDER BY key", new Object[]{key});
		if (list.isEmpty())
			return null;
		return list.get(0);
	}

	public List<Code> getCodesByKey(String key) throws DAOException {
		List<Code> list = this.find("FROM Code WHERE codeKey=? ORDER BY langType, codeNo", new Object[]{key});
		return list;
	}

	public List<Code> getCurrentLangCodesByKey(String key){
		String langType = SessionTool.getLocale().toString();
		return this.getSingleLangCodesByKey(key, this.getCurrentMemberNo(), langType);
	}

	public List<Code> getCurrentLangCodesByKey(String key,String[] values) throws DAOException {
		String langType = SessionTool.getLocale().toString();
		String miNo = this.getCurrentMemberNo();
		
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Code.class); 
		
		detachedCriteria.add(Restrictions.eq("codeKey", key));
		detachedCriteria.add(Restrictions.in("codeNo", values));
		detachedCriteria.add(Restrictions.eq("langType", langType));
		detachedCriteria.add(Restrictions.or(
				                               Restrictions.isNull("miNo"),
				                               Restrictions.eq("miNo", miNo)
				                                              
				                             )
		                     );
	
	
		
		return this.getHibernateTemplate().findByCriteria(detachedCriteria);	
		
	}

	
	public List<Code> getSingleLangCodesByKey(String key, String miNo, String langType)
			throws DAOException {
		//查询出指定接入机构和默认的信息
		//List<Code> list = this.find("FROM Code WHERE codeKey=? AND langType=? AND (miNo is null OR miNo=?) ORDER BY codeNo ", new Object[]{key, langType, miNo});
		
		QueryCondition qc = new QueryCondition("FROM Code ORDER BY sortNo,codeNo");
		qc.addCondition(new ConditionBean("codeKey", key));
		qc.addCondition(new ConditionBean("langType", langType));
		ConditionBean miNoCond = new ConditionBean("miNo", ConditionBean.IS_NULL, null);
		if (!StringUtil.isEmpty(miNo)){
			miNoCond.addPartner(new ConditionBean("miNo", miNo).setLogic(ConditionBean.LOGIC_OR));
		}
		qc.addCondition(miNoCond);
				
		List<Code> list = this.queryByCondition(qc, null);
		
		List<Code> miNoList = new ArrayList<Code>();
		if (!StringUtil.isEmpty(miNo)){//指定了接入机构
			for (Code code : list) {
				if (code.getMiNo()!=null || code.getMiNo() == null){
					miNoList.add(code);
				}
			}
			if (!miNoList.isEmpty())
				return miNoList;//返回指定接入机构定义的CODE信息
		}
		
		return list;//返回默认的code信息
	}
	
	public List<Code> getCodesByKeyWithCodition(String key, List<ConditionBean> condList) throws DAOException {
		QueryCondition qc = new QueryCondition("FROM Code ORDER BY langType, codeNo");
		qc.addCondition(new ConditionBean("codeKey", key));
		qc.addConditionList(condList);
		List<Code> list = this.queryByCondition(qc, null);
		return list;
	}

	public List<Code> getCurrentLangCodesByKeyWithCodition(String key, List<ConditionBean> condList) throws DAOException {
		String langType = SessionTool.getLocale().toString();
		return this.getSingleLangCodesByKeyWithCodition(key, this.getCurrentMemberNo(), langType, condList);
	}

	public List<Code> getSingleLangCodesByKeyWithCodition(String key, String miNo, String langType, List<ConditionBean> condList)
			throws DAOException {
		//查询出指定接入机构和默认的信息
		QueryCondition qc = new QueryCondition("FROM Code ORDER BY codeNo ");
		qc.addCondition(new ConditionBean("codeKey", key));
		qc.addCondition(new ConditionBean("langType", langType));
		qc.addCondition(new ConditionBean("miNo", miNo).addPartner(new ConditionBean("miNo", ConditionBean.IS_NULL, null).setField(ConditionBean.LOGIC_OR)));
		qc.addConditionList(condList);
		List<Code> list = this.queryByCondition(qc, null);
		
		List<Code> miNoList = new ArrayList<Code>();
		if (!StringUtil.isEmpty(miNo)){//指定了接入机构
			for (Code code : list) {
				if (code.getMiNo()!=null){
					miNoList.add(code);
				}
			}
			if (!miNoList.isEmpty())
				return miNoList;//返回指定接入机构定义的CODE信息
		}
		
		return list;//返回默认的code信息
	}
	

	public List<Code> getCodesByNo(String key, String codeNo)
			throws DAOException {
		List<Code> list = this.find("FROM Code WHERE codeKey=? AND codeNo=?  ORDER BY langType ", new Object[]{key, codeNo});
		return list;
	}

	public Code getCurrentLangCodeByNo(String key, String codeNo)
			throws DAOException {
		String langType = SessionTool.getLocale().toString();
		
		return this.getSingleLangCodeByNo(key, codeNo, this.getCurrentMemberNo(), langType);
	}

	public Code getSingleLangCodeByNo(String key, String codeNo, String miNo, String langType)
			throws DAOException {
		String hql = "FROM Code WHERE codeKey=:key AND codeNo=:codeNo AND (miNo=:miNo OR miNo is null) AND langType=:langType ORDER BY miNo asc";
		Map<String,Object> parameterMap = new HashMap<String,Object>(3);
		parameterMap.put("key", key);
		parameterMap.put("codeNo", codeNo);
		parameterMap.put("miNo", miNo);
		parameterMap.put("langType", langType);
				
		List list = this.queryByParam(hql, parameterMap, null);
		if (list.isEmpty()){			
			return null;
		}	
		return (Code)list.get(0);
	}

	public void deleteCodeMetaByKey(String key) throws DAOException {
		this.getHibernateTemplate().delete(this.getCodeMetaByKey(key));
		
	}

	public void deleteCodesByKey(final String key) throws DAOException {
		final String hql = "DELETE FROM Code where key =:key";
		
		
		this.getHibernateTemplate().execute(new HibernateCallback() {

			public  Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				session.createQuery(hql).setParameter("key", key).executeUpdate();
				return null;
			}
		});

	}

	public void deleteSingleLangCodesByKey(String key, String miNo, String langType)
			throws DAOException {
		List<Code> codeList = this.getSingleLangCodesByKey(key, miNo, langType);
		for (Code code : codeList) {
			this.delete(code);
		}

	}

	public void saveCodeMeta(CodeMeta meta) throws DAOException {
		this.getHibernateTemplate().save(meta);
		
	}

	public void updateCodeMeta(CodeMeta meta) throws DAOException {
		this.getHibernateTemplate().update(meta);
		
	}
	/**
	 * 获取当前用户的接入机构号
	 * @return
	 */
	private String getCurrentMemberNo(){
		UserLogonInfo user = SessionTool.getUserLogonInfo();
		if (user == null)
			return null;
		return user.getMiNo();
	}

	

}
