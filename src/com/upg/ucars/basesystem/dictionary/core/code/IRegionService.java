package com.upg.ucars.basesystem.dictionary.core.code;

import java.util.List;

import com.upg.ucars.basesystem.dictionary.model.RegionModel;
import com.upg.ucars.framework.exception.DAOException;

/**
 * 区域信息服务。
 * <br>管理国家、省、市等信息 
 *
 * @author shentuwy
 */
public interface IRegionService {
	/**
	 * 获取国家区域信息
	 * @return
	 * @throws DAOException
	 */
	List<RegionModel> getCountrys() throws DAOException;
	/**
	 * 获取省份区域信息
	 *
	 * @param countryNo
	 * @return
	 * @throws DAOException
	 */
	List<RegionModel> getProvinces(String countryNo) throws DAOException;
	/**
	 * 获取城市区域信息
	 *
	 * @param provinceNo
	 * @return
	 * @throws DAOException
	 */
	List<RegionModel> getCitys(String provinceNo) throws DAOException;
	
	
	

}
