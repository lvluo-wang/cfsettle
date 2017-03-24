/*
 * 源程序名称: TranItemAction.java
 * 软件著作权: 投融长富金融服务集团有限公司 版权所有
 * 系统名称: XXXX业务系统平台(UBSP)
 * 模块名称: 账务配置
 * 
 */
package com.upg.ucars.basesystem.acctrecord.actions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONArray;

import com.upg.ucars.basesystem.acctrecord.config.acctitem.IAcctItemService;
import com.upg.ucars.basesystem.acctrecord.config.acctrecordbody.IAcctRecordBodyService;
import com.upg.ucars.basesystem.acctrecord.config.accttran.IAcctTranService;
import com.upg.ucars.basesystem.acctrecord.config.tranitem.ITranItemService;
import com.upg.ucars.basesystem.dictionary.util.DictionaryUtil;
import com.upg.ucars.constant.AcctRecordConst;
import com.upg.ucars.factory.DynamicPropertyTransfer;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctItem;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctPoint;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctRecordBody;
import com.upg.ucars.mapping.basesystem.acctrecord.AcctTran;
import com.upg.ucars.mapping.basesystem.acctrecord.TranItem;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.model.ConditionBean;
import com.upg.ucars.model.JQueryTreeNode;


/**
 * 
 * 功能说明：交易项的Action
 * @author shentuwy  
 * @date Jun 27, 2011 08:46:20 AM 
 *
 */
public class TranItemAction extends BaseAction {
	private TranItem tranItem;
	private AcctTran acctTran;
	private InputStream jsonStream;
	private ITranItemService tranItemService;
	private List list;
	private boolean isCopy = false;
	
	private IAcctItemService acctItemService;
	private IAcctRecordBodyService acctRecordBodyService;
	private IAcctTranService acctTranService;
	private InputStream treeStream;
	private Map groupMap;
	private String groupNo;
	private Long tranId;
	private String colNames;
	private List<AcctRecordBody> groupBodyList = null;

	private String recordBodyIds = "";
	private String acctItemIds = "";
	private String itemValues = "";
	
	private String jsonArrayAcctItemList;//记帐信息项
	private String jsonArrayTranItemList;//分录信息
	
	
	
	public String main(){
		//Long brchId = SessionTool.getUserLogonInfo().getBranchId();
		//takeBranch = XCarsServiceFactory.getBranchService().getBranchByBrchId(brchId);		
		
		return "main";
	}
	
	public String queryTranTree() throws Exception{		
		List<Code> eventCodeList = DictionaryUtil.getCodesByKey(AcctRecordConst.ACCT_EVENT);
		HashMap<String, String> eventMap = new HashMap<String, String>(); 
		for (Code code : eventCodeList) {
			eventMap.put(code.getCodeNo(), code.getCodeName());
		}
		
		String miNo = SessionTool.getUserLogonInfo().getMiNo();
		List<Object[]> acctInfoList = acctTranService.findAcctPointAndTranInfoByMember(miNo);
		
		HashMap<String, String> prodMap = new HashMap<String, String>();
		
		TreeMap<String, JQueryTreeNode> prodTreeMap = new TreeMap<String, JQueryTreeNode>();
		
		//构造 产品node-->事件node-->交易node 三级树
		for (Object[] objs : acctInfoList) {
			AcctPoint point = (AcctPoint)objs[0];
			AcctTran tran = (AcctTran)objs[1];
			String prodName = (String)objs[2];	
			
			//构造产品node
			JQueryTreeNode prodNode = prodTreeMap.get(point.getProdNo());
			if (prodNode == null){//不存在产品节点
				prodNode = new JQueryTreeNode();
				prodNode.setId(point.getProdNo());
				prodNode.setText(prodName);
				prodNode.setState(JQueryTreeNode.STATE_CLOSED);
				
				prodMap.put(point.getProdNo(), prodName);
				prodTreeMap.put(point.getProdNo(), prodNode);
			}
			
			//构造事件node
			JQueryTreeNode eventNode = prodNode.getChild(point.getEventNo());
			if (eventNode == null){
				eventNode = new JQueryTreeNode(); 
				eventNode.setId(point.getEventNo());
				eventNode.setText(eventMap.get(point.getEventNo()));
				eventNode.setState(JQueryTreeNode.STATE_CLOSED);
				
				prodNode.addChild(eventNode);
			}
			
			//构造交易node			
			JQueryTreeNode tranNode = new JQueryTreeNode(); 
			tranNode.setId(tran.getId().toString());			
			tranNode.setText(tran.getTranName());
			tranNode.setState(JQueryTreeNode.STATE_OPEN);
			
			eventNode.addChild(tranNode);
			
			
		}
		
		JSONArray json=JSONArray.fromObject(prodTreeMap.values());
		System.out.println(json.toString());
		treeStream=new ByteArrayInputStream(json.toString().getBytes("UTF-8"));
		
		return "trantree";
	}
	
