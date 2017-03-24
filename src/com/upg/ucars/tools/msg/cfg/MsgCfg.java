package com.upg.ucars.tools.msg.cfg;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="cfgType")
@XmlRootElement(name="cfg")
public class MsgCfg implements Serializable {
	private static final long serialVersionUID = 9101721187457285371L;
	@XmlAttribute(name="parser")
	private String parser;
	@XmlAttribute(name="key")
	private String key;
	@XmlAttribute(name="path")
	private String path;
	
	public String getParser() {
		return parser;
	}
	public void setParser(String parser) {
		this.parser = parser;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
