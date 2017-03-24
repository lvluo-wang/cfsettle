package com.upg.ucars.basesystem.webUtil.action;



import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.model.SysStatus;
/**
 * 查询组件的初始化
 * @author cuckoo
 *
 */
public class SysStatusComponentAction extends BaseAction {
	private InputStream   statusStream;
	

	public InputStream getStatusStream() {
		return statusStream;
	}
	public void setStatusStream(InputStream statusStream) {
		this.statusStream = statusStream;
	}
	public String refresh()throws Exception{
		SysStatus  status=SessionTool.getSysStatus();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("sys_status", status);
		JSONObject obj=JSONObject.fromObject(map);
		System.out.println(obj.toString());
		statusStream = new ByteArrayInputStream(obj.toString().getBytes("UTF-8"));
		return "success";
	}
}