	public String toTranItemList(){
		String miNo = SessionTool.getUserLogonInfo().getMiNo();
		List<AcctRecordBody> bodyInfoList = this.acctRecordBodyService.queryAcctRecordBodyByMember(miNo, null, null);
		groupMap = new TreeMap();
		for (AcctRecordBody acctRecordBody : bodyInfoList) {
			groupMap.put(acctRecordBody.getGroupNo(), acctRecordBody.getGroupNo());
		}
		
		if (this.getGroupNo()==null){
			this.groupNo = bodyInfoList.get(0).getGroupNo();
		}
		
		this.colNames = "";
		//ArrayList<AcctRecordBody> groupBodyList = new ArrayList<AcctRecordBody>();
		for (AcctRecordBody acctRecordBody : bodyInfoList) {
			if(this.groupNo.equals(acctRecordBody.getGroupNo())){
				//groupBodyList.add(acctRecordBody);
				colNames += acctRecordBody.getName()+",";
			}
		}
		colNames = colNames.substring(0, colNames.length()-1);
				
		return "itemlist";
	}
	
	public String tranItemList(){
		String miNo = SessionTool.getUserLogonInfo().getMiNo();
		List<AcctRecordBody> bodyInfoList = this.acctRecordBodyService.queryAcctRecordBodyByMember(miNo, null, null);
		groupMap = new TreeMap();
		for (AcctRecordBody acctRecordBody : bodyInfoList) {
			groupMap.put(acctRecordBody.getGroupNo(), acctRecordBody.getGroupNo());
		}
		
		List<ConditionBean> cblist = new ArrayList<ConditionBean>();
		cblist.add(new ConditionBean("tranItem.tranId", this.getTranId()));
		cblist.add(new ConditionBean("tranItem.groupNo", this.getGroupNo()));
		List<TranItem> itemList = tranItemService.queryTranItem(cblist, null);
		
		TreeMap<String, TranItemConvertBean> map = new TreeMap<String, TranItemConvertBean>();
		for (TranItem tranItem : itemList) {
			TranItemConvertBean rowObj = map.get(""+tranItem.getRowNo());
			if (rowObj == null){
				rowObj = new TranItemConvertBean();
				
			}
			rowObj = (TranItemConvertBean)DynamicPropertyTransfer.dynamicAddProperty(rowObj, "col"+tranItem.getSerialNo(), tranItem.getValue());
			map.put(""+tranItem.getRowNo(), rowObj);
		}
		
		JSONArray jsonObject = JSONArray.fromObject(map.values());
		jsonStream = outJsonUTFStream(jsonObject);
		
		return "table";
	}
	
