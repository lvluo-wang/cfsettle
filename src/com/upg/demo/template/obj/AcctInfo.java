/*
 * 源程序名称: AcctInfo.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：TODO(这里注明模块名称)
 * 
 */

package com.upg.demo.template.obj;

import java.util.Date;

public class AcctInfo {
	
	private String acctNo;
	private String acctBrchName;
	private Date openDate;
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getAcctBrchName() {
		return acctBrchName;
	}
	public void setAcctBrchName(String acctBrchName) {
		this.acctBrchName = acctBrchName;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	
}
