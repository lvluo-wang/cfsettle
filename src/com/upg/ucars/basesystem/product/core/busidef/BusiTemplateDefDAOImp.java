package com.upg.ucars.basesystem.product.core.busidef;


import java.util.LinkedList;
import java.util.List;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.product.BusiTemplateDef;

/**
 * 交易模板定义DAO实现类
 * @author alw
 *
 */
public class BusiTemplateDefDAOImp extends BaseDAO<BusiTemplateDef, Long> implements IBusiTemplateDefDAO{

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return BusiTemplateDef.class;
	}
	/**
	 * 根据接入点和产品查询交易定义信息
	 * @param prodNo
	 * @param miNo
	 * @param page
	 * @return
	 * @throws DAOException
	 */
	public List<BusiTemplateDef> queryBusiDefsByProdAndMino(String prodNo,String miNo, Page page) throws DAOException{
		// TODO Auto-generated method stub
		
		StringBuffer hqlBf = new StringBuffer("select busi from BusiTemplateDef as busi where 1=1");
		
		List<String> param = new LinkedList<String>();
		
		if(prodNo!=null&&!"".equals(prodNo))
		{
			hqlBf.append(" and busi.prodNo=?");
			param.add(prodNo);
		}
		if(miNo!=null&&!"".equals(miNo))
		{
			hqlBf.append(" and busi.miNo=?");
			param.add(miNo);
		}
		return super.find(hqlBf.toString(), param.toArray(), page);	
		
	}
	
	/**
	 * 根据交易编码查询模板定义
	 * @param busiNo
	 * @return
	 */
	public BusiTemplateDef findByBusiNo(String busiNo) throws DAOException {
		BusiTemplateDef busiDef = null;
		String[] strArr = new String[]{busiNo};
		
		String hql = "select busi from BusiTemplateDef as busi where busi.busiNo=? ";
		
		
		List<BusiTemplateDef> ls = super.find(hql,strArr);
		if(ls!=null && ls.size()>0)
			busiDef = ls.get(0);
		return busiDef;
	}
	
	/**
	 * 根据交易编码和接入点查询模板定义
	 * @param busiNo
	 * @param miNo
	 * @return
	 * @throws DAOException
	 */
	public BusiTemplateDef findByBusinoAndMino(String busiNo, String miNo)
			throws DAOException {
		
		BusiTemplateDef busiDef = null;
		String[] strArr = new String[]{busiNo,miNo};
		
		String hql = "select busi from BusiTemplateDef as busi where busi.busiNo=? and busi.miNo=? ";
		
		
		List<BusiTemplateDef> ls = super.find(hql,strArr);
		if(ls!=null && ls.size()>0)
			busiDef = ls.get(0);
		return busiDef;
	}
	
}
