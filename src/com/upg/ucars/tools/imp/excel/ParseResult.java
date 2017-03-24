package com.upg.ucars.tools.imp.excel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * 
 * 功能说明：TODO(excel文件解析结果)
 * @author shentuwy  
 * @date 2012-1-29 下午2:05:59 
 *
 */
public class ParseResult implements Serializable {
	/**配置信息*/
	ETOObject config; 
	/**解析成功的数据结合*/
	List<Object> dataList=new ArrayList<Object>();
	/**标题头部映射关系*/
	HashMap<String,String> headMap=new HashMap<String,String>();
	/**解析过程中错误集合*/
	List<String> errorList=new ArrayList<String>();
	public ParseResult() {
	}

	public List<String> getErrorList() {
		return errorList;
	}
	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}
	public ETOObject getConfig() {
		return config;
	}
	public void setConfig(ETOObject config) {
		this.config = config;
	}

	public List<Object> getDataList() {
		return dataList;
	}

	public void setDataList(List<Object> dataList) {
		this.dataList = dataList;
	}

	public HashMap<String, String> getHeadMap() {
		return headMap;
	}

	public void setHeadMap(HashMap<String, String> headMap) {
		this.headMap = headMap;
	}
	
}