	public String toCreateTranItem() throws Exception {
		String miNo = SessionTool.getUserLogonInfo().getMiNo();
		
		List<ConditionBean> cblist = new ArrayList<ConditionBean>(1);
		cblist.add(new ConditionBean("groupNo", this.getGroupNo()));
		//分录结构
		groupBodyList = this.acctRecordBodyService.queryAcctRecordBodyByMember(miNo, cblist, null);
		
		
		AcctTran tran = this.acctTranService.findAcctTran(this.getTranId());
		
		List<ConditionBean> cb2list = new ArrayList<ConditionBean>();
		cb2list.add(new ConditionBean("pointId", tran.getPointId()).addPartner(new ConditionBean("belongType", AcctItem.BelongType_Point)));
		cb2list.add(new ConditionBean("miNo", miNo).setLogic(ConditionBean.LOGIC_OR).addPartner(new ConditionBean("belongType", AcctItem.BelongType_Memeber)));
		cb2list.add(new ConditionBean("miNo", miNo).setLogic(ConditionBean.LOGIC_OR)
				.addPartner(new ConditionBean("belongType", AcctItem.BelongType_MemberPoint))
				.addPartner(new ConditionBean("pointId", tran.getPointId())));
		//可选信息项
		List<AcctItem> acctItemList =  this.acctItemService.queryAcctItem(cb2list, null);
				
		JSONArray jsonObject = JSONArray.fromObject(acctItemList);		
		this.jsonArrayAcctItemList = jsonObject.toString();		
		
		return "AddTranItem";
	}
	
	public String createTranItem() throws Exception {
		//设置最大行数，默认为最大行数+1
		List<ConditionBean> cblist = new ArrayList<ConditionBean>();
		cblist.add(new ConditionBean("tranItem.tranId", this.getTranId()));
		cblist.add(new ConditionBean("tranItem.groupNo", this.getGroupNo()));
		List<TranItem> itemList = tranItemService.queryTranItem(cblist, null);
		int curRowNo = 1;
		if (!itemList.isEmpty()){
			curRowNo = itemList.get(itemList.size()-1).getRowNo() +1 ;
			
		}
		
		String[] acctItemIdArr = this.getAcctItemIds().split(",");
		String[] itemValueArr = (this.getItemValues()+" ").split(",");//如果'a,b,,,'则['a','b'];如果'a,b,,, ' 则[a,b,'','',' ']
		String[] recordBodyIdArr = this.getRecordBodyIds().split(",");
		for (int i = 0; i < recordBodyIdArr.length; i++) {
			TranItem ti = new TranItem();
			ti.setGroupNo(this.getGroupNo());
			ti.setAcctItemId(Long.valueOf(acctItemIdArr[i]));
			ti.setRecordBodyId(Long.valueOf(recordBodyIdArr[i]));
			ti.setRowNo(curRowNo);
			ti.setTranId(this.getTranId());
			ti.setValue(itemValueArr[i]);
			ti.setSerialNo(i+1);	
			
			this.tranItemService.createTranItem(ti);
			
		}
		
		return null;
	}
	
	public String deleteTranItem() throws Exception {
		
		tranItemService.deleteRowTranItem(tranId, groupNo, Integer.valueOf(getId()));
		
		return null;
	}
	/**
	 * 上移一行
	 * @return
	 * @throws Exception
	 */
	public String upMoveRow() throws Exception {
		int rowNo = Integer.valueOf(getId());
		if (rowNo>1){
			tranItemService.exchangeRowIndex(tranId, groupNo, rowNo, rowNo-1);
		}

		return null;
	}
	/**
	 * 下移一行
	 * @return
	 * @throws Exception
	 */
	public String downMoveRow() throws Exception {
		
		int maxRow = tranItemService.getMaxRowIndex(tranId, groupNo);
		int rowNo = Integer.valueOf(getId());
		if (rowNo<maxRow){
			tranItemService.exchangeRowIndex(tranId, groupNo, rowNo, rowNo+1);
		}
		
		return null;
		
	}
	
