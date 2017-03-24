package com.upg.ucars.basesystem.dictionary.core.code;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upg.ucars.framework.base.BaseDAO;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.dictionary.CodeMeta;
import com.upg.ucars.mapping.basesystem.dictionary.FieldCodeMap;

public class FieldCodeMapDAOImpl extends BaseDAO<FieldCodeMap, Long> implements IFieldCodeMapDAO {

	public void deleteFieldCodeMapsByKey(String key) throws DAOException {
		List<FieldCodeMap> list = this.getFieldCodeMapsByKey(key);
		for (FieldCodeMap fieldCodeMap : list) {
			this.delete(fieldCodeMap);
		}

	}

	public List<FieldCodeMap> getFieldCodeMapsByKey(String key)
			throws DAOException {
		List<FieldCodeMap> list = this.find("FROM FieldCodeMap WHERE codeKey=?", new String[]{key});
		return list;
	}

	public CodeMeta getCodeMetaByField(String tableName, String fieldName)
			throws DAOException {
		String hql = "SELECT meta FROM CodeMeta meta, FieldCodeMap map WHERE map.tableName=:tableName AND map.fieldName=:fieldName AND meta.key= map.codeKey";
		
		Map<String,Object> parameterMap = new HashMap<String,Object>(2);
		parameterMap.put("tableName", tableName);
		parameterMap.put("fieldName", fieldName);
		
		List list = this.queryByParam(hql, parameterMap, null);
		CodeMeta meta=null;
		if(list!=null&&list.size()>0){
			meta=(CodeMeta)list.get(0);	
		}
		return meta;
	}

	public Class<FieldCodeMap> getEntityClass() {		
		return FieldCodeMap.class;
	}
	
	
	

}
