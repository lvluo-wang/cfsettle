package com.upg.ucars.basesystem.product.core.busidef;

import java.util.List;

import com.upg.ucars.framework.base.Page;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.product.BusiTemplateDef;

public class BusiTemplateDefServiceImp implements IBusiTemplateDefService {

	private IBusiTemplateDefDAO busiDefDao=null;
	
	
	public IBusiTemplateDefDAO getBusiDefDao() {
		return busiDefDao;
	}



	public void setBusiDefDao(IBusiTemplateDefDAO busiDefDao) {
		this.busiDefDao = busiDefDao;
	}



	public Long createBusiTemplateDef(BusiTemplateDef busiDef)
			throws ServiceException, DAOException {
		// TODO Auto-generated method stub
		return busiDefDao.save(busiDef);
	}



	public BusiTemplateDef findUsingBusiDef(String busiNo, String miNo)
			throws ServiceException, DAOException {
		// TODO Auto-generated method stub
		BusiTemplateDef btd = busiDefDao.findByBusinoAndMino(busiNo, miNo);
		if(btd==null)
		   btd = busiDefDao.findByBusiNo(busiNo);
		return btd;
	}



	public void modifyBusiTemplateDef(BusiTemplateDef busiDef)
			throws ServiceException, DAOException {
		// TODO Auto-generated method stub
		busiDefDao.update(busiDef);
	}


	public List<BusiTemplateDef> queryBusiDefsByProdAndMino(String prodNo,
			String miNo, Page page) throws DAOException {
		// TODO Auto-generated method stub
		return busiDefDao.queryBusiDefsByProdAndMino(prodNo, miNo, page);
	}




}
