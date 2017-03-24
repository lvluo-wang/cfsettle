package com.upg.demo.loan.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upg.demo.loan.core.loaninfo.IBLoanService;
import com.upg.demo.loan.core.loaninfo.IPLoanProcessService;
import com.upg.demo.loan.core.loaninfo.LoanServiceFactory;
import com.upg.demo.loan.core.personinfo.PersonInfo;
import com.upg.demo.mapping.loan.LoanInfo;
import com.upg.ucars.constant.QueryComponentConst;
import com.upg.ucars.framework.base.BaseAction;
import com.upg.ucars.framework.base.SessionTool;
import com.upg.ucars.framework.base.message.MessageLevel;
import com.upg.ucars.framework.base.queryComponent.QueryComponent;
import com.upg.ucars.framework.bpm.assign.service.IHumanTaskAssignService;
import com.upg.ucars.mapping.basesystem.dictionary.Code;
import com.upg.ucars.model.BooleanResult;

public class LoanAction extends BaseAction{
	
	private List list;	
	private String id;
	private String taskId;
	private String tokenId;
	private LoanInfo loanInfo;
	private BooleanResult br;
	
	private PersonInfo personInfo;
	
	/**
	 * 主页面
	 * 
	 * @return
	 */
	public String main(){
		setQueryComponentId(QueryComponentConst.LOANINFO_QUERY);
		return "MAIN";
	}
	
	public String queryData() throws Exception{	
		setQueryComponentId(QueryComponentConst.LOANINFO_QUERY);
		
		QueryComponent queryComponent=getQueryComponentFormRequest();		
		list = LoanServiceFactory.getLoanService().queryLoanInfo(queryComponent, this.getPg());
		SessionTool.publishNews(MessageLevel.LEVEL_NORMAL,"查询数据成功,共"+getPg().getTotalRows()+"条");
		return setDatagridInputStreamData(list,getPg());
	}
	
	public String addSave() throws Exception{	
		this.getLoanService().save(loanInfo);		
		SessionTool.publishNews(MessageLevel.LEVEL_NORMAL,"添加一个清单成功!"+loanInfo.getLoanNo());
		return null;
	}
	public String edit()throws Exception{	
		loanInfo = this.getLoanService().getById(new Long(this.getId()));
	    return "EDIT";
	}
	
	public String view()throws Exception{	
		loanInfo = this.getLoanService().getById(loanInfo.getId());
	    return "VIEW";
	}
	
	public String editSave(){	
		this.getLoanService().update(loanInfo);
		SessionTool.publishNews(MessageLevel.LEVEL_NORMAL,"修改清单成功!"+loanInfo.getLoanNo());
		return null;
	}
	public String delete() throws Exception{	
		/*if (1==1){
			BpmControlDAO bcdao = (BpmControlDAO)SourceTemplate.getBean("bpmControlDAO");
			//bcdao.reOpenProcessInstance(16284L, "waitPaymentResult");//commonAudit
			bcdao.reOpenProcessInstance(16284L, "commonAudit");//
			return null;
		}*/
		IBLoanService service = this.getLoanService();
		String[] id=getIds().split(":");
		for (int i = 0; i < id.length; i++) {
			service.deleteById(new Long(id[i]));			
		}
		
		SessionTool.publishNews(MessageLevel.LEVEL_NORMAL,"删除清单成功!");
		return null;
	}
	/**
	 * 申请
	 * @return
	 */
	public String apply(){	
		IPLoanProcessService service =  LoanServiceFactory.getLoanProcessService();
		List<Long> idList = this.getIdList();
		for (Long id : idList) {
			LoanInfo entity = this.getLoanService().getById(id);
			service.startLoanPrcocess(entity);
		}
		
		//SessionTool.publishNews(MessageLevel.LEVEL_NORMAL,"清单编号："+entity.getLoanNo()+",提交申请成功!");
		return null;
	}
	
