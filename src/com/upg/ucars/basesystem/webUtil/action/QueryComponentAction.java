package com.upg.ucars.basesystem.webUtil.action;



import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.base.queryComponent.QueryConfigLoader;
import com.upg.ucars.framework.base.queryComponent.QueryObject;
import com.upg.ucars.framework.base.queryComponent.SortObject;
import com.upg.ucars.util.SysNewsPublisher;
/**
 * 查询组件的初始化
 * @author cuckoo
 *
 */
public class QueryComponentAction extends BaseAction {
	InputStream jsonStream;
	
	public InputStream getJsonStream() {
		return jsonStream;
	}
	public void setJsonStream(InputStream jsonStream) {
		this.jsonStream = jsonStream;
	}
	/**
	 * 初始化查询组件，根据页面传递的组件id通过配置获取组件对象，并返回给页面解析为组件视图。
	 * @return
	 * @throws Exception
	 */
	public String query()throws Exception{
		QueryComponent queryComponent=QueryConfigLoader.getQueryComponent(getQueryComponentId());
		if(queryComponent!=null){
			List<QueryObject> list=queryComponent.getQueryList();
			for(QueryObject query:list){
				String label=query.getQueryName();
				query.setLabel(getText(label));
			}
			List<SortObject> sorts=queryComponent.getSortList();
			for(SortObject sort:sorts){
				String viewName=sort.getSortName();
				sort.setViewName(getText(viewName));
			}
		}
		JSONObject jsonObject=JSONObject.fromObject(queryComponent);
		System.out.println(jsonObject.toString());
		jsonStream=outJsonUTFStream(jsonObject);
		return "success";
	}
	public String message()throws Exception{
		SysNewsPublisher  spublish=(SysNewsPublisher)ActionContext.getContext().getSession().get("publisher");
		if(spublish==null)
			return null;
		String message=spublish.getHtmlNewsContent();
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("message", message);
		JSONObject obj=JSONObject.fromObject(map);
		System.out.println(obj.toString());
		
		jsonStream=outJsonUTFStream(obj);
		return "success";
	}

}
