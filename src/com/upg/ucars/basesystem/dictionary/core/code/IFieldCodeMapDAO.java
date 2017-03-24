package com.upg.ucars.basesystem.dictionary.core.code;

import java.util.List;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.dictionary.CodeMeta;
import com.upg.ucars.mapping.basesystem.dictionary.FieldCodeMap;

/**
 * 对FieldCodeMap数据访问功能
 *
 * @author shentuwy
 */
public interface IFieldCodeMapDAO extends IBaseDAO<FieldCodeMap, Long> {
	
	public void deleteFieldCodeMapsByKey(String key)throws DAOException;
	
	
	public List<FieldCodeMap> getFieldCodeMapsByKey(String key)throws DAOException;
	
	
	public CodeMeta getCodeMetaByField(String tableName, String fieldName)throws DAOException;

}
