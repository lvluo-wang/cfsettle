/*******************************************************************************
 * Leadmind Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Nov 6, 2008
 *******************************************************************************/

package com.upg.ucars.basesystem.acctrecord.analyse;

import org.springframework.orm.hibernate3.HibernateTemplate;

import com.upg.ucars.basesystem.acctrecord.runtime.AcctRecordInfoDAO;
import com.upg.ucars.util.SourceTemplate;

public class AcctAnalyseFactory {
	private static HibernateTemplate ht = SourceTemplate.getHibernateTemplate();
	

	public static AcctTranAnaDAO getAcctTranDAO() {
		AcctTranAnaDAO dao = new AcctTranAnaDAO();
		dao.setHibernateTemplate(ht);
		return dao;
	}

	public static AcctPointAnaDAO getAcctPointDAO() {
		AcctPointAnaDAO dao = new AcctPointAnaDAO();
		dao.setHibernateTemplate(ht);
		return dao;
	}
	
	public static AcctRecordInfoDAO getAcctRecordInfoDAO() {
		AcctRecordInfoDAO dao = new AcctRecordInfoDAO();
		dao.setHibernateTemplate(ht);
		return dao;
	}

}
