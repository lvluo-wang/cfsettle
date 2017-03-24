package com.upg.ucars.basesystem.security.action;

import java.io.InputStream;
import java.util.List;

import com.opensymphony.xwork2.Action;
import com.upg.ucars.basesystem.security.core.sysConfig.ISysConfigService;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.mapping.basesystem.security.SysConfig;
/**
 * 
 * 功能说明：系统配置action
 * @author shentuwy  
 * @date 2011-8-4 上午8:51:47 
 *
 */
public class SysConfigAction extends BaseAction {
	/*
	 * 记录集
	 */
	private List<SysConfig> records;
	/*
	 * 系统配置对象
	 */
	private SysConfig sysConfig;
	/*
	 * 操作服务
	 */
	private ISysConfigService sysConfigService;
	/*
	 * json数据
	 */
	private InputStream jsonStream;
	
	public String input() {
		return Action.INPUT;
	}
	public String list() {
		return "list";
	}
	public String goQueryPage(){
		return "queryPage";
	}
	public void query() {
		
	}
	
	
	
	
	public List<SysConfig> getRecords() {
		return records;
	}
	public void setRecords(List<SysConfig> records) {
		this.records = records;
	}
	public SysConfig getSysConfig() {
		return sysConfig;
	}
	public void setSysConfig(SysConfig sysConfig) {
		this.sysConfig = sysConfig;
	}
	public ISysConfigService getSysConfigService() {
		return sysConfigService;
	}
	public void setSysConfigService(ISysConfigService sysConfigService) {
		this.sysConfigService = sysConfigService;
	}
	public InputStream getJsonStream() {
		return jsonStream;
	}
	public void setJsonStream(InputStream jsonStream) {
		this.jsonStream = jsonStream;
	}



}
