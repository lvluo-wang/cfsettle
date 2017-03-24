package com.upg.demo.loan.esb;

import java.util.HashMap;
import java.util.Map;

import org.mule.api.MuleMessage;
import org.mule.api.config.MuleProperties;
import org.mule.module.client.MuleClient;

import com.upg.ucars.framework.esb.core.ESBClient;

public class ClientTest {
	public static void main(String[] args) {
		ClientTest test = new ClientTest();
		test.testSocket();
	}
	public void testSocket(){
		try {
			String name = "this is a test";
			
			MuleClient mc = ESBClient.getClientInstance();
	        Map<String, Object> props = new HashMap<String, Object>();
	        //必须等待远程响应结果
	        props.put(MuleProperties.MULE_REMOTE_SYNC_PROPERTY, Boolean.TRUE);
	        
			MuleMessage reply = ESBClient.send("socketService", name, props);
			
			if(reply != null) {
				System.out.println(reply.getPayload());
				System.out.println("over");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void testWSDL() {
		String name = "James";
		
		ESBClient.getClientInstance();
        
		MuleMessage reply = ESBClient.send("vm://wsdl", name, null);
		
		if(reply != null) {
			System.out.println(reply.getPayload());
			System.out.println("over");
		}

	}

}
