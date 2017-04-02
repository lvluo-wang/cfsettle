package com.upg.cfsettle.prj.core;

import com.upg.cfsettle.mapping.prj.CfsPrj;
import com.upg.cfsettle.mapping.prj.CfsPrjExt;
import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by zuo on 2017/3/30.
 *
 */
public interface IPrjService extends IBaseService {

    List<Map<String, Object>> findByCondition(CfsPrj searchBean, Page page);

    void addPrjApply(CfsPrj prj);

    void savePrjAndPrjExt(CfsPrj prj, CfsPrjExt prjExt);

    CfsPrj getPrjById(Long id);

    void updatePrjAndPrjExt(CfsPrj prj, CfsPrjExt prjExt);

    void auditPrjAndPrjExt(CfsPrj prj, CfsPrjExt prjExt);

	List<CfsPrj> findPrjByCondition(CfsPrj cfsPrj, Page page);
}