package com.upg.cfsettle.comm.core;

import java.util.List;

import com.upg.cfsettle.mapping.prj.CfsCommDetail;
import com.upg.cfsettle.mapping.prj.CfsMyCommInfo;
import com.upg.ucars.framework.base.IBaseDAO;
import com.upg.ucars.framework.base.Page;

public interface ICfsMyCommInfoDao extends IBaseDAO<CfsMyCommInfo, Long> {

	List<CfsCommDetail> findByCommDetail(CfsMyCommInfo searchBean, Page page);

}