	/**
	 * 主页面
	 * @return
	 * @throws Exception
	 */
	public String mainAcceptTask() throws Exception{
		setQueryComponentId(QueryComponentConst.LOANINFO_QUERY2);
		return "AcceptTask";
	}
	/**
	 * 查询待受理业务
	 * @return
	 * @throws Exception
	 */
	public String queryAcceptTask() throws Exception{		
		setQueryComponentId(QueryComponentConst.LOANINFO_QUERY2);
		QueryComponent queryComponent=getQueryComponentFormRequest();
		long l1 = System.currentTimeMillis();
		list = this.getLoanProcessService().queryAcceptTask(SessionTool.getUserLogonInfo().getSysUserId(), queryComponent, this.getPg());	
		long l2 = System.currentTimeMillis();
		System.err.println("time:"+(l2-l1));
		return setDatagridInputStreamData(list, this.getPg());
	}
	
	public String holdAcceptTask() throws Exception{	
		//领用任务
		this.humnTaskAssignService.holdTask(Long.valueOf(this.getTaskId()), SessionTool.getUserLogonInfo().getSysUserId());
		
		return null;
	}
	
	public String cancelholdAcceptTask() throws Exception{	
		//撤销领用任务
		this.humnTaskAssignService.cancelHoldTask(Long.valueOf(this.getTaskId()), SessionTool.getUserLogonInfo().getSysUserId());
		return null;
	}
	
	public String acceptTaskPersonInfo() throws Exception{	
		//TODO 调用相应服务
		return "AcceptTaskPersonInfo";
	}
	
	public String acceptTaskAcctInfo() throws Exception{	
		//TODO 调用相应服务
		return "AcceptTaskAcctInfo";
	}
	
	//
	
