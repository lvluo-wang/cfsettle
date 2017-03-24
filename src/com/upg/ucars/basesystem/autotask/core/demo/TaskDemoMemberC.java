package com.upg.ucars.basesystem.autotask.core.demo;

import com.upg.ucars.basesystem.autotask.core.AbstractMemberAutoTask;
import com.upg.ucars.model.BooleanResult;

public class TaskDemoMemberC extends AbstractMemberAutoTask{

	@Override
	public BooleanResult runByMember(String memberNo) throws Exception {
		
		for (int i = 0; i < 20; i++) {
			System.err.println(memberNo+ ":C:" + i);
			Thread.currentThread().sleep(1000); 
		}
		
		return new BooleanResult(true);
	}

}
