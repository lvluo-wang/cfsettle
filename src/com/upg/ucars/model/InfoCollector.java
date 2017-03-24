package com.upg.ucars.model;

import bsh.EvalError;
import bsh.Interpreter;



/**
 * 信息收集器
 * 
 * @author shentuwy
 */
public class InfoCollector {

	private Interpreter itpr = new Interpreter();// BSH解析器

	/**
	 * 添加变量
	 * 
	 * @param key 键
	 * @param value 值
	 * @throws RuntimeException
	 */
	public void addVariable(String key, Object value) throws RuntimeException {
		try {
			itpr.set(key, value);
		} catch (EvalError e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 导入类
	 * 
	 * @param clazz
	 * @throws ServiceException
	 */
	public void importClass(Class clazz) throws RuntimeException {
		// itpr.eval("import com.leadmind.common.constants.BusiCodeConst;");
		try {
			itpr.eval("import " + clazz.getName());
		} catch (EvalError e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取事件上下文解释器
	 */
	public Interpreter getInterpreter() {
		return this.itpr;
	}
	
	
	public Object eval(String expr) throws RuntimeException {
		try {
			return this.itpr.eval(expr);
		} catch (EvalError e) {
			throw new RuntimeException(e);
		}
	}
	

}