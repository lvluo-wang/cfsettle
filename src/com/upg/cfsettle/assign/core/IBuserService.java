package com.upg.cfsettle.assign.core;

import com.upg.ucars.framework.base.IBaseService;
import com.upg.ucars.framework.base.Page;
import com.upg.ucars.mapping.basesystem.security.Buser;

import java.util.List;

/**
 * Created by zuo on 2017/4/6.
 */
public interface IBuserService extends IBaseService {

    List<BuserSale> queryBuser(Buser user, Page page);
}
