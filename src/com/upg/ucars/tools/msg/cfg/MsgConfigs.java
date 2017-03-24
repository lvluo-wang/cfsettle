package com.upg.ucars.tools.msg.cfg;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.upg.ucars.constant.ErrorCodeConst;
import com.upg.ucars.framework.exception.ConfigException;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.tools.msg.ConfigParser;
import com.upg.ucars.util.JAXBUtil;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="msg-configs")
public class MsgConfigs implements Serializable {
	private static final long serialVersionUID = -2870317083847025609L;
	private static final String MSG_CONFIGS_PATH = "msg-transform-config.xml";
	
	@XmlElement(name = "parser")
	private List<Parser> parsers;
	
	@XmlElement(name = "cfg")
	private List<MsgCfg> cfgs;
	
	@XmlTransient
	private List<String> cfgErrors = new ArrayList<String>();
	
	@XmlTransient
	private static MsgConfigs msgConfigs;
	
	private MsgConfigs(){}
	
	public static MsgConfigs getInstance(){
		if(msgConfigs != null)
			return msgConfigs;
		else{
//			String cfgFile = "E:/myworkspaces85/xfosc2/src/msg-transform-config/msg-transform-config.xml";
//			MsgConfigs configs = JAXBUtil.unmarshallWithFile(cfgFile, new Class[]{MsgConfigs.class,MsgCfg.class,Parser.class});
			InputStream is = MsgConfigs.class.getClassLoader().getResourceAsStream(MSG_CONFIGS_PATH);
			try {
				msgConfigs = JAXBUtil.unmarshall(is, new Class[]{MsgConfigs.class,MsgCfg.class,Parser.class});
			} catch (JAXBException e) {
				ExceptionManager.throwException(ConfigException.class,ErrorCodeConst.ESB_MSG_TF_CONF_ERROR,"file["+MSG_CONFIGS_PATH+"] config error!");
			}
			msgConfigs.check();
			return msgConfigs;
		}
	}
	
	public List<MsgCfg> getCfgs() {
		if(cfgs == null)
			cfgs = new ArrayList<MsgCfg>();
		return cfgs;
	}
	
	
	public void addCfg(MsgCfg cfg){
		this.getCfgs().add(cfg);
	}
	
	/**
	 * 是否配置错误
	 * @return true:配置错误 false:无
	 **/
	public boolean isCfgError(){
		if(cfgErrors.isEmpty()) 
			return false;
		else
			return true;
	}
	
	/**
	 * 校验是否有重复配置项，依据key来判断
	 * @return true:校验不通过 false:校验通过
	 **/
	private boolean check(){
		boolean result = false;
		MsgCfg repeatCfg = null;
		if(this.getCfgs().isEmpty()) return false;
		Map<String,Object> keyMap = new HashMap<String,Object>();
		for(MsgCfg cfg : cfgs){
			String key = cfg.getKey();
			if(keyMap.containsKey(key)){
				repeatCfg = cfg;
				result = true;
				break;
			}else{
				keyMap.put(key, null);
			}
		}
		String errorMsg = null;
		if(repeatCfg != null){
			errorMsg = "cfg["+repeatCfg.getKey()+"] items duplicate in the "+MSG_CONFIGS_PATH+" file.";
			cfgErrors.add(errorMsg);
			ExceptionManager.throwException(ConfigException.class,ErrorCodeConst.ESB_MSG_TF_CONF_ERROR,errorMsg);
		}
		String parserClassName = null;
		String notFoundClass = null;
		try {
			List<Parser> parsers = this.getParsers();
			for(Parser parser : parsers){
				parserClassName = parser.getClazz();
				Class clazz = Class.forName(parserClassName);
			}
		} catch (ClassNotFoundException e) {
			notFoundClass = parserClassName;
			result = true;
			e.printStackTrace();
		}
		if(notFoundClass != null){
			errorMsg = "Can not found the class["+notFoundClass+"] of Parser In the "+MSG_CONFIGS_PATH+" file.";
			cfgErrors.add(errorMsg);
			ExceptionManager.throwException(ConfigException.class,ErrorCodeConst.ESB_MSG_TF_CONF_ERROR,errorMsg);
		}
		return result;
	}
	
	/**
	 * 取得转换配置文件中的cfg配置项map
	 **/
	public Map<String, MsgCfg> getMsgCfgMap() {
		if(isCfgError()) return null;
		Map<String, MsgCfg> msgCfgMap = new HashMap<String, MsgCfg>();
		List<MsgCfg> cfgs = this.getCfgs();
		for (MsgCfg cfg : cfgs) {
			String key = cfg.getKey();
			msgCfgMap.put(key, cfg);
		}
		return msgCfgMap;
	}
	
	/**
	 * 取得转换配置文件解析器map
	 **/
	public Map<String,ConfigParser> getConfigParserMap(){
		if(isCfgError()) return null;
		Map<String,ConfigParser> configParserMap = new HashMap<String,ConfigParser>();
		List<Parser> parsers = this.getParsers();
		try {
			for(Parser parser : parsers){
				String parserClassName = parser.getClazz();
				Class clazz = Class.forName(parserClassName);
				ConfigParser configParser = (ConfigParser)clazz.newInstance();
				configParserMap.put(parser.getName(), configParser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return configParserMap;
	}


	public List<Parser> getParsers() {
		if(parsers == null)
			parsers = new ArrayList<Parser>();
		return parsers;
	}
	
	public void addConfigParser(Parser parser){
		this.getParsers().add(parser);
	}
	
}
