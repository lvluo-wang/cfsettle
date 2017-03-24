package com.upg.ucars.basesystem.autocode.core.codeconfig;

import java.util.List;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.product.AutoCodeConfig;

/**
 * 编码配置DAO实现
 * @author alw
 *
 */
public class AutoCodeConfigDAOImp extends BaseDAO<AutoCodeConfig, Long> implements IAutoCodeConfigDAO{

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return AutoCodeConfig.class;
	}
	/**
	 * 获取接入点的编码配置
	 * @param miNo
	 * @param page
	 * @return
	 */
	public List<AutoCodeConfig> findAutoGenCodesByMiNo(String miNo,Page page) throws DAOException{
		// TODO Auto-generated method stub
		
		StringBuffer hqlBf = new StringBuffer("select code from AutoCodeConfig as code where 1=1");
		Object[] param = null;
		if(miNo!=null&&!"".equals(miNo))
		{
			hqlBf.append(" and code.miNo=?");
			param= new String[]{miNo};
		}
		return super.find(hqlBf.toString(), param, page);	
		
	}
	/**
	 * 根据编码的key和接入点查询编码配置
	 * @param codeKey
	 * @param miNo
	 * @return
	 * @throws DAOException
	 */
	public AutoCodeConfig findByKeyAndMino(String codeKey, String miNo)
			throws DAOException {
		
		AutoCodeConfig codeConfig = null;
		String[] strArr = new String[]{codeKey,miNo};
		
		String hql = "select code from AutoCodeConfig as code where code.codeKey=? and code.miNo=?";
		
		
		List<AutoCodeConfig> ls = super.find(hql,strArr);
		if(ls!=null && ls.size()>0)
			codeConfig = ls.get(0);
		return codeConfig;
		
	}
	/**
	 * 根据编码的key查询编码配置
	 * @param codeKey
	 * @param miNo
	 * @return
	 */
	public AutoCodeConfig findByCodeKey(String codeKey) throws DAOException {
		AutoCodeConfig codeConfig = null;
		String[] strArr = new String[]{codeKey};
		
		String hql = "select code from AutoCodeConfig as code where code.codeKey=? ";
		
		
		List<AutoCodeConfig> ls = super.find(hql,strArr);
		if(ls!=null && ls.size()>0)
			codeConfig = ls.get(0);
		return codeConfig;
	}

}
