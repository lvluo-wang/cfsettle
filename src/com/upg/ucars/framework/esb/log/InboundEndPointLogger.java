package com.upg.ucars.framework.esb.log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.mule.api.MuleEvent;
import org.mule.api.MuleException;
import org.mule.api.MuleMessage;

/**
 * 入站端点日志
 * 
 * @author shentuwy
 * @date 2011-9-20
 **/
public class InboundEndPointLogger extends AbstractEndPointLogger {
	
	protected List<String> simpleLogProps = addSimpleLogProp();
	
	@Override
	public void log(MuleEvent event) {
		StringBuffer logsb = new StringBuffer("inbound message:").append("\r\n");
    	MuleMessage muleMessage = event.getMessage();
		Object value = null;
		String propName = null;
		if(LOG_PROP_LIMIT_SIMPLE.equals(this.getLogLimit())){
			for(String pName : simpleLogProps){
				value = muleMessage.getInboundProperty(pName);
				logsb.append(propName+"=").append(value).append("\r\n");
			}
		} else if(LOG_PROP_LIMIT_ALL.equals(this.getLogLimit())){
			Set<String> propertyNameSet = muleMessage.getInboundPropertyNames();
			Iterator<String> it = propertyNameSet.iterator();
			for(;it.hasNext();){
				propName = it.next();
				value = muleMessage.getInboundProperty(propName);
				logsb.append(propName+"=").append(value).append("\r\n");
			}
		}else if(LOG_PROP_LIMIT_CUSTOM.equals(this.getLogLimit())){
			String customProps = this.getLogProps();
			if(customProps == null || "".equals(customProps))
				return;
			String[] customPropArr = customProps.split(",");
			if(customPropArr != null && customPropArr.length > 0){
				for(String pName : customPropArr){
					value = muleMessage.getInboundProperty(pName);
					logsb.append(propName+"=").append(value).append("\r\n");
				}
			}
		}
		try {
			logsb.append("request.message=").append(event.getMessageAsString()).append("\r\n");
		} catch (MuleException e) {
			e.printStackTrace();
		}
		this.logWithLevel(logsb.toString());
	}
    
	private static List<String> addSimpleLogProp(){
		List<String> simpleLogProps = new ArrayList<String>();
		simpleLogProps.add("MULE_ORIGINATING_ENDPOINT");
		simpleLogProps.add("MULE_REMOTE_CLIENT_ADDRESS");
		simpleLogProps.add("http.request");
		simpleLogProps.add("http.method");
		simpleLogProps.add("Content-Type");
		simpleLogProps.add("Content-Length");
		return simpleLogProps;
	}
}