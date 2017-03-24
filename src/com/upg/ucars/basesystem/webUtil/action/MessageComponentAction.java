package com.upg.ucars.basesystem.webUtil.action;



import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.util.SysNewsPublisher;
/**
 * 查询组件的初始化
 * @author cuckoo
 *
 */
public class MessageComponentAction extends BaseAction {
	private InputStream   messageStream;
	
	public InputStream getMessageStream() {
		return messageStream;
	}

	public void setMessageStream(InputStream messageStream) {
		this.messageStream = messageStream;
	}

	public String message()throws Exception{
		SysNewsPublisher  spublish=SessionTool.getSysNewsPublisher();
		String message="";
		String error="";
		if(spublish!=null){
			message=spublish.getHtmlNewsContent();
		}
		ExceptionManager manager=ExceptionManager.getSessionInstance(getHttpRequest());
		if(manager!=null){
			error=manager.getHtmlErrors();
		}
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("message", message);
		map.put("errors", error);
		JSONObject obj=JSONObject.fromObject(map);
		System.out.println(obj.toString());
		
		messageStream = new ByteArrayInputStream(obj.toString().getBytes("UTF-8"));
		return "success";
	}
	public String clear()throws Exception{
		SysNewsPublisher  spublish=SessionTool.getSysNewsPublisher();
		if(spublish!=null){
			spublish.removeAll();
		}
		ExceptionManager manager=ExceptionManager.getSessionInstance(getHttpRequest());
		if(manager!=null)
			manager.removeAll();
		return null;
	}
}
