package com.upg.cfsettle.foundation.core;

import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.cfsettle.mapping.filcs.FiLcsFund;

public interface IFiLcsFundDao extends IBaseDAO<FiLcsFund, Long> {
	
	Long addFiLcsFund(FiLcsFund fiLcsFund);
}
