package com.upg.cfsettle.prj.core;

import com.upg.cfsettle.mapping.prj.CfsPrjExt;
import com.upg.ucars.framework.base.IBaseService;

import java.util.List;

/**
 * Created by zuo on 2017/4/1.
 */
public interface IPrjExtService extends IBaseService {

    void addPrjExt(CfsPrjExt prjExt);

    CfsPrjExt getPrjExtByPrjId(Long prjId);


    }
