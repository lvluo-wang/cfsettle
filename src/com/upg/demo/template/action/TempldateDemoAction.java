/*
 * 源程序名称: FreeMarkDemoAction.java 
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称：Demo
 * 
 */

package com.upg.demo.template.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upg.demo.template.obj.AcctInfo;
import com.upg.demo.template.obj.CustInfo;
import com.upg.ucars.factory.TemplateAction;


public class TempldateDemoAction extends TemplateAction{

	private CustInfo custInfo;
	private List accounts = new ArrayList();
	
	private Map map = new HashMap();
	
	private String resultUrl = null;
	
	public String test() throws Exception{
		
		custInfo= new CustInfo();
		custInfo.setCustNo("100001");
		custInfo.setCustName("恒生电子");
		custInfo.setContactPerson("alw");
		custInfo.setContactPhone("0571-XXXXXXX");
		
	    map.put("aaa", new Integer(3));
	    map.put("cust", custInfo);
		
		AcctInfo acct = new AcctInfo();
		acct.setAcctNo("32069087125678");
		acct.setAcctBrchName("招商银行滨江分行");
		acct.setOpenDate(new Date());
		accounts.add(acct);
		
		
		String busiNo="TEST001";
		
		return this.dispatchTempldate(busiNo);
		
	}


	public CustInfo getCustInfo() {
		return custInfo;
	}


	public void setCustInfo(CustInfo custInfo) {
		this.custInfo = custInfo;
	}


	public List getAccounts() {
		return accounts;
	}


	public void setAccounts(List accounts) {
		this.accounts = accounts;
	}


	public String getResultUrl() {
		return resultUrl;
	}


	public void setResultUrl(String resultUrl) {
		this.resultUrl = resultUrl;
	}


	public Map getMap() {
		return map;
	}


	public void setMap(Map map) {
		this.map = map;
	} 
	
}
