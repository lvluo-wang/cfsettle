package com.upg.ucars.basesystem.autotask.core.demo;

import com.upg.ucars.basesystem.autotask.core.AbstractMemberAutoTask;
import com.upg.ucars.model.BooleanResult;

public class TaskDemoMemberA extends AbstractMemberAutoTask{

	@Override
	public BooleanResult runByMember(String memberNo) throws Exception {
		
		
		for (int i = 0; i < 20; i++) {
			System.err.println(memberNo+ ":A:" + i);
			Thread.currentThread();
			Thread.sleep(1000); 
		}
		
		if (memberNo.equals("0001")){
			
			for (int i = 0; i < 20; i++) {
				System.err.println(memberNo+ ":AA:" + i);
				Thread.currentThread();
				Thread.sleep(1000); 
			}
		}
		
		return new BooleanResult(true);
	}

}
