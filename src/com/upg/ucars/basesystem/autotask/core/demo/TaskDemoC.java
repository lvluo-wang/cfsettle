package com.upg.ucars.basesystem.autotask.core.demo;

import com.upg.ucars.basesystem.autotask.core.AbstractCommonAutoTask;
import com.upg.ucars.model.BooleanResult;

public class TaskDemoC extends AbstractCommonAutoTask {

	@Override
	public BooleanResult run() throws Exception {
		for (int i = 0; i < 20; i++) {
			System.err.print("任务C:");
			System.err.println(i);
			Thread.sleep(1000); 
		}
		
		return new BooleanResult(true);
	}

}