	public String acceptTaskSubmit() throws Exception{	
		//this.personInfo.getName();
	//	this.personInfo.getAcctName();
		//TODO 调用相应服务
		return "AcceptTaskSubmit";
	}
	/**
	 * 受理提交
	 * @return
	 * @throws Exception
	 */
	public String acceptInfo() throws Exception{	
		br = new BooleanResult(true);
		this.getLoanProcessService().dealAcceptTask(new Long(this.getTaskId()) , br);
		SessionTool.publishNews(MessageLevel.LEVEL_NORMAL,"任务："+this.getTaskId()+",已完成!");		
		return "AcceptTask";
	}
	
	
	/**
	 * 受理提交
	 *
	 * @return
	 * @throws Exception
	 */
	public String dealAcceptTask() throws Exception{			
		this.getLoanProcessService().dealAcceptTask(new Long(this.getTaskId()) , br);
		
		SessionTool.publishNews(MessageLevel.LEVEL_NORMAL,"任务："+this.getTaskId()+",已完成!");
		return null;
	}
	/**
	 * 普通审核主页面
	 * @return
	 * @throws Exception
	 */
	public String mainCommonAuditTask() throws Exception{
		setQueryComponentId(QueryComponentConst.LOANINFO_QUERY2);
		return "CommonAuditTask";
	}
	/**
	 * 查询普通审核
	 * @return
	 * @throws Exception
	 */
	public String queryCommonAuditTask() throws Exception{	
		setQueryComponentId(QueryComponentConst.LOANINFO_QUERY2);
		QueryComponent queryComponent=getQueryComponentFormRequest();		
		list = LoanServiceFactory.getLoanProcessService().queryCommonAuditTask(SessionTool.getUserLogonInfo().getSysUserId(), queryComponent, this.getPg());
		
		return setDatagridInputStreamData(list, getPg());
	}
	/**
	 * 处理普通审核
	 * @return
	 * @throws Exception
	 */
	public String dealCommonAuditTask() throws Exception{	
		this.setIds(this.getTaskId());
		
		List<Long> idList = this.getIdList();
		for (Long id : idList) {
			this.getLoanProcessService().dealCommonAuditTask(id , br);
		}
		
		//SessionTool.publishNews(MessageLevel.LEVEL_NORMAL,"任务："+this.getTaskId()+",已完成!");
		return null;
	}
	/**
	 * 高级审核主页面
	 * @return
	 * @throws Exception
	 */
	public String mainAdvancedAuditTask() throws Exception{
		setQueryComponentId(QueryComponentConst.LOANINFO_QUERY2);
		return "AdvancedAuditTask";
	}
	/**
	 * 查询高级审核
	 * @return
	 * @throws Exception
	 */
	public String queryAdvancedAuditTask() throws Exception{	
		setQueryComponentId(QueryComponentConst.LOANINFO_QUERY2);
		QueryComponent queryComponent=getQueryComponentFormRequest();		
		list = this.getLoanProcessService().queryAdvancedAuditTask(SessionTool.getUserLogonInfo().getSysUserId(), queryComponent, this.getPg());
		
		return setDatagridInputStreamData(list, getPg());
	}
	/**
	 * 高级审核提交
	 * @return
	 * @throws Exception
	 */
	public String dealAdvancedAuditTask() throws Exception{			
		this.getLoanProcessService().dealAdvancedAuditTask(new Long(this.getTaskId()) , br);
		
		SessionTool.publishNews(MessageLevel.LEVEL_NORMAL,"任务："+this.getTaskId()+",已完成!");
		return null;
	}
	
	
	/**
	 * 付款主页面
	 * @return
	 * @throws Exception
	 */
	public String mainPaymentResult() throws Exception{
		setQueryComponentId(QueryComponentConst.LOANINFO_QUERY2);
		return "PaymentResult";
	}
	/**
	 * 查询付款
	 * @return
	 * @throws Exception
	 */
	public String queryPaymentResult() throws Exception{	
		setQueryComponentId(QueryComponentConst.LOANINFO_QUERY2);
		QueryComponent queryComponent=getQueryComponentFormRequest();		
		list = this.getLoanProcessService().queryWaitPaymentResultBusiInfo(SessionTool.getUserLogonInfo().getSysUserId(), queryComponent, this.getPg());
		
		return setDatagridInputStreamData(list, getPg());
	}
	/**
	 * 处理付款
	 * @return
	 * @throws Exception
	 */
	public String dealPaymentResult() throws Exception{			
		this.getLoanProcessService().notifyPaymentResult(new Long(this.getTokenId()));
		
		SessionTool.publishNews(MessageLevel.LEVEL_NORMAL,"支付结果："+this.getTokenId()+",已完成!");
		return null;
	}
	
	
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}

	public LoanInfo getLoanInfo() {
		return loanInfo;
	}

	public void setLoanInfo(LoanInfo loanInfo) {
		this.loanInfo = loanInfo;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public BooleanResult getBr() {
		return br;
	}
	public void setBr(BooleanResult br) {
		this.br = br;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public PersonInfo getPersonInfo() {
		return personInfo;
	}
	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}
	//Spring注入开始
	private IBLoanService loanService;
	public IBLoanService getLoanService(){
		return this.loanService;
	}
	public void setLoanService(IBLoanService loanService) {
		this.loanService = loanService;
	}
	private IPLoanProcessService loanProcessService;

	public IPLoanProcessService getLoanProcessService() {
		return loanProcessService;
	}
	public void setLoanProcessService(IPLoanProcessService loanProcessService) {
		this.loanProcessService = loanProcessService;
	}
	
	private IHumanTaskAssignService  humnTaskAssignService;
	//Spring注入结束

	public IHumanTaskAssignService getHumnTaskAssignService() {
		return humnTaskAssignService;
	}
	public void setHumnTaskAssignService(
			IHumanTaskAssignService humnTaskAssignService) {
		this.humnTaskAssignService = humnTaskAssignService;
	}
	
	//-------------------------test
	
	
	private List<Code> typeList;
	
	public List<Code> getTypeList() {
		return typeList;
	}
	
	public void setTypeList(List<Code> typeList) {
		this.typeList = typeList;
	}
	private void processTypeList(){
		typeList = new ArrayList<Code>();
		Code code = new Code();
		code.setCodeNo("0");
		code.setCodeName("新建");
		typeList.add(code);
		code = new Code();
		code.setCodeNo("1");
		code.setCodeName("业务受理");
		typeList.add(code);
		code = new Code();
		code.setCodeNo("2");
		code.setCodeName("普通审批");
		typeList.add(code);
	}
	
	public String getComboData(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		for( int i = 0; i < 10; i++ ){
			String str = "" + i;
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", str);
			map.put("text", str);
			list.add(map);
		}
		return setInputStreamData(list);
	}
	
	public String layoutTest(){
		processTypeList();
		return "layoutTest";
		
	}
	

}
