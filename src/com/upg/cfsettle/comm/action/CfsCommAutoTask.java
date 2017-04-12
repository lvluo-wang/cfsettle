package com.upg.cfsettle.comm.action;

import com.upg.cfsettle.comm.core.ICfsMyCommInfoService;
import com.upg.ucars.basesystem.autotask.core.AbstractMemberAutoTask;
import com.upg.ucars.model.BooleanResult;

/**
 * Created by zuobaoshi on 2017/4/12.
 */
public class CfsCommAutoTask extends AbstractMemberAutoTask {

    private ICfsMyCommInfoService commInfoService;

    @Override
    public BooleanResult runByMember(String memberNo) throws Exception {
        BooleanResult booleanResult = new BooleanResult(true);
        commInfoService.runCommTask();
        return null;
    }


    public ICfsMyCommInfoService getCommInfoService() {
        return commInfoService;
    }

    public void setCommInfoService(ICfsMyCommInfoService commInfoService) {
        this.commInfoService = commInfoService;
    }
}