	public String toEditTranItem() throws Exception {
		String miNo = SessionTool.getUserLogonInfo().getMiNo();
		
		List<ConditionBean> cblist = new ArrayList<ConditionBean>(1);
		cblist.add(new ConditionBean("groupNo", this.getGroupNo()));
		//分录结构
		groupBodyList = this.acctRecordBodyService.queryAcctRecordBodyByMember(miNo, cblist, null);		
		
		AcctTran tran = this.acctTranService.findAcctTran(this.getTranId());
		
		List<ConditionBean> cb2list = new ArrayList<ConditionBean>();
		cb2list.add(new ConditionBean("pointId", tran.getPointId()).addPartner(new ConditionBean("belongType", AcctItem.BelongType_Point)));
		cb2list.add(new ConditionBean("miNo", miNo).setLogic(ConditionBean.LOGIC_OR).addPartner(new ConditionBean("belongType", AcctItem.BelongType_Memeber)));
		cb2list.add(new ConditionBean("miNo", miNo).setLogic(ConditionBean.LOGIC_OR)
				.addPartner(new ConditionBean("belongType", AcctItem.BelongType_MemberPoint))
				.addPartner(new ConditionBean("pointId", tran.getPointId())));
		//可选信息项
		List<AcctItem> acctItemList =  this.acctItemService.queryAcctItem(cb2list, null);
		
		JSONArray jsonObject = JSONArray.fromObject(acctItemList);		
		this.jsonArrayAcctItemList = jsonObject.toString();
		
		//查询一行分录信息
		int rowNo = Integer.valueOf(getId());
		List<ConditionBean> cb3list = new ArrayList<ConditionBean>();
		cb3list.add(new ConditionBean("tranItem.tranId",this.getTranId()));
		cb3list.add(new ConditionBean("tranItem.groupNo",this.getGroupNo()));
		cb3list.add(new ConditionBean("tranItem.rowNo", rowNo));
		List<TranItem> tranItemList = this.tranItemService.queryTranItem(cb3list, null);
		
		JSONArray jsonObject2 = JSONArray.fromObject(tranItemList);		
		this.jsonArrayTranItemList = jsonObject2.toString();
		
		return "EditTranItem";
	}
	
	public String modifyTranItem() throws Exception {
		//查询一行分录信息
		int rowNo = Integer.valueOf(getId());
		List<ConditionBean> cb3list = new ArrayList<ConditionBean>();
		cb3list.add(new ConditionBean("tranItem.tranId",this.getTranId()));
		cb3list.add(new ConditionBean("tranItem.groupNo",this.getGroupNo()));
		cb3list.add(new ConditionBean("tranItem.rowNo", rowNo));
		List<TranItem> tranItemList = this.tranItemService.queryTranItem(cb3list, null);
		HashMap<Long,TranItem> tranItemMap = new HashMap<Long,TranItem>(tranItemList.size());
		for (TranItem tranItem : tranItemList) {
			tranItemMap.put(tranItem.getRecordBodyId(), tranItem);
		}
		
		String[] acctItemIdArr = this.getAcctItemIds().split(",");
		String[] itemValueArr = (this.getItemValues()+" ").split(",");//如果'a,b,,,'则['a','b'];如果'a,b,,, ' 则[a,b,'','',' ']
		String[] recordBodyIdArr = this.getRecordBodyIds().split(",");
		for (int i = 0; i < recordBodyIdArr.length; i++) {
			TranItem ti = tranItemMap.get(Long.valueOf(recordBodyIdArr[i]));
			if(ti == null){//不存在添加
				ti =  new TranItem();
				ti.setAcctItemId(Long.valueOf(acctItemIdArr[i]));
				ti.setRecordBodyId(Long.valueOf(recordBodyIdArr[i]));
				ti.setGroupNo(this.getGroupNo());
				ti.setRowNo(rowNo);
				ti.setTranId(this.getTranId());
				ti.setValue(itemValueArr[i]);
				ti.setSerialNo(i+1);	
				this.tranItemService.createTranItem(ti);
				
			}else{//存在修改
				ti.setAcctItemId(Long.valueOf(acctItemIdArr[i]));
				ti.setValue(itemValueArr[i]);
				this.tranItemService.modifyTranItem(ti);				
			}
			
		}
		
		return null;
	}
	
	public String toCopyTranItem() throws Exception {
		//直接调用修改的方法
		this.toEditTranItem();
		
		return "CopyTranItem";
	}
	
