/*******************************************************************************
 * Leadmind Technologies, Ltd.
 * All rights reserved.
 * 
 * Created on Jan 11, 2009
 *******************************************************************************/


package com.upg.ucars.framework.bpm.base;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

/**
 * 工作流审批路线判断结点处理类
 * 
 * @author yangjun (mailto:yangjun@leadmind.com.cn)
 */
public class AuditRouteDecisionHandler implements DecisionHandler {
	private static final long serialVersionUID = 1L;
	//连线名字：进入审批任务
	private final static String Line_Name_To_Audit = "to_audit_route";
	//连线名字：审批结束
	private final static String Line_Name_Audit_End = "audit_route_end";
	//连线名字：审批被拒绝
	private final static String Line_Name_Audit_Refuse = "audit_route_refuse";
	//连线名字：审批任务结点进入审批判断结点
	public final static String Line_Name_To_Audit_Decision = "audit_route_task_end";
	/**审批结点变量名*/
	public final static String Val_Audit_Route_Node_No = "route_node_no";
	/**审批岗位变量名*/
	public final static String Val_Audit_Route_Station_No = "route_station_no";
	/**审批人员*/
	public final static String Val_Audit_Users = "route_users";
	
	/**审批金额变量名-Double类型*/
	public final static String Val_Audit_Route_Money = "route_node_money";
	/**审批路线所属机构-对应Branch.brchId*/
	public final static String Val_Audit_Brch_No = "route_brch_no";
	/**审批路线所属产品*/
	public final static String Val_Audit_Prod_No = "route_prod_no";
	
	/**审批通过 boolean*/
	public final static String Val_Audit_Is_Pass = "audit_route_is_pass";
	/**审批不通过返回给前一审批者 boolean*/
	public final static String Val_Audit_Is_Back = "audit_route_is_back";

	public String decide(ExecutionContext executionContext) throws Exception {
		/*Token token = executionContext.getToken();
		
		String brchNo = (String)executionContext.getContextInstance().getVariableLocally(AuditRouteDecisionHandler.Val_Audit_Brch_No, executionContext.getToken());
		String prodNo = (String)executionContext.getContextInstance().getVariableLocally(AuditRouteDecisionHandler.Val_Audit_Prod_No, executionContext.getToken());
		if (brchNo == null){//子令牌没有则从根令牌取值
			brchNo = (String)executionContext.getContextInstance().getVariable(AuditRouteDecisionHandler.Val_Audit_Brch_No);
		}		
		if (prodNo == null){
			prodNo = (String)executionContext.getContextInstance().getVariable(AuditRouteDecisionHandler.Val_Audit_Prod_No);
		}
		
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		IAuditRouteService auditRouteService = AuditRouteFactory.getAuditRouteService();
		if (!auditRouteService.hasAuditRoute(brchNo, prodNo)){
			//没有审批路线，则审批结束
			return Line_Name_Audit_End;
		}
		
		Long nodeNo = (Long)executionContext.getContextInstance().getVariableLocally(Val_Audit_Route_Node_No, token);
		Long stationNo = (Long)executionContext.getContextInstance().getVariableLocally(Val_Audit_Route_Station_No, token);
		Double money = (Double)executionContext.getContextInstance().getVariableLocally(AuditRouteDecisionHandler.Val_Audit_Route_Money, executionContext.getToken());
		
		if (nodeNo == null){//尚未进入审批
			RouteAnalyseRs anaRs = auditRouteService.routeAnalyse(brchNo, prodNo, money, 1, 1);
			
			executionContext.getContextInstance().createVariable(Val_Audit_Route_Node_No, new Long(1), token);
			executionContext.getContextInstance().createVariable(Val_Audit_Route_Station_No, new Long(1), token);
			executionContext.getContextInstance().createVariable(Val_Audit_Users, anaRs.getAuditors(), token);
			return Line_Name_To_Audit;	
			
		}else {	
			RouteAnalyseRs anaRs = auditRouteService.routeAnalyse(brchNo, prodNo, money, nodeNo.intValue(), stationNo.intValue());
			Boolean isPass = (Boolean)executionContext.getContextInstance().getVariableLocally(Val_Audit_Is_Pass, token);
			if (isPass==null || isPass){//审批通过				
				if (anaRs.isEnd()){
					executionContext.getContextInstance().deleteVariable(Val_Audit_Route_Node_No, token);
					executionContext.getContextInstance().deleteVariable(Val_Audit_Route_Station_No, token);
					executionContext.getContextInstance().deleteVariable(Val_Audit_Is_Pass, token);
					executionContext.getContextInstance().deleteVariable(Val_Audit_Is_Back, token);
					if (anaRs.getStationMoney().compareTo(money)>=0)
						return Line_Name_Audit_End;
					else
						throw new ServiceException("各审批岗位审批完毕,但个岗位的审批权限都小于当前业务的审批金额【"+money+"】,该业务不能审批通过");
				}else{
					executionContext.getContextInstance().setVariable(Val_Audit_Route_Node_No, new Long(anaRs.getNextNodeSerialNo()), token);
					executionContext.getContextInstance().setVariable(Val_Audit_Route_Station_No, new Long(anaRs.getNextStationSerialNo()), token);
					executionContext.getContextInstance().setVariable(Val_Audit_Users, anaRs.getNextAuditors(), token);
					executionContext.getContextInstance().deleteVariable(Val_Audit_Is_Pass, token);
					executionContext.getContextInstance().deleteVariable(Val_Audit_Is_Back, token);
					return Line_Name_To_Audit;	
				}
			}else{//审批不通过
				Boolean isBack = (Boolean)executionContext.getContextInstance().getVariableLocally(Val_Audit_Is_Back, token);
				if (isBack==null || !isBack ||anaRs.getBeforeNodeSerialNo()<1){//不返回上一审批者
					executionContext.getContextInstance().deleteVariable(Val_Audit_Route_Node_No, token);
					executionContext.getContextInstance().deleteVariable(Val_Audit_Route_Station_No, token);
					executionContext.getContextInstance().deleteVariable(Val_Audit_Is_Pass, token);
					executionContext.getContextInstance().deleteVariable(Val_Audit_Is_Back, token);
					return Line_Name_Audit_Refuse;
				}else{//返回上一审批者
					executionContext.getContextInstance().setVariable(Val_Audit_Route_Node_No, new Long(anaRs.getBeforeNodeSerialNo()), token);
					executionContext.getContextInstance().setVariable(Val_Audit_Route_Station_No, new Long(anaRs.getBeforeStationSerialNo()), token);
					executionContext.getContextInstance().setVariable(Val_Audit_Users, anaRs.getBeforeAuditors(), token);
					
					executionContext.getContextInstance().deleteVariable(Val_Audit_Is_Pass, token);
					executionContext.getContextInstance().deleteVariable(Val_Audit_Is_Back, token);
					return Line_Name_To_Audit;	
				}
				
			}					
		}				*/	
		return null;
	}
}
