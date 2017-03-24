package com.upg.demo.loan.esb;

import com.upg.ucars.framework.esb.core.IESBServer;
import com.upg.ucars.util.SourceTemplate;

public class Server {
	public static void main(String[] args) {
		IESBServer esbServer = (IESBServer)SourceTemplate.getBean("muleServer");
		esbServer.start();
	}

}
