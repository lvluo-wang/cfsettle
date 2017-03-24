package com.upg.ucars.basesystem.autotask.core.demo;

import com.upg.ucars.basesystem.autotask.core.AbstractMemberAutoTask;
import com.upg.ucars.model.BooleanResult;

public class TaskDemoMemberB extends AbstractMemberAutoTask{

	@Override
	public BooleanResult runByMember(String memberNo) throws Exception {
		
		for (int i = 0; i < 20; i++) {
			System.err.println(memberNo+ ":B:" + i);
			Thread.currentThread().sleep(1000); 
		}
		
		return new BooleanResult(true);
	}

}