	public String copyTranItem() throws Exception {
		this.createTranItem();
		return null;
	}


	public boolean getIsCopy() {
		return isCopy;
	}

	public void setIsCopy(boolean isCopy) {
		this.isCopy = isCopy;
	}

	public TranItem getTranItem() {
		return tranItem;
	}

	public void setTranItem(TranItem tranItem) {
		this.tranItem = tranItem;
	}

	public InputStream getJsonStream() {
		return jsonStream;
	}

	public void setJsonStream(InputStream jsonStream) {
		this.jsonStream = jsonStream;
	}

	public ITranItemService getTranItemService() {
		return tranItemService;
	}

	public void setTranItemService(ITranItemService tranItemService) {
		this.tranItemService = tranItemService;
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
	
	public static Object getPropertyValueByReflectMethod(Object src, String srcMtdName) throws Exception{
		Method m1 = src.getClass().getMethod(srcMtdName);
		return m1.invoke(src);
	}
	
	public static void setPropertyValueByReflectMethod(Object dst, String dstMtdName, Class cls, Object v) throws Exception{
		Method m2 = dst.getClass().getMethod(dstMtdName, cls);
		m2.invoke(dst, v);
	}
	
	private Long lastTranId = null;
	private String lastGroupNo = null;
	private String lastRowNo = null;
	
	
	
	
	
	
	public AcctTran getAcctTran() {
		return acctTran;
	}

	public void setAcctTran(AcctTran acctTran) {
		this.acctTran = acctTran;
	}

	public InputStream getTreeStream() {
		return treeStream;
	}

	public void setTreeStream(InputStream treeStream) {
		this.treeStream = treeStream;
	}

	public IAcctTranService getAcctTranService() {
		return acctTranService;
	}

	public void setAcctTranService(IAcctTranService acctTranService) {
		this.acctTranService = acctTranService;
	}

	public IAcctRecordBodyService getAcctRecordBodyService() {
		return acctRecordBodyService;
	}

	public void setAcctRecordBodyService(
			IAcctRecordBodyService acctRecordBodyService) {
		this.acctRecordBodyService = acctRecordBodyService;
	}

	public IAcctItemService getAcctItemService() {
		return acctItemService;
	}

	public void setAcctItemService(IAcctItemService acctItemService) {
		this.acctItemService = acctItemService;
	}


	public Map getGroupMap() {
		return groupMap;
	}

	public void setGroupMap(Map groupMap) {
		this.groupMap = groupMap;
	}

	public String getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(String groupNo) {
		this.groupNo = groupNo;
	}


	public Long getTranId() {
		return tranId;
	}

	public void setTranId(Long tranId) {
		this.tranId = tranId;
	}

	public List<AcctRecordBody> getGroupBodyList() {
		return groupBodyList;
	}

	public void setGroupBodyList(List<AcctRecordBody> groupBodyList) {
		this.groupBodyList = groupBodyList;
	}

	public String getColNames() {
		return colNames;
	}

	public void setColNames(String colNames) {
		this.colNames = colNames;
	}

	public String getRecordBodyIds() {
		return recordBodyIds;
	}

	public void setRecordBodyIds(String recordBodyIds) {
		this.recordBodyIds = recordBodyIds;
	}

	public String getAcctItemIds() {
		return acctItemIds;
	}

	public void setAcctItemIds(String acctItemIds) {
		this.acctItemIds = acctItemIds;
	}

	public String getItemValues() {
		return itemValues;
	}

	public void setItemValues(String itemValues) {
		this.itemValues = itemValues;
	}


	public String getJsonArrayAcctItemList() {
		return jsonArrayAcctItemList;
	}

	public String getJsonArrayTranItemList() {
		return jsonArrayTranItemList;
	}

	public void setJsonArrayTranItemList(String jsonArrayTranItemList) {
		this.jsonArrayTranItemList = jsonArrayTranItemList;
	}

	public void setJsonArrayAcctItemList(String jsonArrayAcctItemList) {
		this.jsonArrayAcctItemList = jsonArrayAcctItemList;
	}
}
