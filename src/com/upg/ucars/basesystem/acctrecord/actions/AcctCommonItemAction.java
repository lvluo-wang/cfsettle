/*
 * 源程序名称: AcctItemAction.java
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称: 账务配置
 * 
 */
package com.upg.ucars.basesystem.acctrecord.actions;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.upg.ucars.basesystem.acctrecord.config.acctitem.IAcctItemService;
import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.constant.AcctRecordConst;
import com.upg.ucars.constant.AcctRecordErrorConst;
import com.upg.ucars.factory.DynamicPropertyTransfer;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.base.message.MessageLevel;
import com.upg.ucars.framework.exception.ExceptionManager;
import com.upg.ucars.framework.exception.ServiceException;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctItem;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctPoint;
import com.upg.ucars.mapping.basesystem.product.ProductInfo;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.util.PropertyTransVo;


/**
 * 产品通用的记账项配置的Action 
 *
 * @author shentuwy
 */
public class AcctCommonItemAction extends BaseAction{
	private AcctItem acctItem;
	private InputStream jsonStream;
	private IAcctItemService acctItemService;
	private List list;
	private static List belongTypeList = DictionaryUtil.getCodesByKey(AcctRecordConst.ACCT_ITEM_BELONG_TYPE);
	private static List valueTypeList = DictionaryUtil.getCodesByKey(AcctRecordConst.ACCT_ITEM_VALUE_TYPE);
	
	public List getBelongTypeList() {
		return belongTypeList;
	}

	public void setBelongTypeList(List belongTypeList) {
		this.belongTypeList = belongTypeList;
	}

	public List getValueTypeList() {
		return valueTypeList;
	}

	public void setValueTypeList(List valueTypeList) {
		this.valueTypeList = valueTypeList;
	}

	public AcctItem getAcctItem() {
		return acctItem;
	}

	public void setAcctItem(AcctItem acctItem) {
		this.acctItem = acctItem;
	}

	public InputStream getJsonStream() {
		return jsonStream;
	}

	public void setJsonStream(InputStream jsonStream) {
		this.jsonStream = jsonStream;
	}

	public IAcctItemService getAcctItemService() {
		return acctItemService;
	}

	public void setAcctItemService(IAcctItemService acctItemService) {
		this.acctItemService = acctItemService;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String list() throws Exception {
		return "LIST";
	}
	
	public String queryData() throws Exception {
		if(acctItem==null) acctItem = new AcctItem();
		List<ConditionBean> cblist = new ArrayList<ConditionBean>();
		cblist.add(new ConditionBean("valueType",acctItem.getValueType()));
		cblist.add(new ConditionBean("belongType",AcctItem.BelongType_Point));
		cblist.add(new ConditionBean("miNo",ConditionBean.IS_NULL, null));
		cblist.add(new ConditionBean("pointId",acctItem.getPointId()));
		
		list = acctItemService.queryAcctItem(cblist, this.getPg());
		
		List tmpList =  new ArrayList();
		tmpList.add(new PropertyTransVo("pointId", AcctPoint.class,"id","prodNo"));
		tmpList.add(new PropertyTransVo("pointId", AcctPoint.class,"id","eventNo"));
		list = DynamicPropertyTransfer.transform(list, tmpList);
		
		tmpList =  new ArrayList();
		tmpList.add(new PropertyTransVo("pointId_prodNo", ProductInfo.class,"prodNo","prodName"));
		list = DynamicPropertyTransfer.transform(list, tmpList);
		
		setDatagridInputStreamData(list, getPg());
		jsonStream = this.getDataStream();
		
		SessionTool.publishNews(MessageLevel.LEVEL_NORMAL, "查询数据成功,共"
				+ getPg().getTotalRows() + "条");
		return "table";
	}
	
	public String toCreateAcctItem() throws Exception {
		if (acctItem.getPointId()!=null){
			List tmpList =  new ArrayList();
			tmpList.add(new PropertyTransVo("pointId", AcctPoint.class,"id","prodNo"));
			tmpList.add(new PropertyTransVo("pointId", AcctPoint.class,"id","eventNo"));
			acctItem = (AcctItem)DynamicPropertyTransfer.transform(acctItem, tmpList);
			
			tmpList =  new ArrayList();
			tmpList.add(new PropertyTransVo("pointId_prodNo", ProductInfo.class,"prodNo","prodName","pointName"));
			acctItem = (AcctItem)DynamicPropertyTransfer.transform(acctItem, tmpList);
		}else{
			acctItem = (AcctItem)DynamicPropertyTransfer.dynamicAddProperty(acctItem, "pointName", "");
		}
			
		return "AddAcctItem";
	}
	
	public String createAcctItem() throws Exception {
		acctItem.setBelongType(AcctItem.BelongType_Point);//		
		{//判断编号重复
			ArrayList<ConditionBean> cblist = new ArrayList<ConditionBean>(3);
			cblist.add(new ConditionBean("pointId", acctItem.getPointId()));
			cblist.add(new ConditionBean("itemNo", acctItem.getItemNo()));
			cblist.add(new ConditionBean("belongType", AcctItem.BelongType_Point));
			
			List<AcctItem> itemList = acctItemService.queryAcctItem(cblist, null);
			if (!itemList.isEmpty()){
				throw ExceptionManager.getException(ServiceException.class, AcctRecordErrorConst.ACCT_ITEM_ALREADY_EXISTS);
			}	
		}
		
		
		acctItemService.createAcctItem(acctItem);
		return null;
	}
	
	public String toEditAcctItem() throws Exception {
		acctItem = acctItemService.findAcctItem(Long.valueOf(getId()));
		
		List tmpList =  new ArrayList();
		tmpList.add(new PropertyTransVo("pointId", AcctPoint.class,"id","prodNo"));
		tmpList.add(new PropertyTransVo("pointId", AcctPoint.class,"id","eventNo"));
		acctItem = (AcctItem)DynamicPropertyTransfer.transform(acctItem, tmpList);
		
		tmpList =  new ArrayList();
		tmpList.add(new PropertyTransVo("pointId_prodNo", ProductInfo.class,"prodNo","prodName"));
		acctItem = (AcctItem)DynamicPropertyTransfer.transform(acctItem, tmpList);
		
		return "EditAcctItem";
	}
	
	public String modifyAcctItem() throws Exception {
		acctItem.setBelongType(AcctItem.BelongType_Point);
		
		{//判断编号重复
			ArrayList<ConditionBean> cblist = new ArrayList<ConditionBean>(3);
			cblist.add(new ConditionBean("pointId", acctItem.getPointId()));
			cblist.add(new ConditionBean("itemNo", acctItem.getItemNo()));
			cblist.add(new ConditionBean("belongType", AcctItem.BelongType_Point));
			List<AcctItem> itemList = acctItemService.queryAcctItem(cblist, null);
			if (!itemList.isEmpty()){
				if (!itemList.get(0).getId().equals(acctItem.getId()))
					throw ExceptionManager.getException(ServiceException.class, AcctRecordErrorConst.ACCT_ITEM_ALREADY_EXISTS);
			}	
		}
		
		
		
		acctItemService.modifyAcctItem(acctItem);
		return null;
	}
	
	public String deleteAcctItem() throws Exception {
		acctItemService.deleteAcctItem(Long.valueOf(getId()));
		return null;
	}
	
	public String listAcctItemForChoose() throws Exception {
		return "LISTFORCHOOSE";
	}
}
