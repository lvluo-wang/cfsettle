/*
 * 源程序名称: AcctPointAction.java
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称: 账务配置
 * 
 */
package com.upg.ucars.basesystem.acctrecord.actions;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.upg.ucars.basesystem.acctrecord.config.acctpoint.IAcctPointService;
import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.constant.AcctRecordConst;
import com.upg.ucars.constant.AcctRecordErrorConst;
import com.upg.ucars.factory.DynamicPropertyTransfer;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctPoint;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.PropertyTransVo;

/**
 * 
 * 功能说明：记账点配置的Action
 * @author shentuwy  
 * @date Jun 27, 2011 08:46:20 AM 
 *
 */
public class AcctPointAction extends BaseAction{
	private AcctPoint acctPoint;
	private InputStream jsonStream;
	private IAcctPointService acctPointService;
	private List list;
	private static List acctEventList = DictionaryUtil.getCodesByKey(AcctRecordConst.ACCT_EVENT);

	public List getAcctEventList() {
		return acctEventList;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public IAcctPointService getAcctPointService() {
		return acctPointService;
	}

	public void setAcctPointService(IAcctPointService acctPointService) {
		this.acctPointService = acctPointService;
	}

	public InputStream getJsonStream() {
		return jsonStream;
	}

	public void setJsonStream(InputStream jsonStream) {
		this.jsonStream = jsonStream;
	}

	public AcctPoint getAcctPoint() {
		return acctPoint;
	}

	public void setAcctPoint(AcctPoint acctPoint) {
		this.acctPoint = acctPoint;
	}

	public String list() throws Exception {
		return "LIST";
	}

	public String queryData() throws Exception {
		List<ConditionBean> cblist = new ArrayList<ConditionBean>();
		list = acctPointService.queryAcctPoint(cblist, this.getPg());
		
		List tmpList =  new ArrayList();
		tmpList.add(new PropertyTransVo("prodNo", ProductInfo.class,"prodNo","prodName"));
		list = DynamicPropertyTransfer.transform(list, tmpList);		
		
		setDatagridInputStreamData(list, getPg());
		jsonStream = this.getDataStream();
	
		return "table";
	}
	
	public String toCreateAcctPoint() throws Exception {
		acctPoint = new AcctPoint();
		return "AddAcctPoint";
	}
	
	public String createAcctPoint() throws Exception {
		ArrayList<ConditionBean> cblist = new ArrayList<ConditionBean>(2);
		cblist.add(new ConditionBean("prodNo",acctPoint.getProdNo()));
		cblist.add(new ConditionBean("eventNo",acctPoint.getEventNo()));
		List<AcctPoint> list = acctPointService.queryAcctPoint(cblist, null);
		if (!list.isEmpty()){
			ExceptionManager.throwException(ServiceException.class, AcctRecordErrorConst.ACCT_POINT_ALREADY_EXISTS);
		}		
		
		acctPointService.createAcctPoint(acctPoint);
		return null;
	}
	
	public String deleteAcctPoint() throws Exception {
		acctPointService.deleteAcctPoint(Long.valueOf(getId()));
		return null;
	}

	public String listAcctPointForChoose() throws Exception {
		
		return "LISTFORCHOOSE";
	}
}
