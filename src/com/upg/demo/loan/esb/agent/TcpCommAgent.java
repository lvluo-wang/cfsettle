package com.upg.demo.loan.esb.agent;

import com.upg.demo.mapping.loan.LoanInfo;
import com.upg.ucars.framework.esb.core.ESBClient;
/**
 * 和目标服务进行贷款信息通讯代理
 * @author hezw
 *
 */
public class TcpCommAgent {
	/*
	 * 目标URI
	 */
	private String destUrl = "vm://tcpoutmsg";
	/**
	 * 发送贷款信息至目标服务
	 * @param li	贷款信息
	 */
	public void sendMsg(LoanInfo li) {
		ESBClient.send(destUrl, li, null);
	}

}
