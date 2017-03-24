package com.upg.ucars.tools.msg;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.upg.ucars.tools.msg.cfg.AbstractConfig;
import com.upg.ucars.tools.msg.cfg.MsgCfg;
import com.upg.ucars.tools.msg.cfg.MsgConfigs;

public final class MsgConfigsBuilder {
	
	/** 
	 * 对应msg-transform-config/msg-transform-config.xml的parser配置项
	 * key即为name
	 **/
	private static Map<String, ConfigParser> cfgParserMap;
	
	/** 
	 * 对应msg-transform-config/msg-transform-config.xml的cfg配置项 
	 * key即为msg-transform-config/msg-transform-config.xml的cfg配置项key
	 **/
	private static Map<String, MsgCfg> cfgMap;
	
	/**
	 * 指转换配置文件的Config配置，如msg-transform-config/demo/spCfg.xml等
	 * key即为msg-transform-config/msg-transform-config.xml的cfg配置项key
	 **/
	private static Map<String, AbstractConfig> configMap;

	/**
	 * 解析msg-transform-config/msg-transform-config.xml配置文件
	 **/
	
	static {
		build();
	}
	
	public static void build() {
		MsgConfigs msgCfgs = MsgConfigs.getInstance();
		if(msgCfgs != null && !msgCfgs.isCfgError()){
			cfgParserMap = msgCfgs.getConfigParserMap();
			cfgMap = msgCfgs.getMsgCfgMap();
			if(cfgMap !=null && !cfgMap.isEmpty()){
				Iterator<String> it = cfgMap.keySet().iterator();
				String key = null;
				MsgCfg msgCfg = null;
				String path = null;
				String parserName = null;
				ConfigParser parser = null;
				AbstractConfig abstractConfig = null;
				for(;it.hasNext();){
					key = it.next();
					msgCfg = cfgMap.get(key);
					path = msgCfg.getPath();
					parserName = msgCfg.getParser();
					parser = cfgParserMap.get(parserName);
					abstractConfig = parser.parse(path);
					if(configMap == null)
						configMap = new HashMap<String, AbstractConfig>();
					configMap.put(key, abstractConfig);
				}
			}
		}
	}
	
	public static AbstractConfig getConfig(String key){
		if(configMap == null)
			configMap = new HashMap<String, AbstractConfig>();
		return configMap.get(key);
	}
}
