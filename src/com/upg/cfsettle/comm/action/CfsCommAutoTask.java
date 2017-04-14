package com.upg.cfsettle.comm.action;

import com.upg.cfsettle.comm.core.CfsMyCommInfoServiceImpl;
import com.upg.cfsettle.comm.core.ICfsMyCommInfoService;
import com.upg.ucars.basesystem.autotask.core.AbstractMemberAutoTask;
import com.upg.ucars.model.BooleanResult;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zuobaoshi on 2017/4/12.
 */
public class CfsCommAutoTask extends AbstractMemberAutoTask {


    private ICfsMyCommInfoService myCommInfoService = new CfsMyCommInfoServiceImpl();

    @Override
    public BooleanResult runByMember(String memberNo) throws Exception {
        BooleanResult booleanResult = new BooleanResult(true);
        myCommInfoService.runCommTask();
        return booleanResult;
    }

    public void setMyCommInfoService(ICfsMyCommInfoService myCommInfoService) {
        this.myCommInfoService = myCommInfoService;
    }
}
