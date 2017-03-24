package com.upg.ucars.framework.exception.core;
/**
 * 从异常定义文件解析出的异常定义对象
 * 
 * @date 2010-12-17 上午11:40:51
 *
 */
public class EcError {
	private String code;//错误码
	private String log;//日志记录标识
	private String message;//异常信息
	private String detail;//异常详细信息
	private String help;//异常提示信息
	private String type;//异常记录级别
	public EcError(){}
	public EcError(String code, String log,String type,
			String message, String detail, String help) {
		super();
		this.type=type;
		this.code = code;
		this.log = log;
		this.message = message;
		this.detail = detail;
		this.help = help;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getHelp() {
		return help;
	}
	public void setHelp(String help) {
		this.help = help;
	}
	
}
