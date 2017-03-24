package com.upg.ucars.basesystem.dictionary.core.code;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.upg.ucars.framework.base.BaseService;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.mapping.basesystem.dictionary.CodeMeta;
import com.upg.ucars.mapping.basesystem.dictionary.DictArea;

public class CodeClientServiceImpl extends BaseService implements
		ICodeClientService {
	
	private ICodeDAO codeDAO;//spring 注入
	private IFieldCodeMapDAO fieldCodeMapDAO; //spring 注入
	
	@Autowired
	private IDictTradeDAO dictTradeDAO;
	
	@Autowired
	private IDictAreaDAO dictAreaDAO;

	public List<Code> getCodesByKey(String codeKey) {
		List<Code> list = this.codeDAO.getCurrentLangCodesByKey(codeKey);
		return list;
	}
	
	
	public List<Code> getCodesByKey(String codeKey,String[] values) {
		List<Code> list = this.codeDAO.getCurrentLangCodesByKey(codeKey,values);
		return list;
	}
	

	public String getCodeNameByKey(String codeKey, String codeNo) {
		Code code = this.codeDAO.getCurrentLangCodeByNo(codeKey, codeNo);
		
		return code==null ? null: code.getCodeName();
		
	}

	public List<Code> getCodesByField(String tableName, String fieldName) {
		List<Code> list=new ArrayList<Code>();
		CodeMeta meta=this.fieldCodeMapDAO.getCodeMetaByField(tableName, fieldName);
		if(meta!=null){
			String key = meta.getKey();
			list=this.getCodesByKey(key);
		}
		return list;
	}

	public String getCodeNameByField(String tableName, String fieldName,
			String codeNo) {
		CodeMeta meta=this.fieldCodeMapDAO.getCodeMetaByField(tableName, fieldName);
		String name=codeNo;
		if(meta!=null){
			String key = meta.getKey();
			name = this.getCodeNameByKey(key, codeNo);
		}
		return name;
	}

	public ICodeDAO getCodeDAO() {
		return codeDAO;
	}

	public void setCodeDAO(ICodeDAO codeDAO) {
		this.codeDAO = codeDAO;
	}

	public IFieldCodeMapDAO getFieldCodeMapDAO() {
		return fieldCodeMapDAO;
	}

	public void setFieldCodeMapDAO(IFieldCodeMapDAO fieldCodeMapDAO) {
		this.fieldCodeMapDAO = fieldCodeMapDAO;
	}


	public List<DictArea> getProvinceList() {
		return this.dictAreaDAO.getProvinceList();
	}
	
	public List<DictArea> getAreaListByPid(Long pid) {
		return this.dictAreaDAO.getAreaListByPid(pid);
	}
	
	public List<DictArea> getAreaListByCode(String code) {
		return this.dictAreaDAO.getAreaListByCode(code);
	}

}
