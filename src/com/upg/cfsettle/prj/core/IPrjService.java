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

    /**
     *
     * @return
     */
    List<CfsPrj> findPrjByStatus(Byte status);
    
    /**
     * 查询待放款项目
     * @param searchBean
     * @param pg
     * @return
     */
	List<Map<String, Object>> findLoanPrjByCondition(CfsPrj searchBean, Page pg);
	
	/**
	 * 跟新项目信息
	 * @author renzhuolun
	 * @date 2017年4月4日 下午6:24:29
	 * @param prj
	 */
	void updateCfsPrj(CfsPrj prj);

    void updatePrj(CfsPrj prj);

}
