package com.upg.finance.icbc.bean;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;

import com.upg.finance.icbc.bean.ans.AnswerPub;
import com.upg.finance.icbc.bean.req.RequestPub;

/**
 * 最外层Bean
 * 
 */
public abstract class BasePackage extends BaseIcbcBean {

	private static final String	PUB_NAME	= "pub";

	@XmlElements({ @XmlElement(name = PUB_NAME, type = RequestPub.class),
			@XmlElement(name = PUB_NAME, type = AnswerPub.class) })
	private Pub					pub;

	public Pub getPub() {
		return pub;
	}

	public void setPub(Pub pub) {
		this.pub = pub;
	}

}
