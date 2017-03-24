package com.upg.ucars.basesystem.dictionary.core.code;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.mapping.basesystem.dictionary.CodeMeta;
/**
 * 
 *CodeMetaDAOImpl
 *
 * @author shentuwy
 *
 */
public class CodeMetaDAOImpl extends BaseDAO<CodeMeta, Long> implements ICodeMetaDAO {

	public Class<CodeMeta> getEntityClass() {
		return CodeMeta.class;
	}

	

}
