package com.upg.cfsettle.comm.action;

import com.upg.cfsettle.comm.core.ICfsMyCommInfoService;
import com.upg.ucars.basesystem.autotask.core.AbstractMemberAutoTask;
import com.upg.ucars.model.BooleanResult;

/**
 * Created by zuobaoshi on 2017/4/12.
 */
public class CfsCommAutoTask extends AbstractMemberAutoTask {


    private ICfsMyCommInfoService cfsMyCommInfoService;

    @Override
    public BooleanResult runByMember(String memberNo) throws Exception {
        BooleanResult booleanResult = new BooleanResult(true);
        cfsMyCommInfoService.runCommTask();
        return booleanResult;
    }

    public ICfsMyCommInfoService getCfsMyCommInfoService() {
        return cfsMyCommInfoService;
    }

    public void setCfsMyCommInfoService(ICfsMyCommInfoService cfsMyCommInfoService) {
        this.cfsMyCommInfoService = cfsMyCommInfoService;
    }
}
