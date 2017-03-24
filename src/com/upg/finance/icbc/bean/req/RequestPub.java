package com.upg.finance.icbc.bean.req;

import javax.xml.bind.annotation.XmlRootElement;

import com.upg.finance.icbc.bean.Pub;

@XmlRootElement(name="pub")
public class RequestPub extends Pub {
	/** 交易码 */
	private String	txcode;
	/** 城市号 */
	private String	cityno;
	/** 公司交易日期 */
	private String	cmpdate;
	/** 公司交易时间 */
	private String	cmptime;
	/** 公司方流水号 */
	private String	cmptxsno;
	/** 应用号 */
	private String	appno;

	public String getTxcode() {
		return txcode;
	}

	public void setTxcode(String txcode) {
		this.txcode = txcode;
	}

	public String getCityno() {
		return cityno;
	}

	public void setCityno(String cityno) {
		this.cityno = cityno;
	}

	public String getCmpdate() {
		return cmpdate;
	}

	public void setCmpdate(String cmpdate) {
		this.cmpdate = cmpdate;
	}

	public String getCmptime() {
		return cmptime;
	}

	public void setCmptime(String cmptime) {
		this.cmptime = cmptime;
	}

	public String getCmptxsno() {
		return cmptxsno;
	}

	public void setCmptxsno(String cmptxsno) {
		this.cmptxsno = cmptxsno;
	}

	public String getAppno() {
		return appno;
	}

	public void setAppno(String appno) {
		this.appno = appno;
	}
}
