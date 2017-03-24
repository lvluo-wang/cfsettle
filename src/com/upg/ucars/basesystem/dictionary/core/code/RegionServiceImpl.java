package com.upg.ucars.basesystem.dictionary.core.code;

import java.util.ArrayList;
import java.util.List;

import com.upg.ucars.basesystem.dictionary.model.RegionModel;
import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.constant.CodeKey;
import com.upg.ucars.framework.exception.DAOException;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.model.ConditionBean;

public class RegionServiceImpl implements IRegionService {
	private ICodeDAO codeDAO;//spring 注入

	public List<RegionModel> getCountrys() throws DAOException {
		List<Code> list = DictionaryUtil.getCodesByKey(CodeKey.COUNTRY);
		
		ArrayList<RegionModel> regionList = new ArrayList<RegionModel>(list.size());
		for (Code code : list) {
			regionList.add(new RegionModel(code.getCodeNo(), code.getCodeName(), code.getCodeNo()));
		}
		
		return regionList;
	}

	public List<RegionModel> getProvinces(String countryNo) throws DAOException {
		ArrayList<ConditionBean> condList = new ArrayList<ConditionBean>(1);
		condList.add(new ConditionBean("codeNo", ConditionBean.LIKE, countryNo+"%"));
		
		List<Code> list = codeDAO.getCodesByKeyWithCodition(CodeKey.PROVINCE, condList);
		
		ArrayList<RegionModel> regionList = new ArrayList<RegionModel>(list.size());
		for (Code code : list) {
			regionList.add(new RegionModel(code.getCodeNo(), code.getCodeName(), code.getCodeNo()));
		}
		
		return regionList;
	}

	public List<RegionModel> getCitys(String provinceNo) throws DAOException {
		ArrayList<ConditionBean> condList = new ArrayList<ConditionBean>(1);
		condList.add(new ConditionBean("codeNo", ConditionBean.LIKE, provinceNo+"%"));
		
		List<Code> list = codeDAO.getCodesByKeyWithCodition(CodeKey.CITY, condList);
		
		ArrayList<RegionModel> regionList = new ArrayList<RegionModel>(list.size());
		for (Code code : list) {
			regionList.add(new RegionModel(code.getCodeNo(), code.getCodeName(), code.getCodeNo()));
		}
		
		return regionList;
	}

	public ICodeDAO getCodeDAO() {
		return codeDAO;
	}

	public void setCodeDAO(ICodeDAO codeDAO) {
		this.codeDAO = codeDAO;
	}
	
	
	

}
