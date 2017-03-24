package com.upg.ucars.basesystem.acctrecord.runtime;

import bsh.Interpreter;

import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.model.InfoCollector;
import com.upg.ucars.util.StringUtil;

/**
 * 记帐信息收集器
 * 
 * @author shentuwy
 */
public class AcctInfoCollector {

	private Long entityId;//清单ID
	
	private InfoCollector infoColler = new InfoCollector();
	/**
	 * 
	 * Creates a new instance of AcctInfoCollector.
	 *
	 * @param entityId
	 */
	public AcctInfoCollector(Long entityId) {
		this.entityId = entityId;
	}
	


	public Long getEntityId() {
		return entityId;
	}


	public void setEntityId(Long entityId) {
		this.entityId = entityId;
	}


	public InfoCollector getInfoColler() {
		return infoColler;
	}


	public void setInfoColler(InfoCollector infoColler) {
		this.infoColler = infoColler;
	}
	/**
	 * 增加上下文变量<br>
	 * 以类型名首字母小写为key
	 * 
	 * @param value 
	 * @throws ServiceException
	 */
	public void addVariable(Object value) throws ServiceException{
		String simpleName = value.getClass().getSimpleName();
		String key = StringUtil.firstLower(simpleName);
		this.addVariable(key, value);
	}


	/**
	 * 上下文变量
	 * @param key  键
	 * @param value 值
	 * @throws ServiceException 
	 */
	public void addVariable(String key, Object value) throws ServiceException{
		try {
			this.infoColler.addVariable(key, value);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
	}	
	
	/**
	 * 导入类
	 * @param clazz
	 * @throws ServiceException
	 */
	public void importClass(Class clazz) throws ServiceException{
		try {
			this.infoColler.importClass(clazz);
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	/**
	 * 
	 */
	public Interpreter getContextInterpreter(){
		return this.infoColler.getInterpreter();
	}

}