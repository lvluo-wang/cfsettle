package com.upg.finance.icbc.bean.ans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 支付划款响应 
 *
 */
@XmlRootElement(name = "ans")
public class PaymentTransferAns extends BaseAnswer {
	/** 工行交易流水号 */
	private String	banktxsno;
	/** 疑账标志 */
	private String	doubtflag;

	public String getBanktxsno() {
		return banktxsno;
	}

	public void setBanktxsno(String banktxsno) {
		this.banktxsno = banktxsno;
	}

	public String getDoubtflag() {
		return doubtflag;
	}

	public void setDoubtflag(String doubtflag) {
		this.doubtflag = doubtflag;
	}

}
